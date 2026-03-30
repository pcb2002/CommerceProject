package Lv2;

import java.util.ArrayList;
import java.util.List;

public class OrderRecord {
    // 속성: 이 주문 영수증에 적힌 상품 목록들
    private List<CartItem> orderItems;

    // 생성자
    public OrderRecord(List<CartItem> basketList) {
        // 장바구니가 비워져도 기록이 날아가지 않도록 새로운 리스트로 복사 (방어적 복사)
        this.orderItems = new ArrayList<>(basketList);
    }

    // 기능
    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public CartItem getOrderedItem(int index) {
        return orderItems.get(index);
    }
}