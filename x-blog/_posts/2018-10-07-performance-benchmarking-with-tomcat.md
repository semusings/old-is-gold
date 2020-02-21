---
layout: post
title:  "Performance Benchmarking with Tomcat"
author: developerbhuwan
categories: [ cloud-native, performance-benchmarking ]
image: assets/images/2018-10/performance-benchmarking-with-tomcat/cover.png
---

> "Just as athletes can’t win without a sophisticated mixture of strategy, form, attitude, tactics, and speed, performance engineering requires a good collection of metrics and tools to deliver the desired business results." - Todd DeCapua

On this article, I am going to explain how to use **Elastic APM** with tomcat.

![Elastic APM](/assets/images/2018-10/performance-benchmarking-with-tomcat/cover.png)

The Dockerfile for application to run `elastic-apm-agent.jar` with tomcat.
~~~dockerfile
FROM tomcat:8.5.34-jre8-alpine
ARG WAR_FILE
ARG ELASTIC_APM_AGEN_VERSION=0.7.0
COPY ${WAR_FILE} /usr/local/tomcat/app.war
RUN cd /usr/local/tomcat && \
    rm -rf webapps/* && \
    mkdir -p webapps/ROOT && \
    unzip app.war -d webapps/ROOT > /dev/null && \
    rm /usr/local/tomcat/app.war
RUN wget https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/${ELASTIC_APM_AGEN_VERSION}/elastic-apm-agent-${ELASTIC_APM_AGEN_VERSION}.jar \
    -O ${HOME}/elastic-apm-agent.jar
ADD bin /usr/local/tomcat/bin
WORKDIR /usr/local/tomcat/bin
ENV JPDA_ADDRESS 8000
CMD ["catalina.sh", "jpda", "run"]
~~~ 

 ```bash
mkdir bin && cd bin
touch setenv.sh
chmod +x setenv.sh
```
 
 `setenv.sh`
 
 ```bash
#!/usr/bin/env bash
export CATALINA_OPTS="
        $CATALINA_OPTS -javaagent:${HOME}/elastic-apm-agent.jar
        -Delastic.apm.service_name=${ELASTIC_APM_SERVICE_NAME}
        -Delastic.apm.application_packages=${ELASTIC_APM_APPLICATION_PACKAGES}
        -Delastic.apm.server_urls=${ELASTIC_APM_SERVER_URLS}
"
echo ""
echo "--------------------------------------------------------------"
echo "CATALINA_OPTS: ${CATALINA_OPTS}"
echo "--------------------------------------------------------------"
echo ""
```

`docker-compose.yml`

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
  elastic-apm:
    image: docker.elastic.co/apm/apm-server:6.4.2
    command: [
        "bash",
        "-c",
        "apm-server run -e -E
                          output.elasticsearch.hosts=['http://apm-elasticsearch:9200']
                          "]
    links:
      - apm-elasticsearch
    ports:
      - 8200
  performance-benchmarking-with-tomcat:
    image: docker.io/devbhuwan/performance-benchmarking-with-tomcat
    build:
      context: cicd/docker
      dockerfile: Dockerfile
      args:
      - WAR_FILE=springboot-order-crud-service.war
    ports:
    - 8000:8000
    - 8080:8080
    environment:
      ELASTIC_APM_SERVICE_NAME: performance-benchmarking-with-tomcat
      ELASTIC_APM_APPLICATION_PACKAGES: io.github.bhuwanupadhyay
      ELASTIC_APM_SERVER_URLS: http://elastic-apm:8200
    depends_on:
    - apm-elasticsearch
    - elastic-apm
    - kibana
    links:
    - elastic-apm
```
 
 Take a look at this repository [<i class="fa fa-github"></i> Github](https://github.com/BhuwanUpadhyay/performance-benchmarking-with-tomcat){:target="_blank"}
 to see how it works.