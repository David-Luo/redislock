package xin.luowei.cloud.lock.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.util.StringUtils;

import xin.luowei.cloud.lock.annotation.DistributedLock;
import xin.luowei.cloud.lock.annotation.LockKey;

/**
 * 生成LockKeyGenerator
 */
public class LockKeyGeneratorBuilder {
    public static final String lock_head = "lock";

    public LockKeyGeneratorBuilder() {
    }

    public LockKeyGenerator build(Method method) {

        DistributedLock redisLock = method.getAnnotation(DistributedLock.class);
        String id = redisLock.key();
        if (StringUtils.isEmpty(id)) {
            id = buildId(method);
        }
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("请设置id:使用DistributedLock#key()或者@LockParam()");
        }
        return new SpelLockKeyGenerator(redisLock,id);
    }

    private String buildId(Method method) {
        final Parameter[] parameters = method.getParameters();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            final LockKey lockParam = parameters[i].getAnnotation(LockKey.class);
            if (lockParam == null) {
                continue;
            }
            key.append("#root[").append(i).append("]?.");
            String value = lockParam.value();

            if (StringUtils.isEmpty(value)) {
                key.append("toString()");
            } else {
                key.append(value);
            }
            break;
        }
        return key.toString();
    }
}