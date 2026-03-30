package Lv2;

import java.text.DecimalFormat;
import java.util.List;

public class OrderSystem {
    private List<CartItem> cartItems;
    private List<OrderRecord> orderHistory;

    public OrderSystem(List<CartItem> cartItems, List<OrderRecord> orderHistory) {
        this.cartItems = cartItems;
        this.orderHistory = orderHistory;
    }

    public boolean isCartEmpty() { return cartItems.isEmpty(); }
    public boolean isOrderHistoryEmpty() { return orderHistory.isEmpty(); }

    public void addToCart(Product selectedProduct, int quantity) {
        CartItem item = new CartItem(selectedProduct, quantity);
        cartItems.add(item);
    }

    public String getCartDetails() {
        if (isCartEmpty()) return "장바구니가 비어있습니다.\n";

        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,###");

        for (CartItem item : cartItems) {
            Product p = item.getProduct();
            sb.append(String.format("%-13s | %s원 | %s | 수량: %d개\n",
                    p.getProductName(), df.format(p.getPrice()), p.getProductDescription(), item.getQuantity()));
        }
        return sb.toString();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            // item 스스로 계산한 금액을 받아오기만 하면 됨
            totalPrice += item.getSubTotalPrice();
        }
        return totalPrice;
    }

    public String processOrder() {
        if (isCartEmpty()) return "결제할 상품이 없습니다.";

        OrderRecord newOrder = new OrderRecord(cartItems);
        orderHistory.add(newOrder);

        StringBuilder result = new StringBuilder();
        result.append("주문이 완료되었습니다!\n");

        for (CartItem item : cartItems) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            int beforeQty = p.getInventoryQuantity();

            p.deductInventoryQuantity(qty);

            result.append(String.format("- %s 재고: %d개 -> %d개\n",
                    p.getProductName(), beforeQty, p.getInventoryQuantity()));
        }
        cartItems.clear();
        return result.toString();
    }

    public String getOrderHistoryDetails() {
        if (isOrderHistoryEmpty()) return "주문 내역이 없습니다.\n";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < orderHistory.size(); i++) {
            sb.append((i + 1)).append("번 주문 기록\n");
            List<CartItem> items = orderHistory.get(i).getOrderItems();
            for (CartItem item : items) {
                sb.append("  - ").append(item.getProduct().getProductName())
                        .append(" ").append(item.getQuantity()).append("개\n");
            }
        }
        return sb.toString();
    }

    public String cancelAllOrders() {
        if (isOrderHistoryEmpty()) return "취소할 주문이 없습니다.";

        StringBuilder result = new StringBuilder();
        result.append("🗑️ 주문이 취소되어 재고가 복구되었습니다.\n");

        for (OrderRecord order : orderHistory) {
            List<CartItem> items = order.getOrderItems();
            for (CartItem item : items) {
                Product p = item.getProduct();
                int qty = item.getQuantity();
                int beforeQty = p.getInventoryQuantity();

                p.addInventoryQuantity(qty);

                result.append(String.format("- %s 재고: %d개 -> %d개\n",
                        p.getProductName(), beforeQty, p.getInventoryQuantity()));
            }
        }
        orderHistory.clear();
        return result.toString();
    }
}