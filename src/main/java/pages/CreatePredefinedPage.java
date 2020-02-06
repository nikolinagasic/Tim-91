package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreatePredefinedPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"termini\"]")
    private WebElement liTermini;

    public CreatePredefinedPage() {
    }

    public CreatePredefinedPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getLiTermini() {
        return liTermini;
    }

    public void setLiTermini(WebElement liTermini) {
        this.liTermini = liTermini;
    }
}
