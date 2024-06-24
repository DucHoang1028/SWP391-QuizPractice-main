package Models;

public class Role {

    public static final int ROLE_GUEST = 0;
    public static final int ROLE_CUSTOMER = 1;
    public static final int ROLE_MARKETING = 2;
    public static final int ROLE_SALE = 3;
    public static final int ROLE_EXPERT = 4;
    public static final int ROLE_ADMIN = 5;

    public static String getRoleName(int role) {
        return switch (role) {
            case ROLE_GUEST ->
                "Guest";
            case ROLE_CUSTOMER ->
                "Customer";
            case ROLE_MARKETING ->
                "Marketing";
            case ROLE_SALE ->
                "Sale";
            case ROLE_EXPERT ->
                "Expert";
            case ROLE_ADMIN ->
                "Admin";
            default ->
                "Unknown";
        };
    }
    
}
