package products.demo.product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartItem extends Product {
    private Integer numberOfItems;
}
