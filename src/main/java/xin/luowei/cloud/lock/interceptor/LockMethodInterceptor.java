package xin.luowei.cloud.lock.interceptor;


import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import xin.luowei.cloud.lock.exception.DuplicateSubmmitException;

/**
 * redis 方案
 *
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    private final DistributedLockFactory redisLockRegistryFactory;

    @Autowired
    public LockMethodInterceptor( RedisConnectionFactory redisConnectionFactory) {
        this.redisLockRegistryFactory = new DistributedLockFactory(redisConnectionFactory);
    }

    @Around("execution(public * *(..)) && @annotation(xin.luowei.cloud.lock.annotation.DistributedLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable{
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        LockKeyGenerator keyGenerator = redisLockRegistryFactory.getLockKeyGenerator(method);
        String lockKey = keyGenerator.getLockKey(pjp.getArgs());

        if(lockKey == null){
            return pjp.proceed();
        }
        
        RedisLockRegistry redisLockRegistry = redisLockRegistryFactory.getRegistry(method);
        // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
        Lock lock = redisLockRegistry.obtain(lockKey);
        if (!lock.tryLock()) {
            throw new DuplicateSubmmitException("重复提交" + lockKey);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            lock.unlock();
        }
    }
}