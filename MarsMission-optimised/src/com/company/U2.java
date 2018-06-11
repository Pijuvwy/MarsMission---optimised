package com.company;

public class U2 extends Rocket {

    public U2() {
        rocketCost = 120;       // million $
        maxLoad = 11000;
    }
    @Override
    public boolean launch(double load) {
        double launchRisk = 0.04 * (load / maxLoad);
        System.out.println("load = " + load + "  maxLoad = " + maxLoad );
        double eventScore1 = Math.random();
        //System.out.println("eventScore = " + eventScore1);
        if (eventScore1<= launchRisk) {
            System.out.print("WE HAVE A PROBLEM!");
            return false;
        } else
            System.out.println("U2 launched successfully.");
        return true;
    }

    @Override
    public boolean land(double load) {
        double crashRisk = 0.08 * (load / maxLoad);
        // System.out.println("load = " + load + "  maxLoad = " + maxLoad);
        double eventScore2 = Math.random();
        //System.out.println("eventScore = " + eventScore2);
        if (eventScore2 <= crashRisk) {
            System.out.print("U2 CRASHED!");
            return false;
        } else
            System.out.println("U2 landed successfully.");
        return true;
    }
}
