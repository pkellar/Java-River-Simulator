package kellar_patrick.river.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import kellar_patrick.river.Model.Agriculture;
import kellar_patrick.river.Model.Recreation;
import kellar_patrick.river.Model.RiverSim;
import kellar_patrick.river.Model.Unused;
import kellar_patrick.river.View.Layout;
import kellar_patrick.river.View.TileView;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;

/*
 * Author: Patrick Kellar
 * Description: The controller that will set up and handle all events of the simulation
 * */
public class Controller {

    private final RiverSim riverSim;
    private final Layout layout;

    /*
     * Description: This controller will add event filters or handlers to the buttons in this application
     *
     * @param    riverSim - The River Simulation model that this controller is using
     * @param    layout - The main view of this simulation
     * @return   this
     * */
    public Controller(RiverSim riverSim, Layout layout) {
        this.riverSim = riverSim;
        this.layout = layout;

        //Get the tile views from the layout
        TileView[][] tileViews = layout.getTileViews();
        //Get the dimensions of the model
        Point dimensions = riverSim.getDimensions();

        //Add a click listener to each of the buttons representing the tiles
        for (int i = 0; i < dimensions.x; i++) {
            for (int j = 0; j < dimensions.y; j++) {
                tileViews[i][j].getTileButton().addEventFilter(ActionEvent.ACTION, new ClickListener());
            }
        }

        //Add handler to next month button that will handle that button click
        this.layout.getNextMonthBtn().addEventHandler(ActionEvent.ACTION, new MonthListener());

        //Add handler to resize buttons that will resize and reset the tiles when any of them are clicked
        Button[] resizeButtons = this.layout.getResizeButtons();
        resizeButtons[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new ResizeListener());
        resizeButtons[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new ResizeListener());
        resizeButtons[2].addEventHandler(MouseEvent.MOUSE_CLICKED, new ResizeListener());
    }

    /*
     * Description: This class holds the handler for when a check button is clicked
     * */
    private class CheckListener implements EventHandler<ActionEvent> {

        /*
         * Description: This will handle the event that is triggered by the "add" CheckBox
         *
         * @param    event - An action event that holds the event information
         * @return   void
         * */
        @Override
        public void handle(ActionEvent event) {
            //if the CheckBox is selected, enable the radio buttons
            if (((CheckBox) event.getSource()).isSelected()) {
                layout.enableRadio();
            }
            else {
                //if the CheckBox is not selected, disable the radio buttons
                layout.disableRadio();
            }
        }
    }

    /*
     * Description: This class holds the handler for when a land button is clicked in the app
     * */
    private class ClickListener implements EventHandler<ActionEvent> {

        /*
         * Description: This will handle the event that is triggered by a land button. If check box selected, it will
         *  add land. If check box isn't selected, it will update the land information in the view
         *
         * @param    event - An action event that holds the event information
         * @return   void
         * */
        @Override
        public void handle(ActionEvent event) {
            //Get check box from view
            Boolean checkBoxSelected = layout.selectedCheckBox();

            //Get the id that holds the coordinates of the button clicked
            String landId = ((Node)event.getSource()).getId();
            String[] coordinates = landId.split(",");
            Point landCoordinates = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

            //If checkbox selected, add a land
            if (checkBoxSelected) {
                //Get the string info of the currently selected radio button
                String selectedLandType = layout.getSelectedRadio();
                switch (selectedLandType) {
                    case "Agriculture" -> riverSim.setLandArea(new Agriculture(riverSim.getTimeSinceReset(), landCoordinates),
                            landCoordinates.x, landCoordinates.y);
                    case "Recreation" -> riverSim.setLandArea(new Recreation(riverSim.getTimeSinceReset(), landCoordinates),
                            landCoordinates.x, landCoordinates.y);
                    case "Unused" -> riverSim.setLandArea(new Unused(riverSim.getTimeSinceReset(), landCoordinates),
                            landCoordinates.x, landCoordinates.y);
                }
                //Update the river simulation information in case the funds or filled count have changed
                layout.setRiverInfoBar(riverSim.getRiverSimInfo());
                layout.setLandInfoText(riverSim.getLandInfo(landCoordinates.x, landCoordinates.y));
            }
            else {
                //if checkbox not selected, add the clicked buttons info to the top right of the application
                layout.setLandInfoText(riverSim.getLandInfo(landCoordinates.x, landCoordinates.y));
            }
        }
    }

