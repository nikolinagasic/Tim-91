package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CreatePredefinedPage;
import pages.ReservePredefinedPage;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused", "IfStatementWithIdenticalBranches"})
public class PredefinedTermTest {

    private WebDriver browser;

    private CreatePredefinedPage createPredefinedPage;

    private ReservePredefinedPage reservePredefinedPage;

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

        createPredefinedPage = PageFactory.initElements(browser, CreatePredefinedPage.class);
        reservePredefinedPage = PageFactory.initElements(browser, ReservePredefinedPage.class);
    }

    @Test
    public void testCreatePredefined() {
        createPredefinedPage.ensureIsDisplayedInput();

        createPredefinedPage.getInputMail().sendKeys("cadmin@gmail.com");
        threadSleep(350);
        createPredefinedPage.getInputPassword().sendKeys("admin");
        threadSleep(350);
        createPredefinedPage.getBtnPrijaviSe().click();

        createPredefinedPage.ensureAlertDisplayed();
        threadSleep(1000);
        createPredefinedPage.getDriver().switchTo().alert().accept();
        createPredefinedPage.ensureIsDisplayedPage();

        threadSleep(1000);
        createPredefinedPage.getLiTermini().click();
        createPredefinedPage.ensureIsDisplayedNaslov();

        threadSleep(500);
        ((JavascriptExecutor)createPredefinedPage.getDriver()).executeScript
                ("document.getElementById('a_date_predefinedExam').removeAttribute('readonly',0);");
        WebElement fromDateBox= createPredefinedPage.getDatumPregleda();
        fromDateBox.clear();
        fromDateBox.sendKeys("2020-02-10");

        threadSleep(1000);
        createPredefinedPage.getSelectDoctor().selectByIndex(1);
        threadSleep(1000);
        createPredefinedPage.getInputCena().sendKeys("999.99");
        threadSleep(1000);
        createPredefinedPage.getInputPopust().sendKeys("5");
        threadSleep(1000);
        createPredefinedPage.getBtnNapraviPregled().click();

        createPredefinedPage.ensureAlertDisplayed();
        threadSleep(1200);
        String text_alert = createPredefinedPage.getDriver().switchTo().alert().getText();
        if(text_alert.equals("Успешно сте креирали термин.")){
            createPredefinedPage.getDriver().switchTo().alert().accept();
            System.out.println("Bravo Aco");
        }
        else{
            createPredefinedPage.getDriver().switchTo().alert().accept();
            System.out.println("Termin je vec kreiran");
        }
    }

    @Test
    public void testReservePredefined(){
        reservePredefinedPage.ensureIsDisplayedInput();

        reservePredefinedPage.getInputMail().sendKeys("patient@gmail.com");
        threadSleep(350);
        reservePredefinedPage.getInputPassword().sendKeys("12345678");
        threadSleep(350);
        reservePredefinedPage.getBtnPrijaviSe().click();

        reservePredefinedPage.ensureAlertDisplayed();
        threadSleep(1000);
        reservePredefinedPage.getDriver().switchTo().alert().accept();
        reservePredefinedPage.ensureIsDisplayedPagePatient();

        threadSleep(1200);
        reservePredefinedPage.getLiListaKlinika().click();
        reservePredefinedPage.ensureIsDisplayedTableClinics();

        threadSleep(1200);
        reservePredefinedPage.getBtnPrikazi().click();
        reservePredefinedPage.ensureIsDisplayedClinicProfile();
        threadSleep(1200);
        reservePredefinedPage.getLinkPredefined().click();
        reservePredefinedPage.ensureIsDisplayedPredefinedPage();

        reservePredefinedPage.getBtnReserve().click();
        reservePredefinedPage.ensureAlertDisplayed();
        String text_alert = reservePredefinedPage.getDriver().switchTo().alert().getText();
        threadSleep(1200);
        if(text_alert.equals("Термин успешно резервисан.")){
            reservePredefinedPage.getDriver().switchTo().alert().accept();
            System.out.println("Bravo Aco");
        }
        else{
            reservePredefinedPage.getDriver().switchTo().alert().accept();
            System.out.println("Neko je rezervisao pre tebe");
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
