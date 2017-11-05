package controller;

import javafx.scene.Scene;
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

public class FitnessController {

    //Used to hold the scene information passed from the view.
    private Scene scene;

    //The initial fields for handling the xml file.
    private SAXBuilder builder;
    private File xmlFile;
    private Document xmlFileDocument;
    private Element rootNode;

    public FitnessController(Scene scene){
        this.scene = scene;
    }

    public Scene updateView(){
        return scene;
    }

    public void setView(Scene scene){
        this.scene = scene;
    }

    public String[] getStepData(){
        String[] stepValues;

        documentFileSetup();

        Element year = rootNode.getChild("year");

        Element month = year.getChild("month");

        List stepTakenList = month.getChildren("numberOfSteps");

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

        Element year = rootNode.getChild("year");

        Element month = year.getChild("month");

        List heartRateList = month.getChildren("heartRate");

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
}
