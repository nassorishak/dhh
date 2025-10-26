//package com.example.decoration_backend_springboot.Service;
//
//import com.example.decoration_backend_springboot.Model.Purchase;
//import com.example.decoration_backend_springboot.Model.Sale;
//import com.example.decoration_backend_springboot.Model.Stock;
//import com.example.decoration_backend_springboot.Model.Product;
//import com.example.decoration_backend_springboot.Repository.StockRepository;
//import com.example.decoration_backend_springboot.Repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//@Service
//@Transactional
//public class StockService {
//
//    private final StockRepository stockRepository;
//    private final ProductRepository productRepository;
//
//    @Autowired
//    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
//        this.stockRepository = stockRepository;
//        this.productRepository = productRepository;
//    }
//
//    public List<Stock> getAllStocks() {
//        return stockRepository.findAll();
//    }
//
//    public Optional<Stock> getStockById(Integer id) {
//        return stockRepository.findById(id);
//    }
//
//    public List<Stock> getStockByProductId(Integer productId) {
//        return stockRepository.findByProductProductId(productId);
//    }
//
//    public Stock createStock(Stock stock) {
//        // Check if stock already exists for this product
//        if (stock.getProduct() != null && stock.getProduct().getProductId() != null) {
//            List<Stock> existingStocks = stockRepository.findByProductProductId(stock.getProduct().getProductId());
//            if (!existingStocks.isEmpty()) {
//                throw new IllegalArgumentException("Stock already exists for product ID: " + stock.getProduct().getProductId());
//            }
//        }
//
//        // Ensure current stock is calculated before saving
//        if (stock.getInStock() != null && stock.getOutStock() != null) {
//            stock.setCurrentStock(stock.getInStock() - stock.getOutStock());
//        } else if (stock.getInStock() != null) {
//            stock.setCurrentStock(stock.getInStock());
//        } else {
//            stock.setCurrentStock(0);
//        }
//        return stockRepository.save(stock);
//    }
//
//    public Stock updateStock(Integer id, Stock stockDetails) {
//        Optional<Stock> optionalStock = stockRepository.findById(id);
//
//        if (optionalStock.isPresent()) {
//            Stock existingStock = optionalStock.get();
//
//            // Update fields
//            if (stockDetails.getProduct() != null) {
//                existingStock.setProduct(stockDetails.getProduct());
//            }
//            if (stockDetails.getInStock() != null) {
//                existingStock.setInStock(stockDetails.getInStock());
//            }
//            if (stockDetails.getOutStock() != null) {
//                existingStock.setOutStock(stockDetails.getOutStock());
//            }
//
//            // Recalculate current stock
//            existingStock.setCurrentStock(
//                    (existingStock.getInStock() != null ? existingStock.getInStock() : 0) -
//                            (existingStock.getOutStock() != null ? existingStock.getOutStock() : 0)
//            );
//
//            return stockRepository.save(existingStock);
//        }
//        return null;
//    }
//
//    public Stock addToStock(Integer id, Integer quantity) {
//        Optional<Stock> optionalStock = stockRepository.findById(id);
//
//        if (optionalStock.isPresent()) {
//            Stock stock = optionalStock.get();
//            stock.setInStock((stock.getInStock() != null ? stock.getInStock() : 0) + quantity);
//            stock.setCurrentStock(stock.getCurrentStock() + quantity);
//            return stockRepository.save(stock);
//        }
//        return null;
//    }
//
//    public Stock removeFromStock(Integer id, Integer quantity) {
//        Optional<Stock> optionalStock = stockRepository.findById(id);
//
//        if (optionalStock.isPresent()) {
//            Stock stock = optionalStock.get();
//
//            // Check if sufficient stock exists
//            if (stock.getCurrentStock() < quantity) {
//                throw new IllegalArgumentException("Insufficient stock. Available: " + stock.getCurrentStock() + ", Requested: " + quantity);
//            }
//
//            stock.setOutStock((stock.getOutStock() != null ? stock.getOutStock() : 0) + quantity);
//            stock.setCurrentStock(stock.getCurrentStock() - quantity);
//            return stockRepository.save(stock);
//        }
//        return null;
//    }
//
//    public void deleteStock(Integer id) {
//        stockRepository.deleteById(id);
//    }
//
//    public Integer getTotalInventoryValue() {
//        return stockRepository.findAll().stream()
//                .mapToInt(stock -> {
//                    if (stock.getProduct() != null && stock.getCurrentStock() != null) {
//                        return stock.getCurrentStock();
//                    }
//                    return 0;
//                })
//                .sum();
//    }
//
//    public Integer getTotalInventoryQuantity() {
//        return stockRepository.findAll().stream()
//                .mapToInt(stock -> stock.getCurrentStock() != null ? stock.getCurrentStock() : 0)
//                .sum();
//    }
//
//    @Transactional
//    public void updateStockOnPurchase(Purchase purchase) {
//        if (purchase == null || purchase.getProduct() == null) {
//            throw new IllegalArgumentException("Purchase or product cannot be null");
//        }
//
//        Integer productId = purchase.getProduct().getProductId();
//
//        // Find or create stock record
//        Stock stock = getOrCreateStockForProduct(productId);
//
//        // Update stock with purchase quantity
//        stock.setInStock((stock.getInStock() != null ? stock.getInStock() : 0) + purchase.getQuantity());
//        stock.setCurrentStock(stock.getCurrentStock() + purchase.getQuantity());
//        stockRepository.save(stock);
//    }
//
//    @Transactional
//    public void updateStockOnSale(Sale sale) {
//        if (sale == null || sale.getProduct() == null) {
//            throw new IllegalArgumentException("Sale or product cannot be null");
//        }
//
//        Integer productId = sale.getProduct().getProductId();
//
//        // Find or create stock record
//        Stock stock = getOrCreateStockForProduct(productId);
//
//        // Check if sufficient stock exists
//        if (stock.getCurrentStock() < sale.getQuantity()) {
//            throw new IllegalArgumentException("Insufficient stock. Available: " + stock.getCurrentStock() + ", Requested: " + sale.getQuantity());
//        }
//
//        // Update stock
//        stock.setOutStock((stock.getOutStock() != null ? stock.getOutStock() : 0) + sale.getQuantity());
//        stock.setCurrentStock(stock.getCurrentStock() - sale.getQuantity());
//        stockRepository.save(stock);
//    }
//
//    // Helper method to get or create stock for a product (handles duplicates)
//    private Stock getOrCreateStockForProduct(Integer productId) {
//        List<Stock> existingStocks = stockRepository.findByProductProductId(productId);
//
//        if (!existingStocks.isEmpty()) {
//            // If multiple stocks exist, log warning and use first one
//            if (existingStocks.size() > 1) {
//                System.err.println("WARNING: Multiple stock records found for product ID: " + productId +
//                        ". Using the first one. Consider cleaning up duplicates.");
//            }
//            return existingStocks.get(0);
//        } else {
//            // Create new stock record if it doesn't exist
//            Product product = productRepository.findById(productId)
//                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
//
//            Stock newStock = new Stock();
//            newStock.setProduct(product);
//            newStock.setInStock(0);
//            newStock.setOutStock(0);
//            newStock.setCurrentStock(0);
//            return stockRepository.save(newStock);
//        }
//    }
//
//    // Helper method to create initial stock for all products
//    @Transactional
//    public void initializeStockForAllProducts() {
//        List<Product> products = productRepository.findAll();
//        for (Product product : products) {
//            List<Stock> existingStocks = stockRepository.findByProductProductId(product.getProductId());
//            if (existingStocks.isEmpty()) {
//                Stock stock = new Stock();
//                stock.setProduct(product);
//                stock.setInStock(0);
//                stock.setOutStock(0);
//                stock.setCurrentStock(0);
//                stockRepository.save(stock);
//            }
//        }
//    }
//
//    // Helper method to initialize stock for a specific product
//    @Transactional
//    public Stock initializeStockForProduct(Integer productId) {
//        return getOrCreateStockForProduct(productId);
//    }
//
//    // Method to get available stock for a product
//    public Integer getAvailableStock(Integer productId) {
//        List<Stock> stocks = stockRepository.findByProductProductId(productId);
//        if (stocks.isEmpty()) {
//            return 0;
//        }
//        // Sum up current stock from all records (in case of duplicates)
//        return stocks.stream()
//                .mapToInt(stock -> stock.getCurrentStock() != null ? stock.getCurrentStock() : 0)
//                .sum();
//    }
//
//    // Method to check if product has sufficient stock
//    public boolean hasSufficientStock(Integer productId, Integer requiredQuantity) {
//        Integer availableStock = getAvailableStock(productId);
//        return availableStock >= requiredQuantity;
//    }
//
//    // Method to clean up duplicate stock records
//    @Transactional
//    public void cleanupDuplicateStocks() {
//        List<Product> products = productRepository.findAll();
//        for (Product product : products) {
//            List<Stock> stocks = stockRepository.findByProductProductId(product.getProductId());
//            if (stocks.size() > 1) {
//                System.out.println("Cleaning up " + stocks.size() + " duplicate stock records for product: " + product.getProductName());
//                // Keep the first stock and delete the rest
//                Stock mainStock = stocks.get(0);
//
//                // Merge quantities from duplicates into main stock
//                int totalInStock = mainStock.getInStock() != null ? mainStock.getInStock() : 0;
//                int totalOutStock = mainStock.getOutStock() != null ? mainStock.getOutStock() : 0;
//
//                for (int i = 1; i < stocks.size(); i++) {
//                    Stock duplicate = stocks.get(i);
//                    totalInStock += duplicate.getInStock() != null ? duplicate.getInStock() : 0;
//                    totalOutStock += duplicate.getOutStock() != null ? duplicate.getOutStock() : 0;
//
//                    // Delete the duplicate
//                    stockRepository.delete(duplicate);
//                }
//
//                // Update main stock with merged quantities
//                mainStock.setInStock(totalInStock);
//                mainStock.setOutStock(totalOutStock);
//                mainStock.setCurrentStock(totalInStock - totalOutStock);
//                stockRepository.save(mainStock);
//            }
//        }
//    }
//    public Optional<Stock> getStockByProductId(Integer productId) {
//        List<Stock> stocks = stockRepository.findByProductProductId(productId);
//        return stocks.isEmpty() ? Optional.empty() : Optional.of(stocks.get(0));
//    }
//
//    // Optional: Add method to get stock with shelf by product ID
//    public Optional<Stock> getStockByProductIdWithShelf(Integer productId) {
//        List<Stock> stocks = stockRepository.findByProductProductId(productId);
//        if (!stocks.isEmpty()) {
//            // If you need to ensure shelf is loaded, you might need a custom query
//            Stock stock = stocks.get(0);
//            // Initialize shelf if it's LAZY loaded
//            if (stock.getShelf() != null) {
//                stock.getShelf().getShelfName(); // This triggers lazy loading
//            }
//            return Optional.of(stock);
//        }
//        return Optional.empty();
//    }
//
//}

