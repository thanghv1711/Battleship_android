package fu.prm391.example.configure;

public class EventSocket {
    public static final String EVENT_CLIENT_LOGIN = "1";
    public static final String EVENT_CLIENT_GET_PLAYER_IN_ROOM = "4";
    public static final String EVENT_CLIENT_INVITE_PLAYER = "6";
    public static final String EVENT_CLIENT_CANCEL_INVITE = "12";
    public static final String EVENT_CLIENT_REFUSE_INVITED = "13";
    public static final String EVENT_CLIENT_ACCEPT_INVITED = "7";
    public static final String EVENT_CLIENT_SEND_SHIP_COORS = "8";
    public static final String EVENT_CLIENT_FIGHT = "9";
    public static final String EVENT_CLIENT_END_GAME = "11";

    public static final String EVENT_SERVER_CHECK_LOGIN = "3";
    public static final String EVENT_SERVER_GET_PLAYER_IN_ROOM = "4";
    public static final String EVENT_SERVER_NEW_PLAYER_ONLINE = "5";
    public static final String EVENT_SERVER_INVITED_PLAYER = "6";
    public static final String EVENT_SERVER_CANCEL_INVITED = "12";
    public static final String EVENT_SERVER_PLAYER_LEAVE = "10";
    public static final String EVENT_SERVER_REFUSE_INVITE = "13";
    public static final String EVENT_SERVER_ACCEPT_INVITE = "7";
    public static final String EVENT_SERVER_SEND_SHIP_COORS = "8";
    public static final String EVENT_SERVER_FIGHT = "9";
    public static final String EVENT_SERVER_CHANGE_STATUS_PLAYER = "14";
}
