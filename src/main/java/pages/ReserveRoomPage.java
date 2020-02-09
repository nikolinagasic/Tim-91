package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReserveRoomPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[1]")
    private WebElement inputMail;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div/div/div/div/form/input[3]")
    private WebElement btnPrijaviSe;

    @FindBy(xpath = "//*[@id=\"rooms\"]")
    private WebElement roomMenu;

    @FindBy(xpath = "//*[@id=\"btnPrikaziZahtev\"]")
    private WebElement btnRequests;

    @FindBy(xpath = "/html/body/div/div/div/div/div[3]/table[2]/tbody/tr/td[4]/button")
    private WebElement giveRoom;

    @FindBy(xpath = "/html/body/div/div/div/div/div[3]/div/div/div[1]/div/div/table[2]/tbody/tr[1]/td[4]/button")
    private WebElement reserve;

    @FindBy(xpath = "/html/body/div/div/div/div/div[3]/div/div/div[1]/div/div/table[2]/tbody/tr[2]/td[4]/button")
    private WebElement reserve2;

    public ReserveRoomPage() {
    }

    public ReserveRoomPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void ensureIsDisplayedInput(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(inputMail));
    }

    public void ensureIsDisplayedMenu(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(roomMenu));
    }

    public void ensureIsDisplayedButton(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(btnRequests));
    }

    public void ensureIsDisplayedRequests(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(giveRoom));
    }

    public void ensureIsDisplayedRooms(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(reserve));
    }
    public void ensureIsDisplayedRooms2(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(reserve2));
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

    public WebElement getRoomMenu() {
        return roomMenu;
    }

    public WebElement getBtnRequests() {
        return btnRequests;
    }

    public WebElement getGiveRoom() {
        return giveRoom;
    }

    public WebElement getReserve() {
        return reserve;
    }

    public WebElement getReserve2() {
        return reserve2;
    }
}
