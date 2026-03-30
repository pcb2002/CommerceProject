package Lv2;

import java.text.DecimalFormat;

public class Product {
    private String productName;
    private int price;
    private String productDescription;
    private int inventoryQuantity;

    // 상픔 등록 생성자
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public void printProduct() {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.printf("%s | %s원 | %s | 재고: %d개\n",
                getProductName(), df.format(getPrice()), getProductDescription(), getInventoryQuantity());
    }

    public void deductInventoryQuantity(int i) {
        this.inventoryQuantity -= i;
    }

    public void addInventoryQuantity(int i) {
        this.inventoryQuantity += i;
    }
}