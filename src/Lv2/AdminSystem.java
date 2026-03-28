package Lv2;

import java.util.Scanner;

public class AdminSystem {
    private Scanner sc;
    private ProductManagementSystem pms;

    public AdminSystem(Scanner sc, ProductManagementSystem pms) {
        this.sc = sc;
        this.pms = pms;
    }

    public boolean correctPassword(String pw) {
        return pw.equals("admin123");
    }

    private void addProductList(int Option) {
        String categoryName = pms.getCategories().get(Option).getCategoryName();
        System.out.println("[ " + categoryName + " 카테고리에 상품 추가 ]");
        System.out.println("상품명을 입력해주세요: ");
        String newName = sc.nextLine();
        System.out.println("가격을 입력해주세요: ");
        int newPrice = sc.nextInt();
        System.out.println("상품 설명을 입력해주세요: ");
        String newDescription = sc.nextLine();
        System.out.println("재고수량을 입력해주세요: ");
        int newQuantity = sc.nextInt();
        Product newProduct = new Product(newName, newPrice, newDescription, newQuantity);

        newProduct.printProduct();
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        int saveNewOption = sc.nextInt();
        if (saveNewOption == 1) {
            pms.addProduct(Option, newProduct);
        }
    }

    // 상품 등록 프로세스
    public void addProductProcess() {
        System.out.println("\n어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < pms.getCategories().size(); i++) {
            Category c = pms.getCategories().get(i);
            // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
            System.out.println((i + 1) + ". " + c.getCategoryName());
        }
        int addOption = sc.nextInt();
        addProductList(addOption);
    }

    public void updateProductProcess() {

    }

    public void deleteProductProcess() {

    }

    public void showAllProduct() {
    }
}