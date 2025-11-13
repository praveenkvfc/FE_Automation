package utils;

import com.microsoft.playwright.Page;

import java.text.SimpleDateFormat;
import java.util.*;

public class RandomDataGenerator {
    private Page page;
    public RandomDataGenerator(Page page) {
        this.page = page;
    }
    private static final Random RANDOM = new Random();
    // Region-specific country codes
    private static final Map<String, String> REGION_MOBILE_PREFIXES = new HashMap<>() {{
        put("US", "+1");
        put("UK", "+44");
        put("CA", "+1");
        put("DE", "+49"); // Germany
        put("IT", "+39"); // Italy
        put("FR", "+33"); // France
    }};

    // Generate random name of given length
    public static String generateRandomName() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        sb.append(letters.charAt(RANDOM.nextInt(26))); // Capital first letter
        for (int i = 1; i < 10; i++) {
            sb.append(letters.charAt(26 + RANDOM.nextInt(26))); // Lowercase
        }
        return sb.toString();
    }

    // Generate random email with random domain
    public static String generateRandomEmail(String firstName, String lastName) {
        return (firstName + "." + lastName + getRandomNumber(100, 999) + "@yopmail.com").toLowerCase();
    }

    // Generate region-specific mobile number
    public static String generateMobileNumber(String regionCode) {
        String prefix = REGION_MOBILE_PREFIXES.getOrDefault(regionCode.toUpperCase(), "+1");
        String number = String.format("%010d", RANDOM.nextLong() % 1_000_000_0000L);
        return "499"+number;
    }

    // Generate random DOB in MM/dd/yyyy format (between 1990 and 18 years before today)
    public static String generateDOB() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18); // Max date (user must be 18+)
        Date maxDate = calendar.getTime();

        calendar.set(1990, Calendar.JANUARY, 1); // Min date
        long minMillis = calendar.getTimeInMillis();
        long maxMillis = maxDate.getTime();

        long randomMillis = minMillis + (long) (RANDOM.nextDouble() * (maxMillis - minMillis));
        Date dob = new Date(randomMillis);
        return new SimpleDateFormat("MM/dd/yyyy").format(dob);
    }

    private static int getRandomNumber(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }


}



