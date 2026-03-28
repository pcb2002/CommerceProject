package Lv2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Product> electronicList = new ArrayList<>(List.of(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30)));

        List<Product> clothingList = new ArrayList<>(List.of(new Product("의류1", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("상의", 13500, "상의", 30),
                new Product("하의", 24000, "하의", 30)));

        List<Product> foodList = new ArrayList<>(List.of(new Product("식품1", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("apple", 2500, "빨간색", 30),
                new Product("banana", 5000, "노란색", 30)));

        Category electronicsCategory = new Category("전자제품", electronicList);
        Category clothingCategory = new Category("의류", clothingList);
        Category foodCategory = new Category("식품", foodList);

        List<Category> categories = new ArrayList<>(List.of(electronicsCategory, clothingCategory, foodCategory));
        List<ShoppingBasket> shoppingBasket = new ArrayList<>();
        List<ConfirmedOrder> confirmedOrder = new ArrayList<>();

        ProductManagementSystem pms = new ProductManagementSystem(categories);
        AdminSystem adminSystem = new AdminSystem(sc, pms);
        OrderSystem orderSystem = new OrderSystem(shoppingBasket, confirmedOrder);
        CommerceSystem commerceSystem = new CommerceSystem(sc, orderSystem, pms, adminSystem);

        commerceSystem.start();
    }
}