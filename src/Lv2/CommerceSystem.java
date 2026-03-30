package Lv2;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private CategoryController categoryController;

    public CommerceSystem(Scanner sc, CategoryController categoryController) {
        this.sc = sc;
        this.categoryController = categoryController;
    }

    // void 대신 사용자가 고른 CartItem(상품+수량)을 리턴
    public CartItem handleShopping(int categoryIdx) {
        Category c = categoryController.getCategories().get(categoryIdx);
        DecimalFormat df = new DecimalFormat("#,###");

        while (true) {
            System.out.println("\n[ " + c.getCategoryName() + " 카테고리]");

            for (int i = 0; i < c.getProducts().size(); i++) {
                Product p = c.getProducts().get(i);
                System.out.printf("%d. %-13s | %s원 | %s%n",
                        i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
            }
            System.out.println("0. 뒤로가기");

            try {
                int categoryOption = sc.nextInt();
                sc.nextLine();

                if (categoryOption == 0) {
                    return null; // 뒤로가기를 누르면 아무것도 사지 않았으므로 null 리턴
                }

                int size = c.getProductSize();

                if (categoryOption > 0 && categoryOption <= size) {
                    Product selectedProduct = c.getProduct(categoryOption - 1);

                    System.out.print("선택된 상품: ");
                    System.out.printf("%s | %s원 | %s\n",
                            selectedProduct.getProductName(), df.format(selectedProduct.getPrice()), selectedProduct.getProductDescription());

                    System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                    System.out.println("1. 확인      2. 취소");

                    int saveOption = sc.nextInt();
                    sc.nextLine();

                    if (saveOption == 1) {
                        // 직접 저장하지 않고, 고른 아이템을 포장해서 컨트롤러에게 던져줌 (수량은 1개로 고정)
                        return new CartItem(selectedProduct, 1);
                    }
                    return null; // 취소한 경우 null 리턴

                } else {
                    System.out.println("잘못된 번호입니다. 1번부터 " + size + "번 사이의 숫자를 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자로만 입력해주세요");
                sc.nextLine();
            }
        }
    }
}