package exercicio4.testes;

import exercicio4.pageModel.Login;
import exercicio4.pageModel.Signup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.apache.commons.io.FileUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoginTest {
    private WebDriver driver;
    private Login loginPage;
    private Signup signupPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://automationexercise.com");
        loginPage = new Login(driver);
        signupPage = new Signup(driver);
    }

    @Test
    public void testInvalidLogin() {

        driver.findElement(By.xpath("//a[@href='/login']")).click();

        loginPage.enterLoginDetails("invalid@example.com","wrongpassword");

        loginPage.submitLogin();

        assertEquals("Your email or password is incorrect!", loginPage.getErrorMessage());
    }

    @Test
    public void testUserSignup() throws InterruptedException {

        driver.findElement(By.xpath("//a[@href='/login']")).click();
        loginPage.enterSignupDetails("Eduardo", generateRandomEmail());
        loginPage.submitSignup();

        signupPage.enterName("Eduardo");
        signupPage.enterPassword("teste1234!");
        signupPage.enterFirstName("Eduardo");
        signupPage.enterLastName("Teste");
        signupPage.enterAddress("Rua 1234");
        signupPage.selectCountry("United States");
        signupPage.enterState("California");
        signupPage.enterCity("Los Angeles");
        signupPage.enterZipcode("900011");
        signupPage.enterMobileNumber("1234567890");


        signupPage.submitSignup();
        Thread.sleep(1000);
        String expectedUrl = "https://automationexercise.com/account_created";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl, "A URL deve mostrar que o signup teve sucesso");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            takeScreenshot("test-failure.png");
            driver.quit();
        }
    }

    private void takeScreenshot(String filename) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("./screenshots/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public static String generateRandomEmail() {
        String randomString = generateRandomString(5);
        return randomString + "@example.com";
    }
}
