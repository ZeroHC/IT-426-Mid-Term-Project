/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
*
*/

package ui;

import com.jfoenix.controls.*;
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
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FitnessView extends Application {

    //Constants.
    private static final int INITIALIZING_VALUE = 0;
    private static final int WIDTH = 950;
    private static final int HEIGHT = 630;
    private static final int EXERCISE_TRACKER_BODY_HEIGHT = 430;
    private static final String BACK = "BACK";
    private static final String NEXT = "NEXT";
    private static final String HOME_SCENE = "HOME";
    private static final String SELECT_HIKE_SCENE = "NEW HIKE";
    private static final String HIKE_DETAIL_SCENE = "HIKE DETAIL";
    private static final String REMINDER_MESSAGES_SCENE = "REMINDER MESSAGES";
    private static final int EXERCISE_TRACKER_WIDTH = 500;
    private static final int EXERCISE_TRACKER_HEADER_FOOTER = 100;
    private static final int BUTTON_SHADOW_RADIUS = 1;
    private static final int BUTTON_SHADOW_OFFSET = 2;
    private static final int EXERCISE_PROGRESS_WIDTH = 960;
    public static final int JANURARY = 1;
    public static final int DISPLAY_EACH_MONTH = 1;
    public static final int DECEMBER = 12;

    //Checklist needs to be implemented into the scheduled hike scene.
    //Each checklist must correspond to the hike that owns that checklist.
    private final String[] BUTTON_NAMES_FOR_HOME = {"NEW HIKE", "SCHEDULED HIKE", "EXERCISE PROGRESS", /*"CHECKLIST"*/};
    private static final String[] CHECKLIST = new String[]{"backpack", "binoculars", "flashlight", "compass", "rain coat", "map", "food", "water"};

    //Scene object to hold current scene.
    private Scene currentScene;

    //A copy of the stage parameter in the start method.
    private Stage mainStage;

    //Controller to perform necessary actions.
    private FitnessController controller = new FitnessController(defaultStartingScene());

    //Initial scene setup for application.
    private Scene defaultStartingScene() {
        return currentScene = home();
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        stage.setTitle("Hike Master 9000");
        stage.getIcons().add(new Image("images/leafIcon32x33.jpg"));
        stage.setScene(currentScene);
        stage.setResizable(false);
        stage.show();

    }

    //Makes an array of buttons
    private Button[] makeButtons(String[] name) {

        Button[] buttons = new Button[name.length];

        DropShadow shadow = addDropShadow();

        for (int i = INITIALIZING_VALUE; i < name.length; i++) {

            buttons[i] = new Button(name[i].toUpperCase());
            buttons[i].getStyleClass().add("home-btn");
            addMouseHoverEvent(buttons[i], shadow);
            setButtonActionForSceneChange(buttons[i], name[i]);
        }

        return buttons;
    }

    private Scene getWaitingSceneForReminderMessage() {
        VBox box = new VBox();

        //center out layout and add padding
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));

        ProgressIndicator showProgressCircle = new ProgressIndicator();
        Text text = new Text("Checking for reminder messages...");

        box.getChildren().addAll(showProgressCircle, text);

        return new Scene(box, 300, 100);
    }

    //Sets the action for buttons to change to the correct scene.
    private void setButtonActionForSceneChange(Button button, String key) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.setView(currentScene = sceneSelector(key));
                mainStage.setScene(controller.updateView());
            }
        });
    }

    //Makes a button that goes to the home scene.
    private Button makeBackButton(String buttonName, String sceneName) {

        Button button = new Button(buttonName);
        button.getStyleClass().add("back-btn-icon");
        DropShadow shadow = addDropShadow();

        addMouseHoverEvent(button, shadow);

        setButtonActionForSceneChange(button, sceneName);

        return button;
    }

    //Makes a button that goes to the home scene.
    private Button makeNextButton(String buttonName, String sceneName) {

        Button button = new Button(buttonName);
        button.getStyleClass().add("forward-btn-icon");
        DropShadow shadow = addDropShadow();

        addMouseHoverEvent(button, shadow);

        setButtonActionForSceneChange(button, sceneName);

        return button;
    }

    //Makes a submit button that can have a customized action set
    private Button makeBasicSubmitButton(){
        Button button = new Button("SUBMIT");
        DropShadow shadow = addDropShadow();
        addMouseHoverEvent(button, shadow);

        return button;
    }

    //Helper method for adding multiple buttons to node.
    private void addButtons(VBox container, Button[] buttons) {
        for (Button button : buttons) {
            container.getChildren().add(button);
        }
    }

    //Provides scene to be set by controller.
    private Scene sceneSelector(String sceneName) {

        Scene scene = null;

        switch (sceneName) {
            case "NEW HIKE":
                scene = newTrail();
                break;

            case "SCHEDULED HIKE":
                scene = scheduledHikes();
                break;

            case "EXERCISE PROGRESS":
                scene = exerciseProgress();
                break;

            case "HOME":
                scene = home();
                break;

            case "CHECKLIST":
                scene = checkList();
                break;

            case "HIKE DETAIL":
                scene = hikeDetail();
                break;
            //Might need to be removed
            case "REMINDER MESSAGES":
                scene = reminderMessages();
                break;
        }

        return scene;
    }

    //Creates a shadow effect for button.
    private DropShadow addDropShadow() {
        return new DropShadow(BUTTON_SHADOW_RADIUS, BUTTON_SHADOW_OFFSET, BUTTON_SHADOW_OFFSET, Color.GRAY);
    }

    //Adds mouse events to buttons.
    private void addMouseHoverEvent(Button button, DropShadow shadow) {
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
    private Text titleMaker(String titleName) {
        Text title = new Text(titleName);
        title.getStyleClass().add("title-text");
        return title;
    }

    //Generates text for labels.
    private Label labelMaker(String lableName) {
        Label label = new Label(lableName);
        label.getStyleClass().add("label-text");
        return label;
    }

    //Starting screen.
    private Scene home() {

        VBox menuContainer = new VBox();
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setSpacing(20);
        menuContainer.getStylesheets().add("styles/HikeMasterStyles.css");
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

        return new Scene(menuContainer, WIDTH, HEIGHT);
    }

    //Scene for adding information about user's heart rate and steps taken.
    private Scene exerciseTracker() {

        VBox mainContainer = new VBox();
        mainContainer.getStylesheets().add("styles/HikeMasterStyles.css");
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

        JFXTextField heartRateEntry = new JFXTextField();

//        TextField heartRateEntry = new TextField();

        heartRateEntry.setAlignment(Pos.CENTER);

        heartRateEntry.setMaxWidth(250);


        Label steps = labelMaker("Steps");
        JFXTextField stepsEntry = new JFXTextField();

        stepsEntry.setAlignment(Pos.CENTER);
        stepsEntry.setMaxWidth(250);

        middle.getChildren().addAll(heartRate, heartRateEntry, steps, stepsEntry);

        Button submitButton = makeBasicSubmitButton();
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!heartRateEntry.getText().isEmpty() && !stepsEntry.getText().isEmpty()){
                    controller.addHeartRate(heartRateEntry.getText());
                    controller.addSteps(stepsEntry.getText());
                    heartRateEntry.setText("");
                    stepsEntry.setText("");
                    controller.setView(currentScene = sceneSelector(HOME_SCENE));
                    mainStage.setScene(controller.updateView());
                }else {
                    Alert missingEntry = new Alert(Alert.AlertType.INFORMATION);

                    missingEntry.setHeaderText(null);

                    missingEntry.setContentText("You are missing one or more fields");

                    missingEntry.showAndWait();
                }
            }
        });

        top.getChildren().add(title);
        bottom.getChildren().add(submitButton);

        mainContainer.getChildren().addAll(top, middle, bottom);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //Scene for displaying user's data on heart rate and steps taken.
    private Scene exerciseProgress() {

        String[] stepData = controller.getStepData();
        String[] heartRateData = controller.getHeartRateData();

        VBox mainContainer = new VBox();
        mainContainer.getStylesheets().add("styles/HikeMasterStyles.css");
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

        NumberAxis xMonth = new NumberAxis(JANURARY, DECEMBER, DISPLAY_EACH_MONTH);
        xMonth.setLabel("Month");

        NumberAxis yHeartRate = new NumberAxis(0, 240, 60);
        yHeartRate.setLabel("Heart Rate");

        final LineChart<Number, Number> heartRate = new LineChart<>(xMonth, yHeartRate);

        XYChart.Series<Number, Number> heartCoordinates = new XYChart.Series<>();
        heartCoordinates.setName("Heart Rate per Hike");

        for(int i = 0; i < heartRateData.length; i++){
            heartCoordinates.getData().add(new XYChart.Data<>(i + 1, Integer.parseInt(heartRateData[i])));
        }

        heartRate.getData().add(heartCoordinates);

        NumberAxis xStepMonth = new NumberAxis(JANURARY, DECEMBER, DISPLAY_EACH_MONTH);
        xStepMonth.setLabel("Month");

        NumberAxis ySteps = new NumberAxis(0, 30000, 5000);
        ySteps.setLabel("Steps");

        final LineChart<Number, Number> steps = new LineChart<>(xStepMonth, ySteps);

        XYChart.Series<Number, Number> stepCoordinates = new XYChart.Series<>();
        stepCoordinates.setName("Steps per Hike");

        for (int i = 0; i < stepData.length; i++){
            stepCoordinates.getData().add(new XYChart.Data<>(i + 1, Integer.parseInt(stepData[i])));
        }

        steps.getData().add(stepCoordinates);

        HBox chartBox = new HBox();
        chartBox.setSpacing(15);

        chartBox.getChildren().addAll(heartRate, steps);
        middle.getChildren().add(chartBox);


        Button back = makeBackButton(BACK, HOME_SCENE);
        bottom.getChildren().add(back);

        mainContainer.getChildren().addAll(top, middle, bottom);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //
    private Scene newTrail() {
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(50);
        mainContainer.getStylesheets().add("styles/HikeMasterStyles.css");

        Text title = titleMaker("Select Hike");
        mainContainer.getChildren().add(title);

        JFXComboBox<Label> hikeList = new JFXComboBox<>();
        hikeList.setPromptText("Select a hike or create new");
        hikeList.getItems().add(new Label("New"));


        Button back = makeBackButton(null, HOME_SCENE);

        Button next = makeNextButton(null, HIKE_DETAIL_SCENE);

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        mainContainer.getChildren().addAll(hikeList, buttonRow);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //
    private Scene checkList() {
        VBox vBox = new VBox();
        vBox.getStylesheets().add("styles/HikeMasterStyles.css");
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Text checklistTitle = titleMaker("Checklist");
        vBox.getChildren().add(checklistTitle);

        JFXCheckBox[] boxes = new JFXCheckBox[CHECKLIST.length];

        for (int i = 0; i < CHECKLIST.length; i++) {
            JFXCheckBox box = new JFXCheckBox(CHECKLIST[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        for (int i = 0; i < CHECKLIST.length; i++) {
            final CheckBox box = boxes[i];
            final String listItem = CHECKLIST[i];

            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue == true) {
                        box.setText(listItem + " packed!");
                    } else {
                        box.setText(listItem);
                    }
                }
            });
        }

        Button back = makeBackButton(BACK, HOME_SCENE);
        vBox.getChildren().add(back);

        return new Scene(vBox, WIDTH, HEIGHT);
    }

    //
    private Scene scheduledHikes() {
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(30);
        container.getStylesheets().add("styles/HikeMasterStyles.css");

        Text text = titleMaker("Scheduled Hike");

        JFXListView<Label> list = new JFXListView<>();
        list.setMaxWidth(500);
        for (int i = 0; i < 4; i++) list.getItems().add(new Label("Item " + i));

        Button back = makeBackButton(BACK, HOME_SCENE);

        container.getChildren().addAll(text, list, back);

        return new Scene(container, WIDTH, HEIGHT);
    }

    //
    private Scene reminderMessages() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Text title = titleMaker("Reminder Messages");

        vBox.getChildren().add(title);

        String[] messageList = controller.loadReminderMessages();

        JFXCheckBox[] boxes = new JFXCheckBox[messageList.length];

        for (int i = 0; i < messageList.length; i++) {
            JFXCheckBox box = new JFXCheckBox(messageList[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        Button back = makeBackButton(null, HIKE_DETAIL_SCENE);

        Button next = makeNextButton(null, HOME_SCENE);

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        vBox.getChildren().add(buttonRow);

        Scene listedMessagesScene = new Scene(vBox, WIDTH, HEIGHT);
        listedMessagesScene.getStylesheets().add("styles/HikeMasterStyles.css");

        return listedMessagesScene;
    }

    //
    private Scene hikeDetail() {

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStylesheets().add("styles/HikeMasterStyles.css");

        Text title = titleMaker("Hike Detail");

        VBox inputContainer = new VBox();
        inputContainer.setId("inputContainer");

        JFXTextField locationInput = new JFXTextField();
        locationInput.setPromptText("Location");

        HBox dateContainer = new HBox();
        JFXTextField dateInput = new JFXTextField();
        dateInput.setPromptText("Date");

        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setDefaultColor(Color.valueOf("#3f51b5"));
        datePicker.setMaxWidth(0);

        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dateInput.setText(datePicker.getValue().toString());
            }
        });

        dateContainer.getChildren().addAll(dateInput, datePicker);

        inputContainer.getChildren().addAll(locationInput, dateContainer);

        Button back = makeBackButton(null, SELECT_HIKE_SCENE);

        Button next = makeNextButton(null, REMINDER_MESSAGES_SCENE);

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        mainContainer.getChildren().addAll(title, inputContainer, buttonRow);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }
}