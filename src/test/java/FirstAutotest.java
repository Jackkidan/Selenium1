import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;



public class FirstAutotest {


    WebDriver driver;

    @Before
    public void startDriver() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, SECONDS);
        driver.get("https://www.rgs.ru");

    }

    @After
    public void closeDriver() {
        // driver.quit();
    }

    @Test
    public void testRgsInsurance() {
        //Step 2
        WebElement navBarButton = findByXpath("//*[@id='main-navbar-collapse']//a[contains(text(), 'Страхование')]");
        navBarButton.click();
        //Step 3
        WebElement insuranceNavBarButton = findByXpath("//*[@id='main-navbar-collapse']//a[contains(text(), 'Выезжающим за рубеж')]");
        insuranceNavBarButton.click();

        //Step 4
        //
        WebElement openCalculateOnlineWindow = findByXpath("/html//div[@id='main-content']/div[@class='container container-rgs-content']/" +
                "div[@class='row-rgs-content-two-cols']//div[@class='content-document']/div[4]/div[1]/div/" +
                "div[@class='thumbnail-footer']/a[@href='https://www.rgs.ru/products/private_person/tour/strahovanie_turistov/calc/index.wbp']");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", openCalculateOnlineWindow);
        openCalculateOnlineWindow.click();


        //Step 5
        //driver.manage().timeouts().pageLoadTimeout(5, SECONDS);
        WebElement calculateHeader = findByXpath("//div[@class='container container-rgs-app-title']");
        String expectedText = "Калькулятор страхования путешественников онлайн";
        Assert.assertEquals("Калькулятор страхования путешественников онлайн", expectedText, calculateHeader.getText());

        //Step 6
        //xpath не смог написать руками, пришлось юзать плагинчик.
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // Чекбокс я согласен

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class = 'adaptive-checkbox-label' and contains(text(), ' Я согласен на обработку моих персональных данных в целях расчета страховой премии. ')]")));
        WebElement checkBoxElement = driver.findElement(By.xpath("//label[@class = 'adaptive-checkbox-label' and contains(text(), ' Я согласен на обработку моих персональных данных в целях расчета страховой премии. ')]"));
        checkBoxElement.click();
        // Несколько в течение года
        WebElement severalTripsButton = findByXpath("//div[@id='calc-vzr-steps']/myrgs-steps-partner-auth//" +
                "div[@class='steps']/div[1]/div[@class='step-body']//form/div[1]/btn-radio-group/div/button[2]");
        severalTripsButton.click();

        //Step 7
        driver.manage().timeouts().pageLoadTimeout(5, SECONDS);

        WebElement inputCountries = findByXpath("//input[@class = 'form-control-multiple-autocomplete-actual-input tt-input']");
        inputCountries.sendKeys("Шенген");
        inputCountries.sendKeys(Keys.DOWN);
        inputCountries.sendKeys(Keys.ENTER);


        // step 8
        WebElement webElementForSelect = driver.findElement(By.id("ArrivalCountryList"));
        Select chooseCountry = new Select(webElementForSelect);
        // Как установить ожидание открытия списка?
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chooseCountry.selectByVisibleText("Испания");
        // Как нормально работать с этим календарем?
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.TAB).build().perform();
        WebElement calendarElement = findByXpath("//body/div[5]/div[@class='datepicker-days']/table[@class='table-condensed']/tbody/tr[6]/td[2]");
        calendarElement.click();

        // не более 90
        WebElement daysOfTravel = findByXpath("//label[@class = 'btn btn-attention']");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", daysOfTravel);
        daysOfTravel.click();
        WebElement inputNameLastname = findByXpath("//div[@id='calc-vzr-steps']/myrgs-steps-partner-auth//div[@class='steps']/div[1]/div[@class='step-body']/div[@class='row-step-body']//form/div[2]//div[@class='panel panel-default']/div[2]/div[1]/div//div[@class='form-group']/input[@class='form-control']");
        inputNameLastname.sendKeys("IVAN IVANOV");
        inputNameLastname.sendKeys(Keys.TAB);
        builder.sendKeys("12121985").build().perform();




    }



    private WebElement findByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

}
