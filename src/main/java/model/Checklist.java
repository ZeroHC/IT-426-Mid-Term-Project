package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Checklist
{
    String[] checklist = {"binoculars", "flashlight"};

    public void start(Stage stage) throws Exception
    {
        stage.setScene(createChecklist());
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



        return new Scene(vBox, 300, 300);
    }

    public void addChecklistItem()
    {

    }
}
