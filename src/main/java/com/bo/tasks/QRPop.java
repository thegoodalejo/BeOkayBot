package com.bo.tasks;

import com.bo.singleton.MyDriver;
import com.bo.utils.BotWait;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRPop {


    public static void openFrame() {

        By qrFramexPath = By.xpath("//canvas//..");

        BotWait.forElementLong(qrFramexPath);

        WebElement qrFrame = MyDriver.instance().get().findElement(qrFramexPath);

        // Get entire page screenshot
        File screenshot = ((TakesScreenshot) MyDriver.instance().get()).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = null;
        try {
            fullImg = ImageIO.read(screenshot);
        } catch (IOException e) {
            System.out.println("Fatal: Read ScreenShot");
            throw new RuntimeException(e);
        }
        // Get the location of element on the page
        Point point = new Point(qrFrame.getLocation().x - 10, qrFrame.getLocation().y - 10);
        // Get width and height of the element
        int eleWidth = qrFrame.getSize().getWidth() + 20;
        int eleHeight = qrFrame.getSize().getHeight() + 20;

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        try {
            ImageIO.write(eleScreenshot, "png", screenshot);
        } catch (IOException e) {
            System.out.println("Fatal: Cutting ScrenShot");
            throw new RuntimeException(e);
        }

        // Copy the element screenshot to disk
        File screenshotLocation = new File("C:\\Users\\Alejo\\IdeaProjects\\BeOkayBot\\lib\\src\\test\\resources\\com\\bo\\data\\GoogleLogo_screenshot.png");
        try {
            FileUtils.copyFile(screenshot, screenshotLocation);
        } catch (IOException e) {
            System.out.println("Fatal: Store ScreenShot");
            throw new RuntimeException(e);
        }

        final JFrame parent = new JFrame();
        parent.add(new JLabel(new ImageIcon("lib\\src\\test\\resources\\com\\bo\\data\\GoogleLogo_screenshot.png")));
        parent.setVisible(true);
        parent.setExtendedState(parent.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        BotWait.seconds(3);

        parent.dispose();
    }
}
