package Helper;

public class TextControl {
    private String name;
    private String user_name;
    private String password;

    public TextControl(String name, String user_name, String password) {
        this.name = name;
        this.user_name = user_name;
        this.password = password;
    }


    public  boolean checkText(){
        if(name.isEmpty() || user_name.isEmpty()|| password.isEmpty()){
            return false;
        }
        return true;
    }
}

