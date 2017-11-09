/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* FitnessView.java
* This class contains all the views of the application.
*
*/

package ui;

import com.jfoenix.controls.*;
import controller.FitnessController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

import java.time.LocalDate;

/**
 * This class provides a GUI for users to be able to interact with the application.
 *
 * @author Daniel Capps, Joshua Hawks, Hanchen Liu
 * @version 1.0
 */
public class FitnessView extends Application
{
    //Constants.
    private static final int INITIALIZING_VALUE = 0;
    private static final int WIDTH = 950;
    private static final int HEIGHT = 630;
    private static final int EXERCISE_TRACKER_BODY_HEIGHT = 430;
    private static final String BACK = "BACK";
    private static final String HOME_SCENE = "HOME";
    private static final String SELECT_HIKE_SCENE = "NEW HIKE";
    private static final String HIKE_DETAIL_SCENE = "HIKE DETAIL";
    private static final String REMINDER_MESSAGES_SCENE = "REMINDER MESSAGES";
    private static final String CHECKLIST_SCENE = "CHECKLIST";
    private static final String SCHEDULED_HIKE_SCENE = "SCHEDULED HIKE";
    private static final String EXERCISE_PROGRESS_SCENE = "EXERCISE PROGRESS";
    private static final String EXERCISE_TRACKER_SCENE = "EXERCISE TRACKER";
    private static final String BINNED_REMINDER_MESSAGE_SCENE = "BINNED_REMINDER_MESSAGE_SCENE";
    private static final String MAKE_REMINDER_MESSAGES_SCENE = "CREATE REMINDERS";
    private static final int EXERCISE_TRACKER_WIDTH = 500;
    private static final int EXERCISE_TRACKER_HEADER_FOOTER = 100;
    private static final int BUTTON_SHADOW_RADIUS = 1;
    private static final int BUTTON_SHADOW_OFFSET = 2;
    private static final int JANUARY = 1;
    private static final int DISPLAY_EACH_MONTH = 1;
    private static final int DECEMBER = 12;
    private static final int TEXTFIELD_MAX_WIDTH = 250;
    private static final String FIELD_MISSING_ERROR = "You are missing one or more fields";
    private static final String HIKE_CREATED_MESSAGE = "Hike Created! You can view upcoming hikes in the Scheduled Hike page.";
    private static final String PROGRAM_TITLE = "Hike Master 9000";

    //Checklist needs to be implemented into the scheduled hike scene.
    //Each checklist must correspond to the hike that owns that checklist.
    private final String[] BUTTON_NAMES_FOR_HOME = {"NEW HIKE", "SCHEDULED HIKE", "EXERCISE PROGRESS", "CREATE REMINDERS"};
    private static final String[] CHECKLIST = new String[]{"backpack", "binoculars", "flashlight", "compass", "rain coat", "map", "food", "water"};

    //Scene object to hold current scene.
    private Scene currentScene;

    //A copy of the stage parameter in the start method.
    private Stage mainStage;

    //temporary location holder
    private Label tempLocationHolder;

    //temporary holding today's date
    private LocalDate today = LocalDate.now();

    //Controller to perform necessary actions.
    private FitnessController controller = new FitnessController(defaultStartingScene());
    private String temporaryDateHolder;

    //VBoxes for container template.
    private VBox container;
    private VBox top;
    private VBox middle;
    private VBox bottom;

    //Initial scene setup for application.
    private Scene defaultStartingScene()
    {
        return currentScene = home();
    }

    /**
     * Starts the application.
     *
     * @param stage A parameter that provides access to a set of methods for starting the application.
     */
    @Override
    public void start(Stage stage)
    {
        //field that is a copy of the stage parameter.
        mainStage = stage;

        //Sets the title of the application.
        stage.setTitle(PROGRAM_TITLE);

        //Sets the application icon.
        stage.getIcons().add(new Image("images/leafIcon32x33.jpg"));

        //Sets the scene for the stage.
        stage.setScene(currentScene);

        //Makes it so the user may not resize the application window.
        stage.setResizable(false);

        //Displays the stage.
        stage.show();

        //method that show the alert for reminders.
        popUpMessage();
    }

