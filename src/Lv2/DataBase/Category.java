package Lv2.DataBase;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String categoryName;
    private List<Product> products;

    private Product selectedProduct;

    public Category(String categoryName, List<Product> products) {
        this.categoryName = categoryName;
        // 핵심 방어 로직:
        // 외부에서 List.of() 같은 불변 리스트를 넘기더라도,
        // 내부에서는 관리자 모드에서 상품 추가/삭제가 가능하도록 가변 리스트로 복사하여 저장
        this.products = new ArrayList<>(products);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getProductSize() {
        return products.size();
    }

    public Product selectedProduct(int index) {
        // 인덱스가 리스트 범위를 벗어나지 않는지 확인 (방어적 코드)
        if (index >= 0 && index < products.size()) {
            this.selectedProduct =  products.get(index);
            return selectedProduct;
        }

        // 여기서 System.out.println()을 쓰지 않고 null만 리턴하여,
        // 에러 메시지 출력의 책임도 컨트롤러(Commerce/Admin)에게 넘김
        return null;
    }
}