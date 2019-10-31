package xin.luowei.cloud.lock.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import xin.luowei.cloud.lock.annotation.DistributedLock;
import xin.luowei.cloud.lock.annotation.LockKey;

@Service
public class OrderService {

    Map<Integer, Order> map = new HashMap<>();
    AtomicInteger id = new AtomicInteger(1);

    @DistributedLock(prefix = "order",timeUnit = TimeUnit.MINUTES)
    public Order create(@LockKey("userId") Order order) {
        order.setId(id.incrementAndGet());
        map.put(order.getId(), order);
        return order;
    }

    @DistributedLock(prefix = "order")
    public Order deal(@LockKey("id") Order order) {
        Order saved = map.get(order.getId());
        saved.setStatus("deal");
        return order;
    }

    @DistributedLock(prefix = "order")
    public Order rename(@LockKey("id") Order order) {
        Order saved = map.get(order.getId());
        saved.setOrderName(order.getOrderName());
        return order;
    }

}