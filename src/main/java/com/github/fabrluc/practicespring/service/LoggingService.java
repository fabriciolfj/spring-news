package com.github.fabrluc.practicespring.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoggingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void log(final String message, final String url) {
        logger.info("logging request {} for URI: {}", message, url);
    }
}
