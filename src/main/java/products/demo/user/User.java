package products.demo.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import products.demo.product.Product;

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

public class User implements Serializable {
    private String name;
    private String userName;
    @Id
    private String email;

    @JsonManagedReference
    @OneToMany(
        mappedBy = "userOwner",
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private List<Product> productList;

    public User(String name, String userName, String email) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.productList = new ArrayList<>();
    }

    public int addProduct(Product p) {
        this.productList.add(p);
        return this.productList.size();
    }

}
