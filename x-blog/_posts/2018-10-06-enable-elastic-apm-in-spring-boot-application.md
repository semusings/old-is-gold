---
layout: post
title:  "Enable Elastic APM in Spring Boot Application"
author: developerbhuwan
categories: [ cloud-native, performance-benchmarking ]
image: assets/images/2018-10/enable-elastic-apm-in-spring-boot-application/cover.png
---

> "In business, the idea of measuring what you are doing, picking the measurements that count like customer satisfaction and performance… you thrive on that." - Bill Gates

On this article, I am going to explain how to use **Elastic APM** in detail. Here, APM stands for Application Performance Monitoring.

![Elastic APM](/assets/images/2018-10/enable-elastic-apm-in-spring-boot-application/cover.png)

**Elastic APM** instruments the applications to ship performance metrics to Elasticsearch for visualization in Kibana with pre-configured dashboards.

First of all, We need to create one spring boot project with JPA support, to do so I will use a spring command line which will create a skeleton project for us.
The following command we need to run for the setup of the project:
```bash
spring init\
 -n=order-service\
 -d=web,jpa,h2,lombok\
 --groupId=io.github.bhuwanupadhyay\
 --package-name=io.github.bhuwanupadhyay.order\
 --description="Order Service"\
 --version=1.0-SNAPSHOT\
 --language=java \
order-service
```
Note: if you don't installed spring command line tool yet please [refer](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html){:target="_blank"}.

Now, Let's create simple CRUD operation REST endpoints for order domain.

{% highlight java %}

@Entity
@Table(name = "app_orders")
@Getter
class Order {
    @Id
    private String orderId;
    private String itemId;
    private String customerId;
}

interface OrderRepository extends CrudRepository<Order, String> {
}

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
class OrderEndpoints {

    private final OrderRepository repository;

    @GetMapping
    public ResponseEntity<List<Order>> listOrders() {
        return ResponseEntity.ok((List<Order>) repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(repository.save(order));
    }

}

{% endhighlight %}

The Dockerfile for order-service application to run `elastic-apm-agent.jar`
~~~dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG ELASTIC_APM_AGEN_VERSION=0.7.0
RUN wget https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/${ELASTIC_APM_AGEN_VERSION}/elastic-apm-agent-${ELASTIC_APM_AGEN_VERSION}.jar \
    -O ${HOME}/elastic-apm-agent.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [
    "java",
    "-javaagent:${HOME}/elastic-apm-agent.jar",
    "-Djava.security.egd=file:/dev/./urandom",
    "-jar",
    "/app.jar"
    ]
~~~

The complete `docker-compose.yml` file which includes all necessary components:
- Elasticsearch
- Kibana
- Elastic APM Server
- Order Service [ Spring Boot Application ]

```yaml
version: "3"
services:
  apm-elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:6.4.2
    ports:
    - 9200
  kibana:
    image: docker.elastic.co/kibana/kibana:6.4.2
    environment:
      ELASTICSEARCH_URL: http://apm-elasticsearch:9200
    ports:
    - 5601:5601
    links:
    - apm-elasticsearch
    depends_on:
    - apm-elasticsearch
  elastic-apm-server:
    image: docker.elastic.co/apm/apm-server:6.4.2
    command: [
      "bash",
      "-c",
      "apm-server run -e -E
                          output.elasticsearch.hosts=['http://apm-elasticsearch:9200']
                          "]
    ports:
    - 8200
    links:
    - apm-elasticsearch
    depends_on:
    - apm-elasticsearch
  order-service:
    image: docker.io/devbhuwan/order-service
    build:
      context: .
      dockerfile: Dockerfile
      args:
      - JAR_FILE=target/order-service-1.0-SNAPSHOT.jar
    ports:
    - 8080:8080
    environment:
      ELASTIC_APM_SERVICE_NAME: order-service
      ELASTIC_APM_APPLICATION_PACKAGES: io.github.bhuwanupadhyay
      ELASTIC_APM_SERVER_URLS: http://elastic-apm-server:8200
    links:
    - elastic-apm-server
    depends_on:
    - elastic-apm-server
```
 
 The following command to launch service with elastic apm agent and apm server components:
 ~~~bash
 mvn clean package && docker-compose up
 ~~~
 
 Finally, we need to perform load testing to see some result in kibana dashboard. To do so i will use load testing tool 
 [artillery](https://github.com/shoreditch-ops/artillery){:target="_blank"}.
 ```bash
mkdir artillery-load-testing
cd artillery-load-testing
touch package.json script.yml processor.js
```

`package.json`

```json
{
  "name": "artillery-load-testing",
  "version": "1.0.0",
  "description": "Artillery Load Testing",
  "main": "index.js",
  "scripts": {
    "test": "./node_modules/.bin/artillery run script.yml"
  },
  "license": "MIT",
  "dependencies": {
    "artillery": "^1.6.0-24",
    "uuid": "^3.3.2"
  }
}
```

`script.yml`

```yaml
config:
  target: "http://localhost:8080"
  processor: "./processor.js"
  phases:
  - duration: 60
    arrivalRate: 20
scenarios:
- name: "POST"
  flow:
  - post:
      headers:
        content-type: "application/json"
      url: "/orders"
      beforeRequest: "assignNewId"
      body: |
        {
          "orderId": "@@id@@",
          "itemId": "1",
          "customerId": "1"
        }
```

`processor.js`

```js
'use strict';

const uuidv1 = require('uuid/v1');
// noinspection JSUnresolvedVariable
module.exports = {assignNewId};

function assignNewId(requestParams, context, eventEmitter, done) {
    const id = uuidv1();
    requestParams.body = requestParams.body
        .replace("@@id@@", id);
    return done();
}
```


The following command to perform load testing:
```bash
npm install
npm run test
```
 
Now, we need to analyze performance metrics in kibana dashboard under `APM` menu link in left sidebar.
 
 
 
 Take a look at this repository [<i class="fa fa-github"></i> Github](https://github.com/BhuwanUpadhyay/enable-elastic-apm-in-spring-boot-application){:target="_blank"}
 to see how it works.