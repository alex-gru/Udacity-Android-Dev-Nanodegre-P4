**[← back to project list](https://github.com/alex-gru/android_dev_nanodegree_udacity/blob/master/README.md)**

##P4 - Build It Bigger

This project is actually the final project of Udacity's [Gradle for Android and Java](https://github.com/udacity/ud867/tree/master/FinalProject) course, included in the Android Developer nanodegree.
<a href="http://gradle.org/getting-started-android/" target="_blank">Gradle</a> has become the official build system for Android projects and is fully integrated in the Android Studio IDE. The goal of this course is to get familiar with this powerful build system and to "build it bigger". 
From the user's perspective, an app called "Jokes" provides a very simple UI, with a button, where the user can request a joke. 

But the main focus is on the work behind the scenes. Starting with a single Android project, that provides a simple Activity, the project is extended to a multi-project build system, as shown below (credits go to Udacity). 

<img style="position: center;" src="https://lh3.googleusercontent.com/cJQtO_A08shKWZ1NEJxpvdYcfXxoHH87HYldIx_gOoGcoqnnZDTP3ycVqAnZSUMYzHygxhb-nEE_Yv_QmZY=s0#w=1920&h=1080" width="750">

### Java Library, that serves Jokes
The jokes, provided by the app, should now come from a Java library, which is configured as a dependency in the Android project.

### Android Library, that shows Jokes
Now, the actual joke is not displayed in the same Activity, but instead, this logic is extracted to a external Android library. The library has a simple Activity, which can be started with an Intent parameter, containing the joke string. The app has now dependencies on the Java and Android library. 

### GCE module, that serves Jokes (from the Java library)
Now, a <a href="https://cloud.google.com/appengine/docs/java/endpoints/" target="_blank">GCE</a> (Google Cloud Endpoints) module is set up, which serves jokes to the app. The idea is instead of pulling jokes directly from the Java class, instead, the GCE module (which is in fact a Java application server) provides the middleware between Android app and Java library. So the app sends a Joke request to the GCE server (via an Async task to prevent blocking the UI thread), the server fetches a joke from the Java library, and sends the joke back to the Android app, which now uses the Android library to display the joke. The GCE backend is first run locally, but is then deployed to the <a href="https://cloud.google.com/appengine/docs" target="_blank">Google App Engine</a>, which now provides a "real" online experience. 

### Android "Connected" test

In Android projects, there are usually two types of tests. JUnit tests to test Java functionality, and Android connected tests. As soon as the test should investigate Android-specific behaviour, a connected test must be used. In this case, the correct processing of the Async task, which fetches the jokes from the GCE server, should be tested. 

### "Free" and "Paid" flavors
Finally, an important concept in Android projects is introduced, to provide an app in different "flavors". A flavor is actually an arbitrary special version of the app, but usually there are two flavors. A free version of the app provides a reduced experience to the user, either by hiding functionality, or by showing ads. If the user wants to upgrade, also a paid (or also premium) version is offered, which now contains all features of the app. With gradle, this concept can be realized in a coherent way. 
In "Build It Bigger", the free version shows ads (and has a dependency to the Google Ad library), whereas the paid version removes this ads, and does not depend on any unnecessary libraries. 

<img style="position: center;" src="https://github.com/alex-gru/Udacity-Android-Dev-Nanodegre-P4/blob/master/static/screenshots/P4%20-%20Build%20It%20Bigger%20-Project%20Structure.JPG" width="250">

<img style="position: center;" src="https://github.com/alex-gru/Udacity-Android-Dev-Nanodegre-P4/blob/master/static/screenshots/P4%20-%20Build%20It%20Bigger%20-Flavors_2.png" width="250">
<img style="position: center;" src="https://github.com/alex-gru/Udacity-Android-Dev-Nanodegre-P4/blob/master/static/screenshots/P4%20-%20Build%20It%20Bigger%20-Flavors_3.png" width="250">
<img style="position: center;" src="https://github.com/alex-gru/Udacity-Android-Dev-Nanodegre-P4/blob/master/static/screenshots/P4%20-%20Build%20It%20Bigger%20-Joke.png" width="250">
