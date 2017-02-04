package com.thinkman.jstest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * Created by wangx on 2017-02-04.
 */
public class Main {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "D:\\Github-Thinkman\\JSTest\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://h5.ffan.com/newactivity/161225_promotion_H4.html?promotion_from=70-7-1-1");
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        // time of the process of navigation and page load
        double loadTime = (Double) js.executeScript(
                "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
        System.out.print(loadTime + " seconds"); // 5.15 seconds

    }
}
