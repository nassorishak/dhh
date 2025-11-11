////package com.example.decoration_backend_springboot.Service;
////import com.example.decoration_backend_springboot.Model.Customer;
////import com.example.decoration_backend_springboot.Repository.CustomerRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import java.util.List;
////import java.util.Optional;
////
////@Service
////public class CustomerService {
////    @Autowired
////    private  CustomerRepository customerRepository;
////    public List<Customer> findAll() {
////        return customerRepository.findAll();
////    }
////
////    public Customer save(Customer customer) {
////
////        return  customerRepository.save(customer);
////    }
////
////
////    public Optional<Customer> findById(int customerId) {
////        return  customerRepository.findById(customerId);
////    }
////
////    public void deleteById(int customer_id) {
////        customerRepository.deleteById(customer_id);
////    }
////
////    public Customer findCustomerByIdentifier(String identifier) {
////        // For instance, here we assume the identifier is the customer's email
////        return customerRepository.findByEmail(identifier);
////    }
////}
////
//
//package com.example.decoration_backend_springboot.Service;
//
//import com.example.decoration_backend_springboot.Model.Customer;
//import com.example.decoration_backend_springboot.Model.Enum.Role;
//import com.example.decoration_backend_springboot.Repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomerService {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public CustomerService(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
//    /**
//     * Find all customers
//     */
//    public List<Customer> findAll() {
//        return customerRepository.findAll();
//    }
//
//    /**
//     * Save customer with default role handling
//     */
//    public Customer save(Customer customer) {
//        // Set default role if not provided
//        if (customer.getRole() == null) {
//            customer.setRole(Role.valueOf("CUSTOMER"));
//        }
//        return customerRepository.save(customer);
//    }
//
//    /**
//     * Create customer with all required fields
//     */
//    public Customer createCustomer(String email, String password, String name, String custAddress, String phone) {
//        Customer customer = new Customer(email, password, "CUSTOMER", name, custAddress, phone);
//        return customerRepository.save(customer);
//    }
//
//    /**
//     * Find customer by ID
//     */
//    public Optional<Customer> findById(int customerId) {
//        return customerRepository.findById(customerId);
//    }
//
//    /**
//     * Delete customer by ID
//     */
//    public void deleteById(int customer_id) {
//        customerRepository.deleteById(customer_id);
//    }
//
//    /**
//     * Find customer by email
//     */
//    public Customer findCustomerByIdentifier(String identifier) {
//        return customerRepository.findByEmail(identifier);
//    }
//
//    /**
//     * Find customer by email (direct method)
//     */
//    public Customer findByEmail(String email) {
//        return customerRepository.findByEmail(email);
//    }
//
//    /**
//     * Check if customer exists by email
//     */
//    public boolean existsByEmail(String email) {
//        return customerRepository.findByEmail(email) != null;
//    }
//
//    /**
//     * Update customer name
//     */
//    public Customer updateName(int customerId, String name) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if (optionalCustomer.isPresent()) {
//            Customer customer = optionalCustomer.get();
//            customer.Name(name);
//            return customerRepository.save(customer);
//        }
//        return null;
//    }
//
//    /**
//     * Update customer address
//     */
//    public Customer updateAddress(int customerId, String address) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if (optionalCustomer.isPresent()) {
//            Customer customer = optionalCustomer.get();
//            customer.setCustAddress(address);
//            return customerRepository.save(customer);
//        }
//        return null;
//    }
//
//    /**
//     * Update customer phone
//     */
//    public Customer updatePhone(int customerId, String phone) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if (optionalCustomer.isPresent()) {
//            Customer customer = optionalCustomer.get();
//            customer.setPhone(phone);
//            return customerRepository.save(customer);
//        }
//        return null;
//    }
//
//    /**
//     * Update customer email
//     */
//    public Customer updateEmail(int customerId, String email) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if (optionalCustomer.isPresent()) {
//            Customer customer = optionalCustomer.get();
//            customer.setEmail(email);
//            return customerRepository.save(customer);
//        }
//        return null;
//    }
//
//    /**
//     * Fix NULL fields for existing customer
//     */
//    public Customer fixNullFields(int customerId, String name, String address, String phone) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if (optionalCustomer.isPresent()) {
//            Customer customer = optionalCustomer.get();
//
//            // Fix NULL fields with provided values or defaults
//            if (customer.getName() == null) {
//                customer.setName(name != null ? name : "Unknown Customer");
//            }
//            if (customer.getCustAddress() == null) {
//                customer.setCustAddress(address != null ? address : "Address not specified");
//            }
//            if (customer.getPhone() == null) {
//                customer.setPhone(phone != null ? phone : "Phone not specified");
//            }
//            if (customer.getRole() == null) {
//                customer.setRole(Role.valueOf("CUSTOMER"));
//            }
//
//            return customerRepository.save(customer);
//        }
//        return null;
//    }
//
//    /**
//     * Get customer count
//     */
//    public long getCustomerCount() {
//        return customerRepository.count();
//    }
//
//    /**
//     * Check if customer exists by ID
//     */
//    public boolean existsById(int customerId) {
//        return customerRepository.existsById(customerId);
//    }
//
//    public List<Customer> findByRole(Role role) {
//        return  this.findByRole(role);
//    }
//
//    public List<Customer> findByNameContaining(String name) {
//        return customerRepository.findAll();
//    }
//}
package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import com.example.decoration_backend_springboot.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    /**
     * Find all customers
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Save customer with default role handling
     */
    public Customer save(Customer customer) {
        // Set default role if not provided
        if (customer.getRole() == null) {
            customer.setRole(Role.CUSTOMER); // Use enum directly instead of valueOf
        }
        return customerRepository.save(customer);
    }

    /**
     * Create customer with all required fields
     */
    public Customer createCustomer(String email, String password, String name, String custAddress, String phone) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setCustAddress(custAddress);
        customer.setPhone(phone);
        customer.setRole(Role.CUSTOMER);

        return customerRepository.save(customer);
    }

    /**
     * Find customer by ID
     */
    public Optional<Customer> findById(int customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Delete customer by ID
     */
    public void deleteById(int customer_id) {
        customerRepository.deleteById(customer_id);
    }

    /**
     * Find customer by email
     */
    public Customer findCustomerByIdentifier(String identifier) {
        return customerRepository.findByEmail(identifier);
    }

    /**
     * Find customer by email (direct method)
     */
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * Check if customer exists by email
     */
    public boolean existsByEmail(String email) {
        return customerRepository.findByEmail(email) != null;
    }

    /**
     * Update customer name - FIXED: changed customer.Name(name) to customer.setName(name)
     */
    public Customer updateName(int customerId, String name) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(name); // FIXED: Changed from customer.Name(name)
            return customerRepository.save(customer);
        }
        return null;
    }

    /**
     * Update customer address
     */
    public Customer updateAddress(int customerId, String address) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setCustAddress(address);
            return customerRepository.save(customer);
        }
        return null;
    }

    /**
     * Update customer phone
     */
    public Customer updatePhone(int customerId, String phone) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setPhone(phone);
            return customerRepository.save(customer);
        }
        return null;
    }

    /**
     * Update customer email
     */
    public Customer updateEmail(int customerId, String email) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setEmail(email);
            return customerRepository.save(customer);
        }
        return null;
    }

    /**
     * Fix NULL fields for existing customer
     */
    public Customer fixNullFields(int customerId, String name, String address, String phone) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            // Fix NULL fields with provided values or defaults
            if (customer.getName() == null) {
                customer.setName(name != null ? name : "Unknown Customer");
            }
            if (customer.getCustAddress() == null) {
                customer.setCustAddress(address != null ? address : "Address not specified");
            }
            if (customer.getPhone() == null) {
                customer.setPhone(phone != null ? phone : "Phone not specified");
            }
            if (customer.getRole() == null) {
                customer.setRole(Role.CUSTOMER);
            }

            return customerRepository.save(customer);
        }
        return null;
    }

    /**
     * Get customer count
     */
    public long getCustomerCount() {
        return customerRepository.count();
    }

    /**
     * Check if customer exists by ID
     */
    public boolean existsById(int customerId) {
        return customerRepository.existsById(customerId);
    }

    /**
     * Find customers by role - FIXED: removed recursive call
     */
    public List<Customer> findByRole(Role role) {
        return customerRepository.findByRole(role); // FIXED: Call repository method
    }

    /**
     * Find customers by name containing - FIXED: should call repository method
     */
    public List<Customer> findByNameContaining(String name) {
        return customerRepository.findByNameContaining(name); // FIXED: Call repository method
    }
}