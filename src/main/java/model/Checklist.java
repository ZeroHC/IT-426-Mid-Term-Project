package model;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Checklist extends Application
{
    String[] checklist = {"backpack", "binoculars", "flashlight", "compass", "rain coat", "map", "food", "water"};

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setScene(createChecklist());
        stage.setTitle("Checklist");
        stage.show();
    }

    public Scene createChecklist()
    {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        CheckBox[] boxes = new CheckBox[checklist.length];

        for(int i = 0; i < checklist.length; i++)
        {
            CheckBox box = new CheckBox(checklist[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        for(int i = 0; i < checklist.length; i++)
        {
            final CheckBox box = boxes[i];
            final String listItem = checklist[i];

            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    if(newValue == true)
                    {
                        box.setText(listItem + " packed!");
                    }
                    else
                    {
                        box.setText(listItem);
                    }
                }
            });
        }

        return new Scene(vBox, 300, 300);
    }

    public void addChecklistItem()
    {

    }
}
