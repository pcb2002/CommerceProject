package Lv2.System;

import Lv2.DB;
import Lv2.DataBase.Category;
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

    public Category handleCategorySelection() {
        int size = db.getCategories().size();
        //printAllCategories();
        System.out.println("0. 메인으로 돌아가기");

        try {
            System.out.print("카테고리 번호를 입력하세요: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) return null;
            if (choice > 0 && choice <= size) {
                return db.getCategories().get(choice - 1); // 선택한 카테고리 리턴
            } else {
                System.out.println("잘못된 번호입니다.");
                return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("숫자로만 입력해주세요");
            sc.nextLine();
            return null;
        }
    }
}