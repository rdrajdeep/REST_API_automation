package expertchat.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenBrowser {

    private WebDriver webPage;

    public static void main ( String[] args ) {

        OpenBrowser ob = new OpenBrowser ( );
        ob.setupChrome ( );
        ob.expertChatLogin ( "http://web.qa.experchat.com/#/login" );

    }

    private void setupChrome ( ) {

        //System.setProperty("webdriver.gecko.driver", "Driver/geckodriver.exe");
        webPage = new FirefoxDriver ( );
        webPage.manage ( ).window ( ).maximize ( );
    }

    private void expertChatLogin ( String url ) {

        Actions actions = new Actions ( webPage );
        webPage.get ( url );
        webPage.findElement ( By.name ( "email" ) ).sendKeys ( "kishor+expert31@atlogys.com" );
        webPage.findElement ( By.name ( "password" ) ).sendKeys ( "jyoti1032" );
        webPage.findElement ( By.cssSelector ( ".md-raised.md-primary.btn-large" ) ).click ( );
        WebDriverWait wait = new WebDriverWait ( webPage, 10 );

        By locatorBackButton = By.xpath ( "html/body/main/section/div/div/div/div/div/profile-header/div/md-toolbar/div/div[1]/button" );
        wait.until ( ExpectedConditions.visibilityOfElementLocated ( locatorBackButton ) );
        WebElement bButton = webPage.findElement ( locatorBackButton );
        actions.moveToElement ( bButton ).click ( ).build ( ).perform ( );

        By contentSource = By.cssSelector ( ".md-no-style.md-button.md-ink-ripple" );
        WebElement cSource = webPage.findElement ( contentSource );
        actions.moveToElement ( cSource ).click ( ).perform ( );

        By addAcount = By.cssSelector ( ".md-raised.md-primary.md-btn-md.md-button.md-ink-ripple" );
        By fConnect = By.xpath ( "html/body/main/section/div/div/div/div/div/div[1]/md-list/md-list-item[2]/div/button" );
        By yConnect = By.xpath ( "html/body/main/section/div/div/div/div/div/div[1]/md-list/md-list-item[3]/div/button" );
        By iConnect = By.xpath ( "html/body/main/section/div/div/div/div/div/div[1]/md-list/md-list-item[4]/div/button" );

        WebElement aAccount = webPage.findElement ( addAcount );
        WebElement fb = webPage.findElement ( fConnect );
        WebElement yt = webPage.findElement ( yConnect );
        WebElement insta = webPage.findElement ( iConnect );
        actions.moveToElement ( aAccount ).click ( ).perform ( );
        actions.moveToElement ( fb ).click ( ).perform ( );
        sleep ( );
        fbLogin ( );

    }

    private void sleep ( ) {

        try {
            Thread.sleep ( 10000 );
        } catch ( InterruptedException e ) {

        }
    }

    public String fbLogin ( ) {

        webPage.findElement ( By.name ( "email" ) ).sendKeys ( "ram@atlogys.com" );
        webPage.findElement ( By.id ( "pass" ) ).sendKeys ( "atlogys@123" );
        webPage.findElement ( By.id ( "loginbutton" ) ).click ( );
        String cUrl = webPage.getCurrentUrl ( );
        webPage.quit ( );
        return cUrl;
    }

    public String instaLogin ( WebDriver webPage, String url ) {

        webPage.navigate ( ).to ( url );
        webPage.findElement ( By.id ( "id_username" ) ).sendKeys ( "ram@atlogys.com" );
        webPage.findElement ( By.id ( "id_password" ) ).sendKeys ( "atlogys@123" );
        webPage.findElement ( By.cssSelector ( "input.button-green" ) ).click ( );
        return webPage.getCurrentUrl ( );
    }
}
