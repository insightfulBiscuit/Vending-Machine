/*
BakedGood class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

abstract class BakedGood implements Comparable<BakedGood>{
    //variable declaration
    private String type;
    private double price;
    private String expiryDate; //DD/MM/YYYY
    private String packageType;
    private boolean glutenFree;

    BakedGood(){
        this.type = "N/A";
        this.price = 0;
        this.expiryDate = "N/A";
        this.packageType = "N/A";
        this.glutenFree = false;
    }

    BakedGood(String type, double price, String expiryDate, String packageType, boolean glutenFree){
        this.type = type;
        this.price = price;
        this.expiryDate = expiryDate;
        this.packageType = packageType;
        this.glutenFree = glutenFree;
    }

    //converts the object to string representation of object
    public String toFile(){
        return ((this.type + "," + this.price + "," + this.expiryDate + "," + this.packageType + "," + this.glutenFree + ","));
    }

    @Override
    public String toString(){
        return(String.format("Type: %s\nPrice: %s\nExpiry Date: %s\nPackage Type: %s\nGluten Free: %s\n", this.type, this.price, this.expiryDate, this.packageType, this.glutenFree));
    }

    public String getName(){
        return this.type;
    }

    public double getPrice(){
        return this.price;
    }

    @Override
    //compare objects by expiry date
    public int compareTo(BakedGood other){
        int[] date = new int[2];
        String str;

        if (this.expiryDate.contains("N/A")){
            return 1;
        }
        else if (other.expiryDate.contains("N/A")){
            return -1;
        }

        //converts dates to integer value
        str = this.expiryDate.substring(6) + this.expiryDate.substring(3,5) + this.expiryDate.substring(0,2);
        date[0] = Integer.valueOf(str);
        str = other.expiryDate.substring(6) + other.expiryDate.substring(3,5) + other.expiryDate.substring(0,2);
        date[1] = Integer.valueOf(str);

        //dates are compared
        if (date[0] > date[1]){
            return 1;
        }
        else if (date[0] < date[1]){
            return -1;
        }
        else {
            return 0;
        }
    }
}