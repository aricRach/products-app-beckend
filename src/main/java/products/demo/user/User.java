package products.demo.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import products.demo.product.Product;
import products.demo.product.ProductPurchaseItem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name="app_users")
@ToString
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "email")
@JsonIdentityReference(alwaysAsId = true) // get just the userOwner email instead of the object of the user when do findAll
public class User implements Serializable {
    private String userName;
    @Id
    private String email;

    @OneToMany(
        mappedBy = "userOwner",
        cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private List<Product> productList;

    @OneToMany(cascade= {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ProductPurchaseItem> orders;


    public User(String name, String userName, String email) {
        this.userName = userName;
        this.email = email;
        this.productList = new ArrayList<>();
    }

    public boolean addProduct(Product p) {
        return this.productList.add(p);
    }
    public boolean addProductToOrdersHistory(ProductPurchaseItem p) {
        return this.orders.add(p);
    }

}
