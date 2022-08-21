package com.bo.models;

import java.util.Map;

public class Message {

    String msgId;
    String msgBody;
    Map<String, MessageOptions> optionsMap;
    public Message(String parentId, String message, Map<String, MessageOptions> optionsMap) {
        this.msgId = parentId;
        this.msgBody = message;
        this.optionsMap = optionsMap;
    }

    @Override
    public String toString() {
        return "parentId -> [" + msgId + "]" +
                "\nmessage -> " + msgBody +
                "\nOptions -> [" + optionsMap.size()+"]";
    }

    public String getMsgBody(){ return msgBody; }
    public Map<String, MessageOptions> getOptions(){ return optionsMap; }

    public String nextQuestionIndex(String response){
        MessageOptions option = optionsMap.getOrDefault(response,null);
        String _response =  option != null ? "aaaa" : "bbbb";
        return response;
    }
}
