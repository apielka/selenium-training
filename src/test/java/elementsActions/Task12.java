package elementsActions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Task12 {


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

    private String generateProductName() {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(1000);
        return "Product" + randomNumber;
    }

    private int generateCode() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(10000);
    }


    @Test
    public void checkAddingProduct() {
        String productName = generateProductName();
        String code = String.valueOf(generateCode());
        addProduct(productName, code);
        searchProductInCatalog(productName);
    }


    private void addProduct(String productName, String code) {
        File myFile = new File("image.jpg");
        String absolutePath = myFile.getAbsolutePath();

        login();
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.cssSelector("#content > div:nth-of-type(1) > a:nth-of-type(2)")).click();

        driver.findElement(By.cssSelector("label input[value='1']")).click();
        driver.findElement(By.cssSelector("[name='name[en]']")).sendKeys(productName);
        driver.findElement(By.cssSelector("[name=code]")).sendKeys(code);
        driver.findElement(By.cssSelector("[name='product_groups[]'][value='1-1']")).click();
        driver.findElement(By.cssSelector("[name='new_images[]']")).sendKeys(absolutePath);
        driver.findElement(By.cssSelector("[name=quantity]")).clear();
        driver.findElement(By.cssSelector("[name=quantity]")).sendKeys("10");
        driver.findElement(By.cssSelector("[name='date_valid_from']")).sendKeys("01012018");
        driver.findElement(By.cssSelector("[name='date_valid_to']")).sendKeys("31122018");

        driver.findElement(By.cssSelector("div[class=tabs] a[href='#tab-information']")).click();
        driver.findElement(By.cssSelector("select[name=manufacturer_id]")).sendKeys("ACME" + Keys.ENTER);
        driver.findElement(By.cssSelector("[name=keywords]")).sendKeys("Keywords test");
        driver.findElement(By.cssSelector("[name='short_description[en]']")).sendKeys("Short description test");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Description test");
        driver.findElement(By.cssSelector("[name='head_title[en]']")).sendKeys("Head title test");
        driver.findElement(By.cssSelector("[name='meta_description[en]']")).sendKeys("Meta desription test");

        driver.findElement(By.cssSelector("div[class=tabs] a[href='#tab-prices']")).click();
        driver.findElement(By.cssSelector("[name=purchase_price]")).clear();
        driver.findElement(By.cssSelector("[name=purchase_price]")).sendKeys("999");
        driver.findElement(By.cssSelector("option[value=USD]")).click();

        driver.findElement(By.cssSelector("button[name=save]")).click();
    }

    private void searchProductInCatalog(String productName) {
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.cssSelector("input[name=query]")).sendKeys(productName + Keys.ENTER);
        Assert.assertTrue(driver.findElement(By.linkText(productName)).getText().contains(productName));
    }

    @AfterTest
    public void stop() {
        driver.close();
    }
}