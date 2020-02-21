---
layout: post
title:  "How to deploy spring boot application in aws elastic beanstalk"
author: developerbhuwan
categories: [ cloud-native, spring-boot, aws-elastic-beanstalk ]
image: assets/images/2018-08/how-to-deploy-spring-boot-application-in-beanstalk/cover.png
featured: true
draft: true
---

> "Talk is cheap. Show me the code." - Linus Torvalds

## Create Spring Boot Application

We can use [SPRING INITIALIZR](https://start.spring.io/){:target="_blank"} to create a spring boot application.
![Spring Intializr](/assets/images/2018-08/sample/create-spring-boot-application.png)

Now, we can create one simple rest controller using spring mvc.

{% highlight java %}
@RestController
@RequestMapping("/simple")
public class SimpleEndpoints {

    @GetMapping
    public ResponseEntity<String> simpleTest() {
        return ResponseEntity.ok("Simple is ok.");
    }
}
{% endhighlight %}

Simply, use `mvn clean package` to build the project, after that spring boot jar is ready for deployment.

Next step is define cloudformation template.

## CloudFormation Template



## Deploy Using Terminal


## Deploy With Pipeline


[<i class="fa fa-github"></i> SourceCode](https://github.com/BhuwanUpadhyay/how-to-deploy-spring-boot-application-in-aws-elastic-beanstalk){:target="_blank"}