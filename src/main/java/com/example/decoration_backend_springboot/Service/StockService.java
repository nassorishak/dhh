package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Stock;
import com.example.decoration_backend_springboot.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Integer id) {
        return stockRepository.findById(id);
    }

    public Stream<Stock> getStockByProductId(Integer productId) {
        return stockRepository.findAll().stream();

    }

    public Stock createStock(Stock stock) {
        stock.calculateCurrentStock(); // Ensure current stock is calculated before saving
        return stockRepository.save(stock);
    }

    public Stock updateStock(Integer id, Stock stockDetails) {
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if (optionalStock.isPresent()) {
            Stock existingStock = optionalStock.get();

            // Update fields
            if (stockDetails.getProduct() != null) {
                existingStock.setProduct(stockDetails.getProduct());
            }
            if (stockDetails.getInStock() != null) {
                existingStock.setInStock(stockDetails.getInStock());
            }
            if (stockDetails.getOutStock() != null) {
                existingStock.setOutStock(stockDetails.getOutStock());
            }

            // Recalculate current stock
            existingStock.calculateCurrentStock();

            return stockRepository.save(existingStock);
        }
        return null;
    }

    public Stock addToStock(Integer id, Integer quantity) {
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setInStock((stock.getInStock() != null ? stock.getInStock() : 0) + quantity);
            stock.calculateCurrentStock();
            return stockRepository.save(stock);
        }
        return null;
    }

    public Stock removeFromStock(Integer id, Integer quantity) {
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setOutStock((stock.getOutStock() != null ? stock.getOutStock() : 0) + quantity);
            stock.calculateCurrentStock();
            return stockRepository.save(stock);
        }
        return null;
    }

    public void deleteStock(Integer id) {
        stockRepository.deleteById(id);
    }

    public Integer getTotalInventoryValue() {
        return stockRepository.findAll().stream()
                .mapToInt(stock -> {
                    // Since Product doesn't have getPrice(), we'll need to handle this differently
                    // For now, return 0 or implement your own logic based on what's available
                    if (stock.getProduct() != null && stock.getCurrentStock() != null) {
                        // You can add your custom pricing logic here
                        // For example, if you have purchase price in another entity
                        return stock.getCurrentStock(); // Just return quantity for now
                    }
                    return 0;
                })
                .sum();
    }

    // Alternative method if you want to calculate value based on some other logic
    public Integer getTotalInventoryQuantity() {
        return stockRepository.findAll().stream()
                .mapToInt(stock -> stock.getCurrentStock() != null ? stock.getCurrentStock() : 0)
                .sum();
    }
}
