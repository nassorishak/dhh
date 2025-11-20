package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Model.Shelf;
import com.example.decoration_backend_springboot.Model.User;
import com.example.decoration_backend_springboot.Service.ProductService;
import com.example.decoration_backend_springboot.Service.ShelfService;
import com.example.decoration_backend_springboot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/product")
public class ProductAPI {
    @Autowired
    private ProductService productService;

     private  final ShelfService shelfService;
    private  final UserService userService;

    public ProductAPI(ProductService productService, ShelfService shelfService, UserService userService) {
        this.productService = productService;
        this.shelfService = shelfService;
        this.userService = userService;
    }


@PostMapping("/add/product")
public ResponseEntity<?> addProduct(
        @RequestParam("productCode") String productCode,
        @RequestParam("productName") String productName,
        @RequestParam(value = "productDescription", required = false) String productDescription,
        @RequestParam("price") Double price,
        @RequestParam(value = "latestPurchasePrice", required = false) Double latestPurchasePrice,
        @RequestParam("sellingPrice") Double sellingPrice,
        @RequestParam("productCompany") String productCompany,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "productUnit", required = false) String productUnit,
        @RequestParam(value = "stockQuantity", required = false) Integer stockQuantity,
        @RequestParam(value = "userId", required = false) Integer userId,
        @RequestParam("shelfId") Integer shelfId,
        @RequestParam(value = "image", required = false) MultipartFile image
) throws IOException {

    try {
        System.out.println("=== DEBUG: Adding Product ===");
        System.out.println("Received userId: " + userId);

        // ✅ DEBUG: List ALL users in the database
        System.out.println("=== DEBUG: Checking ALL users in database ===");
        List<User> allUsers = userService.findAll();
        if (allUsers.isEmpty()) {
            System.out.println("❌ NO USERS FOUND IN DATABASE!");
        } else {
            System.out.println("✅ Found " + allUsers.size() + " users in database:");
            for (User user : allUsers) {
                System.out.println("   User ID: " + user.getUserId() +
                        ", Email: " + user.getEmail() +
                        ", Name: " + user.getName() +
                        ", Role: " + user.getRole());
            }
        }

        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName(productName);
        product.setProductDescription(productDescription != null ? productDescription : "");
        product.setPrice(price);
        product.setLatestPurchasePrice(latestPurchasePrice != null ? latestPurchasePrice : 0.0);
        product.setSellingPrice(sellingPrice);
        product.setCategory(category != null ? category : "General");
        product.setProductCompany(productCompany);
        product.setProductUnit(productUnit != null ? productUnit : "piece");

        // ✅ FIX: Handle stock quantity properly
        int finalStockQuantity = (stockQuantity != null) ? stockQuantity : 0;
        product.setStockQuantity(finalStockQuantity);

        // ✅ FIX: Also set current_stock if your entity has this field
        try {
            // Try to set currentStock using reflection if the field exists
            product.getClass().getMethod("setCurrentStock", Integer.class).invoke(product, finalStockQuantity);
            System.out.println("✅ current_stock set to: " + finalStockQuantity);
        } catch (Exception e) {
            System.out.println("ℹ️ current_stock field not available in Product entity");
        }

        if (image != null && !image.isEmpty()) {
            product.setImage(image.getBytes());
            System.out.println("✅ Image set successfully");
        } else {
            System.out.println("⚠️ No image provided");
        }

        // ✅ FIX: Handle user relationship gracefully - DON'T THROW EXCEPTION
        if (userId != null) {
            try {
                Optional<User> userOptional = userService.findById(userId);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    product.setUser(user);
                    System.out.println("✅ User set successfully: " + user.getUserId() + " - " + user.getName());
                } else {
                    System.out.println("⚠️ User not found with id: " + userId + ", proceeding without user association");
                    // ✅ IMPORTANT: Continue without throwing exception
                    // Product will be saved without user association
                }
            } catch (Exception e) {
                System.out.println("❌ Error setting user: " + e.getMessage());
                // ✅ IMPORTANT: Continue without throwing exception
                // Product will be saved without user association
            }
        } else {
            System.out.println("ℹ️ No userId provided, product will have no user association");
        }

        // ✅ Set Shelf
        Optional<Shelf> shelfOptional = shelfService.findById(shelfId);
        if (shelfOptional.isPresent()) {
            Shelf shelf = shelfOptional.get();
            product.setShelf(shelf);
            System.out.println("✅ Shelf set successfully: " + shelf.getId() + " - " + shelf.getShelfName());
        } else {
            System.out.println("❌ Shelf not found with id: " + shelfId);
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Shelf not found with id: " + shelfId)
            );
        }

        // Debug product object before save
        System.out.println("=== DEBUG: Product before save ===");
        System.out.println("Product Code: " + product.getProductCode());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Stock Quantity: " + product.getStockQuantity());
        System.out.println("User: " + (product.getUser() != null ? product.getUser().getUserId() : "null"));
        System.out.println("Shelf: " + (product.getShelf() != null ? product.getShelf().getId() : "null"));

        Product savedProduct = productService.save(product);

        // Debug saved product
        System.out.println("=== DEBUG: Product after save ===");
        System.out.println("Saved Product ID: " + savedProduct.getProductId());
        System.out.println("Saved Stock Quantity: " + savedProduct.getStockQuantity());
        System.out.println("Saved User: " + (savedProduct.getUser() != null ? savedProduct.getUser().getUserId() : "null"));

        Map<String, Object> response = Map.of(
                "success", true,
                "message", "Product added successfully",
                "productId", savedProduct.getProductId(),
                "stockQuantity", savedProduct.getStockQuantity(),
                "hasUser", savedProduct.getUser() != null
        );

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        System.out.println("❌ Error adding product: " + e.getMessage());
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("success", false, "message", "Error adding product: " + e.getMessage())
        );
    }
}

@PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody Product product) {
        try {
            // Check if the product exists
            if (productService.findById(productId).isPresent()) {
                // Set the product ID to keep the same ID during the update
                product.setProductId(productId); // Assuming your Product class has a setProductId method

                // Save the updated product
                Product updatedProduct = productService.save(product); // Save it and get the updated version

                // Return a successful response along with the updated product
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                // Product not found
                return new ResponseEntity<>("The product was not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return new ResponseEntity<>("An error occurred while updating the product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@DeleteMapping("/delete/{product_id}")
public ResponseEntity<?> deleteProduct(@PathVariable int product_id) {
    try {
        productService.deleteById(product_id);
        return new ResponseEntity<>("Product was deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("Product not deleted", HttpStatus.BAD_REQUEST);
    }
}
    @GetMapping("/getByID/{product_id}")
    public ResponseEntity<?> getProductById(@PathVariable int product_id){

        try {
            Optional<Product> optionalProduct = productService.findById(product_id);

            if (optionalProduct.isPresent()){
                return  new ResponseEntity<>(optionalProduct,HttpStatus.ACCEPTED);

            }
            else {
                return  new ResponseEntity<>("the product was accessed successful",HttpStatus.OK);
            }

        }catch (Exception e){
            return  new ResponseEntity<>("the product was not accessed",HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(value = "query", required = false) String query) {
        List<Product> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/api/product")
    public ResponseEntity<String> addProduct(@RequestPart("product") Product product,
                                             @RequestPart("file") MultipartFile file) {
        // Process the product and file
        return ResponseEntity.ok("Product added successfully!");
    }

    @GetMapping("get/product")

    public  ResponseEntity<?> getProduct(){
        try {
            List<Product> ProductList = productService.findAll();
            if (ProductList.isEmpty()){
                return new ResponseEntity<>("the product not added", HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(ProductList,HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            return  new ResponseEntity<>("the product added successful",HttpStatus.OK);
        }
    }


}



