---
layout: post
title:  "Serverless AWS Lambda function with dynamo"
author: developerbhuwan
categories: [ serverless, aws-lambda ]
---

> "Twelve-factor processes are stateless and share-nothing. Any data that needs to persist must be stored in a stateful backing service, typically a database." - The Twelve-Factor App

## Setup Project
Let's start from beginning, Open a terminal and write down following commands:
```bash
mkdir serveless-aws-lambda-function-with-dynamodb
cd serveless-aws-lambda-function-with-dynamodb
gradle init
```
Now, we need to add dependencies for ***aws lambda*** and ***dynamo db*** in `build.gradle`. 
Along with ***JUnit 5*** for testing purpose, ***lombok*** to minimize boilerplate code 
and ***common-lang3*** to use common functionalities like translate
exception stacktrace into string 
which one i will use on this demo.

Finally, `build.gradle`  looks like as below:
{% highlight groovy %}
buildscript {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
}

apply plugin: "java"

dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.2'
    compile("org.apache.commons:commons-lang3:3.7")
    compile("com.amazonaws:aws-lambda-java-core:1.1.0")
    compile("com.amazonaws:aws-java-sdk-dynamodb:1.11.377")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}

test {
    useJUnitPlatform()
}

tasks.withType(Test) {
    testLogging {
        events "started", "passed", "skipped", "failed"
    }
}

task buildZip(type: Zip) {
    from compileJava
    from processResources
    into('lib') {
        from configurations.runtime
    }
}

build.dependsOn buildZip
{% endhighlight %}
## Create Handler Functions
Let's consider we have to expose APIs to manage an order:
 - Put order into the db (Dynamo)
 - Retrieve orders from the db (Dynamo).
 
To achieve these two operations in serverless, we need two lambda functions with http event type:
 - POST : Store order into the db 
 - GET  : Fetch orders to the client

## AWS Lambda Proxy Integration I/O format

Before write a code, let's understand input and output format supported by 
aws lambda proxy integration in API gateway. You can check on following links: 
 - [Input Format](https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-input-format){:target="_blank"}
 - [Output Format](https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-output-format){:target="_blank"}

According to AWS documentation, java class for input format is:
{% highlight java %}
@Data
public class ApiGatewayRequest {

    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Map<String, String> pathParameters;
    private Map<String, String> stageVariables;
    private Map<String, Object> requestContext;
    private String body;
    private boolean isBase64Encoded;

    @SneakyThrows
    <T> T toBody(Class<T> valueType) {
        return MAPPER.readValue(body, valueType);
    }
}
{% endhighlight %}
and java class for out format with its builder class to simply construction object simple:
{% highlight java %}
@Getter
public class ApiGatewayResponse {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers = new HashMap<>();
    private final boolean isBase64Encoded;

    private ApiGatewayResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
        this.setHeaders();
        this.isBase64Encoded = false;
    }

    public static ApiGatewayResponse bad(String message) {
        return build(HttpStatus.SC_BAD_REQUEST, message);
    }

    public static <T> ApiGatewayResponse ok(T body) {
        return build(HttpStatus.SC_OK, body);
    }

    static ApiGatewayResponse serverError(String message) {
        return build(HttpStatus.SC_INTERNAL_SERVER_ERROR, message);
    }

    @SneakyThrows
    private static <T> ApiGatewayResponse build(int statusCode, T body) {
        return new ApiGatewayResponse(statusCode, MAPPER.writeValueAsString(body));
    }

    private void setHeaders() {
        headers.put("X-Powered-By", "BhuwanUpadhyay");
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
    }
}
{% endhighlight %}

### AWS Dynamo DB Persistence
To persist customer orders into the dynamo db we need to define data object which match with 
our table structure in dynamo.
{% highlight java %}
@Data
@DynamoDBTable(tableName = "Order")
public class Order {

    @DynamoDBHashKey
    private String orderId;
    private String description;
    private String customer;
}
{% endhighlight %}
 
 Now, we need a repository to interact with dynamo db.
{% highlight java %}
public class OrderRepository {

    private final DynamoDBMapper mapper;

    public OrderRepository() {
        mapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard().build());
    }

    public List<Order> getOrders() {
        PaginatedScanList<Order> orders = mapper.scan(Order.class, new DynamoDBScanExpression());
        return new ArrayList<>(orders);
    }

    public Order save(Order order) {
        mapper.save(order);
        return mapper.load(order);
    }

    public Optional<Order> findByOrderId(String orderId) {
        return Optional.ofNullable(mapper.load(Order.class, orderId));
    }
}
{% endhighlight %}
  
