package elementsProperties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class Task10 {


    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.navigate().to("http://localhost/litecart/");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
    }

    @Test
    public void checkProductProperties() {

        WebElement product = driver.findElement(By.cssSelector("div#box-campaigns a.link"));
        String productTitle = product.findElement(By.cssSelector(".name")).getText();
        String regularPrice = product.findElement(By.cssSelector(".regular-price")).getText();
        String campaignPrice = product.findElement(By.cssSelector(".campaign-price")).getText();
        String regularPriceColor = product.findElement(By.cssSelector(".regular-price")).getCssValue("color");
        String regularPriceFormat = product.findElement(By.cssSelector(".regular-price")).getCssValue("text-decoration");
        String campaignPriceColor = product.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
        String campaignPriceFormat = product.findElement(By.cssSelector(".campaign-price")).getCssValue("font-weight");

        product.click();

        Assert.assertEquals(productTitle, (driver.findElement(By.cssSelector("h1.title")).getText()));
        Assert.assertEquals(regularPrice, (driver.findElement(By.cssSelector(".regular-price"))).getText());
        Assert.assertEquals(campaignPrice, (driver.findElement(By.cssSelector(".campaign-price"))).getText());

        //this assertion will fail
        Assert.assertEquals(regularPriceColor, (driver.findElement(By.cssSelector(".regular-price"))).getCssValue("color"));
        //this assertion will fail
        Assert.assertEquals(regularPriceFormat, (driver.findElement(By.cssSelector(".regular-price"))).getCssValue("text-decoration"));

        Assert.assertEquals(campaignPriceColor, (driver.findElement(By.cssSelector(".campaign-price"))).getCssValue("color"));
        Assert.assertEquals(campaignPriceFormat, (driver.findElement(By.cssSelector(".campaign-price"))).getCssValue("font-weight"));


    }

    @AfterTest
    public void stop() {
        driver.close();
    }
}
