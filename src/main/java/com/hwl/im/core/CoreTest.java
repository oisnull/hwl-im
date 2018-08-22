package com.hwl.im.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoreTest {
    static Logger log = LogManager.getLogger(CoreTest.class.getName());

    // ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF
    public static void main(String[] args) {
        log.debug("log4j out debug");
        log.info("log4j out info");
        log.warn("log4j out warn");
        log.error("log4j out error");
        log.fatal("log4j out fatal");
    }
}
