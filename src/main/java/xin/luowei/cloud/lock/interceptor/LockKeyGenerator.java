package xin.luowei.cloud.lock.interceptor;


public interface LockKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     */
    String getLockKey(Object[] args);
}