### AWS Serverless Lambda Function  
Now, before create lambda function i will create one abstract class which encapsulate common
behaviour of all lambda function which are going to create on this demo.

{% highlight java %}
public abstract class HttpEventHandler<T> implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        try {
            return this.handle(toRequestIfNoVoidType(input), context.getLogger());
        } catch (Exception e) {
            return ApiGatewayResponse.serverError(ExceptionUtils.getStackTrace(e));
        }
    }

    protected abstract ApiGatewayResponse handle(T request, LambdaLogger log);

    private T toRequestIfNoVoidType(ApiGatewayRequest input) {
        final Class<T> bodyType = getBodyType();
        return !bodyType.equals(Void.class) ? input.toBody(bodyType) : null;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getBodyType() {
        try {
            String typeName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            return (Class<T>) Class.forName(typeName);
        } catch (Exception e) {
            throw new RuntimeException("Class is not parametrized with generic type!!! Please use extends <> ", e);
        }
    }

}
{% endhighlight %}

Now, time come to create our serverless lambda function. 
 - `OrderProcessHandler`
{% highlight java %} 
public class OrderProcessHandler extends HttpEventHandler<Order> {

    private final OrderRepository repository = new OrderRepository();

    @Override
    protected ApiGatewayResponse handle(Order request, LambdaLogger log) {
        Optional<Order> order = repository.findByOrderId(request.getOrderId());
        return order.
                map(o -> bad(String.format("Order already exist with id %s", o.getOrderId()))).
                orElse(ok(repository.save(request)));
    }

} 
{% endhighlight %} 

 - `FetchOrderHandler`
{% highlight java %} 
public class FetchOrderHandler extends HttpEventHandler<Void> {

    private final OrderRepository repository = new OrderRepository();

    @Override
    protected ApiGatewayResponse handle(Void request, LambdaLogger log) {
        log.log("Fetching orders....");
        return ApiGatewayResponse.ok(repository.getOrders());
    }
}
{% endhighlight %} 

We have done with coding so far.

Next step is launch our function into aws cloud platform and evaluate the result.

## Serverless Framework
Severless framework simply deployment process for lambda function. To use it follow the following commands:
```bash
npm install -g serverless
touch serverless.yml
```
And modify `serverless.yml` like below:
```yaml
service: order-service

provider:
  name: aws
  runtime: java8
  stage: ${opt:stage, 'dev'}
  iamRoleStatements:
  - Effect: "Allow"
    Resource: "*"
    Action:
    - "dynamodb:*"

package:
  artifact: build/distributions/serveless-aws-lambda-function-with-dynamodb.zip

functions:
  order-process:
    handler: bhuwanupadhyay.serverless.order.OrderProcessHandler
    events:
    - http:
        path: orders
        method: post
        cors: true

  fetch-order:
    handler: bhuwanupadhyay.serverless.order.FetchOrderHandler
    events:
    - http:
        path: orders
        method: get
        cors: true

resources:
  Resources:
    Order:
      Type: AWS::DynamoDB::Table
      Properties:
        TableName: Order
        AttributeDefinitions:
        - AttributeName: orderId
          AttributeType: S
        KeySchema:
        - AttributeName: orderId
          KeyType: HASH
        ProvisionedThroughput:
          ReadCapacityUnits: 1
          WriteCapacityUnits: 1
```


## Build, Deploy and Test Using Terminal 
`build-and-deploy.sh`
```bash
#!/usr/bin/env bash
./gradlew clean build

LOG_FILE=deploy.aws.log
serverless deploy -v | tee ${LOG_FILE}

export ServiceEndpoint=$(awk '/ServiceEndpoint:/' ${LOG_FILE} | cut -c18-200)
echo "API Service Endpoint: ${ServiceEndpoint}"

echo "----------------------POST------------------------------"
echo "Testing POST: /orders"
curl -H "Content-Type: application/json" \
        -X POST --data @Order.json \
        ${ServiceEndpoint}/orders
echo ""
echo "--------------------------------------------------------"

echo "-----------------------GET------------------------------"
echo "Testing GET: /orders"
curl -X GET \
        ${ServiceEndpoint}/orders
echo ""
echo "--------------------------------------------------------"

echo ""
echo "Cleanup..."
serverless remove
```

You can find example on github: [<i class="fa fa-github"></i> SourceCode](https://github.com/BhuwanUpadhyay/serveless-aws-lambda-function-with-dynamodb){:target="_blank"}
