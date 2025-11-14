package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentDataReader {
    private static Path FILE_PATH;
    private static PaymentDetails paymentDetails;
    private static List<PaymentDetails> paymentDetailsList;

    // Load payment details from JSON by payment type
    public static PaymentDataReader getInstance(String paymentType) {
        String env = System.getProperty("env");
        String brand = System.getProperty("brand");
        String region = System.getProperty("region");
        String tag = System.getProperty("tag");

        String projectDir = System.getProperty("user.dir");
        System.out.println(projectDir + " - this is project dir");

        FILE_PATH = Paths.get(
                projectDir,
                "src", "test", "resources", "testdata",
                "data_" + brand,
                brand + region.toUpperCase(),
                env,
                "PaymentDetails.json"
        );

        System.out.println("Payment details file location : " + FILE_PATH);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        try (InputStream inputStream = Files.newInputStream(FILE_PATH)) {

            // Read the entire JSON as JsonNode to debug
            JsonNode rootNode = mapper.readTree(inputStream);

            // Check if the payment type exists
            if (!rootNode.has(paymentType)) {
                System.err.println("No data found for paymentType: "
                        + paymentType);
                System.err.println("Available payment types: " + getAvailablePaymentTypes(rootNode));
                throw new RuntimeException("No data found for paymentType: " + paymentType);
            }

            JsonNode paymentNode = rootNode.get(paymentType);

            // Debug: Print what we're trying to parse
            System.out.println("Raw JSON for " + paymentType + ": "
                    + paymentNode.toString());

            // Skip VALID_GIFT_CARDS_DYNAMIC_VALUES as it has nested arrays
            if ("VALID_GIFT_CARDS_DYNAMIC_VALUES".equals(paymentType)) {
                System.err.println("VALID_GIFT_CARDS_DYNAMIC_VALUES has unsupported nested array structure");
                throw new
                        RuntimeException("VALID_GIFT_CARDS_DYNAMIC_VALUES payment type is not supported");
            }

            // Check if it's an array and has at least one element
            if (!paymentNode.isArray() || paymentNode.size() == 0) {
                throw new RuntimeException("Payment type " + paymentType + " is not an array or is empty");
            }

            // Get the first element
            JsonNode firstElement = paymentNode.get(0);

            // Manually create PaymentDetails to ensure fields are set
            paymentDetails = new PaymentDetails();

            if (firstElement.has("CARD_NUMBER")) {

                paymentDetails.setCARD_NUMBER(firstElement.get("CARD_NUMBER").asText());
            }
            if (firstElement.has("MONTH")) {
                paymentDetails.setMONTH(firstElement.get("MONTH").asText());
            }
            if (firstElement.has("YEAR")) {
                paymentDetails.setYEAR(firstElement.get("YEAR").asText());
            }
            if (firstElement.has("SECURITY_CODE")) {

                paymentDetails.setSECURITY_CODE(firstElement.get("SECURITY_CODE").asText());
            }
            if (firstElement.has("EMAIL_ID")) {

                paymentDetails.setEMAIL_ID(firstElement.get("EMAIL_ID").asText());
            }
            if (firstElement.has("PASSWORD")) {

                paymentDetails.setPASSWORD(firstElement.get("PASSWORD").asText());
            }
            if (firstElement.has("PIN")) {
                paymentDetails.setPIN(firstElement.get("PIN").asText());
            }
            if (firstElement.has("VALUE")) {
                paymentDetails.setVALUE(firstElement.get("VALUE").asText());
            }

            // Validate required fields for card payment
            if (paymentDetails.getCARD_NUMBER() == null) {
                throw new RuntimeException("Card number is null for payment type: " + paymentType);
            }

            System.out.println("Successfully loaded payment details for: " + paymentType);
            System.out.println("Card Number: " + getMaskedCardNumber(paymentDetails.getCARD_NUMBER()));
            System.out.println("Expiry: " +
                    paymentDetails.getMONTH() + "/" + paymentDetails.getYEAR());
            System.out.println("CVV: " + paymentDetails.getSECURITY_CODE());

            return new PaymentDataReader();

        } catch (Exception e) {
            System.err.println("Failed to read payment details JSON from: " + FILE_PATH);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to read payment details JSON", e);
        }
    }

    // Helper method to get available payment types
    private static List<String> getAvailablePaymentTypes(JsonNode rootNode) {
        List<String> types = new ArrayList<>();
        rootNode.fieldNames().forEachRemaining(types::add);
        return types;
    }

    // Helper method to mask card number for security
    private static String getMaskedCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8) {
            return "****";
        }
        return cardNumber.substring(0, 4) + "****" +
                cardNumber.substring(cardNumber.length() - 4);
    }

    // Getter methods with null safety
    public String getCardNumber() {
        if (paymentDetails == null || paymentDetails.getCARD_NUMBER() == null) {
            throw new RuntimeException("Card number is not available. Payment details not loaded properly.");
        }
        return paymentDetails.getCARD_NUMBER();
    }

    public String getMonth() {
        if (paymentDetails == null || paymentDetails.getMONTH() == null) {
            throw new RuntimeException("Month is not available. Payment details not loaded properly.");
        }
        return paymentDetails.getMONTH();
    }

    public String getYear() {
        if (paymentDetails == null || paymentDetails.getYEAR() == null) {
            throw new RuntimeException("Year is not available. Payment details not loaded properly.");
        }
        return paymentDetails.getYEAR();
    }

    public String getSecurityCode() {
        if (paymentDetails == null ||
                paymentDetails.getSECURITY_CODE() == null) {
            throw new RuntimeException("Security code is not available. Payment details not loaded properly.");
        }
        return paymentDetails.getSECURITY_CODE();
    }

    public String getEmailId() {
        return paymentDetails != null ? paymentDetails.getEMAIL_ID() : null;
    }

    public String getPassword() {
        return paymentDetails != null ? paymentDetails.getPASSWORD() : null;
    }

    public String getPin() {
        return paymentDetails != null ? paymentDetails.getPIN() : null;
    }

    public String getValue() {
        return paymentDetails != null ? paymentDetails.getVALUE() : null;
    }

    public String getFormattedExpiry() {
        return getMonth() + getYear();
    }

    public String getFormattedExpiryWithSlash() {
        return getMonth() + "/" + getYear();
    }
}
