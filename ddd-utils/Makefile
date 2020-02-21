build:
	mvn clean install
copy-docs:
	cp -R ddd-utils-docs/target/contents/reference/htmlsingle/* docs/
gen-docs:
	mvn clean package -Pfull && \
	$(MAKE) copy-docs
deploy:
	@read -p "Revision: " revision; \
	mvn clean deploy -Prelease,central,full -Drevision=$${revision} -e && \
	$(MAKE) copy-docs
staging:
	@read -p "Sonatype Password: " passwd; \
	mvn -s mvn_settings.xml clean deploy -Pcentral -Dsonatype.user=developerbhuwan -Dsonatype.passwd=$${passwd}
gen-gpg:
	gpg --full-generate-key
export-gpg:
	cd ${HOME}/.gnupg && \
	gpg --export-secret-keys -o secring.gpg
publish-gpg-key:
	gpg -K
	@read -p "Gpg Key Id: " keyId; \
	gpg --send-keys --keyserver keyserver.ubuntu.com $${keyId}
