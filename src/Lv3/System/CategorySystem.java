package Lv3.System;

import Lv3.DB;
import Lv3.DataBase.Category;
import Lv3.DataBase.Product;

import java.text.DecimalFormat;
import java.util.List;

public class CategorySystem {
    private DB db;
    private Category c;

    private DecimalFormat df = new DecimalFormat("#,###");

    public CategorySystem(DB db) {
        this.db = db;
    }

    public List<Category> getAllCategories() {
        return db.getCategories();
    }

    public void selectedCategory (int idx) {
        this.c = db.getCategories().get(idx);
    }

    public Category getC() {
        return c;
    }

    public int size() {
        return  c.getProducts().size();
    }

    public void printAllCategories() {
        for (int i = 0; i < db.getCategories().size(); i++) {
            Category c = db.getCategories().get(i);
            System.out.println((i + 1) + ". " + c.getCategoryName());
        }
    }

    public void printCategorySelection(Category selectedCategory) {
        System.out.println("[ " + selectedCategory.getCategoryName() + " 카테고리 ]");
        System.out.println("1. 전체 상품 보기");
        System.out.println("2. 가격대별 필터링 (100만원 이하)");
        System.out.println("3. 가격대별 필터링 (100만원 초과)");
        System.out.println("0. 뒤로가기");
    }

    public void printAllCategoryProducts() {
        System.out.println("\n[ " + c.getCategoryName() + " 카테고리 상품 목록 ]");

        for (int i = 0; i < size(); i++) {
            Product p = c.getProducts().get(i);
            System.out.printf("%d. %-13s | %s원 | %s%n",
                    i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
        }
        System.out.println("0. 돌아가기");
    }

    public Category downFilter(int price) {
        System.out.println("\n[ " + (price / 10000) + "만원 이하 상품 목록 ]");

        // 필터링된 결과를 List<Product> 형태로 변환하여 저장
        List<Product> filteredProducts = c.getProducts().stream()
                .filter(product -> product.getPrice() <= price).toList();

        // 저장된 리스트를 출력
        for (int i = 0; i < filteredProducts.size(); i++) {
            Product p = filteredProducts.get(i);
            System.out.print((i + 1)  + ". ");
            p.printProduct();
        }
        System.out.println("0. 돌아가기");

        return new Category("filter", filteredProducts);
    }

    public Category upFilter(int price) {
        System.out.println("\n[ " + (price / 10000) + "만원 이하 상품 목록 ]");

        // 필터링된 결과를 List<Product> 형태로 변환하여 저장
        List<Product> filteredProducts = c.getProducts().stream()
                .filter(product -> product.getPrice() > price).toList();

        // 저장된 리스트를 출력
        for (int i = 0; i < filteredProducts.size(); i++) {
            Product p = filteredProducts.get(i);
            System.out.print((i + 1)  + ". ");
            p.printProduct();
        }
        System.out.println("0. 돌아가기");

        return new Category("filter", filteredProducts);
    }
}