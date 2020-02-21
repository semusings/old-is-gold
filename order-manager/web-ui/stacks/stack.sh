#!/usr/bin/env bash

STACK_NAME=bj-frontend
DOMAIN_NAME=bhojanalya.tk

aws cloudformation create-stack \
    --template-body file://template.yml --stack-name ${STACK_NAME} \
    --parameters \
        ParameterKey=RootDomainName,ParameterValue=${DOMAIN_NAME}


# Update name servers to your domain provider using aws->route53->Hosted zones
