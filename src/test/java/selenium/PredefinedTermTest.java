package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import pages.ReservePredefinedTerm;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class PredefinedTermTest {

    private WebDriver browser;

    private ReservePredefinedTerm reservePredefinedTerm;

    @BeforeMethod
    public void setUp() {
        // instantiate firefox browser
        System.setProperty("webdriver.gecko.driver","src/test/java/resources/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette",true);
        browser = new FirefoxDriver(firefoxOptions);

        //maximize window
        browser.manage().window().maximize();

        // TODO URL do stranice koju treba testirati
        browser.navigate().to("https://ruter.no/en/?fbclid=IwAR0trS_FoLyeE0D5yVE_e5LyEN-r2mRn5pSMZV1fBkBMsOK31sIql1uC978");

        reservePredefinedTerm = PageFactory.initElements(browser, ReservePredefinedTerm.class);
    }

}
