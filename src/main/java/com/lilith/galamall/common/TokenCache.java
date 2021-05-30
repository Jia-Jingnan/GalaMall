package com.lilith.galamall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author:JiaJingnan
 * @Date: 上午11:04 2021/5/29
 */
@Slf4j
public class TokenCache {

    // LRU算法
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>(){
                // 默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法加载
                @Override
                public String load(String s) throws Exception {

                    return "null";
                }
            });

    public static void setKey(String key, String value){
        localCache.put(key,value);
    }

    public static String getValue(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        } catch (Exception e){
            log.error("localCache get value error", e);
        }
        return null;
    }



}