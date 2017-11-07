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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles the main actions of the application.
 *
 * @author Daniel Capps, Joshua Hawks, Hanchen Liu
 * @version 1.0
 */
public class FitnessController {

    //constants for storing element names
    private static final String PREVIOUSLY_HIKED_ELEMENT_STRING = "previouslyHiked";
    private static final String DATE_ELEMENT_STRING = "date";
    private static final String LOCATION_ELEMENT_STRING = "location";
    private static final String ALL_HIKE_DETAILS = "allHikeDetails";
    private static final String REMINDER_MESSAGE_ELEMENT_STRING = "reminderMessage";

    //constant for storing the first index
    private static final int FIRST_INDEX = 0;

    //Used to hold the scene information passed from the view.
    private Scene scene;

    //The initial fields for handling the xml file.
    private SAXBuilder builder;
    private File xmlFile;
    private Document xmlFileDocument;
    private Element rootNode;

    //Hike object to hold the current hike info
    private Hike hike = new Hike();

    private ReminderMessages reminderMessages = new ReminderMessages();

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

    public void addNewReminderMessage(){
        documentFileSetup();

        Element allHikes = rootNode.getChild(ALL_HIKE_DETAILS);
        List<Element> dates = allHikes.getChildren(DATE_ELEMENT_STRING);
        Element last = dates.get(dates.size() - 1);

        for (int i = 0; i < reminderMessages.getMessages().size(); i++)
        {
            Element message = new Element(REMINDER_MESSAGE_ELEMENT_STRING).setText(reminderMessages.getMessages().get(i));
            last.addContent(message);
        }

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    public String[] getReminderMessage(String reminderMessages, String dateToSearchFor)
    {
        //Field for string array to be returned.
        String[] reminderMessage;

        //Dig down to the reminder messages.
        Element hikes = rootNode.getChild(ALL_HIKE_DETAILS);
        Element date = hikes.getChild(DATE_ELEMENT_STRING);


        //A list of the children in the reminder node.
        List reminderMessageList = date.getChildren(REMINDER_MESSAGE_ELEMENT_STRING);

        //Provides the array with the exact size it needs to be to hold the list.
        reminderMessage = new String[reminderMessageList.size()];

        //Inserts the value of each child into the array.
        for (int i = 0; i < reminderMessageList.size(); i++){
            Element node = (Element) reminderMessageList.get(i);

            reminderMessage[i] = node.getText();
        }

        return reminderMessage;

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
     */
    public void addHike()
    {
        //add location to one specific spot in the file
        addHikeLocation();

        //add the date and location to the file
        addHikeDetails();
    }

    //this method adds the location to the previously hiked element
    private void addHikeLocation()
    {
        //setup for the necessary documents
        documentFileSetup();

        //if there is no location element inside of previously hiked element
        //or if the hike location doesn't exits in file
        //create a new location element
        if (rootNode.getChild(PREVIOUSLY_HIKED_ELEMENT_STRING).getContentSize() == 0 || !duplicateChecker(hike.getLocation()))
        {
            Element allHikeLocations = rootNode.getChild(PREVIOUSLY_HIKED_ELEMENT_STRING);

            allHikeLocations.addContent(new Element(LOCATION_ELEMENT_STRING).setText(hike.getLocation()));
        }

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    //this method checks if there is a duplicate location entry in the previously hiked element
    private boolean duplicateChecker(String checker)
    {
        //use a string array to store all the location strings in previously hiked element
        String[] locations = getHikeLocations();

        for (String location : locations)
        {
            //if the location strings contains the checker return true
            if (location.equals(checker))
            {
                return true;
            }
        }

        return false;
    }

    //this method adds the date and the location of the hike to the xml file
    private void addHikeDetails()
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
     * this method gets all the locations from the previously hiked element
     *
     * @return a string array of locations
     */
    public String[] getHikeLocations()
    {
        documentFileSetup();

        //gets an element called previously hiked from the root
        Element hikeLocations = rootNode.getChild(PREVIOUSLY_HIKED_ELEMENT_STRING);
        return getLocationStrings(hikeLocations);
    }

    /**
     * this method gets all the locations from the all hike details element
     *
     * @return a string array of locations
     */
    public String[] getScheduledHikes()
    {
        documentFileSetup();

        //gets an element called all hike details from the root
        Element allHikeDetails = rootNode.getChild(ALL_HIKE_DETAILS);

        //use a node list to store all the children called date from the all hike details element
        List dateList = allHikeDetails.getChildren(DATE_ELEMENT_STRING);

        //create a string array for storing locations
        String[] locations = new String[dateList.size()];

        //use a for loop to go through the date list
        for (int i = 0; i < dateList.size(); i++)
        {
            //create a temporary element to store date element at index i
            Element node = (Element) dateList.get(i);

            //calls the getLocationStrings method to check if it returns an empty array
            //if it returns an empty array, return the empty locations array
            if (getLocationStrings(node).length == 0)
            {
                return locations;
            }

            //else gets one location back from getLocationString method based on the date element passed in
            else
            {
                locations[i] = getLocationStrings(node)[FIRST_INDEX];
            }
        }

        //returns the location array
        return locations;
    }

    //this method returns a location array based on the parent element passed in
    private String[] getLocationStrings(Element locationElement)
    {
        //uses a string array to store location strings
        String[] locationStrings;

        //use a node list to store all the children called location based on the parent element
        List locationList = locationElement.getChildren(LOCATION_ELEMENT_STRING);

        //initialize the location string array
        locationStrings = new String[locationList.size()];

        //use a for loop to go through the location list
        for (int i = 0; i < locationList.size(); i++)
        {
            //create a temporary element to store location element at index i
            Element node = (Element) locationList.get(i);

            //stores the element's text to the location string array
            locationStrings[i] = node.getText();
        }

        //returns the location array
        return locationStrings;
    }

    /**
     * this method gets all the dates from the all hike details element
     *
     * @return a string array of dates
     */
    public String[] getHikeDates()
    {
        documentFileSetup();

        //get an element called all hike details from the root
        Element allHikes = rootNode.getChild(ALL_HIKE_DETAILS);


        //use a node list to store all the children called date from the all hike details element
        List dateList = allHikes.getChildren(DATE_ELEMENT_STRING);

        //initialize the date string array
        String[] dateStrings = new String[dateList.size()];

        //use a for loop to go through the date list
        for (int i = 0; i < dateList.size(); i++)
        {
            //create a temporary element to store date element at index i
            Element date = (Element)dateList.get(i);

            //stores the element's attribute value to the date string array
            dateStrings[i] = date.getAttributeValue("id");
        }

        //returns the date array
        return dateStrings;
    }

    public void setHikeLocation(String location)
    {
        hike.setLocation(location);
    }

    public void setHikeDate(String date)
    {
        hike.setDate(date);
    }

    public void setHikeReminderMessages(int capacity)
    {
        reminderMessages.setMessages(new ArrayList<>(capacity));
    }

    public void addHikeReminderMessage(String message)
    {
        reminderMessages.addMessage(message);
    }

    public void removeHikeReminderMessage(String message)
    {
        reminderMessages.removeMessage(message);
    }
}
