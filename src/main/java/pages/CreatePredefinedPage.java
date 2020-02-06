package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings({"unused", "UnnecessaryContinue"})
public class CreatePredefinedPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[1]")
    private WebElement inputMail;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[3]")
    private WebElement btnPrijaviSe;

    @FindBy(xpath = "//*[@id=\"termini\"]")
    private WebElement liTermini;

    @FindBy(xpath = "//*[@id=\"a_naslov_predefinedExam\"]")
    private WebElement naslovUnapred;

    @FindBy(xpath = "//*[@id=\"a_date_predefinedExam\"]")
    private WebElement datumPregleda;

    @FindBy(xpath = "//*[@id=\"a_selectDoctors_predefinedExam\"]")
    private WebElement selectDoctor;

    @FindBy(xpath = "//*[@id=\"a_selectSatnica_predefinedExam\"]")
    private WebElement selectSatnica;

    @FindBy(xpath = "//*[@id=\"a_cena_predefinedExam\"]")
    private WebElement inputCena;

    @FindBy(xpath = "//*[@id=\"a_popust_predefinedExam\"]")
    private WebElement inputPopust;

    @FindBy(xpath = "/html/body/div/div/div/div/div[3]/div[1]/form/table/tr[8]/td/input")
    private WebElement btnNapraviPregled;

    public CreatePredefinedPage() {
    }

    public CreatePredefinedPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedPage(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(liTermini));
    }

    public void ensureIsDisplayedInput(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(inputMail));
    }

    public void ensureIsDisplayedNaslov(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(naslovUnapred));
    }

    public void ensureAlertDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.alertIsPresent());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getLiTermini() {
        return liTermini;
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

    public WebElement getNaslovUnapred() {
        return naslovUnapred;
    }

    public WebElement getDatumPregleda() {
        return datumPregleda;
    }

    public Select getSelectDoctor() {
        return new Select(selectDoctor);
    }

    public WebElement getSelectSatnica() {
        return selectSatnica;
    }

    public WebElement getInputCena() {
        return inputCena;
    }

    public WebElement getInputPopust() {
        return inputPopust;
    }

    public WebElement getBtnNapraviPregled() {
        return btnNapraviPregled;
    }
}
