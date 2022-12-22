/*
Person class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

import java.util.Scanner;
import java.io.*;

class Person{
    //variable declaration
    private String personName;
    private double walletBalance;

    public Person(){
        this.personName = "None";
        this.walletBalance = 0;
    }

    public Person(String personName, double walletBalance){
        this.personName = personName;
        this.walletBalance = walletBalance;
    }

    public double getWalletBalance(){
        return this.walletBalance;
    }

    public void setWalletBalance(double walletBalance){
        this.walletBalance = walletBalance;
    }

    //user chooses a role
    public Person chooseRole() throws IOException{
        Scanner input = new Scanner(System.in);
        Person user = new Person();
        String choice;
        String name = "";
        double balance = 0;
        boolean invalid = false;
        
        //do until input is valid
        do{
            if (invalid){
                System.out.println("\nAction Invalid. Please try again.\n");
            }

            System.out.print("1. Customer\n2. Agent\n3. Owner\n\nPlease choose a role: ");
            choice = input.nextLine();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3")){
                System.out.print("\nPlease enter name: ");
                name = input.nextLine();
                System.out.print("Please enter wallet balance: ");
                balance = input.nextDouble();
                invalid = false;
            }

            //gives a role depending on input
            if (choice.equals("1")){
                user = new Customer(name, balance);
            }
            else if (choice.equals("2")){
                user = new Agent(name, balance);
            }
            else if (choice.equals("3")){
                user = new Owner(name, balance);
            }
            else{
                invalid = true;
            }
        } while(invalid); //until input is valid

        return user;
    }

    @Override
    public String toString(){
        return ("Name: " + this.personName + "\nBalance: " + this.walletBalance);
    }

    //not really used as it is overriden
    public String chooseOption() throws IOException{
        String choice;
        Scanner input = new Scanner(System.in);
        boolean invalid = false;

        do{
            if (invalid){
                System.out.println("\nAction Invalid. Please try again.\n");
            }

            System.out.println("1. List Items");

            System.out.print("Please choose an option: ");
            choice = input.nextLine();

            if (choice.equals("1")){
                invalid = false;
            }
            else{
                invalid = true;
            }
        } while(invalid);

        return choice;
    }

    //user is aked to select an item from an array
    public String selectGoods(VendingMachine machine, Person user) throws IOException{
        Scanner input = new Scanner(System.in);
        int item;
        String slot;
        boolean invalid = false;

        //do until input is valid (item number exists)
        do{
            if (invalid){
                System.out.println("\nInvalid Input. Please Try Again.");
            }

            System.out.print("\nPlease enter the slot location: ");
            item = input.nextInt();

            if (item >= 0 && item < 5){
                slot = "0" + item;
            }
            else {
                slot = "" + item;
            }

            //if item number exists - exit out of method
            if ((item >= 0 && item < 5) || (item >= 10 && item < 15) || (item >= 20 && item < 25) || (item >=30 && item < 35)){
                if (!((machine.getInventory().getItemArray()[Integer.valueOf(slot.substring(0,1))][Integer.valueOf(slot.substring(1))][0].getName()).contains("N/A"))){
                    invalid = false;
                }
                else if (user instanceof Agent){
                    if ((machine.getInventory().getItemArray()[Integer.valueOf(slot.substring(0,1))][Integer.valueOf(slot.substring(1))][1].getName()).contains("N/A")){
                        invalid = false;
                    }
                    else{
                        invalid = true;
                    }
                }
                else {
                    System.out.println("\nSlot is empty...");
                    invalid = true;
                }
            }
            else {
                invalid = true;
            }

        } while(invalid); //until valid input

        return String.valueOf(slot);
    }
}