#!/usr/bin/env bash

apiTest() {
    CONTAINER_IMAGE=inventory-test-report:latest

    ./../../gradlew clean test -Dtest.enabled=true

    REPORT_CONTAINER=$(docker ps -a -q --filter "ancestor=${CONTAINER_IMAGE}")

    if test -z "$REPORT_CONTAINER"; then
        echo "Cool, No report container is running!"
    else
        docker stop ${REPORT_CONTAINER} && \
        docker rm ${REPORT_CONTAINER}
    fi

    docker build -t ${CONTAINER_IMAGE} . && \
    docker run -d -p 8980:80 inventory-test-report && \
    xdg-open http://localhost:8980/overview-features.html
}

launch() {
    docker-compose rm -f -s && \
    docker-compose up --remove-orphans
}

function usage() {
    echo "Usage: $(basename $0) <command> [<args>]"
    echo -e
    echo "Available commands:"
    echo "  launch"
    echo "  apiTest"
    echo -e
}

command=$1; shift
case "${command}" in
    launch)
        launch
        ;;
    apiTest)
        apiTest
        ;;
    *)
        usage
        exit 1
       ;;
esac