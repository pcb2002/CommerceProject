package Lv1;

import java.text.DecimalFormat;
import java.util.List;

public class Category {
    private String categoryName;
    private List<Product> products;

    public Category(String  categoryName, List<Product> products) {
        this.categoryName = categoryName;
        this.products = products;
    }

    public String getCategoryName() {
        return categoryName;
    }

    // 등록된 상품의 수 가져오기
    public int getProductSize() {
        return products.size();
    }

    // 등록된 상품 중 하나 출력하기
    public void printProducts(int i) {
        products.get(i).printProduct();
    }

    // 카테고리 출력
    public void printCategory() {
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
    }
}