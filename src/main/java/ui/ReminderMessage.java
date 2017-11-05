package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ReminderMessage extends Application
{
    String date = "10.28.2017";
    String message = "You have a hike coming up on " + date;
    String[] messageList = {"Where your fitbit!", "Be sure to stretch!", "Grab your backpack!",
            "Check the forecast!", "Drink plenty of water!", "Dress in layers!", "Check the Checklist!"};

    @Override
    public void start(Stage stage) throws Exception
    {
        popUpMessage();
//        stage.setScene(addNewMessage());
        stage.setScene(showListedMessages());
        stage.setTitle("Hike Master 9000");
        stage.show();
    }

    public Scene showListedMessages()
    {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        CheckBox[] boxes = new CheckBox[messageList.length];

        for(int i = 0; i < messageList.length; i++)
        {
            CheckBox box = new CheckBox(messageList[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        Scene listedMessagesScene = new Scene(vBox, 400, 600);
        listedMessagesScene.getStylesheets().add("styles/HikeMasterStyles.css");

        return listedMessagesScene;
    }

    public Scene addNewMessage()
    {
        VBox addNewMessageBox = new VBox();
        addNewMessageBox.setAlignment(Pos.TOP_CENTER);
        addNewMessageBox.setSpacing(10);
        addNewMessageBox.setPadding(new Insets(10));

        Text addNewMessageHeader = new Text("Add a new message below: ");

        addNewMessageBox.getChildren().add(addNewMessageHeader);

        //Place to enter message
        HBox messageRow = new HBox();
        messageRow.setSpacing(10);

        Label messageLabel = new Label("Message: ");
        messageLabel.setPrefWidth(200);
        messageLabel.setAlignment(Pos.CENTER_LEFT);

        TextField messageField = new TextField();
        messageField.setPrefWidth(300);

        messageRow.getChildren().addAll(messageLabel, messageField);
        addNewMessageBox.getChildren().add(messageRow);

        return new Scene(addNewMessageBox, 300, 300);
    }

    public void popUpMessage()
    {
        Alert hikeReminder = new Alert(Alert.AlertType.INFORMATION);
        hikeReminder.setTitle("Hike Master 9000");
        hikeReminder.setHeaderText("Reminder");
        hikeReminder.setContentText(messageList[0]);

        hikeReminder.showAndWait();
    }
}

