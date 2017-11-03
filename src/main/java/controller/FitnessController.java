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

import static model.ValidateOS.isMac;
import static model.ValidateOS.isWindows;

public class FitnessController {

    //Used to hold the scene information passed from the view.
    private Scene scene;

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

    //
    public String[] loadReminderMessages(){

        String[] reminderString = null;

        try {
            builder = new SAXBuilder();

            if (isWindows()){
                xmlFile = new File("data/master.xml");
            }

            if(isMac()){
                xmlFile = new File("data/master.xml");
            }

            xmlFileDocument = (Document) builder.build(xmlFile);
            rootNode = xmlFileDocument.getRootElement();

            List reminderMessageList = rootNode.getChildren("reminders");

            reminderString = new String[reminderMessageList.size()];

            for (int i = 0; i < reminderMessageList.size(); i++){
                Element node = (Element) reminderMessageList.get(i);

                reminderString[i] = node.getChildText("message");
            }

        }catch (JDOMException exception){
            System.out.println(exception.getMessage());
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        return reminderString;
    }


    //Writes the value of the heart rate to the XML
    public void addHeartRate(String heartRateValue){
        try {

            builder = new SAXBuilder();

            if (isWindows()){
                xmlFile = new File("data/master.xml");
            }

            if(isMac()){
                xmlFile = new File("data/master.xml");
            }

            xmlFileDocument = (Document) builder.build(xmlFile);
            rootNode = xmlFileDocument.getRootElement();

            Element heartRate = rootNode.getChild("heartRate");

            heartRate.addContent(new Element("rate").setText(heartRateValue));

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(xmlFileDocument, new FileWriter(xmlFile.getAbsolutePath()));

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}
