package model;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class MakeDefaultXML {

    public static void defaultXMl(String filePath) throws IOException{

        Element hiker = new Element("hiker");
        Document xmlDocument = new Document(hiker);

        Element scheduledHike = new Element("scheduledHike");

        xmlDocument.getRootElement().addContent(scheduledHike);

        Element previouslyHiked = new Element("previouslyHiked");
        xmlDocument.getRootElement().addContent(previouslyHiked);

        Element reminders1 = new Element("reminders");
        reminders1.setAttribute("id", "1");
        reminders1.addContent(new Element("message").setText("Wear your fitbit!"));
        xmlDocument.getRootElement().addContent(reminders1);

        Element reminders2 = new Element("reminders");
        reminders2.setAttribute("id", "2");
        reminders2.addContent(new Element("message").setText("Be sure to stretch!"));
        xmlDocument.getRootElement().addContent(reminders2);

        Element reminders3 = new Element("reminders");
        reminders3.setAttribute("id", "3");
        reminders3.addContent(new Element("message").setText("Grab your backpack!"));
        xmlDocument.getRootElement().addContent(reminders3);

        Element reminders4 = new Element("reminders");
        reminders4.setAttribute("id", "4");
        reminders4.addContent(new Element("message").setText("Check the forecast!"));
        xmlDocument.getRootElement().addContent(reminders4);

        Element reminders5 = new Element("reminders");
        reminders5.setAttribute("id", "5");
        reminders5.addContent(new Element("message").setText("Drink plenty of water!"));
        xmlDocument.getRootElement().addContent(reminders5);

        Element reminders6 = new Element("reminders");
        reminders6.setAttribute("id", "6");
        reminders6.addContent(new Element("message").setText("Dress in layers!"));
        xmlDocument.getRootElement().addContent(reminders6);

        Element reminders7 = new Element("reminders");
        reminders7.setAttribute("id", "7");
        reminders7.addContent(new Element("message").setText("Check the Checklist!"));
        xmlDocument.getRootElement().addContent(reminders7);


        XMLOutputter xmlOutput = new XMLOutputter();

        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(xmlDocument, new FileWriter(filePath));

    }

 //   //To do later for refactoring reminder messages.
//    private Element[] makeElementArray(String[] messages){
//        Element
//    }
}
