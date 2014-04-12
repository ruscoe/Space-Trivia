# Space-Trivia

An open-source space trivia app for Android.

Written by Dan Ruscoe (http://ruscoe.org/)

## Requirements

* [Android SDK](http://developer.android.com/sdk/index.html)
* Android 2.2 platform
* [OpenCSV 2.3](http://sourceforge.net/projects/opencsv/files/opencsv/2.3/)

## Usage

* Import the Space Trivia project into Eclipse.
* Download and install the OpenCSV library ([Installing the OpenCSV Library in Eclipse](#installing-the-opencsv-library-in-eclipse).)
* Build the project as an Android application.

Trivia categories and questions can be found in CSV files within the /res/raw directory.

## Installing the OpenCSV Library in Eclipse

* Download [OpenCSV 2.3](http://sourceforge.net/projects/opencsv/files/opencsv/2.3/)
* Extract the archive and locate the file named **opencsv-2.3.jar**, in the 'deploy' directory.
* Open the Space Trivia project in Eclipse.
* Create a new folder named **libs** in the root of the Space Trivia project.
* Copy **opencsv-2.3.jar** into the newly created **libs** folder.
* From the main menu, select **Project** then **Clean...** and clean the project.

## Adding Categories and Questions

Categories and questions are contained in .csv files. To edit the files:

* In the Space Trivia project, navigate to the **res/raw** directory.
* To add a new question to an existing category, open the .csv file named after that category and add a new line. See [Question CSV Data Format](#question-csv-data-format).
* To add a new category, open **categories_data.csv** and add a new line. See [Category CSV Data Format](#category-csv-data-format).
* To add questions for a new category:
  * Create a new file in the **res/raw** directory. The naming convention is: `questions_my_category.csv`
  * Populate the new file with CSV-formatted question data. See [Question CSV Data Format](#question-csv-data-format).
  * Open **src/org/ruscoe/spacetrivia/dao/TriviaDAO.java**.
  * Locate the **onCreate** method and look for the following code:
  
  ```java
  importQuestionsData(db, R.raw.questions_space_exploration);
  importQuestionsData(db, R.raw.questions_earth_moon);
  importQuestionsData(db, R.raw.questions_solar_system);
  ```
  
  * Add a line below that section of code, similar to this:
  
  ```java
  importQuestionsData(db, R.raw.questions_my_category);
  ```
  
  Note that the resource ID (R.raw.*) must match the name of the question data file you created, minus the .csv extension.
  
  * On your device, clear the application data (or uninstall) and rebuild the application from Eclipse.
  * Your new category / question data should now appear in the application.

### Category CSV Data Format
```csv
1, "Space Exploration"
```

**Indexes:**

1. The unique ID of the category.
2. The name of the category.

### Question CSV Data Format
```csv
1,1,"The first manned Moon landing occurred in July of which year?","The first manned Moon landing occurred on July 20th, 1969 during the Apollo 11 mission.",1969,1959,1979
```

**Indexes**

1. The ID of category the question belongs to. Must match an ID in the categories CSV file.
2. The unique ID of the question.
3. The question text.
4. The long answer text, shown after the user selects an answer.
5. The correct answer option.
6. Incorrect answer option #1.
7. Incorrect answer option #2.

## License

Released under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Differences to Published App

Space Trivia is [published on Google Play](https://play.google.com/store/apps/details?id=org.ruscoe.spacetrivia). This open source version differs in that the sound files have been replaced by empty sound files.

The files are located at:

* /res/raw/button_click.ogg
* /res/raw/button_click_failure.ogg
* /res/raw/button_click_success.ogg

The original sound files are under a license that does not permit redistribution.
