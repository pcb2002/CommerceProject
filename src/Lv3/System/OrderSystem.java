package Lv3.System;

import Lv3.DB;
import Lv3.DataBase.CartItem;
import Lv3.DataBase.CustomerGrade;
import Lv3.DataBase.OrderItem;
import Lv3.DataBase.Product;

import java.text.DecimalFormat;
import java.util.Scanner;

public class OrderSystem {
    private Scanner sc;
    private DB db;
    private DecimalFormat df = new DecimalFormat("#,###");
    private CustomerGrade[] grades = CustomerGrade.values();
    private int totalPrice;

    public OrderSystem(Scanner sc, DB db) {
        this.sc = sc;
        this.db = db;
    }

    public boolean isCartEmpty() { return db.getCartItems().isEmpty(); }
    public boolean isOrderHistoryEmpty() { return db.getOrderItems().isEmpty(); }

    public void processOrder(int price) {
        // 1. 장바구니가 비어있을 경우 메시지 출력 후 메서드 즉시 종료
        if (isCartEmpty()) {
            System.out.println("결제할 상품이 없습니다.");
            return;
        }

        // 2. 장바구니 상품을 주문 목록으로 복사
        for (CartItem item : db.getCartItems()) {
            OrderItem newItem = new OrderItem(item.getProduct(), item.getQuantity());
            db.getOrderItems().add(newItem);
        }
        printGrade();

        int choice = 0;
        try {
            choice = Integer.parseInt(sc.nextLine()); // 엔터키 씹힘 방지를 위해 nextLine() 사용 후 변환
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해주세요.");
            return; // 잘못 입력 시 메서드 종료 (또는 while문을 써서 다시 입력받게 할 수도 있습니다)
        }

        // 2. 입력받은 번호 검증 및 등급 매핑
        CustomerGrade selectedGrade;
        if (choice >= 1 && choice <= grades.length) {
            selectedGrade = grades[choice - 1];
        } else {
            System.out.println("잘못된 번호입니다. 기본 등급(BRONZE)으로 결제가 진행됩니다.");
            selectedGrade = CustomerGrade.BRONZE;
        }

        // 3. 주문 완료 메시지 바로 출력
        System.out.println("\n주문이 완료되었습니다!\n");

        // 4. 할인 적용 최종 가격 출력
        this.totalPrice = selectedGrade.calculateDiscountedPrice(price);
        System.out.printf("할인 전 금액: %s원\n", df.format(price));
        System.out.printf("%s 등급 할인(%d%%): -%s원\n", selectedGrade.name(),
                selectedGrade.getDiscountPercent(), selectedGrade.discountAmount(price));
        System.out.printf("최종 결제 금액: %s원\n", df.format(totalPrice));

        // 5. 재고 차감 및 변경 내역 바로 출력
        for (CartItem item : db.getCartItems()) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            int beforeQty = p.getInventoryQuantity();

            p.deductInventoryQuantity(qty);

            System.out.printf("- %s 재고: %d개 -> %d개\n",
                    p.getProductName(), beforeQty, p.getInventoryQuantity());
        }

        // 6. 장바구니 초기화
        db.clearCart();
    }

    // 4. 주문 취소 핸들러
    public void handleCancelProcess() {
        if (isOrderHistoryEmpty()) {
            System.out.println("취소할 주문이 없습니다.");
            return;
        }

        System.out.println("\n[ 최근 주문 내역 ]");
        printOrderHistory();
        System.out.println("\n주문을 취소하시겠습니까?");
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기");

        int cancelOption = sc.nextInt();
        sc.nextLine();

        if (cancelOption == 1) {
            System.out.println(cancelAllOrders());
        }
    }

    public void printGrade() {
        System.out.println("고객 등급을 입력해주세요.");
        for (int i = 0; i < 4; i++) {
            System.out.print((i + 1) + ". ");
            System.out.printf("%-8s : %2d%% 할인\n",
                    grades[i].name(),
                    grades[i].getDiscountPercent());
        }
    }

    public void printOrderHistory() { // get을 print로 변경하면 의미상 더 자연스럽습니다.
        if (isOrderHistoryEmpty()) {
            System.out.println("주문 내역이 없습니다.\n");
            return;
        }

        for (OrderItem item : db.getOrderItems()) {
            Product p = item.getProduct();

            System.out.printf("%-13s | %s원 | %s | 수량: %d개\n",
                    p.getProductName(),
                    df.format(p.getPrice()),
                    p.getProductDescription(),
                    item.getQuantity());
        }

        System.out.println("[ 총 주문 금액 ]\n" + df.format(totalPrice) + "원");
    }

    public String cancelAllOrders() {
        if (isOrderHistoryEmpty()) return "취소할 주문이 없습니다.";

        StringBuilder result = new StringBuilder();
        result.append("\n주문이 취소되어 재고가 복구되었습니다.\n");

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