    /*
     * Description: This class holds the handler for when the next month button is clicked
     * */
    private class MonthListener implements EventHandler<ActionEvent> {

        /*
         * Description: This will take in a next month event and update the model and view accordingly
         *
         * @param    event - An action event of clicking the next month button
         * @return   void
         * */
        @Override
        public void handle(ActionEvent event) {
            //Have the model go forward a month
            riverSim.nextMonth();
            //update the river info at the bottom of the layout
            layout.setRiverInfoBar(riverSim.getRiverSimInfo());
        }
    }

    /*
     * Description: This class holds the handler for when a key is clicked
     * */
    private class KeyListener implements EventHandler<KeyEvent> {

        /*
         * Description: This will take in a key event and perform a shortcut if it's a hotkey
         *
         * @param    event - A key event with the KeyCode
         * @return   void
         * */
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.N) {
                //Have the model go forward a month
                riverSim.nextMonth();
                //update the river info at the bottom of the layout
                layout.setRiverInfoBar(riverSim.getRiverSimInfo());
            } else if (event.getCode() == KeyCode.A) {
                layout.toggleCheckBox();
            } else if (event.getCode() == KeyCode.R) {
                riverSim.reset(3,4);

                //Reset the layout with the new model
                try {
                    layout.resetLayout(riverSim);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Get the new TileViews and set a new handler on each button that will wait for them to be clicked
                TileView[][] tileViews = layout.getTileViews();
                Point dimensions = riverSim.getDimensions();

                for (int i = 0; i < dimensions.x; i++) {
                    for (int j = 0; j < dimensions.y; j++) {
                        tileViews[i][j].getTileButton().addEventFilter(ActionEvent.ACTION, new ClickListener());
                    }
                }
            }
        }
    }

    /*
     * Description: This class holds the handler for when a resize button is clicked, and that event needs to be handled
     * */
    private class ResizeListener implements EventHandler<MouseEvent> {

        /*
         * Description: This will take in a resize event, decide which resize button was clicked, and update accordingly
         *
         * @param    event - A mouse event that holds the event information
         * @return   void
         * */
        @Override
        public void handle(MouseEvent event) {
            //Get the id of the resize button clicked
            String landId = ((Node)event.getSource()).getId();

            //Reset the RiverSim model based off of which button clicked
            switch (landId) {
                case "5x3" -> riverSim.reset(3,4);
                case "7x5" -> riverSim.reset(5,6);
                case "9x7" -> riverSim.reset(7,8);
            }

            //Reset the layout with the new model
            try {
                layout.resetLayout(riverSim);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Get the new TileViews and set a new handler on each button that will wait for them to be clicked
            TileView[][] tileViews = layout.getTileViews();
            Point dimensions = riverSim.getDimensions();

            for (int i = 0; i < dimensions.x; i++) {
                for (int j = 0; j < dimensions.y; j++) {
                    tileViews[i][j].getTileButton().addEventFilter(ActionEvent.ACTION, new ClickListener());
                }
            }
        }
    }

    /*
     * Description: Takes a checkbox and adds the check listener to be its event handler
     * */
    public void setCheckBoxListener(CheckBox checkBox) {
        checkBox.addEventHandler(ActionEvent.ACTION, new CheckListener());
    }

    /*
     * Description: Takes a scene and adds the key listener to be its event handler
     * */
    public void setSceneHotKeys(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new KeyListener());
    }
}
