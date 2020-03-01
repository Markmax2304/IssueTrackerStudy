package app.util.enums;

public enum StatusType {
    New(0), Assigned(1), InProgress(2), Review(3),
    Fixed(4), Reopened(5), Closed(6), Canceled(7);

    private int id;

    private StatusType(int value){
        id = value;
    }

    public int getValue(){
        return id;
    }

    public static StatusType getEnum(int value){
        switch (value){
            case 0:
                return New;
            case 1:
                return Assigned;
            case 2:
                return InProgress;
            case 3:
                return Review;
            case 4:
                return Fixed;
            case 5:
                return Reopened;
            case 6:
                return Closed;
            case 7:
                return Canceled;
            default:
                return  null;
        }
    }
}