package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Purchase;
import com.example.decoration_backend_springboot.Model.Sale;
import com.example.decoration_backend_springboot.Model.Stock;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Repository.StockRepository;
import com.example.decoration_backend_springboot.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Autowired
    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    // ✅ Get all stocks with shelf information
    public List<Stock> getAllStocksWithShelf() {
        return stockRepository.findAllWithShelf();
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Integer id) {
        return stockRepository.findById(id);
    }

    // ✅ FIXED: Return Optional<Stock> instead of List<Stock>
    public Optional<Stock> getStockByProductId(Integer productId) {
        List<Stock> stocks = stockRepository.findByProductProductId(productId);
        return stocks.isEmpty() ? Optional.empty() : Optional.of(stocks.get(0));
    }

    // ✅ Get stock by product ID with shelf information
    public Optional<Stock> getStockByProductIdWithShelf(Integer productId) {
        List<Stock> stocks = stockRepository.findByProductProductIdWithShelf(productId);
        return stocks.isEmpty() ? Optional.empty() : Optional.of(stocks.get(0));
    }

    // ✅ Get stock by ID with shelf information
    public Optional<Stock> getStockByIdWithShelf(Integer id) {
        return stockRepository.findByIdWithShelf(id);
    }

//    public Stock createStock(Stock stock) {
//        // Check if stock already exists for this product
//        if (stock.getProduct() != null && stock.getProduct().getProductId() != null) {
//            List<Stock> existingStocks = stockRepository.findByProductProductId(stock.getProduct().getProductId());
//            if (!existingStocks.isEmpty()) {
//                throw new IllegalArgumentException("Stock already exists for product ID: " + stock.getProduct().getProductId());
//            }
//        }
//
//        // Ensure current stock is calculated before saving
//        if (stock.getInStock() != null && stock.getOutStock() != null) {
//            stock.setCurrentStock(stock.getInStock() - stock.getOutStock());
//        } else if (stock.getInStock() != null) {
//            stock.setCurrentStock(stock.getInStock());
//        } else {
//            stock.setCurrentStock(0);
//        }
//        return stockRepository.save(stock);
//    }


    public Stock createStock(Stock stock) {
        // Check if stock already exists for this product
        if (stock.getProduct() != null && stock.getProduct().getProductId() != null) {
            List<Stock> existingStocks = stockRepository.findByProductProductId(stock.getProduct().getProductId());
            if (!existingStocks.isEmpty()) {
                throw new IllegalArgumentException("Stock already exists for product ID: " + stock.getProduct().getProductId());
            }
        }

        // Don't assign default shelf - keep whatever shelf was provided (or null)
        if (stock.getShelf() == null) {
            System.out.println("ℹ️ Creating stock with NULL shelf for product: " +
                    (stock.getProduct() != null ? stock.getProduct().getProductName() : "Unknown"));
        }

        // Ensure current stock is calculated before saving
        if (stock.getInStock() != null && stock.getOutStock() != null) {
            stock.setCurrentStock(stock.getInStock() - stock.getOutStock());
        } else if (stock.getInStock() != null) {
            stock.setCurrentStock(stock.getInStock());
        } else {
            stock.setCurrentStock(0);
        }
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
            if (stockDetails.getShelf() != null) {
                existingStock.setShelf(stockDetails.getShelf());
            }

            // Recalculate current stock
            existingStock.setCurrentStock(
                    (existingStock.getInStock() != null ? existingStock.getInStock() : 0) -
                            (existingStock.getOutStock() != null ? existingStock.getOutStock() : 0)
            );

            return stockRepository.save(existingStock);
        }
        return null;
    }

    public Stock addToStock(Integer id, Integer quantity) {
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setInStock((stock.getInStock() != null ? stock.getInStock() : 0) + quantity);
            stock.setCurrentStock(stock.getCurrentStock() + quantity);
            return stockRepository.save(stock);
        }
        return null;
    }

    public Stock removeFromStock(Integer id, Integer quantity) {
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();

            // Check if sufficient stock exists
            if (stock.getCurrentStock() < quantity) {
                throw new IllegalArgumentException("Insufficient stock. Available: " + stock.getCurrentStock() + ", Requested: " + quantity);
            }

            stock.setOutStock((stock.getOutStock() != null ? stock.getOutStock() : 0) + quantity);
            stock.setCurrentStock(stock.getCurrentStock() - quantity);
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
                    if (stock.getProduct() != null && stock.getCurrentStock() != null) {
                        return stock.getCurrentStock();
                    }
                    return 0;
                })
                .sum();
    }

    public Integer getTotalInventoryQuantity() {
        return stockRepository.findAll().stream()
                .mapToInt(stock -> stock.getCurrentStock() != null ? stock.getCurrentStock() : 0)
                .sum();
    }

    @Transactional
    public void updateStockOnPurchase(Purchase purchase) {
        if (purchase == null || purchase.getProduct() == null) {
            throw new IllegalArgumentException("Purchase or product cannot be null");
        }

        Integer productId = purchase.getProduct().getProductId();

        // Find or create stock record
        Stock stock = getOrCreateStockForProduct(productId);

        // Update stock with purchase quantity
        stock.setInStock((stock.getInStock() != null ? stock.getInStock() : 0) + purchase.getQuantity());
        stock.setCurrentStock(stock.getCurrentStock() + purchase.getQuantity());
        stockRepository.save(stock);
    }

    @Transactional
    public void updateStockOnSale(Sale sale) {
        if (sale == null || sale.getProduct() == null) {
            throw new IllegalArgumentException("Sale or product cannot be null");
        }

        Integer productId = sale.getProduct().getProductId();

        // Find or create stock record
        Stock stock = getOrCreateStockForProduct(productId);

        // Check if sufficient stock exists
        if (stock.getCurrentStock() < sale.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + stock.getCurrentStock() + ", Requested: " + sale.getQuantity());
        }

        // Update stock
        stock.setOutStock((stock.getOutStock() != null ? stock.getOutStock() : 0) + sale.getQuantity());
        stock.setCurrentStock(stock.getCurrentStock() - sale.getQuantity());
        stockRepository.save(stock);
    }

    // Helper method to get or create stock for a product (handles duplicates)
    private Stock getOrCreateStockForProduct(Integer productId) {
        List<Stock> existingStocks = stockRepository.findByProductProductId(productId);

        if (!existingStocks.isEmpty()) {
            // If multiple stocks exist, log warning and use first one
            if (existingStocks.size() > 1) {
                System.err.println("WARNING: Multiple stock records found for product ID: " + productId +
                        ". Using the first one. Consider cleaning up duplicates.");
            }
            return existingStocks.get(0);
        } else {
            // Create new stock record if it doesn't exist
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

            Stock newStock = new Stock();
            newStock.setProduct(product);
            newStock.setInStock(0);
            newStock.setOutStock(0);
            newStock.setCurrentStock(0);
            return stockRepository.save(newStock);
        }
    }

    // Helper method to create initial stock for all products
    @Transactional
    public void initializeStockForAllProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            List<Stock> existingStocks = stockRepository.findByProductProductId(product.getProductId());
            if (existingStocks.isEmpty()) {
                Stock stock = new Stock();
                stock.setProduct(product);
                stock.setInStock(0);
                stock.setOutStock(0);
                stock.setCurrentStock(0);
                stockRepository.save(stock);
            }
        }
    }

    // Helper method to initialize stock for a specific product
    @Transactional
    public Stock initializeStockForProduct(Integer productId) {
        return getOrCreateStockForProduct(productId);
    }

    // Method to get available stock for a product
    public Integer getAvailableStock(Integer productId) {
        List<Stock> stocks = stockRepository.findByProductProductId(productId);
        if (stocks.isEmpty()) {
            return 0;
        }
        // Sum up current stock from all records (in case of duplicates)
        return stocks.stream()
                .mapToInt(stock -> stock.getCurrentStock() != null ? stock.getCurrentStock() : 0)
                .sum();
    }

    // Method to check if product has sufficient stock
    public boolean hasSufficientStock(Integer productId, Integer requiredQuantity) {
        Integer availableStock = getAvailableStock(productId);
        return availableStock >= requiredQuantity;
    }

    // Method to clean up duplicate stock records
    @Transactional
    public void cleanupDuplicateStocks() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            List<Stock> stocks = stockRepository.findByProductProductId(product.getProductId());
            if (stocks.size() > 1) {
                System.out.println("Cleaning up " + stocks.size() + " duplicate stock records for product: " + product.getProductName());
                // Keep the first stock and delete the rest
                Stock mainStock = stocks.get(0);

                // Merge quantities from duplicates into main stock
                int totalInStock = mainStock.getInStock() != null ? mainStock.getInStock() : 0;
                int totalOutStock = mainStock.getOutStock() != null ? mainStock.getOutStock() : 0;

                for (int i = 1; i < stocks.size(); i++) {
                    Stock duplicate = stocks.get(i);
                    totalInStock += duplicate.getInStock() != null ? duplicate.getInStock() : 0;
                    totalOutStock += duplicate.getOutStock() != null ? duplicate.getOutStock() : 0;

                    // Delete the duplicate
                    stockRepository.delete(duplicate);
                }

                // Update main stock with merged quantities
                mainStock.setInStock(totalInStock);
                mainStock.setOutStock(totalOutStock);
                mainStock.setCurrentStock(totalInStock - totalOutStock);
                stockRepository.save(mainStock);
            }
        }
    }
}