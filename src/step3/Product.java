package step3;

import java.text.DecimalFormat;

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

    public void printProduct() {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.printf("선택한 상품: %s | %s원 | %s | 재고: %d개\n\n",
                getProductName(), df.format(getPrice()), getProductDescription(), getInventoryQuantity());
    }
}