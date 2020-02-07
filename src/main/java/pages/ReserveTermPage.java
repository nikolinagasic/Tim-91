package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class ReserveTermPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[1]")
    private WebElement inputMail;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[3]")
    private WebElement btnPrijaviSe;

    @FindBy(xpath = "//*[@id=\"klinike\"]")
    private WebElement liKlinike;

    @FindBy(xpath = "//*[@id=\"headerSearchClinicDate\"]")
    private WebElement datepicker;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/form[2]/table/tbody/tr[1]/td[2]")
    private WebElement tdInTableClinics;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div/form[2]/table")
    private WebElement tableDoctors;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div/form[2]/table/tbody/tr[1]/td[4]/button")
    private WebElement btnRezervisi;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div[2]/div/div[1]/div[2]/table/tbody/tr[2]/td[2]/button")
    private WebElement btnOdaberi;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div[2]/div/div[1]/div[1]/h3")
    private WebElement naslovPotvrdaRez;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div[2]/div/div[1]/div[3]/button[2]")
    private WebElement btnFinalReserve;

    public ReserveTermPage() {
    }

    public ReserveTermPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedInput(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(inputMail));
    }

    public void ensureIsDisplayedPatientPage(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(liKlinike));
    }

    public void ensureIsDisplayedDatepicker() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(datepicker));
    }

    public void ensureIsDisplayedTableDoctors(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(tableDoctors));
    }

    public void ensureIsDisplayedOdaberi(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(btnOdaberi));
    }

    public void ensureIsDisplayedPotvrdaRez(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(naslovPotvrdaRez));
    }

    public void ensureAlertDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.alertIsPresent());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getInputMail() {
        return inputMail;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public WebElement getBtnPrijaviSe() {
        return btnPrijaviSe;
    }

    public WebElement getLiKlinike() {
        return liKlinike;
    }

    public WebElement getDatepicker() {
        return datepicker;
    }

    public WebElement getTdInTableClinics() {
        return tdInTableClinics;
    }

    public WebElement getTableDoctors() {
        return tableDoctors;
    }

    public WebElement getBtnRezervisi() {
        return btnRezervisi;
    }

    public WebElement getBtnOdaberi() {
        return btnOdaberi;
    }

    public WebElement getNaslovPotvrdaRez() {
        return naslovPotvrdaRez;
    }

    public WebElement getBtnFinalReserve() {
        return btnFinalReserve;
    }
}
