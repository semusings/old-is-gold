#!/usr/bin/env bash

KUBE_NAMESPACE=$2
DEPLOYMENT=$3
echo "------------------------------------------"
printf "Kube Namespace: ${KUBE_NAMESPACE}\nDeployment: ${DEPLOYMENT}\n"
echo "------------------------------------------"

describe() {
    for p in $(kubectl get pods --namespace ${KUBE_NAMESPACE} | grep ^${DEPLOYMENT} | cut -f 1 -d ' '); do
        kubectl describe pod ${p} --namespace ${KUBE_NAMESPACE}
    done
}

logs() {
    for p in $(kubectl get pods --namespace ${KUBE_NAMESPACE} | grep ^${DEPLOYMENT} | cut -f 1 -d ' '); do
        kubectl logs -f ${p} --namespace ${KUBE_NAMESPACE}
    done
}

function usage() {
    echo "Usage: $(basename $0) <command> [<args>]"
    echo -e
    echo "Available commands:"
    echo "  logs"
    echo "  describe"
    echo -e
}

command=$1; shift
case "${command}" in
    logs)
        logs
        ;;
    describe)
        describe
        ;;
    *)
        usage
        exit 1
       ;;
esac
