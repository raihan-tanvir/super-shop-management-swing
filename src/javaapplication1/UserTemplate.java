package javaapplication1;

public class UserTemplate {
    
    private String name;
    private int age;
    private String gender; 
    private String userName;
    private byte[] picture;
    private  String accessLevel;
    
    public UserTemplate( String userName,String name,int age, String gender, byte[] img, String accessLevel)
    {
        this.userName=userName;
        this.name = name;
        this.age = age;
        this.gender = gender;
        
        this.picture = img;
        this.accessLevel=accessLevel;
    }
    
   
    public String getName()
    {
        return name;
    }
    
     public int getAge()
    {
        return age;
    }
     public String getGender()
    {
        return gender;
    }
   public String getUserName()
    {
        return userName;
    }
   
    public byte[] getImage()
    {
        return picture;
    }
     public String getAccessLevel()
    {
        return accessLevel;
    }
     
}
