package pages;

import dto.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegistrationPage extends BasePage{
    public RegistrationPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory
                (driver, 10), this);
    }
        @FindBy(id = "name")
        WebElement inputFirstName;
        @FindBy(id = "lastName")
        WebElement inputLastName;
        @FindBy(id = "email")
        WebElement inputEmail;
        @FindBy(id = "password")
        WebElement inputPassword;
        @FindBy(xpath = "//*[@class='checkbox-container']")
        WebElement checkboxIAgree;
        @FindBy(xpath = "//button[text()='Y’alla!']")
        WebElement btnYalla;
        @FindBy(xpath = "//h2[text()='You are logged in success']")
        WebElement popUpSuccessfulRegistered;

        public void typeSignUpForm(User user) {
            inputFirstName.sendKeys(user.getFirstName());
            inputLastName.sendKeys(user.getLastName());
            inputEmail.sendKeys(user.getEmail());
            inputPassword.sendKeys(user.getPassword());
            checkboxIAgree.click();

        }
        public void clickCheckBoxIAgree(){
            if (!checkboxIAgree.isSelected())
                checkboxIAgree.click();

        }

        public void clickBtnYalla(){
            btnYalla.click();
        }

        public boolean isRegisteredDisplayed(){
            return isElementDisplayed(popUpSuccessfulRegistered);
        }
    }

