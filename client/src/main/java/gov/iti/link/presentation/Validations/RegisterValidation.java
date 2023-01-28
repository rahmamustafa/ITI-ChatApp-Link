package gov.iti.link.presentation.Validations;

import java.sql.Date;
import java.util.regex.*;

import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.UserServiceImp;

public class RegisterValidation {
    private static String regex;

    private static Pattern Pattern;

    public static Matcher matcher;

    public static boolean validName(String name) {
        if (name == null || name.isBlank()){
            return false;
        }
        regex = "^[a-zA-Z]{2,30}$";
        Pattern = Pattern.compile(regex);
        matcher = Pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validPhone(String phone) {
        if (phone == null|| phone.isBlank()) {
            return false;
        }
        regex = "^\\d{6,10}";
        Pattern = Pattern.compile(regex);
        matcher = Pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean validEmail(String Email) {
        if (Email == null|| Email.isBlank()) {
            return false;
        }
        regex = "^(.+)@(.+)$";
        Pattern = Pattern.compile(regex);
        matcher = Pattern.matcher(Email);
        return matcher.matches();
    }

    public static boolean validPassword(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }
        regex = "^\\d{6,10}";
        Pattern = Pattern.compile(regex);
        matcher = Pattern.matcher(password);
        return (matcher.matches());
    }
    public static boolean validConfirmPassword(String password, String confirmationPassword) {
        if (password == null || confirmationPassword == null || password.isBlank() || confirmationPassword.isBlank()) {
            return false;
        }
        return (confirmationPassword.equals(password));
    }


    public static boolean validBio(String bio) {
        if (bio == null|| bio.isBlank()) {
            return false;
        }
       
        return true;
    }
    public static boolean validDate(Date date) {
        if (date == null) {
            return false;
        }
       
        return true;
    }
    public static boolean validGender(Object gender) {
        if (gender == null) {
            return false;
        }
       
        return true;
    }
    public static boolean uniquePhone(String phone) {
        UserService userService = new UserServiceImp();
        if(userService.findByPhone(phone)!=null){
            return false;
        }
        if (phone == null) {
            return false;
        }
        return true;
    }

}
