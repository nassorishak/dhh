package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {

}
