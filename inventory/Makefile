export KUBE_NAMESPACE := k8s-og
export APP_NAME := inventory
export KUBE_API_ENDPOINT
export APP_INGRESS := api.k8s-og-inventory.devbhuwan.$(KUBE_API_ENDPOINT)
helmUp:
	helm upgrade \
        --install \
        --force \
        --set image.pullPolicy=Always \
        --set ingress.enabled=true \
        --set ingress.hosts[0]=${APP_INGRESS} \
        --namespace=${KUBE_NAMESPACE} \
        ${APP_NAME}  \
        ./cicd/helm/${APP_NAME}
clean:
	helm delete --purge ${APP_NAME}
logs:
	./scripts/get-logs.sh ${KUBE_NAMESPACE} ${APP_NAME}
describe:
	./scripts/get-describe.sh ${KUBE_NAMESPACE} ${APP_NAME}
package:
	./gradlew clean build
image:
	cp -R build/libs/inventory-0.0.1-SNAPSHOT.jar cicd/docker/inventory.jar
	docker-compose build
	rm -R cicd/docker/inventory.jar
imageUp: image
	docker-compose up
publish:
	docker-compose push
up:
	@read -p "Kubernetes Api Endpoint: " KUBE_API_ENDPOINT; $(MAKE) helmUp
all: image publish up