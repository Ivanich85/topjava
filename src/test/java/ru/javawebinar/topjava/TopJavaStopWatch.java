package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TopJavaStopWatch extends Stopwatch {
    private static final Logger LOG = LoggerFactory.getLogger(TopJavaStopWatch.class);
    private static final List<String> TESTS_RESULT = new ArrayList<>();

    private void logInfo(Description description, String status, long nanos) {
        Long spentMicros = TimeUnit.NANOSECONDS.toMicros(nanos);
        String formatString = String.format("Test \"%s\" %s, spent %d microseconds", description.getMethodName(), status, spentMicros);
        if (!status.equals("failed")) {
            LOG.info(formatString);
        } else {
            LOG.warn(formatString);
        }
        TESTS_RESULT.add(String.format("Test \"%s\" spent %d microseconds", description.getMethodName(), spentMicros));
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }

    public static void createSummaryTestReport() {
        LOG.info("======= Summary test report =======");
        TESTS_RESULT.forEach(s -> LOG.info(s));
        LOG.info("===================================");
    }
}