package expertchat.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
* Created by Nikhil Vashistha on 4/18/2017 for ec_android_user.
*/

public class Session implements Comparator<Session>{

   @SerializedName("price")
   @Expose
   private int price;

   @SerializedName("session_length")
   @Expose
   private int sessionLength;

   public int getPrice() {
       return price;
   }

   public void setPrice(int price) {
       this.price = price;
   }

   public int getSessionLength() {
       return sessionLength;
   }

   public void setSessionLength(int sessionLength) {
       this.sessionLength = sessionLength;
   }

   @Override
   public int compare(Session session1, Session session2) {
       if(session1.getSessionLength() == session2.getSessionLength()){
           return 0;
       }else if(session1.getSessionLength() > session2.getSessionLength()){
           return 1;
       }else{
           return -1;
       }
   }
}