    //Launches a pop to display reminder messages that are associated with the current date.
    private void popUpMessage()
    {
        //holds an array of hike dates.
        String[] dates = controller.getHikeDates();

        //Conditional that will display an alert if there is a reminder that needs to be displayed for that date.
        if (dates == null)
        {
            defaultStartingScene();
        }
        else
        {
            //uses a for loop to go through both arrays
            for (int i = 0; i < dates.length; i++)
            {
                //parse the date as a local date object
                LocalDate date = LocalDate.parse(dates[i]);

                //if the date is today or is after today
                //then display the hike info
                if (date.equals(today))
                {
                    String messageHolder = "";
                    for (String message : controller.getReminderMessageBasedOnDate(dates[i]))
                    {
                        messageHolder = messageHolder + message + "\n";
                    }
                    displayAlertWindow(messageHolder);
                }
            }
        }
    }

    //Makes an array of buttons
    private Button[] makeHomeButtons(String[] name)
    {
        //An array to hold buttons.
        Button[] buttons = new Button[name.length];

        //Applies a shadow to the buttons.
        DropShadow shadow = addDropShadow();

        //A loop to make the actual button.
        for (int i = INITIALIZING_VALUE; i < name.length; i++)
        {
            buttons[i] = new Button(name[i].toUpperCase());
            buttons[i].getStyleClass().add("home-btn");

            //Adds a mouse hovering event.
            addMouseHoverEvent(buttons[i], shadow);

            //Sets the action for the button.
            setButtonActionForSceneChange(buttons[i], name[i]);
        }

        return buttons;
    }

