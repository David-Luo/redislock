package xin.luowei.cloud.lock.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * OrderController
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping(value="/order")
    public Order create(@RequestBody Order entity) {
        service.create(entity);
        return entity;
    }

    @PutMapping(value="/order")
    public Order rename(@RequestBody Order entity) {
        service.rename(entity);
        return entity;
    }

    @DeleteMapping(value="/order")
    public Order deal(@RequestBody Order entity) {
        service.deal(entity);
        return entity;
    }

}