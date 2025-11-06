////package com.example.decoration_backend_springboot.Service;
////import com.example.decoration_backend_springboot.Model.Order;
////import com.example.decoration_backend_springboot.Model.Payment;
////import com.example.decoration_backend_springboot.Repository.OrderRepository;
////import com.example.decoration_backend_springboot.Repository.PaymentRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////import java.util.List;
////import java.util.Optional;
////
////@Service
////public class PaymentService {
////    @Autowired
////    private PaymentRepository paymentRepository;
////
////    @Autowired
////    private OrderRepository orderRepository;
////
////    public PaymentService(PaymentRepository paymentRepository) {
////        this.paymentRepository = paymentRepository;
////
////    }
////
////    public Payment savePayment(Payment payment) {
////        return paymentRepository.save(payment);
////    }
////
////    public List<Payment> findAll() {
////        return  paymentRepository.findAll();
////    }
////
////    public Payment save(Payment payment) {
////        return paymentRepository.save(payment);
////    }
////
////    public void deleteById(int paymentId) {
////        paymentRepository.deleteById(paymentId);
////    }
////
////    public Optional<Payment> findById(int paymentId) {
////        return  paymentRepository.findById(paymentId);
////    }
////
////
////
////}
////
////
////
////
////
////
////
//
//
//package com.example.decoration_backend_springboot.Service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class PaymentService {
//
//    @Value("${mobile.payment.tigo.api.url:https://api.tigo.com/v1/payments}")
//    private String tigoApiUrl;
//
//    @Value("${mobile.payment.mpesa.api.url:https://api.mpesa.com/v1/payments}")
//    private String mpesaApiUrl;
//
//    @Value("${mobile.payment.airtel.api.url:https://api.airtel.com/v1/payments}")
//    private String airtelApiUrl;
//
//    @Value("${mobile.payment.halopesa.api.url:https://api.halopesa.com/v1/payments}")
//    private String halopesaApiUrl;
//
//    @Value("${mobile.payment.tigo.api.key:your_tigo_api_key}")
//    private String tigoApiKey;
//
//    @Value("${mobile.payment.mpesa.api.key:your_mpesa_api_key}")
//    private String mpesaApiKey;
//
//    @Value("${mobile.payment.airtel.api.key:your_airtel_api_key}")
//    private String airtelApiKey;
//
//    @Value("${mobile.payment.halopesa.api.key:your_halopesa_api_key}")
//    private String halopesaApiKey;
//
//    private final RestTemplate restTemplate;
//
//    public PaymentService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    // Real balance check with mobile network APIs
//    public Map<String, Object> checkRealBalance(String phoneNumber, String network) {
//        try {
//            String apiUrl = getApiUrl(network, "balance");
//            String apiKey = getApiKey(network);
//
//            // Prepare request payload
//            Map<String, String> requestPayload = new HashMap<>();
//            requestPayload.put("phoneNumber", phoneNumber);
//            requestPayload.put("serviceType", "BALANCE_CHECK");
//
//            // Prepare headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + apiKey);
//            headers.set("X-API-Key", apiKey);
//
//            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestPayload, headers);
//
//            // Make API call to mobile network
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    apiUrl, HttpMethod.POST, request, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map<String, Object> responseBody = response.getBody();
//
//                Map<String, Object> result = new HashMap<>();
//                result.put("success", true);
//                result.put("balance", responseBody.get("availableBalance"));
//                result.put("currency", "TZS");
//                result.put("phoneNumber", phoneNumber);
//                result.put("network", network);
//                result.put("accountStatus", responseBody.get("accountStatus"));
//                result.put("timestamp", System.currentTimeMillis());
//
//                return result;
//            } else {
//                throw new RuntimeException("Failed to check balance with " + network);
//            }
//
//        } catch (Exception e) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("success", false);
//            result.put("error", "Balance check failed: " + e.getMessage());
//            return result;
//        }
//    }
//
//    // Real payment processing with mobile network APIs
//    public Map<String, Object> processRealPayment(String phoneNumber, String pin, double amount,
//                                                  String network, String reference) {
//        try {
//            String apiUrl = getApiUrl(network, "payment");
//            String apiKey = getApiKey(network);
//
//            // Prepare request payload
//            Map<String, Object> requestPayload = new HashMap<>();
//            requestPayload.put("phoneNumber", phoneNumber);
//            requestPayload.put("pin", pin); // In production, this should be encrypted
//            requestPayload.put("amount", amount);
//            requestPayload.put("currency", "TZS");
//            requestPayload.put("reference", reference);
//            requestPayload.put("description", "Payment for Order: " + reference);
//            requestPayload.put("merchantId", getBusinessNumber(network));
//
//            // Prepare headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + apiKey);
//            headers.set("X-API-Key", apiKey);
//
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
//
//            // Make API call to mobile network
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    apiUrl, HttpMethod.POST, request, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map<String, Object> responseBody = response.getBody();
//
//                Map<String, Object> result = new HashMap<>();
//                result.put("success", true);
//                result.put("transactionId", responseBody.get("transactionId"));
//                result.put("status", responseBody.get("status"));
//                result.put("amount", amount);
//                result.put("phoneNumber", phoneNumber);
//                result.put("network", network);
//                result.put("timestamp", System.currentTimeMillis());
//
//                return result;
//            } else {
//                throw new RuntimeException("Payment processing failed with " + network);
//            }
//
//        } catch (Exception e) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("success", false);
//            result.put("error", "Payment processing failed: " + e.getMessage());
//            return result;
//        }
//    }
//
//    // Real transaction verification with mobile network APIs
//    public Map<String, Object> verifyRealTransaction(String transactionId, String network) {
//        try {
//            String apiUrl = getApiUrl(network, "verification") + "/" + transactionId;
//            String apiKey = getApiKey(network);
//
//            // Prepare headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + apiKey);
//            headers.set("X-API-Key", apiKey);
//
//            HttpEntity<String> request = new HttpEntity<>(headers);
//
//            // Make API call to mobile network
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    apiUrl, HttpMethod.GET, request, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map<String, Object> responseBody = response.getBody();
//
//                Map<String, Object> result = new HashMap<>();
//                result.put("success", true);
//                result.put("transactionId", transactionId);
//                result.put("status", responseBody.get("transactionStatus"));
//                result.put("amount", responseBody.get("amount"));
//                result.put("phoneNumber", responseBody.get("phoneNumber"));
//                result.put("timestamp", responseBody.get("transactionDate"));
//                result.put("verified", true);
//
//                return result;
//            } else {
//                throw new RuntimeException("Transaction verification failed");
//            }
//
//        } catch (Exception e) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("success", false);
//            result.put("error", "Transaction verification failed: " + e.getMessage());
//            return result;
//        }
//    }
//
//    // Helper methods
//    private String getApiUrl(String network, String endpoint) {
//        String baseUrl;
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                baseUrl = tigoApiUrl;
//                break;
//            case "mpesa":
//            case "vodacom":
//                baseUrl = mpesaApiUrl;
//                break;
//            case "airtel":
//            case "airtelmoney":
//                baseUrl = airtelApiUrl;
//                break;
//            case "halopesa":
//            case "halotel":
//                baseUrl = halopesaApiUrl;
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported network: " + network);
//        }
//
//        return baseUrl + "/" + endpoint;
//    }
//
//    private String getApiKey(String network) {
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return tigoApiKey;
//            case "mpesa":
//            case "vodacom":
//                return mpesaApiKey;
//            case "airtel":
//            case "airtelmoney":
//                return airtelApiKey;
//            case "halopesa":
//            case "halotel":
//                return halopesaApiKey;
//            default:
//                throw new IllegalArgumentException("Unsupported network: " + network);
//        }
//    }
//
//    private String getBusinessNumber(String network) {
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return "123456"; // Your Tigo business number
//            case "mpesa":
//            case "vodacom":
//                return "123456"; // Your M-Pesa business number
//            case "airtel":
//            case "airtelmoney":
//                return "123456"; // Your Airtel business number
//            case "halopesa":
//            case "halotel":
//                return "123456"; // Your Halotel business number
//            default:
//                return "123456";
//        }
//    }
//}

