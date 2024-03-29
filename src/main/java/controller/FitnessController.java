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
public class FitnessController
{
    //constants for storing element names
    private static final String PREVIOUSLY_HIKED_ELEMENT_STRING = "previouslyHiked";
    private static final String DATE_ELEMENT_STRING = "date";
    private static final String LOCATION_ELEMENT_STRING = "location";
    private static final String ALL_HIKE_DETAILS = "allHikeDetails";
    private static final String REMINDER_MESSAGE_ELEMENT_STRING = "reminderMessage";

    //constant for storing attribute name
    private static final String ID = "id";

    //constant for storing the first index
    private static final int FIRST_INDEX = 0;
    private static final int MONTH = 12;

    //Used to hold the scene information passed from the view.
    private Scene scene;

    //The initial fields for handling the xml file.
    private SAXBuilder builder;
    private File xmlFile;
    private Document xmlFileDocument;
    private Element rootNode;

    //Hike object to hold the current hike info
    private Hike hike = new Hike();

    //reminder messages object to hold the selected reminder messages
    private ReminderMessages reminderMessages = new ReminderMessages();

    /**
     * This is a constructor method that allows us to handle the interaction with the view.
     *
     * @param scene This parameter is used to handle the changes to the scenes for the view and
     *              initially has the default scene from the view.
     */
    //Constructor which initializes a scene for the start of the application.
    public FitnessController(Scene scene)
    {
        this.scene = scene;
    }

    /**
     * This method is used to update the scene.
     *
     * @return The method returns a scene object.
     */
    public Scene updateView()
    {
        return scene;
    }

    /**
     * This method is used to set the scene.
     *
     * @param scene Provides the value of the scene object that the field needs to be set to.
     */
    public void setView(Scene scene)
    {
        this.scene = scene;
    }

    /**
     * This method gets the steps data for calculating the steps data.
     *
     * @return This method returns a string array with numeric strings.
     */
    public int[] getStepData()
    {
        //Holds the string value of the steps taken.
        String[] totalSteps = new String[MONTH];

        //Denominator for the calculation of averages.
        int[] numberToDivideStepValuesBy = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //Field for string to be returned.
        int[] stepValues = new int[MONTH];

        //Method call to setup the necessary document and file.
        documentFileSetup();

        //Gets the child node
        Element allHikes = rootNode.getChild("allHikeDetails");

        List stepTakenList = allHikes.getChildren("date");

        //A list from the child node date.
        //List stepTakenList = dates.getChildren("month");

        //Inserts the value of each child into the array.
        for (int i = 0; i < stepTakenList.size(); i++)
        {
            Element node = (Element) stepTakenList.get(i);

            if (node.getChild("month").getName() == "month")
            {
                switch (node.getChild("month").getAttributeValue(ID))
                {
                    case "01":
                        totalSteps[0] = node.getChild("month").getChild("steps").getText();
                        stepValues[0] = stepValues[0] + Integer.parseInt(totalSteps[0]);
                        numberToDivideStepValuesBy[0]++;
                        break;

                    case "02":
                        totalSteps[1] = node.getChild("month").getChild("steps").getText();
                        stepValues[1] = stepValues[1] + Integer.parseInt(totalSteps[1]);
                        numberToDivideStepValuesBy[1]++;
                        break;

                    case "03":
                        totalSteps[2] = node.getChild("month").getChild("steps").getText();
                        stepValues[2] = stepValues[2] + Integer.parseInt(totalSteps[2]);
                        numberToDivideStepValuesBy[2]++;
                        break;

                    case "04":
                        totalSteps[3] = node.getChild("month").getChild("steps").getText();
                        stepValues[3] = stepValues[3] + Integer.parseInt(totalSteps[3]);
                        numberToDivideStepValuesBy[3]++;
                        break;

                    case "05":
                        totalSteps[4] = node.getChild("month").getChild("steps").getText();
                        stepValues[4] = stepValues[4] + Integer.parseInt(totalSteps[4]);
                        numberToDivideStepValuesBy[4]++;
                        break;

                    case "06":
                        totalSteps[5] = node.getChild("month").getChild("steps").getText();
                        stepValues[5] = stepValues[5] + Integer.parseInt(totalSteps[5]);
                        numberToDivideStepValuesBy[5]++;
                        break;

                    case "07":
                        totalSteps[6] = node.getChild("month").getChild("steps").getText();
                        stepValues[6] = stepValues[6] + Integer.parseInt(totalSteps[6]);
                        numberToDivideStepValuesBy[6]++;
                        break;

                    case "08":
                        totalSteps[7] = node.getChild("month").getChild("steps").getText();
                        stepValues[7] = stepValues[7] + Integer.parseInt(totalSteps[7]);
                        numberToDivideStepValuesBy[7]++;
                        break;

                    case "09":
                        totalSteps[8] = node.getChild("month").getChild("steps").getText();
                        stepValues[8] = stepValues[8] + Integer.parseInt(totalSteps[8]);
                        numberToDivideStepValuesBy[8]++;
                        break;

                    case "10":
                        totalSteps[9] = node.getChild("month").getChild("steps").getText();
                        stepValues[9] = stepValues[9] + Integer.parseInt(totalSteps[9]);
                        numberToDivideStepValuesBy[9]++;
                        break;

                    case "11":
                        totalSteps[10] = node.getChild("month").getChild("steps").getText();
                        stepValues[10] = stepValues[10] + Integer.parseInt(totalSteps[10]);
                        numberToDivideStepValuesBy[10]++;
                        break;

                    case "12":
                        totalSteps[11] = node.getChild("month").getChild("steps").getText();
                        stepValues[11] = stepValues[11] + Integer.parseInt(totalSteps[11]);
                        numberToDivideStepValuesBy[11]++;
                        break;
                }
            }

        }

        //Finds the average for steps and returns an array of those averages.
        return calculateAverageValues(stepValues, numberToDivideStepValuesBy);
    }

