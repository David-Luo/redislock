package xin.luowei.cloud.lock.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Method;

import org.junit.Test;

import xin.luowei.cloud.lock.annotation.DistributedLock;
import xin.luowei.cloud.lock.annotation.LockKey;

/**
 * LockKeyGeneratorBuilderTest
 */
@SuppressWarnings("all")
public class LockKeyGeneratorBuilderTest {

    @Test
    public void testDistributedLock() throws NoSuchMethodException, SecurityException {
        LockKeyGeneratorBuilder builder = new LockKeyGeneratorBuilder();
        LockDemo test = new LockDemo();

        Class target = test.getClass();
        Method method3 = target.getDeclaredMethod("method3", Object1.class);
        LockKeyGenerator generator = builder.build(method3);
        String key = generator.getLockKey(null);
        assertNull(key);

        key = generator.getLockKey(new Object[] { new Object1("a") });
        assertEquals("anull", key);

        generator = builder.build(method3);
        key = generator.getLockKey(new Object[] { new Object1("a", "b") });
        assertEquals("ab", key);
    }

    @Test
    public void testLockParam() throws NoSuchMethodException, SecurityException {
        LockKeyGeneratorBuilder builder = new LockKeyGeneratorBuilder();
        LockDemo test = new LockDemo();

        Class target = test.getClass();
        Method method1 = target.getDeclaredMethod("method1", String.class);
        LockKeyGenerator result = builder.build(method1);
        String key = result.getLockKey(null);
        assertNull(key);
        key = result.getLockKey(new String[] { "a" });
        assertEquals("a", key);

        Method method2 = target.getDeclaredMethod("method2", String.class, Object1.class);
        result = builder.build(method2);
        key = result.getLockKey(new Object[] { "a", new Object1("b") });
        assertEquals("b", key);
    }
}

class LockDemo {

    public LockDemo() {

    }

    @DistributedLock(prefix = "test")
    public void method1(@LockKey String id) {

    }

    @DistributedLock(prefix = "test")
    public void method2(String id, @LockKey("attr") Object1 p) {

    }

    @DistributedLock(prefix = "test", key = "#root[0].attr+#root[0].add")
    public void method3(Object1 p) {

    }
}

class Object1 {
    public Object1(String attr) {
        this.attr = attr;
    }

    public Object1(String attr, String add) {
        this.attr = attr;
        this.add = add;
    }

    private String attr;
    private String add;

    public String getAttr() {
        return attr;
    }

    public String getAdd() {
        return add;
    }
}