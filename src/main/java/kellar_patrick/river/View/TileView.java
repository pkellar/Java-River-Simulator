package kellar_patrick.river.View;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kellar_patrick.river.Main;
import kellar_patrick.river.Model.Tile;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

/*
 * Author: Patrick Kellar
 * Description: The view node that displays the tile's info. This is the observer in the observer pattern
 * */
public class TileView extends Button implements PropertyChangeListener {

    private final Button tileButton;

    /*
     * Description: Will call the function that creates the button that is associated with this view and set this
     *  as an observer
     *
     * @param    tile - The tile this view is an observer of
     * @param    xCord - The x coordinate of the button
     * @param    yCord - The y coordinate of the button
     * @return   this
     * */
    TileView(Tile tile, Integer xCord, Integer yCord) throws IOException {
        //create and set the land button
        this.tileButton = createLandButton(tile.getButtonInfo(), xCord, yCord);
        //set this view as the observer
        tile.addPropertyChangeListener(this);
    }

    /*
     * Description: Will make and return a button with the provided test and id with its coordinates
     *
     * @param    landText - the text information of the land this button is representing
     * @param    xCord - The x coordinate of the button
     * @param    yCord - The y coordinate of the button
     * @return   landButton - the button representing a tile
     * */
    private static Button createLandButton(String landText, Integer xCord, Integer yCord) throws IOException {
        Button landButton = new Button(landText);

        //Set the button with an id of its coordinates
        landButton.setId(xCord.toString() + "," + yCord.toString());

        //Set high preferred width and height so buttons expand as much as they can
        landButton.setPrefWidth(999);
        landButton.setPrefHeight(Double.MAX_VALUE);

        Image image = new Image(Objects.requireNonNull(Main.class.getResource("unused.png")).openStream());
        landButton.setGraphic(new ImageView(image));

        return landButton ;
    }

    public Button getTileButton() {
        return tileButton;
    }

    /*
     * Description: Will update the text of the tile button when a change is detected in the tile
     *
     * @param    evt - The property change event that occurred
     * @return   void
     * */
    //GRADING: OBSERVE
    public void propertyChange(PropertyChangeEvent evt) {
        String[] landInfoSplit = ((String) evt.getNewValue()).split("\n");
        switch (landInfoSplit[0]) {
            case "-A-" -> {
                try {
                    tileButton.setGraphic(new ImageView(new Image(Objects.requireNonNull
                            (Main.class.getResource("agriculture.png")).openStream())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "-R-" -> {
                try {
                    tileButton.setGraphic(new ImageView(new Image(Objects.requireNonNull
                            (Main.class.getResource("recreation.png")).openStream())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "-U-" -> {
                try {
                    tileButton.setGraphic(new ImageView(new Image(Objects.requireNonNull
                            (Main.class.getResource("unused.png")).openStream())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "-F-" -> {
                try {
                    tileButton.setGraphic(new ImageView(new Image(Objects.requireNonNull
                            (Main.class.getResource("flooded.png")).openStream())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        tileButton.setText((String) evt.getNewValue());
    }

}
