package expertchat.bussinesslogic;

// Created by Kishor on 5/9/2017.

public interface SessionStatus {

    String SCHEDULED="scheduled";
    String IN_PROGRESS="in_progress";
    String COMPLETED="completed";
    String CANCELLED="cancelled";
    String USER_MISSED="user_missed";
    String EXPERT_MISSED="expert_missed";
    String DISCONNECTED = "disconnected";

}
