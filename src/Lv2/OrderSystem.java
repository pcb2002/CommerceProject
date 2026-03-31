package Lv2;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrderSystem {
    private Scanner sc;
    private DataBase db;
    private DecimalFormat df = new DecimalFormat("#,###");

    public OrderSystem(Scanner sc, DataBase db) {
        this.sc = sc;
        this.db = db;
    }

    public boolean isCartEmpty() { return db.getCartItems().isEmpty(); }
    public boolean isOrderHistoryEmpty() { return db.getOrderItems().isEmpty(); }

    // void 대신 사용자가 고른 CartItem(상품+수량)을 리턴
    public void handleShopping(int categoryIdx) {
        Category selectedCategory = db.getCategories().get(categoryIdx);
        List<Product> selectedProductList = selectedCategory.getProducts();
        int size = selectedProductList.size();

        while (true) {
            System.out.println("\n[ " + selectedCategory.getCategoryName() + " 카테고리]");

            for (int i = 0; i < size; i++) {
                Product p = selectedProductList.get(i);
                System.out.printf("%d. %-13s | %s원 | %s%n",
                        i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
            }
            System.out.println("0. 뒤로가기");

            try {
                int categoryOption = sc.nextInt();
                sc.nextLine();

                if (categoryOption == 0) {
                    break;
                }

                if (categoryOption > 0 && categoryOption <= size) {
                    Product selectedProduct = selectedProductList.get(categoryOption - 1);

                    System.out.print("선택된 상품: ");
                    System.out.printf("%s | %s원 | %s\n",
                            selectedProduct.getProductName(), df.format(selectedProduct.getPrice()), selectedProduct.getProductDescription());

                    System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                    System.out.println("1. 확인      2. 취소");

                    int saveOption = sc.nextInt();
                    sc.nextLine();

                    if (saveOption == 1) {
                        db.addCartItem(selectedProduct, 1);
                        System.out.println(selectedProduct.getProductName() + "이(가) 장바구니에 담겼습니다.\n");
                    }
                    break;

                } else {
                    System.out.println("잘못된 번호입니다. 1번부터 " + size + "번 사이의 숫자를 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자로만 입력해주세요");
                sc.nextLine();
            }
        }
    }

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
        int totalPrice = 0;
        for (CartItem item : db.getCartItems()) {
            // item 스스로 계산한 금액을 받아오기만 하면 됨
            totalPrice += item.getSubTotalPrice();
        }
        return totalPrice;
    }

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