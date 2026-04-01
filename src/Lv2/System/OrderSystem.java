package Lv2.System;


import Lv2.DB;
import Lv2.DataBase.CartItem;
import Lv2.DataBase.OrderItem;
import Lv2.DataBase.Product;

import java.text.DecimalFormat;
import java.util.Scanner;

public class OrderSystem {
    private Scanner sc;
    private DB db;
    private DecimalFormat df = new DecimalFormat("#,###");

    public OrderSystem(Scanner sc, DB db) {
        this.sc = sc;
        this.db = db;
    }

    public boolean isCartEmpty() { return db.getCartItems().isEmpty(); }
    public boolean isOrderHistoryEmpty() { return db.getOrderItems().isEmpty(); }

    public String processOrder() {
        if (isCartEmpty()) return "결제할 상품이 없습니다.";

        for (CartItem item : db.getCartItems()) {
            OrderItem newItem = new OrderItem(item.getProduct(), item.getQuantity());
            db.getOrderItems().add(newItem);
        }

        StringBuilder result = new StringBuilder();
        result.append("주문이 완료되었습니다!\n");

        for (CartItem item : db.getCartItems()) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            int beforeQty = p.getInventoryQuantity();

            p.deductInventoryQuantity(qty);

            result.append(String.format("- %s 재고: %d개 -> %d개\n",
                    p.getProductName(), beforeQty, p.getInventoryQuantity()));
        }
        db.clearCart();
        return result.toString();
    }

    // 4. 주문 취소 핸들러
    public void handleCancelProcess() {
        if (isOrderHistoryEmpty()) {
            System.out.println("취소할 주문이 없습니다.");
            return;
        }

        System.out.println("\n[ 최근 주문 내역 ]");
        System.out.print(getOrderHistoryDetails());
        System.out.println("\n주문을 취소하시겠습니까?");
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기");

        int cancelOption = sc.nextInt();
        sc.nextLine();

        if (cancelOption == 1) {
            System.out.println(cancelAllOrders());
        }
    }

    public String getOrderHistoryDetails() {
        if (isOrderHistoryEmpty()) return "주문 내역이 없습니다.\n";

        StringBuilder sb = new StringBuilder();
        for (OrderItem item : db.getOrderItems()) {
            Product p = item.getProduct();
            sb.append(String.format("%-13s | %s원 | %s | 수량: %d개\n",
                    p.getProductName(), df.format(p.getPrice()), p.getProductDescription(), item.getQuantity()));
        }
        return sb.toString();
    }

    public String cancelAllOrders() {
        if (isOrderHistoryEmpty()) return "취소할 주문이 없습니다.";

        StringBuilder result = new StringBuilder();
        result.append("주문이 취소되어 재고가 복구되었습니다.\n");

        for (OrderItem item : db.getOrderItems()) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            int beforeQty = p.getInventoryQuantity();

            p.addInventoryQuantity(qty);

            result.append(String.format("- %s 재고: %d개 -> %d개\n",
                    p.getProductName(), beforeQty, p.getInventoryQuantity()));

        }
        db.clearOrder();
        return result.toString();
    }
}