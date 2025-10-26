//package com.example.decoration_backend_springboot.Service;
//
//import com.example.decoration_backend_springboot.Model.Shelf;
//import com.example.decoration_backend_springboot.Repository.ShelfRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ShelfService {
//
//    @Autowired
//    private ShelfRepository shelfRepository;
//
//    public List<Shelf> findAll() {
//        return shelfRepository.findAll();
//    }
//
//    public Optional<Shelf> findById(Integer shelfId) {
//        return shelfRepository.findById(Long.valueOf(shelfId));
//    }
//
//    public Shelf save(Shelf shelf) {
//        return shelfRepository.save(shelf);
//    }
//
//    public void deleteById(Integer shelfId) {
//        shelfRepository.deleteById(Long.valueOf(shelfId));
//    }
//
//    // ✅ FIXED: Complete implementation of getAllShelves
//    public List<Shelf> getAllShelves() {
//        return shelfRepository.findAllByOrderByShelfNameAsc();
//    }
//}


package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Shelf;
import com.example.decoration_backend_springboot.Repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;

    public List<Shelf> findAll() {
        return shelfRepository.findAll();
    }

    public Optional<Shelf> findById(Integer shelfId) {
        return shelfRepository.findById(Long.valueOf(shelfId));
    }

    // ✅ ADD THIS METHOD: getShelfById (same as findById but different name)
    public Optional<Shelf> getShelfById(Integer shelfId) {
        return shelfRepository.findById(Long.valueOf(shelfId));
    }

    public Shelf save(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    public void deleteById(Integer shelfId) {
        shelfRepository.deleteById(Long.valueOf(shelfId));
    }

    // ✅ FIXED: Complete implementation of getAllShelves
    public List<Shelf> getAllShelves() {
        return shelfRepository.findAllByOrderByShelfNameAsc();
    }

    // ✅ FIXED: Complete implementation of getDefaultShelf
    public Shelf getDefaultShelf() {
        // Try to get the first shelf from the database
        List<Shelf> allShelves = shelfRepository.findAllByOrderByShelfNameAsc();

        if (!allShelves.isEmpty()) {
            return allShelves.get(0); // Return first shelf as default
        }

        // If no shelves exist, create a default one
        Shelf defaultShelf = new Shelf();
        defaultShelf.setShelfName("Main Shelf");
        defaultShelf.setLocationDescription("Default storage location");

        return shelfRepository.save(defaultShelf);
    }

    // ✅ Additional helpful methods
    public boolean existsById(Integer shelfId) {
        return shelfRepository.existsById(Long.valueOf(shelfId));
    }

    public Shelf createDefaultShelfIfNotExists() {
        List<Shelf> shelves = getAllShelves();
        if (shelves.isEmpty()) {
            Shelf defaultShelf = new Shelf();
            defaultShelf.setShelfName("Main Shelf");
            defaultShelf.setLocationDescription("Primary storage location");
            return shelfRepository.save(defaultShelf);
        }
        return shelves.get(0);
    }

    public Optional<Shelf> findByShelfName(String shelfName) {
        return shelfRepository.findByShelfName(shelfName);
    }
}