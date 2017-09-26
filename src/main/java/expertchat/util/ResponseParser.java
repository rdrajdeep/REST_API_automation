package expertchat.util;

import expertchat.Session;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class ResponseParser {

    private ArrayList<Session> sessionsPricingList;
    private ArrayList<String> mSlotsLengthList;
    private ArrayList<String> slotsTime;
    private String date;

    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> slotLengthLinkedHashMap;

    public ArrayList<String> onSuccess(String response){

        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            slotLengthLinkedHashMap = new LinkedHashMap<>();
            LinkedHashMap<String, ArrayList<String>> slotDatesLinkedHashMap;

            ArrayList<String> availableSlotsList;

            for (int i = 0; i <= jsonArray.length() - 1; i++) {
                Iterator<String> jsonSlotLengthKeys = jsonArray.getJSONObject(i).keys();

                if (jsonSlotLengthKeys.hasNext()) {
                    slotDatesLinkedHashMap = new LinkedHashMap<>();
                    String key = jsonSlotLengthKeys.next();

                    JSONObject objectDate = (JSONObject)jsonArray.getJSONObject(i).get(key);
//                    System.out.println(objectDate);
                    Iterator<String> dateKey = objectDate.keys();

                    while (dateKey.hasNext()) {
                        String datekeyString = dateKey.next();
                        availableSlotsList = new ArrayList<>();
                        JSONArray availableSlotsArray = objectDate.getJSONArray(datekeyString);
                        for (int j = 0; j <= availableSlotsArray.length() - 1; j++) {
                            String availableSlots = availableSlotsArray.getJSONObject(j).getString("start_time");
//                            availableSlots = getTimeInTwelveHrFormt(availableSlots);
                            availableSlotsList.add(availableSlots);
                        }
                        slotDatesLinkedHashMap.put(datekeyString, availableSlotsList);
                    }
                    if (objectDate != null && objectDate.length() > 0) {
                        slotLengthLinkedHashMap.put(key, slotDatesLinkedHashMap);
                    }
                }
            }

            TreeMap<Integer, LinkedHashMap<String, ArrayList<String>>> slotLengthLinkedHashMapSorted = new TreeMap<>();

            for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> entry : slotLengthLinkedHashMap.entrySet()) {
                int key = Integer.parseInt(entry.getKey());
                slotLengthLinkedHashMapSorted.put(key, entry.getValue());
            }

            slotLengthLinkedHashMap.clear();
            for (Map.Entry<Integer, LinkedHashMap<String, ArrayList<String>>> entry : slotLengthLinkedHashMapSorted.entrySet()) {
                String key = String.valueOf(entry.getKey());
                slotLengthLinkedHashMap.put(key, entry.getValue());
            }
            DatetimeUtility util=new DatetimeUtility();
            String today=util.currentDateOnly();

            ArrayList<String> dataFromHashMap = getValueListFromHashMap("10", "", false);
            if (dataFromHashMap != null && dataFromHashMap.size() > 0) {

//                System.out.println(getValueListFromHashMap("", "", false));
                System.out.println(dataFromHashMap);
                //date=dataFromHashMap;
                DateTime dateTime;
                DateTime datetime1;
                for (String date : sortedList) {

                    dateTime = new DateTime(date);
                    datetime1=new DateTime(today);
                  //  System.out.println("Today "+dateTime);
                    if(dateTime.isEqual(datetime1)){

                        System.out.println((slotLengthLinkedHashMap.get("10")).get(date));
                        slotsTime=(slotLengthLinkedHashMap.get("10")).get(date);
                        return slotsTime;

                    }else{
                        System.out.println("No slots available today");
                    }
                   // System.out.println(dateTime.isEqualNow());
                }
               // System.out.println((slotLengthLinkedHashMap.get("10")).get(sortedList.get(0)));

               // slotsTime=(slotLengthLinkedHashMap.get("10")).get(sortedList.get(1));
                /*wheelLeft.setData(getValueListFromHashMap("", "", false));

                wheelCenter.setData(dataFromHashMap);

                wheelRight.setData((slotLengthLinkedHashMap.get(30)).get(sortedList.get(0)));*/


            } else {

                System.out.println("empty data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return slotsTime;
    }

    private ArrayList<String> sortedList;

    private ArrayList<String> getValueListFromHashMap(String mapKeyLeft, String mapKeyCenter, boolean hasList) {

        ArrayList<String> slotsLengthList = new ArrayList<>();
        if (slotsLengthList != null) {
            slotsLengthList.clear();
        }
        if (isEmpty(mapKeyLeft) && !hasList) {
            mSlotsLengthList = new ArrayList<>();
            for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> entry : slotLengthLinkedHashMap.entrySet()) {
                String key = entry.getKey();
                mSlotsLengthList.add(key);
            }

            for (String slotLength : mSlotsLengthList) {
                if (sessionsPricingList != null) {
                    for (Session session : sessionsPricingList) {
                        if (session.getSessionLength() == Integer.parseInt(slotLength)) {
                            slotLength = slotLength + " min ($" + session.getPrice() + ")";
                            break;
                        }
                    }
                }
                slotsLengthList.add(slotLength);
            }

        } else if (!isEmpty(mapKeyLeft) && !hasList) {
            sortedList = new ArrayList<>();
            for (Map.Entry<String, ArrayList<String>> entry : slotLengthLinkedHashMap.get(mapKeyLeft).entrySet()) {
                String key = entry.getKey();
                sortedList.add(key);
                Collections.sort(sortedList);
            }

            for (String sortDates : sortedList) {
//                sortDates = getDayFromStringDate(sortDates);
                slotsLengthList.add(sortDates);
            }
        } else if (!isEmpty(mapKeyLeft) && !isEmpty(mapKeyCenter) && hasList) {
            slotsLengthList.addAll(slotLengthLinkedHashMap.get(mapKeyLeft).get(mapKeyCenter));
        }
        return slotsLengthList;
    }


    private static boolean isEmpty(String string){
        if(string != null && string.length() > 0) {
            return false;
        }
        return true;
    }
}
