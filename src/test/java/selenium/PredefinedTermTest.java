package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CreatePredefinedPage;
import pages.ReservePredefinedPage;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class PredefinedTermTest {

    private WebDriver browser;

    private CreatePredefinedPage createPredefinedPage;

    @BeforeMethod
    public void setUp() {
        // instantiate firefox browser
        System.setProperty("webdriver.gecko.driver","src/test/java/resources/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette",true);
        browser = new FirefoxDriver(firefoxOptions);

        //maximize window
        browser.manage().window().maximize();

        // TODO URL do stranice koju treba testirati
        browser.navigate().to("http://localhost:3000/#/pageadmin");

        createPredefinedPage = PageFactory.initElements(browser, CreatePredefinedPage.class);
    }

    @Test
    public void test(){

    }

}
