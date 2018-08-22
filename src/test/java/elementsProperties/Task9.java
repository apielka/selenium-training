package elementsProperties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class Task9 {


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
    public void checkCountriesSort() {
        login();
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");

        int countriesSize = driver.findElements(By.cssSelector("tr.row")).size();
        System.out.println(countriesSize);
        List<WebElement> countries = driver.findElements(By.xpath("//tr[contains(@class,'row')]/td[5]/a"));


        List<String> countriesList = new ArrayList<String>();

        for (int i = 0; i < countriesSize; i++) {
            System.out.println(countries.get(i).getText());
            countriesList.add(countries.get(i).getText());
        }

        List<String> countriesListBeforeSorting = new ArrayList<String>(countriesList);
        Collections.sort(countriesList, Collections.reverseOrder());
        assertEquals(countriesList, countriesListBeforeSorting);

        System.out.print("*********************************");



    }

    @AfterTest
    public void stop() {
        driver.close();
    }
}