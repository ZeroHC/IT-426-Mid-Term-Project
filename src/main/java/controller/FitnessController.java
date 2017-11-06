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
import org.w3c.dom.NodeList;

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
     * @param scene
     */
    public void setView(Scene scene){
        this.scene = scene;
    }

    public String[] getStepData(){
        String[] stepValues;

        documentFileSetup();

        Element date = rootNode.getChild("date");

        List stepTakenList = date.getChildren("numberOfSteps");

        stepValues = new String[stepTakenList.size()];

        for(int i = 0; i < stepTakenList.size(); i++){
            Element node = (Element) stepTakenList.get(i);

            stepValues[i] = node.getText();
        }

        return stepValues;
    }

    public String[] getHeartRateData(){
        String[] heartRateValues;

        documentFileSetup();

        Element date = rootNode.getChild("date");

        List heartRateList = date.getChildren("heartRate");

        heartRateValues = new String[heartRateList.size()];

        for(int i = 0; i < heartRateList.size(); i++){
            Element node = (Element) heartRateList.get(i);

            heartRateValues[i] = node.getText();
        }

        return heartRateValues;
    }

    //
    public String[] loadReminderMessages(){

        String[] reminderString;

        documentFileSetup();

        Element reminder = rootNode.getChild("reminders");

        List reminderMessageList = reminder.getChildren("message");

        reminderString = new String[reminderMessageList.size()];

        for (int i = 0; i < reminderMessageList.size(); i++){
            Element node = (Element) reminderMessageList.get(i);

            reminderString[i] = node.getText();
        }

        return reminderString;
    }

    //Writes the value of the heart rate to the XML
    public void addHeartRate(String heartRateValue){

        documentFileSetup();

        Element year = rootNode.getChild("year");

        Element month = year.getChild("month");

        month.addContent(new Element("heartRate").setText(heartRateValue));

        writeToXMLFile(xmlFileDocument, xmlFile);

    }

    public void addSteps(String stepsTaken){
        documentFileSetup();

        Element year = rootNode.getChild("year");

        Element month = year.getChild("month");

        month.addContent(new Element("numberOfSteps").setText(stepsTaken));

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

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

    public void addHike(Hike hike)
    {
        documentFileSetup();

        Element allHikes = rootNode.getChild("previouslyHiked");

        allHikes.addContent(new Element("location").setText(hike.getLocation()));

        allHikes.addContent(new Element("date").setText(hike.getDate()));

        writeToXMLFile(xmlFileDocument, xmlFile);
    }

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
