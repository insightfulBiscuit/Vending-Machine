/*
main class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class main extends VendingMachine{
    public static void main(String[] args) throws IOException{
        //variable declaration
        VendingMachine machine = new VendingMachine(1, new Inventory("Stock.txt"), 22.00);
        Person user = new Customer("Daniel", 19.99);
        String choice = "";
        String[] items = new String[2];
        boolean[] stay = {true, true};
        boolean invalid;

        //sorts items within a slot by expiry date (soonner --> front)
        for (int y = 0; y < machine.getInventory().getItemArray().length; y++){   
            for (int x = 0; x < machine.getInventory().getItemArray()[0].length; x++){
                Arrays.sort(machine.getInventory().getItemArray()[y][x]);
            }
        }

        //main do-while loop - first menu
        do{
            user = user.chooseRole();
            stay[1] = true;

            //secondary do-while loop - second menu
            do{
                choice = user.chooseOption();
                items = new String[2];

                //1. print stock
                if (choice.equals("1")){
                    System.out.println("");
                    machine.getInventory().printStock();
                }

                //Customer Exclusive Options

                //2. purchase item
                else if (choice.equals("2") && (user instanceof Customer)){

                    //makes the customer confirm their order after selection - if not will repeat the selection process
                    do{
                        items[0] = user.selectGoods(machine, user);
                    } while((((Customer)user).confirmOrder(items, machine)));

                    //gets payment from customer
                    ((Customer)user).payForItem(user, machine, items);

                    System.out.println("");
                    
                    //dispenses item from story
                    machine.getInventory().dispenseItem(items[0], machine.getInventory());
                }

                //3. purchase combo
                else if (choice.equals("3") && (user instanceof Customer)){
                    boolean invalid1 = false;

                    //customer selects the goods until they confirm their order
                    do{
                        //makes user select an item# 1
                        System.out.println("\nChoose Item #1");
                        items[0] = user.selectGoods(machine, user);
                        //makes sure the customer chooses TWO DIFFERENT items for the combo deal
                        do{
                            //if option is invalid - going to print the error statement
                            if (invalid1){
                                System.out.println("\nCant choose the same item. Please try again.");
                            }

                            //customer selects item# 2
                            System.out.println("\nChoose Item #2");
                            items[1] = user.selectGoods(machine, user);

                            //if items are the same - redo the selection process
                            if (items[0].equals(items[1])){
                                invalid1 = true;
                            }
                            else{
                                invalid1 = false;
                            }
                        } while (invalid1); //until customer selects two different items
                    
                    } while((((Customer)user).confirmOrder(items, machine))); //until the customer confirms their items - if not, redo selection process

                    //customer pays for item
                    ((Customer)user).payForItem(user, machine, items);

                    System.out.println("");

                    //items are dispensed
                    machine.getInventory().dispenseItem(items[0], machine.getInventory());
                    machine.getInventory().dispenseItem(items[1], machine.getInventory());
                }

                //--------------------------------------------------------------------------------------------------
                
                //Agent Exclusive Options
                
                //2. load item
                else if (choice.equals("2") && (user instanceof Agent)){
                    choice = "0";

                    //agent creates an item to load into machine
                    String objectDetails = ((Agent)user).inputDetails();
                    BakedGood newItem = machine.getInventory().createObject(objectDetails);
                    
                    System.out.println("\nItem created succesfully!\n");

                    //agent MUST choose a slot that is fully empty (none in front, none behind)
                    System.out.println("Slot must be FULLY EMPTY!");
                    String location = user.selectGoods(machine, user);

                    BakedGood machineSlot = (machine.getInventory().getItemArray())[Integer.valueOf(location.substring(0,1))][Integer.valueOf(location.substring(1))][1];
                    
                    //if slot is empty - load item
                    if (machineSlot.getName().contains("N/A")){
                        ((Agent)user).loadProduct(machine, location, newItem);
                        System.out.println("\nItem Loaded!\n");
                    }
                }

                //3. remove item
                else if (choice.equals("3") && (user instanceof Agent)){

                    //item selection
                    System.out.println("Selecting item...");
                    items[0] = user.selectGoods(machine, user);
                    System.out.println("Item at: " + items[0]);

                    //removes item
                    ((Agent)user).removeItem(items[0], machine.getInventory());
                }

                //4. replace item
                else if (choice.equals("4") && (user instanceof Agent)){
                    //agent creates item to replace with
                    String objectDetails = ((Agent)user).inputDetails();
                    BakedGood newItem = machine.getInventory().createObject(objectDetails);
                    
                    System.out.println("\nItem created succesfully!\n");

                    //agent selects slot that is fully empty
                    System.out.println("Slot must be FILLED!");
                    String location = user.selectGoods(machine, user);

                    BakedGood machineSlot = (machine.getInventory().getItemArray())[Integer.valueOf(location.substring(0,1))][Integer.valueOf(location.substring(1))][0];
                    
                    //if slot is empty - replace item
                    if (!(machineSlot.getName().contains("N/A"))){
                        ((Agent)user).replaceProduct(machine, location, newItem);
                        System.out.println("\nItem Replaced!\n");
                    }
                }
                //--------------------------------------------------------------------------------------------------
                
                //Owner Exclusive Options
                
                //2. withdraw cash
                else if (choice.equals("2") && (user instanceof Owner)){
                    ((Owner)user).withdrawCash(machine);
                } 

                //exit out of second menu
                else if ((choice.equals("4") && (user instanceof Customer)) || (choice.equals("5") && (user instanceof Agent)) || (choice.equals("3") && (user instanceof Owner))){
                    stay[1] = false;
                }
                else{
                    stay[1] = true;
                }
            } while(stay[1]); //exit out of second menu

            Scanner input = new Scanner(System.in);
            invalid = false;

            do{
                //if input is invalid
                if (invalid){
                    System.out.println("\nInvalid Input. Please Try Again.");
                }
                
                //user enters option
                System.out.print("\nDo you with to exit the program? (Y/N): ");
                String str = input.nextLine();

                //if user wants to finish session
                if (str.equals("Y")){
                    stay[0] = false;
                    invalid = false;
                }
                else if (str.equals("N")){
                    invalid = false;
                }
                else{
                    invalid = true;
                }
             } while(invalid); // input is allowed

            System.out.println();

        } while(stay[0]); //exit out of first menu

        //once user ends their session, the items in the vending machine are written to file
        machine.getInventory().writeStock("Stock.txt", machine.getInventory().getItemArray());
    }
}