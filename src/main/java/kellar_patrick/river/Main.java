package kellar_patrick.river;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kellar_patrick.river.View.Layout;

import java.io.IOException;

/*
* Program Name: JavaFX, Observer
* Author: Patrick Kellar
* Class: CSC-468-M01 Spring 2023
* Description: This is a river simulation app that uses the observer pattern.
* Last tier passed: Finished all tiers

    _X_ Followed the class OOP diagram
    _X_ Observer pattern (ignores tiers)


    1.	Tier: Views and animal
    _X_ a. All objects (ignoring the sim area)
    _X_ b. Have a starting number of tiles in sim area
    _X_ c. Able to add/remove a land area properly
    _X_ d. Info bar listed correctly with all the required elements
    _X_ e. Tile Text correct in land area
    _X_ f. Tile Text correct for each for all rectangles
    _X_ g. Radio buttons update properly
    _X_ h. Selecting a rectangle without “add” updates the land area info



    2a Tier: Advanced functionality
    _X_ a. Next month button has some noticeable effect
    _X_ b. Land areas updated properly on “next”
    _X_ c. Sim info bar updated properly
    _X_ d. Selecting a tile after an update shows the new information



    2b: Layout
    _X_ a. Location of all items in correct spot
    _X_ b. Layout still correct on window resize
    _X_ c. Resize grid at minimum resets the grid and info
    _X_ d. Everything still working that is listed above with resize


    Final Tier: Extensions 30

    Extension 1: number->2a  points->5  name->Mark what rectangle is being shown in the land are information section:
        This extension can be seen visually with an id provided to each rectangle and the corresponding id showing up
            in the land information when clicked. The id is an add-on member variable to LandArea.

    Extension 2: number->2b  points->10  name->Add Image instead of using text only
        This extension can be seen visually with a specific image added to tiles of specific land types. Unused has
            a red x, recreation has stick figures, agriculture has a hand with a plant, and flooded has a water symbol.
        These images get set initially by the createLandButton function, and they are later set by the property
            change handler in TileView. This will set the corresponding image on a land add or next month actions.

    Extension 3: number->2g  points->5  name->Disable the land selection when "add" is not checked
        This was implemented by adding a function in the controller (setCheckBoxListener) that takes in a CheckBox and
            adds the CheckListener to it. This allows the checkbox changes to be handled. The handler will then call
            layout.enableRadio or layout.disableRadio based off if the checkbox is selected or not.

    Extension 4: number->3a  points->10  name->Add hot-keys to 3+ buttons
        This was implemented by adding a function in the controller (setSceneHotKeys) that takes in a Scene and sets
            the KeyListener to it. This allows the keyboard clicks to be handled. The handler will perform actions on
            the "N", "A", and "R" keys. If "N" is clicked, it will perform the actions of the next month button. If "A"
            is clicked, it will toggle the "Add" checkbox. If "R" is clicked it will reset the simulator to a 5x3 board.
*/

public class Main extends Application {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * Description: Start of application that will have layout make its contents and set the
     *  scene with the returned Border Pane
     *
     * @param    stage       The stage to put a Border Pane into
     * */
    @Override
    public void start(Stage stage) throws IOException {
        Layout layout = new Layout();
        Parent root = layout.makeContents();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        layout.setSceneHotkeys(scene);
        stage.setScene(scene);
        stage.setTitle("RiverSim Simulator");
        stage.show();
    }

}