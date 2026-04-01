package Lv3;

import Lv3.DataBase.Category;
import Lv3.DataBase.Product;
import Lv3.System.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private CategorySystem categorySystem;
    private ProductSystem productSystem;
    private CartSystem cartSystem;
    private OrderSystem orderSystem;
    private AdminSystem adminSystem;

    // 5개의 개별 시스템을 거느리는 유일한 매니저
    public CommerceSystem(Scanner sc, CategorySystem categorySystem, ProductSystem productSystem,
                          CartSystem cartSystem, OrderSystem orderSystem, AdminSystem adminSystem) {
        this.sc = sc;
        this.categorySystem = categorySystem;
        this.productSystem = productSystem;
        this.cartSystem = cartSystem;
        this.orderSystem = orderSystem;
        this.adminSystem = adminSystem;
    }

    // 1. 메인 루프 (라우팅 전담)
    public void start() {
        while(true){
            printMainMenu(); // 메뉴 출력 분리

            try {
                int mainOption = sc.nextInt();
                sc.nextLine();

                int categoryCount = categorySystem.getAllCategories().size();

                if (mainOption > 0 && mainOption <= categoryCount) handleShoppingProcess(mainOption - 1);
                else if (mainOption == 4) handleCartProcess();   // 핸들러 호출
                else if (mainOption == 5) orderSystem.handleCancelProcess();  // 핸들러 호출
                else if (mainOption == 6) adminSystem.handleAdminLogin();     // 핸들러 호출
                else if (mainOption == 0) {
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                }
                else {
                    System.out.println("잘못된 번호입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자로만 입력해주세요");
                sc.nextLine();
            }
        }
    }

    // 2. 메인 메뉴 출력부
    private void printMainMenu() {
        System.out.println("\n[ 실시간 커머스 플랫폼 메인 ]");

        categorySystem.printAllCategories();

        System.out.println("0. 종료           | 프로그램 종료");
        System.out.println("6. 관리자 모드");

        if (!cartSystem.isCartEmpty() || !orderSystem.isOrderHistoryEmpty()) {
            System.out.println("\n[ 주문 관리 ]");
            System.out.println("4. 장바구니 확인  | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. 주문 취소      | 진행중인 주문을 취소합니다.");
        }
    }

    private void handleShoppingProcess(int categoryIdx) {
        while(true) {
            // 1. 카테고리 시스템: 목록 출력 및 선택 전담
            Category selectedCategory = categorySystem.selectedCategory(categoryIdx);
            if (selectedCategory == null) break; // 0번(뒤로가기) 입력 시 쇼핑 종료

            // 2. 프로덕트 시스템: 상품 목록 출력, 선택, 구매 확인 전담
            Product selectedProduct = productSystem.handleProductSelection(selectedCategory);
            if (selectedProduct == null) break; // 0번(뒤로가기) 및 취소 시 카테고리 선택으로 돌아감

            // 3. 카트 시스템: 선택된 상품을 장바구니에 담기 전담
            cartSystem.handleAddToCart(selectedProduct);
        }
    }

    // 3. 주문 처리 핸들러
    private void handleCartProcess() {
        if (cartSystem.isCartEmpty()) {
            System.out.println("장바구니가 비어있어 접근할 수 없습니다.");
        }else {
            cartSystem.printCart();

            int confirmOption = sc.nextInt();
            sc.nextLine();

            if (confirmOption == 1) {
                System.out.println(orderSystem.processOrder());
            }
        }
    }
}