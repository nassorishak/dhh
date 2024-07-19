package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Service.PaymentService;
import com.example.decoration_backend_springboot.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product")
public class ProductAPI {
    @Autowired
    private ProductService productService;

    @PostMapping("/add/product")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product product1= productService.save(product);
            return  new ResponseEntity<>("product was successful posted", HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("product was not posted",HttpStatus.BAD_REQUEST);
        }

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

    @PutMapping("/update/{product_id}")
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



