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
* Copy **opencsv-2.3.jar** to wherever you'd like your libraries to exist on your system.
* Open the Space Trivia project in Eclipse and select **Project** then **Properties** from the main menu.
* Select **Java Build Path** from the list on the left of the properties window.
* Select the **Libraries** tab and click the **Add External JARs...** button.
* Locate **opencsv-2.3.jar**, select the file and click **Open**
* Click the **OK** button to exit the properties window.

## License

Released under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Differences to Published App

Space Trivia is published on Google Play. This open source version differs in that the sound files have been replaced by empty sound files.

The files are located at:

* /res/raw/button_click.ogg
* /res/raw/button_click_failure.ogg
* /res/raw/button_click_success.ogg

The original sound files are under a license that does not permit redistribution.
