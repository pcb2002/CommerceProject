package step3;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private List<Category> category;

    public CommerceSystem(Scanner sc) {
        this.sc = sc;
        this.category = initCategories();
    }




    private List<Category> initCategories() {
        return List.of(
                new Category("전자제품"),
                new Category("의류"),
                new Category("식품")
        );
    }


    public void start() {
        while(true){
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");

            for (int i = 0; i < category.size(); i++) {
                Category c = category.get(i);
                // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
                System.out.println((i + 1) + ". " + c.getCategoryName());
            }

            System.out.println("0. 종료           | 프로그램 종료");

            int mainOption = sc.nextInt();

            switch(mainOption) {
                case 1 -> category.get(0).printCategory();
                case 2 -> category.get(1).printCategory();
                case 3 -> category.get(2).printCategory();
                case 0 -> System.out.println("커머스 플랫폼을 종료합니다.");
            }
            if (mainOption == 0) {
                break;
            }
        }

    }
}