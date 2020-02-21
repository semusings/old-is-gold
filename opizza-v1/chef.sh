#!/usr/bin/env bash

set -e

./mvnw clean install -pl opizza-chef

VERSION=$(cat pom.xml | grep -E '\<version\>' | sed -n 3p |  cut -c 14- | cut -f 1 -d '<')

printf "Current opizza version ${VERSION}.\n"

echo "alias opizza='java -cp \"${PWD}/opizza-chef/target/opizza-jar-with-dependencies.jar\" io.github.bhuwanupadhyay.opizza.chef.OPizzaChef'" >> ~/.bashrc

gnome-terminal

