package selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ReserveRoomPage;
import pages.ReserveTermPage;

public class ReserveRoomTest {
    private WebDriver browser;

    private ReserveTermPage reserveTermPage;

    private ReserveRoomPage reserveRoomPage;

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
        reserveRoomPage = PageFactory.initElements(browser, ReserveRoomPage.class);
    }

    @Test
    public void testReserveRoom() {
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
        reserveTermPage.ensureIsDisplayedIzaberi();
        reserveTermPage.getIzaberi().click();
        threadSleep(1200);
        reserveTermPage.ensureIsDisplayedPotvrdaRez();
        reserveTermPage.getBtnFinalReserve().click();
        threadSleep(1200);
        reserveTermPage.ensureAlertDisplayed();
        String alert_text = reserveTermPage.getDriver().switchTo().alert().getText();
        threadSleep(1000);
        if(alert_text.equals("Захтев успешно послат. О детаљима прегледа бићете обавештени путем адресе е-поште.")){
            reserveTermPage.getDriver().switchTo().alert().accept();
        }
        reserveTermPage.ensureIsDisplayedPrikazi();
        reserveTermPage.getPrikazi().click();
        threadSleep(1200);
        reserveTermPage.ensureIsDisplayedIzaberi();
        reserveTermPage.getIzaberi().click();
        threadSleep(1200);
        reserveTermPage.ensureIsDisplayedPotvrdaRez();
        reserveTermPage.getBtnFinalReserve().click();
        threadSleep(1200);
        reserveTermPage.ensureAlertDisplayed();
        alert_text = reserveTermPage.getDriver().switchTo().alert().getText();
        threadSleep(1000);
        if(alert_text.equals("Захтев успешно послат. О детаљима прегледа бићете обавештени путем адресе е-поште.")){
            reserveTermPage.getDriver().switchTo().alert().accept();
        }


        browser.navigate().to("http://localhost:3000/#/login");
        threadSleep(1000);
        reserveRoomPage.ensureIsDisplayedInput();
        reserveRoomPage.getInputMail().sendKeys("cadmin@gmail.com");
        threadSleep(350);
        reserveRoomPage.getInputPassword().sendKeys("admin");
        threadSleep(350);
        reserveRoomPage.getBtnPrijaviSe().click();

        reserveRoomPage.ensureAlertDisplayed();
        threadSleep(1000);
        reserveRoomPage.getDriver().switchTo().alert().accept();
        reserveRoomPage.ensureIsDisplayedMenu();
        threadSleep(350);
        reserveRoomPage.getRoomMenu().click();
        reserveRoomPage.ensureIsDisplayedButton();
        threadSleep(350);
        reserveRoomPage.getBtnRequests().click();
        reserveRoomPage.ensureIsDisplayedRequests();
        threadSleep(350);
        reserveRoomPage.getGiveRoom().click();
        reserveRoomPage.ensureIsDisplayedRooms();
        threadSleep(350);
        reserveRoomPage.getReserve().click();
        reserveRoomPage.ensureAlertDisplayed();
        threadSleep(1000);

        String text_alert = reserveRoomPage.getDriver().switchTo().alert().getText();
        if(text_alert.equals("Сала успешно резервисана.")){
            reserveRoomPage.getDriver().switchTo().alert().accept();
            System.out.println("Sala rezervisana");
        }
        else if (text_alert.equals("Доктор је заузет у изабраном термину.")){
            reserveRoomPage.getDriver().switchTo().alert().accept();
            System.out.println("Doktor je zauzet");
        } else  if (text_alert.equals("Сала је заузета у изабраном термину.")) {
            reserveRoomPage.getDriver().switchTo().alert().accept();
            System.out.println("Sala je zauzeta");
        }
        reserveRoomPage.ensureAlertDisplayed();
        threadSleep(1000);
        reserveRoomPage.getDriver().switchTo().alert().accept();
        reserveRoomPage.ensureIsDisplayedRequests();
        threadSleep(350);
        reserveRoomPage.getGiveRoom().click();
        reserveRoomPage.ensureIsDisplayedRooms();
        threadSleep(350);
        reserveRoomPage.getReserve().click();
        threadSleep(1000);
        text_alert = reserveRoomPage.getDriver().switchTo().alert().getText();
        if (text_alert.equals("Сала је заузета у изабраном термину.")) {
            reserveRoomPage.getDriver().switchTo().alert().accept();
            System.out.println("Sala je zauzeta");
        }
        System.out.println("pre reserve ensured");
        reserveRoomPage.ensureIsDisplayedRooms2();
        threadSleep(500);
        System.out.println("pre reserve klika");
        reserveRoomPage.getReserve2().click();
        threadSleep(1000);
        text_alert = reserveRoomPage.getDriver().switchTo().alert().getText();
        if (text_alert.equals("Сала успешно резервисана.")) {
            reserveRoomPage.getDriver().switchTo().alert().accept();
            System.out.println("Sala rezervisana");
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
