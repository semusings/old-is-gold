# OPizza Build Infrastructure

This repository contains common infrastructure to be used by OPizza modules that build with Maven. It consists of a *resources* project that bundles up resources that are needed during the build CSS for reference documentation generation and JavaDoc. The second project is *parent* that can be used as parent project to pre-configure core dependencies, properties, reference documentation generation and most important of all the appropriate distribution assembly.

The parent project can be eased for either a single-module Maven project or a multi-module one. Each of the setups requires a slightly different setup of the project.

## Project setup

### General setup

The parent project configures the following aspects of the project build:

Shared resources are pulled in from the `opizza-build-resources` dependency (images, CSS, XSLTs for documentation generation). Renders reference documentation from Docbook file named `index.xml` within `src/docbkx`.

```
- changelog.txt
- license.txt
- notice.txt
- readme.txt
+ reference -> Docbook generated reference documentation
  + html
  + pdf
  + epub
+ api -> JavaDoc
```

  
The following dependencies are pre-configured.
  
- Logging dependencies: SLF4j + Commons Logging bridge and Logback as test dependency
- Test dependencies: JUnit / Hamcrest / Mockito
- Dependency versions for commonly used dependencies

### Single project setup

If the client project is a project consisting of a single project only all that needs to be done is declaring the parent project:

```xml
<parent>
	<groupId>io.github.bhuwanupadhyay.build</groupId>
	<artifactId>opizza-parent</artifactId>
	<version>${most-recent-release-version}</version>
</parent>
```
    
Be sure to adapt the version number to the latest release version. The second and already last step of the setup is to activate the assembly and wagon plugin in the build section:

```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-assembly-plugin</artifactId>
</plugin>
<plugin>
	<groupId>org.codehaus.mojo</groupId>
	<artifactId>wagon-maven-plugin</artifactId>
</plugin>
```
	
### Multi project setup
	
A multi module setup requires slightly more setup and some structure being set up. 

- The root `pom.xml` needs to configure the `project.type` property to `multi`.
- Docbook documentation sources need to be in the root project.
- The assembly needs to be build in a dedicated sub-module (e.g. `distribution`), declare the assembly plugin (see single project setup) in that submodule and reconfigure the `project.root` property in that module to `${basedir}/..`.
- Configure `${dist.id}` in the root project to the basic artifact id (e.g. `opizza-order-service`) as this will serve as file name for distribution artifacts, static resources etc. It will default to the artifact id and thus usually resolve to a `…-parent` if not configured properly.

As an example have a look at the build of [OPizza Order Service](http://github.com/BhuwanUpadhyay/opizza-order-service).

## Build configuration

- Configure "Artifactory Maven 3" task
- Goals to execute `clean (dependency:tree) install -Pci`
- A nightly build can then use `clean (dependency:tree) deploy -Pdistribute` to publish static resources and reference documentation

## Additional build profiles

- `ci` - Packages the JavaDoc as JAR for distribution (needs to be active on the CI server to make sure we distribute JavaDoc as JAR).
- `distribute` - Creates Docbook documentation, assembles the distribution zip, etc.
- `milestone` - Configures the binary distribution to upload to the milestone S3 repository.
- `release` - Configures the binary distribution to upload to the release S3 repository.
