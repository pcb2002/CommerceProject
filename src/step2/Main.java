package step2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Product Galaxy = new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 30);
        Product iPhone = new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30);
        Product MacBook = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30);
        Product AirPods = new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30);

        List<Product> products = new ArrayList<>(List.of(Galaxy, iPhone, MacBook, AirPods));

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