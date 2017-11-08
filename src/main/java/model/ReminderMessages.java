/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* ReminderMessages.java
* This file creates a reminder messages object.
*/

package model;

import java.util.ArrayList;

/**
 * this class creates a reminder messages object
 *
 * @author Hanchen Liu
 * @version 1.0
 */
public class ReminderMessages
{
    //use an array list of strings to store messages
    private ArrayList<String> messages;

    /**
     * this method gets the reminder messages
     *
     * @return reminder messages
     */
    public ArrayList<String> getMessages()
    {
        return messages;
    }

    /**
     * this method sets the reminder messages
     *
     * @param messages reminder messages
     */
    public void setMessages(ArrayList<String> messages)
    {
        this.messages = messages;
    }

    /**
     * this method adds a message to the list of reminder messages
     *
     * @param message a reminder message
     */
    public void addMessage(String message)
    {
        messages.add(message);
    }

    /**
     * this method removes a message from the list of reminder messages
     *
     * @param message a reminder message
     */
    public void removeMessage(String message)
    {
        messages.remove(message);
    }
}