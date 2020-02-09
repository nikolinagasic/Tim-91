package selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SearchClinicPage;

@SuppressWarnings({"FieldCanBeLocal", "unused", "IfStatementWithIdenticalBranches"})
public class SearchClinicTest {

    private WebDriver browser;

    private SearchClinicPage searchClinicPage;

    @BeforeMethod
    public void setUp() {
        // instantiate firefox browser
        System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette", true);
        browser = new FirefoxDriver(firefoxOptions);

        //maximize window
        browser.manage().window().maximize();

        browser.navigate().to("http://localhost:3000/#/login");

        searchClinicPage = PageFactory.initElements(browser, SearchClinicPage.class);
    }


    @Test
    public void testFilterClinic(){

        searchClinicPage.ensureIsDisplayedInput();
        searchClinicPage.getInputMail().sendKeys("patient@gmail.com");
        threadSleep(350);
        searchClinicPage.getInputPassword().sendKeys("12345678");
        threadSleep(350);
        searchClinicPage.getBtnPrijaviSe().click();
        searchClinicPage.ensureAlertDisplayed();
        threadSleep(1000);
        searchClinicPage.getDriver().switchTo().alert().accept();
        searchClinicPage.ensureIsDisplayedPagePatients();

        threadSleep(1200);
        searchClinicPage.getLiKlinike().click();
        searchClinicPage.ensureIsDisplayedTableClinics();

        ((JavascriptExecutor)searchClinicPage.getDriver()).executeScript
                ("document.getElementById('headerSearchClinicDate').removeAttribute('readonly',0);");
        WebElement fromDateBox = searchClinicPage.getDatepicker();
        fromDateBox.clear();
        fromDateBox.sendKeys("2020-02-10");
        threadSleep(1000);
        searchClinicPage.getSelectTip().selectByIndex(2);   //kardiologija
        threadSleep(350);
        searchClinicPage.getBtnPretrazi().click();
        threadSleep(1200);
        searchClinicPage.ensureIsDisplayedTableClinics();
        threadSleep(1500);
        searchClinicPage.getSelectTip().selectByIndex(0);   //svi tipovi
        threadSleep(350);
        searchClinicPage.getBtnPretrazi().click();
        threadSleep(1200);
        searchClinicPage.ensureIsDisplayedTableClinics();
        threadSleep(1000);
        searchClinicPage.getCenaDo().sendKeys("3000");
        threadSleep(1600);
        searchClinicPage.getCenaDo().clear();
        threadSleep(1000);
        searchClinicPage.getOcenaOd().sendKeys("8");
        threadSleep(1600);
        searchClinicPage.getOcenaDo().sendKeys("9");
        threadSleep(1600);
        searchClinicPage.getOcenaDo().clear();
        threadSleep(1200);
        searchClinicPage.getBtnPrikazi().click();
        searchClinicPage.ensureIsDisplayedClinicProfile();
        threadSleep(2000);
        searchClinicPage.getLinkPredefined().click();
        searchClinicPage.ensureIsDisplayedTableClinics();

    }

    @Test
    public void testSearchClinic(){
        searchClinicPage.ensureIsDisplayedInput();
        searchClinicPage.getInputMail().sendKeys("patient@gmail.com");
        threadSleep(350);
        searchClinicPage.getInputPassword().sendKeys("12345678");
        threadSleep(350);
        searchClinicPage.getBtnPrijaviSe().click();
        searchClinicPage.ensureAlertDisplayed();
        threadSleep(1000);
        searchClinicPage.getDriver().switchTo().alert().accept();
        searchClinicPage.ensureIsDisplayedPagePatients();
        threadSleep(1200);
        searchClinicPage.getLiKlinike().click();
        searchClinicPage.ensureIsDisplayedTableClinics();
        threadSleep(1200);

        searchClinicPage.getBtnPretrazi().click();
        threadSleep(1200);
        searchClinicPage.ensureAlertDisplayed();
        String alert_text = searchClinicPage.getDriver().switchTo().alert().getText();
        threadSleep(1000);
        if(alert_text.equals("Обавезан је унос датума прегледа.")){
            searchClinicPage.getDriver().switchTo().alert().accept();
            System.out.println("Uspesno");
        }
        else{
            searchClinicPage.getDriver().switchTo().alert().accept();
            System.out.println(alert_text);
        }

        threadSleep(1200);
        ((JavascriptExecutor)searchClinicPage.getDriver()).executeScript
                ("document.getElementById('headerSearchClinicDate').removeAttribute('readonly',0);");
        WebElement fromDateBox = searchClinicPage.getDatepicker();
        fromDateBox.clear();
        fromDateBox.sendKeys("2020-01-08");
        threadSleep(1000);
        searchClinicPage.getBtnPretrazi().click();
        threadSleep(1200);
        searchClinicPage.ensureIsDisplayedTableClinics();
        threadSleep(1000);
        searchClinicPage.getColumnTable().click();
        threadSleep(500);
        searchClinicPage.ensureIsDisplayedFilterDoctors();

    }

    private void threadSleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
