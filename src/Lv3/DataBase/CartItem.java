package Lv3.DataBase;

public class CartItem {
    private Product product; // 바구니 안의 상품이라는 수식어 제거 (깔끔함 유지)
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // 내(아이템) 총액을 내가 계산해서 알려줌
    public int getSubTotalPrice() {
        return product.getPrice() * quantity;
    }
}