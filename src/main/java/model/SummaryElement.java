package model;

public class SummaryElement {

    private double quantity;
    private int price;

    public SummaryElement(double quantity, int price) {
        this.quantity = quantity;
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
