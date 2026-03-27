package step1;

public class Product {
    private String productName;
    private int price;
    private String productDescription;
    private int inventoryQuantity;

    public Product(String productName, int price, String productDescription, int inventoryQuantity) {
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }
}