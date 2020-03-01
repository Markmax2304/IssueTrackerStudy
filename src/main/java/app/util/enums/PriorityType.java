package app.util.enums;

public enum PriorityType {
    Low(0), Medium(1), High(2);

    private int id;

    private PriorityType(int value){
        id = value;
    }

    public int getValue(){
        return id;
    }

    public static PriorityType getEnum(int value){
        switch (value){
            case 0:
                return Low;
            case 1:
                return Medium;
            case 2:
                return High;
            default:
                return  null;
        }
    }
}
