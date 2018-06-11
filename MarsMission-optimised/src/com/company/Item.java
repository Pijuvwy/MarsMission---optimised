package com.company;

public class  Item {

    public String name;
    public int mass;
    public double dMass;

    public Item(String name, int mass) {
        this.name = name;
        this.mass = mass;
    }

    public Item(String name, double dMass) {
        this.name = name;
        this.dMass = dMass;
    }

    @Override
    public String toString() {
        return name + " " + mass;
    }
}
