
grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
  // inherit Grails' default dependencies
  inherits("global") {
    // specify dependency exclusions here; for example, uncomment this to disable ehcache:
    // excludes 'ehcache'
    //grails.plugin.excludes = ['somePluginCamelCaseName','someOtherPlugin']
	excludes 'slf4j-log4j12'
  }

  log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  checksums true // Whether to verify checksums on resolve

  repositories {
    inherits true // Whether to inherit repository definitions from plugins

    grailsPlugins()
    grailsHome()
    grailsCentral()

    mavenLocal()
    mavenCentral()

    // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
    //mavenRepo "http://snapshots.repository.codehaus.org"
    //mavenRepo "http://repository.codehaus.org"
    //mavenRepo "http://download.java.net/maven/2/"
    //mavenRepo "http://repository.jboss.com/maven2/"

    mavenRepo "http://download.java.net/maven/2/"
    mavenRepo "http://repository.jboss.org/nexus/content/groups/public-jboss/"

  }


  dependencies {
    // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
    // runtime 'mysql:mysql-connector-java:5.1.20'
  }


  plugins {
    compile ":activemq:0.4.1"
    compile ":bootstrap-file-upload:2.1.1"
    compile ":console:1.2"
    compile ":csv:0.3.1"
    compile ":fields:1.1"
    compile ":jms:1.2"
    compile ":rest:0.7"
    compile ":joda-time:1.4"
    compile ":ws-client:1.0"
    compile ":quartz2:0.2.3"
    compile ":springcache:1.3.1"
    compile ":perf4j:0.1.1"

      runtime ":hibernate:$grailsVersion"
//    runtime ":jquery:1.7.2"
//
//	compile ":cache-headers:1.1.5"
//	runtime ":cached-resources:1.0"
    runtime ":database-migration:1.1"
    runtime ":resources:1.1.6"
    runtime ":twitter-bootstrap:2.0.2.25"

    // Uncomment these (or add new ones) to enable additional resources capabilities
    //runtime ":zipped-resources:1.0"
    //runtime ":yui-minify-resources:0.1.4"

    build ":tomcat:$grailsVersion"

    compile ':cache:1.0.0'
	compile ":executor:0.3"
  }
}
