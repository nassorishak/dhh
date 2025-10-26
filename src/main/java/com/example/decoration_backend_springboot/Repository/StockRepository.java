//package com.example.decoration_backend_springboot.Repository;
//
//import com.example.decoration_backend_springboot.Model.Stock;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface StockRepository extends JpaRepository<Stock, Integer> {
//    List<Stock> findByProductProductId(Integer productId);
//
//
//}
package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByProductProductId(Integer productId);

    // ✅ Fetch stocks with shelf information using JOIN FETCH
    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.shelf")
    List<Stock> findAllWithShelf();

    // ✅ Find stock by ID with shelf information
    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.shelf WHERE s.stockId = :stockId")
    Optional<Stock> findByIdWithShelf(Integer stockId);

    // ✅ Find stocks by product ID with shelf information
    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.shelf WHERE s.product.productId = :productId")
    List<Stock> findByProductProductIdWithShelf(Integer productId);

    // ✅ Optional: Find stocks by shelf ID
    @Query("SELECT s FROM Stock s LEFT JOIN FETCH s.shelf WHERE s.shelf.shelfId = :shelfId")
    List<Stock> findByShelfShelfId(Integer shelfId);

    List<Stock> findByShelfIsNull();
}