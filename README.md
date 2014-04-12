# Space-Trivia

An open-source space trivia app for Android.

Written by Dan Ruscoe (http://ruscoe.org/)

## Requirements

* [Android SDK](http://developer.android.com/sdk/index.html)
* Android 2.2 platform
* [OpenCSV 2.3](http://sourceforge.net/projects/opencsv/files/opencsv/2.3/)

## Usage

* Import the Space Trivia project into Eclipse.
* Download and install the OpenCSV library (see instructions below.)
* Build the project as an Android application.

Trivia categories and questions can be found in CSV files within the /res/raw directory.

## Installing the OpenCSV Library in Eclipse

* Download [OpenCSV 2.3](http://sourceforge.net/projects/opencsv/files/opencsv/2.3/)
* Extract the archive and locate the file named **opencsv-2.3.jar**, in the 'deploy' directory.
* Open the Space Trivia project in Eclipse.
* Create a new folder named **libs** in the root of the Space Trivia project.
* Copy **opencsv-2.3.jar** into the newly created **libs** folder.
* From the main menu, select **Project** then **Clean...** and clean the project.

## License

Released under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Differences to Published App

Space Trivia is published on Google Play. This open source version differs in that the sound files have been replaced by empty sound files.

The files are located at:

* /res/raw/button_click.ogg
* /res/raw/button_click_failure.ogg
* /res/raw/button_click_success.ogg

The original sound files are under a license that does not permit redistribution.
