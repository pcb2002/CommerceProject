package Lv3.System;

import Lv3.DataBase.CartItem;
import Lv3.DB;
import Lv3.DataBase.Product;
import java.text.DecimalFormat;

public class CartSystem {
    private DB db;
    private DecimalFormat df = new DecimalFormat("#,###");

    public CartSystem(DB db) {
        this.db = db;
    }

    // 상품을 받아서 카트에 담기만 합니다.
    public void handleAddToCart(Product p) {
        db.addCartItem(p, 1);
        System.out.println(p.getProductName() + "이(가) 장바구니에 담겼습니다.\n");
    }

    // (기존의 장바구니 조회, 가격 계산 등의 로직은 그대로 유지)
    public boolean isCartEmpty() { return db.getCartItems().isEmpty(); }

    public String getCartDetails() {
        if (isCartEmpty()) return "장바구니가 비어있습니다.\n";
        StringBuilder sb = new StringBuilder();
        for (CartItem item : db.getCartItems()) {
            Product p = item.getProduct();
            sb.append(String.format("%-13s | %s원 | %s | 수량: %d개\n",
                    p.getProductName(), df.format(p.getPrice()), p.getProductDescription(), item.getQuantity()));
        }
        return sb.toString();
    }

    public int calculateTotalPrice() {
        int total = 0;
        for (CartItem item : db.getCartItems()) total += item.getSubTotalPrice();
        return total;
    }

    // 3. 주문 처리 핸들러
    public void printCart() {
        System.out.println("\n[ 장바구니 내역 ]");
        System.out.print(getCartDetails());
        System.out.println("\n[ 총 주문 금액 ]\n" + df.format(calculateTotalPrice()) + "원\n");
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
    }
}