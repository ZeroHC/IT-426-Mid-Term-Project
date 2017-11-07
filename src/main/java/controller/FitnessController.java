/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* FitnessController.java
* This file takes care of the main actions of the application.
*/

package controller;

import javafx.scene.Scene;
import model.Hike;
import model.ReminderMessages;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;  
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles the main actions of the application.
 *
 * @author Daniel Capps, Joshua Hawks, Hanchen Liu
 * @version 1.0
 */
public class FitnessController {

    private static final String PREVIOUSLY_HIKED_ELEMENT_STRING = "previouslyHiked";
    private static final String DATE_ELEMENT_STRING = "date";
    private static final String LOCATION_ELEMENT_STRING = "location";
    private static final String ALL_HIKE_DETAILS = "allHikeDetails";
    private static final String REMINDER_MESSAGE_ELEMENT_STRING = "reminderMessage";
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
    public void addHeartRate(String heartRateValue, String dateToSearchFor){

        //Method call to setup the necessary document and file.
        documentFileSetup();

        Element allHikes = rootNode.getChild("allHikeDetails");

        List dates = allHikes.getChildren("date");

        Element dateNode;

        for (int i = 0; i < dates.size(); i++){
            dateNode = (Element) dates.get(i);

            String attributeHolder = dateNode.getAttributeValue("id");
            if (attributeHolder.contentEquals(dateToSearchFor)){
                dateNode.addContent(new Element("heartRate").setText(heartRateValue));
            }
        }

        writeToXMLFile(xmlFileDocument, xmlFile);

    }

    public void addSteps(String stepsTaken, String dateToSearchFor){
        documentFileSetup();

        Element allHikes = rootNode.getChild("allHikeDetails");

        List dates = allHikes.getChildren("date");

        Element dateNode;

        for (int i = 0; i < dates.size(); i++){
            dateNode = (Element) dates.get(i);

            String attributeHolder = dateNode.getAttributeValue("id");
            if (attributeHolder.contentEquals(dateToSearchFor)){
                dateNode.addContent(new Element("steps").setText(stepsTaken));
            }
        }

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    public void addNewReminderMessage(ReminderMessages reminderMessages){
        documentFileSetup();

        Element allHikes = rootNode.getChild(ALL_HIKE_DETAILS);
        Element date = allHikes.getChild(DATE_ELEMENT_STRING);

        for (int i = 0; i < reminderMessages.getMessages().size(); i++)
        {
            Element message = new Element(REMINDER_MESSAGE_ELEMENT_STRING).setText(reminderMessages.getMessages().get(i));
            date.addContent(message);
        }

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    //Template method for setting up the document.
    private void documentFileSetup(){

        try {
            builder = new SAXBuilder();

            xmlFile = new File("data/master.xml");

            xmlFileDocument = (Document) builder.build(xmlFile);

            rootNode = xmlFileDocument.getRootElement();

        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }catch (JDOMException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void writeToXMLFile(Document xmlDocument, File xmlFile){
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(xmlDocument, new FileWriter(xmlFile.getAbsolutePath()));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * this method adds one hike information to the file
     *
     * @param hike user created hike
     */
    public void addHike(Hike hike)
    {
        //add location to one specific spot in the file
        addHikeLocation(hike);

        //add the date and location to the file
        addHikeDetails(hike);
    }

    //this method adds the location to the previously hiked element
    private void addHikeLocation(Hike hike)
    {
        //setup for the necessary documents
        documentFileSetup();

        //if there is no location element inside of previously hiked element or if the hike location doesn't exits in file
        //create a new location element
        if (rootNode.getChild(PREVIOUSLY_HIKED_ELEMENT_STRING).getContentSize() == 0 || !duplicateChecker(hike.getLocation()))
        {
            Element allHikeLocations = rootNode.getChild(PREVIOUSLY_HIKED_ELEMENT_STRING);

            allHikeLocations.addContent(new Element(LOCATION_ELEMENT_STRING).setText(hike.getLocation()));
        }

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    private boolean duplicateChecker(String checker)
    {
        String[] locations = getHikeLocations();
        for (String location : locations)
        {
            if (location.equals(checker))
            {
                return true;
            }
        }
        return false;
    }

    //this method adds the date and the location of the hike to the xml file
    private void addHikeDetails(Hike hike)
    {
        //setup for necessary document
        documentFileSetup();

        //create a new element called date and with the hike date as the id attribute
        Element dateElement = new Element(DATE_ELEMENT_STRING).setAttribute("id", hike.getDate());

        //create a new element under the date element called location
        dateElement.addContent(new Element(LOCATION_ELEMENT_STRING).setText(hike.getLocation()));

        //get the element called allHikeDetails from the root
        Element allHikes = rootNode.getChild(ALL_HIKE_DETAILS);

        //add the date element to the allHikeDetails element
        allHikes.addContent(dateElement);


        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    /**
     *
     * @return
     */
    public String[] getHikeLocations()
    {
        documentFileSetup();

        Element hikeLocations = rootNode.getChild(PREVIOUSLY_HIKED_ELEMENT_STRING);
        return getLocationStrings(hikeLocations);
    }

    public String[] getScheduledHikes()
    {
        documentFileSetup();

        Element allHikeDetails = rootNode.getChild(ALL_HIKE_DETAILS);

        List dateList = allHikeDetails.getChildren(DATE_ELEMENT_STRING);

        String[] locations = new String[dateList.size()];

        int firstIndex = 0;

        for (int i = 0; i < dateList.size(); i++)
        {
            Element node = (Element) dateList.get(i);
            if (getLocationStrings(node).length == 0)
            {
                return locations;
            }
            else
            {
                locations[i] = getLocationStrings(node)[firstIndex];
            }
        }

        return locations;
    }

    private String[] getLocationStrings(Element locationElement)
    {
        String[] locationString;

        List locationList = locationElement.getChildren(LOCATION_ELEMENT_STRING);

        locationString = new String[locationList.size()];

        for (int i = 0; i < locationList.size(); i++)
        {
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
        documentFileSetup();

        Element allHikes = rootNode.getChild(ALL_HIKE_DETAILS);

        List dateList = allHikes.getChildren(DATE_ELEMENT_STRING);

        String[] dateString = new String[dateList.size()];

        for (int i = 0; i < dateList.size(); i++)
        {
            Element date = (Element)dateList.get(i);
            dateString[i] = date.getAttributeValue("id");
        }

        return dateString;
    }
}
