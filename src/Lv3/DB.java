package Lv3;

import Lv3.DataBase.*;

import java.util.ArrayList;
import java.util.List;

public class DB {
    // 1. 순수 데이터(저장소) 역할만 수행하는 리스트들 (상태 변수 완전 제거)
    private List<Category> categories;
    private List<CartItem> cartItems;
    private List<OrderItem> orderItems;

    // 1. 상품 리스트 초기화 (Category 내부에서 ArrayList로 변환하므로 여기선 List.of만 써도 안전합니다)
    List<Product> electronicList = List.of(
            new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 30),
            new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30),
            new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30),
            new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 30)
    );

    List<Product> clothingList = List.of(
            new Product("겨울 패딩", 120000, "따뜻한 오리털 패딩", 30),
            new Product("반팔 티셔츠", 15000, "편안한 면 100% 티셔츠", 30),
            new Product("청바지", 45000, "슬림핏 데님 팬츠", 30)
    );

    List<Product> foodList = List.of(
            new Product("닭가슴살", 3000, "단백질 보충용", 30),
            new Product("사과", 2500, "당도 높은 꿀사과", 30),
            new Product("바나나", 5000, "신선한 바나나 한 송이", 30)
    );

    // 2. 카테고리 생성
    Category electronicsCategory = new Category("전자제품", electronicList);
    Category clothingCategory = new Category("의류", clothingList);
    Category foodCategory = new Category("식품", foodList);

    public DB(List<CartItem> cartItems, List<OrderItem> orderItems) {
        this.categories = new ArrayList<>(List.of(electronicsCategory, clothingCategory, foodCategory));
        this.cartItems = cartItems;
        this.orderItems = orderItems;
    }

    // ==========================================
    // [ 카테고리 및 상품 데이터 관리 ]
    // ==========================================
    public List<Category> getCategories() {
        return categories;
    }

    // 변경점: DB가 선택 상태를 모르기 때문에, "어느 카테고리"에 넣을지 명확하게 인자로 받습니다.
    public void addProductToCategory(int categoryIdx, Product newProduct) {
        categories.get(categoryIdx).getProducts().add(newProduct);
    }

    // 변경점: "어느 카테고리"의 "어느 상품"을 지울지 명확하게 인자로 받습니다.
    public void deleteProductFromCategory(int categoryIdx, int productIdx) {
        categories.get(categoryIdx).getProducts().remove(productIdx);
    }

    // ==========================================
    // [ 장바구니 데이터 관리 ]
    // ==========================================
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addCartItem(Product product, int quantity) {
        CartItem newItem = new CartItem(product, quantity);
        cartItems.add(newItem);
    }

    public void clearCart() {
        cartItems.clear();
    }

    // ==========================================
    // [ 주문 데이터 관리 ]
    // ==========================================
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void clearOrder() {
        orderItems.clear();
    }
}