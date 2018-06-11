package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;


public class Simulation {

    private Item oneItem;
    private Rocket thisU1, thisU2;
    ArrayList<Rocket> arrayU1s = new ArrayList<>();
    ArrayList<Rocket> arrayU2s = new ArrayList<>();

    private File phase1Resources = new File("./resources/phase-1.txt");
    private File phase2Resources = new File("./resources/phase-2.txt");

    public ArrayList loadItems(int phase) {
        if (phase == 1) {
            ArrayList<Item> P1LoadList = new ArrayList();                   // phase 1 load list
            try (Scanner scanner1 = new Scanner(phase1Resources)) {
                String name;
                int mass;
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    String[] part = line.split("\\=");
                    name = part[0].trim();
                    mass = Integer.parseInt(part[1]);
                    oneItem = new Item(name, mass);
                    P1LoadList.add(oneItem);
                }
            } catch (FileNotFoundException b) {
                System.out.println("There is no list");
            }

            return P1LoadList;
        } else {
            ArrayList<Item> P2LoadList = new ArrayList();                       // phase 2 load list
            try (Scanner scanner2 = new Scanner(phase2Resources)) {
                String name;
                int mass;
                while (scanner2.hasNextLine()) {
                    String line = scanner2.nextLine();
                    String[] part = line.split("\\=");
                    name = part[0].trim();
                    mass = Integer.parseInt(part[1]);
                    oneItem = new Item(name, mass);
                    P2LoadList.add(oneItem);
                }
            } catch (FileNotFoundException b) {
                System.out.println("There is no list");
            }
            return P2LoadList;
        }
    }

    public ArrayList loadU1(List<Item> toLoadList) {
        int U1maxLoad = 8000;                                           // makes alteration easier
        int numberOfU1s = 0;                                            // counts rockets
        int checkSum = 0;                                               // used to check if all items have been loaded
        int tooHeavyMass = U1maxLoad + 1;                               // too heavy to fit on U1 rocket
        int heaviestIndex = 0;                                          // sets to index of heaviest item found
        int lightestIndex = 99;                                         // sets to index of lightest item still to load
        int lightestMass = U1maxLoad;                                   // sets to mass of lightest item found
        Item heaviest = toLoadList.get(0);                              // initialise heaviest to  be first item
        Item lightest = toLoadList.get(0);                              // initialise lightest to be first item

        // TODO make array to match number of items in toLoadList to track which have been loaded
        int[] checkList = new int[toLoadList.size()];                   // create array matching number of items
        for (int i = 0; i < checkList.length; i++){                     // in loadList
            checkList[i] = i + 1;
        }

        for (int i = 0; i < checkList.length; i++) {                    // calculate CheckSum
            checkSum += checkList[i];                                   // if checkSum > 0 there are unloaded items
        }
        System.out.println("\n");
        System.out.println(Arrays.toString(checkList));                 // print check array
        System.out.println("checkSum = " + checkSum);                   // print checksum ****************************

        // TODO new rocket
        thisU1 = new U1();                                              // build a new rocket
        System.out.println("\nHAVE BUILT ROCKET NUMBER 1");
        numberOfU1s ++;                                                 // count rockets

        // TODO repeat until all items have been loaded
        while ((checkSum > 0)){

            // TODO repeat while rocket can fit the LIGHTEST item & checksum != 0
            while ((thisU1.load+lightestMass<=thisU1.maxLoad) && (checkSum != 0)){
                int heaviestMass = 0;                                   // sets to mass of heaviest item found

                // TODO find HEAVIEST item in original list
                for (int i = 0; i < toLoadList.size(); i++) {           // iterate through the whole list
                    if (!(checkList[i] == 0)) {                         // as long as item is not marked as loaded
                                        // each time a heavier item is found which is less than the too-heavy amount:
                        if ((toLoadList.get(i).mass > heaviestMass) && (toLoadList.get(i).mass < tooHeavyMass)) {
                            heaviest = toLoadList.get(i);                   // call it the heaviest
                            heaviestMass = heaviest.mass;                   // note the mass of the heaviest item
                            heaviestIndex = i;                              // note the index of heaviest item
                        }                               // if item is not heavier than previous heaviest, NEXT i
                    }                               // if item is marked as loaded, NEXT i
                }                               // We now have identified the heaviest unloaded item.
                System.out.println("\nheaviestIndex = " + (heaviestIndex + 1));
                System.out.println("heaviestMass = " + heaviestMass);
                System.out.println("Heaviest Item to consider = " + heaviest.name + " " + heaviest.mass + " kg");

                // TODO find LIGHTEST item in original list
                for (int i = 0; i < toLoadList.size(); i++) {               // iterate through the whole list
                    if (!(checkList[i] == 0)) {                             // as long as item is not marked as loaded
                        if (toLoadList.get(i).mass <lightestMass)  {    // each time a lighter item is found:
                            lightest = toLoadList.get(i);                   // call it the lightest
                            lightestMass = lightest.mass;                   // note the mass of the lightest item
                            lightestIndex = i;                              // note the index of lightest item
                        }                               // if item is not lighter than previous lightest, NEXT i
                    }                               // if item is marked as loaded, NEXT i
                }                               // We now have identified the lightest unloaded item.
                System.out.println("lightestIndex = " + (lightestIndex + 1));
                System.out.println("lightestMass = " + lightestMass);
                System.out.println("Lightest Item = " + lightest.name + " " + lightest.mass + " kg");

                // TODO load item from original list if it fits onto rocket
                Item thisItem = toLoadList.get(heaviestIndex);
                if (thisU1.canCarry(thisItem, U1maxLoad)) {                 // if the rocket can add the item
                    thisU1.carry(thisItem);                                 // load it
                    System.out.println("Have loaded " + thisItem.name + " onto rocket number " + numberOfU1s + ".");
                    System.out.println("This rocket's load is now " + thisU1.load + " kg.\n");

                    // TODO set item mass in checkList to 0 as indicator that this item is loaded
                    checkList[heaviestIndex] = 0;
                } else {                                                    // if the heaviest item won't fit
                    System.out.println(toLoadList.get(heaviestIndex).name + " " + toLoadList.get(heaviestIndex).mass + " won't fit.\n");
                    tooHeavyMass = heaviestMass;                            // mark that weight as too heavy and try again
                }

                System.out.println(Arrays.toString(checkList));             // print check array
                checkSum = 0;
                for (int i = 0; i < checkList.length; i++) {                    // re-calculate CheckSum
                    checkSum += checkList[i];                                   // if checkSum > 0 there are unloaded items
                }
                System.out.println("checkSum = " + checkSum);               // print checksum

            }   // WHILE exits when rocket didn't take another item or is full

            // TODO record loaded rocket to array
            arrayU1s.add(thisU1);                                       // add the rocket to the arrayList of rockets

            // TODO set checkSum to total of items in check list (WHILE loop exits when checkSum = 0)
            checkSum = 0;
            for (int i = 0; i < checkList.length; i++) {
                checkSum += checkList[i];
            }

            // TODO new rocket
            if (checkSum >0){                                       // only if there are items remaining to load
                thisU1 = new U1();                                          // build a new rocket
                numberOfU1s++;                                              // add to the count of rockets
                System.out.println("\nHAVE BUILT ROCKET NUMBER " + numberOfU1s + ".");
            }

            tooHeavyMass = U1maxLoad + 1;                               // reset tooHeavyMass to original (maxLoad + 1)
            lightestMass = U1maxLoad;                                   // reset lightestMass
        }
        return arrayU1s;
    }

    public ArrayList loadU2(List<Item> toLoadList) {

        int U2maxLoad = 11000;                                          // makes alteration easier
        int numberOfU2s = 0;                                            // counts rockets
        int checkSum = 0;                                               // used to check if all items have been loaded
        int tooHeavyMass = U2maxLoad + 1;                               // too heavy to fit on U2 rocket
        int heaviestIndex = 0;                                          // sets to index of heaviest item found
        int lightestIndex = 99;                                         // sets to index of lightest item still to load
        int lightestMass = U2maxLoad;                                   // sets to mass of lightest item found
        Item heaviest = toLoadList.get(0);                              // initialise heaviest to  be first item
        Item lightest = toLoadList.get(0);                              // initialise lightest to be first item

        // TODO make array to match number of items in toLoadList to track which have been loaded
        int[] checkList = new int[toLoadList.size()];                   // create array matching number of items
        for (int i = 0; i < checkList.length; i++){                     // in loadList
            checkList[i] = i + 1;
        }

        for (int i = 0; i < checkList.length; i++) {                    // calculate CheckSum
            checkSum += checkList[i];                                   // if checkSum > 0 there are unloaded items
        }
        System.out.println("\n");
        System.out.println(Arrays.toString(checkList));                 // print check array
        System.out.println("checkSum = " + checkSum);                   // print checksum ****************************

        // TODO new rocket
        thisU2 = new U2();                                              // build a new rocket
        System.out.println("\nHAVE BUILT ROCKET NUMBER 1");
        numberOfU2s ++;                                                 // count rockets

        // TODO repeat until all items have been loaded
        while ((checkSum > 0)){

            // TODO repeat while rocket can fit the LIGHTEST item & checksum != 0
            while ((thisU2.load+lightestMass<=thisU2.maxLoad)&& (checkSum != 0)) {
                int heaviestMass = 0;                                   // sets to mass of heaviest item found

                // TODO find HEAVIEST item in original list
                for (int i = 0; i < toLoadList.size(); i++) {           // iterate through the whole list
                    if (!(checkList[i] == 0)) {                         // as long as item is not marked as loaded
                        // each time a heavier item is found which is less than the too-heavy amount:
                        if ((toLoadList.get(i).mass > heaviestMass) && (toLoadList.get(i).mass < tooHeavyMass)) {
                            heaviest = toLoadList.get(i);                   // call it the heaviest
                            heaviestMass = heaviest.mass;                   // note the mass of the heaviest item
                            heaviestIndex = i;                              // note the index of heaviest item
                        }                               // if item is not heavier than previous heaviest, NEXT i
                    }                               // if item is marked as loaded, NEXT i
                }                               // We now have identified the heaviest unloaded item.
                System.out.println("\nheaviestIndex = " + (heaviestIndex + 1));
                System.out.println("heaviestMass = " + heaviestMass);
                System.out.println("Heaviest Item to consider = " + heaviest.name + " " + heaviest.mass + " kg");

                // TODO find LIGHTEST item in original list
                for (int i = 0; i < toLoadList.size(); i++) {               // iterate through the whole list
                    if (!(checkList[i] == 0)) {                             // as long as item is not marked as loaded
                        if (toLoadList.get(i).mass <lightestMass)  {    // each time a lighter item is found:
                            lightest = toLoadList.get(i);                   // call it the lightest
                            lightestMass = lightest.mass;                   // note the mass of the lightest item
                            lightestIndex = i;                              // note the index of lightest item
                        }                               // if item is not lighter than previous lightest, NEXT i
                    }                               // if item is marked as loaded, NEXT i
                }                               // We now have identified the lightest unloaded item.
                System.out.println("lightestIndex = " + (lightestIndex + 1));
                System.out.println("lightestMass = " + lightestMass);
                System.out.println("Lightest Item = " + lightest.name + " " + lightest.mass + " kg");

                // TODO load item from original list if it fits onto rocket
                Item thisItem = toLoadList.get(heaviestIndex);
                if (thisU2.canCarry(thisItem, U2maxLoad)) {                 // if the rocket can add the item
                    thisU2.carry(thisItem);                                 // load it
                    System.out.println("Have loaded " + thisItem.name + " onto rocket number " + numberOfU2s + ".");
                    System.out.println("This rocket's load is now " + thisU2.load + " kg.\n");

                    // TODO set item mass in checkList to 0 as indicator that this item is loaded
                    checkList[heaviestIndex] = 0;
                } else {                                                    // if the heaviest item won't fit
                    System.out.println(toLoadList.get(heaviestIndex).name + " " + toLoadList.get(heaviestIndex).mass + " won't fit.\n");
                    tooHeavyMass = heaviestMass;                            // mark that weight as too heavy and try again
                }

                System.out.println(Arrays.toString(checkList));             // print check array
                checkSum = 0;
                for (int i = 0; i < checkList.length; i++) {                    // re-calculate CheckSum
                    checkSum += checkList[i];                                   // if checkSum > 0 there are unloaded items
                }
                System.out.println("checkSum = " + checkSum);               // print checksum

            }   // WHILE exits when rocket didn't take another item or is full

            // TODO record loaded rocket to array
            arrayU2s.add(thisU2);                                       // add the rocket to the arrayList of rockets

            // TODO set checkSum to total of items in check list (WHILE loop exits when checkSum = 0)
            checkSum = 0;
            for (int i = 0; i < checkList.length; i++) {
                checkSum += checkList[i];
            }

            // TODO new rocket
            if (checkSum >0){                                       // only if there are items remaining to load
                thisU2 = new U2();                                          // build a new rocket
                numberOfU2s++;                                              // add to the count of rockets
                System.out.println("\nHAVE BUILT ROCKET NUMBER " + numberOfU2s + ".");
            }
            tooHeavyMass = U2maxLoad + 1;                               // reset tooHeavyMass to original (maxLoad + 1)
            lightestMass = U2maxLoad;                                   // reset lightestMass
        }
        return arrayU2s;
    }


    public int runSimulation(ArrayList<Rocket> rocketList) {
        int budget = 0;
        int rocketsUsed = 0;
        int missionCount = 0;
        for (Rocket thisRocket : rocketList) {               // for each rocket in the list
            boolean deliveryComplete = false;                   // initialise delivery
            missionCount++;                                     // add to mission count
            System.out.println("\nMars delivery # " + missionCount + " prepared.");
            System.out.println("This rocket-- ");
            while (!deliveryComplete) {                                 // keep looping until delivery success
                Rocket cloneRocket = thisRocket;                            // prepare a backup rocket
                if (!thisRocket.launch(thisRocket.load)) {                  // if launch fails
                    System.out.println("  Rocket destroyed on launch!");        // oh bother
                    rocketsUsed++;                                              //  add mission to count of rockets
                    budget += thisRocket.rocketCost;                            // add mission cost to budget
                    thisRocket = cloneRocket;                                   // deploy the backup rocket
                    System.out.println("Replacement rocket prepared.");
                    ArrayList<Rocket> replacements = new ArrayList<Rocket>();   // create a List of replacements
                    replacements.add(thisRocket);                               // add the rocket to the replacements list
                    runSimulation(replacements);                                // recursively launch this rocket
                    replacements.remove(0);                               // removes rocket from replacement list
                    rocketsUsed++;                                              //  add mission to count of rockets
                    budget += thisRocket.rocketCost;                            // add mission cost to budget
                    break;
                } else if (!thisRocket.land(thisRocket.load)) {              // if landing fails
                    System.out.println("  Rocket destroyed on landing!");       // oh bother
                    rocketsUsed++;                                              //  add mission to count of rockets
                    budget += thisRocket.rocketCost;                            // add mission cost to budget
                    thisRocket = cloneRocket;                                   // deploy the backup rocket
                    System.out.println("Replacement rocket prepared.");
                    ArrayList<Rocket> replacements = new ArrayList<Rocket>();   // create a List of replacements
                    replacements.add(thisRocket);                               // add the rocket to the replacements list
                    runSimulation(replacements);                                // recursively launch this rocket
                    replacements.remove(0);                               // removes rocket from replacement list
                    rocketsUsed++;                                              //  add mission to count of rockets
                    budget += thisRocket.rocketCost;                            // add mission cost to budget
                    break;
                } else {                                                // BUT if neither fail
                    System.out.println("Delivery complete.");
                    deliveryComplete = true;                                // delivery is successful (both launch & land)
                    rocketsUsed++;                                          //  add mission to count of rockets
                    budget += thisRocket.rocketCost;                        // add mission cost to budget
                }
            }
            System.out.println("That's " + rocketsUsed + " rockets  and  $ " + budget + " million\n");
        }
        return budget;
    }

    public int U1simulation() {
        int expenditure = 0;
        System.out.println("--------------------  U1 Simulation  --------------------");
        expenditure = runSimulation(arrayU1s);
        return expenditure;
    }

    public int U2simulation() {
        int expenditure = 0;
        System.out.println("--------------------  U2 Simulation  --------------------");
        expenditure = runSimulation(arrayU2s);
        return expenditure;
    }
}
