#!/usr/bin/env bash

set -e
read -p "AWS Region: "
export AWS_REGION=$REPLY

cim stack-up