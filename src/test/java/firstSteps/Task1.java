package firstSteps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Task1 {

    private WebDriver driver;

    @Test
    public void runBrowser() {
        driver = new ChromeDriver();
        driver.navigate().to("http://www.google.pl");
        driver.close();
    }


}
