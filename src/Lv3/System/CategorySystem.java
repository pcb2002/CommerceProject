package Lv3.System;

import Lv3.DB;
import Lv3.DataBase.Category;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CategorySystem {
    private Scanner sc;
    private DB db;

    public CategorySystem(Scanner sc, DB db) {
        this.sc = sc;
        this.db = db;
    }

    public List<Category> getAllCategories() {
        return db.getCategories();
    }

    public Category selectedCategory(int idx) {
        return db.getCategories().get(idx);
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
}