package dev.cmartin.learn.camelapp.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DummyScheduler {
    private final Logger logger = LoggerFactory.getLogger(DummyScheduler.class);

    @Scheduled(initialDelay = 10_000, fixedRate = 30_000)
    public void perform() {
        logger.debug("TODO: dummy scheduler");
    }
}
