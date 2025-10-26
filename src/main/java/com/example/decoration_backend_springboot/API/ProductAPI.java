package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Product;
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
import java.util.List;
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

//    @PostMapping("/add/product")
//    public Product addProduct(
//            @RequestParam("productName") String productName,
//            @RequestParam("productDescription") String productDescription,
//            @RequestParam("price") Double price,
//            @RequestParam("productCompany") String productCampany,
//            @RequestParam("category") String category,
//            @RequestParam("image") MultipartFile image) throws IOException{
//        Product product = new Product();
//         product.setProductName(productName);
//         product.setProductDescription(productDescription);
//         product.setImage(image.getBytes());
//         product.setCategory(category);
//         product.setPrice(price);
//         product.setProductCompany(productCampany);
//         return productService.save(product);
//    }

//    @PostMapping("/add/product")
//    public Product addProduct(
//            @RequestParam("productCode") String productCode,
//            @RequestParam("productName") String productName,
//            @RequestParam("productDescription") String productDescription,
//            @RequestParam("price") Double price,
//            @RequestParam("productCompany") String productCompany,
//            @RequestParam("category") String category,
//            @RequestParam("productUnit") String productUnit,
//            @RequestParam("stockQuantity") Integer stockQuantity,
//            @RequestParam Integer userId,
//            @RequestParam(value = "image", required = false) MultipartFile image
//    ) throws IOException {
//
//        Product product = new Product();
//        product.setProductCode(productCode);
//        product.setProductName(productName);
//        product.setProductDescription(productDescription);
//        product.setPrice(price);
//        product.setCategory(category);
//        product.setProductCompany(productCompany);
//        product.setProductUnit(productUnit);
//        product.setStockQuantity(stockQuantity);
//        if (image != null && !image.isEmpty()) {
//            product.setImage(image.getBytes());
//        }
//
//        return productService.save(product);
//    }

//    @PostMapping("/add/product")
//    public Product addProduct(
//            @RequestParam("productCode") String productCode,
//            @RequestParam("productName") String productName,
//            @RequestParam("productDescription") String productDescription,
//            @RequestParam("price") Double price,
//            @RequestParam("productCompany") String productCompany,
//            @RequestParam("category") String category,
//            @RequestParam("productUnit") String productUnit,
//            @RequestParam("stockQuantity") Integer stockQuantity,
//            @RequestParam Integer userId, // ID of the user/vendor
//            @RequestParam(value = "image", required = false) MultipartFile image
//    ) throws IOException {
//
//        Product product = new Product();
//        product.setProductCode(productCode);
//        product.setProductName(productName);
//        product.setProductDescription(productDescription);
//        product.setPrice(price);
//        product.setCategory(category);
//        product.setProductCompany(productCompany);
//        product.setProductUnit(productUnit);
//        product.setStockQuantity(stockQuantity);
//
//        if (image != null && !image.isEmpty()) {
//            product.setImage(image.getBytes());
//        }
//
//        // ✅ Fetch the User entity and set it
//        User user = userService.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//        product.setUser(user);
//
//        return productService.save(product);
//    }

