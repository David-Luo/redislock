package xin.luowei.cloud.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import xin.luowei.cloud.lock.interceptor.LockMethodInterceptor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LockMethodInterceptor.class)
public @interface EnableDistributedLock {
    
}