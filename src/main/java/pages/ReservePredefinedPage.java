package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class ReservePredefinedPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[1]")
    private WebElement inputMail;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[3]")
    private WebElement btnPrijaviSe;

    @FindBy(xpath = "//*[@id=\"klinike\"]")
    private WebElement liListaKlinika;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/div/form[2]/table")
    private WebElement tabelaKlinika;

    @FindBy(xpath = "//*[@id=\"a_tr_clinic_search_prikazi\"]")
    private WebElement btnPrikazi;

    @FindBy(xpath = "//*[@id=\"a_click_unapredDef\"]")
    private WebElement linkPredefined;

    @FindBy(xpath = "//*[@id=\"a_h1_unapredDef\"]")
    private WebElement naslovPredefinedTerm;

    @FindBy(xpath = "//*[@id=\"a_table_predefinedTerm\"]")
    private WebElement tablePredefined;

    @FindBy(xpath = "//*[@id=\"a_btn_predefined_term\"]")
    private WebElement btnReserve;

    public ReservePredefinedPage() {
    }

    public ReservePredefinedPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void ensureIsDisplayedInput(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(inputMail));
    }

    public void ensureIsDisplayedPagePatient(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(liListaKlinika));
    }

    public void ensureIsDisplayedTableClinics(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(tabelaKlinika));
    }

    public void ensureIsDisplayedClinicProfile(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(linkPredefined));
    }

    public void ensureIsDisplayedPredefinedPage(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(naslovPredefinedTerm));
    }

    public void ensureAlertDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.alertIsPresent());
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

    public WebElement getLiListaKlinika() {
        return liListaKlinika;
    }

    public WebElement getBtnPrikazi() {
        return btnPrikazi;
    }

    public WebElement getLinkPredefined() {
        return linkPredefined;
    }

    public WebElement getNaslovPredefinedTerm() {
        return naslovPredefinedTerm;
    }

    public WebElement getTablePredefined() {
        return tablePredefined;
    }

    public WebElement getBtnReserve() {
        return btnReserve;
    }

    public WebElement getTabelaKlinika() {
        return tabelaKlinika;
    }
}
