package utils;

import com.microsoft.playwright.Locator; import com.microsoft.playwright.Page; import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import pages.VANS.US.vans_productListpage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static utils.PlaywrightFactory.getPage;

public class SortUtility extends vans_productListpage {

    public SortUtility(Page page) {
        super(page);
    }

    /**
     * Validate if prices are sorted according to the specified sort type
     *
     * @param sortType "low_to_high" or "high_to_low"
     */
    public boolean validatePriceSorting(String sortType, Page page, Locator productCards) {
        // Wait for products to load after sorting
        page.waitForTimeout(3000);

        // Get product cards using locator method
        productCards.waitFor(new Locator.WaitForOptions().setTimeout(10000));

        int totalProducts = productCards.count();
        System.out.println("Validating " + sortType + " sorting for "
                + totalProducts + " products");

        // Extract prices from all products using actual locators
        List<Double> productPrices = new ArrayList<>();
        List<String> originalPriceTexts = new ArrayList<>();

        for (int i = 0; i < totalProducts; i++) {
            Locator productLocator = productCards.nth(i);
            String priceText = extractProductPrice(productLocator, i);
            originalPriceTexts.add(priceText);

            Double priceValue = extractNumericPrice(priceText);
            if (priceValue != null) {
                productPrices.add(priceValue);
                System.out.println("Product " + i + " price: " + priceValue + " (from: " + priceText + ")");
            }
        }

        // Validate sorting
            boolean isSorted = switch (sortType.toLowerCase()) {
                case "low_to_high", "low-high", "price_low_high" -> isSortedLowToHigh(productPrices);
                case "high_to_low", "high-low", "price_high_low" -> isSortedHighToLow(productPrices);
                default -> throw new IllegalArgumentException("Invalid sort type: " + sortType);
            };


        printSortingValidationResult(sortType, productPrices, isSorted);
        return isSorted;
    }

    /**
     * Extract product price from product locator
     */
    protected String extractProductPrice(Locator productLocator, int index) {
        try {
            // Try multiple strategies to find product price within the product card
            Locator priceInProduct =
                    productLocator.locator(".product-price, .price, .amount, [data-testid*='price'], .cost, .value, [class*='price'], [class*='Price']");
            if (priceInProduct.count() > 0) {
                return priceInProduct.first().innerText().trim();
            }

            // Look for price patterns in the entire product text
            String productText = productLocator.innerText().trim();
            // Regex to find price patterns like $10.99, £20, 30.00, etc.
            Pattern pricePattern = Pattern.compile("[£$€]?\\d+[.,]?\\d*[£$€]?");
            java.util.regex.Matcher matcher = pricePattern.matcher(productText);
            if (matcher.find()) {
                return matcher.group();
            }
        } catch (Exception e) {
            System.out.println("Could not extract price for product at index " + index);
        }
        return "Price not available";
    }

    /**
     * Extract product name from product locator
     */
    protected String extractProductName(Locator productLocator, int index) {
        try {
            // Try multiple strategies to find product name within the product card
            Locator nameInProduct =
                    productLocator.locator(".product-name, .title, h3, h4, [data-testid*='title'], [data-testid*='name'], [class*='name'], [class*='title'], a");
            if (nameInProduct.count() > 0) {
                return nameInProduct.first().innerText().trim();
            }

            // If no specific name element found, try getting text from the product card itself
            String productText = productLocator.innerText().trim();
            String[] lines = productText.split("\n");
            if (lines.length > 0) {
                return lines[0]; // First line is often the product name
            }
        } catch (Exception e) {
            System.out.println("Could not extract name for product at index " + index);
        }
        return "Unknown Product";
    }

    private Double extractNumericPrice(String priceText) {
        try {
            // Remove all non-numeric characters except decimal point and minus sign
            String numericString = priceText.replaceAll("[^\\d.-]", "");
            if (!numericString.isEmpty() && !numericString.equals("-")) {
                return Double.parseDouble(numericString);
            }
        } catch (NumberFormatException e) {
            System.out.println("Could not parse price: " + priceText);
        }
        return null;
    }