    //Calculates the average number of any given value.
    private int[] calculateAverageValues(int[] numberOfSteps, int[] dividerValue)
    {

        //Index to get access to the values of that index.
        int index = 0;

        //Calculates the average or provides dummy data to avoid errors.
        for (int steps : numberOfSteps)
        {
            if (dividerValue[index] != 0)
            {
                numberOfSteps[index] = steps / dividerValue[index];
            }
            else
            {
                numberOfSteps[index] = 0;
            }

            index++;
        }

        return numberOfSteps;
    }

    /**
     * This method gets the heart rate data for calculating the steps data.
     *
     * @return This method returns a string array with numeric strings.
     */
    public int[] getHeartRateData()
    {
        //Used to hold the numeric string of the heart rate.
        String[] totalHeartRate = new String[MONTH];

        //Denominator to divide the heart rate by.
        int[] numberToDivideHeartRateBy = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //Field for string to be returned.
        int[] heartRate = new int[MONTH];

        //Method call to setup the necessary document and file.
        documentFileSetup();

        //Gets the child node
        Element allHikes = rootNode.getChild("allHikeDetails");

        List stepTakenList = allHikes.getChildren("date");

        //Inserts the value of each child into the array.
        for (int i = 0; i < stepTakenList.size(); i++)
        {
            Element node = (Element) stepTakenList.get(i);

            if (node.getChild("month").getName().equals("month"))
            {
                switch (node.getChild("month").getAttributeValue(ID))
                {
                    case "01":
                        totalHeartRate[0] = node.getChild("heartRate").getText();
                        heartRate[0] = heartRate[0] + Integer.parseInt(totalHeartRate[0]);
                        numberToDivideHeartRateBy[0]++;
                        break;

                    case "02":
                        totalHeartRate[1] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[1] = heartRate[1] + Integer.parseInt(totalHeartRate[1]);
                        numberToDivideHeartRateBy[1]++;
                        break;

                    case "03":
                        totalHeartRate[2] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[2] = heartRate[2] + Integer.parseInt(totalHeartRate[2]);
                        numberToDivideHeartRateBy[2]++;
                        break;

                    case "04":
                        totalHeartRate[3] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[3] = heartRate[3] + Integer.parseInt(totalHeartRate[3]);
                        numberToDivideHeartRateBy[3]++;
                        break;

                    case "05":
                        totalHeartRate[4] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[4] = heartRate[4] + Integer.parseInt(totalHeartRate[4]);
                        numberToDivideHeartRateBy[4]++;
                        break;

                    case "06":
                        totalHeartRate[5] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[5] = heartRate[5] + Integer.parseInt(totalHeartRate[5]);
                        numberToDivideHeartRateBy[5]++;
                        break;

                    case "07":
                        totalHeartRate[6] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[6] = heartRate[6] + Integer.parseInt(totalHeartRate[6]);
                        numberToDivideHeartRateBy[6]++;
                        break;

                    case "08":
                        totalHeartRate[7] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[7] = heartRate[7] + Integer.parseInt(totalHeartRate[7]);
                        numberToDivideHeartRateBy[7]++;
                        break;

                    case "09":
                        totalHeartRate[8] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[8] = heartRate[8] + Integer.parseInt(totalHeartRate[8]);
                        numberToDivideHeartRateBy[8]++;
                        break;

                    case "10":
                        totalHeartRate[9] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[9] = heartRate[9] + Integer.parseInt(totalHeartRate[9]);
                        numberToDivideHeartRateBy[9]++;
                        break;

                    case "11":
                        totalHeartRate[10] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[10] = heartRate[10] + Integer.parseInt(totalHeartRate[10]);
                        numberToDivideHeartRateBy[10]++;
                        break;

                    case "12":
                        totalHeartRate[11] = node.getChild("month").getChild("heartRate").getText();
                        heartRate[11] = heartRate[11] + Integer.parseInt(totalHeartRate[11]);
                        numberToDivideHeartRateBy[11]++;
                        break;
                }
            }

        }

        //Finds the average for heart rate and returns an array of those averages.
        return calculateAverageValues(heartRate, numberToDivideHeartRateBy);
    }

