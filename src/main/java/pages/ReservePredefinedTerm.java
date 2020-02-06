package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class ReservePredefinedTerm {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"a_click_unapredDef\"]")
    private WebElement linkPredefined;

    @FindBy(xpath = "//*[@id=\"a_h1_unapredDef\"]")
    private WebElement h1PredefinedTerm;

    @FindBy(xpath = "//*[@id=\"a_table_predefinedTerm\"]")
    private WebElement tablePredefined;

    @FindBy(xpath = "//*[@id=\"a_btn_predefined_term\"]")
    private WebElement btnReserve;

    // alert mi treba isto


    public ReservePredefinedTerm() {
    }

    public ReservePredefinedTerm(WebDriver driver) {
        this.driver = driver;
    }

    // prikazana je pocetna stranica kada vidim link za unapred def
    public void ensureIsDisplayedClinicProfile(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(linkPredefined));
    }

    // prikazana je lista predefinisanih termina tek kad vidim naslov Unapred definisani
    public void ensureIsDisplayedPredefinedPage(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(h1PredefinedTerm));
    }

    public WebElement getLinkPredefined() {
        return linkPredefined;
    }

    public WebElement getH1PredefinedTerm() {
        return h1PredefinedTerm;
    }

    public WebElement getTablePredefined() {
        return tablePredefined;
    }

    public WebElement getBtnReserve() {
        return btnReserve;
    }
}
