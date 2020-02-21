#!/usr/bin/env bash

set -e

echo Staging started on `date`

STAGE=test
LOG_FILE=sls-${STAGE}.log

echo "Deploying staging cloud formation stack..."
serverless deploy --stage ${STAGE} -v | tee ${LOG_FILE}

export ServiceEndpoint=$(awk '/ServiceEndpoint:/' ${LOG_FILE} | cut -c18-200)

echo "Api Endpoint: ${ServiceEndpoint}"

echo "Preparing Data For Acceptance Tests..."

echo "RUNNING ACCEPTANCE TESTS"
mvn clean verify -Ptest -pl :acceptance-tests

echo "Deleting staging cloud formation stack..."
serverless remove --stage test