package Lv2;

public class ShoppingBasket {
    private Product productInBasket;
    private int quantity;

    public ShoppingBasket(Product productInBasket, int quantity) {
        this.productInBasket = productInBasket;
        this.quantity = quantity;
    }

    public Product getProductInBasket() {
        return productInBasket;
    }

    public int getQuantity() {
        return quantity;
    }
}