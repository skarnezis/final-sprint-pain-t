# final-sprint-pain-t
This is the final sprint/submission for my Microsoft Pain(t) Application for CS-250

Overview of code layout

The code contained in this zip file is organized within the following classes
1) Paint Controller
2) Paint Application
3) CoolCanvas
4) DrawCanvas
5) CoolTab
6) Display
7) MenuBar

The hierarchy of the code is began with the Application class, which is directly linked to the Controller class. This is what starts up the application and opens a new paint
window for the user. The Controller class includes all the methods that are directly tied to the FXML file for the user to directly interact with the paint window.

From here, the hierarchy moves down to the Display and MenuBar classes. Since there is one general "display" showing everything, as well as one MenuBar, these classes are not extensions
of anything. Their purpose is to help distribute the code from the Controller class so that debugging and organization is much clearer for future use.

Most of the default classes, such as the Canvas class and the Tab class, do not have all the features that are necessary to properly implement all the features of paint. So,
the hierarchy works its way down to CoolCanvas and DrawCanvas, which are both extensions of Canvas, and CoolTab, an extension of Tab. These classes are not meant to rewrite the features
of the Canvas and Tab classes. They are simply used to extend those two classes and include all the features necessary for this application.
