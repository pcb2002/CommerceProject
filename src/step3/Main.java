package step3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommerceSystem commerceSystem = new CommerceSystem(sc);
        commerceSystem.start();

    }
}