package xin.luowei.cloud.lock.mock;

import lombok.Data;

/**
 * Order
 */
@Data
public class Order {
    private int id;
    private String userId;
    private String orderName;
    private String status;
}