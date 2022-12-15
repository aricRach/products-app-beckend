package products.demo.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.userOwner.email = ?1")
      List<Product> findProductsByOwner(String owner);

//    Optional<Product> findProductByOwner(String owner);
    Optional<Product> findProductById(Long id);
}
