package step3;

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

        CommerceSystem commerceSystem = new CommerceSystem(sc, new ArrayList<>(List.of(Galaxy, iPhone, MacBook, AirPods)));
        commerceSystem.start();
    }
}