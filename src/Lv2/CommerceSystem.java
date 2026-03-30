package Lv2;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private OrderSystem orderSystem;
    private CategoryController categoryController; // PMS 대신 CategoryController
    private AdminSystem adminSystem;

    private boolean isAdminLocked = false;

    public CommerceSystem(Scanner sc, OrderSystem orderSystem,
                          CategoryController categoryController, AdminSystem adminSystem) {
        this.sc = sc;
        this.orderSystem = orderSystem;
        this.categoryController = categoryController;
        this.adminSystem = adminSystem;
    }

    public void start() {
        while (true) {
            printMainMenu();
            try {
                int mainOption = sc.nextInt();
                sc.nextLine();

                int categoryCount = categoryController.getCategories().size();

                if (mainOption > 0 && mainOption <= categoryCount) {
                    handleShopping(mainOption - 1);
                }
                else if (mainOption == 4) handleOrderProcess();
                else if (mainOption == 5) handleCancelProcess();
                else if (mainOption == 6) handleAdminLogin();
                else if (mainOption == 0) {
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                }
                else {
                    System.out.println("잘못된 번호입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자로만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n[ 실시간 커머스 플랫폼 메인 ]");

        for (int i = 0; i < categoryController.getCategories().size(); i++) {
            System.out.println((i + 1) + ". " + categoryController.getCategories().get(i).getCategoryName());
        }

        System.out.println("0. 종료           | 프로그램 종료");
        System.out.println("6. 관리자 모드");

        if (!orderSystem.isCartEmpty() || !orderSystem.isOrderHistoryEmpty()) {
            System.out.println("\n[ 주문 관리 ]");
            System.out.println("4. 장바구니 확인  | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. 주문 취소      | 진행중인 주문을 취소합니다.");
        }
        System.out.print("메뉴 선택: ");
    }

    private void handleShopping(int categoryIdx) {
        Category category = categoryController.getCategories().get(categoryIdx);
        System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");

        DecimalFormat df = new DecimalFormat("#,###");
        for (int i = 0; i < category.getProducts().size(); i++) {
            Product p = category.getProducts().get(i);
            System.out.printf("%d. %-13s | %s원 | %s%n",
                    i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
        }
        System.out.println("0. 뒤로가기");
        System.out.print("상품 선택: ");

        int productOption = sc.nextInt();
        sc.nextLine();

        if (productOption == 0) return;

        if (productOption > 0 && productOption <= category.getProductSize()) {
            Product selectedProduct = category.getProduct(productOption - 1);
            System.out.println("\n[" + selectedProduct.getProductName() + "]을(를) 장바구니에 추가하시겠습니까?");
            System.out.println("1. 확인      2. 취소");

            if (sc.nextInt() == 1) {
                orderSystem.addToCart(selectedProduct, 1);
                System.out.println(selectedProduct.getProductName() + "이(가) 장바구니에 담겼습니다.");
            }
            sc.nextLine();
        } else {
            System.out.println("잘못된 상품 번호입니다.");
        }
    }

    private void handleOrderProcess() {
        if (orderSystem.isCartEmpty()) {
            System.out.println("장바구니가 비어있어 접근할 수 없습니다.");
            return;
        }

        System.out.println("\n[ 장바구니 내역 ]");
        System.out.print(orderSystem.getCartDetails());
        System.out.println("\n[ 총 주문 금액 ] : " + orderSystem.calculateTotalPrice() + "원");
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");

        if (sc.nextInt() == 1) {
            String result = orderSystem.processOrder();
            System.out.println(result);
        }
        sc.nextLine();
    }

    private void handleCancelProcess() {
        if (orderSystem.isOrderHistoryEmpty()) {
            System.out.println("취소할 주문이 없습니다.");
            return;
        }

        System.out.println("\n[ 최근 주문 내역 ]");
        System.out.print(orderSystem.getOrderHistoryDetails());
        System.out.println("\n주문을 취소하시겠습니까?");
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기");

        if (sc.nextInt() == 1) {
            String result = orderSystem.cancelAllOrders();
            System.out.println(result);
        }
        sc.nextLine();
    }

    private void handleAdminLogin() {
        if (isAdminLocked) {
            System.out.println("관리자 모드가 영구적으로 잠겼습니다. 고객센터에 문의하세요.");
            return;
        }

        int count = 1;
        while (count <= 3) {
            System.out.print("\n관리자 비밀번호를 입력해주세요: ");
            String pw = sc.next();
            sc.nextLine();

            if (adminSystem.correctPassword(pw)) {
                System.out.println("관리자 인증에 성공했습니다.");
                showAdminMenu();
                return;
            } else {
                System.out.println("비밀번호가 틀렸습니다. (현재 " + count + "회 틀림 / 최대 3회)");
                if (count == 3) {
                    isAdminLocked = true;
                    System.out.println("비밀번호 3회 오류로 관리자 모드가 차단되었습니다.");
                }
                count++;
            }
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n[ 관리자 모드 ]");
            System.out.println("1. 상품 등록 \n2. 상품 수정\n3. 상품 삭제\n4. 전체 상품 현황\n0. 메인으로 돌아가기");
            System.out.print("메뉴 선택: ");

            int adminOption = sc.nextInt();
            sc.nextLine();

            if (adminOption == 0) break;

            switch (adminOption) {
                case 1 -> adminSystem.addProductProcess();
                case 2 -> adminSystem.updateProductProcess();
                case 3 -> adminSystem.deleteProductProcess();
                case 4 -> adminSystem.showAllProduct();
                default -> System.out.println("잘못된 번호입니다.");
            }
        }
    }
}