package de.codecentric.rgr.haexample;


import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class HAExampleServletAT {

    @Test
    public void shortURLTest() {

        String test_url = System.getProperty("acceptanceTest.baseUrl","http://www.google.de");
        String hub_url = System.getProperty("acceptanceTest.hubUrl","http://localhost:4444/wd/hub");


        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver( new URL(hub_url), capabilities);
        } catch (MalformedURLException e) {
        }

        // And now use this to visit Google
        driver.get(test_url);
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        //WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        //element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        //element.submit();

        // Check the title of the page
        System.out.println("Page Source is: " + driver.getPageSource());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().toLowerCase().contains("from session");
            }
        });

        // Should see: "cheese! - Google Search"
        //System.out.println("Page title is: " + driver.getPageSource());

        //Close the browser
        driver.quit();
    }
}
