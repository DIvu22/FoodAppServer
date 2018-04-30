package serverside.example.com.serverside.Common;

import serverside.example.com.serverside.Model.User;

/**
 * Created by Divya Gupta on 27-04-2018.
 */

public class Common {
    public  static final  String  UPDATE="Update";
    public  static  final  String DELETE="Delete";
    public static   final int PICK_IMAGE_REQUEST=71;
    public static User currentUser;

    public static String convertCodeToStatus(String code) {
        if (code.equals("0"))
            return "Placed";

        else if (code.equals("0"))
            return "On the way";

        else
            return "Shipped";


    }

}
