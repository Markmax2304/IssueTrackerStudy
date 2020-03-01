package app.util.enums;

public enum CriticalType {
    Minor(0), Major(1), Critical(2), Blocker(3);

    private int id;

    private CriticalType(int value){
        id = value;
    }

    public int getValue(){
        return id;
    }

    public static CriticalType getEnum(int value){
        switch (value){
            case 0:
                return Minor;
            case 1:
                return Major;
            case 2:
                return Critical;
            case 3:
                return Blocker;
            default:
                return  null;
        }
    }
}
;
