package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Sale;
import com.example.decoration_backend_springboot.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Integer id) {
        return saleRepository.findById(id);
    }

    public List<Sale> getSalesByDate(LocalDate date) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getDate() != null && sale.getDate().equals(date))
                .toList();
    }

    public List<Sale> getSalesByCustomer(String customerName) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getCustomerName() != null &&
                        sale.getCustomerName().equalsIgnoreCase(customerName))
                .toList();
    }

    public List<Sale> getSalesByProduct(Integer productId) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getProductId() != null &&
                        sale.getProductId().equals(productId))
                .toList();
    }

    public Sale createSale(Sale sale) {
        sale.calculateTotalPrice(); // Ensure total price is calculated before saving
        return saleRepository.save(sale);
    }

    public Sale updateSale(Integer id, Sale saleDetails) {
        Optional<Sale> optionalSale = saleRepository.findById(id);

        if (optionalSale.isPresent()) {
            Sale existingSale = optionalSale.get();

            // Update fields
            if (saleDetails.getDate() != null) {
                existingSale.setDate(saleDetails.getDate());
            }
            if (saleDetails.getProduct() != null) {
                existingSale.setProduct(saleDetails.getProduct());
            }
            if (saleDetails.getQuantity() != null) {
                existingSale.setQuantity(saleDetails.getQuantity());
            }
            if (saleDetails.getUnitPrice() != null) {
                existingSale.setUnitPrice(saleDetails.getUnitPrice());
            }
            if (saleDetails.getCustomerName() != null) {
                existingSale.setCustomerName(saleDetails.getCustomerName());
            }

            // Recalculate total price
            existingSale.calculateTotalPrice();

            return saleRepository.save(existingSale);
        }
        return null; // Or handle as needed
    }

    public void deleteSale(Integer id) {
        saleRepository.deleteById(id);
    }

    public Double getTotalSalesAmount() {
        return saleRepository.findAll().stream()
                .mapToDouble(sale -> sale.getTotalPrice() != null ? sale.getTotalPrice() : 0.0)
                .sum();
    }

    public Double getTotalSalesAmountByDate(LocalDate date) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getDate() != null && sale.getDate().equals(date))
                .mapToDouble(sale -> sale.getTotalPrice() != null ? sale.getTotalPrice() : 0.0)
                .sum();
    }

    public Double getTotalSalesAmountByCustomer(String customerName) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getCustomerName() != null &&
                        sale.getCustomerName().equalsIgnoreCase(customerName))
                .mapToDouble(sale -> sale.getTotalPrice() != null ? sale.getTotalPrice() : 0.0)
                .sum();
    }

    public Integer getTotalQuantitySold() {
        return saleRepository.findAll().stream()
                .mapToInt(sale -> sale.getQuantity() != null ? sale.getQuantity() : 0)
                .sum();
    }

    public Integer getTotalQuantitySoldByProduct(Integer productId) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getProductId() != null && sale.getProductId().equals(productId))
                .mapToInt(sale -> sale.getQuantity() != null ? sale.getQuantity() : 0)
                .sum();
    }
}