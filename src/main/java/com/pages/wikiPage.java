package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class wikiPage {
    private WebDriver driver;

    public By searchInput = By.id("searchInput");
    public By searchButton = By.id("searchButton");
    public By tagSpan = By.tagName("span");
    public By tagA = By.tagName("a");
    public By displayButton = By.className("uls-settings-trigger");
    public By chineseButton = By.id("p4js-conversion-donotshow");


    public wikiPage(WebDriver driver){
        this.driver = driver;
    }

    public By getXpathBy(String xpath){
        return By.xpath(xpath);
    }

    public String getWikiPageTitle(){
        return driver.getTitle();
    }

    public void inputSearch(String input){
        driver.findElement(searchInput).sendKeys(input);
    }

    public String getWikePageUrl(){ return driver.getCurrentUrl();}

    public HashSet<String> getpersonalInfos() {

        By personalLife = getXpathBy("//*[@id=\"mw-content-text\"]/div[1]/p");
        driver.findElement(personalLife);
        List<WebElement> personalEle = driver.findElements(personalLife);

        HashSet<String> personalInfos = new HashSet<>();

        for(WebElement ele:personalEle) {
            String personalInfo = ele.getText();
            personalInfos.add(personalInfo);
        }
        return personalInfos;
    }

    public void clickSearch(){
        driver.findElement(searchButton).click();
    }

    public void clickButton(){

        try {
           // WebDriverWait wait = new WebDriverWait(driver,(Duration.ofSeconds(100)));
           // wait.until(ExpectedConditions.presenceOfElementLocated(displayButton));
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    public void openUrl(String url){ driver.navigate().to(url); }

    public String getLanguageDisplay(){

        try {
//            String laugauageSetting = driver.findElement(By.className("uls-display-settings-language-tab")).findElement(By.tagName("P")).findElements(By.tagName("Span")).get(1).getText();
//            return laugauageSetting;
            By laugauageSetting = getXpathBy("//*[@id=\"languagesettings-settings-panel\"]/div/div[2]/p[1]/span[2]");
            return driver.findElement(laugauageSetting).getText();
        }catch (Exception a){
            return "laugauageSetting doesn't exist";
        }
    }

    public Map<String,String> getLanguageList(){
        /**
         * get language list on wiki page
         *
         **/

        By languageClass = getXpathBy("//*[@id=\"p-lang\"]/div/ul/li");
        List<WebElement> languageEle = driver.findElements(languageClass);
        Map<String,String> titleList = new HashMap();

        for(WebElement ele:languageEle) {
            String language = ele.findElement(tagSpan).getText();
            String url = ele.findElement(tagA).getAttribute("href");
            //if(StringUtils.indexOfAny(url,nonStandardUrl)<0) {
           // if(StringUtils.indexOf(url,"zh.wikipedia.org")>=0) {
                titleList.put(language, url);
           // }
        }
        return titleList;

    }


}
