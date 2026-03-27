package step3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category {
    private String categoryName;
    private List<Product> products;

    public Category(String  categoryName, List<Product> products) {
        this.categoryName = categoryName;
        this.products = products;
    }

    public static Category createElectronicsCategory() {
        List<Product> electronicList = List.of(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30));
        return new Category("전자제품", electronicList);
    }

    public static Category createClothingCategory() {
        List<Product> clothingList = List.of(new Product("의류1", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30));
        return new Category("의류", clothingList);
    }

    public static Category createFoodCategory() {
        List<Product> foodList = List.of(new Product("식품1", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30));
        return new Category("식품", foodList);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void printCategory() {
        Scanner sc = new Scanner(System.in);

        System.out.println("[ " + categoryName + " 카테고리]");
        // 금액 콤마 포맷팅
        DecimalFormat df = new DecimalFormat("#,###");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
            System.out.printf("%d. %-13s | %s원 | %s%n",
                    i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
        }
        System.out.println("0. 뒤로가기");

        int categoryOption = sc.nextInt();
        for (int i = 0; i < products.size(); i++) {
            if (categoryOption == 0){
                break;
            }
            else if (categoryOption == i + 1) {
                products.get(i).printProduct();
            }
        }

    }

}
