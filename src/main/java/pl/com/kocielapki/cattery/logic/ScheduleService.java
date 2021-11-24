package pl.com.kocielapki.cattery.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.com.kocielapki.cattery.auth.AuthenticatedUser;

@Service
public class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private CacheService cacheService;
    private AuthenticatedUser authenticatedUser;

    public ScheduleService(CacheService cacheService, AuthenticatedUser authenticatedUser) {
        this.cacheService = cacheService;
        this.authenticatedUser = authenticatedUser;
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void scheduleFixedDelayTask() {
        cacheService.invalidateDictionaries();
        cacheService.invalidateMenu();
        log.debug("Caches invalidated");
    }

}