//    @PostMapping("/add/product")
//    public Product addProduct(
//            @RequestParam("productCode") String productCode,
//            @RequestParam("productName") String productName,
//            @RequestParam("productDescription") String productDescription,
//            @RequestParam("price") Double price,
//            @RequestParam("productCompany") String productCompany,
//            @RequestParam("category") String category,
//            @RequestParam("productUnit") String productUnit,
//            @RequestParam("stockQuantity") Integer stockQuantity,
//            @RequestParam(value = "userId", required = false) Integer userId, // ✅ optional now
//            @RequestParam(value = "image", required = false) MultipartFile image
//    ) throws IOException {
//
//        Product product = new Product();
//        product.setProductCode(productCode);
//        product.setProductName(productName);
//        product.setProductDescription(productDescription);
//        product.setPrice(price);
//        product.setCategory(category);
//        product.setProductCompany(productCompany);
//        product.setProductUnit(productUnit);
//        product.setStockQuantity(stockQuantity);
//
//        // Handle image if present
//        if (image != null && !image.isEmpty()) {
//            product.setImage(image.getBytes());
//        }
//
//        // Only set user/vendor if userId is provided (for vendors)
//        if (userId != null) {
//            User user = userService.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//            product.setUser(user);
//        }
//
//        return productService.save(product);
//    }
//
//
//@PostMapping("/add/product")
//public Product addProduct(
//        @RequestParam("productCode") String productCode,
//        @RequestParam("productName") String productName,
//        @RequestParam("productDescription") String productDescription,
//        @RequestParam("price") Double price,
//        @RequestParam("latestPurchasePrice") Double latestPurchasePrice, // ✅ ADD THIS
//        @RequestParam("sellingPrice") Double sellingPrice, // ✅ ADD THIS
//        @RequestParam("productCompany") String productCompany,
//        @RequestParam("category") String category,
//        @RequestParam("productUnit") String productUnit,
//        @RequestParam("stockQuantity") Integer stockQuantity,
//        @RequestParam(value = "userId", required = false) Integer userId,
//        @RequestParam("shelfId") Integer shelfId,
//        @RequestParam(value = "image", required = false) MultipartFile image
//) throws IOException {
//
//    Product product = new Product();
//    product.setProductCode(productCode);
//    product.setProductName(productName);
//    product.setProductDescription(productDescription);
//    product.setPrice(price);
//    product.setLatestPurchasePrice(latestPurchasePrice); // ✅ SET THIS
//    product.setSellingPrice(sellingPrice); // ✅ SET THIS
//    product.setCategory(category);
//    product.setProductCompany(productCompany);
//    product.setProductUnit(productUnit);
//    product.setStockQuantity(stockQuantity);
//
//    if (image != null && !image.isEmpty()) {
//        product.setImage(image.getBytes());
//    }
//
//    // ✅ Set Vendor (if provided)
//    if (userId != null) {
//        User user = userService.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//        product.setUser(user);
//    }
//
//    // ✅ Set Shelf
//    com.example.decoration_backend_springboot.Model.Shelf shelf =
//            shelfService.findById(shelfId)
//                    .orElseThrow(() -> new RuntimeException("Shelf not found with id: " + shelfId));
//    product.setShelf(shelf);
//
//    return productService.save(product);
//}

    @PostMapping("/add/product")
    public Product addProduct(
            @RequestParam("productCode") String productCode,
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("price") Double price,
            @RequestParam("latestPurchasePrice") Double latestPurchasePrice,
            @RequestParam("sellingPrice") Double sellingPrice,
            @RequestParam("productCompany") String productCompany,
            @RequestParam("category") String category,
            @RequestParam("productUnit") String productUnit,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam(value = "userId", required = false) Integer userId, // This should be received
            @RequestParam("shelfId") Integer shelfId,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setPrice(price);
        product.setLatestPurchasePrice(latestPurchasePrice);
        product.setSellingPrice(sellingPrice);
        product.setCategory(category);
        product.setProductCompany(productCompany);
        product.setProductUnit(productUnit);
        product.setStockQuantity(stockQuantity);

        if (image != null && !image.isEmpty()) {
            product.setImage(image.getBytes());
        }

        // ✅ FIX: Properly set User relationship
        if (userId != null) {
            try {
                User user = userService.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                product.setUser(user);
                System.out.println("✅ User set successfully: " + user.getUserId()); // Debug log
            } catch (Exception e) {
                System.out.println("❌ Error setting user: " + e.getMessage()); // Debug log
                // You can choose to throw the exception or continue without user
                throw new RuntimeException("Failed to set user: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No userId provided, product will have no user association"); // Debug log
        }

        // ✅ Set Shelf
        com.example.decoration_backend_springboot.Model.Shelf shelf =
                shelfService.findById(shelfId)
                        .orElseThrow(() -> new RuntimeException("Shelf not found with id: " + shelfId));
        product.setShelf(shelf);

        Product savedProduct = productService.save(product);
        System.out.println("✅ Product saved with ID: " + savedProduct.getProductId()); // Debug log
        if (savedProduct.getUser() != null) {
            System.out.println("✅ Product user ID: " + savedProduct.getUser().getUserId()); // Debug log
        } else {
            System.out.println("❌ Product user is null"); // Debug log
        }

        return savedProduct;
    }

//    @PostMapping("/add/product")
//    public Product addProduct(
//            @RequestParam("productCode") String productCode,
//            @RequestParam("productName") String productName,
//            @RequestParam("productDescription") String productDescription,
//            @RequestParam("price") Double price,
//            @RequestParam("productCompany") String productCompany,
//            @RequestParam("category") String category,
//            @RequestParam("productUnit") String productUnit,
//            @RequestParam("stockQuantity") Integer stockQuantity,
//            @RequestParam(value = "userId", required = false) Integer userId,
//            @RequestParam("shelfId") Integer shelfId, // ✅ NEW
//            @RequestParam(value = "image", required = false) MultipartFile image
//    ) throws IOException {
//
//        Product product = new Product();
//        product.setProductCode(productCode);
//        product.setProductName(productName);
//        product.setProductDescription(productDescription);
//        product.setPrice(price);
//        product.setCategory(category);
//        product.setProductCompany(productCompany);
//        product.setProductUnit(productUnit);
//        product.setStockQuantity(stockQuantity);
//
//        if (image != null && !image.isEmpty()) {
//            product.setImage(image.getBytes());
//        }
//
//        // ✅ Set Vendor (if provided)
//        if (userId != null) {
//            User user = userService.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//            product.setUser(user);
//        }
//
//        // ✅ Set Shelf
//        com.example.decoration_backend_springboot.Model.Shelf shelf =
//                shelfService.findById(shelfId)
//                        .orElseThrow(() -> new RuntimeException("Shelf not found with id: " + shelfId));
//        product.setShelf(shelf);
//
//        return productService.save(product);
//    }

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
//    @DeleteMapping("delete/{product_id}")
//    public  ResponseEntity<?> deleteProduct(@PathVariable int product_id){
//
//        try {
//            productService.deleteById(product_id);
//            return new ResponseEntity<>("product was deleted successful",HttpStatus.OK);
//
//        }catch (Exception e){
//            return  new ResponseEntity<>("product not deleted",HttpStatus.BAD_REQUEST);
//        }
//
//    }
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

}



