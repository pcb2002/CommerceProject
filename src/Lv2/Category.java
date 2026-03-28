package Lv2;

import java.text.DecimalFormat;
import java.util.List;

public class Category {
    private String categoryName;
    private List<Product> products;

    public Category(String  categoryName, List<Product> products) {
        this.categoryName = categoryName;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int i) {
        // 인덱스가 리스트 범위를 벗어나지 않는지 확인 (방어적 코드)
        if (i >= 0 && i < products.size()) {
            return products.get(i);
        }

        System.out.println("해당 인덱스에 상품이 존재하지 않습니다.");
        return null;
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
        System.out.print("선택된 상품: ");
        products.get(i).printProduct();
    }

    public void printChoicedProducts(int i) {
        products.get(i).printChoiceProduct();
    }
}