package Lv2;

import java.text.DecimalFormat;
import java.util.Scanner;

public class AdminSystem {
    private Scanner sc;
    private CategoryController categoryController; // 이름 변경 반영
    private DecimalFormat df = new DecimalFormat("#,###");

    public AdminSystem(Scanner sc, CategoryController categoryController) {
        this.sc = sc;
        this.categoryController = categoryController;
    }

    public boolean correctPassword(String pw) {
        return pw.equals("admin123");
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n[ 관리자 모드 ]");
            System.out.println("1. 상품 등록 \n2. 상품 수정\n3. 상품 삭제\n4. 전체 상품 현황\n0. 메인으로 돌아가기");
            System.out.print("메뉴 선택: ");

            int adminOption = sc.nextInt();
            sc.nextLine();

            if (adminOption == 0) break;

            switch (adminOption) {
                case 1 -> addProductProcess();
                case 2 -> updateProductProcess();
                case 3 -> deleteProductProcess();
                case 4 -> showAllProduct();
                default -> System.out.println("잘못된 번호입니다.");
            }
        }
    }

    public String getCategoryDetails(int idx) {
        Category c = categoryController.getCategories().get(idx);
        StringBuilder sb = new StringBuilder();

        sb.append("[ ").append(c.getCategoryName()).append(" 카테고리 ]\n");
        for (int i = 0; i < c.getProducts().size(); i++) {
            Product p = c.getProduct(i);
            sb.append(String.format("%d. %-13s | %s원 | %s | 재고: %d개\n",
                    i + 1, p.getProductName(), df.format(p.getPrice()),
                    p.getProductDescription(), p.getInventoryQuantity()));
        }
        return sb.toString();
    }

    public void showAllProduct() {
        System.out.println("\n[ 전체 상품 현황 ]");
        for (int i = 0; i < categoryController.getCategories().size(); i++) {
            System.out.print(getCategoryDetails(i));
        }
    }

    public void addProductProcess() {
        System.out.println("\n[ 상품 등록 ]\n어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < categoryController.getCategories().size(); i++) {
            System.out.println((i + 1) + ". " + categoryController.getCategories().get(i).getCategoryName());
        }
        System.out.print("선택: ");
        int categoryIdx = sc.nextInt() - 1;
        sc.nextLine();

        if (categoryIdx >= 0 && categoryIdx < categoryController.getCategories().size()) {
            executeAddProduct(categoryIdx);
        } else {
            System.out.println("잘못된 카테고리 번호입니다.");
        }
    }

    private void executeAddProduct(int categoryIdx) {
        Category c = categoryController.getCategories().get(categoryIdx);
        System.out.println("\n[ " + c.getCategoryName() + " 카테고리에 상품 추가 ]");

        String newName = getValidNewProductName(c);

        System.out.print("가격을 입력해주세요: ");
        int newPrice = sc.nextInt();
        sc.nextLine();

        System.out.print("상품 설명을 입력해주세요: ");
        String newDescription = sc.nextLine();

        System.out.print("재고수량을 입력해주세요: ");
        int newQuantity = sc.nextInt();
        sc.nextLine();

        Product newProduct = new Product(newName, newPrice, newDescription, newQuantity);

        System.out.println("\n[ 등록할 상품 정보 ]");
        newProduct.printProduct();
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        if (sc.nextInt() == 1) {
            categoryController.addProduct(categoryIdx, newProduct);
            System.out.println("상품이 성공적으로 추가되었습니다!");
        } else {
            System.out.println("상품 추가가 취소되었습니다.");
        }
        sc.nextLine();
    }

    // 같은 상품이름 있는지 검증
    private String getValidNewProductName(Category c) {
        while (true) {
            System.out.print("상품명을 입력해주세요: ");
            String name = sc.nextLine();

            boolean isDuplicate = false;
            for (Product p : c.getProducts()) {
                if (p.getProductName().equals(name)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("같은 이름의 상품이 있습니다. 다시 입력하세요.");
            } else {
                return name;
            }
        }
    }

    public void updateProductProcess() {
        System.out.print("\n[ 상품 수정 ]\n수정할 상품명을 입력해주세요: ");
        String targetName = sc.nextLine();
        Product targetProduct = null;

        for (Category c : categoryController.getCategories()) {
            for (Product p : c.getProducts()) {
                if (targetName.equals(p.getProductName())) {
                    targetProduct = p;
                    break;
                }
            }
            if (targetProduct != null) break;
        }

        if (targetProduct != null) {
            System.out.println("\n[ 현재 상품 정보 ]");
            targetProduct.printProduct();
            executeUpdateOption(targetProduct);
        } else {
            System.out.println("상품 정보가 없습니다.");
        }
    }

    private void executeUpdateOption(Product p) {
        System.out.println("\n수정할 항목을 선택해주세요:");
        System.out.println("1. 가격\n2. 설명\n3. 재고 수량");
        System.out.print("선택: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1 -> {
                System.out.printf("현재 가격: %s원\n", df.format(p.getPrice()));
                System.out.print("새로운 가격을 입력해주세요: ");
                int newPrice = sc.nextInt();
                sc.nextLine();

                // 객체지향적 처리: Product 객체 스스로 상태를 바꾸도록 지시!
                p.setPrice(newPrice);
                System.out.printf("%s의 가격이 %s원으로 수정되었습니다.\n", p.getProductName(), df.format(newPrice));
            }
            case 2 -> {
                System.out.println("현재 설명: " + p.getProductDescription());
                System.out.print("새로운 설명을 입력해주세요: ");
                String newDesc = sc.nextLine();

                p.setDescription(newDesc);
                System.out.printf("%s의 설명이 [%s]으로 수정되었습니다.\n", p.getProductName(), newDesc);
            }
            case 3 -> {
                System.out.println("현재 재고수량: " + p.getInventoryQuantity() + "개");
                System.out.print("새로운 재고수량을 입력해주세요: ");
                int newQty = sc.nextInt();
                sc.nextLine();

                p.setInventoryQuantity(newQty);
                System.out.printf("%s의 재고수량이 %d개로 수정되었습니다.\n", p.getProductName(), newQty);
            }
            default -> System.out.println("잘못된 번호입니다.");
        }
    }

    public void deleteProductProcess() {
        System.out.println("\n[ 상품 삭제 ]\n어느 카테고리의 상품을 삭제하시겠습니까?");
        for (int i = 0; i < categoryController.getCategories().size(); i++) {
            System.out.println((i + 1) + ". " + categoryController.getCategories().get(i).getCategoryName());
        }
        System.out.print("선택: ");
        int categoryIdx = sc.nextInt() - 1;
        sc.nextLine();

        if (categoryIdx >= 0 && categoryIdx < categoryController.getCategories().size()) {
            executeDeleteProduct(categoryIdx);
        } else {
            System.out.println("잘못된 카테고리 번호입니다.");
        }
    }

    private void executeDeleteProduct(int categoryIdx) {
        Category c = categoryController.getCategories().get(categoryIdx);
        System.out.print(getCategoryDetails(categoryIdx));

        System.out.print("\n삭제할 상품 번호를 선택하세요: ");
        int productIdx = sc.nextInt() - 1;
        sc.nextLine();

        if (productIdx >= 0 && productIdx < c.getProductSize()) {
            Product p = c.getProduct(productIdx);
            System.out.printf("%s이(가) 삭제되었습니다.\n", p.getProductName());
            categoryController.deleteProduct(categoryIdx, productIdx);
        } else {
            System.out.println("잘못된 상품 번호입니다.");
        }
    }
}