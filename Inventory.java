/*
Inventory class
Desc:
Daniel Kovalevskiy
Nov. 2, 2022 
*/

import java.io.*;
import java.util.Scanner;

class Inventory{
    //variable declaration
    private BakedGood[][][] storage;

    public Inventory(){
        this.storage = new BakedGood[4][5][2];
    }

    public Inventory(String stockFile) throws IOException{
        this.storage = new BakedGood[4][5][2];
        readStock(stockFile, storage);
    }

    public BakedGood[][][] getItemArray(){
        return this.storage;
    }

    //converts string representation of object to object
    public BakedGood createObject(String str){
        int index;
        int j = 0;
        String[] parameter = new String[8];
        BakedGood product;
        
        //slip up parameters by searching for "," commas.
        for (int i = 0; i < str.length(); i++){
            index = (str.substring(i)).indexOf(",");

            if (index == -1){
                parameter[j] = str.substring(i);
                break;
            }
            else{
                parameter[j] = str.substring(i, index+i);
                i += index;
                j++;
            }
        }

        //differentiate Dessert with YeastProduct by looking at number of parameters
        if (parameter[7] == null){
            product = new Dessert(parameter[0], Double.valueOf(parameter[1]), parameter[2], parameter[3], (parameter[4].equals("true")), parameter[5], parameter[6]);
        }
        else{
            product = new YeastProduct(parameter[0], Double.valueOf(parameter[1]), parameter[2], parameter[3], (parameter[4].equals("true")), parameter[5], Integer.valueOf(parameter[6]), Integer.valueOf(parameter[7]));
        }

        return product;
    }

    //prints out the items in terms on index numbers for user to see
    public void printStock(){
        for (int y = 0; y < storage.length; y++){   
            for (int x = 0; x < storage[0].length; x++){
                System.out.print("" + y + x + ". ");

                if (((storage[y][x][0]).getName()).contains("N/A")){
                    System.out.println("EMPTY");
                }
                else{
                    System.out.println(storage[y][x][0].getName());
                }
            }
        }
        System.out.println("------------------------------");
    }

    //reads item array from file
    public void readStock(String stockFile, BakedGood[][][] storage) throws IOException{
        File file = new File(stockFile);
        Scanner input = new Scanner(file);
        String line;
        int space = 0;

        for (int y = 0; y < storage.length; y++){   
            for (int x = 0; x < storage[0].length; x++){
                for (int z = 0; z < storage[0][0].length; z++){
                    line = input.nextLine();
                    
                    if (line.equals("-") || line.equals("")){
                        line = input.nextLine();
                    }

                    storage[y][x][z] = createObject(line);
    
                }
            }
        }
    }

    //writes the item array to file
    public void writeStock(String fileName, BakedGood[][][] storage) throws IOException{
        File file = new File(fileName);
        FileWriter output = new FileWriter(file);
        String line;
        for (int y = 0; y < storage.length; y++){   
            for (int x = 0; x < storage[0].length; x++){
                for (int z = 0; z < storage[0][0].length; z++){
                    line = (storage[y][x][z]).toFile();
                    output.write(line + "\n");
                }
                if (x < storage[0].length-1){
                    output.write("-" + "\n");
                }
            }
            output.write(""+ "\n");
        }
        output.close();
    }

    //pushed items forward - when items are loaded by agents, its loaded in the back and the user wont see it
    //by pushing the item forward, the user can purchase it
    public void pushForward(BakedGood[] slot){
        if (slot[0] instanceof Dessert){
            slot[0] = slot[1];
            slot[1] = new Dessert();
        }
        else if (slot[0] instanceof YeastProduct){
            slot[0] = slot[1];
            slot[1] = new YeastProduct();
        }
    }
        
    //dispense items from vending machine item array
    public void dispenseItem(String item, Inventory storage){
        int y = Integer.valueOf(item.substring(0,1));
        int x = Integer.valueOf(item.substring(1));
        BakedGood[] slot = (storage.getItemArray())[y][x];

        if (slot[0].getName().equals("N/A")){
            System.out.println("\nThe slot is EMPTY, please choose another item\n");
        }
        else {
            System.out.println(slot[0].getName() + " was dispensed successfully!");
            pushForward(slot);
        }
    }

    //returns an item as an object from where use getters to get specific values (i.e price, name)
    public BakedGood itemInfo(String item, Inventory storage){
        int y = Integer.valueOf(item.substring(0,1));
        int x = Integer.valueOf(item.substring(1));
        BakedGood product = (storage.getItemArray())[y][x][0];

        return product;
    }
}