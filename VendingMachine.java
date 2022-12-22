/*
VendingMachine class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

class VendingMachine{
    //variable declaration
    private int machineID;
    private Inventory storage;
    private double cashIn;
    private double cashReserve;

    public VendingMachine(){
        this.machineID = 0;
        this.storage = new Inventory();
        this.cashIn = 0;
        this.cashReserve = 0;
    }

    public VendingMachine(int machineID, Inventory storage, double cashReserve){
        this.machineID = machineID;
        this.storage = storage;
        this.cashReserve = cashReserve;
    }
    
    public double getCashReserve(){
        return this.cashReserve;
    }

    public void setCashReserve(double amount){
        this.cashReserve += amount;
    }

    public Inventory getInventory(){
        return this.storage;
    }
    
    public double getCashIn(){
        return this.cashIn;
    }

    public void setCashIn(double amount){
        this.cashIn = amount;
    }
}