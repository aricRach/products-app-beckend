package products.demo.product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartItem extends Product {
 // todo: maybe need only id and number of items without inherit from product
    private Integer numberOfItems;
}
