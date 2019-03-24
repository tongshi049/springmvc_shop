package com.example.o2o.service;

public interface CacheService {
    /**
     * According to keyPrefix to find all matched key-value pairs
     *
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
