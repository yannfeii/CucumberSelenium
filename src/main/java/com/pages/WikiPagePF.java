package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.qa.util.Constants.*;

public class WikiPagePF {

    Logger logger = Logger.getLogger(WikiPagePF.class.getName());

    private WebDriver driver;

    @FindBy( id= "searchInput" ) WebElement searchInput;
    @FindBy( id = "searchButton" ) WebElement searchButton;
    //@FindBy( xpath = "//*[@id=\"p-lang\"]/button") WebElement displayButton;
    //@FindBy( id = "p4js-conversion-donotshow" ) WebElement chineseButton;
    @FindBy( xpath = "//*[@id=\"p-lang\"]/div/ul/li" ) List<WebElement> languageClass;
    @FindBy( className = "bday" ) WebElement birthDate;
    @FindBys({
            @FindBy( css = "div[style*='white-space:nowrap']"),
            @FindBy( css = "div[style*='white-space:normal']")
    }) List<WebElement> spouseNode;

    /*
    * FindBy unable to locate dynamic page elements
     */

    public By displayButton = By.className("uls-settings-trigger");
    public By chineseButton = By.id("p4js-conversion-donotshow");
    public By laugauageSetting = By.xpath("//*[@id=\"languagesettings-settings-panel\"]/div/div[2]/p[1]/span[2]");

    public WikiPagePF(WebDriver driver){

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public String getWikiPageTitle(){

        return driver.getTitle();
    }

    public void inputSearch(String input){

        searchInput.sendKeys(input);
    }

    public String getWikiPageUrl(){ return driver.getCurrentUrl();}


    public String getBirthDate(){

        return birthDate.getAttribute(INNERHTML);

    }

    public List<String> getSpouse() {
        List<String> spouseList = new ArrayList<>();

        if(!spouseNode.isEmpty()){
            for(WebElement ele:spouseNode){
                spouseList.add(ele.getText());
            }
        }

        return spouseList;
    }


    public void clickSearch(){
        searchButton.click();
    }

    public void clickButton(){

        try{
            driver.findElement(displayButton).click();
        } catch (Exception e) {
            System.out.println(displayButton+" doesn't exist");
            try{
                if(driver.findElement(chineseButton).isDisplayed()){
                    driver.findElement(chineseButton).click();
                    driver.findElement(displayButton).click();
                }
            } catch (Exception a) {
                System.out.println(chineseButton+" doesn't exist");
            }
        }
    }


    public void timeOut(){

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void openUrl(String url){
        driver.get(url);
    }

    public String getLanguageDisplay(){

        try {

            String language = driver.findElement(laugauageSetting).getText();

            return language;

        }catch (Exception a){

            return "laugauageSetting doesn't exist";
        }
    }

    public Map<String,String> getLanguageList(){
        /**
         * get language list on wiki page
         *
         **/

        Map<String,String> titleList = new HashMap();

        for(WebElement ele:languageClass) {
            String language = ele.getText();
            String url = ele.findElement(By.tagName(TAG_A)).getAttribute(HREF);
            titleList.put(language, url);
        }

        return titleList;

    }
}
