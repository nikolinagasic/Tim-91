package selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ReserveTermPage;

@SuppressWarnings("FieldCanBeLocal")
public class ReserveTermTest {

    private WebDriver browser;

    private ReserveTermPage reserveTermPage;

    @BeforeMethod
    public void setUp() {
        // instantiate firefox browser
        System.setProperty("webdriver.gecko.driver","src/test/java/resources/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette",true);
        browser = new FirefoxDriver(firefoxOptions);

        //maximize window
        browser.manage().window().maximize();

        browser.navigate().to("http://localhost:3000/#/login");

        reserveTermPage = PageFactory.initElements(browser, ReserveTermPage.class);
    }

    @Test
    public void testReserveTerm() {
        reserveTermPage.ensureIsDisplayedInput();

        reserveTermPage.getInputMail().sendKeys("patient@gmail.com");
        threadSleep(350);
        reserveTermPage.getInputPassword().sendKeys("12345678");
        threadSleep(350);
        reserveTermPage.getBtnPrijaviSe().click();

        reserveTermPage.ensureAlertDisplayed();
        threadSleep(1000);
        reserveTermPage.getDriver().switchTo().alert().accept();

        reserveTermPage.getLiKlinike().click();
        reserveTermPage.ensureIsDisplayedPatientPage();
        threadSleep(700);
        ((JavascriptExecutor)reserveTermPage.getDriver()).executeScript
                ("document.getElementById('headerSearchClinicDate').removeAttribute('readonly',0);");
        WebElement fromDateBox = reserveTermPage.getDatepicker();
        fromDateBox.clear();
        fromDateBox.sendKeys("2020-02-10");

        threadSleep(1000);
        reserveTermPage.getTdInTableClinics().click();

        threadSleep(1200);
        reserveTermPage.ensureIsDisplayedTableDoctors();
        reserveTermPage.getBtnRezervisi().click();
        threadSleep(1200);
        reserveTermPage.ensureIsDisplayedOdaberi();
        reserveTermPage.getBtnOdaberi().click();
        threadSleep(1200);
        reserveTermPage.ensureIsDisplayedPotvrdaRez();
        reserveTermPage.getBtnFinalReserve().click();
        threadSleep(1200);
        reserveTermPage.ensureAlertDisplayed();
        String alert_text = reserveTermPage.getDriver().switchTo().alert().getText();
        threadSleep(1000);
        if(alert_text.equals("Захтев успешно послат. О детаљима прегледа бићете обавештени путем адресе е-поште.")){
            reserveTermPage.getDriver().switchTo().alert().accept();
            System.out.println("Bravo Aco");
        }
        else{
            reserveTermPage.getDriver().switchTo().alert().accept();
            System.out.println("Neko je vec rezervisao termin");
        }
    }

    private void threadSleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
