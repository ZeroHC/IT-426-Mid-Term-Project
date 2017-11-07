package model;

import java.util.ArrayList;

public class ReminderMessages
{
    private ArrayList<String> messages;

    public ArrayList<String> getMessages()
    {
        return messages;
    }

    public void setMessages(ArrayList<String> messages)
    {
        this.messages = messages;
    }

    public void addMessage(String message)
    {
        messages.add(message);
    }

    public void removeMessage(String message)
    {
        messages.remove(message);
    }
}