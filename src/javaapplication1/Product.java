package javaapplication1;

public class Product {
    
    private int id;
    private String name;
    private float price;
    private int unit; 
    private byte[] picture;
    private String addDate;
    private float sales;
    
    public Product(int pid, String pname, float pprice, int punit, byte[] pimg,String pAddDate,float psales)
    {
        this.id = pid;
        this.name = pname;
        this.price = pprice;
        this.unit = punit;
        this.picture = pimg;
        this.addDate=pAddDate;
        this.sales=psales;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public float getPrice()
    {
        return price;
    }
    
    public int getUnit()
    {
        return  unit;
    }
    
    public byte[] getImage()
    {
        return picture;
    }
    public String getAddDate()
    {
        return addDate;
    }
     public float getSales()
    {
        return sales;
    }
}
