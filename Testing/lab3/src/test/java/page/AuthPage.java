package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthPage extends DefaultPage {


    @Getter
    private final String URL = "https://gitlab.se.ifmo.ru/users/sign_in";

    private final By usernameField = By.xpath("//input[@id='user_login']");
    private final By passwordField = By.xpath("//input[@id='user_password']");

    private final By rememberMeButton = By.xpath("//input[@id='user_remember_me']");
    private final By loginButton = By.xpath("//input[@type='submit']");


    public AuthPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void setUsername(String username) {
        typoIntoField(usernameField, username);
    }

    public void setPassword(String password) {
        typoIntoField(passwordField, password);
    }

    public void setRememberMe(boolean rememberMe) {
        click(rememberMeButton);
    }

    public void submit() {
        click(loginButton);
    }


}
