import grails.util.BuildSettings
import grails.util.Environment

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

root(ERROR, ['STDOUT'])

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}

logger("grails.app.controllers.th.co.ais",  DEBUG,   ['STDOUT'],   false)
logger("grails.app.domain.th.co.ais",       DEBUG,   ['STDOUT'],   false)
logger("grails.app.services.th.co.ais",     DEBUG,   ['STDOUT'],   false)
logger("grails.app.taglib.th.co.ais",       DEBUG,   ['STDOUT'],   false)
logger("grails.app.conf.th.co.ais",         DEBUG,   ['STDOUT'],   false)
logger("grails.app.filters.th.co.ais",      DEBUG,   ['STDOUT'],   false)
logger("grails.app.jobs.th.co.ais",         DEBUG,   ['STDOUT'],   false)

logger("org.springframework.security",      DEBUG,   ['STDOUT'],   false)
logger("grails.plugin.springsecurity",      DEBUG,   ['STDOUT'],   false)
logger("org.pac4j",                         DEBUG,   ['STDOUT'],   false)