//package com.example.decoration_backend_springboot.Service;
//
//import com.example.decoration_backend_springboot.Model.Payment;
//import com.example.decoration_backend_springboot.Repository.PaymentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private  final PaymentRepository paymentRepository;
//
//    @Value("${mobile.payment.tigo.api.url:https://api.tigo.com/v1}")
//    private String tigoApiUrl;
//
//    @Value("${mobile.payment.mpesa.api.url:https://api.mpesa.com/v1}")
//    private String mpesaApiUrl;
//
//    @Value("${mobile.payment.airtel.api.url:https://api.airtel.com/v1}")
//    private String airtelApiUrl;
//
//    @Value("${mobile.payment.halopesa.api.url:https://api.halopesa.com/v1}")
//    private String halopesaApiUrl;
//
//    @Value("${mobile.payment.tigo.api.key:your_tigo_api_key}")
//    private String tigoApiKey;
//
//    @Value("${mobile.payment.mpesa.api.key:your_mpesa_api_key}")
//    private String mpesaApiKey;
//
//    @Value("${mobile.payment.airtel.api.key:your_airtel_api_key}")
//    private String airtelApiKey;
//
//    @Value("${mobile.payment.halopesa.api.key:your_halopesa_api_key}")
//    private String halopesaApiKey;
//
//    @Value("${payment.business.number.tigo:123456}")
//    private String tigoBusinessNumber;
//
//    @Value("${payment.business.number.mpesa:123456}")
//    private String mpesaBusinessNumber;
//
//    @Value("${payment.business.number.airtel:123456}")
//    private String airtelBusinessNumber;
//
//    @Value("${payment.business.number.halopesa:123456}")
//    private String halopesaBusinessNumber;
//
//    private final RestTemplate restTemplate;
//
//    public PaymentService(PaymentRepository paymentRepository, String tigoApiUrl, String mpesaApiUrl, String airtelApiUrl, String halopesaApiUrl, String tigoApiKey, String mpesaApiKey, String airtelApiKey, String halopesaApiKey, String tigoBusinessNumber, String mpesaBusinessNumber, String airtelBusinessNumber, String halopesaBusinessNumber, RestTemplate restTemplate) {
//        this.paymentRepository = paymentRepository;
//        this.tigoApiUrl = tigoApiUrl;
//        this.mpesaApiUrl = mpesaApiUrl;
//        this.airtelApiUrl = airtelApiUrl;
//        this.halopesaApiUrl = halopesaApiUrl;
//        this.tigoApiKey = tigoApiKey;
//        this.mpesaApiKey = mpesaApiKey;
//        this.airtelApiKey = airtelApiKey;
//        this.halopesaApiKey = halopesaApiKey;
//        this.tigoBusinessNumber = tigoBusinessNumber;
//        this.mpesaBusinessNumber = mpesaBusinessNumber;
//        this.airtelBusinessNumber = airtelBusinessNumber;
//        this.halopesaBusinessNumber = halopesaBusinessNumber;
//        this.restTemplate = restTemplate;
//    }
//
//    public static Map<String, Object> checkRealBalance(String phoneNumber, String network) {
//        Map<String, Object> result = new HashMap<>();
//
//        try {
//            String apiUrl = getApiUrl(network) + "/account/balance";
//            String apiKey = getApiKey(network);
//
//            // Prepare request payload
//            Map<String, Object> requestPayload = new HashMap<>();
//            requestPayload.put("msisdn", phoneNumber);
//            requestPayload.put("serviceType", "BALANCE_CHECK");
//            requestPayload.put("timestamp", System.currentTimeMillis());
//
//            // Prepare headers
//            HttpHeaders headers = createHeaders(apiKey);
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
//
//            // Make API call to mobile network
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    apiUrl, HttpMethod.POST, request, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map<String, Object> responseBody = response.getBody();
//
//                result.put("success", true);
//                result.put("balance", responseBody.get("availableBalance"));
//                result.put("currency", "TZS");
//                result.put("phoneNumber", phoneNumber);
//                result.put("network", network.toUpperCase());
//                result.put("accountStatus", responseBody.get("accountStatus"));
//                result.put("timestamp", System.currentTimeMillis());
//
//            } else {
//                result.put("success", false);
//                result.put("error", "Failed to check balance with " + network);
//            }
//
//        } catch (HttpClientErrorException e) {
//            result.put("success", false);
//            result.put("error", "Client error: " + e.getResponseBodyAsString());
//        } catch (HttpServerErrorException e) {
//            result.put("success", false);
//            result.put("error", "Server error from " + network + ": " + e.getResponseBodyAsString());
//        } catch (Exception e) {
//            result.put("success", false);
//            result.put("error", "Balance check failed: " + e.getMessage());
//        }
//
//        return result;
//    }
//
//    public Map<String, Object> processRealPayment(String phoneNumber, String pin, double amount,
//                                                  String network, String reference) {
//        Map<String, Object> result = new HashMap<>();
//
//        try {
//            String apiUrl = getApiUrl(network) + "/payment/request";
//            String apiKey = getApiKey(network);
//            String businessNumber = getBusinessNumber(network);
//
//            // Prepare request payload
//            Map<String, Object> requestPayload = new HashMap<>();
//            requestPayload.put("msisdn", phoneNumber);
//            requestPayload.put("pin", encryptPin(pin)); // PIN should be encrypted
//            requestPayload.put("amount", amount);
//            requestPayload.put("currency", "TZS");
//            requestPayload.put("reference", reference);
//            requestPayload.put("description", "Payment for Order: " + reference);
//            requestPayload.put("merchantId", businessNumber);
//            requestPayload.put("timestamp", System.currentTimeMillis());
//
//            // Prepare headers
//            HttpHeaders headers = createHeaders(apiKey);
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
//
//            // Make API call to mobile network
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    apiUrl, HttpMethod.POST, request, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map<String, Object> responseBody = response.getBody();
//
//                String status = (String) responseBody.get("status");
//                if ("SUCCESS".equals(status) || "COMPLETED".equals(status)) {
//                    result.put("success", true);
//                    result.put("transactionId", responseBody.get("transactionId"));
//                    result.put("status", status);
//                    result.put("amount", amount);
//                    result.put("phoneNumber", phoneNumber);
//                    result.put("network", network.toUpperCase());
//                    result.put("timestamp", System.currentTimeMillis());
//                } else {
//                    result.put("success", false);
//                    result.put("error", "Payment failed: " + responseBody.get("message"));
//                }
//
//            } else {
//                result.put("success", false);
//                result.put("error", "Payment processing failed with " + network);
//            }
//
//        } catch (HttpClientErrorException e) {
//            result.put("success", false);
//            result.put("error", "Client error: " + e.getResponseBodyAsString());
//        } catch (HttpServerErrorException e) {
//            result.put("success", false);
//            result.put("error", "Server error from " + network + ": " + e.getResponseBodyAsString());
//        } catch (Exception e) {
//            result.put("success", false);
//            result.put("error", "Payment processing failed: " + e.getMessage());
//        }
//
//        return result;
//    }
//
//    public Map<String, Object> verifyRealTransaction(String transactionId, String network) {
//        Map<String, Object> result = new HashMap<>();
//
//        try {
//            String apiUrl = getApiUrl(network) + "/payment/verify/" + transactionId;
//            String apiKey = getApiKey(network);
//
//            // Prepare headers
//            HttpHeaders headers = createHeaders(apiKey);
//            HttpEntity<String> request = new HttpEntity<>(headers);
//
//            // Make API call to mobile network
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    apiUrl, HttpMethod.GET, request, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map<String, Object> responseBody = response.getBody();
//
//                String status = (String) responseBody.get("transactionStatus");
//                if ("COMPLETED".equals(status) || "SUCCESS".equals(status)) {
//                    result.put("success", true);
//                    result.put("transactionId", transactionId);
//                    result.put("status", status);
//                    result.put("amount", responseBody.get("amount"));
//                    result.put("phoneNumber", responseBody.get("phoneNumber"));
//                    result.put("timestamp", responseBody.get("transactionDate"));
//                    result.put("verified", true);
//                } else {
//                    result.put("success", false);
//                    result.put("error", "Transaction not completed: " + status);
//                }
//
//            } else {
//                result.put("success", false);
//                result.put("error", "Transaction verification failed");
//            }
//
//        } catch (HttpClientErrorException e) {
//            result.put("success", false);
//            result.put("error", "Client error: " + e.getResponseBodyAsString());
//        } catch (HttpServerErrorException e) {
//            result.put("success", false);
//            result.put("error", "Server error from " + network + ": " + e.getResponseBodyAsString());
//        } catch (Exception e) {
//            result.put("success", false);
//            result.put("error", "Transaction verification failed: " + e.getMessage());
//        }
//
//        return result;
//    }
//
//    // ==================== HELPER METHODS ====================
//
//    private HttpHeaders createHeaders(String apiKey) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.set("X-API-Key", apiKey);
//        headers.set("Accept", "application/json");
//        return headers;
//    }
//
//    private String getApiUrl(String network) {
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return tigoApiUrl;
//            case "mpesa":
//            case "vodacom":
//                return mpesaApiUrl;
//            case "airtel":
//            case "airtelmoney":
//                return airtelApiUrl;
//            case "halopesa":
//            case "halotel":
//                return halopesaApiUrl;
//            default:
//                throw new IllegalArgumentException("Unsupported network: " + network);
//        }
//    }
//
//    private String getApiKey(String network) {
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return tigoApiKey;
//            case "mpesa":
//            case "vodacom":
//                return mpesaApiKey;
//            case "airtel":
//            case "airtelmoney":
//                return airtelApiKey;
//            case "halopesa":
//            case "halotel":
//                return halopesaApiKey;
//            default:
//                throw new IllegalArgumentException("Unsupported network: " + network);
//        }
//    }
//
//    private String getBusinessNumber(String network) {
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return tigoBusinessNumber;
//            case "mpesa":
//            case "vodacom":
//                return mpesaBusinessNumber;
//            case "airtel":
//            case "airtelmoney":
//                return airtelBusinessNumber;
//            case "halopesa":
//            case "halotel":
//                return halopesaBusinessNumber;
//            default:
//                return "123456";
//        }
//    }
//
//    private String encryptPin(String pin) {
//        // In production, use proper encryption
//        // This is a basic example - use AES encryption in real implementation
//        return "encrypted_" + pin; // Replace with real encryption
//    }
//
//    public Payment save(Payment payment) {
//        return  this.save(payment);
//    }
//
//    public Optional<Payment> findById(int payment_id) {
//        return findById(payment_id);
//    }
//
//    public void deleteById(int payment_id) {
//    }
//
//    public List<Payment> findAll() {
//        return  paymentRepository.findAll();
//    }
//}

