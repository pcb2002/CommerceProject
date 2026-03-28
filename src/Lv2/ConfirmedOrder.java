package Lv2;

import java.util.ArrayList;
import java.util.List;

public class ConfirmedOrder {
    //속성
    private List<ShoppingBasket> confirmedOrder;

    //생성자
    public ConfirmedOrder(List<ShoppingBasket> basketList) {
        // 장바구니를 비우면 주문된 상품도 같이 지워지는 것을 방지하기 위해
        // 장바구니의 목록을 주문 목록에 그대로 복사해오도록 인스턴스 생성
        this.confirmedOrder = new ArrayList<>(basketList);
    }

    //기능
    public List<ShoppingBasket> getConfirmedOrder() {
        return confirmedOrder;
    }

    public ShoppingBasket getOrderedItem(int i) {
        return confirmedOrder.get(i);
    }
}