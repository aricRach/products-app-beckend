package products.demo.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import products.demo.user.User;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

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
    private String name;
    private String image;
    private Double price;
    @Transient
    private Double finalPrice;
    private Integer stock;
    private Double discountPercent;
    @JsonBackReference
    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User userOwner;

    public Product(String name, String image,
                   Double price, Integer stock, Double discountPercent, User userOwner) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.discountPercent = discountPercent;
        this.stock = stock;
        this.userOwner = userOwner;
    }

    public void updateProduct(Product product) {
        this.setName(product.name);
        this.setImage(product.image);
        this.setPrice(product.price);
        this.setStock(product.stock);
        this.setDiscountPercent(product.discountPercent);
    }

    String getOwner() {
        return this.userOwner.getEmail();
    }
}
