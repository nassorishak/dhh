//package com.example.decoration_backend_springboot.API;
//
//import com.example.decoration_backend_springboot.Model.Shelf;
//import com.example.decoration_backend_springboot.Service.ShelfService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api/shelves")
//public class ShelfAPI {
//
//    @Autowired
//    private ShelfService shelfService;
//
//    @GetMapping("/list-shelves")
//    public ResponseEntity<List<Shelf>> getAllShelves() {
//        List<Shelf> shelves = shelfService.findAll();
//        return ResponseEntity.ok(shelves);
//    }
//
//    @PostMapping("/add/shelves")
//    public ResponseEntity<Shelf> addShelf(@RequestBody Shelf shelf) {
//        Shelf savedShelf = shelfService.save(shelf);
//        return ResponseEntity.ok(savedShelf);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteShelf(@PathVariable Integer id) {
//        shelfService.deleteById(id);
//        return ResponseEntity.ok("Shelf deleted successfully");
//    }
//}
package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Shelf;
import com.example.decoration_backend_springboot.Service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/shelves")
public class ShelfAPI {

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list-shelves")
    public ResponseEntity<List<Shelf>> getAllShelves() {
        List<Shelf> shelves = shelfService.findAll();
        return ResponseEntity.ok(shelves);
    }

    @PostMapping("/add/shelves")
    public ResponseEntity<Shelf> addShelf(@RequestBody Shelf shelf) {
        Shelf savedShelf = shelfService.save(shelf);
        return ResponseEntity.ok(savedShelf);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Shelf> updateShelf(@PathVariable Integer id, @RequestBody Shelf shelfDetails) {
        Optional<Shelf> optionalShelf = shelfService.findById(id);

        if (optionalShelf.isPresent()) {
            Shelf shelf = optionalShelf.get();
            shelf.setShelfName(shelfDetails.getShelfName());
            shelf.setLocationDescription(shelfDetails.getLocationDescription());

            Shelf updatedShelf = shelfService.save(shelf);
            return ResponseEntity.ok(updatedShelf);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShelf(@PathVariable Integer id) {
        shelfService.deleteById(id);
        return ResponseEntity.ok("Shelf deleted successfully");
    }

}
