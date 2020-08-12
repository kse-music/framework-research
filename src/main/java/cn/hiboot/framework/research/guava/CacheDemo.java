package cn.hiboot.framework.research.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.Test;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/5/18 23:32
 */
public class CacheDemo {

    /**
     * 　1. cacheLoader
     * 　2. callable callback
     * 　　通过这两种方法创建的cache，和通常用map来缓存的做法比，不同在于，这两种方法都实现了一种逻辑——从缓存中取key X的值，
     * 如果该值已经缓存过了，则返回缓存中的值，如果没有缓存过，可以通过某个方法来获取这个值。
     * 但不同的在于cacheLoader的定义比较宽泛，是针对整个cache定义的，可以认为是统一的根据key值load value的方法。
     * 而callable的方式较为灵活，允许你在get的时候指定。
     */

    @Test
    public void loadingCache() throws Exception {

        LoadingCache<String, String> cacheBuilder = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "hello " + key + "!";
                    }

                });

        System.out.println("jerry value:" + cacheBuilder.getUnchecked("jerry"));
        System.out.println("jerry value:" + cacheBuilder.get("jerry"));
        System.out.println("peida value:" + cacheBuilder.get("peida"));
        System.out.println("peida value:" + cacheBuilder.get("peida"));
        System.out.println("lisa value:" + cacheBuilder.get("lisa"));

        cacheBuilder.put("harry", "ssdded");
        System.out.println("harry value:" + cacheBuilder.getIfPresent("harry"));

        System.out.println("harry value:" + cacheBuilder.get(("harry"), () -> "ssdded2"));

        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("jerry", () ->  "hello " + "jerry" + "!");
        System.out.println("jerry value : " + resultVal);

        resultVal = cache.get("peida", () -> "hello " + "peida" + "!");
        System.out.println("peida value : " + resultVal);
    }

}
