package Lv2;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private OrderSystem orderSystem;
    private List<Category> category;


    public CommerceSystem(Scanner sc, OrderSystem orderSystem, List<Category> category) {
        this.sc = sc;
        this.orderSystem = orderSystem;
        this.category = category;
    }

    public void getProductOption(int categoryNum) {
        while (true) {
            try {
                int categoryOption = sc.nextInt();

                // 1. 뒤로가기 (0번)
                if (categoryOption == 0) {
                    break;
                }

                // 선택된 카테고리 내부 상품의 개수
                int size = category.get(categoryNum).getProductSize();

                // 2. 정상 범위의 상품 선택 (1 ~ 상품 개수)
                if (categoryOption > 0 && categoryOption <= size) {
                    category.get(categoryNum).printProducts(categoryOption - 1);
                    orderSystem.saveProduct(categoryNum, categoryOption - 1);
                    break;
                    // 상품 상세 정보를 본 후 다시 목록을 보여줄지,
                    // 아니면 여기서 break해서 뒤로 갈지 결정
                } else {
                    // 3. 범위를 벗어난 숫자 입력
                    System.out.println("잘못된 번호입니다. 1번부터 "
                            + (size + 1) + "번 사이의 숫자를 입력해주세요.");
                }

            } catch (InputMismatchException e) {
                // 4. 문자가 입력되었을 때 예외 처리
                System.out.println("숫자로만 입력해주세요");
                sc.nextLine(); // 중요! 잘못 입력된 문자열을 비워줘야 무한 루프에 빠지지 않습니다.
            }
        }
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

            if (!orderSystem.isBasketEmpty() || !orderSystem.isOrderEmpty()) {
                System.out.println("\n[ 주문 관리 ]");
                System.out.println("4. 장바구니 확인  | 장바구니를 확인 후 주문합니다.");
                System.out.println("5. 주문 취소  | 진헹중인 주문을 취소합니다.");
            }

            int mainOption = sc.nextInt();

            switch(mainOption) {
                case 1 -> {
                    category.get(0).printCategory();
                    getProductOption(0);
                }
                case 2 -> {
                    category.get(1).printCategory();
                    getProductOption(1);
                }
                case 3 -> {
                    category.get(2).printCategory();
                    getProductOption(2);
                }
                case 4 -> {
                    if (orderSystem.isBasketEmpty())
                        System.out.println("장바구니가 비어있어 접근할 수 없습니다.");
                    else orderSystem.confirmOrder();
                }
                case 5 -> {
                    if (orderSystem.isOrderEmpty())
                        System.out.println("취소할 주문이 없습니다.");
                    else orderSystem.cancleOrder();
                }
                case 0 -> System.out.println("커머스 플랫폼을 종료합니다.");
            }
            if (mainOption == 0) {
                break;
            }
        }
    }
}