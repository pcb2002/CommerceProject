package Lv2;

import java.util.Scanner;

public class AdminSystem {
    private Scanner sc;

    public AdminSystem(Scanner sc) {
        this.sc = sc;
    }

    // 관리자 메뉴 루프
    public void startAdminMode(ProductManagementSystem pms) {
        System.out.println("\n🛠️ 관리자 모드에 접속했습니다.");
        System.out.println("1. 상품 등록  2. 상품 수정  3. 상품 삭제  0. 나가기");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> addProductProcess(pms);
            case 2 -> updateProductProcess(pms);
            case 3 -> deleteProductProcess(pms);
        }
    }
    // 상품 등록 프로세스
    private void addProductProcess(ProductManagementSystem pms) {
        System.out.println("등록할 카테고리 번호를 선택하세요...");
        // 1. 여기서 Scanner로 이름, 가격 등을 입력받습니다.
        // 2. 입력받은 정보로 new Product(...) 객체를 만듭니다.
        // 3. pms.addProduct(categoryIdx, newProduct)를 호출하여 저장합니다!
    }
    private void deleteProductProcess(ProductManagementSystem pms) {
    }

    private void updateProductProcess(ProductManagementSystem pms) {
    }


}