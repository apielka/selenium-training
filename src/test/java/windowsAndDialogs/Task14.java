package windowsAndDialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class Task14 {


    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.navigate().to("http://localhost/litecart/admin");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
    }

    private void login() {
        String username = "admin";
        String password = "admin";
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }


    @Test
    public void checkWindows() {
        login();
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("td#content div > a")).click();

        List<WebElement> elementsWithLinks = driver.findElements(By.cssSelector("#content [target='_blank']"));

        String mainWindow = driver.getWindowHandle();

        for (int i = 1; i < elementsWithLinks.size(); i++) {
            elementsWithLinks.get(i).click();
            wait.until(numberOfWindowsToBe(2));
            Set<String> windowsSet = driver.getWindowHandles();
            for (String window : windowsSet) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);
                    String actualURL = driver.getCurrentUrl();
                    driver.close();
                }
            }

            driver.switchTo().window(mainWindow);
        }
    }


    @AfterTest
    public void stop() {
        driver.close();
    }
}