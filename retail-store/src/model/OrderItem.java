package model;

public class OrderItem {
    private int ordItemID;
    private int ordID;
    private int prodsID;
    private int qty;
    private double unitPrice;
    private double totalPrice;

    // Getters and Setters
    public int getOrdItemID() { return ordItemID; }
    public void setOrdItemID(int ordItemID) { this.ordItemID = ordItemID; }

    public int getOrdID() { return ordID; }
    public void setOrdID(int ordID) { this.ordID = ordID; }

    public int getProdsID() { return prodsID; }
    public void setProdsID(int prodsID) { this.prodsID = prodsID; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
