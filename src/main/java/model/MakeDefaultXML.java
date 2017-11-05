package model;

import java.io.FileWriter;
import java.io.IOException;

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

        makeNode(xmlDocument, "hike");

        makeNode(xmlDocument, "previouslyHiked");

        makeReminderMessages(xmlDocument, defaultReminderMessages);

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
    public static void makeReminderMessages(Document document, String[] reminderMessages){

        String idNumberString;
        int idNumber = 1;

        for (int i = 0; i < reminderMessages.length; i++){
            idNumberString = "" + idNumber;
            document.getRootElement().addContent(new Element("reminders").
                    setAttribute("id", idNumberString).
                    addContent(new Element("message").
                            setText(reminderMessages[i])));
            idNumber++;
        }
    }
}
