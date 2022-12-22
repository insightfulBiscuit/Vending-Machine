/*
Agent class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

import java.util.Scanner;
import java.io.*;

class Agent extends Person {
    public Agent(String personName, double walletBalance){
        super(personName, walletBalance);
    }

    @Override
    //agent is asked to select make a choice from menu
    public String chooseOption() throws IOException{
        String choice;
        Scanner input = new Scanner(System.in);
        boolean invalid = false;

        //do until valid input
        do{
            if (invalid){
                System.out.println("\nAction Invalid. Please try again.");
            }
        
            System.out.println("\n1. List Items" + 
                            "\n2. Load Items" +
                            "\n3. Remove Item" +
                            "\n4. Replace Item" + 
                            "\n5. Go Back");
           
            System.out.print("Please choose an option: ");
            choice = input.nextLine();

            //if input is valid, exit out of loop
            if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5")){
                invalid = false;
            }
            else{
                invalid = true;
            }
        } while(invalid); //until input is valid

        return choice;
    }

    //removes item at agents discretion
    public void removeItem(String item, Inventory storage){
        int y = Integer.valueOf(item.substring(0,1));
        int x = Integer.valueOf(item.substring(1));
        BakedGood[] product = (storage.getItemArray())[y][x];

        if (product[0] instanceof Dessert){
            product[0] = new Dessert();
        }
        else if (product[0] instanceof YeastProduct){
            product[0] = new YeastProduct();
        }

        //the slot is filled by items behind it
        storage.pushForward(storage.getItemArray()[y][x]);

        System.out.println("\nProduct removed.\n");
    }

    //agent inputs details of products he/she wants to create
    public String inputDetails()throws IOException{
        Scanner input = new Scanner(System.in);
        String type;
        String line = "";
        boolean invalid = false;

        //do until valid input
        do{
            if (invalid){
                System.out.println("\nInvalid Input. Please Try Again.\n");
            }

            System.out.print("\nCreating a Dessert or Yeast Product? (Dessert/YeastProduct): ");
            type = input.nextLine();

            if (type.equals("Dessert") || type.equals("YeastProduct")){
                invalid = false;
            }
            else {
                invalid = true;
            }

        } while(invalid); //until valid input

        //agent types in item desctiption
        System.out.print("\nType: ");
        line += input.nextLine() + ",";

        System.out.print("Price (0.00): ");
        line += input.nextDouble() + ",";

        input.nextLine();

        //do until proper format is achieved
        do{
            System.out.print("Expiry Date (DD/MM/YYYY): ");
            int lineLength = line.length();
            line += input.nextLine() + ",";
            if (line.substring(2+lineLength,3+lineLength).equals("/") && line.substring(5+lineLength,6+lineLength).equals("/")){
                invalid = false;
            }
            else{
                System.out.println("\nImproper format entered. Please try again.\n");
                invalid = true;
            }
        } while (invalid); //until proper format is achieved

        System.out.print("Packaging: ");
        line += input.nextLine() + ",";

        System.out.print("Gluten Free (true/false): ");
        line += input.nextLine() + ",";

        //Different description depenting on object(item) type
        if (type.equals("Dessert")){
            System.out.print("Filling: ");
            line += input.nextLine() + ",";

            System.out.print("Topping: ");
            line += input.nextLine();
        }
        else if (type.equals("YeastProduct")){
            System.out.print("Shape: ");
            line += input.nextLine() + ",";

            System.out.print("Carbohydrates (%): ");
            line += input.nextInt() + ",";

            System.out.print("Alcohol Content (%): ");
            line += input.nextInt();
        }
        return line;
    }

    //agent loads item he/she created in the slot of choice
    public void loadProduct(VendingMachine machine, String location, BakedGood newItem) throws IOException{
        BakedGood[][][] storage;
        int y = Integer.valueOf(location.substring(0,1));
        int x = Integer.valueOf(location.substring(1));
        Scanner input = new Scanner(System.in);
        boolean invalid = false;
        String response;

        storage = machine.getInventory().getItemArray();
        storage[y][x][1] = newItem;

        do {
            if (invalid){
                System.out.println("\nInvalid Input. Please Try Again.\n");
            }

            System.out.print("Push item to front of slot? (Y/N): ");
            response = input.nextLine();
            
            if (response.equals("Y") || response.equals("N")){
                invalid = false;
                if (response.equals("Y")){
                    machine.getInventory().pushForward(storage[y][x]);
                }
            }
            else {
                invalid = false;
            }
        } while(invalid);
    }

    //agent replaces item at slot of choice
    public void replaceProduct(VendingMachine machine, String location, BakedGood newItem){
        BakedGood[][][] storage;
        int y = Integer.valueOf(location.substring(0,1));
        int x = Integer.valueOf(location.substring(1));

        storage = machine.getInventory().getItemArray();
        storage[y][x][0] = newItem;
    }
}