package stepdefinitions;

import com.pages.WikiPagePF;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.*;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;


public class WikiSearch {

    private String title;
    //private wikiPage wikiPage = new wikiPage(DriverFactory.getDriver());
    private WikiPagePF wikiPage = new WikiPagePF(DriverFactory.getDriver());
    Map<String,String> languageSelected = new HashMap<>();
    Map<String,String> urlSelected = new HashMap<>();
    Logger logger = Logger.getLogger(WikiSearch.class.getName());
    Map<String,String> nonStandardUrl = new HashMap<>();

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

        nonStandardUrl.put("বাংলা","bn.wikipedia.org");
        nonStandardUrl.put("العربية","ar.wikipedia.org");
        nonStandardUrl.put("Türkçe","tr.wikipedia.org");
        nonStandardUrl.put("Português","pt.wikipedia.org");
        nonStandardUrl.put("한국어","ko.wikipedia.org");
        nonStandardUrl.put("Euskara","eu.wikipedia.org");
        nonStandardUrl.put("Srpski","sr.wikipedia.org");
        nonStandardUrl.put("Српски / srpski","sr.wikipedia.org");
        nonStandardUrl.put("עברית","he.wikipedia.org");
        nonStandardUrl.put("Français","fr.wikipedia.org");
        nonStandardUrl.put("فارسی","fa.wikipedia.org");
        //nonStandardUrl.put("বাংলা","en.wikipedia.org");
        nonStandardUrl.put("Simple English","simple.wikipedia.org");
        nonStandardUrl.put("Meta-Wiki","meta.wikimedia.org");


        Map<String,String> languageList = wikiPage.getLanguageList();
        String[] keys = languageList.keySet().toArray(new String[0]);
        Random random = new Random();

        for(int i = 0; i < 10; i++) {
            String language = keys[random.nextInt(keys.length)];
            String url = languageList.get(language);
            logger.info(i + "----- " + language + " ----------- language is selected");
            wikiPage.openUrl(url);
            String currURL = wikiPage.getWikiPageUrl();

            logger.info("user is navigated to new language page");
            wikiPage.timeOut();
            if(nonStandardUrl.keySet().contains(language)){
                urlSelected.put(language,currURL);
            }else {
                wikiPage.clickButton();
                wikiPage.timeOut();
                String languageDisplaied = wikiPage.getLanguageDisplay();
                if (!languageDisplaied.isEmpty()) {
                    languageSelected.put(language, languageDisplaied);
                }
            }
        }

    }

    @And("validates language is rendered")
    public void validate_languge_is_rendered() {
        /**
         * validate seleted language is matching with display language on page
         */

        // the language which do not have display button on page


        for(String expLanguage:languageSelected.keySet()){
            String languageSel = languageSelected.get(expLanguage).replaceAll("\\(", "").replaceAll("\\)", "");
            String expLanguageS = expLanguage.split("/")[0];

            Assert.assertTrue(StringUtils.containsIgnoreCase(expLanguageS, languageSel)
                    || StringUtils.containsIgnoreCase(languageSel, expLanguageS));
            logger.info(expLanguage+"------"+languageSel);
        }
        if(!urlSelected.isEmpty()){
            for(String key:urlSelected.keySet()){
                if (nonStandardUrl.keySet().contains(key)) {
                    Assert.assertTrue(urlSelected.get(key).contains(nonStandardUrl.get(key)));
                    logger.info(key+ "---------" + urlSelected.get(key));
                }

            }
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

        String birthDate = wikiPage.getBirthDate();
        List<String> spouseName = wikiPage.getSpouse();
        logger.info("---------- " + celebrity + "'s birthdate is " + birthDate);

        if(spouseName.isEmpty()){
            logger.info("---------- " + celebrity + " is still single");
        }else {
            String spouse = spouseName.get(spouseName.size()-1).replace("\n","");
            logger.info("---------- " + spouse + " is " + celebrity + "'s spouse");
        }

    }



}
