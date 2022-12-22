/*
Owner class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

import java.util.Scanner;
import java.io.*;

class Owner extends Person {
    public Owner(){
    }

    public Owner(String personName, double walletBalance){
        super(personName, walletBalance);
    }

    @Override
    //owner selects option from menu
    public String chooseOption() throws IOException{
        String choice;
        Scanner input = new Scanner(System.in);
        boolean invalid = false;

        do{
            if (invalid){
                System.out.println("\nAction Invalid. Please try again.");
            }

            System.out.println("\n1. List Items" +
                            "\n2. Withdraw Cash" +
                            "\n3. Go Back");


            System.out.print("Please choose an option: ");
            choice = input.nextLine();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3")){
                invalid = false;
            }
            else{
                invalid = true;
            }
        } while(invalid);

        return choice;
    }

    //owner withdraws cash from machine
    public void withdrawCash(VendingMachine machine) throws IOException{
        Scanner input = new Scanner(System.in);
        boolean invalid;
        double amount;

        do{
            System.out.println("\nMachine Balance: " + machine.getCashReserve());
            System.out.print("Please enter withdraw amount: ");
            amount = input.nextDouble();

            amount = (Math.round(amount*100)/100);

            if (amount > machine.getCashReserve()){
                System.out.println("\nAmount too large. Try again.");
                invalid = true;
            }
            else {
                System.out.println("balance: " + this.getWalletBalance());
                System.out.println("Amount Added: " + amount);
                System.out.println("balance: " + (this.getWalletBalance() + amount));
                this.setWalletBalance(this.getWalletBalance() + amount);
                machine.setCashReserve(machine.getCashReserve() - amount);
                
                System.out.println("\nWithdraw Successfull!");
                invalid = false;
            }
        } while (invalid);
    }
}