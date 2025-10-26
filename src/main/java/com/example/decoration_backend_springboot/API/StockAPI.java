//////package com.example.decoration_backend_springboot.API;
//////
//////import com.example.decoration_backend_springboot.Model.Sale;
//////import com.example.decoration_backend_springboot.Model.Stock;
//////import com.example.decoration_backend_springboot.Repository.SaleRepository;
//////import com.example.decoration_backend_springboot.Repository.StockRepository;
//////import com.example.decoration_backend_springboot.Service.SaleService;
//////import com.example.decoration_backend_springboot.Service.StockService;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.http.HttpStatus;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.web.bind.annotation.*;
//////
//////import java.util.HashMap;
//////import java.util.List;
//////import java.util.Map;
//////import java.util.Optional;
//////import java.util.stream.Collectors;
//////import java.util.stream.Stream;
//////@CrossOrigin("http://localhost:3000")
//////@RestController
//////@RequestMapping("/api/stocks")
//////public class StockAPI {
//////
//////    private final StockService stockService;
//////
//////    private  final SaleService saleService;
//////
//////
//////    @Autowired
//////    private  final  StockRepository stockRepository;
//////
//////    private  final SaleRepository saleRepository;
//////
//////    public StockAPI(StockService stockService, SaleService saleService, StockRepository stockRepository, SaleRepository saleRepository) {
//////        this.stockService = stockService;
//////        this.saleService = saleService;
//////        this.stockRepository = stockRepository;
//////        this.saleRepository = saleRepository;
//////    }
//////
//////    //    @GetMapping("/all-stocks")
////////    public ResponseEntity<List<Stock>> getAllStocks() {
////////        List<Stock> stocks = stockService.getAllStocks();
////////        return ResponseEntity.ok(stocks);
////////    }
//////@GetMapping("/all-stocks")
//////public List<Map<String, Object>> getAllStocks() {
//////    List<Stock> stocks = stockRepository.findAll();
//////
//////    return stocks.stream().map(stock -> {
//////        int soldQuantity = saleRepository.sumQuantitySoldByStockId(stock.getStockId());
//////        int currentStock = stock.getInStock() - soldQuantity;
//////        String status = currentStock == 0 ? "Sold Out"
//////                : soldQuantity > 0 ? "Partially Sold"
//////                : "In Stock";
//////
//////        Map<String, Object> stockMap = new HashMap<>();
//////        stockMap.put("stockId", stock.getStockId());
//////        stockMap.put("product", stock.getProduct());
//////        stockMap.put("inStock", stock.getInStock());
//////        stockMap.put("outStock", soldQuantity);  // sold units
//////        stockMap.put("currentStock", currentStock);
//////        stockMap.put("status", status);
//////        stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
//////        stockMap.put("sellingPrice", stock.getSellingPrice());
//////        return stockMap;
//////    }).collect(Collectors.toList());
//////}
//////
//////
//////    @GetMapping("/{id}")
//////    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
//////        Optional<Stock> stock = stockService.getStockById(id);
//////        return stock.map(ResponseEntity::ok)
//////                .orElse(ResponseEntity.notFound().build());
//////    }
//////
//////    @GetMapping("/product/{productId}")
//////    public ResponseEntity<Stock> getStockByProductId(@PathVariable Integer productId) {
//////        Stream<Stock> stock = stockService.getStockByProductId(productId).stream();
//////        return (ResponseEntity<Stock>) stock.map(ResponseEntity::ok);
//////
//////    }
//////
//////    @GetMapping("/inventory-value")
//////    public ResponseEntity<Integer> getTotalInventoryValue() {
//////        Integer inventoryValue = stockService.getTotalInventoryValue();
//////        return ResponseEntity.ok(inventoryValue);
//////    }
//////
////////    @PostMapping("/add-stocks")
////////    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
////////        Stock createdStock = stockService.createStock(stock);
////////        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
////////    }
//////@PostMapping("/add-stocks")
//////public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
//////    // Recalculate current stock
//////    int currentStock = stock.getInStock() - stock.getOutStock();
//////    stock.setCurrentStock(currentStock);
//////
//////    // Update status based on stock
//////    if (currentStock == 0) {
//////        stock.setStatus("Sold Out");
//////    } else if (stock.getOutStock() > 0) {
//////        stock.setStatus("Partially Sold");
//////    } else {
//////        stock.setStatus("In Stock");
//////    }
//////
//////    // Save stock to database
//////    Stock savedStock = stockRepository.save(stock);
//////    return ResponseEntity.ok(savedStock);
//////}
//////
//////
//////    @PutMapping("/stocks/{id}")
//////    public ResponseEntity<Stock> updateStock(@PathVariable Integer id, @RequestBody Stock stock) {
//////        Stock updatedStock = stockService.updateStock(id, stock);
//////        if (updatedStock != null) {
//////            return ResponseEntity.ok(updatedStock);
//////        }
//////        return ResponseEntity.notFound().build();
//////    }
//////
//////    @PatchMapping("/{id}/add")
//////    public ResponseEntity<Stock> addToStock(@PathVariable Integer id, @RequestParam Integer quantity) {
//////        Stock updatedStock = stockService.addToStock(id, quantity);
//////        if (updatedStock != null) {
//////            return ResponseEntity.ok(updatedStock);
//////        }
//////        return ResponseEntity.notFound().build();
//////    }
//////
//////    @PatchMapping("/{id}/remove")
//////    public ResponseEntity<Stock> removeFromStock(@PathVariable Integer id, @RequestParam Integer quantity) {
//////        Stock updatedStock = stockService.removeFromStock(id, quantity);
//////        if (updatedStock != null) {
//////            return ResponseEntity.ok(updatedStock);
//////        }
//////        return ResponseEntity.notFound().build();
//////    }
//////
//////    @DeleteMapping("/{id}")
//////    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
//////        Optional<Stock> stock = stockService.getStockById(id);
//////        if (stock.isPresent()) {
//////            stockService.deleteStock(id);
//////            return ResponseEntity.noContent().build();
//////        }
//////        return ResponseEntity.notFound().build();
//////    }
//////
//////    @GetMapping("/stocks/monthly-report")
//////    public ResponseEntity<?> getMonthlyStockReport(@RequestParam int month, @RequestParam int year) {
//////        try {
//////            // Fetch all sales for the month
//////            List<Sale> monthlySales = saleService.getSalesByMonth(month, year);
//////
//////            // Fetch all stocks
//////            List<Stock> allStocks = stockService.getAllStocks();
//////
//////            Map<String, Object> report = new HashMap<>();
//////            report.put("monthlySales", monthlySales);
//////            report.put("currentStocks", allStocks);
//////
//////            return ResponseEntity.ok(report);
//////        } catch (Exception e) {
//////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//////                    .body(Map.of("error", "Server error", "message", e.getMessage()));
//////        }
//////    }
//////
//////}
////
////package com.example.decoration_backend_springboot.API;
////
////import com.example.decoration_backend_springboot.Model.Sale;
////import com.example.decoration_backend_springboot.Model.Stock;
////import com.example.decoration_backend_springboot.Repository.SaleRepository;
////import com.example.decoration_backend_springboot.Repository.StockRepository;
////import com.example.decoration_backend_springboot.Service.SaleService;
////import com.example.decoration_backend_springboot.Service.StockService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.*;
////import java.util.stream.Collectors;
////import java.util.stream.Stream;
////
////@CrossOrigin("http://localhost:3000")
////@RestController
////@RequestMapping("/api/stocks")
////public class StockAPI {
////
////    private final StockService stockService;
////    private final SaleService saleService;
////    private final StockRepository stockRepository;
////    private final SaleRepository saleRepository;
////
////    public StockAPI(StockService stockService, SaleService saleService,
////                    StockRepository stockRepository, SaleRepository saleRepository) {
////        this.stockService = stockService;
////        this.saleService = saleService;
////        this.stockRepository = stockRepository;
////        this.saleRepository = saleRepository;
////    }
////
////    // Fixed: Return proper stock data with all required fields
//////    @GetMapping("/all-stocks")
//////    public ResponseEntity<List<Map<String, Object>>> getAllStocks() {
//////        try {
//////            List<Stock> stocks = stockRepository.findAll();
//////
//////            List<Map<String, Object>> stockList = stocks.stream().map(stock -> {
//////                try {
//////                    // Calculate sold quantity from sales (most accurate)
//////                    Integer soldQuantityFromSales = saleRepository.sumQuantitySoldByStockId(stock.getStockId());
//////                    if (soldQuantityFromSales == null) soldQuantityFromSales = 0;
//////
//////                    // Use the actual outStock from stock entity as fallback
//////                    Integer actualOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
//////
//////                    // Choose the most accurate sold quantity (prefer sales data)
//////                    Integer effectiveSoldQuantity = Math.max(soldQuantityFromSales, actualOutStock);
//////
//////                    // Calculate current stock based on sales data (most accurate)
//////                    int currentStock = Math.max(0, stock.getInStock() - effectiveSoldQuantity);
//////
//////                    // Determine status based on accurate current stock
//////                    String status;
//////                    if (currentStock == 0) {
//////                        status = "Sold Out";
//////                    } else if (effectiveSoldQuantity > 0) {
//////                        status = "Partially Sold";
//////                    } else {
//////                        status = "In Stock";
//////                    }
//////
//////                    // Calculate profit using the most accurate sold quantity
//////                    Double profit = 0.0;
//////                    if (stock.getSellingPrice() != null && stock.getLatestPurchasePrice() != null) {
//////                        profit = (stock.getSellingPrice() - stock.getLatestPurchasePrice()) * effectiveSoldQuantity;
//////                    }
//////
//////                    // Check for data consistency
//////                    boolean dataInconsistent = !actualOutStock.equals(soldQuantityFromSales);
//////
//////                    Map<String, Object> stockMap = new HashMap<>();
//////                    stockMap.put("stockId", stock.getStockId());
//////                    stockMap.put("product", stock.getProduct());
//////                    stockMap.put("inStock", stock.getInStock());
//////                    stockMap.put("outStock", effectiveSoldQuantity); // Use the most accurate value
//////                    stockMap.put("soldFromSales", soldQuantityFromSales);
//////                    stockMap.put("stockOutStock", actualOutStock); // Keep original for reference
//////                    stockMap.put("currentStock", currentStock);
//////                    stockMap.put("status", status);
//////                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
//////                    stockMap.put("sellingPrice", stock.getSellingPrice());
//////                    stockMap.put("profit", profit);
//////                    stockMap.put("dataInconsistent", dataInconsistent); // Flag for debugging
//////
//////                    return stockMap;
//////                } catch (Exception e) {
//////                    // Log individual stock error but don't fail the entire request
//////                    System.err.println("Error processing stock ID " + stock.getStockId() + ": " + e.getMessage());
//////
//////                    // Return basic stock data without calculations
//////                    Map<String, Object> stockMap = new HashMap<>();
//////                    stockMap.put("stockId", stock.getStockId());
//////                    stockMap.put("product", stock.getProduct());
//////                    stockMap.put("inStock", stock.getInStock());
//////                    stockMap.put("outStock", stock.getOutStock() != null ? stock.getOutStock() : 0);
//////                    stockMap.put("currentStock", stock.getCurrentStock() != null ? stock.getCurrentStock() : 0);
//////                    stockMap.put("status", stock.getStatus() != null ? stock.getStatus() : "Unknown");
//////                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
//////                    stockMap.put("sellingPrice", stock.getSellingPrice());
//////                    stockMap.put("profit", 0.0);
//////                    stockMap.put("error", "Calculation failed");
//////
//////                    return stockMap;
//////                }
//////            }).collect(Collectors.toList());
//////
//////            return ResponseEntity.ok(stockList);
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//////                    .body(Collections.emptyList());
//////        }
//////    }
////
////
////    @GetMapping("/{id}")
////    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
////        Optional<Stock> stock = stockService.getStockById(id);
////        return stock.map(ResponseEntity::ok)
////                .orElse(ResponseEntity.notFound().build());
////    }
////
////    // Fixed: Proper endpoint for getting stock by product ID
////    @GetMapping("/product/{productId}")
////    public ResponseEntity<Stock> getStockByProductId(@PathVariable Integer productId) {
////        Stream<Stock> stock = (Stream<Stock>) stockService.getStockByProductId(productId);
////        return (ResponseEntity<Stock>) stock.map(ResponseEntity::ok);
////
////    }
////
////    @GetMapping("/inventory-value")
////    public ResponseEntity<Integer> getTotalInventoryValue() {
////        Integer inventoryValue = stockService.getTotalInventoryValue();
////        return ResponseEntity.ok(inventoryValue);
////    }
////
////    // Fixed: Proper stock creation with validation
////    @PostMapping("/add-stocks")
////    public ResponseEntity<?> addStock(@RequestBody Stock stock) {
////        try {
////            // Validate input
////            if (stock.getProduct() == null || stock.getProduct().getProductId() == null) {
////                return ResponseEntity.badRequest().body(Map.of("error", "Product is required"));
////            }
////
////            if (stock.getInStock() < 0) {
////                return ResponseEntity.badRequest().body(Map.of("error", "In stock quantity cannot be negative"));
////            }
////
////            // Set initial values
////            stock.setOutStock(0); // Initially no sales
////            stock.setCurrentStock(stock.getInStock());
////
////            // Set status based on initial stock
////            if (stock.getInStock() == 0) {
////                stock.setStatus("Sold Out");
////            } else {
////                stock.setStatus("In Stock");
////            }
////
////            // Ensure prices are set (can be 0)
////            if (stock.getLatestPurchasePrice() == null) {
////                stock.setLatestPurchasePrice(0.0);
////            }
////            if (stock.getSellingPrice() == null) {
////                stock.setSellingPrice(0.0);
////            }
////
////            Stock savedStock = stockRepository.save(stock);
////            return ResponseEntity.ok(savedStock);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to add stock: " + e.getMessage()));
////        }
////    }
////
////    // Fixed: Proper update endpoint
////    // Fixed: Proper update endpoint with profit consideration
////    @PutMapping("/{id}")
////    public ResponseEntity<?> updateStock(@PathVariable Integer id, @RequestBody Stock stockDetails) {
////        try {
////            Optional<Stock> optionalStock = stockService.getStockById(id);
////            if (optionalStock.isEmpty()) {
////                return ResponseEntity.notFound().build();
////            }
////
////            Stock existingStock = optionalStock.get();
////
////            // Update fields
////            if (stockDetails.getInStock() != null) {
////                existingStock.setInStock(stockDetails.getInStock());
////            }
////            if (stockDetails.getOutStock() != null) {
////                existingStock.setOutStock(stockDetails.getOutStock());
////            }
////            if (stockDetails.getLatestPurchasePrice() != null) {
////                existingStock.setLatestPurchasePrice(stockDetails.getLatestPurchasePrice());
////            }
////            if (stockDetails.getSellingPrice() != null) {
////                existingStock.setSellingPrice(stockDetails.getSellingPrice());
////            }
////
////            // Let the entity calculate current stock and status automatically
////            // via @PreUpdate annotation
////
////            Stock updatedStock = stockRepository.save(existingStock);
////
////            // Return updated stock with profit information
////            Map<String, Object> response = new HashMap<>();
////            response.put("stockId", updatedStock.getStockId());
////            response.put("product", updatedStock.getProduct());
////            response.put("inStock", updatedStock.getInStock());
////            response.put("outStock", updatedStock.getOutStock());
////            response.put("currentStock", updatedStock.getCurrentStock());
////            response.put("status", updatedStock.getStatus());
////            response.put("latestPurchasePrice", updatedStock.getLatestPurchasePrice());
////            response.put("sellingPrice", updatedStock.getSellingPrice());
////            response.put("profit", updatedStock.getProfit()); // Include profit in response
////
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to update stock: " + e.getMessage()));
////        }
////    }
////
////    @PatchMapping("/{id}/add")
////    public ResponseEntity<Stock> addToStock(@PathVariable Integer id, @RequestParam Integer quantity) {
////        Stock updatedStock = stockService.addToStock(id, quantity);
////        if (updatedStock != null) {
////            return ResponseEntity.ok(updatedStock);
////        }
////        return ResponseEntity.notFound().build();
////    }
////
////    @PatchMapping("/{id}/remove")
////    public ResponseEntity<Stock> removeFromStock(@PathVariable Integer id, @RequestParam Integer quantity) {
////        Stock updatedStock = stockService.removeFromStock(id, quantity);
////        if (updatedStock != null) {
////            return ResponseEntity.ok(updatedStock);
////        }
////        return ResponseEntity.notFound().build();
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
////        Optional<Stock> stock = stockService.getStockById(id);
////        if (stock.isPresent()) {
////            stockService.deleteStock(id);
////            return ResponseEntity.noContent().build();
////        }
////        return ResponseEntity.notFound().build();
////    }
////
////    @GetMapping("/monthly-report")
////    public ResponseEntity<?> getMonthlyStockReport(@RequestParam int month, @RequestParam int year) {
////        try {
////            List<Sale> monthlySales = saleService.getSalesByMonth(month, year);
////            List<Stock> allStocks = stockService.getAllStocks();
////
////            Map<String, Object> report = new HashMap<>();
////            report.put("monthlySales", monthlySales);
////            report.put("currentStocks", allStocks);
////
////            return ResponseEntity.ok(report);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Server error", "message", e.getMessage()));
////        }
////    }
////}
//
//
////package com.example.decoration_backend_springboot.API;
////
////import com.example.decoration_backend_springboot.Model.Sale;
////import com.example.decoration_backend_springboot.Model.Stock;
////import com.example.decoration_backend_springboot.Repository.SaleRepository;
////import com.example.decoration_backend_springboot.Repository.StockRepository;
////import com.example.decoration_backend_springboot.Service.SaleService;
////import com.example.decoration_backend_springboot.Service.StockService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.*;
////import java.util.stream.Collectors;
////
////@CrossOrigin("http://localhost:3000")
////@RestController
////@RequestMapping("/api/stocks")
////public class StockAPI {
////
////    private final StockService stockService;
////    private final SaleService saleService;
////    private final StockRepository stockRepository;
////    private final SaleRepository saleRepository;
////
////    public StockAPI(StockService stockService, SaleService saleService,
////                    StockRepository stockRepository, SaleRepository saleRepository) {
////        this.stockService = stockService;
////        this.saleService = saleService;
////        this.stockRepository = stockRepository;
////        this.saleRepository = saleRepository;
////    }
////
//////     ✅ FIXED: Return stock data with shelf information
////    @GetMapping("/all-stocks")
////    public ResponseEntity<List<Map<String, Object>>> getAllStocks() {
////        try {
////            List<Stock> stocks = stockService.getAllStocksWithShelf();
////
////            List<Map<String, Object>> stockList = stocks.stream().map(stock -> {
////                try {
////                    // Calculate sold quantity from sales
////                    Integer soldQuantityFromSales = saleRepository.sumQuantitySoldByStockId(stock.getStockId());
////                    if (soldQuantityFromSales == null) soldQuantityFromSales = 0;
////
////                    Integer actualOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
////                    Integer effectiveSoldQuantity = Math.max(soldQuantityFromSales, actualOutStock);
////                    int currentStock = Math.max(0, stock.getInStock() - effectiveSoldQuantity);
////
////                    // Determine status
////                    String status;
////                    if (currentStock == 0) {
////                        status = "Sold Out";
////                    } else if (effectiveSoldQuantity > 0) {
////                        status = "Partially Sold";
////                    } else {
////                        status = "In Stock";
////                    }
////
////                    // Calculate profit
////                    Double profit = 0.0;
////                    if (stock.getSellingPrice() != null && stock.getLatestPurchasePrice() != null) {
////                        profit = (stock.getSellingPrice() - stock.getLatestPurchasePrice()) * effectiveSoldQuantity;
////                    }
////
////                    // Calculate margin
////                    Double margin = 0.0;
////                    if (stock.getLatestPurchasePrice() != null && stock.getLatestPurchasePrice() > 0) {
////                        margin = ((stock.getSellingPrice() - stock.getLatestPurchasePrice()) / stock.getLatestPurchasePrice()) * 100;
////                    }
////
////                    Map<String, Object> stockMap = new HashMap<>();
////                    stockMap.put("stockId", stock.getStockId());
////                    stockMap.put("product", stock.getProduct());
////                    stockMap.put("inStock", stock.getInStock());
////                    stockMap.put("outStock", effectiveSoldQuantity);
////                    stockMap.put("currentStock", currentStock);
////                    stockMap.put("status", status);
////                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
////                    stockMap.put("sellingPrice", stock.getSellingPrice());
////                    stockMap.put("profit", profit);
////                    stockMap.put("margin", margin);
////
////                    // ✅ Add shelf data to response
////                    if (stock.getShelf() != null) {
////                        Map<String, Object> shelfMap = new HashMap<>();
////                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
////                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
////                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
////                        stockMap.put("shelf", shelfMap);
////                    } else {
////                        stockMap.put("shelf", null);
////                    }
////
////                    return stockMap;
////                } catch (Exception e) {
////                    System.err.println("Error processing stock ID " + stock.getStockId() + ": " + e.getMessage());
////
////                    // Fallback with basic data including shelf
////                    Map<String, Object> stockMap = new HashMap<>();
////                    stockMap.put("stockId", stock.getStockId());
////                    stockMap.put("product", stock.getProduct());
////                    stockMap.put("inStock", stock.getInStock());
////                    stockMap.put("outStock", stock.getOutStock() != null ? stock.getOutStock() : 0);
////                    stockMap.put("currentStock", stock.getCurrentStock() != null ? stock.getCurrentStock() : 0);
////                    stockMap.put("status", stock.getStatus() != null ? stock.getStatus() : "Unknown");
////                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
////                    stockMap.put("sellingPrice", stock.getSellingPrice());
////                    stockMap.put("profit", 0.0);
////                    stockMap.put("margin", 0.0);
////
////                    // Always include shelf data even in error case
////                    if (stock.getShelf() != null) {
////                        Map<String, Object> shelfMap = new HashMap<>();
////                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
////                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
////                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
////                        stockMap.put("shelf", shelfMap);
////                    } else {
////                        stockMap.put("shelf", null);
////                    }
////
////                    return stockMap;
////                }
////            }).collect(Collectors.toList());
////
////            return ResponseEntity.ok(stockList);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Collections.emptyList());
////        }
////    }
////
////
////
////
////    // ✅ FIXED: Get stock by ID with shelf information
////    @GetMapping("/{id}")
////    public ResponseEntity<?> getStockById(@PathVariable Integer id) {
////        try {
////            Optional<Stock> stock = stockService.getStockByIdWithShelf(id);
////            if (stock.isPresent()) {
////                // Return stock with shelf information
////                Map<String, Object> response = new HashMap<>();
////                response.put("stockId", stock.get().getStockId());
////                response.put("product", stock.get().getProduct());
////                response.put("inStock", stock.get().getInStock());
////                response.put("outStock", stock.get().getOutStock());
////                response.put("currentStock", stock.get().getCurrentStock());
////                response.put("status", stock.get().getStatus());
////                response.put("latestPurchasePrice", stock.get().getLatestPurchasePrice());
////                response.put("sellingPrice", stock.get().getSellingPrice());
////
////                // Include shelf data
////                if (stock.get().getShelf() != null) {
////                    Map<String, Object> shelfMap = new HashMap<>();
////                    shelfMap.put("shelfId", stock.get().getShelf().getShelfId());
////                    shelfMap.put("shelfName", stock.get().getShelf().getShelfName());
////                    shelfMap.put("locationDescription", stock.get().getShelf().getLocationDescription());
////                    response.put("shelf", shelfMap);
////                } else {
////                    response.put("shelf", null);
////                }
////
////                return ResponseEntity.ok(response);
////            }
////            return ResponseEntity.notFound().build();
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to fetch stock: " + e.getMessage()));
////        }
////    }
////
////    // ✅ FIXED: Get stock by product ID
////    @GetMapping("/product/{productId}")
////    public ResponseEntity<?> getStockByProductId(@PathVariable Integer productId) {
////        try {
////            Optional<Stock> stock = stockService.getStockByProductIdWithShelf(productId);
////            if (stock.isPresent()) {
////                // Return stock with shelf information
////                Map<String, Object> response = new HashMap<>();
////                response.put("stockId", stock.get().getStockId());
////                response.put("product", stock.get().getProduct());
////                response.put("inStock", stock.get().getInStock());
////                response.put("outStock", stock.get().getOutStock());
////                response.put("currentStock", stock.get().getCurrentStock());
////                response.put("status", stock.get().getStatus());
////                response.put("latestPurchasePrice", stock.get().getLatestPurchasePrice());
////                response.put("sellingPrice", stock.get().getSellingPrice());
////
////                // Include shelf data
////                if (stock.get().getShelf() != null) {
////                    Map<String, Object> shelfMap = new HashMap<>();
////                    shelfMap.put("shelfId", stock.get().getShelf().getShelfId());
////                    shelfMap.put("shelfName", stock.get().getShelf().getShelfName());
////                    shelfMap.put("locationDescription", stock.get().getShelf().getLocationDescription());
////                    response.put("shelf", shelfMap);
////                } else {
////                    response.put("shelf", null);
////                }
////
////                return ResponseEntity.ok(response);
////            }
////            return ResponseEntity.notFound().build();
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to fetch stock: " + e.getMessage()));
////        }
////    }
////
////    @GetMapping("/inventory-value")
////    public ResponseEntity<Integer> getTotalInventoryValue() {
////        Integer inventoryValue = stockService.getTotalInventoryValue();
////        return ResponseEntity.ok(inventoryValue);
////    }
////
////    @PostMapping("/add-stocks")
////    public ResponseEntity<?> addStock(@RequestBody Stock stock) {
////        try {
////            if (stock.getProduct() == null || stock.getProduct().getProductId() == null) {
////                return ResponseEntity.badRequest().body(Map.of("error", "Product is required"));
////            }
////
////            if (stock.getInStock() < 0) {
////                return ResponseEntity.badRequest().body(Map.of("error", "In stock quantity cannot be negative"));
////            }
////
////            stock.setOutStock(0);
////            stock.setCurrentStock(stock.getInStock());
////
////            if (stock.getInStock() == 0) {
////                stock.setStatus("Sold Out");
////            } else {
////                stock.setStatus("In Stock");
////            }
////
////            if (stock.getLatestPurchasePrice() == null) {
////                stock.setLatestPurchasePrice(0.0);
////            }
////            if (stock.getSellingPrice() == null) {
////                stock.setSellingPrice(0.0);
////            }
////
////            Stock savedStock = stockRepository.save(stock);
////            return ResponseEntity.ok(savedStock);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to add stock: " + e.getMessage()));
////        }
////    }
////
////    @PutMapping("/update/{id}")
////    public ResponseEntity<?> updateStock(@PathVariable Integer id, @RequestBody Stock stockDetails) {
////        try {
////            Optional<Stock> optionalStock = stockService.getStockById(id);
////            if (optionalStock.isEmpty()) {
////                return ResponseEntity.notFound().build();
////            }
////
////            Stock existingStock = optionalStock.get();
////
////            // Update fields including shelf
////            if (stockDetails.getInStock() != null) {
////                existingStock.setInStock(stockDetails.getInStock());
////            }
////            if (stockDetails.getOutStock() != null) {
////                existingStock.setOutStock(stockDetails.getOutStock());
////            }
////            if (stockDetails.getLatestPurchasePrice() != null) {
////                existingStock.setLatestPurchasePrice(stockDetails.getLatestPurchasePrice());
////            }
////            if (stockDetails.getSellingPrice() != null) {
////                existingStock.setSellingPrice(stockDetails.getSellingPrice());
////            }
////            if (stockDetails.getShelf() != null) {
////                existingStock.setShelf(stockDetails.getShelf());
////            }
////
////            Stock updatedStock = stockRepository.save(existingStock);
////
////            Map<String, Object> response = new HashMap<>();
////            response.put("stockId", updatedStock.getStockId());
////            response.put("product", updatedStock.getProduct());
////            response.put("inStock", updatedStock.getInStock());
////            response.put("outStock", updatedStock.getOutStock());
////            response.put("currentStock", updatedStock.getCurrentStock());
////            response.put("status", updatedStock.getStatus());
////            response.put("latestPurchasePrice", updatedStock.getLatestPurchasePrice());
////            response.put("sellingPrice", updatedStock.getSellingPrice());
////            response.put("profit", updatedStock.getProfit());
////
////            // Include shelf in response
////            if (updatedStock.getShelf() != null) {
////                Map<String, Object> shelfMap = new HashMap<>();
////                shelfMap.put("shelfId", updatedStock.getShelf().getShelfId());
////                shelfMap.put("shelfName", updatedStock.getShelf().getShelfName());
////                shelfMap.put("locationDescription", updatedStock.getShelf().getLocationDescription());
////                response.put("shelf", shelfMap);
////            } else {
////                response.put("shelf", null);
////            }
////
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to update stock: " + e.getMessage()));
////        }
////    }
////
////    @PatchMapping("/{id}/add")
////    public ResponseEntity<Stock> addToStock(@PathVariable Integer id, @RequestParam Integer quantity) {
////        Stock updatedStock = stockService.addToStock(id, quantity);
////        if (updatedStock != null) {
////            return ResponseEntity.ok(updatedStock);
////        }
////        return ResponseEntity.notFound().build();
////    }
////
////    @PatchMapping("/{id}/remove")
////    public ResponseEntity<Stock> removeFromStock(@PathVariable Integer id, @RequestParam Integer quantity) {
////        Stock updatedStock = stockService.removeFromStock(id, quantity);
////        if (updatedStock != null) {
////            return ResponseEntity.ok(updatedStock);
////        }
////        return ResponseEntity.notFound().build();
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
////        Optional<Stock> stock = stockService.getStockById(id);
////        if (stock.isPresent()) {
////            stockService.deleteStock(id);
////            return ResponseEntity.noContent().build();
////        }
////        return ResponseEntity.notFound().build();
////    }
////
////    @GetMapping("/monthly-report")
////    public ResponseEntity<?> getMonthlyStockReport(@RequestParam int month, @RequestParam int year) {
////        try {
////            List<Sale> monthlySales = saleService.getSalesByMonth(month, year);
////            List<Stock> allStocks = stockService.getAllStocks();
////
////            Map<String, Object> report = new HashMap<>();
////            report.put("monthlySales", monthlySales);
////            report.put("currentStocks", allStocks);
////
////            return ResponseEntity.ok(report);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Server error", "message", e.getMessage()));
////        }
////    }
////}
//
//package com.example.decoration_backend_springboot.API;
//
//import com.example.decoration_backend_springboot.Model.Sale;
//import com.example.decoration_backend_springboot.Model.Shelf;
//import com.example.decoration_backend_springboot.Model.Stock;
//import com.example.decoration_backend_springboot.Repository.SaleRepository;
//import com.example.decoration_backend_springboot.Repository.StockRepository;
//import com.example.decoration_backend_springboot.Service.SaleService;
//import com.example.decoration_backend_springboot.Service.ShelfService;
//import com.example.decoration_backend_springboot.Service.StockService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@CrossOrigin("http://localhost:3000")
//@RestController
//@RequestMapping("/api/stocks")
//public class StockAPI {
//
//    private final StockService stockService;
//    private final SaleService saleService;
//    private final ShelfService shelfService;
//    private final StockRepository stockRepository;
//    private final SaleRepository saleRepository;
//
//    public StockAPI(StockService stockService, SaleService saleService,
//                    ShelfService shelfService, StockRepository stockRepository,
//                    SaleRepository saleRepository) {
//        this.stockService = stockService;
//        this.saleService = saleService;
//        this.shelfService = shelfService;
//        this.stockRepository = stockRepository;
//        this.saleRepository = saleRepository;
//    }
//
//    // ✅ FIXED: Return stock data with shelf information
////    @GetMapping("/all-stocks")
////    public ResponseEntity<List<Map<String, Object>>> getAllStocks() {
////        try {
////            List<Stock> stocks = stockService.getAllStocksWithShelf();
////
////            List<Map<String, Object>> stockList = stocks.stream().map(stock -> {
////                try {
////                    // Calculate sold quantity from sales
////                    Integer soldQuantityFromSales = saleRepository.sumQuantitySoldByStockId(stock.getStockId());
////                    if (soldQuantityFromSales == null) soldQuantityFromSales = 0;
////
////                    Integer actualOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
////                    Integer effectiveSoldQuantity = Math.max(soldQuantityFromSales, actualOutStock);
////                    int currentStock = Math.max(0, stock.getInStock() - effectiveSoldQuantity);
////
////                    // Determine status
////                    String status;
////                    if (currentStock == 0) {
////                        status = "Sold Out";
////                    } else if (effectiveSoldQuantity > 0) {
////                        status = "Partially Sold";
////                    } else {
////                        status = "In Stock";
////                    }
////
////                    // Calculate profit
////                    Double profit = 0.0;
////                    if (stock.getSellingPrice() != null && stock.getLatestPurchasePrice() != null) {
////                        profit = (stock.getSellingPrice() - stock.getLatestPurchasePrice()) * effectiveSoldQuantity;
////                    }
////
////                    // Calculate margin
////                    Double margin = 0.0;
////                    if (stock.getLatestPurchasePrice() != null && stock.getLatestPurchasePrice() > 0) {
////                        margin = ((stock.getSellingPrice() - stock.getLatestPurchasePrice()) / stock.getLatestPurchasePrice()) * 100;
////                    }
////
////                    Map<String, Object> stockMap = new HashMap<>();
////                    stockMap.put("stockId", stock.getStockId());
////                    stockMap.put("product", stock.getProduct());
////                    stockMap.put("inStock", stock.getInStock());
////                    stockMap.put("outStock", effectiveSoldQuantity);
////                    stockMap.put("currentStock", currentStock);
////                    stockMap.put("status", status);
////                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
////                    stockMap.put("sellingPrice", stock.getSellingPrice());
////                    stockMap.put("profit", profit);
////                    stockMap.put("margin", margin);
////
////                    // ✅ Add shelf data to response
////                    if (stock.getShelf() != null) {
////                        Map<String, Object> shelfMap = new HashMap<>();
////                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
////                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
////                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
////                        stockMap.put("shelf", shelfMap);
////                    } else {
////                        stockMap.put("shelf", null);
////                    }
////
////                    return stockMap;
////                } catch (Exception e) {
////                    System.err.println("Error processing stock ID " + stock.getStockId() + ": " + e.getMessage());
////
////                    // Fallback with basic data including shelf
////                    Map<String, Object> stockMap = new HashMap<>();
////                    stockMap.put("stockId", stock.getStockId());
////                    stockMap.put("product", stock.getProduct());
////                    stockMap.put("inStock", stock.getInStock());
////                    stockMap.put("outStock", stock.getOutStock() != null ? stock.getOutStock() : 0);
////                    stockMap.put("currentStock", stock.getCurrentStock() != null ? stock.getCurrentStock() : 0);
////                    stockMap.put("status", stock.getStatus() != null ? stock.getStatus() : "Unknown");
////                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
////                    stockMap.put("sellingPrice", stock.getSellingPrice());
////                    stockMap.put("profit", 0.0);
////                    stockMap.put("margin", 0.0);
////
////                    // Always include shelf data even in error case
////                    if (stock.getShelf() != null) {
////                        Map<String, Object> shelfMap = new HashMap<>();
////                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
////                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
////                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
////                        stockMap.put("shelf", shelfMap);
////                    } else {
////                        stockMap.put("shelf", null);
////                    }
////
////                    return stockMap;
////                }
////            }).collect(Collectors.toList());
////
////            return ResponseEntity.ok(stockList);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Collections.emptyList());
////        }
////    }
//
//    // ✅ UPDATED: getAllStocks method to use consistent shelfId format
//    @GetMapping("/all-stocks")
//    public ResponseEntity<List<Map<String, Object>>> getAllStocks() {
//        try {
//            List<Stock> stocks = stockService.getAllStocksWithShelf();
//
//            List<Map<String, Object>> stockList = stocks.stream().map(stock -> {
//                try {
//                    // Calculate sold quantity from sales
//                    Integer soldQuantityFromSales = saleRepository.sumQuantitySoldByStockId(stock.getStockId());
//                    if (soldQuantityFromSales == null) soldQuantityFromSales = 0;
//
//                    Integer actualOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
//                    Integer effectiveSoldQuantity = Math.max(soldQuantityFromSales, actualOutStock);
//                    int currentStock = Math.max(0, stock.getInStock() - effectiveSoldQuantity);
//
//                    // Determine status
//                    String status;
//                    if (currentStock == 0) {
//                        status = "Sold Out";
//                    } else if (effectiveSoldQuantity > 0) {
//                        status = "Partially Sold";
//                    } else {
//                        status = "In Stock";
//                    }
//
//                    // Calculate profit
//                    Double profit = 0.0;
//                    if (stock.getSellingPrice() != null && stock.getLatestPurchasePrice() != null) {
//                        profit = (stock.getSellingPrice() - stock.getLatestPurchasePrice()) * effectiveSoldQuantity;
//                    }
//
//                    // Calculate margin
//                    Double margin = 0.0;
//                    if (stock.getLatestPurchasePrice() != null && stock.getLatestPurchasePrice() > 0) {
//                        margin = ((stock.getSellingPrice() - stock.getLatestPurchasePrice()) / stock.getLatestPurchasePrice()) * 100;
//                    }
//
//                    Map<String, Object> stockMap = new HashMap<>();
//                    stockMap.put("stockId", stock.getStockId());
//                    stockMap.put("product", stock.getProduct());
//                    stockMap.put("inStock", stock.getInStock());
//                    stockMap.put("outStock", effectiveSoldQuantity);
//                    stockMap.put("currentStock", currentStock);
//                    stockMap.put("status", status);
//                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
//                    stockMap.put("sellingPrice", stock.getSellingPrice());
//                    stockMap.put("profit", profit);
//                    stockMap.put("margin", margin);
//
//                    // ✅ UPDATED: Add shelf data with consistent shelfId field
//                    if (stock.getShelf() != null) {
//                        Map<String, Object> shelfMap = new HashMap<>();
//                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
//                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
//                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
//                        stockMap.put("shelf", shelfMap);
//                    } else {
//                        stockMap.put("shelf", null);
//                    }
//
//                    return stockMap;
//                } catch (Exception e) {
//                    System.err.println("Error processing stock ID " + stock.getStockId() + ": " + e.getMessage());
//
//                    // Fallback with basic data including shelf
//                    Map<String, Object> stockMap = new HashMap<>();
//                    stockMap.put("stockId", stock.getStockId());
//                    stockMap.put("product", stock.getProduct());
//                    stockMap.put("inStock", stock.getInStock());
//                    stockMap.put("outStock", stock.getOutStock() != null ? stock.getOutStock() : 0);
//                    stockMap.put("currentStock", stock.getCurrentStock() != null ? stock.getCurrentStock() : 0);
//                    stockMap.put("status", stock.getStatus() != null ? stock.getStatus() : "Unknown");
//                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
//                    stockMap.put("sellingPrice", stock.getSellingPrice());
//                    stockMap.put("profit", 0.0);
//                    stockMap.put("margin", 0.0);
//
//                    // Always include shelf data even in error case with consistent format
//                    if (stock.getShelf() != null) {
//                        Map<String, Object> shelfMap = new HashMap<>();
//                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
//                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
//                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
//                        stockMap.put("shelf", shelfMap);
//                    } else {
//                        stockMap.put("shelf", null);
//                    }
//
//                    return stockMap;
//                }
//            }).collect(Collectors.toList());
//
//            return ResponseEntity.ok(stockList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.emptyList());
//        }
//    }
//
//    // ✅ FIXED: Get stock by ID with shelf information
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getStockById(@PathVariable Integer id) {
//        try {
//            Optional<Stock> stock = stockService.getStockByIdWithShelf(id);
//            if (stock.isPresent()) {
//                return createStockResponse(stock.get());
//            }
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fetch stock: " + e.getMessage()));
//        }
//    }
//
//    // ✅ FIXED: Get stock by product ID
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<?> getStockByProductId(@PathVariable Integer productId) {
//        try {
//            Optional<Stock> stock = stockService.getStockByProductIdWithShelf(productId);
//            if (stock.isPresent()) {
//                return createStockResponse(stock.get());
//            }
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fetch stock: " + e.getMessage()));
//        }
//    }
//
//    // ✅ FIXED: Add stock with proper shelf handling
////    @PostMapping("/add-stocks")
////    public ResponseEntity<?> addStock(@RequestBody Stock stock) {
////        try {
////            if (stock.getProduct() == null || stock.getProduct().getProductId() == null) {
////                return ResponseEntity.badRequest().body(Map.of("error", "Product is required"));
////            }
////
////            if (stock.getInStock() < 0) {
////                return ResponseEntity.badRequest().body(Map.of("error", "In stock quantity cannot be negative"));
////            }
////
////            // ✅ FIX: Handle shelf assignment properly
////            if (stock.getShelf() != null && stock.getShelf().getShelfId() != null) {
////                // If shelf ID is provided, validate it exists
////                Optional<Shelf> existingShelf = shelfService.getShelfById(stock.getShelf().getShelfId());
////                if (existingShelf.isPresent()) {
////                    stock.setShelf(existingShelf.get());
////                } else {
////                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid shelf ID provided"));
////                }
////            } else {
////                // ✅ FIX: Assign default shelf if none provided
////                Shelf defaultShelf = shelfService.getDefaultShelf();
////                stock.setShelf(defaultShelf);
////                System.out.println("Assigned default shelf: " + defaultShelf.getShelfId() + " - " + defaultShelf.getShelfName());
////            }
////
////            stock.setOutStock(0);
////            stock.setCurrentStock(stock.getInStock());
////
////            if (stock.getInStock() == 0) {
////                stock.setStatus("Sold Out");
////            } else {
////                stock.setStatus("In Stock");
////            }
////
////            if (stock.getLatestPurchasePrice() == null) {
////                stock.setLatestPurchasePrice(0.0);
////            }
////            if (stock.getSellingPrice() == null) {
////                stock.setSellingPrice(0.0);
////            }
////
////            Stock savedStock = stockRepository.save(stock);
////            System.out.println("Stock saved with shelf ID: " + (savedStock.getShelf() != null ? savedStock.getShelf().getShelfId() : "NULL"));
////
////            return createStockResponse(savedStock);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to add stock: " + e.getMessage()));
////        }
////    }
//
//
//    // ✅ FIXED: Add stock with proper shelf handling
//    @PostMapping("/add-stocks")
//    public ResponseEntity<?> addStock(@RequestBody Stock stock) {
//        try {
//            if (stock.getProduct() == null || stock.getProduct().getProductId() == null) {
//                return ResponseEntity.badRequest().body(Map.of("error", "Product is required"));
//            }
//
//            if (stock.getInStock() < 0) {
//                return ResponseEntity.badRequest().body(Map.of("error", "In stock quantity cannot be negative"));
//            }
//
//            // ✅ FIXED: Handle shelf assignment properly - don't default to shelf 2
//            if (stock.getShelf() != null && stock.getShelf().getShelfId() != null) {
//                // If shelf ID is provided, validate it exists
//                Optional<Shelf> existingShelf = shelfService.getShelfById(stock.getShelf().getShelfId());
//                if (existingShelf.isPresent()) {
//                    stock.setShelf(existingShelf.get());
//                    System.out.println("Assigned provided shelf: " + existingShelf.get().getShelfId() + " - " + existingShelf.get().getShelfName());
//                } else {
//                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid shelf ID provided: " + stock.getShelf().getShelfId()));
//                }
//            } else {
//                // ✅ FIXED: Don't assign default shelf if none provided - set to null
//                stock.setShelf(null);
//                System.out.println("No shelf provided, setting shelf to NULL");
//            }
//
//            stock.setOutStock(0);
//            stock.setCurrentStock(stock.getInStock());
//
//            if (stock.getInStock() == 0) {
//                stock.setStatus("Sold Out");
//            } else {
//                stock.setStatus("In Stock");
//            }
//
//            if (stock.getLatestPurchasePrice() == null) {
//                stock.setLatestPurchasePrice(0.0);
//            }
//            if (stock.getSellingPrice() == null) {
//                stock.setSellingPrice(0.0);
//            }
//
//            Stock savedStock = stockRepository.save(stock);
//            System.out.println("Stock saved with shelf ID: " + (savedStock.getShelf() != null ? savedStock.getShelf().getShelfId() : "NULL"));
//
//            return createStockResponse(savedStock);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to add stock: " + e.getMessage()));
//        }
//    }
//
//    // ✅ FIXED: Update stock with shelf handling
////    @PutMapping("/update/{id}")
////    public ResponseEntity<?> updateStock(@PathVariable Integer id, @RequestBody Stock stockDetails) {
////        try {
////            Optional<Stock> optionalStock = stockService.getStockById(id);
////            if (optionalStock.isEmpty()) {
////                return ResponseEntity.notFound().build();
////            }
////
////            Stock existingStock = optionalStock.get();
////
////            // Update fields including shelf
////            if (stockDetails.getInStock() != null) {
////                existingStock.setInStock(stockDetails.getInStock());
////            }
////            if (stockDetails.getOutStock() != null) {
////                existingStock.setOutStock(stockDetails.getOutStock());
////            }
////            if (stockDetails.getLatestPurchasePrice() != null) {
////                existingStock.setLatestPurchasePrice(stockDetails.getLatestPurchasePrice());
////            }
////            if (stockDetails.getSellingPrice() != null) {
////                existingStock.setSellingPrice(stockDetails.getSellingPrice());
////            }
////
////            // ✅ FIX: Handle shelf update properly
////            if (stockDetails.getShelf() != null && stockDetails.getShelf().getShelfId() != null) {
////                Optional<Shelf> existingShelf = shelfService.getShelfById(stockDetails.getShelf().getShelfId());
////                if (existingShelf.isPresent()) {
////                    existingStock.setShelf(existingShelf.get());
////                } else {
////                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid shelf ID provided"));
////                }
////            } else if (stockDetails.getShelf() == null) {
////                // If shelf is explicitly set to null, assign default shelf
////                Shelf defaultShelf = shelfService.getDefaultShelf();
////                existingStock.setShelf(defaultShelf);
////            }
////
////            Stock updatedStock = stockRepository.save(existingStock);
////            return createStockResponse(updatedStock);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to update stock: " + e.getMessage()));
////        }
////    }
//
//    // ✅ FIXED: Update stock with proper shelf handling
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateStock(@PathVariable Integer id, @RequestBody Stock stockDetails) {
//        try {
//            Optional<Stock> optionalStock = stockService.getStockById(id);
//            if (optionalStock.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            Stock existingStock = optionalStock.get();
//
//            // Update fields including shelf
//            if (stockDetails.getInStock() != null) {
//                existingStock.setInStock(stockDetails.getInStock());
//            }
//            if (stockDetails.getOutStock() != null) {
//                existingStock.setOutStock(stockDetails.getOutStock());
//            }
//            if (stockDetails.getLatestPurchasePrice() != null) {
//                existingStock.setLatestPurchasePrice(stockDetails.getLatestPurchasePrice());
//            }
//            if (stockDetails.getSellingPrice() != null) {
//                existingStock.setSellingPrice(stockDetails.getSellingPrice());
//            }
//
//            // ✅ FIXED: Handle shelf update properly - only update if provided
//            if (stockDetails.getShelf() != null && stockDetails.getShelf().getShelfId() != null) {
//                Optional<Shelf> existingShelf = shelfService.getShelfById(stockDetails.getShelf().getShelfId());
//                if (existingShelf.isPresent()) {
//                    existingStock.setShelf(existingShelf.get());
//                    System.out.println("Updated shelf to: " + existingShelf.get().getShelfId() + " - " + existingShelf.get().getShelfName());
//                } else {
//                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid shelf ID provided: " + stockDetails.getShelf().getShelfId()));
//                }
//            } else if (stockDetails.getShelf() == null) {
//                // If shelf is explicitly set to null, keep it as null
//                existingStock.setShelf(null);
//                System.out.println("Shelf explicitly set to NULL");
//            }
//            // If shelf is not provided in the request, don't change the existing shelf
//
//            Stock updatedStock = stockRepository.save(existingStock);
//            return createStockResponse(updatedStock);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to update stock: " + e.getMessage()));
//        }
//    }
//
//    // ✅ NEW: Assign shelf to existing stock
//    @PatchMapping("/{stockId}/assign-shelf")
//    public ResponseEntity<?> assignShelfToStock(@PathVariable Integer stockId, @RequestParam Integer shelfId) {
//        try {
//            Optional<Stock> stockOpt = stockService.getStockById(stockId);
//            if (stockOpt.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            Optional<Shelf> shelfOpt = shelfService.getShelfById(shelfId);
//            if (shelfOpt.isEmpty()) {
//                return ResponseEntity.badRequest().body(Map.of("error", "Shelf not found with ID: " + shelfId));
//            }
//
//            Stock stock = stockOpt.get();
//            stock.setShelf(shelfOpt.get());
//            Stock updatedStock = stockRepository.save(stock);
//
//            return createStockResponse(updatedStock);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to assign shelf: " + e.getMessage()));
//        }
//    }
//
//    // ✅ NEW: Fix all NULL shelf records
//    @PostMapping("/fix-null-shelves")
//    public ResponseEntity<?> fixAllNullShelves() {
//        try {
//            List<Stock> stocksWithNullShelf = stockRepository.findByShelfIsNull();
//            Shelf defaultShelf = shelfService.getDefaultShelf();
//
//            int fixedCount = 0;
//            for (Stock stock : stocksWithNullShelf) {
//                stock.setShelf(defaultShelf);
//                stockRepository.save(stock);
//                fixedCount++;
//            }
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Fixed " + fixedCount + " stocks with NULL shelf",
//                    "defaultShelfId", defaultShelf.getShelfId(),
//                    "defaultShelfName", defaultShelf.getShelfName()
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fix NULL shelves: " + e.getMessage()));
//        }
//    }
//
//    // ✅ NEW: Get available shelves
////    @GetMapping("/available-shelves")
////    public ResponseEntity<?> getAvailableShelves() {
////        try {
////            List<Shelf> shelves = shelfService.getAllShelves();
////            return ResponseEntity.ok(shelves);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of("error", "Failed to fetch shelves: " + e.getMessage()));
////        }
////    }
////
////    // Helper method to create consistent stock response
////    private ResponseEntity<?> createStockResponse(Stock stock) {
////        Map<String, Object> response = new HashMap<>();
////        response.put("stockId", stock.getStockId());
////        response.put("product", stock.getProduct());
////        response.put("inStock", stock.getInStock());
////        response.put("outStock", stock.getOutStock());
////        response.put("currentStock", stock.getCurrentStock());
////        response.put("status", stock.getStatus());
////        response.put("latestPurchasePrice", stock.getLatestPurchasePrice());
////        response.put("sellingPrice", stock.getSellingPrice());
////
////        // Include shelf data
////        if (stock.getShelf() != null) {
////            Map<String, Object> shelfMap = new HashMap<>();
////            shelfMap.put("shelfId", stock.getShelf().getShelfId());
////            shelfMap.put("shelfName", stock.getShelf().getShelfName());
////            shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
////            response.put("shelf", shelfMap);
////        } else {
////            response.put("shelf", null);
////        }
////
////        return ResponseEntity.ok(response);
////    }
//    // ✅ UPDATED: Get available shelves with consistent shelfId field
//    @GetMapping("/available-shelves")
//    public ResponseEntity<?> getAvailableShelves() {
//        try {
//            List<Shelf> shelves = shelfService.getAllShelves();
//            return ResponseEntity.ok(shelves);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fetch shelves: " + e.getMessage()));
//        }
//    }
//
//    // ✅ UPDATED: Helper method to create consistent stock response with shelfId
//    private ResponseEntity<?> createStockResponse(Stock stock) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("stockId", stock.getStockId());
//        response.put("product", stock.getProduct());
//        response.put("inStock", stock.getInStock());
//        response.put("outStock", stock.getOutStock());
//        response.put("currentStock", stock.getCurrentStock());
//        response.put("status", stock.getStatus());
//        response.put("latestPurchasePrice", stock.getLatestPurchasePrice());
//        response.put("sellingPrice", stock.getSellingPrice());
//
//        // Include shelf data with consistent shelfId field
//        if (stock.getShelf() != null) {
//            Map<String, Object> shelfMap = new HashMap<>();
//            shelfMap.put("shelfId", stock.getShelf().getShelfId());
//            shelfMap.put("shelfName", stock.getShelf().getShelfName());
//            shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
//            response.put("shelf", shelfMap);
//        } else {
//            response.put("shelf", null);
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//
//    // Existing methods remain the same...
//    @GetMapping("/inventory-value")
//    public ResponseEntity<Integer> getTotalInventoryValue() {
//        Integer inventoryValue = stockService.getTotalInventoryValue();
//        return ResponseEntity.ok(inventoryValue);
//    }
//
//    @PatchMapping("/{id}/add")
//    public ResponseEntity<Stock> addToStock(@PathVariable Integer id, @RequestParam Integer quantity) {
//        Stock updatedStock = stockService.addToStock(id, quantity);
//        if (updatedStock != null) {
//            return ResponseEntity.ok(updatedStock);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PatchMapping("/{id}/remove")
//    public ResponseEntity<Stock> removeFromStock(@PathVariable Integer id, @RequestParam Integer quantity) {
//        Stock updatedStock = stockService.removeFromStock(id, quantity);
//        if (updatedStock != null) {
//            return ResponseEntity.ok(updatedStock);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
//        Optional<Stock> stock = stockService.getStockById(id);
//        if (stock.isPresent()) {
//            stockService.deleteStock(id);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/monthly-report")
//    public ResponseEntity<?> getMonthlyStockReport(@RequestParam int month, @RequestParam int year) {
//        try {
//            List<Sale> monthlySales = saleService.getSalesByMonth(month, year);
//            List<Stock> allStocks = stockService.getAllStocks();
//
//            Map<String, Object> report = new HashMap<>();
//            report.put("monthlySales", monthlySales);
//            report.put("currentStocks", allStocks);
//
//            return ResponseEntity.ok(report);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Server error", "message", e.getMessage()));
//        }
//    }
//
//}


package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Sale;
import com.example.decoration_backend_springboot.Model.Shelf;
import com.example.decoration_backend_springboot.Model.Stock;
import com.example.decoration_backend_springboot.Repository.SaleRepository;
import com.example.decoration_backend_springboot.Repository.StockRepository;
import com.example.decoration_backend_springboot.Service.SaleService;
import com.example.decoration_backend_springboot.Service.ShelfService;
import com.example.decoration_backend_springboot.Service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/stocks")
public class StockAPI {

    private final StockService stockService;
    private final SaleService saleService;
    private final ShelfService shelfService;
    private final StockRepository stockRepository;
    private final SaleRepository saleRepository;

    public StockAPI(StockService stockService, SaleService saleService,
                    ShelfService shelfService, StockRepository stockRepository,
                    SaleRepository saleRepository) {
        this.stockService = stockService;
        this.saleService = saleService;
        this.shelfService = shelfService;
        this.stockRepository = stockRepository;
        this.saleRepository = saleRepository;
    }

    // ✅ FIXED: Return stock data with shelf information
    @GetMapping("/all-stocks")
    public ResponseEntity<List<Map<String, Object>>> getAllStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocksWithShelf();

            List<Map<String, Object>> stockList = stocks.stream().map(stock -> {
                try {
                    // Calculate sold quantity from sales
                    Integer soldQuantityFromSales = saleRepository.sumQuantitySoldByStockId(stock.getStockId());
                    if (soldQuantityFromSales == null) soldQuantityFromSales = 0;

                    Integer actualOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
                    Integer effectiveSoldQuantity = Math.max(soldQuantityFromSales, actualOutStock);
                    int currentStock = Math.max(0, stock.getInStock() - effectiveSoldQuantity);

                    // Determine status
                    String status;
                    if (currentStock == 0) {
                        status = "Sold Out";
                    } else if (effectiveSoldQuantity > 0) {
                        status = "Partially Sold";
                    } else {
                        status = "In Stock";
                    }

                    // Calculate profit
                    Double profit = 0.0;
                    if (stock.getSellingPrice() != null && stock.getLatestPurchasePrice() != null) {
                        profit = (stock.getSellingPrice() - stock.getLatestPurchasePrice()) * effectiveSoldQuantity;
                    }

                    // Calculate margin
                    Double margin = 0.0;
                    if (stock.getLatestPurchasePrice() != null && stock.getLatestPurchasePrice() > 0) {
                        margin = ((stock.getSellingPrice() - stock.getLatestPurchasePrice()) / stock.getLatestPurchasePrice()) * 100;
                    }

                    Map<String, Object> stockMap = new HashMap<>();
                    stockMap.put("stockId", stock.getStockId());
                    stockMap.put("product", stock.getProduct());
                    stockMap.put("inStock", stock.getInStock());
                    stockMap.put("outStock", effectiveSoldQuantity);
                    stockMap.put("currentStock", currentStock);
                    stockMap.put("status", status);
                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
                    stockMap.put("sellingPrice", stock.getSellingPrice());
                    stockMap.put("profit", profit);
                    stockMap.put("margin", margin);

                    // ✅ Add shelf data to response
                    if (stock.getShelf() != null) {
                        Map<String, Object> shelfMap = new HashMap<>();
                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
                        stockMap.put("shelf", shelfMap);
                    } else {
                        stockMap.put("shelf", null);
                    }

                    return stockMap;
                } catch (Exception e) {
                    System.err.println("Error processing stock ID " + stock.getStockId() + ": " + e.getMessage());

                    // Fallback with basic data including shelf
                    Map<String, Object> stockMap = new HashMap<>();
                    stockMap.put("stockId", stock.getStockId());
                    stockMap.put("product", stock.getProduct());
                    stockMap.put("inStock", stock.getInStock());
                    stockMap.put("outStock", stock.getOutStock() != null ? stock.getOutStock() : 0);
                    stockMap.put("currentStock", stock.getCurrentStock() != null ? stock.getCurrentStock() : 0);
                    stockMap.put("status", stock.getStatus() != null ? stock.getStatus() : "Unknown");
                    stockMap.put("latestPurchasePrice", stock.getLatestPurchasePrice());
                    stockMap.put("sellingPrice", stock.getSellingPrice());
                    stockMap.put("profit", 0.0);
                    stockMap.put("margin", 0.0);

                    // Always include shelf data even in error case
                    if (stock.getShelf() != null) {
                        Map<String, Object> shelfMap = new HashMap<>();
                        shelfMap.put("shelfId", stock.getShelf().getShelfId());
                        shelfMap.put("shelfName", stock.getShelf().getShelfName());
                        shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
                        stockMap.put("shelf", shelfMap);
                    } else {
                        stockMap.put("shelf", null);
                    }

                    return stockMap;
                }
            }).collect(Collectors.toList());

            return ResponseEntity.ok(stockList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    // ✅ FIXED: Get stock by ID with shelf information
    @GetMapping("/{id}")
    public ResponseEntity<?> getStockById(@PathVariable Integer id) {
        try {
            Optional<Stock> stock = stockService.getStockByIdWithShelf(id);
            if (stock.isPresent()) {
                return createStockResponse(stock.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch stock: " + e.getMessage()));
        }
    }

    // ✅ FIXED: Get stock by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getStockByProductId(@PathVariable Integer productId) {
        try {
            Optional<Stock> stock = stockService.getStockByProductIdWithShelf(productId);
            if (stock.isPresent()) {
                return createStockResponse(stock.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch stock: " + e.getMessage()));
        }
    }

    // ✅ FIXED: Add stock with proper shelf handling - NO DEFAULT SHELF
    @PostMapping("/add-stocks")
    public ResponseEntity<?> addStock(@RequestBody Stock stock) {
        try {
            if (stock.getProduct() == null || stock.getProduct().getProductId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Product is required"));
            }

            if (stock.getInStock() < 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "In stock quantity cannot be negative"));
            }

            // ✅ FIXED: Handle shelf assignment properly - don't default to any shelf
            if (stock.getShelf() != null && stock.getShelf().getShelfId() != null) {
                // If shelf ID is provided, validate it exists
                Optional<Shelf> existingShelf = shelfService.getShelfById(stock.getShelf().getShelfId());
                if (existingShelf.isPresent()) {
                    stock.setShelf(existingShelf.get());
                    System.out.println("✅ Assigned provided shelf: " + existingShelf.get().getShelfId() + " - " + existingShelf.get().getShelfName());
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid shelf ID provided: " + stock.getShelf().getShelfId()));
                }
            } else {
                // ✅ FIXED: Don't assign any default shelf - set to null
                stock.setShelf(null);
                System.out.println("ℹ️ No shelf provided, setting shelf to NULL");
            }

            stock.setOutStock(0);
            stock.setCurrentStock(stock.getInStock());

            if (stock.getInStock() == 0) {
                stock.setStatus("Sold Out");
            } else {
                stock.setStatus("In Stock");
            }

            if (stock.getLatestPurchasePrice() == null) {
                stock.setLatestPurchasePrice(0.0);
            }
            if (stock.getSellingPrice() == null) {
                stock.setSellingPrice(0.0);
            }

            Stock savedStock = stockRepository.save(stock);
            System.out.println("💾 Stock saved with shelf ID: " + (savedStock.getShelf() != null ? savedStock.getShelf().getShelfId() : "NULL"));

            return createStockResponse(savedStock);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add stock: " + e.getMessage()));
        }
    }

    // ✅ FIXED: Update stock with proper shelf handling - NO DEFAULT SHELF
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Integer id, @RequestBody Stock stockDetails) {
        try {
            Optional<Stock> optionalStock = stockService.getStockById(id);
            if (optionalStock.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Stock existingStock = optionalStock.get();

            // Update fields including shelf
            if (stockDetails.getInStock() != null) {
                existingStock.setInStock(stockDetails.getInStock());
            }
            if (stockDetails.getOutStock() != null) {
                existingStock.setOutStock(stockDetails.getOutStock());
            }
            if (stockDetails.getLatestPurchasePrice() != null) {
                existingStock.setLatestPurchasePrice(stockDetails.getLatestPurchasePrice());
            }
            if (stockDetails.getSellingPrice() != null) {
                existingStock.setSellingPrice(stockDetails.getSellingPrice());
            }

            // ✅ FIXED: Handle shelf update properly - only update if provided
            if (stockDetails.getShelf() != null && stockDetails.getShelf().getShelfId() != null) {
                Optional<Shelf> existingShelf = shelfService.getShelfById(stockDetails.getShelf().getShelfId());
                if (existingShelf.isPresent()) {
                    existingStock.setShelf(existingShelf.get());
                    System.out.println("✅ Updated shelf to: " + existingShelf.get().getShelfId() + " - " + existingShelf.get().getShelfName());
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid shelf ID provided: " + stockDetails.getShelf().getShelfId()));
                }
            } else if (stockDetails.getShelf() == null) {
                // If shelf is explicitly set to null, set it to null
                existingStock.setShelf(null);
                System.out.println("ℹ️ Shelf explicitly set to NULL");
            }
            // If shelf is not provided in the request, don't change the existing shelf

            Stock updatedStock = stockRepository.save(existingStock);
            return createStockResponse(updatedStock);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update stock: " + e.getMessage()));
        }
    }

    // ✅ NEW: Assign shelf to existing stock
    @PatchMapping("/{stockId}/assign-shelf")
    public ResponseEntity<?> assignShelfToStock(@PathVariable Integer stockId, @RequestParam Integer shelfId) {
        try {
            Optional<Stock> stockOpt = stockService.getStockById(stockId);
            if (stockOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Optional<Shelf> shelfOpt = shelfService.getShelfById(shelfId);
            if (shelfOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Shelf not found with ID: " + shelfId));
            }

            Stock stock = stockOpt.get();
            stock.setShelf(shelfOpt.get());
            Stock updatedStock = stockRepository.save(stock);

            return createStockResponse(updatedStock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to assign shelf: " + e.getMessage()));
        }
    }

    // ✅ NEW: Remove default shelf assignments (shelf_id = 2)
    @PostMapping("/remove-default-shelves")
    public ResponseEntity<?> removeDefaultShelves() {
        try {
            List<Stock> stocksWithDefaultShelf = stockRepository.findByShelfShelfId(2);
            int updatedCount = 0;

            for (Stock stock : stocksWithDefaultShelf) {
                stock.setShelf(null);
                stockRepository.save(stock);
                updatedCount++;
                System.out.println("🗑️ Removed default shelf from stock ID: " + stock.getStockId());
            }

            return ResponseEntity.ok(Map.of(
                    "message", "Removed default shelf from " + updatedCount + " stocks",
                    "updatedCount", updatedCount
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to remove default shelves: " + e.getMessage()));
        }
    }

    // ✅ NEW: Fix all NULL shelf records (if you want to assign default shelf later)
    @PostMapping("/fix-null-shelves")
    public ResponseEntity<?> fixAllNullShelves() {
        try {
            List<Stock> stocksWithNullShelf = stockRepository.findByShelfIsNull();
            Shelf defaultShelf = shelfService.getDefaultShelf();

            int fixedCount = 0;
            for (Stock stock : stocksWithNullShelf) {
                stock.setShelf(defaultShelf);
                stockRepository.save(stock);
                fixedCount++;
            }

            return ResponseEntity.ok(Map.of(
                    "message", "Fixed " + fixedCount + " stocks with NULL shelf",
                    "defaultShelfId", defaultShelf.getShelfId(),
                    "defaultShelfName", defaultShelf.getShelfName()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fix NULL shelves: " + e.getMessage()));
        }
    }

    // ✅ Get available shelves
    @GetMapping("/available-shelves")
    public ResponseEntity<?> getAvailableShelves() {
        try {
            List<Shelf> shelves = shelfService.getAllShelves();
            return ResponseEntity.ok(shelves);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch shelves: " + e.getMessage()));
        }
    }

    // Helper method to create consistent stock response
    private ResponseEntity<?> createStockResponse(Stock stock) {
        Map<String, Object> response = new HashMap<>();
        response.put("stockId", stock.getStockId());
        response.put("product", stock.getProduct());
        response.put("inStock", stock.getInStock());
        response.put("outStock", stock.getOutStock());
        response.put("currentStock", stock.getCurrentStock());
        response.put("status", stock.getStatus());
        response.put("latestPurchasePrice", stock.getLatestPurchasePrice());
        response.put("sellingPrice", stock.getSellingPrice());

        // Include shelf data
        if (stock.getShelf() != null) {
            Map<String, Object> shelfMap = new HashMap<>();
            shelfMap.put("shelfId", stock.getShelf().getShelfId());
            shelfMap.put("shelfName", stock.getShelf().getShelfName());
            shelfMap.put("locationDescription", stock.getShelf().getLocationDescription());
            response.put("shelf", shelfMap);
        } else {
            response.put("shelf", null);
        }

        return ResponseEntity.ok(response);
    }

    // Existing methods remain the same...
    @GetMapping("/inventory-value")
    public ResponseEntity<Integer> getTotalInventoryValue() {
        Integer inventoryValue = stockService.getTotalInventoryValue();
        return ResponseEntity.ok(inventoryValue);
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

    @GetMapping("/monthly-report")
    public ResponseEntity<?> getMonthlyStockReport(@RequestParam int month, @RequestParam int year) {
        try {
            List<Sale> monthlySales = saleService.getSalesByMonth(month, year);
            List<Stock> allStocks = stockService.getAllStocks();

            Map<String, Object> report = new HashMap<>();
            report.put("monthlySales", monthlySales);
            report.put("currentStocks", allStocks);

            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server error", "message", e.getMessage()));
        }
    }
}