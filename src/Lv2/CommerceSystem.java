package Lv2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private OrderSystem orderSystem;
    private ProductManagementSystem pms;
    private AdminSystem adminSystem;

    private boolean isAdminLocked = false; // 관리자 모드 잠금 상태 (기본값 false)

    public CommerceSystem(Scanner sc, OrderSystem orderSystem,
                          ProductManagementSystem pms, AdminSystem adminSystem) {
        this.sc = sc;
        this.orderSystem = orderSystem;
        this.pms = pms;
        this.adminSystem = adminSystem;
    }

    private void saveProductOption(int i, int categoryNum) {
        // 1. 카테고리에서 상품 객체를 먼저 꺼냅니다.
        Category selectedCategory = pms.getCategories().get(i);
        Product selectedProduct = selectedCategory.getProduct(categoryNum);

        if (selectedProduct == null) return;

        selectedCategory.printChoicedProducts(categoryNum);
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        int saveOption = sc.nextInt();
        if (saveOption == 1) {
            // 여기서 수량을 물어볼 수도 있습니다. (예: 1개)
            int quantity = 1;

            // 타입이 일치하는 리스트에 추가
            orderSystem.saveProduct(selectedProduct, quantity);

            System.out.println(selectedProduct.getProductName() + "이(가) 장바구니에 담겼습니다.\n");
        }
    }

    private void getProductOption(int categoryNum) {
        while (true) {
            try {
                int categoryOption = sc.nextInt();

                // 1. 뒤로가기 (0번)
                if (categoryOption == 0) {
                    break;
                }

                // 선택된 카테고리 내부 상품의 개수
                int size = pms.getCategories().get(categoryNum).getProductSize();

                // 2. 정상 범위의 상품 선택 (1 ~ 상품 개수)
                if (categoryOption > 0 && categoryOption <= size) {
                    pms.getCategories().get(categoryNum).printProducts(categoryOption - 1);
                    saveProductOption(categoryNum, categoryOption - 1);
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

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n[ 관리자 모드 ]");
            System.out.println("1. 상품 등록 \n2. 상품 수정\n3. 상품 삭제\n4. 전체 상품 현황\n0. 메인으로 돌아가기");

            int adminOption = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

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

    public void start() {
        while(true){
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");

            for (int i = 0; i < pms.getCategories().size(); i++) {
                Category c = pms.getCategories().get(i);
                // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
                System.out.println((i + 1) + ". " + c.getCategoryName());
            }

            System.out.println("0. 종료           | 프로그램 종료");
            System.out.println("6. 관리자 모드");

            if (!orderSystem.isBasketEmpty() || !orderSystem.isOrderEmpty()) {
                System.out.println("\n[ 주문 관리 ]");
                System.out.println("4. 장바구니 확인  | 장바구니를 확인 후 주문합니다.");
                System.out.println("5. 주문 취소  | 진헹중인 주문을 취소합니다.");
            }

            int mainOption = sc.nextInt();

            switch(mainOption) {
                case 1 -> {
                    pms.getCategories().get(0).printCategory();
                    getProductOption(0);
                }
                case 2 -> {
                    pms.getCategories().get(1).printCategory();
                    getProductOption(1);
                }
                case 3 -> {
                    pms.getCategories().get(2).printCategory();
                    getProductOption(2);
                }
                case 4 -> {
                    if (orderSystem.isBasketEmpty())
                        System.out.println("장바구니가 비어있어 접근할 수 없습니다.");
                    else {
                        int confirmOption = sc.nextInt();
                        orderSystem.confirmOrder(confirmOption);
                    }
                }
                case 5 -> {
                    if (orderSystem.isOrderEmpty())
                        System.out.println("취소할 주문이 없습니다.");
                    else {
                        int cancelOption = sc.nextInt();
                        orderSystem.cancelOrder(cancelOption);
                    }
                }
                case 6 -> {
                    // 1. 이미 잠겨있는지 먼저 확인
                    if (isAdminLocked) {
                        System.out.println("관리자 모드가 영구적으로 잠겼습니다. 고객센터에 문의하세요.");
                        break;
                    }

                    int count = 1;

                    while (count <= 3) {
                        System.out.print("관리자 비밀번호를 입력해주세요: ");
                        String pw = sc.next(); // nextLine() 대신 next() 권장 (공백 전까지 입력)

                        if (adminSystem.correctPassword(pw)) {
                            System.out.println("관리자 인증에 성공했습니다.");
                            showAdminMenu(); // 관리자 메뉴 진입
                            break;
                        } else {
                            System.out.println("비밀번호가 틀렸습니다. (현재 " + count + "회 틀림 / 최대 3회)");
                            if (count == 3) {
                                isAdminLocked = true; // 3회 실패 시 잠금 설정
                                System.out.println("비밀번호 3회 오류로 관리자 모드가 차단되었습니다.");
                            }
                            count++;
                        }
                    }
                }
                case 0 -> System.out.println("커머스 플랫폼을 종료합니다.");
                default -> System.out.println("잘못된 번호입니다.");
            }
            if (mainOption == 0) {
                break;
            }
        }
    }
}