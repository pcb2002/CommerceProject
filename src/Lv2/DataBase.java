package Lv2;

import java.util.List;

public class DataBase {
    // 1. 순수 데이터(저장소) 역할만 수행하는 리스트들 (상태 변수 완전 제거)
    private List<Category> categories;
    private List<CartItem> cartItems;
    private List<OrderItem> orderItems;

    public DataBase(List<Category> categories, List<CartItem> cartItems, List<OrderItem> orderItems) {
        this.categories = categories;
        this.cartItems = cartItems;
        this.orderItems = orderItems;
    }

    // ==========================================
    // [ 카테고리 및 상품 데이터 관리 ]
    // ==========================================
    public List<Category> getCategories() {
        return categories;
    }

    // 변경점: DB가 선택 상태를 모르기 때문에, "어느 카테고리"에 넣을지 명확하게 인자로 받습니다.
    public void addProductToCategory(int categoryIdx, Product newProduct) {
        categories.get(categoryIdx).getProducts().add(newProduct);
    }

    // 변경점: "어느 카테고리"의 "어느 상품"을 지울지 명확하게 인자로 받습니다.
    public void deleteProductFromCategory(int categoryIdx, int productIdx) {
        categories.get(categoryIdx).getProducts().remove(productIdx);
    }

    // ==========================================
    // [ 장바구니 데이터 관리 ]
    // ==========================================
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public CartItem addCartItem(Product product, int quantity) {
        CartItem newItem = new CartItem(product, quantity);
        cartItems.add(newItem);
        return newItem;
    }

    public void clearCart() {
        cartItems.clear();
    }

    // ==========================================
    // [ 주문 데이터 관리 ]
    // ==========================================
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderItem addOrderItem(Product product, int quantity) {
        OrderItem newItem = new OrderItem(product, quantity);
        orderItems.add(newItem);
        return newItem;
    }

    public void clearOrder() {
        orderItems.clear();
    }
}