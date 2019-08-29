# COMP1202-Enigma-Machine
This is a virtual adaptation of the infamous Enigma Machine. You can use it either with the command line app or directly with your own java file extending to the EnigmaMachine class.

It uses up to three Rotors or Turnover Rotors with five different configurations. It supports two types of Reflectors. It can read and write from files to encrypt/decrypt from files. It will ignore special characters and only works with A-Z characters.

# How to Setup
To use the Enigma Machine in your terminal, simply follow these steps:
1. Install the latest JVM on your computer.
2. Download this repo or git pull it onto your own directory with ```git pull https://github.com/OscarVanL/COMP1202-Enigma-Machine/```
3. Navigate to /src/ in terminal and run ```javac CommandLineInterface.java```
4. Now run the Enigma Machine with ```java CommandLineInterface```
4. Done!

# Test Results
* TestHarness:
    1. Part 5 Test 1: BADGER
    2. Part 5 Test 2: SNAKE
    3. Part 7 Test 3: THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOG
* Bombe:
    1. Part 8 Challenge 1:
        * Output: DAISYDAISYGIVEMEYOURANSWERDO
        * First Missing Plug: U
        * Second Missing Plug: A
    2. Part 8 Challenge 2:
        * Output: WELLALWAYSBETOGETHERHOWEVERFARITSEEMSWELLALWAYSBETOGETHERTOGETHERINELECTRICDREAMS
        * Rotor 1 Initial Position: 6
        * Rotor 2 Initial Position: 9
        * Rotor 3 Initial Position: 15
    3. Part 8 Challenge 3:
        * Output: ILOVECOFFEEILOVETEAILOVETHEJAVAJIVEANDITLOVESME
        * Rotor 1 Type: V
        * Rotor 2 Type: III
        * Rotor 3 Type: II
    
Mark: 88%