    /**
     * Used to provide a boolean on whether the data is present or not.
     *
     * @return Returns true if there is data or false if there is no data or a null value is provided.
     */
    public boolean hasChartValues()
    {
        documentFileSetup();

        try
        {
            //List of date nodes.
            List dates = rootNode.getChild("allHikeDetails").getChildren("date");

            //Loops through each date node to ensure there is no missing data.
            for (int i = 0; i < dates.size(); i++){
                Element dateNode = (Element) dates.get(i);

                String stepValue = dateNode.getChild("month").getChild("steps").getText();
                String heartValue = dateNode.getChild("month").getChild("heartRate").getText();

                //Empty data returns false.
                if (stepValue.isEmpty() || heartValue.isEmpty())
                {
                    return false;
                }
            }

            return true;
        }
        catch (NullPointerException exception)
        {
            //If a null was found, then returns false.
            return false;
        }
    }

    /**
     * This method loads an array of strings that contain the reminder messages.
     *
     * @return This method returns a string array with reminder messages.
     */
    public String[] loadReminderMessages()
    {
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
        for (int i = 0; i < reminderMessageList.size(); i++)
        {
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
    public void addHeartRate(String heartRateValue, String dateToSearchFor)
    {
        //Method call to setup the necessary document and file.
        documentFileSetup();

        //Gets a child node of root.
        Element allHikes = rootNode.getChild("allHikeDetails");

        //List of children node called date.
        List dates = allHikes.getChildren("date");

        //Element that holds the date nodes.
        Element dateNode;

        //Searches for the date.
        for (int i = 0; i < dates.size(); i++)
        {
            dateNode = (Element) dates.get(i);

            //gets the attribute value of the date node.
            String attributeHolder = dateNode.getAttributeValue(ID);

            //Insert steps if node is null
            if (attributeHolder.contentEquals(dateToSearchFor))
            {
                if (dateNode.getChild("month").getChild("heartRate") == null)
                {
                    dateNode.getChild("month").addContent(new Element("heartRate").setText(heartRateValue));
                }
            }
        }

        //Writes to the xml file.
        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    /**
     * This method adds the number of steps to the xml file.
     *
     * @param stepsTaken A string with the numeric value of the user's steps
     * @param dateToSearchFor
     */
    public void addSteps(String stepsTaken, String dateToSearchFor)
    {
        //Sets the files and root node.
        documentFileSetup();

        //Gets a child node of root.
        Element allHikes = rootNode.getChild("allHikeDetails");

        //List of children node called date.
        List dates = allHikes.getChildren("date");

        //Element that holds the date nodes.
        Element dateNode;

        //Searches for the date.
        for (int i = 0; i < dates.size(); i++)
        {
            dateNode = (Element) dates.get(i);

            //gets the attribute value of the date node.
            String attributeHolder = dateNode.getAttributeValue(ID);

            //Insert steps if node is null
            if (attributeHolder.contentEquals(dateToSearchFor))
            {
                if (dateNode.getChild("month").getChild("steps") == null)
                {
                    dateNode.getChild("month").addContent(new Element("steps").setText(stepsTaken));
                }
            }
        }

        //Writes to the xml file.
        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    /**
     * Makes a parent node for the heartRate and steps node.
     *
     * @param numericMonth Provides the numeric string of the month.
     * @param dateToSearchFor Provides a date string to search for.
     */
    public void heartRateAndStepsOrganizer(String numericMonth, String dateToSearchFor)
    {
        //Setup for the file.
        documentFileSetup();

        //Child of the root.
        Element allHikes = rootNode.getChild("allHikeDetails");

        //List that holds the children of the parent.
        List dates = allHikes.getChildren("date");

        //Element that holds the date node.
        Element dateNode;

        //Loop for searching for the date.
        for (int i = 0; i < dates.size(); i++)
        {
            dateNode = (Element) dates.get(i);

            //Gets the value of the attribute of that date.
            String attributeHolder = dateNode.getAttributeValue(ID);

            //Checks the date and then creates the month element with given id.
            if (attributeHolder.contentEquals(dateToSearchFor))
            {
                if (dateNode.getChild("month") == null)
                {
                    dateNode.addContent(new Element("month").setAttribute(ID, numericMonth));
                }
            }
        }

        //Writes to the xml file.
        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    /**
     * this method adds all the checked reminder messages to the hike
     */
    public void addReminderMessageToHike()
    {
        documentFileSetup();

        //dig down to the date elements
        Element allHikes = rootNode.getChild(ALL_HIKE_DETAILS);
        List<Element> dates = allHikes.getChildren(DATE_ELEMENT_STRING);

        //make sure the reminder messages are binned to the hikes
        Element last = dates.get(dates.size() - 1);

        //uses a for loop to add all reminder messages
        for (int i = 0; i < reminderMessages.getMessages().size(); i++)
        {
            Element message = new Element(REMINDER_MESSAGE_ELEMENT_STRING).setText(reminderMessages.getMessages().get(i));
            last.addContent(message);
        }

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

    /**
     * this method goes through the file and finds all the reminder messages
     * based on the specific date
     *
     * @param dateString a string of date
     * @return an array of reminder messages
     */
    public String[] getReminderMessageBasedOnDate(String dateString)
    {
        documentFileSetup();

        //Field for string array to be returned.
        String[] reminderMessage;

        //Dig down to the reminder messages.
        Element hikes = rootNode.getChild(ALL_HIKE_DETAILS);
        Element dateElement = null;

        //get the date element at the specific date
        for (Element date : hikes.getChildren(DATE_ELEMENT_STRING))
        {
            //if the attribute value is the same as the specific date that's being passed
            //get the date element
            String id = date.getAttributeValue(ID);
            if (id.equals(dateString))
            {
                dateElement = date;
            }
        }

        //A list of the children in the reminder node.
        List reminderMessageList = dateElement.getChildren(REMINDER_MESSAGE_ELEMENT_STRING);

        //Provides the array with the exact size it needs to be to hold the list.
        reminderMessage = new String[reminderMessageList.size()];

        //Inserts the value of each child into the array.
        for (int i = 0; i < reminderMessageList.size(); i++)
        {
            Element node = (Element) reminderMessageList.get(i);

            reminderMessage[i] = node.getText();
        }

        return reminderMessage;
    }

    //Template method for setting up the document.
    private void documentFileSetup()
    {
        try
        {
            //Makes a Sax parser.
            builder = new SAXBuilder();

            //Calls the file from the path
            xmlFile = new File("data/master.xml");

            //Makes a document based on the xml file.
            xmlFileDocument = (Document) builder.build(xmlFile);

            //Provides the root node of the document.
            rootNode = xmlFileDocument.getRootElement();

        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
        }
        catch (JDOMException exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    //Writes to the xml file
    private void writeToXMLFile(Document xmlDocument, File xmlFile)
    {
        try
        {
            //Output object.
            XMLOutputter xmlOutput = new XMLOutputter();

            //Provides indentation to xml to make it easier for humans to read.
            xmlOutput.setFormat(Format.getPrettyFormat());

            //Writes to the xml file
            xmlOutput.output(xmlDocument, new FileWriter(xmlFile.getAbsolutePath()));
        }
        catch (IOException exception)
        {
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
        Element dateElement = new Element(DATE_ELEMENT_STRING).setAttribute(ID, hike.getDate());

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
            Element date = (Element) dateList.get(i);

            //stores the element's attribute value to the date string array
            dateStrings[i] = date.getAttributeValue(ID);
        }

        //returns the date array
        return dateStrings;
    }

    public String getDate()
    {
        return hike.getDate();
    }

    /**
     * this method set the location for a hike object
     *
     * @param location hike location
     */
    public void setHikeLocation(String location)
    {
        hike.setLocation(location);
    }

    /**
     * this method set the date for a hike object
     *
     * @param date hike date
     */
    public void setHikeDate(String date)
    {
        hike.setDate(date);
    }

    /**
     * this method setup an array list for reminder messages
     *
     * @param size size of array list
     */
    public void setHikeReminderMessages(int size)
    {
        reminderMessages.setMessages(new ArrayList<>(size));
    }

    /**
     * this method adds a message to the reminder messages object
     *
     * @param message a reminder message
     */
    public void addHikeReminderMessage(String message)
    {
        reminderMessages.addMessage(message);
    }

    /**
     * this method removes a message from the reminder messages object
     *
     * @param message a reminder message
     */
    public void removeHikeReminderMessage(String message)
    {
        reminderMessages.removeMessage(message);
    }

    /**
     * Adds a new reminder message to the xml file.
     *
     * @param message This parameter provides the string that is to become the new reminder message.
     */
    public void addNewReminderMessage(String message)
    {
        //Sets up the files
        documentFileSetup();

        //Sets the text to the new message element
        rootNode.getChild("reminders").addContent(new Element("message").setText(message));

        //Writes to the file.
        writeToXMLFile(xmlFileDocument, xmlFile);
    }
}