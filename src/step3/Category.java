package step3;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class Category {
    private String categoryName;
    private List<Product> products;

    public Category(String  categoryName) {
        this.categoryName = categoryName;
        if (categoryName.equals("전자제품")) {
            this.products = electronicsList();
        } else if(categoryName.equals("의류")) {
            this.products = clothingList();
        } else if (categoryName.equals(("식품"))) {
            this.products = foodList();
        }
    }

    private List<Product> electronicsList() {
        return List.of(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30));
    }

    private List<Product> clothingList() {
        return List.of(new Product("의류1", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30));
    }

    private List<Product> foodList() {
        return List.of(new Product("식품1", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30));
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
