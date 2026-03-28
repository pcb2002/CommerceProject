package Lv2;

import java.text.DecimalFormat;
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

    public void printCategory(int idx) {
        Category c = pms.getCategories().get(idx);
        DecimalFormat df = new DecimalFormat("#,###");

        System.out.println("[ " + c.getCategoryName() + " 카테고리]");
        for (int i = 0; i < c.getProducts().size(); i++) {
            Product p = c.getProduct(i);
            System.out.printf("%d. %-13s | %s원 | %s | 재고: %d개\n",
                    i + 1, p.getProductName(), df.format(p.getPrice()),
                    p.getProductDescription(), p.getInventoryQuantity());
        }
    }

    private void addProductList(int Option) {
        Category c = pms.getCategories().get(Option - 1);
        String categoryName = c.getCategoryName();
        System.out.println("[ " + categoryName + " 카테고리에 상품 추가 ]");
        String newName;
        // 상품명 검증
        while (true) {
            System.out.print("상품명을 입력해주세요: ");
            String name = sc.nextLine();

            boolean equals = false;
            for (Product p : c.getProducts()) {
                if (p.getProductName().equals(name)) {
                    System.out.println("같은 이름의 상품이 있습니다. 다시 입력하세요.");
                    equals = true;
                }
            } if (!equals) {
                newName = name;
                break;
            }
        }
        System.out.print("가격을 입력해주세요: ");
        int newPrice = sc.nextInt();
        sc.nextLine();
        System.out.print("상품 설명을 입력해주세요: ");
        String newDescription = sc.nextLine();
        System.out.println("재고수량을 입력해주세요: ");
        int newQuantity = sc.nextInt();
        Product newProduct = new Product(newName, newPrice, newDescription, newQuantity);

        newProduct.printProduct();
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        int saveNewOption = sc.nextInt();
        if (saveNewOption == 1) {
            pms.addProduct(Option - 1, newProduct);
            System.out.println("\n상품이 성공적으로 추가되었습니다!");
        }
    }

    private void selectUpdateOption(Product p) {
        System.out.println("\n수정할 항목을 선택해주세요:");
        System.out.println("1. 가격\n2. 설명\n3. 재고 수량");

        int option = sc.nextInt();
        sc.nextLine(); // 버퍼 비우기

        switch (option) {
            case 1 -> {
                DecimalFormat df = new DecimalFormat("#,###");
                System.out.printf("현재 가격: %s원", df.format(p.getPrice()));
                System.out.print("새로운 가격을 입력해주세요: ");
                int price = sc.nextInt();
                System.out.printf("\n%s의 가격이 %s원 -> %s원으로 수정되었습니다.\n",
                        p.getProductName(), df.format(p.getPrice()), df.format(price));
                pms.updateProductPrice(p, price);
            }
            case 2 -> {
                System.out.println("현재 설명: " + p.getProductDescription());
                System.out.print("새로운 설명을 입력해주세요: ");
                String description = sc.nextLine();
                System.out.printf("\n%s의 설명이 %s \n-> %s으로 수정되었습니다.\n",
                        p.getProductName(), p.getProductDescription(), description);
                pms.updateProductDescription(p, description);
            }
            case 3 -> {
                System.out.println("현재 재고수량: " + p.getInventoryQuantity());
                System.out.print("새로운 재고수량을 입력해주세요: ");
                int quantity = sc.nextInt();
                System.out.printf("\n%s의 재고수량이 %d개 -> %d개로 수정되었습니다.\n",
                        p.getProductName(), p.getInventoryQuantity(), quantity);
                pms.updateInventoryQuantity(p, quantity);
            }
            default -> System.out.println("잘못된 번호입니다.");
        }
    }

    private void deleteProductOption (int deleteOption) {
        Category c = pms.getCategories().get(deleteOption - 1);
        printCategory(deleteOption - 1);
        System.out.println("\n삭제할 상품 번호를 선택하세요.");
        int option = sc.nextInt();
        Product p = c.getProduct(option - 1);
        System.out.printf("%s이(가) 삭제되었습니다.", p.getProductName());
        pms.deleteProduct(deleteOption - 1, option - 1);
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
        sc.nextLine();
        addProductList(addOption);
    }

    public void updateProductProcess() {
        System.out.println("\n수정할 상품명을 입력해주세요: ");
        String uName = sc.nextLine();
        boolean existence = false;
        for (Category c : pms.getCategories()) {
            for (Product p : c.getProducts()) {
                if (uName.equals(p.getProductName())){
                    existence = true;
                    System.out.println("현재 상품 정보: ");
                    p.printProduct();
                    selectUpdateOption(p);
                }
            }
        }
        if (!existence) {
            System.out.println("상품 정보가 없습니다.");
        }
    }

    public void deleteProductProcess() {
        System.out.println("\n어느 카테고리에 상품을 삭제하시겠습니까?");
        for (int i = 0; i < pms.getCategories().size(); i++) {
            Category c = pms.getCategories().get(i);
            // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
            System.out.println((i + 1) + ". " + c.getCategoryName());
        }
        int deleteOption = sc.nextInt();
        deleteProductOption(deleteOption);
    }

    public void showAllProduct() {
        for (int i = 0; i < pms.getCategories().size(); i ++) {
            printCategory(i);
        }
    }
}