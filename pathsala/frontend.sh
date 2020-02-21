#!/usr/bin/env bash

repository="pathsala"
githubSshUrl="git@github.com:BhuwanUpadhyay/${repository}.git"
root=${PWD}
frontend="pathsala-frontend"
DATE=`date +%Y-%m-%d`

function serve() {
    echo "Starting ng server..."
    cd ${root}/${frontend}
    ng serve
}

function build() {
    echo "Preparing site..."
    cd ${root}/${frontend}
    ng build --prod --base-href "https://bhuwanupadhyay.github.io/pathsala/"
    cd ${root}
}

function publish() {
    echo "Cleaning temporary system path..."
    rm -rf tmp
    mkdir tmp
    build
    echo "Cloning remote site..."
    cd tmp && git clone -b gh-pages ${githubSshUrl}
    echo "Moving local site to remote site..."
    cd ${repository}
    cp -R ${root}/${frontend}/dist/${frontend}/* .
    echo "Publishing..."
    git add *
    git commit -m "Last Published On: ${DATE}"
    git push
    cd ${root}
    rm -rf tmp
    echo "Published...! -> DONE"
}

function usage() {
    echo "Usage: $(basename $0) <command> [<args>]"
    echo -e
    echo "Available commands:"
    echo "  serve          Start ng server for development"
    echo "  build          Prepare static website from ng"
    echo "  publish        Publish site"
    echo -e
}

function requires() {
    local command=$1
    which ${command} > /dev/null 2>&1 || { echo "!! This script requires [${command}] to be installed" && exit 1; }
}

requires 'git'

command=$1; shift
case "${command}" in
    serve)
        serve
        ;;
    build)
        build
        ;;
    publish)
        publish
        ;;
    *)
        usage
        exit 1
       ;;
esac