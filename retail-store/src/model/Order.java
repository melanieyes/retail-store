package model;

public class Order {
    private int ordID;
    private int custID;
    private java.sql.Date ordDate;
    private int statusID;
    private double totAmt;

    // Getters and Setters
    public int getOrdID() { return ordID; }
    public void setOrdID(int ordID) { this.ordID = ordID; }

    public int getCustID() { return custID; }
    public void setCustID(int custID) { this.custID = custID; }

    public java.sql.Date getOrdDate() { return ordDate; }
    public void setOrdDate(java.sql.Date ordDate) { this.ordDate = ordDate; }

    public int getStatusID() { return statusID; }
    public void setStatusID(int statusID) { this.statusID = statusID; }

    public double getTotAmt() { return totAmt; }
    public void setTotAmt(double totAmt) { this.totAmt = totAmt; }
}
