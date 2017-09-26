package expertchat.apioperation;

import static expertchat.util.ExpertChatUtility.getValue;

public interface ExpertChatEndPoints {

    String REGISTER = "expert/register/";

    String REGISTER_USER = "user/register/";

    String LOGIN = "expert/login/";

    String LOGIN_USER = "user/login/";

    String CHANGE_PASSWORD = "expert/change-password/";

    String U_CHANGE_PASSWORD = "user/change-password/";

    String RESEND_EMAIL_VERIFICATION = "expert/resend/";

    String U_RESEND_EMAIL_VERIFICATION = "user/resend/";

    String BASIC_PROFILE = "expert/me-basic-info/";

    String U_BASIC_PROFILE = "user/me-basic-info/";

    String RESET = "password/reset/";

    String ME_PHOTO = "expert/me-photo/";

    String U_ME_PHOTO = "user/me-photo/";

    String PROFILE_STATUS = "expert/profile-status/";

    /*Social Link URLS*/

    String SOCIAL_LINKS = "social-links/";

    String PROFILE = "expert/profile/";

    String GET_FEEDS = "social/get_feeds/";

    String FEED_LINK = "feed/addlink/";

    String EXPERT_PROFILE = "expert/expertprofiles/";

    String EXPERT_PROFILE_BY_USER = "user/expertprofiles/";

    String PUBLISH_CONTENT = "contents/";

    String IGNORE_CONTENT = "ignored-contents/";

    String LIKE="user/contents/";

    String DISLIKE="user/contents/";


    /*PHONE CODE API END POINTS */

    String PHONECODEVERIFY = "expert/phonecodeverify/";

    String U_PHONECODEVERIFY = "user/phonecodeverify/";

    String PHONECODERESEND = "expert/resendphonecode/";

    String U_PHONECODERESEND = "user/resendphonecode/";

    String PHONECODESEND = "expert/phonecodesend/";

    String U_PHONECODESEND = "user/phonecodesend/";

    /*SEARCH END POINTS*/

    String SEARCH_BASE = getValue ( "search_base" );

    String SEARCH_BY_TEXT = SEARCH_BASE + "experts/?search=";

    String SEARCH_BY_EXPERT_ID = SEARCH_BASE + "experts/";

    String SEARCH_BY_TAG_ID = SEARCH_BASE + "experts/?tag_ids=";

    /*SUPER ADMIN ENDPOINTS*/

    String SUPER_ADMIN_CONTENTS = "user/super-admin-contents/";
    String SUPER_ADMIN_LOGIN = "api-auth/login/";

    /*Expert Payment Account*/
    String EXPERT_ACCOUNT = "expert/expert-accounts/";

    /*GET STREAM*/
    String BY_EXPERT_PROFILE = "expertprofiles/";

    String BY_TAGS = "tags/";

    String BY_EXPERT = "experts/";

     /*User End points*/
     /*Call Endpoint*/

    String SESSION = "sessions/";

    String REGISTER_DEVICE = "devices/";

    String SLOTS="expert/slots/";

    String AVILABLE_SLOTS="expert/available-slots/";

    String USER_AVILABLE_SLOTS="user/available-slots/";

    /*Promo code End point*/

    String PROMO_CODE= "promo-codes/";

}
