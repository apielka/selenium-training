package elementsActions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Task11 {


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

    private String generateEmail() {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(10000);
        return "selenium-training" + randomNumber + "@tempmail.com";
    }


    @Test
    public void checkSignUpAndSignIn() {

        String email = generateEmail();
        String password = "test1234";

        registerUser(email, password);
        Assert.assertTrue(driver.findElement(By.cssSelector("div.notice.success")).getText().contains("Your customer account has been created"));
        logout();
        Assert.assertTrue(driver.findElement(By.cssSelector("div.notice.success")).getText().contains("You are now logged out"));
        login(email, password);
        Assert.assertTrue(driver.findElement(By.cssSelector("div.notice.success")).getText().contains("John Doe"));
        logout();
        Assert.assertTrue(driver.findElement(By.cssSelector("div.notice.success")).getText().contains("You are now logged out"));
    }

    private void registerUser(String email, String password) {
        driver.findElement(By.cssSelector("#box-account-login a")).click();

        driver.findElement(By.cssSelector("[name=company]")).sendKeys("Company XYZ1234");
        driver.findElement(By.cssSelector("[name=firstname]")).sendKeys("John");
        driver.findElement(By.cssSelector("[name=lastname]")).sendKeys("Doe");
        driver.findElement(By.cssSelector("[name=address1]")).sendKeys("Zygmunta Starego 12A/99");
        driver.findElement(By.cssSelector("[name=postcode]")).sendKeys("11-999");
        driver.findElement(By.cssSelector("[name=city]")).sendKeys("Warsaw");
        Select country = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        country.selectByValue("PL");
        driver.findElement(By.cssSelector("[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("[name=phone]")).sendKeys("+481234567890");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=create_account]")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("div.notice.success")));

    }


    private void login(String email, String password) {

        driver.findElement(By.cssSelector("[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=login]")).click();
    }

    private void logout() {
        driver.findElement(By.cssSelector("div.content > ul.list-vertical > li:nth-of-type(4) > a")).click();
    }


    @AfterTest
    public void stop() {
        driver.close();
    }

}
