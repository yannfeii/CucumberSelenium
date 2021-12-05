package stepdefinitions;

import com.qa.factory.DriverFactory;
import io.cucumber.java.en.*;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import com.pages.wikiPage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;


public class WikiSearch {

    private String title;
    private wikiPage wikiPage = new wikiPage(DriverFactory.getDriver());
    Map<String,String> languageSelected = new HashMap<>();
    Logger logger = Logger.getLogger(WikiSearch.class.getName());

    @Given("user is on wiki main page")
    public void user_is_on_wiki_main_page() {
        DriverFactory.getDriver().get("https://en.wikipedia.org/wiki/Main_Page");
        wikiPage.timeOut();
        title = wikiPage.getWikiPageTitle();
        logger.info("Current pagae title is "+title);
    }

    @When("user is navigated to new language page")
    public void user_is_navigated_to_new_language_page() {

        /**
         * Random select 10 languages and navigate to the specifically URL
         */

        Map<String,String> languageList = wikiPage.getLanguageList();
        String[] keys = languageList.keySet().toArray(new String[0]);
        Random random = new Random();
        int count = 0;

        for(int i = 0; i < 10; i++){
            String language = keys[random.nextInt(keys.length)];
            String url = languageList.get(language);
            logger.info(++count + "----- "+language+ " ----------- language is selected");
            wikiPage.openUrl(url);
            title = wikiPage.getWikiPageTitle();
            logger.info("user is navigated to new language page");
            wikiPage.timeOut();
            wikiPage.clickButton();
            String languageDisplaied = wikiPage.getLanguageDisplay();
            if(!languageDisplaied.isEmpty()) {
                languageSelected.put(language, languageDisplaied);
            }
        }

    }

    @And("validates language is rendered")
    public void validate_languge_is_rendered() {
        /**
         * validate seleted language is matching with display language on page
         */

        for(String expLanguage:languageSelected.keySet()){
            String languageSel = languageSelected.get(expLanguage).replaceAll("\\(", "").replaceAll("\\)", "");
            String expLanguageS = expLanguage.split("/")[0];
            logger.info(expLanguage+"------"+languageSel);
            Assert.assertTrue(StringUtils.containsIgnoreCase(expLanguageS, languageSel)
                    || StringUtils.containsIgnoreCase(languageSel, expLanguageS));
        }

        logger.info("validates language is rendered");
    }

    @Then ("user is navigated to then main page")
    public void user_is_navigated_to_main_page() throws InterruptedException {
        user_is_on_wiki_main_page();
        logger.info("user is navigated to then main page");
    }


    @When("^user enter (.*) in search box$")
    public void user_enter_a_text_in_search_box(String text) {
        wikiPage.inputSearch(text);
        logger.info("Inside Step - user enter a text in search box");
    }

    @And("hits enter")
    public void hits_enter() {
        wikiPage.clickSearch();
        logger.info("Inside Step - hits enter");
    }

    @Then("^check (.*) info$")
    public void user_is_navigated_to_search_results(String celebrity) {
        HashSet<String> personalInfos = wikiPage.getpersonalInfos();
        //String birthDateTxt = wikiPage.getBirthDateTxt();
        String birthDate = "";
        String spouse = "";
        for (String info:personalInfos) {
            if (StringUtils.indexOf(info, "born ") > 0 && birthDate.isEmpty()) {
                birthDate = StringUtils.substringBetween(info, "born ", ")");
                logger.info("---------- " + celebrity + " birth date is " + birthDate);
            }else if (StringUtils.indexOf(info, "wife") > 0 || StringUtils.indexOf(info, "husband") > 0){
                spouse = StringUtils.substringBetween(info, "wife ", ",");
                spouse = StringUtils.substringBetween(info, "husband ", ",");
            }
        }
        logger.info("---------- "+ spouse+" is " + celebrity + "'s spouse");

    }



}
