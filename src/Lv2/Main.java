package Lv2;

import Lv2.DataBase.CartItem;
import Lv2.DataBase.OrderItem;
import Lv2.System.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 주문 및 장바구니 리스트 생성
        List<CartItem> cartItems = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();

        // 시스템 객체 생성 및 의존성 주입 (Dependency Injection)
        DB db = new DB(cartItems, orderItems);
        CategorySystem categorySystem = new CategorySystem(sc, db);
        ProductSystem productSystem = new ProductSystem(sc);
        CartSystem cartSystem = new CartSystem(db);
        OrderSystem orderSystem = new OrderSystem(sc, db);
        AdminSystem adminSystem = new AdminSystem(sc, db);
        CommerceSystem commerceSystem = new CommerceSystem(sc, categorySystem, productSystem,cartSystem, orderSystem, adminSystem);

        // 프로그램 시작
        commerceSystem.start();
    }
}