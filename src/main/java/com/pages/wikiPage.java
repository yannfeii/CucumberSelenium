package com.pages;

import org.apache.commons.lang.StringUtils;
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

    public HashSet<String> getpersonalInfos() {

//        By personalLife = getXpathBy("//*[@id=\"toc\"]/ul/li");
//        List<WebElement> personalEle = driver.findElements(personalLife);
//
//        for(WebElement ele:personalEle) {
//            String spanTxt = ele.findElement(tagA).getAttribute("href");
//            if(StringUtils.containsIgnoreCase(spanTxt,"Personal_life")){
//                openHerf(spanTxt);
//            }
//        }

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

//    public String getBirthDateTxt(){
//        By birthDateTxt = getXpathBy("//*[@id=\"mw-content-text\"]/div[1]/p[2]");
//        return driver.findElement(birthDateTxt).getText();
//    }

    public void clickSearch(){
        driver.findElement(searchButton).click();
    }

    public void clickButton(){
        WebDriverWait wait = new WebDriverWait(driver,(Duration.ofSeconds(100)));
        wait.until(ExpectedConditions.presenceOfElementLocated(displayButton));
        driver.findElement(displayButton).click();
    }

    public void timeOut(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(1000));
    }

    public void openUrl(String url){ driver.navigate().to(url); }

    public void openHerf(String herf){
        driver.findElement(By.partialLinkText(herf)).click();
    }

    public String getLanguageDisplay(){
        By laugauageSetting = getXpathBy("//*[@id=\"languagesettings-settings-panel\"]/div/div[2]/p[1]/span[2]");
        return driver.findElement(laugauageSetting).getText(); }

    public Map<String,String> getLanguageList(){
        /**
         * get language list on wiki page and exclude the language which do not have display button on page
         */
        String[] nonStandardUrl = {
                "bn.wikipedia.org",
                "ar.wikipedia.org",
                "tr.wikipedia.org",
                "pt.wikipedia.org",
                "ko.wikipedia.org",
                "eu.wikipedia.org",
                "sr.wikipedia.org",
                "he.wikipedia.org",
                "fr.wikipedia.org",
                "fa.wikipedia.org",
                "en.wikipedia.org",
                "simple.wikipedia.org",
                "meta.wikimedia.org"};

        By languageClass = getXpathBy("//*[@id=\"p-lang\"]/div/ul/li");
        List<WebElement> languageEle = driver.findElements(languageClass);
        Map<String,String> titleList = new HashMap();

        for(WebElement ele:languageEle) {
            String language = ele.findElement(tagSpan).getText();
            String url = ele.findElement(tagA).getAttribute("href");
            if(StringUtils.indexOfAny(url,nonStandardUrl)<0) {
                titleList.put(language, url);
            }
        }
        return titleList;

    }


}
