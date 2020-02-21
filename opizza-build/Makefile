build:
	mvn clean install
staging:
	@read -p "Sonatype Password: " passwd; \
	mvn -s mvn_settings.xml clean deploy -Pcentral -Dsonatype.user=developerbhuwan -Dsonatype.passwd=$${passwd}
release:
	@read -p "Sonatype Password: " passwd; \
	mvn -s mvn_settings.xml clean deploy -Prelease,central -Dsonatype.user=developerbhuwan -Dsonatype.passwd=$${passwd}
gen-gpg:
	gpg --full-generate-key
export-gpg:
	cd ${HOME}/.gnupg && \
	gpg --export-secret-keys -o secring.gpg
publish-gpg-key:
	gpg -K
	@read -p "Gpg Key Id: " keyId; \
	gpg --send-keys --keyserver keyserver.ubuntu.com $${keyId}
clear-cache:
	rm -rf ~/.m2/repository/io/github/bhuwanupadhyay && \
	ls -l  ~/.m2/repository/io/github