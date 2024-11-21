//package com.example.decoration_backend_springboot.API;
//import com.example.decoration_backend_springboot.Model.Order;
//import com.example.decoration_backend_springboot.Service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.Optional;
//@CrossOrigin("http://localhost:3000")
//@RestController
//@RequestMapping("api/orders")
//public class OrderAPI {
//    @Autowired
//    private OrderService orderService;
//
//    @GetMapping("get/orders")
//    public ResponseEntity<?> getOrder() {
//        try {
//            List<Order> OrderList = orderService.findAll();
//            if (OrderList.isEmpty()) {
//                return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);
//
//            } else {
//                return new ResponseEntity<>(OrderList, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("The  order was not added", HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PostMapping("/add/orders")
//    public ResponseEntity<?> createOrder(@RequestBody Order order) {
//        try {
//            Order savedOrder = orderService.save(order);
//            return new ResponseEntity<>("The order was added successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("The order was not added", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//
//    @PutMapping("update/{order_id}")
//    public ResponseEntity<?> updateOrder(@PathVariable int orderId, @RequestBody Order order) {
//
//        try {
//            if (orderService.findById(orderId).isPresent()) {
//                Order order1 = orderService.save(order);
//                return new ResponseEntity<>("The was successful updated",HttpStatus.OK);
//            }
//
//            else {
//                return new ResponseEntity<>("the order require updated ",HttpStatus.BAD_GATEWAY);
//            }
//        }
//
//        catch (Exception exception) {
//
//            return new ResponseEntity<>("the order not updated", HttpStatus.BAD_REQUEST);
//
//        }
//
//
//    }
//    @DeleteMapping("delete/{orderId}")
//    public  ResponseEntity<?> deleteOrder(@PathVariable int orderId){
//        try {
//            orderService.deleteById(orderId);
//            return  new ResponseEntity<>("The order was successful deleted",HttpStatus.OK);
//        }catch (Exception e){
//            return  new ResponseEntity<>("The order was not deleted",HttpStatus.BAD_REQUEST);
//        }
//
//    }
//    @GetMapping("getById/{orderId}")
//    public  ResponseEntity<?> getOrderById(@PathVariable int orderId){
//        try {
//            Optional<Order> OptionalOrder = orderService.findById(orderId);
//            if (OptionalOrder.isPresent()){
//                return  new ResponseEntity<>("The order was successful deleted",HttpStatus.OK);
//
//
//            }else {
//                return  new ResponseEntity<>(OptionalOrder,HttpStatus.BAD_REQUEST);
//
//            }
//        }catch (Exception e){
//            return  new ResponseEntity<>("the order was not updated",HttpStatus.BAD_GATEWAY);
//        }
//
//    }
//
//    @GetMapping("/count")
//    public ResponseEntity<Long> countAllOrders() {
//        Long count = orderService.countAllOrders();
//        return new ResponseEntity<>(count, HttpStatus.OK);
//    }
//
//
//
//
//    @GetMapping("/customer/{userId}")
//    public List<Order> getOrdersByCustomerId(@PathVariable int userId) {
//        return orderService.getOrdersByCustomerId(userId);
//    }
//
//
//
//
//    @PatchMapping("/cancel/{orderId}")
//    public ResponseEntity<String> cancelOrder(@PathVariable int orderId) {
//        String result = orderService.cancelOrder(orderId);
//        return ResponseEntity.ok(result);
//    }
//
//
//    // Endpoint to approve an order
//    @PatchMapping("/approve/{orderId}")
//    public ResponseEntity<String> approveOrder(@PathVariable int orderId) {
//        String result = orderService.approveOrder(orderId);
//        return ResponseEntity.ok(result);
//    }
//
//
//
//
//}
//
//
//
//




package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin("http://localhost:3000")
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
                return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);

            } else {
                return new ResponseEntity<>(OrderList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("The  order was not added", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.save(order);
            return new ResponseEntity<>("The order was added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("The order was not added", HttpStatus.BAD_REQUEST);
        }
    }




    @PutMapping("update/{order_id}")
    public ResponseEntity<?> updateOrder(@PathVariable int orderId, @RequestBody Order order) {

        try {
            if (orderService.findById(orderId).isPresent()) {
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
    @DeleteMapping("delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId) {
        try {
            orderService.deleteById(orderId);
            return new ResponseEntity<>("The order was successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("The order was not deleted", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("getById/{orderId}")
    public  ResponseEntity<?> getOrderById(@PathVariable int orderId){
        try {
            Optional<Order> OptionalOrder = orderService.findById(orderId);
            if (OptionalOrder.isPresent()){
                return  new ResponseEntity<>("The order was successful deleted",HttpStatus.OK);


            }else {
                return  new ResponseEntity<>(OptionalOrder,HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            return  new ResponseEntity<>("the order was not updated",HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllOrders() {
        Long count = orderService.countAllOrders();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }




    @GetMapping("/customer/{userId}")
    public List<Order> getOrdersByCustomerId(@PathVariable int userId) {
        return orderService.getOrdersByCustomerId(userId);
    }




    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable int orderId) {
        String result = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(result);
    }


    // Endpoint to approve an order
    @PatchMapping("/approve/{orderId}")
    public ResponseEntity<String> approveOrder(@PathVariable int orderId) {
        String result = orderService.approveOrder(orderId);
        return ResponseEntity.ok(result);
    }




}




