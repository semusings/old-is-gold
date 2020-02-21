---
layout: post
title:  "Log aggregation for AWS Lambda"
author: developerbhuwan
categories: [ serverless, log-aggregation ]
image: assets/images/2018-09/log-aggregation-for-aws-lambda/cover.png
---

> "I get paid for produce code that works, not for writing tests." - Kent Back

In the post, I explained an approach of using a Lambda function and Kinesis Stream to ship all your lambda logs from
CloudWatch logs to a log aggregation service such as **ELK**-(Elasticsearch, Logstash, Kibana).

![Log aggregation for AWS Lambda](/assets/images/2018-09/log-aggregation-for-aws-lambda/cover.png "Log aggregation for AWS Lambda")
 
 Take a look at this repository [<i class="fa fa-github"></i> Github](https://github.com/BhuwanUpadhyay/log-aggregation-for-aws-lambda){:target="_blank"}
 to see how it works.