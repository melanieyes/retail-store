package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        for (OrderItem existingItem : items) {
            if (existingItem.getProdsID() == item.getProdsID()) {
                // Update quantity and total price if item already exists
                existingItem.setQty(existingItem.getQty() + item.getQty());
                existingItem.setTotalPrice(existingItem.getQty() * existingItem.getUnitPrice());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProdsID() == productId);
    }

    public void updateItemQuantity(int productId, int quantity) {
        for (OrderItem item : items) {
            if (item.getProdsID() == productId) {
                item.setQty(quantity);
                item.setTotalPrice(item.getQty() * item.getUnitPrice());
                return;
            }
        }
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
