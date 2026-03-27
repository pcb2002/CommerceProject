package step3;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private Scanner sc;
    private List<Product> products;

    public CommerceSystem(Scanner sc, List<Product> products) {
        this.sc = sc;
        this.products = products;

    }

    public void start(){
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        // 금액 콤마 포맷팅
        DecimalFormat df = new DecimalFormat("#,###");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            // %-12s: 왼쪽 정렬 (공백 확보), %,d: 천 단위 콤마
            System.out.printf("%d. %-13s | %s원 | %s%n",
                    i + 1, p.getProductName(), df.format(p.getPrice()), p.getProductDescription());
        }

        System.out.println("0. 종료           | 프로그램 종료");

        int end = sc.nextInt();
        if (end == 0){
            System.out.println("커머스 플랫폼을 종료합니다.");
        }
    }
}