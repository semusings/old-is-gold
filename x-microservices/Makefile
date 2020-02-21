flydb:
	./gradlew clean flywaymigrate
jooq:
	./gradlew generateOrderingJooqSchemaSource
proto:
	./gradlew generateProto
clean:
	./gradlew clean
unit:
	$(MAKE) clean && \
	$(MAKE) jooq && \
	$(MAKE) proto && \
	./gradlew test
build:
	./gradlew clean build dockerBuildImage
ui:
	cd web && \
	ng serve --open
gen-ui:
	ng new web --prefix oms && \
	cd web && \
	ng add @angular/material
infra:
	docker-compose rm && docker-compose up
rabbit:
	docker-compose rm rabbitmq && docker-compose up rabbitmq
mongo:
	docker-compose rm mongo && docker-compose up mongo
axon:
	docker-compose rm axonserver && docker-compose up axonserver