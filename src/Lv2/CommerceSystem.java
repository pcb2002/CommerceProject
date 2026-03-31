package Lv2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private OrderSystem orderSystem;
    private AdminSystem adminSystem;
    private DataBase db;

    private boolean isAdminLocked = false;

    public CommerceSystem(Scanner sc, DataBase db, OrderSystem orderSystem, AdminSystem adminSystem) {
        this.sc = sc;
        this.db = db;
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

                int categoryCount = db.getCategories().size();

                if (mainOption > 0 && mainOption <= categoryCount) orderSystem.handleShopping(mainOption - 1);
                else if (mainOption == 4) handleOrderProcess();   // 핸들러 호출
                else if (mainOption == 5) handleCancelProcess();  // 핸들러 호출
                else if (mainOption == 6) handleAdminLogin();     // 핸들러 호출
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

        for (int i = 0; i < db.getCategories().size(); i++) {
            Category c = db.getCategories().get(i);
            System.out.println((i + 1) + ". " + c.getCategoryName());
        }

        System.out.println("0. 종료           | 프로그램 종료");
        System.out.println("6. 관리자 모드");

        if (!orderSystem.isCartEmpty() || !orderSystem.isOrderHistoryEmpty()) {
            System.out.println("\n[ 주문 관리 ]");
            System.out.println("4. 장바구니 확인  | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. 주문 취소      | 진행중인 주문을 취소합니다.");
        }
    }

    // 3. 주문 처리 핸들러
    private void handleOrderProcess() {
        if (orderSystem.isCartEmpty()) {
            System.out.println("장바구니가 비어있어 접근할 수 없습니다.");
            return;
        }

        System.out.println("\n[ 장바구니 내역 ]");
        System.out.print(orderSystem.getCartDetails());
        System.out.println("\n[ 총 주문 금액 ]\n" + orderSystem.calculateTotalPrice() + "원\n");
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");

        int confirmOption = sc.nextInt();
        sc.nextLine();

        if (confirmOption == 1) {
            System.out.println(orderSystem.processOrder());
        }
    }

    // 4. 주문 취소 핸들러
    private void handleCancelProcess() {
        if (orderSystem.isOrderHistoryEmpty()) {
            System.out.println("취소할 주문이 없습니다.");
            return;
        }

        System.out.println("\n[ 최근 주문 내역 ]");
        System.out.print(orderSystem.getOrderHistoryDetails());
        System.out.println("\n주문을 취소하시겠습니까?");
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기");

        int cancelOption = sc.nextInt();
        sc.nextLine();

        if (cancelOption == 1) {
            System.out.println(orderSystem.cancelAllOrders());
        }
    }

    // 5. 관리자 로그인 핸들러
    private void handleAdminLogin() {
        if (isAdminLocked) {
            System.out.println("관리자 모드가 영구적으로 잠겼습니다. 고객센터에 문의하세요.");
            return;
        }

        int count = 1;
        while (count <= 3) {
            System.out.print("관리자 비밀번호를 입력해주세요: ");
            String pw = sc.next();
            sc.nextLine();

            if (adminSystem.correctPassword(pw)) {
                System.out.println("관리자 인증에 성공했습니다.");
                adminSystem.showAdminMenu();
                break;
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
}