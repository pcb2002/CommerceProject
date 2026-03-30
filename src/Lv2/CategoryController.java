package Lv2;

import java.util.List;

public class CategoryController {
    private List<Category> categories;

    public CategoryController(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    // 카테고리에 새로운 상품 추가 (리스트 관리)
    public void addProduct(int categoryIdx, Product newProduct) {
        categories.get(categoryIdx).getProducts().add(newProduct);
    }

    // 카테고리에서 상품 삭제 (리스트 관리)
    public void deleteProduct(int categoryIdx, int productIdx) {
        categories.get(categoryIdx).getProducts().remove(productIdx);
    }
}