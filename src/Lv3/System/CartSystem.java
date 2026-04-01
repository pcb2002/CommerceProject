package Lv3.System;

import Lv3.DataBase.CartItem;
import Lv3.DB;
import Lv3.DataBase.Product;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class CartSystem {
    private Scanner sc;
    private DB db;
    private DecimalFormat df = new DecimalFormat("#,###");

    public CartSystem(Scanner sc, DB db) {
        this.sc = sc;
        this.db = db;
    }

    // 상품을 받아서 카트에 담기만 합니다.
    public void handleAddToCart(Product p) {
        db.addCartItem(p, 1);
        System.out.println(p.getProductName() + "이(가) 장바구니에 담겼습니다.\n");
    }

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
        System.out.println("1. 주문 확정     2. 상품 삭제     3. 메인으로 돌아가기");
    }

    public void deleteCartItemProcess() {
        System.out.print("삭제할 상품명을 입력하세요: ");
        String name = sc.nextLine();

        // stream.filter를 사용해 '지우려는 상품명과 다른' 상품들만 수집
        List<CartItem> updatedCart = db.getCartItems().stream()
                .filter(item -> !item.getProduct().getProductName().equals(name))
                .toList();

        // 필터링 전후의 장바구니 크기를 비교하여 삭제 여부 확인
        if (db.getCartItems().size() == updatedCart.size()) {
            System.out.println("장바구니에 [" + name + "] 상품이 존재하지 않습니다.");
            return;
        }

        // 기존 장바구니를 비우고, 필터링된 새로운 리스트로 갱신
        db.getCartItems().clear();
        db.getCartItems().addAll(updatedCart);

        System.out.println("[" + name + "] 상품이 장바구니에서 제거되었습니다.");
    }
}