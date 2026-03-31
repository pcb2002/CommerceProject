package Lv2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. 상품 리스트 초기화 (Category 내부에서 ArrayList로 변환하므로 여기선 List.of만 써도 안전합니다)
        List<Product> electronicList = List.of(
                new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30)
        );

        List<Product> clothingList = List.of(
                new Product("겨울 패딩", 120000, "따뜻한 오리털 패딩", 30),
                new Product("반팔 티셔츠", 15000, "편안한 면 100% 티셔츠", 30),
                new Product("청바지", 45000, "슬림핏 데님 팬츠", 30)
        );

        List<Product> foodList = List.of(
                new Product("닭가슴살", 3000, "단백질 보충용", 30),
                new Product("사과", 2500, "당도 높은 꿀사과", 30),
                new Product("바나나", 5000, "신선한 바나나 한 송이", 30)
        );

        // 2. 카테고리 생성
        Category electronicsCategory = new Category("전자제품", electronicList);
        Category clothingCategory = new Category("의류", clothingList);
        Category foodCategory = new Category("식품", foodList);

        List<Category> categories = new ArrayList<>(List.of(electronicsCategory, clothingCategory, foodCategory));

        // 3. 주문 및 장바구니 리스트 생성
        List<CartItem> cartItems = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();

        // 4. 시스템 객체 생성 및 의존성 주입 (Dependency Injection)
        DataBase dataBase = new DataBase(categories, cartItems, orderItems);
        OrderSystem orderSystem = new OrderSystem(sc, dataBase);
        AdminSystem adminSystem = new AdminSystem(sc, dataBase);
        CommerceSystem commerceSystem = new CommerceSystem(sc, dataBase, orderSystem, adminSystem);

        // 5. 프로그램 시작
        commerceSystem.start();
    }
}