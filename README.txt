README

Course: CS400
Semester: Spring 2020
Project name: Milk Weight
Team Members:
    1. Zander Keith,   LEC001, zkeith@wisc.edu
    2. Daniel Levy,    LEC001, dslevy2@wisc.edu
    3. Matthew Holmes, LEC001, mrholmes@wisc.edu
    4. Solly Parenti,  LEC001, sparenti@wisc.edu
    5. Hyejin Yeon,    LEC001, hyeon2@wisc.edu

Q. Which team members were on same xteam together?

Members 1, 2, 3, & 5 were on the same xteam175.
Member 4 was in xteam169.

Other notes or comments to the grader:
    The jar file was made for Windows 10.
    On mac os, there is a chance that the home image does not display, although we believe we fixed this issue for some mac computers.  

Known Bugs:
    1. executable.jar does not run on mac OS. It is unknown if the jar file works on Linux.
       On Windows 10, working command is 
       java --module-path "Your local path to \javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar
    2. Reports sort weights and percentages lexographically instead of numerically.

Planned:
    1. Implementing "All" funcitonality on Annual and Monthly Report 
    2. Enable user to cancel overwriting the existing data. Currently the data for the same date gets overwritten.
    3. Enable user to delete specific data. Currently the user can only remove a farm completely. The user could overwrite the weight to be zero, which will practically remove the data, but it would be nice if this funcitonality is more obvious to the user. 