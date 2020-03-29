package app.util.enums;

public enum AccessType
{
    Private(0), Public(1);

    private int id;

    private AccessType(int value){
        id = value;
    }

    public int getValue(){
        return id;
    }

    public static AccessType getEnum(int value){
        switch (value){
            case 0:
                return Private;
            case 1:
                return Public;
            default:
                return  null;
        }
    }
}
;

