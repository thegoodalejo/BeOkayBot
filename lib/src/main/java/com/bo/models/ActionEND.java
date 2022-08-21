package com.bo.models;

import com.bo.db.FCS;
import com.bo.queue.ChatQueue;

public class ActionEND extends MsgAction{
    @Override
    public void excecute(User user) {
        System.out.println("Ending for => "+user.getUserDirection());
        FCS.getInstance().userUpdate("BOT_LISTENING","false",user.getUserDirection());
        ChatQueue.removeList.add(user);
    }
}
