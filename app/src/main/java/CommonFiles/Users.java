package CommonFiles;

import java.io.Serializable;

public class Users implements Serializable {
    public String name;
    public String email;
    public String phone;
    private String UID;

    public Users() {

    }
     public Users(String name,String email,String phone)
     {
         this.name = name;
         this.email = email;
         this.phone = phone;

     }


}
