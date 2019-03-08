Feature: Program initiation and termination feature

    Scenario: the program terminates when directory path argument is not given
        When the program runs with empty argument
        Then the program terminates with a message 'directory path is not supplied'

    Scenario: the program terminates when directory path argument is not valid
        When the program runs with an invalid argument
        Then the program terminates with a message 'directory path is invalid'

    Scenario: Program terminates when there is no files in the search directory
        Given there are no files in search directory
        Then the program terminates with a message 'no files exist in the directory, therefore program terminates'

    Scenario: normal execution and expecting certain messages
        When the program runs with a correct path argument
        Then the program prints how many files are there and showing 'search> ' prompt in the next line and the app gets terminated

    Scenario: the program terminates when use type ':quit'
        Given there are files in search directory
        When the program runs and user types ':quit'
        Then the program terminates

