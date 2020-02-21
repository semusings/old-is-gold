#!/usr/bin/env bash

DOMAIN_NAME=bhojanalya.tk

aws s3 cp ./../dist/web-ui/ s3://${DOMAIN_NAME}/ --recursive --acl public-read
