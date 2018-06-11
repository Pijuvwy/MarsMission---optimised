package com.company;

public interface SpaceShip {

    boolean launch(double load);
    boolean land(double load);
    boolean canCarry(Item thisItem, int maxLoad);
    int carry (Item thisItem);
}
