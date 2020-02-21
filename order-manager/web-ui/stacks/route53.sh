#!/usr/bin/env bash

STACK_NAME=bj-frontend-route53
DOMAIN_NAME=bhojanalya.tk

aws cloudformation create-stack \
    --template-body file://route53-zone.yml --stack-name ${STACK_NAME} \
    --parameters \
        ParameterKey=RootDomainName,ParameterValue=${DOMAIN_NAME}
