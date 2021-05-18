package com.hc.ms.generated.web.rest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CacheResource {

    private final static Logger log = LoggerFactory.getLogger(CacheResource.class);

    @Autowired
    private CacheManager cacheManager;

    /**
     * Testing cache.
     * 
     * @param id
     * @return
     */
    @GetMapping("/setCache/{id}")
    @Cacheable(cacheNames = "byId")
    public String testCache(@PathVariable String id) {
        Date date = new Date();
        log.debug(
                "\n*******************************\n\n Testing cache, id : {} , data create time : {} \n \n*******************************\n",
                id, date);

        return "{'Success':" + date + "}";
    }

    /**
     * Clear Cache
     * 
     * @param name
     * @param id
     * @return
     */
    @GetMapping("/cleanCache/{name}/{id}")
    public ResponseEntity<Void> cleanCache(@PathVariable String name, @PathVariable(required = false) String id) {
        if (id != null) {
            this.cacheManager.getCache(name).evict(id);
            log.debug(
                    "\n*******************************\n\n Delete cache, name : {} , id : {} \n \n*******************************\n",
                    name, id);
        } else {
            log.debug(
                    "\n*******************************\n\n Delete cache, name : {}\n \n*******************************\n",
                    name);
            this.cacheManager.getCache(name).clear();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Get Cache
     * 
     * @param name
     * @return
     */
    @GetMapping("/getCache/{name}")
    public ResponseEntity<Cache> getCache(@PathVariable String name) {
        return ResponseEntity.ok().body(this.cacheManager.getCache(name));
    }

    /**
     * Get All Cache
     * 
     * @return
     */
    @GetMapping("/getAllCache")
    public ResponseEntity<List<Cache>> getAllCache() {
        return ResponseEntity.ok(this.cacheManager.getCacheNames().stream()
                .map(item -> this.cacheManager.getCache(item)).collect(Collectors.toList()));
    }

    /**
     * Clear All Cache
     * 
     * @return
     */
    @DeleteMapping("/clearAll")
    public ResponseEntity<Void> deleteAll() {
        this.cacheManager.getCacheNames().stream().forEach(item -> this.cacheManager.getCache(item).clear());
        return ResponseEntity.ok().build();
    }

}
