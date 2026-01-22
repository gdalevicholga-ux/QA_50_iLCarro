package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;

import java.util.Random;

public class RegistrationTests extends ApplicationManager {
    @Test
    public void registrationPositiveTest(){
        User user = User.builder()
                .firstName("Lola")
                .lastName("Fishman")
                .email("qa" + new Random().nextInt(100) + "@gmail.com")
                .password("Fish123!")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnSignUp();
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.typeSignUpForm(user);
        registrationPage.clickCheckBoxIAgree();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage.isRegisteredDisplayed());
    }
}