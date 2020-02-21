#!/usr/bin/env bash

set -e
read -p "AWS Region: "
export AWS_REGION=$REPLY

function up() {
    cim stack-up
}

function down() {
    cim stack-delete
}

function usage() {
    echo "Usage: $(basename $0) <command> [<args>]"
    echo -e
    echo "Available commands:"
    echo "  up          Create stack"
    echo "  down        Delete stack"
    echo -e
}

function requires() {
    local command=$1
    which ${command} > /dev/null 2>&1 || { echo "!! This script requires [${command}] to be installed" && exit 1; }
}

requires 'cim'

command=$1; shift
case "${command}" in
    up)
        up
        ;;
    down)
        down
        ;;
    *)
        usage
        exit 1
       ;;
esac

