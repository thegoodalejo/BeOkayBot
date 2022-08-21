package com.bo.models;

import com.bo.db.FCS;

import java.util.List;

public class MessageOptions {

    public String hookId, optionId;
    public List<MsgAction> actionsList;

    public MessageOptions(String optionId, String hookId, List<MsgAction> actionsList) {
        this.optionId = optionId;
        this.hookId = hookId;
        this.actionsList = actionsList;
    }

    @Override
    public String toString() {
        return "hookId -> " + hookId + "\nfield -> " + optionId;
    }

}
