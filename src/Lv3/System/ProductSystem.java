package Lv3.System;

import Lv3.DataBase.Category;
import Lv3.DataBase.Product;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductSystem {
    private Scanner sc;
    private DecimalFormat df = new DecimalFormat("#,###");

    // DB 없이 순수하게 UI 역할만 수행
    public ProductSystem(Scanner sc) {
        this.sc = sc;
    }

    public Product handleProductSelection(Category c) {
        int size = c.getProducts().size();
        System.out.println("\n[ " + c.getCategoryName() + " 카테고리 상품 목록 ]");

        for (int i = 0; i < size; i++) {
            Product p = c.getProducts().get(i);
            System.out.printf("%d. %-13s | %s원 | %s%n",
                    i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
        }
        System.out.println("0. 돌아가기");

        try {
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) return null;

            if (choice > 0 && choice <= size) {
                Product p = c.getProducts().get(choice - 1);

                System.out.print("\n선택된 상품: ");
                System.out.printf("%s | %s원 | %s\n",
                        p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
                System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인      2. 취소");

                if (sc.nextInt() == 1) {
                    sc.nextLine();
                    return p; // 구매 확정 시 상품 리턴
                } else {
                    sc.nextLine();
                    System.out.println("취소되었습니다.");
                    return null;
                }
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