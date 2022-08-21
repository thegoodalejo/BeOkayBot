package com.bo.models;

import com.bo.db.FCS;

public class ActionDB extends MsgAction{

    private String optionId,value;

    public ActionDB(String optionId, String value) {
        this.optionId = optionId;
        this.value = value;
    }

    @Override
    public void excecute(User user) {
        System.out.println("Updating for => "+user.getUserDirection());
        FCS.getInstance().userUpdate(optionId,value,user.getUserDirection());
    }
}
