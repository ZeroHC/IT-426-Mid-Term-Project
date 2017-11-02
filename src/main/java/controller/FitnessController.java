package controller;

import javafx.scene.Scene;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FitnessController {

    //Used to hold the scene information passed from the view.
    private Scene scene;

    SAXBuilder builder = new SAXBuilder();
    File xmlFile = new File("/Users/Naoki_Shisho/Desktop/" +
            "IT-426-Mid-Term-Project/data/master.xml");

    public FitnessController(Scene scene){
        this.scene = scene;
    }

    public Scene updateView(){
        return scene;
    }

    public void setView(Scene scene){
        this.scene = scene;
    }

    public String[] loadReminderMessages(){

        String[] reminderString = null;

        builder = new SAXBuilder();

        xmlFile = new File("/Users/Naoki_Shisho/Desktop/" +
                "IT-426-Mid-Term-Project/data/master.xml");

        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List reminderMessageList = rootNode.getChildren("reminders");

            reminderString = new String[reminderMessageList.size()];


            for (int i = 0; i < reminderMessageList.size(); i++){
                Element node = (Element) reminderMessageList.get(i);

                reminderString[i] = node.getChildText("message");
            }

        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }catch (JDOMException exception){
            System.out.println(exception.getMessage());
        }

        return reminderString;
    }



}
