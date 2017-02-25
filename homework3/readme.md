In order to test application, you should write a class that implements IPluggable.
The easiest way to do it is to just build a .jar file of the application.

To load it you should do the following
    - in case of running application in IDE, you should put .jar file into the root folder where src/ is located
    - if you run it as .bat file, created by installDist task, you should put .jar file in the same folder as .bat file

As soon as you do it, the application will analyze and load the class.
