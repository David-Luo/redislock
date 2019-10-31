package xin.luowei.cloud.lock.interceptor;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import xin.luowei.cloud.lock.annotation.DistributedLock;

public class SpelLockKeyGenerator implements LockKeyGenerator {

    private Expression expression;

    public SpelLockKeyGenerator(DistributedLock redisLock, String spel) {

        String prefix = redisLock.prefix();
        if (StringUtils.isEmpty(prefix)) {
            throw new IllegalArgumentException("请设置锁前缀:DistributedLock#prefix()");
        }

        StringBuilder sb = new StringBuilder().append("'").append(prefix).append(redisLock.delimiter()).append("'");

        if (!StringUtils.isEmpty(spel)) {
            sb.append(" + ").append(spel);
        }

        ExpressionParser parser = new SpelExpressionParser();
        this.expression = parser.parseExpression(sb.toString());
    }

    @Override
    public String getLockKey(Object[] args) {
        if (args == null) {
            return null;
        }
        return (String) expression.getValue(args);
    }

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("''");
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
}