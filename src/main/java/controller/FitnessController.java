/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* FitnessController.java
* This file takes care of the main actions of the application.
*/

package controller;

import javafx.scene.Scene;
import model.Hike;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;  
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class handles the main actions of the application.
 *
 * @author Daniel Capps, Joshua Hawks, Hanchen Liu
 * @version 1.0
 */
public class FitnessController {

    //Used to hold the scene information passed from the view.
    private Scene scene;

    //The initial fields for handling the xml file.
    private SAXBuilder builder;
    private File xmlFile;
    private Document xmlFileDocument;
    private Element rootNode;

    /**
     * This is a constructor method that allows us to handle the interaction with the view.
     *
     * @param scene This parameter is used to handle the changes to the scenes for the view and
     *              initially has the default scene from the view.
     */
    //Constructor which initializes a scene for the start of the application.
    public FitnessController(Scene scene){
        this.scene = scene;
    }

    /**
     * This method is used to update the scene.
     *
     * @return The method returns a scene object.
     */
    public Scene updateView(){
        return scene;
    }

    /**
     * This method is used to set the scene.
     *
     * @param scene Provides the value of the scene object that the field needs to be set to.
     */
    public void setView(Scene scene){
        this.scene = scene;
    }

    /**
     * This method gets the steps data for calculating the steps data.
     *
     * @return This method returns a string array with numeric strings.
     */
    public String[] getStepData(){

        //Field for string to be returned.
        String[] stepValues;

        //Method call to setup the necessary document and file.
        documentFileSetup();

        //Gets the child node
        Element date = rootNode.getChild("date");

        //A list from the child node date.
        List stepTakenList = date.getChildren("numberOfSteps");

        //Provides the array with the exact size it needs to be to hold the list.
        stepValues = new String[stepTakenList.size()];

        //Inserts the value of each child into the array.
        for(int i = 0; i < stepTakenList.size(); i++){
            Element node = (Element) stepTakenList.get(i);

            stepValues[i] = node.getText();
        }

        return stepValues;
    }

    /**
     * This method gets the heart rate data for calculating the steps data.
     *
     * @return This method returns a string array with numeric strings.
     */
    public String[] getHeartRateData(){

        //Field for string array to be returned.
        String[] heartRateValues;

        //Method call to setup the necessary document and file.
        documentFileSetup();

        //Gets the child node date.
        Element date = rootNode.getChild("date");

        //List of children from date node.
        List heartRateList = date.getChildren("heartRate");

        //Provides the array with the exact size it needs to be to hold the list.
        heartRateValues = new String[heartRateList.size()];

        //Inserts the value of each child into the array.
        for(int i = 0; i < heartRateList.size(); i++){
            Element node = (Element) heartRateList.get(i);

            heartRateValues[i] = node.getText();
        }

        return heartRateValues;
    }

    /**
     * This method loads an array of strings that contain the reminder messages.
     *
     * @return This method returns a string array with reminder messages.
     */
    public String[] loadReminderMessages(){

        //Field for string array to be returned.
        String[] reminderString;

        //Method call to setup the necessary document and file.
        documentFileSetup();

        //Gets the child node reminders.
        Element reminder = rootNode.getChild("reminders");

        //A list of the children in the reminder node.
        List reminderMessageList = reminder.getChildren("message");

        //Provides the array with the exact size it needs to be to hold the list.
        reminderString = new String[reminderMessageList.size()];

        //Inserts the value of each child into the array.
        for (int i = 0; i < reminderMessageList.size(); i++){
            Element node = (Element) reminderMessageList.get(i);

            reminderString[i] = node.getText();
        }

        return reminderString;
    }

    /**
     * This method adds the value of heart rate to the xml file.
     *
     * @param heartRateValue A string with the numeric value of the user's heart rate.
     */
    //Needs to be updated.
    public void addHeartRate(String heartRateValue){

        //Method call to setup the necessary document and file.
        documentFileSetup();

        Element year = rootNode.getChild("year");

        Element month = year.getChild("month");

        month.addContent(new Element("heartRate").setText(heartRateValue));

        writeToXMLFile(xmlFileDocument, xmlFile);

    }


    /**
     * This method adds the value of steps to the xml file.
     *
     * @param stepsTaken A string with the numeric value of the number of steps the user has taken.
     */
    //Needs to be updated.
    public void addSteps(String stepsTaken){
        documentFileSetup();

        Element year = rootNode.getChild("year");

        Element month = year.getChild("month");

        month.addContent(new Element("numberOfSteps").setText(stepsTaken));

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    //Template method for setting up the document.
    private void documentFileSetup(){

        try {

            //Creates a new document using a SAX parser.
            builder = new SAXBuilder();

            //The xml file that is to be parsed.
            xmlFile = new File("data/master.xml");

            //Builds the JDOM document based on the Sax parser.
            xmlFileDocument = (Document) builder.build(xmlFile);

            //Gets the root node of the xml document.
            rootNode = xmlFileDocument.getRootElement();

        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }catch (JDOMException exception){
            System.out.println(exception.getMessage());
        }
    }

    //Template method for writing to the xml file.
    private void writeToXMLFile(Document xmlDocument, File xmlFile){
        try {
            //Create an object to write new content to the xml file.
            XMLOutputter xmlOutput = new XMLOutputter();

            //Provides indentation to the document to make it easier to read when viewing the file directly.
            xmlOutput.setFormat(Format.getPrettyFormat());

            //Writes the new content to the xml file.
            xmlOutput.output(xmlDocument, new FileWriter(xmlFile.getAbsolutePath()));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     *
     * @param hike
     */
    public void addHike(Hike hike)
    {
        documentFileSetup();

        Element allHikes = rootNode.getChild("previouslyHiked");

        allHikes.addContent(new Element("location").setText(hike.getLocation()));

        allHikes.addContent(new Element("date").setText(hike.getDate()));

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    /**
     *
     * @return
     */
    public String[] getHikeLocations()
    {
        String[] locationString;

        documentFileSetup();

        Element allHikes = rootNode.getChild("previouslyHiked");

        List locationList = allHikes.getChildren("location");

        locationString = new String[locationList.size()];

        for (int i = 0; i < locationList.size(); i++){
            Element node = (Element) locationList.get(i);

            locationString[i] = node.getText();
        }

        return locationString;
    }

    /**
     *
     * @return
     */
    public String[] getHikeDates()
    {
        String[] dateString;

        documentFileSetup();

        Element allHikes = rootNode.getChild("previouslyHiked");

        List dateList = allHikes.getChildren("date");

        dateString = new String[dateList.size()];

        for (int i = 0; i < dateList.size(); i++){
            Element node = (Element) dateList.get(i);

            dateString[i] = node.getText();
        }

        return dateString;
    }
}
