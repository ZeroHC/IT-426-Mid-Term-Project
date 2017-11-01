package ui;

import com.jfoenix.controls.JFXCheckBox;
import controller.FitnessController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FitnessView extends Application {

    //Constants.
    private static final int INITIALIZING_VALUE = 0;
    private static final int WIDTH = 960;
    private static final int HEIGHT = 630;
    private static final int EXERCISE_TRACKER_BODY_HEIGHT = 430;
    private static final String BACK = "BACK";
    private static final String NEXT = "NEXT";
    private static final int EXERCISE_TRACKER_WIDTH = 500;
    private static final int EXERCISE_TRACKER_HEADER_FOOTER = 100;
    private static final int BUTTON_SHADOW_RADIUS = 1;
    private static final int BUTTON_SHADOW_OFFSET = 2;
    private static final int EXERCISE_PROGRESS_WIDTH = 960;
    private final String[] BUTTON_NAMES_FOR_HOME = {"NEW HIKE", "SCHEDULED HIKE", "EXERCISE PROGRESS", "CHECKLIST"};
    private static final String[] CHECKLIST = new String[]{"backpack", "binoculars", "flashlight", "compass", "rain coat", "map", "food", "water"};

    //Scene object to hold current scene.
    private Scene currentScene;

    //A copy of the stage parameter in the start method.
    private Stage mainStage;

    //Controller to perform necessary actions.
    private FitnessController controller = new FitnessController(defaultStartingScene());



    //Initial scene setup for application.
    private Scene defaultStartingScene(){
        return currentScene = home();
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        stage.setTitle("Hike Master 9000");
        stage.setScene(currentScene);
        stage.show();

    }

    //Makes an array of buttons
    private Button[] makeButtons(String[] name){

        Button[] buttons = new Button[name.length];

        DropShadow shadow = addDropShadow();

        for (int i = INITIALIZING_VALUE; i < name.length; i++){

            buttons[i] = new Button(name[i].toUpperCase());
            buttons[i].getStyleClass().add("home-buttons");
            addMouseHoverEvent(buttons[i], shadow);
            setButtonActionForSceneChange(buttons[i], name[i]);
        }

        return buttons;
    }

    //Sets the action for buttons to change to the correct scene.
    private void setButtonActionForSceneChange(Button button, String key){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.setView(currentScene = sceneSelector(key));
                mainStage.setScene(controller.updateView());
            }
        });
    }

    //Makes a button that goes to the home scene.
    private Button makeBackButton(String name){

        Button button = new Button(name);
        button.getStyleClass().add("back-btn-icon");
        DropShadow shadow = addDropShadow();

        addMouseHoverEvent(button, shadow);

        setButtonActionForSceneChange(button, BACK);

        return button;
    }

    //Makes a button that goes to the home scene.
    private Button makeNextButton(String name){

        Button button = new Button(name);
        //button.getStyleClass().add("back-btn-icon");
        DropShadow shadow = addDropShadow();

        addMouseHoverEvent(button, shadow);

        setButtonActionForSceneChange(button, NEXT);

        return button;
    }

    //Helper method for adding multiple buttons to node.
    private void addButtons(VBox container, Button[] buttons){
        for (Button button : buttons){
            container.getChildren().add(button);
        }
    }

    //Provides scene to be set by controller.
    private Scene sceneSelector(String sceneName){

        Scene scene = null;

        switch (sceneName){
            case "NEW HIKE":
                scene = newTrail();
                break;

            case "SCHEDULED HIKE":
                scene = scheduledHikes();
                break;

            case "EXERCISE PROGRESS":
                scene = exerciseProgress();
                break;

            case "BACK":
                scene = home();
                break;

            case "CHECKLIST":
                scene = checkList();
                break;

            case "NEXT":
                scene = hikeDetail();
                break;
        }

        return scene;
    }

    //Creates a shadow effect for button.
    private DropShadow addDropShadow(){
        return new DropShadow(BUTTON_SHADOW_RADIUS, BUTTON_SHADOW_OFFSET, BUTTON_SHADOW_OFFSET, Color.GRAY);
    }

    //Adds mouse events to buttons.
    private void addMouseHoverEvent(Button button, DropShadow shadow){
        EventHandler<MouseEvent> mouseOverEventEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(shadow);
            }
        };

        EventHandler<MouseEvent> mouseOffEventEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }
        };

        button.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseOverEventEventHandler);
        button.addEventFilter(MouseEvent.MOUSE_EXITED, mouseOffEventEventHandler);
    }

    //Generates text for title headings.
    private Text titleMaker(String titleName){
        Text title = new Text(titleName);
        title.getStyleClass().add("title-text");
        return title;
    }

    //Generates text for labels.
    private Label labelMaker(String labaleName){
        Label label = new Label(labaleName);
        label.getStyleClass().add("label-text");
        return label;
    }

    //Starting screen.
    private Scene home(){

        VBox menuContainer = new VBox();
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setSpacing(20);
        menuContainer.getStylesheets().add("styles/HikemasterStyles.css");
        menuContainer.setId("home-image");

        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(50));

        Text titleText = titleMaker("Hike Master 9000");

        Reflection reflection = new Reflection();
        reflection.getFraction();
        titleText.setEffect(reflection);

        title.getChildren().add(titleText);

        menuContainer.getChildren().add(title);

        Button[] buttons = makeButtons(BUTTON_NAMES_FOR_HOME);

        buttons[0].setId("new-hike-btn-icon");
        buttons[1].setId("scheduled-btn-icon");
        buttons[BUTTON_SHADOW_OFFSET].setId("exercise-progress-btn-icon");

        addButtons(menuContainer, buttons);

        //Temporary button until other buttons are made.
        Button tempButton = new Button("Exercise Tracker".toUpperCase());
        tempButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.setView(exerciseTracker());
                mainStage.setScene(controller.updateView());
            }
        });

        menuContainer.getChildren().add(tempButton);

        return new Scene(menuContainer, WIDTH, HEIGHT);
    }

    //Scene for adding information about user's heart rate and steps taken.
    private Scene exerciseTracker(){

        VBox mainContainer = new VBox();
        mainContainer.getStylesheets().add("styles/HikemasterStyles.css");
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setMaxSize(EXERCISE_TRACKER_WIDTH, HEIGHT);

        VBox top = new VBox();
        top.setAlignment(Pos.TOP_CENTER);
        top.setMaxSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        top.setMinSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        top.getStyleClass().add("window-top");

        HBox menuHolder = new HBox();
        VBox menu = new VBox();

        VBox middle = new VBox();
        middle.setAlignment(Pos.CENTER);
        middle.setSpacing(10);
        middle.setMaxSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_BODY_HEIGHT);
        middle.setMinSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_BODY_HEIGHT);
        middle.getStyleClass().add("window-mid");

        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setMaxSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        bottom.setMinSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        bottom.getStyleClass().add("window-bot");

        Text title = titleMaker("Exercise Tracker");

        Label heartRate = labelMaker("Heart Rate");
        TextField heartRateEntry = new TextField();

        heartRateEntry.setMaxWidth(250);

        Label steps = labelMaker("Steps");
        TextField stepsEntry = new TextField();

        stepsEntry.setMaxWidth(250);

        middle.getChildren().addAll(heartRate, heartRateEntry, steps, stepsEntry);

        Button button = makeBackButton(BACK);

        top.getChildren().add(title);
        bottom.getChildren().add(button);

        mainContainer.getChildren().addAll(top, middle, bottom);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //Scene for displaying user's data on heart rate and steps taken.
    private Scene exerciseProgress(){
        VBox mainContainer = new VBox();
        mainContainer.getStylesheets().add("styles/HikemasterStyles.css");
        mainContainer.setAlignment(Pos.TOP_LEFT);
        mainContainer.setMinSize(WIDTH, HEIGHT);
        mainContainer.setMaxSize(WIDTH, HEIGHT);

        VBox top = new VBox();
        top.setAlignment(Pos.TOP_CENTER);
        top.setMaxSize(WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        top.setMinSize(WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        top.getStyleClass().add("window-top");

        VBox middle = new VBox();
        middle.setAlignment(Pos.CENTER);
        middle.setMaxSize(WIDTH, EXERCISE_TRACKER_BODY_HEIGHT);
        middle.setMinSize(WIDTH, EXERCISE_TRACKER_BODY_HEIGHT);
        middle.getStyleClass().add("window-mid");

        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setMaxSize(WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        bottom.setMinSize(WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        bottom.getStyleClass().add("window-bot");

        Text title = titleMaker("Exercise Progress");
        top.getChildren().add(title);

        NumberAxis xMonth = new NumberAxis(1, 12, 1);
        xMonth.setLabel("Month");

        NumberAxis yHeartRate = new NumberAxis(0, 240, 60);
        yHeartRate.setLabel("Heart Rate");

        final LineChart<Number, Number> heartRate = new LineChart<>(xMonth, yHeartRate);

        XYChart.Series<Number, Number> coordinates = new XYChart.Series<>();
        coordinates.setName("Heart Rate per Hike");

        coordinates.getData().add(new XYChart.Data<>(1, 90));
        coordinates.getData().add(new XYChart.Data<>(2, 85));
        coordinates.getData().add(new XYChart.Data<>(3, 88));
        coordinates.getData().add(new XYChart.Data<>(4, 70));
        coordinates.getData().add(new XYChart.Data<>(5, 76));
        coordinates.getData().add(new XYChart.Data<>(6, 70));
        coordinates.getData().add(new XYChart.Data<>(7, 68));
        coordinates.getData().add(new XYChart.Data<>(8, 100));
        coordinates.getData().add(new XYChart.Data<>(9, 89));
        coordinates.getData().add(new XYChart.Data<>(10, 60));

        heartRate.getData().add(coordinates);

        NumberAxis xStepMonth = new NumberAxis(1, 12, 1);
        xStepMonth.setLabel("Month");

        NumberAxis ySteps = new NumberAxis(0, 20000, 10000);
        ySteps.setLabel("Steps");

        final LineChart<Number, Number> steps = new LineChart<>(xStepMonth, ySteps);

        XYChart.Series<Number, Number> stepCoordinates = new XYChart.Series<>();
        stepCoordinates.setName("Steps per Hike");

        stepCoordinates.getData().add(new XYChart.Data<>(1, 6000));
        stepCoordinates.getData().add(new XYChart.Data<>(2, 4000));
        stepCoordinates.getData().add(new XYChart.Data<>(3, 3456));
        stepCoordinates.getData().add(new XYChart.Data<>(4, 2345));
        stepCoordinates.getData().add(new XYChart.Data<>(5, 3657));
        stepCoordinates.getData().add(new XYChart.Data<>(5, 10000));
        stepCoordinates.getData().add(new XYChart.Data<>(7, 4567));
        stepCoordinates.getData().add(new XYChart.Data<>(8, 9876));
        stepCoordinates.getData().add(new XYChart.Data<>(9, 2345));
        stepCoordinates.getData().add(new XYChart.Data<>(10, 12345));

        steps.getData().add(stepCoordinates);

        HBox chartBox = new HBox();
        chartBox.setSpacing(15);

        chartBox.getChildren().addAll(heartRate, steps);
        middle.getChildren().add(chartBox);


        Button back = makeBackButton(BACK);
        bottom.getChildren().add(back);

        mainContainer.getChildren().addAll(top, middle, bottom);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //
    private Scene newTrail(){
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStylesheets().add("styles/HikemasterStyles.css");

        Text title = titleMaker("Select Hike");
        mainContainer.getChildren().add(title);

        Button back = makeBackButton(BACK);

        Button next = makeNextButton(NEXT);

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        mainContainer.getChildren().add(buttonRow);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //
    private Scene checkList()
    {
        VBox vBox = new VBox();
        vBox.getStylesheets().add("styles/HikemasterStyles.css");
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Text checklistTitle = titleMaker("Checklist");
        vBox.getChildren().add(checklistTitle);

        JFXCheckBox[] boxes = new JFXCheckBox[CHECKLIST.length];

        for(int i = 0; i < CHECKLIST.length; i++)
        {
            JFXCheckBox box = new JFXCheckBox(CHECKLIST[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        for(int i = 0; i < CHECKLIST.length; i++)
        {
            final CheckBox box = boxes[i];
            final String listItem = CHECKLIST[i];

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

//        Scene checklistScene = new Scene(vBox, 400, 600);
//        checklistScene.getStylesheets().add("styles/hikeStyles.css");
//
        Button back = makeBackButton(BACK);
        vBox.getChildren().add(back);
//
        return new Scene(vBox, 400, 600);

    }

    //
    private Scene scheduledHikes(){
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getStylesheets().add("styles/HikemasterStyles.css");

        Text text = titleMaker("Scheduled Hike");

        Button back = makeBackButton(BACK);

        container.getChildren().addAll(text, back);

        return new Scene(container, WIDTH, HEIGHT);
    }

    //
    private Scene reminderMessages(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        String[] messageList = {"Where your fitbit!", "Be sure to stretch!", "Grab your backpack!",
                "Check the forecast!", "Drink plenty of water!", "Dress in layers!", "Check your Checklist!"};

        JFXCheckBox[] boxes = new JFXCheckBox[messageList.length];

        for(int i = 0; i < messageList.length; i++)
        {
            JFXCheckBox box = new JFXCheckBox(messageList[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        Scene listedMessagesScene = new Scene(vBox, 400, 600);
        listedMessagesScene.getStylesheets().add("styles/HikemasterStyles.css");

        return listedMessagesScene;
    }


    //
    private Scene hikeDetail()
    {
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStylesheets().add("styles/HikemasterStyles.css");

        Text title = titleMaker("Hike Detail");

        VBox inputContainer = new VBox();
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setSpacing(20);

        HBox locationRow = new HBox();
        locationRow.setAlignment(Pos.CENTER);

        Label hikeLocation = new Label("Location:\t");
        TextField locationInput = new TextField();

        locationRow.getChildren().addAll(hikeLocation, locationInput);

        HBox dateRow = new HBox();
        dateRow.setAlignment(Pos.CENTER);

        Label hikeDate = new Label("Date:\t");
        DatePicker dateInput = new DatePicker();

        dateRow.getChildren().addAll(hikeDate, dateInput);

        inputContainer.getChildren().addAll(locationRow, dateRow);

        Button back = makeBackButton(BACK);

        mainContainer.getChildren().addAll(title, inputContainer, back);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }
}