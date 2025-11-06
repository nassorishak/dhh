
package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:ishaknassor09@gmail.com}")
    private String fromEmail;

    // Payment configuration with default values
    @Value("${payment.business.number.tigo:123456}")
    private String tigoBusinessNumber;

    @Value("${payment.business.number.mpesa:123456}")
    private String mpesaBusinessNumber;

    @Value("${payment.business.number.airtel:123456}")
    private String airtelBusinessNumber;

    @Value("${payment.business.number.halopesa:123456}")
    private String halopesaBusinessNumber;

    @Value("${company.name:Decoration Store}")
    private String companyName;

    @Value("${company.email:ishaknassor09@gmail.com}")
    private String companyEmail;

    @Value("${company.phone:+255 123 456 789}")
    private String companyPhone;

    public void sendControlNumberEmail(Order order) {
        sendControlNumberEmail(order, "tigo");
    }

    public void sendControlNumberEmail(Order order, String preferredNetwork) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, companyName);
            helper.setTo(order.getCustomerEmail());
            helper.setSubject("🎉 Order Confirmation - Control Number: " + order.getControlNumber());

            String emailContent = buildEmailContent(order, preferredNetwork);
            helper.setText(emailContent, true);

            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + order.getCustomerEmail());

        } catch (Exception e) {
            System.err.println("❌ Failed to send email to " + order.getCustomerEmail() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendControlNumberEmailWithAllNetworks(Order order) {
        sendControlNumberEmail(order, null);
    }

    private String buildEmailContent(Order order, String network) {
        if (network == null) {
            network = "tigo";
        }

        String businessNumber = getBusinessNumber(network);
        String businessName = getBusinessName(network);
        String networkName = getNetworkDisplayName(network);
        String instructions = getPaymentInstructions(network);

        // Safe methods to get customer and product names
        String customerName = getCustomerName(order);
        String productName = getProductName(order);
        String formattedAmount = formatAmount(order.getTotalAmount());

        // FIXED: Using regular string concatenation instead of text blocks
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<style>")
                .append("body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; background: #f5f5f5; }")
                .append(".container { background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }")
                .append(".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }")
                .append(".content { padding: 30px; }")
                .append(".control-number { background: #fff3cd; padding: 20px; text-align: center; font-size: 24px; font-weight: bold; margin: 25px 0; border: 2px dashed #ffc107; border-radius: 10px; color: #856404; }")
                .append(".payment-section { background: #f8f9fa; padding: 20px; border-radius: 10px; margin: 20px 0; border-left: 4px solid #667eea; }")
                .append(".step { margin: 12px 0; padding: 10px; background: white; border-radius: 5px; border-left: 3px solid #28a745; }")
                .append(".network-badge { display: inline-block; background: #667eea; color: white; padding: 5px 10px; border-radius: 15px; font-size: 12px; font-weight: bold; margin-bottom: 10px; }")
                .append(".order-details { background: white; padding: 20px; border-radius: 10px; margin: 20px 0; border: 1px solid #e9ecef; }")
                .append(".detail-row { display: flex; justify-content: space-between; margin: 10px 0; padding: 8px 0; border-bottom: 1px solid #eee; }")
                .append(".footer { text-align: center; padding: 20px; color: #666; font-size: 14px; background: #f1f1f1; }")
                .append(".important-note { background: #fff3cd; padding: 15px; border-radius: 5px; border-left: 4px solid #ffc107; margin: 15px 0; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class=\"container\">")
                .append("<div class=\"header\">")
                .append("<h1>📦 Order Confirmed!</h1>")
                .append("<p>Thank you for your purchase!</p>")
                .append("</div>")
                .append("<div class=\"content\">")
                .append("<p>Dear <strong>").append(customerName).append("</strong>,</p>")
                .append("<p>Your order has been successfully received and is being processed. Please use the control number below to complete your payment via <strong>").append(networkName).append("</strong>.</p>")
                .append("<div class=\"control-number\">")
                .append("🔒 Control Number: ").append(order.getControlNumber())
                .append("</div>")
                .append("<div class=\"payment-section\">")
                .append("<div class=\"network-badge\">").append(networkName).append(" PAYMENT</div>")
                .append("<h3>💰 Payment Instructions:</h3>")
                .append(formatInstructions(instructions))
                .append("</div>")
                .append("<div class=\"important-note\">")
                .append("<strong>📋 Important Payment Details:</strong><br>")
                .append("• Business Number: <strong>").append(businessNumber).append("</strong><br>")
                .append("• Account Number: <strong>").append(order.getControlNumber()).append("</strong> (Your Control Number)<br>")
                .append("• Amount: <strong>Tsh ").append(formattedAmount).append("</strong><br>")
                .append("• Business Name: <strong>").append(businessName).append("</strong>")
                .append("</div>")
                .append("<div class=\"order-details\">")
                .append("<h3>📋 Order Details:</h3>")
                .append("<div class=\"detail-row\">")
                .append("<span>Product:</span>")
                .append("<span><strong>").append(productName).append("</strong></span>")
                .append("</div>")
                .append("<div class=\"detail-row\">")
                .append("<span>Quantity:</span>")
                .append("<span><strong>").append(order.getQuantity()).append("</strong></span>")
                .append("</div>")
                .append("<div class=\"detail-row\">")
                .append("<span>Order Type:</span>")
                .append("<span><strong>").append(order.getOrderType()).append("</strong></span>")
                .append("</div>")
                .append("<div class=\"detail-row\">")
                .append("<span>Size:</span>")
                .append("<span><strong>").append(order.getSize()).append("</strong></span>")
                .append("</div>")
                .append("<div class=\"detail-row\">")
                .append("<span>Total Amount:</span>")
                .append("<span><strong>Tsh ").append(formattedAmount).append("</strong></span>")
                .append("</div>")
                .append("<div class=\"detail-row\">")
                .append("<span>Order Status:</span>")
                .append("<span><strong>").append(order.getStatus()).append("</strong></span>")
                .append("</div>")
                .append("</div>")
                .append("<p><strong>⚠️ Important:</strong> Your order will be processed once payment is confirmed. You will receive SMS confirmation from your mobile network after successful payment.</p>")
                .append("</div>")
                .append("<div class=\"footer\">")
                .append("<p>Need help? Contact our support team:</p>")
                .append("<p>📞 ").append(companyPhone).append(" | ✉️ ").append(companyEmail).append("</p>")
                .append("<p>Thank you for choosing <strong>").append(companyName).append("</strong>! 🎉</p>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return htmlContent.toString();
    }

    // Safe methods to get customer and product names
    private String getCustomerName(Order order) {
        if (order.getCustomer() != null && order.getCustomer().getName() != null) {
            return order.getCustomer().getName();
        }
        return "Valued Customer";
    }

    private String getProductName(Order order) {
        if (order.getProduct() != null && order.getProduct().getProductName() != null) {
            return order.getProduct().getProductName();
        }
        return "Product";
    }

    private String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return String.format("%,.2f", amount);
    }

    // Helper methods for payment configuration
    private String getBusinessNumber(String network) {
        if (network == null) {
            network = "tigo";
        }

        switch (network.toLowerCase()) {
            case "tigo":
            case "tigopesa":
                return tigoBusinessNumber;
            case "mpesa":
            case "vodacom":
                return mpesaBusinessNumber;
            case "airtel":
            case "airtelmoney":
                return airtelBusinessNumber;
            case "halopesa":
            case "halotel":
                return halopesaBusinessNumber;
            default:
                return tigoBusinessNumber;
        }
    }

    private String getBusinessName(String network) {
        return companyName;
    }

    private String getPaymentInstructions(String network) {
        if (network == null) {
            network = "tigo";
        }

        switch (network.toLowerCase()) {
            case "tigo":
            case "tigopesa":
                return "1. Dial *150*01# on your Tigo Pesa\\n2. Select 'Lipa kwa Simu'\\n3. Enter Business Number\\n4. Enter Account Number (Control Number)\\n5. Enter Amount\\n6. Enter your PIN and confirm";
            case "mpesa":
            case "vodacom":
                return "1. Dial *150*00# on your M-Pesa\\n2. Select 'Lipa Bill'\\n3. Enter Business Number\\n4. Enter Account Number (Control Number)\\n5. Enter Amount\\n6. Enter your PIN and confirm";
            case "airtel":
            case "airtelmoney":
                return "1. Dial *150*60# on your Airtel Money\\n2. Select 'Make Payment'\\n3. Select 'Pay Bill'\\n4. Enter Business Number\\n5. Enter Account Number (Control Number)\\n6. Enter Amount\\n7. Enter your PIN and confirm";
            case "halopesa":
            case "halotel":
                return "1. Dial *150*99# on your Halotel Pesa\\n2. Select 'Pay Bill'\\n3. Enter Business Number\\n4. Enter Account Number (Control Number)\\n5. Enter Amount\\n6. Enter your PIN and confirm";
            default:
                return getPaymentInstructions("tigo");
        }
    }

    private String formatInstructions(String instructions) {
        String[] steps = instructions.split("\\\\n");
        StringBuilder htmlSteps = new StringBuilder();
        for (String step : steps) {
            htmlSteps.append("<div class=\"step\">").append(step).append("</div>");
        }
        return htmlSteps.toString();
    }

    private String getNetworkDisplayName(String network) {
        if (network == null) {
            return "Mobile Money";
        }

        switch (network.toLowerCase()) {
            case "tigo":
            case "tigopesa":
                return "Tigo Pesa";
            case "mpesa":
            case "vodacom":
                return "M-Pesa";
            case "airtel":
            case "airtelmoney":
                return "Airtel Money";
            case "halopesa":
            case "halotel":
                return "Halotel Pesa";
            default:
                return "Mobile Money";
        }
    }

    // Additional email methods
    public void sendPaymentReminder(Order order, String network) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, companyName);
            helper.setTo(order.getCustomerEmail());
            helper.setSubject("⏰ Payment Reminder - Order #" + order.getControlNumber());

            String reminderContent = buildPaymentReminderContent(order, network);
            helper.setText(reminderContent, true);

            mailSender.send(message);
            System.out.println("✅ Payment reminder sent to: " + order.getCustomerEmail());

        } catch (Exception e) {
            System.err.println("❌ Failed to send payment reminder: " + e.getMessage());
        }
    }

    public void sendPaymentConfirmationEmail(Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, companyName);
            helper.setTo(order.getCustomerEmail());
            helper.setSubject("✅ Payment Confirmed - Order #" + order.getControlNumber());

            String confirmationContent = buildPaymentConfirmationContent(order);
            helper.setText(confirmationContent, true);

            mailSender.send(message);
            System.out.println("✅ Payment confirmation sent to: " + order.getCustomerEmail());

        } catch (Exception e) {
            System.err.println("❌ Failed to send payment confirmation: " + e.getMessage());
        }
    }

    private String buildPaymentReminderContent(Order order, String network) {
        String customerName = getCustomerName(order);
        String formattedAmount = formatAmount(order.getTotalAmount());

        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; }")
                .append(".container { background: white; border-radius: 10px; padding: 30px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }")
                .append(".header { background: #fff3cd; padding: 20px; border-radius: 8px; text-align: center; margin-bottom: 20px; }")
                .append(".control-number { font-size: 24px; font-weight: bold; color: #856404; margin: 15px 0; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class=\"container\">")
                .append("<div class=\"header\">")
                .append("<h2>⏰ Payment Reminder</h2>")
                .append("</div>")
                .append("<p>Dear <strong>").append(customerName).append("</strong>,</p>")
                .append("<p>This is a friendly reminder that your order <strong>").append(order.getControlNumber()).append("</strong> is awaiting payment.</p>")
                .append("<p><strong>Total Amount: Tsh ").append(formattedAmount).append("</strong></p>")
                .append("<div class=\"control-number\">")
                .append("Control Number: ").append(order.getControlNumber())
                .append("</div>")
                .append("<p>Please complete your payment to avoid order cancellation.</p>")
                .append("<p>Thank you!</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return htmlContent.toString();
    }

    private String buildPaymentConfirmationContent(Order order) {
        String customerName = getCustomerName(order);
        String formattedAmount = formatAmount(order.getTotalAmount());

        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; }")
                .append(".container { background: white; border-radius: 10px; padding: 30px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }")
                .append(".header { background: #d4edda; padding: 20px; border-radius: 8px; text-align: center; margin-bottom: 20px; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class=\"container\">")
                .append("<div class=\"header\">")
                .append("<h2>✅ Payment Confirmed!</h2>")
                .append("</div>")
                .append("<p>Dear <strong>").append(customerName).append("</strong>,</p>")
                .append("<p>We have successfully received your payment for order <strong>").append(order.getControlNumber()).append("</strong>.</p>")
                .append("<p><strong>Amount Paid: Tsh ").append(formattedAmount).append("</strong></p>")
                .append("<p>Your order is now being processed and will be shipped soon.</p>")
                .append("<p>Thank you for your business!</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return htmlContent.toString();
    }

    public void sendEmail(String customerEmail, String subject, String text) {
    }
}