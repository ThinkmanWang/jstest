package com.thinkman.jstest;

/**
 * Created by wangx on 2017-02-05.
 */

import com.google.gson.JsonObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhantomJSTest {

    //Warning: phantomjs should be place to the same dictory of jar
    private static final String PHANTOMJS_PATH_WIN = "phantomjs.exe";
    private static final String PHANTOMJS_PATH_LINUX = "phantomjs";

    public PhantomJSTest() {
    }

    /**
     * Initialize PhantomJSDriver.
     */
    private PhantomJSDriver initDriver() {
        // set Capabilities
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setJavascriptEnabled(true);

        if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
            System.setProperty("phantomjs.binary.path", PHANTOMJS_PATH_LINUX);
        } else {
            System.setProperty("phantomjs.binary.path", PHANTOMJS_PATH_WIN);
        }

        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        return driver;
    }

    private String getSr(PhantomJSDriver driver) {
        if (true) {
            return "393x699";
        }
        String szScript = "" +
                "function _getViewport() {" +
                "   if (window.innerWidth !== null) {" +
                "       return {" +
                "           width: window.innerWidth," +
                "           height: window.innerHeight" +
                "       };" +
                "   if (document.compatMode === \"CSS1Compat\") {" +
                "       return {" +
                "           width: document.documentElement.clientWidth," +
                "           height: document.documentElement.clientHeight" +
                "       };" +
                "   }" +
                "   return {" +
                "       width: document.body.clientWidth," +
                "       height: document.body.clientWidth" +
                "   };" +
                "}" +
                "" +
                "let screen = window.screen, " +
                "   viewport = _getViewport();" +
                "screen = screen ? screen.width + \"x\" + screen.height : '-';" +
                "ret screen;" +
                "" ;

        return (String) driver.executeScript(szScript);
    }

    private String getVp(PhantomJSDriver driver) {
        if (true) {
            return "393x619";
        }
        String szScript = "" +
                "function _getViewport() {" +
                "   if (window.innerWidth !== null) {" +
                "       return {" +
                "           width: window.innerWidth," +
                "           height: window.innerHeight" +
                "       };" +
                "   if (document.compatMode === \"CSS1Compat\") {" +
                "       return {" +
                "           width: document.documentElement.clientWidth," +
                "           height: document.documentElement.clientHeight" +
                "       };" +
                "   }" +
                "   return {" +
                "       width: document.body.clientWidth," +
                "       height: document.body.clientWidth" +
                "   };" +
                "}" +
                "" +
                "let screen = window.screen, " +
                "   viewport = _getViewport();" +
                "viewport = viewport.width + \"x\" + viewport.height;" +
                "ret viewport;" +
                "" ;

        return (String) driver.executeScript(szScript);
    }

    /**
     * Check Performance.
     */
    public JsonObject checkSitePerformance(String url) {
        System.out.println("Start check site performance");
        PhantomJSDriver driver = null;
        JsonObject jObjRet = new JsonObject();
        JsonObject jObjApp = new JsonObject();

        try {
            driver = initDriver();
            driver.get(url);    // access to specified URL
            waitForLoad(driver);

            jObjRet.addProperty("v", "0.0.2");

            // Get values of Navigation Timing
            jObjApp.addProperty("redirect", (Long) driver.executeScript("return window.performance.timing.fetchStart - window.performance.timing.navigationStart") );
            jObjApp.addProperty("dns", (Long) driver.executeScript("return window.performance.timing.domainLookupEnd - window.performance.timing.domainLookupStart") );
            jObjApp.addProperty("connect", (Long) driver.executeScript("return window.performance.timing.connectEnd - window.performance.timing.connectStart") );
            jObjApp.addProperty("network", (Long) driver.executeScript("return window.performance.timing.connectEnd - window.performance.timing.navigationStart") );
            jObjApp.addProperty("send", (Long) driver.executeScript("return window.performance.timing.responseStart - window.performance.timing.requestStart") );

            jObjApp.addProperty("receive", (Long) driver.executeScript("return window.performance.timing.responseEnd - window.performance.timing.responseStart") );
            jObjApp.addProperty("backend", (Long) driver.executeScript("return window.performance.timing.responseEnd - window.performance.timing.responseEnd") );
            jObjApp.addProperty("render", (Long) driver.executeScript("return window.performance.timing.loadEventEnd - window.performance.timing.loadEventEnd") );
            jObjApp.addProperty("dom", (Long) driver.executeScript("return window.performance.timing.domComplete - window.performance.timing.domLoading") );
            jObjApp.addProperty("frontend", (Long) driver.executeScript("return window.performance.timing.loadEventEnd - window.performance.timing.loadEventEnd") );

            jObjApp.addProperty("load", (Long) driver.executeScript("return window.performance.timing.loadEventEnd - window.performance.timing.loadEventEnd") );
            jObjApp.addProperty("domReady", (Long) driver.executeScript("return window.performance.timing.domContentLoadedEventStart - window.performance.timing.domContentLoadedEventStart") );
            jObjApp.addProperty("interactive", (Long) driver.executeScript("return window.performance.timing.domInteractive - window.performance.timing.navigationStart") );
            jObjApp.addProperty("ttf", (Long) driver.executeScript("return window.performance.timing.fetchStart - window.performance.timing.navigationStart") );
            jObjApp.addProperty("ttr", (Long) driver.executeScript("return window.performance.timing.requestStart - window.performance.timing.navigationStart") );

            jObjApp.addProperty("ttdns", (Long) driver.executeScript("return window.performance.timing.domainLookupStart - window.performance.timing.navigationStart") );
            jObjApp.addProperty("ttconnect", (Long) driver.executeScript("return window.performance.timing.connectStart - window.performance.timing.connectStart") );
            jObjApp.addProperty("ttfb", (Long) driver.executeScript("return window.performance.timing.responseStart - window.performance.timing.navigationStart") );

            jObjRet.add("app", jObjApp);

            jObjRet.addProperty("sr", getSr(driver));
            jObjRet.addProperty("vp", getVp(driver));
            System.out.format("Response Time : %s\n", jObjRet.toString());
            //System.out.format("PageLoad Time : %d\n", loadEndTime - startTime);


        } catch (Exception ex) {

        } finally {
            if (driver != null) {
                driver.quit();
            }

            return jObjRet;
        }
    }

    /**
     * Wait for page load.
     */
    void waitForLoad(PhantomJSDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(pageLoadCondition);
    }
}
