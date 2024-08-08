package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Service.PaymentService;
import com.example.decoration_backend_springboot.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/product")
public class ProductAPI {
    @Autowired
    private ProductService productService;

    @PostMapping("/add/product")
    public Product addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) throws IOException{
        Product product = new Product();
         product.setProductName(productName);
         product.setProductDescription(productDescription);
         product.setImage(image.getBytes());
         product.setCategory(category);
         product.setPrice(price);
         return productService.save(product);
    }

    @PostMapping("/api/product/add/product")
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
    public  ResponseEntity<?> updateProduct(@PathVariable int product_id ,@RequestBody Product product){
        try {
            if (productService.findById(product_id).isPresent()){

                Product product1 = productService.save(product);

                return  new ResponseEntity<>("product updated",HttpStatus.OK);


            }else{
                return new ResponseEntity<>("the product not updated",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return  new ResponseEntity<>("product updated required",HttpStatus.BAD_GATEWAY);
        }
    }
    @DeleteMapping("delete/{product_id}")
    public  ResponseEntity<?> deleteProduct(@PathVariable int product_id){

        try {
            productService.deleteById(product_id);
            return new ResponseEntity<>("product was deleted successful",HttpStatus.OK);

        }catch (Exception e){
            return  new ResponseEntity<>("product not deleted",HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("getByID/{product_id}")
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
}



