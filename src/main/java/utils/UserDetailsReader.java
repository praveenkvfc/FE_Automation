package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class UserDetailsReader {
    private static Path FILE_PATH;
    private static UserDetails user;

    // Load user from JSON by user type
    public static UserDetailsReader getInstance(String userType) {
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
                "UserDetails.json"
        );

        System.out.println("User details file location : " + FILE_PATH);

        // Configure ObjectMapper to ignore unknown properties (for safety)
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        try (InputStream inputStream = Files.newInputStream(FILE_PATH)) {
            Map<String, List<UserDetails>> userMap = mapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, List<UserDetails>>>() {
                    }
            );

            if (userMap.containsKey(userType) &&
                    !userMap.get(userType).isEmpty()) {
                user = userMap.get(userType).get(0);
                System.out.println("Successfully loaded user details for: " + userType);
                System.out.println("User email: " + user.getEmail());
                System.out.println("Country: " + user.getCountryAbbreviation());
                return new UserDetailsReader();
            } else {
                System.err.println("No data found for userType: " + userType);
                System.err.println("Available user types: " + userMap.keySet());
                throw new RuntimeException("No data found for userType: " + userType + ". Available types: " + userMap.keySet());
            }

        } catch (Exception e) {
            System.err.println("Failed to read user details JSON from: " + FILE_PATH);
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to read user details JSON from: " + FILE_PATH, e);
        }
    }

    // Getter methods
    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getPhone() {
        return user.getPhone();
    }

    public String getStreetAddress() {
        return user.getStreetAddress();
    }

    public String getBuilding() {
        return user.getBuilding();
    }

    public String getPostalCode() {
        return user.getPostalCode();
    }

    public String getCity() {
        return user.getCity();
    }

    public String getProvince() {
        return user.getProvince();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getBirthday() {
        return user.getBirthday();
    }

    public String getCountryAbbreviation() {
        return user.getCountryAbbreviation();
    }

    public String getConfirmPassword() {
        return user.getConfirmPassword();
    }
}
