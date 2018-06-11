package com.company;

public class U1 extends Rocket {

    public U1() {
        rocketCost = 100;   // million $
        maxLoad = 8000;
    }
    @Override
    public boolean launch(double load) {
        double launchRisk = 0.05 * (load / maxLoad);
        System.out.println("load = " + load + "  maxLoad = " + maxLoad );
      double eventScore1 = Math.random();
        // System.out.println("eventScore = " + eventScore1);
        if (eventScore1<= launchRisk) {
            System.out.print("WE HAVE A PROBLEM!");
            return false;
        } else
            System.out.println("U1 launched successfully.");
        return true;
    }

    @Override
    public boolean land(double load) {
        double crashRisk = 0.01 * (load / maxLoad);
        // System.out.println("load = " + load + "  maxLoad = " + maxLoad);
        // System.out.println("Risk = " + crashRisk);
        double eventScore2 = Math.random();
        // System.out.println("eventScore = " + eventScore2);
        if (eventScore2 <= crashRisk) {
            System.out.print("U1 CRASHED!");
            return false;
        } else
            System.out.println("U1 landed successfully.");
        return true;
    }
}
