package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findAllByOrderByShelfNameAsc();

    Optional<Shelf> findByShelfName(String shelfName);
}
