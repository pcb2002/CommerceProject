package Lv3.DataBase;

import java.util.List;

public enum CustomerGrade {
    BRONZE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15);

    private int discountPercent; // 할인율 (%)

    // 생성자
    CustomerGrade(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    // 할인율 Getter
    public int getDiscountPercent() {
        return discountPercent;
    }

    // 할인할 금액 계산: 원가 * (할인율 / 100)
    public int discountAmount(int originalPrice) {
        return originalPrice * discountPercent / 100;
    }

    // 할인율이 적용된 최종 금액 계산 메서드
    public int calculateDiscountedPrice(int originalPrice) {
        return originalPrice - discountAmount(originalPrice);
    }
}