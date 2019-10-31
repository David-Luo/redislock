package xin.luowei.cloud.lock.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import xin.luowei.cloud.lock.annotation.DistributedLock;

public class DistributedLockFactory {
    private final RedisConnectionFactory redisConnectionFactory;
    private final Map<String, RedisLockRegistry> redisLockRegistryCache;
    private final Map<String, LockKeyGenerator> lockKeyGeneratorCache;
    private LockKeyGeneratorBuilder lockKeyGeneratorBuilder;

    public DistributedLockFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisLockRegistryCache = new ConcurrentHashMap<>();
        this.lockKeyGeneratorBuilder = new LockKeyGeneratorBuilder();
        this.lockKeyGeneratorCache = new ConcurrentHashMap<>();
    }

    public RedisLockRegistry getRegistry(Method method) {
        DistributedLock redisLock = method.getAnnotation(DistributedLock.class);
        String cacheKey = getCacheKey(method);
        long expireAfter = redisLock.timeUnit().toMillis(redisLock.expire());

        return redisLockRegistryCache.computeIfAbsent(cacheKey, key -> {
            return new RedisLockRegistry(redisConnectionFactory, LockKeyGeneratorBuilder.lock_head, expireAfter);
        });
    }

    public LockKeyGenerator getLockKeyGenerator(Method method) {
        String cacheKey = getCacheKey(method);
        return lockKeyGeneratorCache.computeIfAbsent(cacheKey, key -> {
            return lockKeyGeneratorBuilder.build(method);
        });
    }

    private String getCacheKey(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }
}