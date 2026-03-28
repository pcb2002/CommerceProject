package Lv2;

import java.util.List;

public class ProductManagementSystem {
    // 속성
    private List<Category> categories; // 이제 상품 창고는 PMS가 관리합니다.

    // 생성자
    public ProductManagementSystem(List<Category> categories) {
        this.categories = categories;
    }

    // 기능
    public List<Category> getCategories() {
        return categories;
    }

    // 관리자용: 새로운 상품 추가
    public void addProduct(int categoryIdx, Product newProduct) {
        categories.get(categoryIdx).getProducts().add(newProduct);
    }

    // 관라지용: 상품 수정
    public void updateProductPrice(Product p, int price) {
        p.setPrice(price);
    }

    public void updateProductDescription(Product p, String description) {
        p.setProductDescription(description);
    }

    public void updateInventoryQuantity(Product p, int quantity) {
        p.setInventoryQuantity(quantity);
    }
    // 관리자용: 상품 삭제
    public void deleteProduct(int categoryIdx, int productIdx) {
        categories.get(categoryIdx).getProducts().remove(productIdx);
    }
}