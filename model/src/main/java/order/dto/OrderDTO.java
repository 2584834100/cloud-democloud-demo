package order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO implements Serializable {

    private Integer id;
    private Integer customerId;
    private Integer productId;
    private BigDecimal amount;
    private Date orderDate;
}
