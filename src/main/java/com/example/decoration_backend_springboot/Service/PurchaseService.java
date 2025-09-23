package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Purchase;
import com.example.decoration_backend_springboot.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> getPurchaseById(Integer id) {
        return purchaseRepository.findById(id);
    }

    public Purchase createPurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase updatePurchase(Integer id, Purchase purchaseDetails) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);

        if (optionalPurchase.isPresent()) {
            Purchase existingPurchase = optionalPurchase.get();

            // Update fields
            if (purchaseDetails.getProduct() != null) {
                existingPurchase.setProduct(purchaseDetails.getProduct());
            }
            if (purchaseDetails.getQuantity() != null) {
                existingPurchase.setQuantity(purchaseDetails.getQuantity());
            }
            if (purchaseDetails.getPurchasePrice() != null) {
                existingPurchase.setPurchasePrice(purchaseDetails.getPurchasePrice());
            }
            if (purchaseDetails.getSupplier() != null) {
                existingPurchase.setSupplier(purchaseDetails.getSupplier());
            }
            if (purchaseDetails.getPurchaseDate() != null) {
                existingPurchase.setPurchaseDate(purchaseDetails.getPurchaseDate());
            }

            return purchaseRepository.save(existingPurchase);
        }
        return null;
    }

    public void deletePurchase(Integer id) {
        purchaseRepository.deleteById(id);
    }
}
