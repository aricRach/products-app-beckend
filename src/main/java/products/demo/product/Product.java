package products.demo.product;

import javax.persistence.*;

@Entity
@Table
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
    @Column(nullable = false)
    private String owner;

    public Product() {}

    public Product(Long id, String name, String image,
                   Double price, Integer stock, Double discountPercent, String owner) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.discountPercent = discountPercent;
        this.owner = owner;
    }

    public Product(String name, String image,
                   Double price, Integer stock, Double discountPercent, String owner) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.discountPercent = discountPercent;
        this.owner = owner;
    }

    public void updateProduct(Product product) {
        this.setName(product.name);
//        this.setImage(product.image);
        this.setPrice(product.price);
        this.setStock(product.stock);
        this.setDiscountPercent(product.discountPercent);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public Double getFinalPrice() { return price - (price * (discountPercent/100)); }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", discountPercent=" + discountPercent +
                ", owner=" + owner +
                '}';
    }
}
