package waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.testng.Assert.assertEquals;

public class Task13 {


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
    public void checkAddingAndDeletingProducts() {
        addProduct("Blue Duck", 1);
        addProduct("Red Duck", 1);
        addProduct("Green Duck", 1);
        removeProduct();
    }

    private void addProduct(String productName, int amount) {
        int currentQuantityInBasket = Integer.parseInt(driver.findElement(cssSelector("span.quantity")).getText());
        for (int i = 1; i <= amount; i++) {
            driver.findElement(cssSelector(".link[title='" + productName + "']")).click();
            driver.findElement(cssSelector("[name='add_cart_product']")).click();
            WebElement quantityInBasket = driver.findElement(cssSelector("span.quantity"));
            wait.until(textToBe(cssSelector("span.quantity"), String.valueOf(i + currentQuantityInBasket)));
            driver.findElement(cssSelector("img[title='My Store']")).click();
        }
        driver.findElement(By.cssSelector("div#cart > a.link"));
    }

    private void removeProduct() {

        driver.findElement(By.cssSelector("div#cart > a.link")).click();

        for (int i = 0; i <= 2; i++) {
            driver.findElement(By.cssSelector("button[value='Remove']")).click();
            WebElement summaryTable = driver.findElement(By.cssSelector("#box-checkout-summary"));
            wait.until(ExpectedConditions.stalenessOf(summaryTable));
        }
    }


    @AfterTest
    public void stop() {
        driver.close();
    }
}
