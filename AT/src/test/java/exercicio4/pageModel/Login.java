package exercicio4.pageModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
    private final WebDriver driver;


    private final By emailField = By.cssSelector("input[data-qa='login-email']");
    private final By passwordField = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private By errorMessage = By.xpath("//p[text()='Your email or password is incorrect!']");

    private final By signupNameField = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmailField = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public void enterLoginDetails(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void submitLogin() {
        driver.findElement(loginButton).click();
    }

    public void enterSignupDetails(String name, String email) {
        driver.findElement(signupNameField).sendKeys(name);
        driver.findElement(signupEmailField).sendKeys(email);
    }

    public void submitSignup() {
        driver.findElement(signupButton).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}
