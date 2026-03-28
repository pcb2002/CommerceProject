package Lv2;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class OrderSystem {
    private Scanner sc;
    private List<ShoppingBasket> basketList;
    private List<Category> category;
    private List<ConfirmedOrder> confirmedOrder;

    public OrderSystem(Scanner sc, List<ShoppingBasket> basketList,
                       List<Category> category, List<ConfirmedOrder> confirmedOrder) {
        this.sc = sc;
        this.basketList = basketList;
        this.category = category;
        this.confirmedOrder = confirmedOrder;
    }

    public boolean isBasketEmpty() {
        return basketList.isEmpty();
    }

    public boolean isOrderEmpty() {
        return confirmedOrder.isEmpty();
    }

    public void saveProduct(int i, int categoryNum) {
        // 1. 카테고리에서 상품 객체를 먼저 꺼냅니다.
        Category selectedCategory = category.get(i);
        Product selectedProduct = selectedCategory.getProduct(categoryNum);

        if (selectedProduct == null) return;

        selectedCategory.printChoicedProducts(categoryNum);
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        int saveOption = sc.nextInt();
        if (saveOption == 1) {
            // 여기서 수량을 물어볼 수도 있습니다. (예: 1개)
            int quantity = 1;

            // 2. ShoppingBasket 타입의 객체를 생성합니다.
            ShoppingBasket item = new ShoppingBasket(selectedProduct, quantity);

            // 3. 이제 타입이 일치하는 리스트에 추가합니다!
            basketList.add(item);

            System.out.println(selectedProduct.getProductName() + "이(가) 장바구니에 담겼습니다.\n");
        }
    }

    public void printBasketList() {
        System.out.println("[ 장바구니 내역 ]");
        // 금액 콤마 포맷팅
        DecimalFormat df = new DecimalFormat("#,###");

        for (ShoppingBasket shoppingBasket : basketList) {
            Product p = shoppingBasket.getProductInBasket();
            // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
            System.out.printf("%-13s | %s원 | %s| 수량: %d개\n",
                    p.getProductName(), df.format(p.getPrice()), p.getProductDescription(), shoppingBasket.getQuantity());
        }
    }
    // 총 주문금액 계산
    public int totalPrice() {
        int totalPrice = 0;

        for (int i = 0; i < basketList.size(); i++) {
            Product p = basketList.get(i).getProductInBasket();
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    //총 주문금액 출력
    public void printTotalPrice() {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println(df.format(totalPrice()) + "원\n");
    }

    public void confirmOrder() {
        printBasketList();
        System.out.println("\n[ 총 주문 금액 ]");
        printTotalPrice();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");

        int confirmOption = sc.nextInt();

        // 주문확정 시 반복
        if (confirmOption == 1) {
            ConfirmedOrder confirmedOrders = new ConfirmedOrder(basketList);
            confirmedOrder.add(confirmedOrders);

            System.out.print("주문이 완료되었습니다! 총 금액: ");
            printTotalPrice();

            // 재고 업데이트
            for (ShoppingBasket shoppingBasket : basketList) {
                Product p = shoppingBasket.getProductInBasket();
                int quantity = shoppingBasket.getQuantity();
                p.deductInventoryQuantity(quantity);
                System.out.printf("%s 재고가 %d개 -> %d게로 업데이트되었습니다.\n\n",
                        p.getProductName(), p.getInventoryQuantity() + quantity, p.getInventoryQuantity());
            }
            basketList.clear();
        }
    }

    public void cancleOrder() {
        System.out.println("[ 최근 주문 내역 ]");
        // 1. 먼저 어떤 주문들이 있는지 보여줍니다.
        for (int i = 0; i < confirmedOrder.size(); i++) {
            System.out.println((i + 1) + "번 주문 기록");
            List<ShoppingBasket> oneOrder = confirmedOrder.get(i).getConfirmedOrder();
            for (ShoppingBasket item : oneOrder) {
                System.out.println("  - " + item.getProductInBasket().getProductName() + " " + item.getQuantity() + "개");
            }
        }
        System.out.println("\n주문을 취소하시겠습니까?");
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기");

        int cancleOption = sc.nextInt();

        if (cancleOption == 1) {
            // 1. 주문서(ConfirmedOrder)를 한 장씩 꺼냄
            for (ConfirmedOrder order : confirmedOrder) {
                // 2. 그 주문서 안에 적힌 상품 리스트를 가져와서 하나씩 꺼냄
                List<ShoppingBasket> items = order.getConfirmedOrder();
                for (ShoppingBasket item : items) {
                    Product p = item.getProductInBasket();
                    // 주문했던 수량
                    int qty = item.getQuantity();

                    // 재고 복구 (기존 재고 + 주문했던 수량)
                    int beforeQty = p.getInventoryQuantity();
                    p.addInventoryQuantity(qty);

                    System.out.printf("%s 재고가 %d개 -> %d게로 업데이트되었습니다.\n\n",
                            p.getProductName(), beforeQty, p.getInventoryQuantity());

                }
            }
            confirmedOrder.clear();
        }
    }
}