package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Payment;
import com.example.decoration_backend_springboot.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

    // In-memory storage for simulation (remove in production)
    private final Map<String, Map<String, Object>> simulatedTransactions = new ConcurrentHashMap<>();
    private final Map<String, Double> simulatedBalances = new ConcurrentHashMap<>();

    private  final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        // Initialize some simulated balances for testing
        simulatedBalances.put("0651234567", 150000.0);
        simulatedBalances.put("0671234567", 75000.0);
        simulatedBalances.put("0711234567", 250000.0);
        simulatedBalances.put("0751234567", 50000.0);
        simulatedBalances.put("0761234567", 100000.0);
    }

    /**
     * Process REAL mobile payment (Simulated for now)
     */
    public Map<String, Object> processRealPayment(String phoneNumber, String pin,
                                                  double amount, String network,
                                                  String controlNumber) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate inputs
            if (!isValidTanzanianPhoneNumber(phoneNumber)) {
                response.put("success", false);
                response.put("error", "Invalid Tanzanian mobile number");
                return response;
            }

            if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
                response.put("success", false);
                response.put("error", "Invalid 4-digit PIN");
                return response;
            }

            if (amount <= 0) {
                response.put("success", false);
                response.put("error", "Invalid amount");
                return response;
            }

            // Simulate API call delay
            Thread.sleep(2000);

            // Check if phone number has sufficient balance
            double currentBalance = simulatedBalances.getOrDefault(phoneNumber, 10000.0);
            if (currentBalance < amount) {
                response.put("success", false);
                response.put("error", "Insufficient balance. Available: Tsh " +
                        String.format("%,.0f", currentBalance));
                return response;
            }

            // Simulate PIN verification (in real API, this would be done by Tigo)
            if (!simulatePinVerification(phoneNumber, pin)) {
                response.put("success", false);
                response.put("error", "Invalid PIN");
                return response;
            }

            // Generate transaction ID
            String transactionId = generateTransactionId(network);

            // Process payment (deduct amount from balance)
            double newBalance = currentBalance - amount;
            simulatedBalances.put(phoneNumber, newBalance);

            // Store transaction details
            Map<String, Object> transaction = new HashMap<>();
            transaction.put("transactionId", transactionId);
            transaction.put("phoneNumber", phoneNumber);
            transaction.put("amount", amount);
            transaction.put("network", network);
            transaction.put("controlNumber", controlNumber);
            transaction.put("status", "COMPLETED");
            transaction.put("timestamp", System.currentTimeMillis());
            simulatedTransactions.put(transactionId, transaction);

            // Prepare success response
            response.put("success", true);
            response.put("transactionId", transactionId);
            response.put("amount", amount);
            response.put("phoneNumber", phoneNumber);
            response.put("network", network.toUpperCase());
            response.put("controlNumber", controlNumber);
            response.put("balance", newBalance);
            response.put("message", "Payment processed successfully");
            response.put("timestamp", System.currentTimeMillis());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            response.put("success", false);
            response.put("error", "Payment processing interrupted");
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Payment processing failed: " + e.getMessage());
        }

        return response;
    }

    /**
     * Check REAL balance (Simulated for now)
     */
    public Map<String, Object> checkRealBalance(String phoneNumber, String network) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate phone number
            if (!isValidTanzanianPhoneNumber(phoneNumber)) {
                response.put("success", false);
                response.put("error", "Invalid Tanzanian mobile number");
                return response;
            }

            // Simulate API call delay
            Thread.sleep(1500);

            // Get simulated balance (in real API, this would come from Tigo)
            double balance = simulatedBalances.getOrDefault(phoneNumber,
                    Math.random() * 100000 + 1000); // Random balance if not found

            // Prepare response
            response.put("success", true);
            response.put("balance", balance);
            response.put("currency", "TZS");
            response.put("phoneNumber", phoneNumber);
            response.put("network", network.toUpperCase());
            response.put("message", "Balance retrieved successfully");
            response.put("timestamp", System.currentTimeMillis());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            response.put("success", false);
            response.put("error", "Balance check interrupted");
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Balance check failed: " + e.getMessage());
        }

        return response;
    }

    /**
     * Verify REAL transaction (Simulated for now)
     */
    public Map<String, Object> verifyRealTransaction(String transactionId, String network) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate inputs
            if (transactionId == null || transactionId.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "Transaction ID is required");
                return response;
            }

            // Simulate API call delay
            Thread.sleep(1000);

            // Check if transaction exists in our simulation
            Map<String, Object> transaction = simulatedTransactions.get(transactionId);

            if (transaction != null) {
                response.put("success", true);
                response.put("transactionId", transactionId);
                response.put("status", transaction.get("status"));
                response.put("amount", transaction.get("amount"));
                response.put("phoneNumber", transaction.get("phoneNumber"));
                response.put("network", network.toUpperCase());
                response.put("verified", true);
                response.put("message", "Transaction verified successfully");
                response.put("timestamp", transaction.get("timestamp"));
            } else {
                // Simulate random verification for unknown transactions
                boolean isVerified = Math.random() > 0.3; // 70% success rate

                if (isVerified) {
                    response.put("success", true);
                    response.put("transactionId", transactionId);
                    response.put("status", "COMPLETED");
                    response.put("verified", true);
                    response.put("message", "Transaction verified successfully");
                } else {
                    response.put("success", false);
                    response.put("error", "Transaction not found or failed");
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            response.put("success", false);
            response.put("error", "Verification interrupted");
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Verification failed: " + e.getMessage());
        }

        return response;
    }

    /**
     * Get payment instructions for specific network
     */
    public Map<String, Object> getPaymentInstructions(String network) {
        Map<String, Object> instructions = new HashMap<>();

        String businessNumber = "";
        String ussdCode = "";
        String steps = "";

        switch (network.toLowerCase()) {
            case "tigo":
            case "tigopesa":
                businessNumber = "123456"; // Simulated business number
                ussdCode = "*150*01#";
                steps = "1. Dial *150*01# on your Tigo Pesa\n" +
                        "2. Select 'Lipa kwa Simu' from the menu\n" +
                        "3. Enter Business Number: " + businessNumber + "\n" +
                        "4. Enter Account Number (Your Control Number)\n" +
                        "5. Enter Amount\n" +
                        "6. Enter your Tigo Pesa PIN and press OK to confirm\n" +
                        "7. You will receive a confirmation message";
                break;

            case "mpesa":
            case "vodacom":
                businessNumber = "123457";
                ussdCode = "*150*00#";
                steps = "1. Dial *150*00# on your M-Pesa\n" +
                        "2. Select 'Lipa Bill' from the menu\n" +
                        "3. Enter Business Number: " + businessNumber + "\n" +
                        "4. Enter Account Number (Your Control Number)\n" +
                        "5. Enter Amount\n" +
                        "6. Enter your M-Pesa PIN and press OK to confirm";
                break;

            case "airtel":
            case "airtelmoney":
                businessNumber = "123458";
                ussdCode = "*150*60#";
                steps = "1. Dial *150*60# on your Airtel Money\n" +
                        "2. Select 'Make Payment' from the menu\n" +
                        "3. Select 'Pay Bill'\n" +
                        "4. Enter Business Number: " + businessNumber + "\n" +
                        "5. Enter Account Number (Your Control Number)\n" +
                        "6. Enter Amount\n" +
                        "7. Enter your Airtel Money PIN and press OK to confirm";
                break;

            case "halopesa":
            case "halotel":
                businessNumber = "123459";
                ussdCode = "*150*99#";
                steps = "1. Dial *150*99# on your Halotel Pesa\n" +
                        "2. Select 'Pay Bill' from the menu\n" +
                        "3. Enter Business Number: " + businessNumber + "\n" +
                        "4. Enter Account Number (Your Control Number)\n" +
                        "5. Enter Amount\n" +
                        "6. Enter your Halotel Pesa PIN and press OK to confirm";
                break;

            default:
                instructions.put("success", false);
                instructions.put("error", "Unsupported network: " + network);
                return instructions;
        }

        instructions.put("success", true);
        instructions.put("ussd", ussdCode);
        instructions.put("businessNumber", businessNumber);
        instructions.put("businessName", "Decoration Store");
        instructions.put("steps", steps);
        instructions.put("network", network.toUpperCase());

        return instructions;
    }

    /**
     * Validate Tanzanian phone number
     */
    public boolean isValidTanzanianPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;

        // Remove any spaces, dashes, or country code
        String cleaned = phoneNumber.replaceAll("[\\s\\-+]", "");

        // If starts with 255, convert to 0
        if (cleaned.startsWith("255") && cleaned.length() == 12) {
            cleaned = "0" + cleaned.substring(3);
        }

        // Tanzanian phone number regex
        String phoneRegex = "^(065|067|071|075|076|074|068|069|062|073|077)\\d{7}$";
        return cleaned.matches(phoneRegex);
    }

    /**
     * Get supported networks
     */
    public Map<String, Object> getSupportedNetworks() {
        Map<String, Object> networks = new HashMap<>();
        networks.put("tigo", "Tigo Pesa");
        networks.put("mpesa", "M-Pesa (Vodacom)");
        networks.put("airtel", "Airtel Money");
        networks.put("halopesa", "Halopesa");
        networks.put("default", "tigo");
        return networks;
    }

    // ==================== PRIVATE HELPER METHODS ====================

    private String generateTransactionId(String network) {
        String prefix;
        switch (network.toLowerCase()) {
            case "tigo": case "tigopesa":
                prefix = "TGO";
                break;
            case "mpesa": case "vodacom":
                prefix = "MPE";
                break;
            case "airtel": case "airtelmoney":
                prefix = "ART";
                break;
            case "halopesa": case "halotel":
                prefix = "HAL";
                break;
            default:
                prefix = "MOB";
        }

        return prefix + System.currentTimeMillis() +
                String.format("%04d", (int)(Math.random() * 10000));
    }

    private boolean simulatePinVerification(String phoneNumber, String pin) {
        // Simple PIN verification simulation
        // In real implementation, this would be handled by the mobile money provider
        return pin.length() == 4 && pin.matches("\\d{4}");
    }

    // Method to reset simulation data (for testing)
    public void resetSimulationData() {
        simulatedTransactions.clear();
        simulatedBalances.clear();

        // Reinitialize balances
        simulatedBalances.put("0651234567", 150000.0);
        simulatedBalances.put("0671234567", 75000.0);
        simulatedBalances.put("0711234567", 250000.0);
        simulatedBalances.put("0751234567", 50000.0);
        simulatedBalances.put("0761234567", 100000.0);
    }

    // Method to get simulation data (for testing/debugging)
    public Map<String, Object> getSimulationData() {
        Map<String, Object> data = new HashMap<>();
        data.put("transactions", simulatedTransactions);
        data.put("balances", simulatedBalances);
        return data;
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return  paymentRepository.findAll();
    }

    public Optional<Payment> findById(int payment_id) {
        return paymentRepository.findById(payment_id);
    }

    public void deleteById(int payment_id) {
    }
}