    //Sets the action for buttons to change to the correct scene.
    private void setButtonActionForSceneChange(Button button, String key)
    {
        button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //Sets controller to hold the new scene.
                controller.setView(currentScene = sceneSelector(key));

                //Updates the current scene with the current set scene.
                mainStage.setScene(controller.updateView());
            }
        });
    }

    //Sets the action for buttons to change to the correct scene.
    private void setButtonActionForSceneChange(Button button, String key, String date)
    {
        button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                controller.setHikeDate(date);
                controller.setView(currentScene = sceneSelector(key));
                mainStage.setScene(controller.updateView());
            }
        });
    }

    //Makes a button that goes to the home scene.
    private Button makeBackButton(String buttonName, String sceneName)
    {
        Button button = new Button(buttonName);

        //add a icon to the button.
        button.getStyleClass().add("back-btn-icon");

        //Adds shadow to the button.
        DropShadow shadow = addDropShadow();

        addMouseHoverEvent(button, shadow);

        setButtonActionForSceneChange(button, sceneName);

        return button;
    }

    //Makes a button that goes to the home scene.
    private Button makeNextButton(String sceneName)
    {
        Button button = new Button();
        button.getStyleClass().add("forward-btn-icon");
        DropShadow shadow = addDropShadow();

        addMouseHoverEvent(button, shadow);

        setButtonActionForSceneChange(button, sceneName);

        return button;
    }

    //Makes a submit button that can have a customized action set
    private Button makeBasicButton(String buttonName)
    {
        Button button = new Button(buttonName);
        DropShadow shadow = addDropShadow();
        addMouseHoverEvent(button, shadow);

        return button;
    }

    //Helper method for adding multiple buttons to node.
    private void addButtons(VBox container, Button[] buttons)
    {
        //adds buttons
        for (Button button : buttons)
        {
            container.getChildren().add(button);
        }
    }

    //Provides scene to be set by controller.
    private Scene sceneSelector(String sceneName)
    {

        //Scene to be returned.
        Scene scene = null;

        //Assigns the scene that is to be returned.
        switch (sceneName)
        {
            case HOME_SCENE:
                scene = home();
                break;

            case SELECT_HIKE_SCENE:
                scene = newTrail();
                break;

            case SCHEDULED_HIKE_SCENE:
                scene = scheduledHikes();
                break;

            case EXERCISE_TRACKER_SCENE:
                scene = exerciseTracker();
                break;

            case EXERCISE_PROGRESS_SCENE:
                scene = exerciseProgress();
                break;

            case HIKE_DETAIL_SCENE:
                scene = hikeDetail();
                break;

            case CHECKLIST_SCENE:
                scene = checkList();
                break;

            //Might need to be removed
            case REMINDER_MESSAGES_SCENE:
                scene = reminderMessages();
                break;

            case BINNED_REMINDER_MESSAGE_SCENE:
                scene = uniqueReminderList();
                break;

            case MAKE_REMINDER_MESSAGES_SCENE:
                scene = makeReminderMessages();
                break;
        }

        return scene;
    }

    //Creates a shadow effect for button.
    private DropShadow addDropShadow()
    {
        return new DropShadow(BUTTON_SHADOW_RADIUS, BUTTON_SHADOW_OFFSET, BUTTON_SHADOW_OFFSET, Color.GRAY);
    }

    //Adds mouse events to buttons.
    private void addMouseHoverEvent(Button button, DropShadow shadow)
    {
        //Provides an event handler for when the mouse is hovering over the object.
        EventHandler<MouseEvent> mouseOverEventEventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                button.setEffect(shadow);
            }
        };

        //Provides and event handler for when the mouse is not hovering the object.
        EventHandler<MouseEvent> mouseOffEventEventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                button.setEffect(null);
            }
        };

        button.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseOverEventEventHandler);
        button.addEventFilter(MouseEvent.MOUSE_EXITED, mouseOffEventEventHandler);
    }

    //Generates text for title headings.
    private Text titleMaker(String titleName)
    {
        //Makes a title.
        Text title = new Text(titleName);

        //Provides a style for the title.
        title.getStyleClass().add("title-text");
        return title;
    }

    //Generates text for labels.
    private Label labelMaker(String labelName)
    {
        //Makes a label
        Label label = new Label(labelName);

        //Provides a style for the label.
        label.getStyleClass().add("label-text");
        return label;
    }

    //Starting screen.
    private Scene home()
    {
        //Holds the elements of the scene.
        VBox menuContainer = new VBox();
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setSpacing(20);
        menuContainer.getStylesheets().add("styles/HikeMasterStyles.css");
        menuContainer.setId("home-image");

        //Holds the title of the scene.
        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(50));

        Text titleText = titleMaker(PROGRAM_TITLE);

        //Provides a reflection for the title.
        Reflection reflection = new Reflection();
        reflection.getFraction();
        titleText.setEffect(reflection);

        title.getChildren().add(titleText);

        menuContainer.getChildren().add(title);

        Button[] buttons = makeHomeButtons(BUTTON_NAMES_FOR_HOME);

        //Provides an id to assign a unique icon to each button.
        buttons[0].setId("new-hike-btn-icon");
        buttons[1].setId("scheduled-btn-icon");
        buttons[2].setId("exercise-progress-btn-icon");
        buttons[3].setId("new-reminder-message-btn-icon");

        addButtons(menuContainer, buttons);

        return new Scene(menuContainer, WIDTH, HEIGHT);
    }

    //Scene for adding information about user's heart rate and steps taken.
    private Scene exerciseTracker()
    {
        //Provides a template form.
        vboxSceneTemplate();

        //Title for the scene
        Text title = titleMaker("Exercise Tracker");

        //Label for the heart rate.
        Label heartRate = labelMaker("Heart Rate");

        //Jfoenix textfield for the heart rate.
        JFXTextField heartRateEntry = new JFXTextField();

        heartRateEntry.setAlignment(Pos.CENTER);

        heartRateEntry.setMaxWidth(TEXTFIELD_MAX_WIDTH);

        //Label for steps taken.
        Label steps = labelMaker("Steps");

        //Textfield that takes in the input for steps taken.
        JFXTextField stepsEntry = new JFXTextField();

        stepsEntry.setAlignment(Pos.CENTER);
        stepsEntry.setMaxWidth(TEXTFIELD_MAX_WIDTH);

        middle.getChildren().addAll(heartRate, heartRateEntry, steps, stepsEntry);

        //A button for submitting the textfield inputs.
        Button submitButton = makeBasicButton("SUBMIT");
        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //Adds heart rate and step data to the xml and moves onto the next scene.
                //If it's false, then it pops up an alert that says that they are missing inputs.
                if (!heartRateEntry.getText().isEmpty() && !stepsEntry.getText().isEmpty())
                {
                    //Inserts data into the xml
                    controller.addHeartRate(heartRateEntry.getText(), controller.getDate());
                    controller.addSteps(stepsEntry.getText(), controller.getDate());

                    //clears the textfields.
                    heartRateEntry.setText("");
                    stepsEntry.setText("");

                    //Sets and updates the scene.
                    controller.setView(currentScene = sceneSelector(SCHEDULED_HIKE_SCENE));
                    mainStage.setScene(controller.updateView());
                }
                else
                {
                    //Alert
                    displayAlertWindow(FIELD_MISSING_ERROR);
                }
            }
        });

        top.getChildren().add(title);
        bottom.getChildren().add(submitButton);

        container.getChildren().addAll(top, middle, bottom);

        return new Scene(container, WIDTH, HEIGHT);
    }

    //Scene for displaying user's data on heart rate and steps taken.
    private Scene exerciseProgress()
    {
        //Used to hold the calculated data for the graphs.
        int[] stepData;
        int[] heartRateData;

        //Checks to see if the value is present or null.
        if (controller.hasChartValues())
        {
            stepData = controller.getStepData();
            heartRateData = controller.getHeartRateData();
        }
        else
        {
            //provides dummy data.
            stepData = new int[12];
            heartRateData = new int[12];
            for (int i = 0; i < stepData.length; i++)
            {
                stepData[i] = 0;
                heartRateData[i] = 0;
            }
        }

        vboxSceneTemplate();

        //Scene Title
        Text title = titleMaker("Exercise Progress");
        top.getChildren().add(title);

        //Month
        NumberAxis xMonth = new NumberAxis(JANUARY, DECEMBER, DISPLAY_EACH_MONTH);
        xMonth.setLabel("Month");

        //Heart rate
        NumberAxis yHeartRate = new NumberAxis(0, 240, 60);
        yHeartRate.setLabel("Heart Rate");

        //The line chart for user's heart rate.
        final LineChart<Number, Number> heartRate = new LineChart<>(xMonth, yHeartRate);

        //Holds the coordinates for heart rate
        XYChart.Series<Number, Number> heartCoordinates = new XYChart.Series<>();

        //Tells what the line represents.
        heartCoordinates.setName("Average Heart Rate per Hike");

        //Assigns coordinates for the heart rate.
        for (int i = 0; i < 12; i++)
        {
            heartCoordinates.getData().add(new XYChart.Data<>(i + 1, heartRateData[i]));
        }

        heartRate.getData().add(heartCoordinates);

        //Month
        NumberAxis xStepMonth = new NumberAxis(JANUARY, DECEMBER, DISPLAY_EACH_MONTH);
        xStepMonth.setLabel("Month");

        //The number of steps the user has taken.
        NumberAxis ySteps = new NumberAxis(0, 30000, 5000);
        ySteps.setLabel("Steps");

        //Line chart for the steps the user has taken.
        final LineChart<Number, Number> steps = new LineChart<>(xStepMonth, ySteps);

        //Holds coordinates for steps.
        XYChart.Series<Number, Number> stepCoordinates = new XYChart.Series<>();
        stepCoordinates.setName("Average Steps per Hike");

        //Assigns coordinates
        for (int i = 0; i < stepData.length; i++)
        {
            stepCoordinates.getData().add(new XYChart.Data<>(i + 1, stepData[i]));
        }

        steps.getData().add(stepCoordinates);

        HBox chartBox = new HBox();
        chartBox.setSpacing(15);

        chartBox.getChildren().addAll(heartRate, steps);
        middle.getChildren().add(chartBox);
        middle.setMaxWidth(WIDTH);
        middle.setMinWidth(WIDTH);


        Button back = makeBackButton(BACK, HOME_SCENE);
        bottom.getChildren().add(back);

        container.getChildren().addAll(top, middle, bottom);

        return new Scene(container, WIDTH, HEIGHT);
    }

    //scene for user to select or create a trail
    private Scene newTrail()
    {
        //Holds the elements of the scene
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(50);
        mainContainer.getStylesheets().add("styles/HikeMasterStyles.css");

        //Scene title.
        Text selectHikeTitle = titleMaker("Select Hike");
        mainContainer.getChildren().add(selectHikeTitle);

        Label userNote = new Label("Select a hike or create new");

        //Box that holds the values of new or previous hiking locations
        JFXComboBox<Label> hikeList = new JFXComboBox<>();
        hikeList.setMinWidth(200);

        Label newHike = new Label("New");
        hikeList.getItems().add(newHike);
        hikeList.setValue(newHike);

        String[] locations = controller.getHikeLocations();

        //Assigns locations.
        if (locations != null)
        {
            for (String location : locations)
            {
                hikeList.getItems().add(new Label(location));
            }
        }

        Button back = makeBackButton(null, HOME_SCENE);

        Button next = makeNextButton(HIKE_DETAIL_SCENE);

        //Sets and action for holding the location.
        next.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (!hikeList.getValue().getText().equals("New"))
                {
                    tempLocationHolder = hikeList.getValue();
                }
                else
                {
                    tempLocationHolder = new Label("");
                }
            }
        });

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        mainContainer.getChildren().addAll(userNote, hikeList, buttonRow);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //scene for user to input trail information
    private Scene hikeDetail()
    {
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStylesheets().add("styles/HikeMasterStyles.css");

        Text title = titleMaker("Hike Detail");

        VBox inputContainer = new VBox();
        inputContainer.setId("inputContainer");

        //uses a jfoenix text field to take user input on location
        JFXTextField locationInput = new JFXTextField();
        locationInput.setPromptText("Location");

        //if user selected a drop down item that is not New from the select hike scene
        //assign the text of location input field to whatever the user selected
        if (!tempLocationHolder.getText().equals(""))
        {
            locationInput.setText(tempLocationHolder.getText());
            tempLocationHolder.setText("");
        }

        HBox dateContainer = new HBox();

        //uses a jfoenix text field to take user input on date
        JFXTextField dateInput = new JFXTextField();
        dateInput.setPromptText("Date");

        //uses a jfoenix date picker for assisting user to choose a hike date
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setDefaultColor(Color.valueOf("#3f51b5"));
        datePicker.setMaxWidth(0);

        //set the date input field text to whatever the date user picked from the date picker
        datePicker.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                dateInput.setText(datePicker.getValue().toString());
            }
        });

        dateContainer.getChildren().addAll(dateInput, datePicker);

        inputContainer.getChildren().addAll(locationInput, dateContainer);

        //back button to go back to select hike scene
        Button back = makeBackButton(null, SELECT_HIKE_SCENE);

        //next button to go to reminder messages scene
        Button next = makeNextButton(REMINDER_MESSAGES_SCENE);

        //check if the input fields are filled or not when pressing the next button
        next.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                // if both input fields are not empty, set the location and date to a hike object
                if (!locationInput.getText().isEmpty() && !dateInput.getText().isEmpty())
                {
                    controller.setHikeLocation(locationInput.getText());
                    controller.setHikeDate(dateInput.getText());
                }

                //display an alert
                else
                {
                    displayAlertWindow(FIELD_MISSING_ERROR);
                }
            }
        });

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        mainContainer.getChildren().addAll(title, inputContainer, buttonRow);

        return new Scene(mainContainer, WIDTH, HEIGHT);
    }

    //scene for display scheduled hikes that is coming up
    private Scene scheduledHikes()
    {
        //Template structure
        vboxSceneTemplate();

        //creates a scroll pane to make all hike info viewable when overflow
        ScrollPane windowScroller = new ScrollPane();
        VBox subContainer = new VBox();
        subContainer.setAlignment(Pos.CENTER);
        subContainer.setSpacing(30);
        windowScroller.getStylesheets().add("styles/HikeMasterStyles.css");

        //always show a vertical scroller
        windowScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        windowScroller.setContent(subContainer);

        //set scene title
        Text scheduledHikeTitle = titleMaker("Scheduled Hikes");

        //uses one string array to store location data and another string array to store dates
        String[] locations = controller.getScheduledHikes();
        String[] dates = controller.getHikeDates();

        //uses a for loop to go through both arrays
        for (int i = 0; i < dates.length; i++)
        {
            //parse the date as a local date object
            LocalDate date = LocalDate.parse(dates[i]);

            //if the date is today or is after today
            //then display the hike info
            if (date.equals(today) || date.isAfter(today))
            {
                HBox hikeRow = new HBox();
                hikeRow.setId("hikeRow");

                //display the date and the location of one hike
                Label hikeDate = new Label("Date: " + dates[i]);
                Label hikeLocation = new Label("Location: " + locations[i]);

                String fullDate = dates[i];

                String month = fullDate.substring(5, 7);

                controller.heartRateAndStepsOrganizer(month, fullDate);

                //creates a check list button that brings up a check list
                Button checkListButton = new Button("Check List");
                setButtonActionForSceneChange(checkListButton, CHECKLIST_SCENE);

                //creates a reminder message button that brings up the associated reminder messages of the hike
                Button reminderMessageButton = new Button("Reminder Messages");
                setButtonActionForSceneChange(reminderMessageButton, BINNED_REMINDER_MESSAGE_SCENE);

                //creates a temporary index that is going to be passed into the event handler
                final int index = i;

                //set the temporary date holder to the date at this index
                reminderMessageButton.setOnMousePressed(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        temporaryDateHolder = dates[index];
                    }
                });

                //creates an add exercise data button that allows user to input heart rate and steps of the hike
                Button addExerciseDataButton = new Button("Add Exercise Data");
                setButtonActionForSceneChange(addExerciseDataButton, EXERCISE_TRACKER_SCENE, dates[i]);

                hikeRow.getChildren().addAll(hikeDate, hikeLocation, checkListButton, reminderMessageButton, addExerciseDataButton);

                subContainer.getChildren().add(hikeRow);
            }
        }

        //back button that brings user to home scene
        Button back = makeBackButton(BACK, HOME_SCENE);

        //Adds the children to their proper nodes.
        top.getChildren().add(scheduledHikeTitle);
        middle.getChildren().add(windowScroller);
        middle.setMinWidth(WIDTH);
        middle.setMaxWidth(WIDTH);
        bottom.getChildren().add(back);
        container.getChildren().addAll(top, middle, bottom);

        return new Scene(container, WIDTH, HEIGHT);
    }

    //Provides a list of reminders for the user during the creation of a new hike.
    private Scene reminderMessages()
    {
        //main container for all the elements of the page.
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        //The title of the scene.
        Text title = titleMaker("Reminder Messages");

        vBox.getChildren().add(title);

        //stores the values for the reminder message.
        String[] messageList = controller.loadReminderMessages();

        //Check boxes using the Jfoenix library.
        JFXCheckBox[] boxes = new JFXCheckBox[messageList.length];

        //Loops through the message list to make viewable checkboxes.
        for (int i = 0; i < messageList.length; i++)
        {
            JFXCheckBox box = new JFXCheckBox(messageList[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        //set up a list for storing reminder messages
        controller.setHikeReminderMessages(messageList.length);

        //check the state of the check boxes
        for (int i = 0; i < messageList.length; i++)
        {
            final CheckBox box = boxes[i];

            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    //if the box is checked, add the reminder message to list
                    if (newValue)
                    {
                        controller.addHikeReminderMessage(box.getText());
                    }

                    //else if the box is not checked or changed from checked to unchecked
                    //remove the reminder message from list
                    else
                    {
                        controller.removeHikeReminderMessage(box.getText());
                    }
                }
            });
        }

        //Goes back to the previous scene.
        Button back = makeBackButton(null, HIKE_DETAIL_SCENE);

        //Goes back to the starting scene.
        Button next = makeNextButton(HOME_SCENE);

        // when the next button is being pressed, it will add the hike info and reminder messages to the xml file
        next.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                //adds the hike info to the file
                controller.addHike();

                //adds the associated reminder messages all together with the hike info
                controller.addReminderMessageToHike();
            }
        });

        //when the next button is being pressed and released, it will inform user the hike is created
        next.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                //use a alert window to inform user the hike is created
                displayAlertWindow(HIKE_CREATED_MESSAGE);
            }
        });

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        buttonRow.getChildren().addAll(back, next);

        vBox.getChildren().add(buttonRow);

        //Scene to be returned.
        Scene listedMessagesScene = new Scene(vBox, WIDTH, HEIGHT);

        //Style for the scene.
        listedMessagesScene.getStylesheets().add("styles/HikeMasterStyles.css");

        return listedMessagesScene;
    }

    //Provides a checklist that users can work with ensure that they have all stuff checked off.
    private Scene checkList()
    {
        //main container for the elements of the scene.
        VBox vBox = new VBox();
        vBox.getStylesheets().add("styles/HikeMasterStyles.css");
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        //Title for the scene.
        Text checklistTitle = titleMaker("Checklist");
        vBox.getChildren().add(checklistTitle);

        JFXCheckBox[] boxes = new JFXCheckBox[CHECKLIST.length];

        //Assigns a checklist item to a check box object.
        for (int i = 0; i < CHECKLIST.length; i++)
        {
            JFXCheckBox box = new JFXCheckBox(CHECKLIST[i]);
            boxes[i] = box;
            box.setPrefWidth(200);
        }

        vBox.getChildren().addAll(boxes);

        //Appends or removes the "packed" term for each checklist item.
        for (int i = 0; i < CHECKLIST.length; i++)
        {
            final CheckBox box = boxes[i];
            final String listItem = CHECKLIST[i];

            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    if (newValue)
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

        Button back = makeBackButton(BACK, SCHEDULED_HIKE_SCENE);
        vBox.getChildren().add(back);

        return new Scene(vBox, WIDTH, HEIGHT);
    }

    //Displays a unique list of  reminders for any given hike.
    private Scene uniqueReminderList()
    {
        //Holds all the elements of the scene.
        VBox reminderContainer = new VBox();
        reminderContainer.setAlignment(Pos.CENTER);
        reminderContainer.getStylesheets().add("styles/HikeMasterStyles.css");
        reminderContainer.setPadding(new Insets(10));
        reminderContainer.setSpacing(10);

        Text reminderTitle = titleMaker("Reminders");
        reminderContainer.getChildren().add(reminderTitle);

        //gets all reminder messages based on the hike date
        String[] messageList = controller.getReminderMessageBasedOnDate(temporaryDateHolder);

        ListView listedMessages = new ListView();

        listedMessages.getItems().addAll(FXCollections.observableArrayList(messageList));

        reminderContainer.getChildren().add(listedMessages);

        Button back = makeBackButton(BACK, SCHEDULED_HIKE_SCENE);

        reminderContainer.getChildren().add(back);

        return new Scene(reminderContainer, WIDTH, HEIGHT);
    }

    //Adds new custom reminder messages to the xml data.
    private Scene makeReminderMessages()
    {
        //Template structure for scene.
        vboxSceneTemplate();

        //Title for scene
        Text reminderTitle = titleMaker("New Reminder Message");

        //Textfield for user input
        JFXTextField reminderMessageInput = new JFXTextField();

        //A prompt in the test field to entice users to enter a new message.
        reminderMessageInput.setPromptText("Enter new reminder message");

        //Sets the width of the textfield
        reminderMessageInput.setMaxWidth(TEXTFIELD_MAX_WIDTH);
        reminderMessageInput.setMinWidth(TEXTFIELD_MAX_WIDTH);

        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.CENTER);

        //A button to just go back to the home scene.
        Button cancel = makeBasicButton("CANCEL");
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                controller.setView(currentScene = sceneSelector(HOME_SCENE));
                mainStage.setScene(controller.updateView());
            }
        });

        //Adds the new message to the xml file and then goes to the home scene.
        Button submit = makeBasicButton("SUBMIT");
        submit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //Adds message to xml file
                controller.addNewReminderMessage(reminderMessageInput.getText());

                //sets and updates to new scene.
                controller.setView(currentScene = sceneSelector(HOME_SCENE));
                mainStage.setScene(controller.updateView());
            }
        });

        //Assigns children to their parent nodes.
        top.getChildren().add(reminderTitle);
        buttonContainer.getChildren().addAll(cancel, submit);
        middle.getChildren().add(reminderMessageInput);
        bottom.getChildren().add(buttonContainer);
        container.getChildren().addAll(top, middle, bottom);

        return new Scene(container, WIDTH, HEIGHT);

    }

    //A template to be used to help make the scenes more uniform.
    private void vboxSceneTemplate()
    {
        //The parent that holds all the separate children parts for the scene.
        container = new VBox();
        container.getStylesheets().add("styles/HikeMasterStyles.css");
        container.setAlignment(Pos.TOP_CENTER);
        container.setMaxSize(EXERCISE_TRACKER_WIDTH, HEIGHT);

        //Handles the upper third of the window
        top = new VBox();
        top.setAlignment(Pos.TOP_CENTER);
        top.setMaxSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        top.setMinSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        top.getStyleClass().add("window-top");

        //Handles the middle section of the window
        middle = new VBox();
        middle.setAlignment(Pos.CENTER);
        middle.setSpacing(10);
        middle.setMaxSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_BODY_HEIGHT);
        middle.setMinSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_BODY_HEIGHT);
        middle.getStyleClass().add("window-mid");

        //Handles the elements of the lower third of the window.
        bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setMaxSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        bottom.setMinSize(EXERCISE_TRACKER_WIDTH, EXERCISE_TRACKER_HEADER_FOOTER);
        bottom.getStyleClass().add("window-bot");
    }

    //this method will display an alert window
    private void displayAlertWindow(String alertMessage)
    {
        //Makes an alert object.
        Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);

        //Alert Title
        alertWindow.setTitle(PROGRAM_TITLE);

        //Sets the icon image for the alert.
        ((Stage)alertWindow.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/leafIcon32x33.jpg"));

        //Removes the alert header
        alertWindow.setHeaderText(null);

        //Alert message
        alertWindow.setContentText(alertMessage);

        //Displays alert and awaits user action.
        alertWindow.showAndWait();
    }
}