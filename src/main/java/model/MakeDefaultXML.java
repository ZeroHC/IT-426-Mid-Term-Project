/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* MakeDefaultXML.java
* This file makes a default xml file to read and write to.
*
*/

package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * This class creates the default values for the application to utilize
 * when the application is run for the first time.
 *
 * @author Joshua Hawks
 * @version 1.0
 */
public class MakeDefaultXML {

    //Constant reminder messages that the application starts with.
    private static final String[] defaultReminderMessages = {"Wear your fitbit!", "Be sure to stretch!",
                                                "Grab your backpack!", "Check the forecast!",
                                                "Drink plenty of water!", "Dress in layers!",
                                                "Check the Checklist!"};

    //Field used to make the root node of the file as well as adding child nodes to the document.
    private static Document xmlDocument;

    /**
     * This method creates the default xml file.
     *
     * @param filePath This parameter provides the location to create the file in.
     * @throws IOException This exception is used to throw an IOException if something is missing.
     */
    public static void defaultXMl(String filePath) throws IOException{

        //Root of the document.
        xmlDocument = makeRoot("hiker");

        //Field that holds the root node of the document.
        Element root = xmlDocument.getRootElement();

        //Makes a child node to the root for previously hiked trails.
        makeNode(xmlDocument, "previouslyHiked");

        //Makes a child node to the root to hold the reminder messages.
        makeNode(xmlDocument, "reminders");

        //Field that holds the child node reminders.
        Element reminders = root.getChild("reminders");

        //Makes child nodes with reminder messages for the reminders node
        makeReminderMessages(reminders, defaultReminderMessages);

        //Makes a child node in root called year.
        makeNode(xmlDocument, "date");

        //Sets a unique id attribute for the year node.
        root.getChild("date").setAttribute("id", "2017-11-06");

        //XML output object.
        XMLOutputter xmlOutput = new XMLOutputter();

        //Provides the document indentation to make the file easier for people to read.
        xmlOutput.setFormat(Format.getPrettyFormat());

        //Creates the xml file.
        xmlOutput.output(xmlDocument, new FileWriter(filePath));
    }

    //A facade to make the root node.
    private static Document makeRoot(String rootName){
        return new Document(new Element(rootName));
    }

    //A facade to make a child node to the root.
    private static void makeNode(Document document, String nodeName){
        document.getRootElement().addContent(new Element(nodeName));
    }

    //A facade to make a child node to another node.
    private static void makeNode(Element element, String nodeName){
        element.addContent(new Element(nodeName));
    }

    //A facade to make node children to another node.
    private static void makeReminderMessages(Element element, String[] reminderMessages){

        //Loop to add nodes to parent node.
        for (int i = 0; i < reminderMessages.length; i++){

            element.addContent(new Element("message").setText(reminderMessages[i]));
        }
    }
}
