/*
Dessert class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

class Dessert extends BakedGood{
    private String filling;
    private String topping;

    public Dessert(){
        this.filling = "N/A";
        this.topping = "N/A";
    }

    public Dessert(String type, double price, String expiryDate, String packageType, boolean glutenFree, String filling, String topping){
        super(type, price, expiryDate, packageType, glutenFree);
        this.filling = filling;
        this.topping = topping;
    }
    
    @Override
    //returns string representation of object
    public String toFile(){
        return ((super.toFile() + this.filling + "," + this.topping));
    }

    @Override
    //not used
    public String toString(){
        return ((super.toString() + "Filling: " + this.filling + "\nTopping: " + this.topping));
    }

    @Override
    public String getName(){
        return (super.getName() + " (" + this.filling + ")");
    }
}