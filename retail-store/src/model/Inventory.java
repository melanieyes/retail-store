package model;

public class Inventory {
    private int prodsID;     // Product ID
    private int qtyInStock;  // Quantity in stock

    // Getters and Setters
    public int getProdsID() {
        return prodsID;
    }

    public void setProdsID(int prodsID) {
        this.prodsID = prodsID;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }
}
