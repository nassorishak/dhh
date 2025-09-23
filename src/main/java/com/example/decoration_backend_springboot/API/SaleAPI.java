package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Sale;
import com.example.decoration_backend_springboot.Service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/sales")
public class SaleAPI {

        private final SaleService saleService;

        @Autowired
        public SaleAPI(SaleService saleService) {
            this.saleService = saleService;
        }

        @GetMapping("/all-sales")
        public ResponseEntity<List<Sale>> getAllSales() {
            List<Sale> sales = saleService.getAllSales();
            return ResponseEntity.ok(sales);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Sale> getSaleById(@PathVariable Integer id) {
            Optional<Sale> sale = saleService.getSaleById(id);
            return sale.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/date/{date}")
        public ResponseEntity<List<Sale>> getSalesByDate(@PathVariable LocalDate date) {
            List<Sale> sales = saleService.getSalesByDate(date);
            return ResponseEntity.ok(sales);
        }

        @GetMapping("/customer/{customerName}")
        public ResponseEntity<List<Sale>> getSalesByCustomer(@PathVariable String customerName) {
            List<Sale> sales = saleService.getSalesByCustomer(customerName);
            return ResponseEntity.ok(sales);
        }

        @GetMapping("/product/{productId}")
        public ResponseEntity<List<Sale>> getSalesByProduct(@PathVariable Integer productId) {
            List<Sale> sales = saleService.getSalesByProduct(productId);
            return ResponseEntity.ok(sales);
        }

        @GetMapping("/total-amount")
        public ResponseEntity<Double> getTotalSalesAmount() {
            Double totalAmount = saleService.getTotalSalesAmount();
            return ResponseEntity.ok(totalAmount);
        }

        @GetMapping("/total-amount/date/{date}")
        public ResponseEntity<Double> getTotalSalesAmountByDate(@PathVariable LocalDate date) {
            Double totalAmount = saleService.getTotalSalesAmountByDate(date);
            return ResponseEntity.ok(totalAmount);
        }

        @GetMapping("/total-amount/customer/{customerName}")
        public ResponseEntity<Double> getTotalSalesAmountByCustomer(@PathVariable String customerName) {
            Double totalAmount = saleService.getTotalSalesAmountByCustomer(customerName);
            return ResponseEntity.ok(totalAmount);
        }

        @GetMapping("/total-quantity")
        public ResponseEntity<Integer> getTotalQuantitySold() {
            Integer totalQuantity = saleService.getTotalQuantitySold();
            return ResponseEntity.ok(totalQuantity);
        }

        @GetMapping("/total-quantity/product/{productId}")
        public ResponseEntity<Integer> getTotalQuantitySoldByProduct(@PathVariable Integer productId) {
            Integer totalQuantity = saleService.getTotalQuantitySoldByProduct(productId);
            return ResponseEntity.ok(totalQuantity);
        }

        @PostMapping("/add-sale")
        public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
            Sale createdSale = saleService.createSale(sale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Sale> updateSale(@PathVariable Integer id, @RequestBody Sale sale) {
            Sale updatedSale = saleService.updateSale(id, sale);
            if (updatedSale != null) {
                return ResponseEntity.ok(updatedSale);
            }
            return ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteSale(@PathVariable Integer id) {
            Optional<Sale> sale = saleService.getSaleById(id);
            if (sale.isPresent()) {
                saleService.deleteSale(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }
    }


