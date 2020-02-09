package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings({"unused", "UnnecessaryContinue"})
public class SearchClinicPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[1]")
    private WebElement inputMail;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[3]")
    private WebElement btnPrijaviSe;

    @FindBy(xpath="//*[@id=\"klinike\"]")
    private WebElement liKlinike;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/form[2]/table")
    private WebElement tabelaKlinika;

    @FindBy(xpath = "//*[@id=\"headerSearchClinicDate\"]")
    private WebElement datepicker;

    @FindBy(xpath = "//*[@id=\"headerSearchClinicTip\"]")
    private WebElement selectTip;

    @FindBy(xpath = "//*[@id=\"searchClinic\"]")
    private WebElement btnPretrazi;

    @FindBy(xpath = "//*[@id=\"a_tr_clinic_search_prikazi\"]")
    private WebElement btnPrikazi;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div/p[1]")
    private WebElement linkPredefined;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div/form[2]/table")
    private WebElement tableDoctors;

    @FindBy(xpath = "//*[@id=\"filter_clinic_cenaDo\"]")
    private WebElement cenaDo;

    @FindBy(xpath = "//*[@id=\"filter_clinic_ocenaOd\"]")
    private WebElement ocenaOd;

    @FindBy(xpath = "//*[@id=\"filter_clinic_ocenaDo\"]")
    private WebElement ocenaDo;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/form[2]/table/tbody/tr[1]/td[2]")
    private WebElement columnTable;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/div/form[1]")
    private WebElement filterDoctor;

    public SearchClinicPage() {

    }

    public SearchClinicPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedPagePatients(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(liKlinike));
    }

    public void ensureIsDisplayedInput(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(inputMail));
    }

    public void ensureIsDisplayedTableClinics(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(tabelaKlinika));
    }

    public void ensureIsDisplayedClinicProfile(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(linkPredefined));
    }

    public void ensureIsDisplayedTableDoctors(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(tableDoctors));
    }

    public void ensureIsDisplayedFilterDoctors(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(filterDoctor));
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

    public WebElement getTabelaKlinika() {
        return tabelaKlinika;
    }

    public WebElement getDatepicker() {
        return datepicker;
    }

    public Select getSelectTip(){
        return new Select(selectTip);
    }

    public WebElement getBtnPretrazi() {
        return btnPretrazi;
    }

    public WebElement getBtnPrikazi() {
        return btnPrikazi;
    }

    public WebElement getLinkPredefined() {
        return linkPredefined;
    }

    public WebElement getTableDoctors() {
        return tableDoctors;
    }

    public WebElement getCenaDo() {
        return cenaDo;
    }

    public WebElement getOcenaOd() {
        return ocenaOd;
    }

    public WebElement getOcenaDo() {
        return ocenaDo;
    }

    public WebElement getColumnTable() {
        return columnTable;
    }
}
