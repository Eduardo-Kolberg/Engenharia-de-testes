import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Tp3Tests {

    private WebDriver driver;

    private final String cookiesFilePath = "cookies.txt";


    @BeforeEach
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test
    public void questao3Parte1() throws InterruptedException {

        driver.get("https://demo.prestashop.com/#/en/front");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));

        WebElement signInSpan = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div#_desktop_user_info span.hidden-sm-down")
                )
        );

        assertNotNull(signInSpan);
        signInSpan.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-link-action='display-register-form']"))).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-firstname"))).sendKeys("Eduardo");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-lastname"))).sendKeys("silva");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email"))).sendKeys("Eduardo.Silva@something.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password"))).sendKeys("paralel10");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement termsCheckbox = driver.findElement(By.name("psgdpr"));
        termsCheckbox.click();
        WebElement privacyCheckbox = driver.findElement(By.name("customer_privacy"));
        privacyCheckbox.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-link-action='save-customer']"))).click();
        Thread.sleep(5000);
        WebElement signOutLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("a.logout.hidden-sm-down[href*='mylogout']")
                )
        );
        try {
            saveCookies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(signOutLink);
    }

    @Test
    public void questao3Parte2() throws InterruptedException {

        driver.get("https://demo.prestashop.com/#/en/front");
        try {
            loadCookies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        driver.navigate().refresh();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));

        WebElement signInSpan = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div#_desktop_user_info span.hidden-sm-down")
                )
        );

        assertNotNull(signInSpan);
        signInSpan.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-link-action='display-register-form']"))).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-firstname"))).sendKeys("Eduardo");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-lastname"))).sendKeys("silva");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email"))).sendKeys("Eduardo.Silva@something.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password"))).sendKeys("paralel10");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement termsCheckbox = driver.findElement(By.name("psgdpr"));
        termsCheckbox.click();
        WebElement privacyCheckbox = driver.findElement(By.name("customer_privacy"));
        privacyCheckbox.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-link-action='save-customer']"))).click();
        Thread.sleep(10000);
        WebElement signOutLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("a.logout.hidden-sm-down[href*='mylogout']")
                )
        );

        assertNotNull(signOutLink);
        Thread.sleep(5000);

        WebElement productMiniature = driver.findElement(By.cssSelector("article.product-miniature.js-product-miniature[data-id-product='1'][data-id-product-attribute='1']"));
        productMiniature.click();

        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button.btn.btn-primary.add-to-cart[data-button-action='add-to-cart']")
        )).click();

        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("myModalLabel")
                )
        );
        assertEquals("\uE876Product successfully added to your shopping cart", successMessage.getText());
        WebElement cart = driver.findElement(By.className("cart-products-count"));
        assertEquals("(1)", cart.getText());
    }

    @Test
    public void questao4part1() throws InterruptedException, IOException {
        driver.get("https://practicetestautomation.com/practice-test-login/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("student");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("Password123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit"))).click();
        assertEquals("https://practicetestautomation.com/logged-in-successfully/", driver.getCurrentUrl());
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(screenshot, new File("screenshot.png"));
    }



    private void saveCookies() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(cookiesFilePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        Set<Cookie> cookies = driver.manage().getCookies();
        Set<Cookie> modifiedCookies = new HashSet<>();
        for (Cookie cookie : cookies) {

            if (!cookie.getDomain().equals(".prestashop.com")) {
                cookie = new Cookie(cookie.getName(), cookie.getValue(), ".prestashop.com", cookie.getPath(), cookie.getExpiry());
            }
            modifiedCookies.add(cookie);
        }
        objectOutputStream.writeObject(modifiedCookies);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    private void loadCookies() throws IOException, ClassNotFoundException {
        FileInputStream fileOutputStream = new FileInputStream(cookiesFilePath);
        ObjectInputStream objectintputStream = new ObjectInputStream(fileOutputStream);

        Set<Cookie> cookies = (Set<Cookie>) objectintputStream.readObject();


            for (Cookie cookie : cookies) {
                if (Objects.requireNonNull(cookie.getDomain()).equals(".prestashop.com")) {
                    driver.manage().addCookie(cookie);
                }
            }
        objectintputStream.close();
        fileOutputStream.close();
    }

    @AfterEach
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }

}
