package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.PopUpPage;
import pages.RegistrationPage;

import java.util.Random;
import static utils.UserFactory.*;


public class RegistrationTests extends ApplicationManager {
    RegistrationPage registrationPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void goToRegistrationPage() {
        new HomePage(getDriver()).clickBtnSignUp();
        registrationPage = new RegistrationPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        User user = User.builder()
                .firstName("Yri")
                .lastName("Petrov")
                .email("yripetrov"+i+"@gmail.com")
                .password("Qwerty1234!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("You are logged in success"));
    }

    @Test
    public void registrationPositiveTest_WithFaker() {
        User user = positiveUser();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("You are logged in success"));
    }
    @Test
    public void registrationNegativeTest_UserAlreadyExists(){
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olyagold@gmail.com")
                .password("Password123!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver()).isTextInPopUpMessagePresent("User already exists"));
    }
    @Test
    public void registrationNegativeTest_WithSpaseInFirstName(){
        User user = User.builder()
                .firstName(" ")
                .lastName("Goldshtein")
                .email("olyagold@gmail.com")
                .password("Password123!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue( new PopUpPage(getDriver()).isTextInPopUpMessagePresent("must not be blank"));

    }
    @Test
    public void registrationNegativeTest_InvalidEmail_WithoutAtSymbol() {
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olyagoldgmail.com")
                .password("Password123!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(
                registrationPage.isTextInErrorPresent("Wrong email format"),
                "validate field email");
    }

    @Test
    public void registrationNegativeTest_InvalidEmail_WithoutDomain() {
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olyagold@gmail.")
                .password("Password123!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(
                registrationPage.isTextInErrorPresent("Wrong email format"),
                "validate field email");
    }

    @Test
    public void registrationNegativeTest_InvalidEmail_WithSpaces() {
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olya gold@gmail.com")
                .password("Password123!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(
                registrationPage.isTextInErrorPresent("Wrong email format"),
                "validate field email");
    }

    @Test
    public void registrationNegativeTest_WeakPassword_NoSpecialCharacters() {
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olyagold@gmail.com")
                .password("Password123")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        registrationPage.pause(1);

        Assert.assertTrue(
                registrationPage.isTextInErrorPresent("Password must contain 1 uppercase letter," +
                        " 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"));
    }

    @Test
    public void registrationNegativeTest_WeakPassword_NoUpperCase(){
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olyagold@gmail.com")
                .password("password123!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(
                registrationPage.isTextInErrorPresent("Password must contain 1 uppercase letter," +
                        " 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"));
    }

    @Test
    public void registrationNegativeTest_WeakPassword_NoDigits(){
        User user = User.builder()
                .firstName("Olya")
                .lastName("Goldshtein")
                .email("olyagold@gmail.com")
                .password("Passwordpas!")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(
                registrationPage.isTextInErrorPresent("Password must contain 1 uppercase letter," +
                        " 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"));

    }
    @Test
    public void registrationNegativeTest_WithAllEmptyFields(){
        User user = User.builder()
                .firstName("")
                .lastName("")
                .email("")
                .password("")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
       softAssert.assertTrue(registrationPage
               .isTextInErrorPresent("Name is required"), "validate error message Name is required");
        softAssert.assertTrue(registrationPage
                .isTextInErrorPresent("Last name is required"), "validate error message  Last Name is required");
        softAssert.assertTrue(registrationPage
                .isTextInErrorPresent("Email is required"), "validate error message Email is required");
        softAssert.assertTrue(registrationPage
                .isTextInErrorPresent("Password is required"), "validate error message Password is required");
        softAssert.assertAll();


    }
}