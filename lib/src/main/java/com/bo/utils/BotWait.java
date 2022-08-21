package com.bo.utils;

import com.bo.singleton.MyDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.concurrent.TimeUnit;

public class BotWait {
    public static void seconds(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void halfSecond() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void forElementLong(By element) {
        //System.out.println("Temp to wait long for " + element.toString());
        for (int i = 0; i < 100; i++) {
            try {
                MyDriver.instance().get().findElement(element).isDisplayed();
                break;
            }catch (NoSuchElementException e){
                if(i==99)System.out.println("Attempt ["+i+"] to catch element.... " + element.toString());
                halfSecond();
            }
        }
    }

    public static void forElementShort(By element) {
        //System.out.println("Temp to wait short for " + element.toString());
        for (int i = 0; i < 20; i++) {
            try {
                MyDriver.instance().get().findElement(element).isDisplayed();
                break;
            }catch (NoSuchElementException e){
                if(i==99)System.out.println("Attempt ["+i+"] to catch element.... " + element.toString());
                halfSecond();
            }
        }
    }
}
