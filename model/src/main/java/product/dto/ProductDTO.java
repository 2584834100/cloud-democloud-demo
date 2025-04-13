package product.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDTO implements Serializable {
    private Integer Id;
    private String name;
    private BigDecimal price;
    private Integer stock;

}
