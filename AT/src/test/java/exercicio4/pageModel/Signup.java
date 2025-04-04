package exercicio4.pageModel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Signup {
    private final WebDriver driver;


    private final By nameField = By.cssSelector("input[data-qa='name']");
    private final By passwordField = By.cssSelector("input[data-qa='password']");
    private final By firstNameField = By.cssSelector("input[data-qa='first_name']");
    private final By lastNameField = By.cssSelector("input[data-qa='last_name']");
    private final By addressField = By.cssSelector("input[data-qa='address']");
    private final By countrySelect = By.cssSelector("select[data-qa='country']");
    private final By stateField = By.cssSelector("input[data-qa='state']");
    private final By cityField = By.cssSelector("input[data-qa='city']");
    private final By zipcodeField = By.cssSelector("input[data-qa='zipcode']");
    private final By mobileNumberField = By.cssSelector("input[data-qa='mobile_number']");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");

    public Signup(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void selectCountry(String country) {
        WebElement countryDropdown = driver.findElement(countrySelect);
        countryDropdown.sendKeys(country);
    }

    public void enterState(String state) {
        driver.findElement(stateField).sendKeys(state);
    }

    public void enterCity(String city) {
        driver.findElement(cityField).sendKeys(city);
    }

    public void enterZipcode(String zipcode) {
        driver.findElement(zipcodeField).sendKeys(zipcode);
    }

    public void enterMobileNumber(String mobileNumber) {
        driver.findElement(mobileNumberField).sendKeys(mobileNumber);
    }


    public void submitSignup() {
        WebElement button = driver.findElement(createAccountButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public boolean isNameFieldVisible() {
        return driver.findElement(nameField).isDisplayed();
    }
}
