//package com.example.decoration_backend_springboot.API;
//
//import com.example.decoration_backend_springboot.Model.Purchase;
//import com.example.decoration_backend_springboot.Service.PurchaseService;
//import com.example.decoration_backend_springboot.Service.StockService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@CrossOrigin("http://localhost:3000")
//@RestController
//@RequestMapping("/api/purchases")
//public class PurchaseAPI {
//
//    private final PurchaseService purchaseService;
//
//    private  final StockService stockService;
//
//    public PurchaseAPI(PurchaseService purchaseService, StockService stockService) {
//        this.purchaseService = purchaseService;
//        this.stockService = stockService;
//    }
//
//    // ✅ Get all purchases
//    @GetMapping
//    public ResponseEntity<List<Purchase>> getAllPurchases() {
//        List<Purchase> purchases = purchaseService.getAllPurchases();
//        return ResponseEntity.ok(purchases);
//    }
//
//    // ✅ Get purchase by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("id") Integer id) {
//        return purchaseService.getPurchaseById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // ✅ Create new purchase
////    @PostMapping("/add-purchase")
////    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase) {
////        try {
////            Purchase createdPurchase = purchaseService.createPurchase(purchase);
////            return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchase);
////        } catch (IllegalArgumentException e) {
////            return ResponseEntity.badRequest().body(
////                    Map.of("error", "Validation error", "message", e.getMessage())
////            );
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
////                    Map.of("error", "Server error", "message", e.getMessage())
////            );
////        }
////    }
//
//    @PostMapping("/add-purchase")
//    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase) {
//        try {
//            Purchase createdPurchase = purchaseService.createPurchase(purchase);
//
//            // Update stock after purchase
//            stockService.updateStockOnPurchase(createdPurchase);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchase);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(
//                    Map.of("error", "Validation error", "message", e.getMessage())
//            );
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    Map.of("error", "Server error", "message", e.getMessage())
//            );
//        }
//    }
//
//    // ✅ Update purchase
//    @PutMapping("/{id}")
//    public ResponseEntity<Purchase> updatePurchase(@PathVariable("id") Integer id, @RequestBody Purchase purchase) {
//        return purchaseService.getPurchaseById(id)
//                .map(existing -> {
//                    Purchase updatedPurchase = purchaseService.updatePurchase(id, purchase);
//                    return ResponseEntity.ok(updatedPurchase);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // ✅ Delete purchase
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deletePurchase(@PathVariable("id") Integer id) {
//        return purchaseService.getPurchaseById(id)
//                .map(existing -> {
//                    purchaseService.deletePurchase(id);
//                    return ResponseEntity.noContent().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//}

package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Purchase;
import com.example.decoration_backend_springboot.Service.PurchaseService;
import com.example.decoration_backend_springboot.Service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/purchases")
public class PurchaseAPI {

    private final PurchaseService purchaseService;
    private final StockService stockService;

    public PurchaseAPI(PurchaseService purchaseService, StockService stockService) {
        this.purchaseService = purchaseService;
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("id") Integer id) {
        return purchaseService.getPurchaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create new purchase
    @PostMapping("/add-purchase")
    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase) {
        try {
            // ✅ Simple validation
            if (purchase.getSellingPrice() == null || purchase.getSellingPrice() <= 0) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Validation error", "message", "Selling price must be greater than 0.")
                );
            }

            Purchase createdPurchase = purchaseService.createPurchase(purchase);

            // ✅ Update stock after purchase
            stockService.updateStockOnPurchase(createdPurchase);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Validation error", "message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Server error", "message", e.getMessage())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable("id") Integer id, @RequestBody Purchase purchase) {
        return purchaseService.getPurchaseById(id)
                .map(existing -> ResponseEntity.ok(purchaseService.updatePurchase(id, purchase)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePurchase(@PathVariable("id") Integer id) {
        return purchaseService.getPurchaseById(id)
                .map(existing -> {
                    purchaseService.deletePurchase(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
