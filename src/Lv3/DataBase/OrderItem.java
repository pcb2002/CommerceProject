package Lv3.DataBase;

public class OrderItem {
    private Product product; // 바구니 안의 상품이라는 수식어 제거 (깔끔함 유지)
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}