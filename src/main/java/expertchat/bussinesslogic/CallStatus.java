package expertchat.bussinesslogic;

public interface CallStatus {

    String INITIATED = "1";
    String ACCEPTED = "2";
    String DECLINED = "3";
    String COMPLETED = "4";
    String DELAYED = "5";
    String RECONNECT = "1";

    String USER_DISCONNECT = "user_disconnect";
    String EXPERT_DISCONNECT = "expert_disconnect";
    String NETWORK_FAILURE = "network_disconnect";
    String USER_DECLINED = "user_declined";
    String EXPERT_DECLINED = "expert_declined";
    String TIMEOUT = "timeout";


}
