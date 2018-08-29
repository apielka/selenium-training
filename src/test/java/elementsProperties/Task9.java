package elementsProperties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLOutput;
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
    public void checkCountriesSorting() {
        login();
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> countries = driver.findElements(By.xpath("//tr[contains(@class,'row')]/td[5]/a"));
        List<String> countriesList = new ArrayList<String>();

        for (int i = 0; i < countries.size(); i++) {
            countriesList.add(countries.get(i).getText());
        }

        List<String> countriesListBeforeSorting = new ArrayList<String>(countriesList);
        Collections.sort(countriesList);
        assertEquals(countriesList, countriesListBeforeSorting);
    }

    @Test
    public void checkZonesInCountriesSorting() {
        login();
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");

        int countriesSize = driver.findElements(By.cssSelector("tr.row")).size();
        System.out.println(countriesSize);
        List<WebElement> countries = driver.findElements(By.xpath("//tr[contains(@class,'row')]/td[5]/a"));


        for (int i = 0; i < countriesSize; i++) {
            int zonesCount = Integer.parseInt(driver.findElement(By.cssSelector("tbody > tr > td:nth-child(6)")).getText());
            System.out.println(countries.get(i).getText());
            if (zonesCount > 0) {
                countries.get(i).click();
                System.out.println(countries.get(i).getText());
                driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
            }

        }
    }


    @Test
    public void checkZonesSorting() {

        login();
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");


        int countriesSize = driver.findElements(By.cssSelector(".dataTable .row td:nth-child(3) > a")).size();
        for (int i = 0; i < countriesSize; i++) {

            List<WebElement> countries = driver.findElements(By.cssSelector(".dataTable .row td:nth-child(3) > a"));
            countries.get(i).getText();
            countries.get(i).click();
            List<WebElement> geoZones = driver.findElements(By.cssSelector("#table-zones [name*=zone_code]"));
            List<String> geoZonesList = new ArrayList<String>();

            for (int j = 0; j < geoZones.size(); j++) {

                String geoZoneName = geoZones.get(j).findElement(By.cssSelector("[selected]")).getAttribute("textContent");
                geoZonesList.add(geoZoneName);
            }
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            List<String> geoZonesListBeforeSorting = new ArrayList<String>(geoZonesList);
            Collections.sort(geoZonesList);
            assertEquals(geoZonesList, geoZonesListBeforeSorting);
        }
    }


    @AfterTest
    public void stop() {
        driver.close();
    }
}