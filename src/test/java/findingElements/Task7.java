package findingElements;

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
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;


public class Task7 {

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
    public void checkMenuItems() {
        List<WebElement> menuItems;
        List<WebElement> submenuItems;

        login();
        menuItems = driver.findElements(By.cssSelector("li[id='app-'] > a"));
        for (int i = 0; i < menuItems.size(); i++) {
            menuItems = driver.findElements(By.cssSelector("li[id='app-'] > a"));
            menuItems.get(i).click();
            menuItems = driver.findElements(By.cssSelector("li[id='app-'] > a"));
            assertTrue(driver.findElement(By.cssSelector("h1")).isDisplayed());

            if (driver.findElements(By.cssSelector(".docs")).size() > 0) {
                submenuItems = driver.findElements(By.cssSelector("li#app-.selected ul li"));
                for (int j = 0; j < submenuItems.size(); j++) {
                    submenuItems = driver.findElements(By.cssSelector("li#app-.selected ul li"));
                    submenuItems.get(j).click();
                    assertTrue(driver.findElement(By.cssSelector("h1")).isDisplayed());

                }
            }

        }
    }

    @AfterTest
    public void stop() {
        driver.close();
    }
}
