package products.demo.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "purchases")
public class ProductPurchaseItem {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private Date date;
    private Integer numberOfItems;
    private Double finalPrice;
    private String image;
    private String name;

    public ProductPurchaseItem(Product baseProduct) {
        this.setName(baseProduct.getName());
        this.setFinalPrice(baseProduct.getFinalPrice());
        this.setImage(baseProduct.getImage());
    }
}
