---
layout: post
title:  "AWS Lambda load testing with large scale data using Gatling"
author: developerbhuwan
categories: [ serverless, load-testing, gatling ]
image: assets/images/2018-09/aws-lambda-load-testing-with-large-scale-data-using-gatling/cover.png
---

> "No amount of testing can prove a software right, a single test can prove a software wrong." - Amir Ghahrai

Let's say, We have to perform **Two Million** HTTP requests load in AWS Lambda function via API Gateway.
Obviously, to perform 2M requests we need to run load testing in cluster mode.
[Gatling Frontline](https://gatling.io/gatling-frontline/){:target="_blank"} support cluster load testing directly. With Gatling Community Version we need to do some extra work to achieve cluster mode load testing.

![AWS Lambda load testing with large scale data using Gatling](/assets/images/2018-09/aws-lambda-load-testing-with-large-scale-data-using-gatling/cover.png "AWS Lambda load testing with large scale data using Gatling")

I will use [gatling-aws-maven-plugin](https://github.com/electronicarts/gatling-aws-maven-plugin){:target="_blank"} to run 20 EC2 instances together, which runs load testing parallelly.

For 2M http requests by using 20 instances together, in gatling our plan will be like below:
{% highlight bash %}
REPETITION -> 2000
NO_OF_USERS -> 50
-----------------------------------------------
TOTAL REQUEST = 2000 * 50 = 100000
WITH 20 INSTANCES = 100000 * 20 = 2000000 = 2M
{% endhighlight %} 

Now our load testing simulation will looks like:

{% highlight java %}
import java.util.UUID.randomUUID
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class LoadTestingSimulation extends Simulation {

  private val ENDPOINT = "https://xxxxx.execute-api.ap-southeast-2.amazonaws.com/test"
  private val BODY: String = "{\"customerId\" : \"@@customerId@@\" }"

  private val scn: ScenarioBuilder = scenario("Load Testing")
    .repeat(2000) {
      exec(
        http("initiation")
          .post(ENDPOINT + "/orders")
          .check(status.is(200))
          .body(StringBody(session => BODY.replaceAll("@@customerId@@", newId)))
          .header("Content-type", "text/xml")
      )
    }

  setUp(scn.inject(atOnceUsers(50))
    .protocols(http
      .baseURL(ENDPOINT)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")))

  private def newId = {
    randomUUID().toString.substring(0, 33)
  }

}
{% endhighlight %}

### Project Structure 
![Project Structure](/assets/images/2018-09/aws-lambda-load-testing-with-large-scale-data-using-gatling/project-structure.png "Project Structure") &nbsp;&nbsp; [<i class="fa fa-github"></i> SourceCode](https://github.com/BhuwanUpadhyay/aws-lambda-load-testing-with-large-scale-data-using-gatling){:target="_blank"}


### STEPS
- Run `downloadGatling.sh`
- Provide correct AWS `accessKey` and `secretKey` in `aws.properties`
- Change values in `runLoadtestRemotely.sh` according to you configuration
{% highlight bash %}
S3_BUCKET=load-testing-s3-bucket
EC2_KEY_PAIR=loadtest-keypair
NO_OF_INSTANCES=20
EC2_ENDPOINT=https://ec2.us-west-1.amazonaws.com
EC2_SECURITY_GROUP_ID=sg-xxxxxxx
AMI_ID=ami-0782017a917e973e7
SSH_USER=ec2-user
........
{% endhighlight %}
- Deploy order api in aws run `./deploy.sh`
- Run `runLoadtestRemotely.sh`
