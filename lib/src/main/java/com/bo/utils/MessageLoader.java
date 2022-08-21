package com.bo.utils;

import com.bo.models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageLoader {

    public static Map<String, Message> messageMap = new HashMap<String, Message>();
    public static String defaultMsg = ".";
    public static String endSessionMsg = ".";

    @SuppressWarnings("unchecked")
    public static void load() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader =
                     new FileReader("lib/src/test/resources/com/bo/data/message.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray messageList = new JSONArray();
            messageList.add(obj);

            //Iterate over array
            parseMsgObject( (JSONObject) messageList.get(0) );

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    private static void parseMsgObject(JSONObject message)
    {
        JSONArray messageObject = (JSONArray) message.get("messageTree");

        defaultMsg = message.get("defaultMsg").toString();
        endSessionMsg = message.get("endSessionMsg").toString();

        System.out.println("Msg tree size => " +messageObject.size());
        System.out.println("defaultMsg => " + defaultMsg);
        System.out.println("endSessionMsg => " + endSessionMsg);

        for (int i = 0; i <messageObject.size(); i++) {
            JSONObject msgItem = (JSONObject) messageObject.get(i);
            //System.out.println("Msg ["+msgItem.get("id")+"] =>" + msgItem.get("msg"));
            JSONArray optionsMsg = (JSONArray) msgItem.get("options");
            Map<String, MessageOptions> optionsListList = getListOfOptions(optionsMsg);
            Message singleMessage = new Message(msgItem.get("id").toString(),msgItem.get("msg").toString(),optionsListList);
            messageMap.put(msgItem.get("id").toString(),singleMessage);
        }
    }

    private static Map<String, MessageOptions>  getListOfOptions(JSONArray optionsMsg){
        Map<String, MessageOptions> optionsListList = new HashMap<>();
        for (int j = 0; j < optionsMsg.size(); j++) {
            JSONObject actionsBody = (JSONObject) optionsMsg.get(j);
            //System.out.println("Response option ["+actionsBody.get("id")+"]" + " hooks to next MSG => " + actionsBody.get("hook"));
            JSONArray actionsListJSON = (JSONArray) actionsBody.get("actions");
            List<MsgAction> actionList = getListOfActions(actionsListJSON);
            MessageOptions singleOption = new MessageOptions(
                    actionsBody.get("id").toString(),
                    actionsBody.get("hook").toString(),
                    actionList);
            optionsListList.put(actionsBody.get("id").toString(),singleOption);
        }
        return optionsListList;
    }

    private static List<MsgAction> getListOfActions(JSONArray actionsList){
        List<MsgAction> actionList = new ArrayList<>();
        for (int k = 0; k < actionsList.size(); k++) {
        JSONObject singleAction = (JSONObject) actionsList.get(k);
        String actionType = singleAction.get("type").toString();
        switch (actionType){
            case "DB":
                ActionDB actionDB = new ActionDB(singleAction.get("field").toString(),singleAction.get("value").toString());
                actionList.add(actionDB);
                //System.out.println("DB Type Action");
                //System.out.println("Field : "+ singleAction.get("field").toString() + " Gona update to : " + singleAction.get("value"));
                break;
            case "END":
                ActionEND actionEND = new ActionEND();
                actionList.add(actionEND);
                //System.out.println("END type action");
                //System.out.println("Finishing chat whit this guy...");
                break;
            default:
                System.out.println("anonymus msg ??¡¡??¡¡?¡?¡?¡?¡?");
                break;
            }

        }
        return actionList;
    }

    public static String getNextKey(int index) {
        return null;
    }
}
