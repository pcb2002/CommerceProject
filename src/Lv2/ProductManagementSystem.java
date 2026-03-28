package Lv2;

import java.util.List;

public class ProductManagementSystem {
    private List<Category> categories; // 이제 상품 창고는 PMS가 관리합니다.

    public ProductManagementSystem(List<Category> categories) {
        this.categories = categories;
    }

    // 관리자용: 새로운 상품 추가
    public void addProduct(int categoryIdx, Product newProduct) {
        categories.get(categoryIdx).addProduct(newProduct);
    }

    // 관리자용: 상품 삭제
    public void deleteProduct(int categoryIdx, int productIdx) {
        categories.get(categoryIdx).removeProduct(productIdx);
    }

    // 공용: 카테고리 목록 주기
    public List<Category> getCategories() {
        return categories;
    }
}