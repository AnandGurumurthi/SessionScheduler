Session Scheduler
===

This is deployed on Heroku and available at : http://sheltered-thicket-9624.herokuapp.com/

If you want to set up the code locally follow the instructions below. 

Setting up the code and running it
----

The first step is to clone this git repo or download and extract the zip.

This can be executed in the following ways from your local box:

- Running it on eclipse
- Running it on a browser

Running it on eclipse
----

- Import the downloaded zip file or git folder in eclipse.
- Make sure the project is identified by eclipse as a maven project. If not enable it. 
- If you have build automatically setup in eclipse, this should trigger immediately and start downloading the required jars.
- If build automatically is not setup in eclipse, right click on the project and Run as -> mvn install
- Now all the code is compiled and test cases are run. 
- Open src/main/java/com/scheduler/CommandLineScheduler.java
- Right click and select Run as -> Run configurations
- Go to the arguments tab and in program arguments enter the list of sessions (Note: use double quotes so it takes everything we specify as a single argument).
- Click run
- The scheduled track(s) will be printed in the console.

Running it on a browser
---

- Import the downloaded zip file or git folder in eclipse.
- Make sure the project is identified by eclipse as a maven project. If not enable it. 
- If you have build automatically setup in eclipse, this should trigger immediately and start downloading the required jars.
- If build automatically is not setup in eclipse, right click on the project and Run as -> mvn install
- Now all the code is compiled and test cases are run. 
- Open src/main/java/com/scheduler/Main.java
- Right click and select Run as -> Java application (This is a functionality provided by Heroku called Webapp Runner)
- This should start your server in port 8080 
- Open your browser and go to http://localhost:8080/
- You should get the Session Scheduler.
- Enter the sessions in the text area and hit Schedule
- The scheduled track(s) will be printed in the browser in a nice table format.



