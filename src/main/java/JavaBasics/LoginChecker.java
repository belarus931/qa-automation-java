package JavaBasics;

public class LoginChecker {
    public void checkAccess(boolean isEmailVerified, boolean hasValidPassword){
        if ((isEmailVerified) && (hasValidPassword)){
            System.out.println("Access is allowed");
        } else System.out.println("Access denied");
    }
}
