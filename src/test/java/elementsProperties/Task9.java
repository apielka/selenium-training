package elementsProperties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class Task9 {


    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
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

        for (int i = 0; i < countriesSize; i++) {
            List<WebElement> countries = driver.findElements(By.cssSelector("tbody > tr > td:nth-child(5) > a"));
            int zonesCount = Integer.parseInt(driver.findElement(By.cssSelector("tbody > tr:nth-of-type(" + (i + 2) + ") > td:nth-of-type(6)")).getText());

            if (zonesCount > 0) {
                countries.get(i).click();
                int zonesSize = driver.findElements(By.cssSelector("[id=table-zones] > tbody > tr > td:nth-child(3) > input[type=hidden]")).size();
                List<String> zonesList = new ArrayList<String>();

                for (int j = 0; j < zonesSize; j++) {

                    List<WebElement> zones = driver.findElements(By.cssSelector("[id=table-zones] > tbody > tr > td:nth-child(3) > input[type=hidden]"));
                    zonesList.add(zones.get(j).getAttribute("value"));
                }
                List<String> zonesListBeforeSorting = new ArrayList<String>(zonesList);
                Collections.sort(zonesList);
                assertEquals(zonesList, zonesListBeforeSorting);
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


    @AfterMethod
    public void stop() {
        driver.close();
    }
}