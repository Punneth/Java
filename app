package com.transaction.common.util;

import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * Application logger wrapper that standardizes logging format.
 * 
 * Features:
 * - Automatically adds type="APPLICATION"
 * - Automatically retrieves and adds traceId and apiName from MDC
 * - Automatically adds timestamp in ISO-8601 format
 * - Automatically adds thread name
 * - Automatically adds method name
 * - Includes stack trace for ERROR level logs
 * - Outputs in Logstash structured log format
 * 
 * Usage example:
 * <pre>
 * private static final ApplicationLogger logger = ApplicationLogger.getLogger(MyService.class);
 * logger.info("Processing request");
 * logger.warn("Invalid data: {}", data);
 * logger.error("Operation failed", exception);
 * </pre>
 */
public class ApplicationLogger {
    private static final String LOG_TYPE = "APPLICATION";
    
    private final Logger delegate;
    private final String className;

    private ApplicationLogger(Logger delegate, String className) {
        this.delegate = delegate;
        this.className = className;
    }

    /**
     * Get ApplicationLogger instance
     */
    public static ApplicationLogger getLogger(Class<?> clazz) {
        return new ApplicationLogger(
            LoggerFactory.getLogger(clazz),
            clazz.getSimpleName()
        );
    }

    /**
     * Log at INFO level
     */
    public void info(String message) {
        delegate.info(Markers.appendEntries(createLogMap(message)), message);
    }

    /**
     * Log at INFO level with variable arguments
     */
    public void info(String message, Object... args) {
        delegate.info(Markers.appendEntries(createLogMap(message)), message, args);
    }

    /**
     * Log at INFO level with custom fields
     * Merges custom fields with default log fields under "details" key
     */
    public void info(Map<String, Object> customFields, String message) {
        Map<String, Object> logMap = createLogMap(message);
        logMap.put("details", customFields);
        delegate.info(Markers.appendEntries(logMap), message);
    }

    /**
     * Log at WARN level
     */
    public void warn(String message) {
        delegate.warn(Markers.appendEntries(createLogMap(message)), message);
    }

    /**
     * Log at WARN level with variable arguments
     */
    public void warn(String message, Object... args) {
        delegate.warn(Markers.appendEntries(createLogMap(message)), message, args);
    }

    /**
     * Log at WARN level with custom fields
     * Merges custom fields with default log fields under "details" key
     */
    public void warn(Map<String, Object> customFields, String message) {
        Map<String, Object> logMap = createLogMap(message);
        logMap.put("details", customFields);
        delegate.warn(Markers.appendEntries(logMap), message);
    }

    /**
     * Log at ERROR level
     */
    public void error(String message) {
        delegate.error(Markers.appendEntries(createLogMap(message)), message);
    }

    /**
     * Log at ERROR level with variable arguments
     */
    public void error(String message, Object... args) {
        delegate.error(Markers.appendEntries(createLogMap(message)), message, args);
    }

    /**
     * Log at ERROR level with exception (stack trace automatically included)
     */
    public void error(String message, Throwable ex) {
        Map<String, Object> logMap = createLogMap(message);
        logMap.put("stackTrace", getStackTrace(ex));
        delegate.error(Markers.appendEntries(logMap), message, ex);
    }

    /**
     * Log at ERROR level with custom fields
     * Merges custom fields with default log fields under "details" key
     */
    public void error(Map<String, Object> customFields, String message) {
        Map<String, Object> logMap = createLogMap(message);
        logMap.put("details", customFields);
        delegate.error(Markers.appendEntries(logMap), message);
    }

    /**
     * Log at DEBUG level
     */
    public void debug(String message) {
        if (delegate.isDebugEnabled()) {
            delegate.debug(Markers.appendEntries(createLogMap(message)), message);
        }
    }

    /**
     * Log at DEBUG level with format argument
     */
    public void debug(String message, Object arg) {
        if (delegate.isDebugEnabled()) {
            delegate.debug(Markers.appendEntries(createLogMap(message)), message, arg);
        }
    }

    /**
     * Create log map with common fields automatically added
     */
    Map<String, Object> createLogMap(String message) {
        Map<String, Object> log = new HashMap<>();
        log.put("type", LOG_TYPE);
        
        // Add timestamp in ISO-8601 format
        log.put("timestamp", OffsetDateTime.now(ZoneOffset.UTC).toString());
        
        // Add thread name
        log.put("thread", Thread.currentThread().getName());
        
        // Add method name (caller's method)
        log.put("method", resolveMethodName());
                
        log.put("class", className);
        
        return log;
    }

    /**
     * Package-private hook for loggers that need to emit with a prepared map.
     * Message is passed to SLF4J and added by logback encoder
     */
    void infoWithLogMap(Map<String, Object> logMap, String message) {
        delegate.info(Markers.appendEntries(logMap), message);
    }

    /**
     * Get method name of the logger caller
     * Examines the stack trace and returns the first method name that is not part of ApplicationLogger implementation
     */
    private String resolveMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // stackTrace[0]: Thread.currentThread()
        // stackTrace[1]: resolveMethodName
        // stackTrace[2]: createLogMap
        // stackTrace[3]: logger call method (info/warn/error/debug etc.)
        // stackTrace[4]: actual logger caller
        if (stackTrace.length > 4) {
            return stackTrace[4].getMethodName();
        }
        return "unknown";
    }

    /**
     * Get stack trace as string from Throwable
     */
    private String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
