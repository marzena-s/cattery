package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @CacheEvict(value = CacheType.MENU, allEntries = true)
    public void invalidateMenu() {

    }

    @CacheEvict(value = CacheType.DICTIONARIES, allEntries = true)
    public void invalidateDictionaries() {

    }

}
