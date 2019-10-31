package xin.luowei.cloud.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 锁的参数
 * 
 * <p>使用在被@DistributedLock注解的方法参数上</p>
 * <p>当@DistributedLock#key()有值时,本方法失效</p>
 *  <p>本注解在一个方法中只能使用一次</p>
 * @author david
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LockKey {

    /**
     * 
     * 
     * 字段名称
     *
     * <p>获取被注解参数的属性值,当没有赋值时,直接调用被注解方法的toString()方法</p>
     */
    String value() default "";
}