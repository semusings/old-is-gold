#!/usr/bin/env bash

repository="BhuwanUpadhyay.github.io"
githubIoSshUrl="git@github.com:BhuwanUpadhyay/${repository}.git"
root=${PWD}
DATE=`date +%Y-%m-%d`

function serve() {
    echo "Starting jekyll server..."
    bundle exec jekyll serve --watch
}

function build() {
    echo "Preparing site..."
    bundle install
    JEKYLL_ENV=production bundle exec jekyll build
}

function publish() {
    echo "Cleaning temporary system path..."
    rm -rf tmp
    mkdir tmp
    build
    echo "Cloning remote site..."
    cd tmp && git clone ${githubIoSshUrl}
    echo "Moving local site to remote site..."
    cd ${repository}
    cp -R ${root}/_site/* .
    echo "Publishing..."
    git add *
    git commit -m "Last Published On: ${DATE}"
    git push
    cd ../../
    rm -rf tmp
    rm -rf _site
    echo "Published...! -> DONE"
}

function usage() {
    echo "Usage: $(basename $0) <command> [<args>]"
    echo -e
    echo "Available commands:"
    echo "  serve          Start jekyll server for development"
    echo "  build          Prepare static website from hugo site"
    echo "  publish        Publish site"
    echo -e
}

function requires() {
    local command=$1
    which ${command} > /dev/null 2>&1 || { echo "!! This script requires [${command}] to be installed" && exit 1; }
}

requires 'hugo'
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
