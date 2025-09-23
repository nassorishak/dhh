package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Stock;
import com.example.decoration_backend_springboot.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/stocks")
public class StockAPI {

    private final StockService stockService;

    @Autowired
    public StockAPI(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/all-stocks")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Stock> getStockByProductId(@PathVariable Integer productId) {
        Stream<Stock> stock = stockService.getStockByProductId(productId);
        return (ResponseEntity<Stock>) stock.map(ResponseEntity::ok);

    }

    @GetMapping("/inventory-value")
    public ResponseEntity<Integer> getTotalInventoryValue() {
        Integer inventoryValue = stockService.getTotalInventoryValue();
        return ResponseEntity.ok(inventoryValue);
    }

    @PostMapping("/add-stocks")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id, @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(id, stock);
        if (updatedStock != null) {
            return ResponseEntity.ok(updatedStock);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/add")
    public ResponseEntity<Stock> addToStock(@PathVariable Integer id, @RequestParam Integer quantity) {
        Stock updatedStock = stockService.addToStock(id, quantity);
        if (updatedStock != null) {
            return ResponseEntity.ok(updatedStock);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/remove")
    public ResponseEntity<Stock> removeFromStock(@PathVariable Integer id, @RequestParam Integer quantity) {
        Stock updatedStock = stockService.removeFromStock(id, quantity);
        if (updatedStock != null) {
            return ResponseEntity.ok(updatedStock);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        Optional<Stock> stock = stockService.getStockById(id);
        if (stock.isPresent()) {
            stockService.deleteStock(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}