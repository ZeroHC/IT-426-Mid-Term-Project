package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class MakeDefaultXML {

    private static String[] defaultReminderMessages = {"Wear your fitbit!", "Be sure to stretch!",
                                                "Grab your backpack!", "Check the forecast!",
                                                "Drink plenty of water!", "Dress in layers!",
                                                "Check the Checklist!"};

    private static Document xmlDocument;

    public static void defaultXMl(String filePath) throws IOException{

        xmlDocument = makeRoot("hiker");

        Element root = xmlDocument.getRootElement();

        makeNode(xmlDocument, "previouslyHiked");

        makeNode(xmlDocument, "reminders");

        Element reminders = root.getChild("reminders");

        makeReminderMessages(reminders, defaultReminderMessages);

        makeNode(xmlDocument, "year");

        root.getChild("year").setAttribute("id", "2017");

        Element year = root.getChild("year");

        for(int i = 1; i < 13; i++){
            makeNode(year, "month");
        }

        List month = year.getChildren("month");

        for(int i = 0; i < month.size(); i++){
            Element node = (Element) month.get(i);

            node.setAttribute("monthNumber", "" + (i + 1));
        }

        XMLOutputter xmlOutput = new XMLOutputter();

        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(xmlDocument, new FileWriter(filePath));
    }

    //
    private static Document makeRoot(String rootName){
        return new Document(new Element(rootName));
    }

    //
    private static void makeNode(Document document, String nodeName){
        document.getRootElement().addContent(new Element(nodeName));
    }

    //
    private static void makeNode(Element element, String nodeName){
        element.addContent(new Element(nodeName));
    }

    //
    public static void makeReminderMessages(Element element, String[] reminderMessages){

        for (int i = 0; i < reminderMessages.length; i++){

            element.addContent(new Element("message").setText(reminderMessages[i]));
        }
    }
}
