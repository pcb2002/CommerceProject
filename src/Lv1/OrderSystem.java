package Lv1;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class OrderSystem {
    private Scanner sc;
    private List<ShoppingBasket> basketList;
    private List<Category> category;

    public OrderSystem(Scanner sc, List<ShoppingBasket> basketList, List<Category> category) {
        this.sc = sc;
        this.basketList = basketList;
        this.category = category;
    }

    public void saveProduct(int i, int categoryNum) {
        // 1. 카테고리에서 상품 객체를 먼저 꺼냅니다.
        Category selectedCategory = category.get(i);
        Product selectedProduct = selectedCategory.getProduct(categoryNum - 1);

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

            System.out.println(selectedProduct.getProductName() + "이(가) 장바구니에 담겼습니다.");
        }
    }

    public void printBasketList() {
        System.out.println("[ 장바구니 내역 ]");
        // 금액 콤마 포맷팅
        DecimalFormat df = new DecimalFormat("#,###");

        for (int i = 0; i < basketList.size(); i++) {
            Product p = basketList.get(i).getProductInBasket();
            // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
            System.out.printf("%-13s | %s원 | %s%n| 수량: %d개\n",
                    p.getProductName(), df.format(p.getPrice()), p.getProductDescription(), basketList.get(i).getQuantity());
        }
    }

    public void printTotalPrice() {
        System.out.println("[ 총 주문 금액 ]");
        DecimalFormat df = new DecimalFormat("#,###");
        int totalPrice = 0;

        for (int i = 0; i < basketList.size(); i++) {
            Product p = basketList.get(i).getProductInBasket();
            totalPrice += p.getPrice();
        }
        System.out.println(df.format(totalPrice) + "원\n");
    }

    public void confirmOrder() {
        printBasketList();
        printTotalPrice();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
        // 확정된 주문 리스트는 어디에?

    }

    // 상품 수량 관리 메서드는 어떻게?

    public void cancleOrder() {
        System.out.println("주문을 취소하시겠습니까?");
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기");

        int cancleOption = sc.nextInt();

        if (cancleOption == 1) {
            basketList.clear();
        }
    }
}
