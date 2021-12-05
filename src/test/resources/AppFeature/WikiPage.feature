Feature: feature to test wiki search functionality

  Scenario: Validation wiki switching language is working
    Given user is on wiki main page
    When user is navigated to new language page
    And validates language is rendered
    Then user is navigated to then main page

  Scenario Outline: Validation wiki search is working
    Given user is on wiki main page
    When user enter <Hollywood celebrity> in search box
    And hits enter
    Then check <Hollywood celebrity> info

    Examples:
    |Hollywood celebrity|
    |Kim Kardashian     |
    |Matt Damon         |
    |Rihanna            |
    |Angelina Jolie     |
    |Jennifer Aniston   |
    |Jennifer Lawrence  |
    |Brad Pitt          |
    |Leonardo DiCaprio  |
    |Matthew McConaughey|
    |Will Smith         |

