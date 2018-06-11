package com.company;

public class Rocket implements SpaceShip {

    int rocketCost ;
    double load;                                       // The mass of the current payload
    double maxLoad;

    @Override
    public boolean launch(double load) {
        return true;
    }

    @Override
    public boolean land(double load) {
        return true;
    }

    @Override
    public boolean canCarry(Item thisItem, int maxLoad) {

        System.out.println("Load : " + load + " kg   Item mass : " + thisItem.mass + " kg   maxLoad : " + maxLoad + " kg");
        System.out.print("Can Carry?");
        if (load + thisItem.mass <= maxLoad) {
            System.out.println("   YES");
            return true;
        } else{
            System.out.println("    NO can't carry");
            return false;
        }
    }

    @Override
    public int carry(Item thisItem) {
        load += thisItem.mass;
        return 0;
    }
}
