package model;

import java.io.Serializable;

public class MenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final double price;
    private int totalQuantitySold;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.totalQuantitySold = 0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void addQuantitySold(int quantity) {
        this.totalQuantitySold += quantity;
    }
}