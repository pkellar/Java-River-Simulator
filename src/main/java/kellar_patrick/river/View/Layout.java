package kellar_patrick.river.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import kellar_patrick.river.Controller.Controller;
import kellar_patrick.river.Model.RiverSim;

import java.io.IOException;

/*
 * Author: Patrick Kellar
 * Description: Class that will set up and contain the view information
 * */
public class Layout {
    private RiverSimView riverSimView;
    private final BorderPane borderPane = new BorderPane();
    private final ToggleGroup landRadioGroup = new ToggleGroup();
    private final CheckBox landCheckBox = new CheckBox("Add");
    private final Button nextMonthBtn = new Button("Next Month");
    private final Text riverInfoBar = new Text();
    private Text landInfoText;
    private final Button[] resizeButtons = new Button[3];
    private final Controller controller;
    private final RadioButton recRadio = new RadioButton("Recreation");
    private final RadioButton unRadio = new RadioButton("Unused");
    private final RadioButton agRadio = new RadioButton("Agriculture");


    //Constructor, will create a new riverSimView and controller, while also setting up the resize button info
    public Layout() throws IOException {
        RiverSim riverSim = new RiverSim();
        this.riverSimView = new RiverSimView(riverSim);

        this.resizeButtons[0] = new Button("5x3");
        this.resizeButtons[0].setId("5x3");
        this.resizeButtons[1] = new Button("7x5");
        this.resizeButtons[1].setId("7x5");
        this.resizeButtons[2] = new Button("9x7");
        this.resizeButtons[2].setId("9x7");

        this.controller = new Controller(riverSim, this);
    }

    /*
     * Description: Will create and set the nodes needed for the action grid on the right of the border pane
     *
     * @return   actionGrid - The new GridPane created that has the next month button, the radio buttons,
     *  the add checkbox, and the resize buttons
     * */
    private GridPane createActionGrid() {
        GridPane actionGrid = new GridPane();
        //actionGrid.setGridLinesVisible(true);

        //Set column width to 100%
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        actionGrid.getColumnConstraints().add(columnConstraints);

        //Initialize row constraints
        RowConstraints nextMonthRow = new RowConstraints();
        RowConstraints radioRow = new RowConstraints();
        RowConstraints checkBoxRow = new RowConstraints();
        RowConstraints resizeRow = new RowConstraints();

        //The radioRow needs a larger % since its height is naturally taller than the rest in the action grid
        nextMonthRow.setPercentHeight(50);
        radioRow.setPercentHeight(80);
        checkBoxRow.setPercentHeight(50);
        resizeRow.setPercentHeight(50);

        //Add the row constraints to actionGrid
        actionGrid.getRowConstraints().addAll(nextMonthRow, radioRow, checkBoxRow, resizeRow);

        //Add the next month button to the top center of a horizontal box
        HBox nextMonthHBox = new HBox();
        nextMonthHBox.getChildren().add(nextMonthBtn);
        nextMonthHBox.setAlignment(Pos.TOP_CENTER);

        //Radio Button Config, put into vertical box
        VBox radioVBox = new VBox();

        //So the agriculture option is selected at start
        agRadio.setSelected(true);

        //Add radio buttons to group so only one selected at a time
        agRadio.setToggleGroup(landRadioGroup);
        recRadio.setToggleGroup(landRadioGroup);
        unRadio.setToggleGroup(landRadioGroup);

        disableRadio();

        radioVBox.getChildren().addAll(agRadio, recRadio, unRadio);
        radioVBox.setAlignment(Pos.CENTER); //Center the radio buttons

        //Check Box Config, put into a horizontal box
        controller.setCheckBoxListener(landCheckBox);
        landCheckBox.setSelected(false);
        HBox checkHBox = new HBox(landCheckBox);
        checkHBox.setAlignment(Pos.CENTER); //Center the checkbox

        //Resize Box Config, make new grid pane for resize options on the bottom of the actionGrid
        GridPane resizeGrid = new GridPane();

        //Make 2 columns for the "resize" text, and the dimension buttons
        ColumnConstraints textCol = new ColumnConstraints();
        textCol.setPercentWidth(100);

        ColumnConstraints dimensionsCol = new ColumnConstraints();
        dimensionsCol.setPercentWidth(100);
        resizeGrid.getColumnConstraints().addAll(textCol, dimensionsCol);

        //Set the resizeGrid to expand the length of the right col
        RowConstraints innerResizeRow = new RowConstraints();
        innerResizeRow.setPercentHeight(100);
        resizeGrid.getRowConstraints().add(innerResizeRow);

        //Make horizontal box that will contain the text box, set position to the bottom left
        HBox resizeTextHBox = new HBox();
        resizeTextHBox.getChildren().add(new Text("Resize"));
        resizeTextHBox.setAlignment(Pos.BOTTOM_LEFT);

        //Make horizontal box that will contain the 3 buttons with the resize options
        HBox resizeButtonHBox = new HBox();
        resizeButtonHBox.getChildren().addAll(this.resizeButtons[0], this.resizeButtons[1], this.resizeButtons[2]);
        resizeButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);

        //Add in the two horizontal boxes just created into the resizeGrid
        resizeGrid.add(resizeTextHBox, 0,0);
        resizeGrid.add(resizeButtonHBox, 1,0);

