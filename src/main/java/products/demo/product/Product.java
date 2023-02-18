package products.demo.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import products.demo.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Product implements Serializable {

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
    @Column(length = 1024)
    private String image;
    private Double price;
    @Transient
    private Double finalPrice;
    private Integer stock;
    private Double discountPercent;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
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

    public Double getFinalPrice() {
        return discountPercent != 0 ? price  - (price * discountPercent/100) : price ;
    }

    public void setFinalPrice(Double price) {
        this.finalPrice = price;
    }

    String getOwner() {
        return this.userOwner.getEmail();
    }

    public void setImage(String imageUrl){
        this.image = imageUrl;
    };
}
