package expertchat.params;// Created by Kishor on 4/25/2017.

public class parameter {

    protected static boolean isNegative ;

    protected static boolean isExpert;

    public static void setIsNegative ( boolean isNegative ) {

        parameter.isNegative = isNegative;
    }

    public static void setExpert ( boolean isExpert ) {

        parameter.isExpert = isExpert;
    }

    public static boolean isExpert ( ) {
        return isExpert;
    }

    public static boolean isNegative ( ) {

        return isNegative;
    }
}
