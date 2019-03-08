Feature: The user can search file contents with a keyword
  As a user
  I want to search through files with a keyword
  So that I can see a list of files with matching percentage

  Scenario: the exact search keyword is in every file. searching for 'I'

    Given there are files containing 'I'
    When the application runs and user types 'I'
    Then files name list should be displayed with a matching percentage

  Scenario: the files do not have the search keyword (even partially)
    Given there are files containing 'no matching keyword'
    When the application runs and user types 'airblower'
    Then no matches found should be printed