        //Add the different nodes containing the action buttons into their respective spots on the actionGrid
        actionGrid.add(nextMonthHBox, 0,0);
        actionGrid.add(radioVBox, 0,1);
        actionGrid.add(checkHBox, 0, 2);
        actionGrid.add(resizeGrid, 0, 3);

        return actionGrid;
    }

    /*
     * Description: Will create and set the river info bar at bottom of border pane
     *
     * @return   riverInfo - new horizontal box that has river info centered
     * */
    public HBox createBottomText() {
        HBox riverInfo = new HBox();
        // Get the current info of the RiverSim and set it as the views current river info
        riverInfoBar.setText(riverSimView.getRiverSimInfo());

        //Set river info in HBox, centered
        riverInfo.setAlignment(Pos.CENTER);
        riverInfo.getChildren().add(riverInfoBar);

        return riverInfo;
    }

    /*
     * Description: Will create and set the land information in the top right of the application
     *
     * @return   landInfo - new horizontal box that has the currently selected land's info in it
     * */
    private HBox createLandInfo() {
        //Make new horizontal box
        HBox landInfo = new HBox();

        //Can set initial land info here since all tiles have this info at the start
        landInfoText = new Text("""
                Unused
                Last changed: 0-1
                Age: 0-1
                Total Cost: $0k
                Total Revenue: $0k
                Land Id: 01""");

        //Set Land Info display, centered
        landInfo.setAlignment(Pos.CENTER);
        landInfo.getChildren().add(landInfoText);

        return landInfo;
    }

    /*
     * Description: Will create and set the GridPane that is holding the landInfo HBox and the actionGrid GridPane
     *
     * @return   rightGrid - new GridPane that is contains the completed landInfo and the action buttons in the correct
     *  positions
     * */
    private GridPane createRightGrid() {
        //Pane for right of the BorderPane
        GridPane rightGrid = new GridPane();
        //rightGrid.setGridLinesVisible(true);

        //Set row constraints, want a 1:1 ratio
        RowConstraints landInfoRow = new RowConstraints();
        landInfoRow.setPercentHeight(50);

        RowConstraints actionGridRow = new RowConstraints();
        actionGridRow.setPercentHeight(50);

        rightGrid.getRowConstraints().addAll(landInfoRow, actionGridRow);

        //Create Land Information
        HBox landInfo = createLandInfo();

        //Create Action Grid
        GridPane actionGrid = createActionGrid();

        //Add landInfo and actionCommands to the grid
        rightGrid.add(landInfo,0,0); //top
        rightGrid.add(actionGrid,0,1); //bottom

        return rightGrid;
    }

    /*
     * Description: will disable the radio buttons
     * */
    public void disableRadio() {
        agRadio.setDisable(true);
        recRadio.setDisable(true);
        unRadio.setDisable(true);
    }

    /*
     * Description: will enable the radio buttons
     * */
    public void enableRadio() {
        agRadio.setDisable(false);
        recRadio.setDisable(false);
        unRadio.setDisable(false);
    }

    public Button[] getResizeButtons() {
        return resizeButtons;
    }

    /*
     * Description: Returns text of selected radio button
     *
     * @return   string of selected radio button text
     * */
    public String getSelectedRadio() {
        return ((RadioButton) landRadioGroup.getSelectedToggle()).getText();
    }

    public TileView[][] getTileViews() {
        return riverSimView.getTileViews();
    }
    public Button getNextMonthBtn() {
        return nextMonthBtn;
    }

    /*
     * Description: Will call make all the contents on of the app and set them into their respective spots in a BorderPane
     *
     * @return   borderPane - pane holding all the contents of the app
     * */
    public BorderPane makeContents() {
        //Create and set center grid holding the river simulation buttons
        GridPane centerGrid = riverSimView.createCenterGrid();
        borderPane.setCenter(centerGrid);

        //Create and set right grid
        GridPane rightGrid = createRightGrid();
        borderPane.setRight(rightGrid);

        //Create and set river information
        HBox riverInfo = createBottomText();
        borderPane.setBottom(riverInfo);

        return  borderPane;
    }

    /*
     * Description: Will reset the current layout with new model
     *
     * @param    riverSim - A new model that the layout should now be made from
     * @return   void
     * */
    public void resetLayout(RiverSim riverSim) throws IOException {
        //Make a new riverSimView since the tiles changed
        riverSimView = new RiverSimView(riverSim);
        //Remake the contents
        makeContents();
    }

    /*
     * Description: Says if checkbox is selected or not
     *
     * @return   true if selected, false if not
     * */
    public Boolean selectedCheckBox() {
        return landCheckBox.isSelected();
    }
    public void setRiverInfoBar(String riverInfo) {
        riverInfoBar.setText(riverInfo);
    }
    public void setLandInfoText(String landInfo) {
        landInfoText.setText(landInfo);
    }

    /*
     * Description: will set the hotkeys on the scene
     *
     * @return   void
     * */
    public void setSceneHotkeys(Scene scene) {
        controller.setSceneHotKeys(scene);
    }

    /*
     * Description: if the checkbox is unselected, will disable the radio buttons, else they will be enabled
     *
     * @return   void
     * */
    public void toggleCheckBox() {
        if (landCheckBox.isSelected()) {
            landCheckBox.setSelected(false);
            disableRadio();
        }
        else {
            landCheckBox.setSelected(true);
            enableRadio();
        }
    }
}