    private boolean isSortedLowToHigh(List<Double> prices) {
        if (prices.size() < 2) return true;

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                System.out.println(" Sorting violation at index " + i
                        + ": " + prices.get(i) + " > " + prices.get(i + 1));
                return false;
            }
        }
        return true;
    }

    private boolean isSortedHighToLow(List<Double> prices) {
        if (prices.size() < 2) return true;

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                System.out.println(" Sorting violation at index " + i
                        + ": " + prices.get(i) + " < " + prices.get(i + 1));
                return false;
            }
        }
        return true;
    }

    private void printSortingValidationResult(String sortType, List<Double> prices, boolean isSorted) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SORTING VALIDATION RESULT");
        System.out.println("=".repeat(50));
        System.out.println("Sort Type: " + sortType);
        System.out.println("Products with valid prices: " + prices.size());
        System.out.println("Correctly sorted: " + (isSorted ? "YES" : "NO"));

        if (prices.size() > 0) {
            System.out.println("Price range: " + prices.get(0) + " to " + prices.get(prices.size() - 1));
        }
        System.out.println("=".repeat(50) + "\n");
    }

    /**
     * Get all products with their names and prices
     */
    public List<ProductInfo> getAllProducts() {
        Locator productCards = getProductCards();
        productCards.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));

        int totalProducts = productCards.count();
        List<ProductInfo> allProducts = new ArrayList<>();

        for (int i = 0; i < totalProducts; i++) {
            Locator productLocator = productCards.nth(i);
            String name = extractProductName(productLocator, i);
            String price = extractProductPrice(productLocator, i);

            allProducts.add(new ProductInfo(name, price, i));
        }

        return allProducts;
    }

    /**
     * Print all products with names and prices
     */
    public void printAllProducts() {
        List<ProductInfo> allProducts = getAllProducts();

        System.out.println("\n=== PRINTING ALL PRODUCTS ===");
        System.out.println("Total products: " + allProducts.size());

        for (ProductInfo product : allProducts) {
            System.out.println(product.toString());
        }

        System.out.println("=== END OF PRODUCTS ===\n");
    }

    /**
     * Print only prices in simple format
     */
    public void printPricesSimple() {
        List<ProductInfo> allProducts = getAllProducts();
        List<String> simplePrices = new ArrayList<>();

        for (ProductInfo product : allProducts) {
            String simplePrice = extractSimplePrice(product.price());
            if (!simplePrice.isEmpty()) {
                simplePrices.add(simplePrice);
            }
        }

        System.out.println("Prices: " + String.join(", ", simplePrices));
    }

    /**
     * Select a product by its exact name using the role-based locator
     */
    public void selectProductByName(String productName) {
        try {
            Locator productButton = getPage().getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName(productName));

            productButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));

            productButton.click();
            System.out.println("Successfully selected product: " + productName);
            getPage().waitForTimeout(2000);

        } catch (Exception e) {
            System.out.println("Failed to select product: " + productName);
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Select a product by index (from first 10 products)
     */
    public ProductInfo selectRandomProductFromFirstTen() {
        List<ProductInfo> allProducts = getAllProducts();
        int productsToConsider = Math.min(allProducts.size(), 10);

        if (productsToConsider == 0) {
            System.out.println("No products found to select from!");
            return null;
        }

        // Randomly select one from first 10
        int randomIndex = new java.util.Random().nextInt(productsToConsider);
        ProductInfo selectedProduct = allProducts.get(randomIndex);

        System.out.println("Selected product index: " + randomIndex +
                " | Name: " + selectedProduct.name() +
                " | Price: " + selectedProduct.price());

        // Select using the product name
        selectProductByName(selectedProduct.name());

        return selectedProduct;
    }

    /**
     * Select a specific product by index
     */
    public ProductInfo selectProductByIndex(int index) {
        List<ProductInfo> allProducts = getAllProducts();

        if (index < 0 || index >= allProducts.size()) {
            System.out.println("Invalid index: " + index + ". Total products: " + allProducts.size());
            return null;
        }

        ProductInfo selectedProduct = allProducts.get(index);
        System.out.println("Selecting product at index " + index +
                " | Name: " + selectedProduct.name() +
                " | Price: " + selectedProduct.price());

        // Select using the product name
        selectProductByName(selectedProduct.name());

        return selectedProduct;
    }

    /**
     * Get product names only as List
     */
    public List<String> getAllProductNames() {
        List<ProductInfo> allProducts = getAllProducts();
        List<String> productNames = new ArrayList<>();

        for (ProductInfo product : allProducts) {
            productNames.add(product.name());
        }

        return productNames;
    }

    /**
     * Print all product names only
     */
    public void printAllProductNames() {
        List<String> productNames = getAllProductNames();

        System.out.println("\n=== PRODUCT NAMES ===");
        for (int i = 0; i < productNames.size(); i++) {
            System.out.println(i + ": " + productNames.get(i));
        }
        System.out.println("=== END OF NAMES ===\n");
    }

    /**
     * Print only prices in the format you requested (3, 9, 19, 21, 23, 167)
     */
    public void printAllProductPricesOnly() {
        List<ProductInfo> allProducts = getAllProducts();
        List<String> pricesOnly = new ArrayList<>();

        for (ProductInfo product : allProducts) {
            String numericPrice =
                    extractNumericPriceForPrinting(product.price());
            if (!numericPrice.isEmpty()) {
                pricesOnly.add(numericPrice);
            }
        }

        // Print prices in the requested format
        System.out.println("\n=== PRODUCT PRICES ONLY ===");
        System.out.println(String.join(", ", pricesOnly));
        System.out.println("=== END OF PRICES ===\n");
    }

    /**
     * Helper method to extract only numeric part from price for printing
     */
    private String extractNumericPriceForPrinting(String priceText) {
        if (priceText == null || priceText.isEmpty() || priceText.equals("Price not available")) {
            return "";
        }

        // Extract numbers (including decimals)
        Pattern numericPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        java.util.regex.Matcher matcher = numericPattern.matcher(priceText);
        if (matcher.find()) {
            // Remove decimal part if it's .00
            String price = matcher.group(1);
            if (price.endsWith(".0") || price.endsWith(".00")) {
                price = price.substring(0, price.indexOf('.'));
            }
            return price;
        }

        return "";
    }

    /**
     * Extract simple integer price without decimals
     */
    private String extractSimplePrice(String priceText) {
        if (priceText == null || priceText.isEmpty() || priceText.equals("Price not available")) {
            return "";
        }

        Pattern integerPattern = Pattern.compile("(\\d+)(?:\\.\\d+)?");
        java.util.regex.Matcher matcher = integerPattern.matcher(priceText);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    /**
     * Scroll to load all products (useful for lazy loading)
     */
    public void scrollToLoadAllProducts() {
        Locator productCards = getProductCards();
        int initialCount = productCards.count();

        // Scroll to bottom multiple times to trigger lazy loading
        for (int i = 0; i < 3; i++) {
            getPage().evaluate("window.scrollTo(0, document.body.scrollHeight)");
            getPage().waitForTimeout(2000);

            int newCount = productCards.count();
            if (newCount == initialCount) {
                break; // No new products loaded
            }
            initialCount = newCount;
        }

        // Scroll back to top
        getPage().evaluate("window.scrollTo(0, 0)");
        getPage().waitForTimeout(1000);
    }
}
