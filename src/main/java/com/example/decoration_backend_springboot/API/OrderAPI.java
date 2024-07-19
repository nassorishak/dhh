package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("api/orders")
public class OrderAPI {
    @Autowired
    private OrderService orderService;

    @GetMapping("get/orders")
    public ResponseEntity<?> getOrder() {
        try {
            List<Order> OrderList = orderService.findAll();
            if (OrderList.isEmpty()) {
                return new ResponseEntity<>("The order added successful", HttpStatus.OK);

            } else {
                return new ResponseEntity<>(OrderList, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("The  order was not added", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {

        try {
            Order order1 = orderService.save(order);
            return new ResponseEntity<>("The order was added successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("the order was not added", HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("update/{order_id}")
    public ResponseEntity<?> updateOrder(@PathVariable int order_id, @RequestBody Order order) {

        try {
            if (orderService.findById(order_id).isPresent()) {
                Order order1 = orderService.save(order);
                return new ResponseEntity<>("The was successful updated",HttpStatus.OK);
            }

            else {
                return new ResponseEntity<>("the order require updated ",HttpStatus.BAD_GATEWAY);
            }
        }

        catch (Exception exception) {

            return new ResponseEntity<>("the order not updated", HttpStatus.BAD_REQUEST);

        }


    }
    @DeleteMapping("delete/{order_id}")
    public  ResponseEntity<?> deleteOrder(@PathVariable int order_id){
        try {
            orderService.deleteById(order_id);
            return  new ResponseEntity<>("The order was successful deleted",HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("The order was not deleted",HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("getById/{order_id}")
    public  ResponseEntity<?> getOrderById(@PathVariable int order_id){
        try {
            Optional<Order> OptionalOrder = orderService.findById(order_id);
            if (OptionalOrder.isPresent()){
                return  new ResponseEntity<>("The order was successful deleted",HttpStatus.OK);


            }else {
                return  new ResponseEntity<>(OptionalOrder,HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            return  new ResponseEntity<>("the order was not updated",HttpStatus.BAD_GATEWAY);
        }

    }

}


