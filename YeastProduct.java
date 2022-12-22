/*
YeastProduct class
Desc: 
Daniel Kovalevskiy
Nov. 2, 2022 
*/

class YeastProduct extends BakedGood{
    private String shape;
    private int carbContent;
    private int alcoholContent;

    public YeastProduct(){
        this.shape = "N/A";
        this.carbContent = 0;
        this.alcoholContent = 0;
    }

    public YeastProduct(String type, double price, String expiryDate, String packageType, boolean glutenFree, String Shape, int carbContent, int alcoholContent){
        super(type, price, expiryDate, packageType, glutenFree);
        this.shape = shape;
        this.carbContent = carbContent;
        this.alcoholContent = alcoholContent;
    }

    @Override
    //returns string representation of item (object)
    public String toFile(){
        return (super.toFile() + this.shape + "," + this.carbContent + "," + this.alcoholContent);
    }

    @Override
    //not used
    public String toString(){
        return ((super.toString() + "Shape: " + this.shape + "\nCarbohydrate Content: " + this.carbContent + "\nAlcohol Content: " + this.alcoholContent));
    }
}