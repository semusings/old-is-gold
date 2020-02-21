#!/usr/bin/env bash

open() {
    xdg-open http://localhost:7080
}

launch() {
    docker-compose rm -f -s postgres broker_app && \
    docker-compose up postgres broker_app
}

function usage() {
    echo "Usage: $(basename $0) <command> [<args>]"
    echo -e
    echo "Available commands:"
    echo "  launch"
    echo "  open"
    echo -e
}

command=$1; shift
case "${command}" in
    launch)
        launch
        ;;
    open)
        open
        ;;
    *)
        usage
        exit 1
       ;;
esac