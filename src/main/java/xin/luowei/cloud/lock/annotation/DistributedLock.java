package xin.luowei.cloud.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * 
 * <p>
 * 在方法上加这个注解可以保证在分布式场景下同一资源顺序性访问. <br>
 * 
 * 锁key值为:prefix() + delimiter() + key()
 * </p>
 * <p>生成的Key：N:SO1008:500</p>
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {

    /**
     * redis 锁key的前缀<br>
     * 一个排它性资源使用同一个前缀,且其他资源不能使用这个前缀
     */
    String prefix()default "";

    /**
     * <p>Key的唯一键（默认 :）</p>
     * <p>内容为spel表达式，获取方法参数args的值</p>
     */
    
    String key() default "";

    /**
     * <p>Key的分隔符（默认 :）</p>
     */
    String delimiter() default ":";

    /**
     * 过期毫秒数,默认为5秒.
     * <p>
     * 过期时间越短越好,但不能短于当前接口完成服务所需要的最大时间.
     * 比如一个接口预期最大需要3S,那么过期时间不能短于3S
     * 
     *</p>
     * @return 轮询锁的时间
     */
    int expire() default 5;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}