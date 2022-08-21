package com.bo.actions;

import com.bo.db.FCS;
import com.bo.models.Message;
import com.bo.models.User;
import com.bo.queue.ChatQueue;
import com.bo.singleton.MyDriver;
import com.bo.ui.Ui;
import com.bo.utils.BotWait;
import com.bo.utils.MessageLoader;
import org.openqa.selenium.*;

import java.util.List;

public class BotActions {
    public static final boolean DEBUG = true;
    public static void queuePendingMessagesToRead() {
        final boolean DEBUG = true;
        try {
            BotWait.forElementLong(Ui.sidePanel);
            System.out.println("Looking unread msg's");
            if (!MyDriver.instance().get().findElements(Ui.unreadMessageContainer).isEmpty()) {
                System.out.println(
                        "find [" + MyDriver.instance().get().findElements(Ui.unreadMessageContainer).size() + "] unread MSG´s");
                List<WebElement> unreadMsg = MyDriver.instance().get().findElements(Ui.unreadMessageContainer);
                for (WebElement element : unreadMsg) {
                    String lines[] = element.getText().split("\\r?\\n");
                    String phoneNumber = lines[0];
                    System.out.println("Unread Msg came from -> " + phoneNumber);
                    User user = FCS.getInstance().userInfo(phoneNumber);
                    if(user != null){
                        if(user.isNewUser()){
                            ChatQueue.addToNewUserQueue(user);
                            FCS.getInstance().addNewUser(user.getUserDirection());
                        }else {
                            ChatQueue.addToSend(user);
                        }
                    }
                }
            }else{
                System.out.println("No new MSG's");
            }
        } catch (ElementClickInterceptedException e) {
            System.out.println("Looks no MSG´s yet, lets wait 1 sec");
            BotWait.seconds(1);
        }
    }

    public static void goToUserDirection(String userDirection) {
        BotWait.forElementShort(Ui.searchNumber);
        MyDriver.instance().get().findElement(Ui.searchNumber).click();
        MyDriver.instance().get().findElement(Ui.searchNumber).clear();
        MyDriver.instance().get().findElement(Ui.searchNumber).sendKeys(userDirection);
        String xpath = "//div[@data-testid='cell-frame-container']//span[contains(text(),'{0}')]";
        By pickNumberResult = By.xpath(xpath.replace("{0}",userDirection));
        BotWait.forElementLong(pickNumberResult);
        BotWait.seconds(1);
        try {
            MyDriver.instance().get().findElement(pickNumberResult).click();
        }catch (StaleElementReferenceException e){
            System.out.println("Arleady in last msg element is not attached to the page document");
        }catch (ElementClickInterceptedException e){
            System.out.println("ElementClickInterceptedException err ?");
        }

        //BotWait.forElementShort(Ui.backSearchNumber);
        BotWait.halfSecond();
        //MyDriver.instance().get().findElement(Ui.backSearchNumber).click();
        try {
            MyDriver.instance().get().findElement(Ui.downMsg).click();
        } catch (NoSuchElementException e) {
        } catch (StaleElementReferenceException e){
            System.out.println("Arleady in last msg element is not attached to the page document");
        }
        BotWait.forElementShort(Ui.txtChatContainer);
        MyDriver.instance().get().findElement(Ui.txtChatContainer).click();
    }

    public static String getLastResponse(String userDir){
        goToUserDirection(userDir);
        String msgType = MyDriver.instance().get().findElement(Ui.ctrLastMsg).getAttribute("data-id");
        if (msgType.contains("false")) {
            try {
                return MyDriver.instance().get().findElement(Ui.txtLastMsg).getText();
            } catch (NoSuchElementException e) {
                System.out.println("Si hay una respuesta pero no contiene text..");
                return "";
            }
        } else {
            System.out.println("Aun no responde");
            return "null";
        }
    }
    public static void sendMsgToUser(String userDir){
        goToUserDirection(userDir);
        Message msg = MessageLoader.messageMap.get("1");
        System.out.println("Sending FIRST msg -> " + msg.getMsgBody());
        BotWait.forElementShort(Ui.txtChat);
        BotWait.seconds(1);
        MyDriver.instance().get().findElement(Ui.txtChat).sendKeys(msg.getMsgBody());
        MyDriver.instance().get().findElement(Ui.btnSendMsg).click();
        BotWait.halfSecond();
    }

    public static void sendMsgToUser(String userDir, String index){
        goToUserDirection(userDir);
        Message msg = MessageLoader.messageMap.get(index);
        System.out.println("Sending regular msg -> " + msg.getMsgBody());
        BotWait.forElementShort(Ui.txtChat);
        BotWait.seconds(1);
        if(!index.equals("0")){
            MyDriver.instance().get().findElement(Ui.txtChat).sendKeys(msg.getMsgBody());
        }else{
            MyDriver.instance().get().findElement(Ui.txtChat).sendKeys(MessageLoader.endSessionMsg);
        }
        MyDriver.instance().get().findElement(Ui.btnSendMsg).click();
        BotWait.halfSecond();
    }
    public static void sendCommonMsgToUser(String userDir, String msg){
        goToUserDirection(userDir);
        System.out.println("Sending common msg -> " + msg);
        BotWait.forElementShort(Ui.txtChat);
        BotWait.seconds(1);
        MyDriver.instance().get().findElement(Ui.txtChat).sendKeys(msg);
        MyDriver.instance().get().findElement(Ui.btnSendMsg).click();
        BotWait.halfSecond();
    }
}
