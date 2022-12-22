/*
Customer class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

import java.util.Scanner;
import java.io.*;

class Customer extends Person {
    public Customer(String personName, double walletBalance){
        super(personName, walletBalance);
    }

    @Override
    //Customer is asked to select make a choice from menu
    public String chooseOption() throws IOException{
        String choice;
        Scanner input = new Scanner(System.in);
        boolean invalid = false;

        //repeat until valid input
        do{
            if (invalid){
                System.out.println("\nAction Invalid. Please try again.");
            }

            System.out.println("\n1. List Items" + 
                            "\n2. Buy product (qty: 1)" + 
                            "\n3. Buy products (qty: 2) (10% off)" + 
                            "\n4. Go Back");

            System.out.print("Please choose an option: ");
            choice = input.nextLine();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")){
                invalid = false;
            }
            else{
                invalid = true;
            }
        } while(invalid);

        return choice; 
    }

    //Customer is asked to confirm their order
    public boolean confirmOrder(String[] item, VendingMachine machine) throws IOException{
        Scanner input = new Scanner(System.in);
        BakedGood[][][] storage = machine.getInventory().getItemArray();
        int y = Integer.valueOf(item[0].substring(0,1));
        int x = Integer.valueOf(item[0].substring(1));
        boolean invalid = false;
        String str;

        //do until input is valid
        do{
            if (invalid){
                System.out.println("\nAction Invalid. Please try again.");
            }

            //prints out item names
            System.out.println("\n" + storage[y][x][0].getName());
            if (item[1] != null){
                int y1 = Integer.valueOf(item[1].substring(0,1));
                int x1 = Integer.valueOf(item[1].substring(1));
                System.out.println(storage[y1][x1][0].getName());
            }

            System.out.print("\nPlease confirm your order (Y/N): ");
            str = input.nextLine();

            if (str.equals("Y") || str.equals("N")){
                invalid = false;
            }
            else {
                invalid = true;
            }

        } while (invalid);

        if (str.equals("Y")){
            return false;
        }
        else{
            return true;
        }
    }

    //customer is asked to pay for items
    public void payForItem(Person user, VendingMachine machine, String[] item) throws IOException{
        boolean incompletePayment = false;
        Scanner input = new Scanner(System.in);
        double cashInput = 0;
        BakedGood itemInfo;
        int y1, y2, x1, x2;
        double amountDue = 0;

        //do until product(s) are paid in full
        do {
            if (incompletePayment){
                System.out.println("\nInsufficient payment");
                amountDue -= cashInput;
            }

            //prints out the reciept (prices, items, discounts)
            else{
                y1 = Integer.valueOf(item[0].substring(0,1));
                x1 = Integer.valueOf(item[0].substring(1));
                itemInfo = machine.getInventory().getItemArray()[y1][x1][0];

                System.out.println("\n" + itemInfo.getName() + "\t" + itemInfo.getPrice());
                amountDue += itemInfo.getPrice();

                if (item[1] != null){
                    y2 = Integer.valueOf(item[1].substring(0,1));
                    x2 = Integer.valueOf(item[1].substring(1));
                    itemInfo = machine.getInventory().getItemArray()[y2][x2][0];

                    System.out.println(itemInfo.getName() + "\t" + itemInfo.getPrice());
                    amountDue += itemInfo.getPrice();

                    double discount = (Math.round((amountDue * 0.1)*100.0))/100.0;

                    amountDue -= discount;

                    System.out.println("Disccount\t\t-" + discount + " (10% off)");
                }
            }

            amountDue = (Math.round(amountDue*100.0))/100.0;

            //user is asked to pay for item(s)
            System.out.print("Amount due: \t" + (amountDue - machine.getCashIn()));
            System.out.print("\nPlease input cash payment: ");
            cashInput = input.nextDouble();

            //payment is not complete due to no wallet balance
            if ((cashInput > user.getWalletBalance()) || cashInput < 0){
                System.out.println("\nBalance too low...");
                incompletePayment = true;
                cashInput = 0;
            }
            else{
                user.setWalletBalance(user.getWalletBalance() - cashInput);
                machine.setCashIn(machine.getCashIn() + cashInput);

                //partial payment
                if (machine.getCashIn() < amountDue){
                    machine.setCashIn(machine.getCashIn() - cashInput);
                    incompletePayment = true;
                }
                //payment exceeds required amount
                else if (machine.getCashIn() > amountDue){
                    double change;
                    change = (Math.round((machine.getCashIn() - amountDue)*100.0))/100.0;
                    
                    user.setWalletBalance(user.getWalletBalance() + change);
                    System.out.println("\n" + change + " change returned to wallet.");

                    machine.setCashReserve(machine.getCashIn() - change);
                    machine.setCashIn(0);
                    incompletePayment = false;
                }
                else {
                    incompletePayment = false;
                } 
            }
        } while(incompletePayment); //until paid in full
    }
}
