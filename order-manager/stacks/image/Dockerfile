FROM maven:3.5.4-jdk-8-alpine

# Installing awscli
RUN \
	apk -Uuv add groff less python py-pip nodejs nodejs-npm && \
	pip install awscli && \
	apk --purge -v del py-pip && \
	rm /var/cache/apk/*
RUN npm --version && \
    npm install -g serverless
# Caching .m2 folder needed dependencies for order-manager to speedup build cycle
RUN mkdir --parents /usr/src/app

ENV APP_DIR /usr/src/app
WORKDIR ${APP_DIR}
ADD . ${APP_DIR}
RUN ls -l
RUN mvn clean verify --fail-never
RUN rm -rf * && ls -l