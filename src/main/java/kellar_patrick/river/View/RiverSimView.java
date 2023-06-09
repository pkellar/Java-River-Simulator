package kellar_patrick.river.View;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import kellar_patrick.river.Model.RiverSim;
import kellar_patrick.river.Model.Tile;

import java.awt.*;
import java.io.IOException;

/*
 * Author: Patrick Kellar
 * Description: View that holds the gridPane with the land buttons and their respective tileViews
 * */
public class RiverSimView {
    private final RiverSim riverSim;
    private final GridPane riverSimGridPane = new GridPane();
    private final TileView[][] tileView;

    /*
     * Description: Will create all the tile views needed and return this
     *
     * @param    riverSim - A new model that the river view should now be made from
     * @return   this
     * */
    RiverSimView(RiverSim riverSim) throws IOException {
        //Get the current tiles and dimensions from the river simulation model
        Tile[][] tiles = riverSim.getTiles();
        Point riverSimDimensions = riverSim.getDimensions();

        //Create new TileViews and fill in the 2D array
        tileView = new TileView[riverSimDimensions.x][riverSimDimensions.y];
        for (int x = 0; x < riverSimDimensions.x; x++) {
            for (int y = 0; y < riverSimDimensions.y; y++) {
                tileView[x][y] = new TileView(tiles[x][y], x, y);
            }
        }
        //Set the river sim
        this.riverSim = riverSim;
    }

    /*
     * Description: Will create the GridPane holding the land buttons and the "river" in the middle
     *
     * @return   riverSimGridPane - The GridPane containing the land buttons and the river
     * */
    public GridPane createCenterGrid() {
        //Set column constraints, leaving 6% gap in the middle
        //47% cols hold the land buttons, the 6% col is the "river"
        ColumnConstraints landCol1 = new ColumnConstraints();
        landCol1.setPercentWidth(47);

        ColumnConstraints riverCol = new ColumnConstraints();
        riverCol.setPercentWidth(6);

        ColumnConstraints landCol2 = new ColumnConstraints();
        landCol2.setPercentWidth(47);

        //applying col constraints
        riverSimGridPane.getColumnConstraints().addAll(landCol1, riverCol, landCol2);

        Point dimensions = riverSim.getDimensions();

        //Set row constraints, want a 1:1:1 ratio
        for (int i=0; i < dimensions.x; i++) {
            RowConstraints newRow = new RowConstraints();
            newRow.setPercentHeight(100);

            //applying row constraint
            riverSimGridPane.getRowConstraints().add(newRow);

            //This will add two HBoxes on either side of the river. Each HBox will contain half the number of buttons
            // needed to fill the column dimensions the model has.
            riverSimGridPane.add(createLandHbox(dimensions.y/2,0, i), 0, i);
            riverSimGridPane.add(createLandHbox(dimensions.y/2,dimensions.y/2, i), 2, i);
        }

        return riverSimGridPane;
    }

    /*
     * Description: Will make a HBox that contains the number of buttons asked to fill
     *
     * @param    buttonCount - Number of buttons wanted in the HBox
     * @param    xCord - The x coordinate for the buttons being added
     * @param    yCord - The y coordinate for the buttons being added
     * @return   landHBox - The Horizontal box that contains the buttons added
     * */
    private HBox createLandHbox(Integer buttonCount, Integer xCord, Integer yCord) {
        HBox landHBox = new HBox();

        for (;buttonCount > 0; buttonCount--) {
            //It does xCord + (buttonCount - 1) since it adds the button from right to left
            landHBox.getChildren().add(this.tileView[yCord][xCord + (buttonCount - 1)].getTileButton());
        }

        return landHBox;
    }

    /*
     * Description: Will return the information of the river sim model
     *
     * @return   string that has the information of the riverSim member variable
     * */
    public String getRiverSimInfo() {
        return this.riverSim.getRiverSimInfo();
    }

    public TileView[][] getTileViews() {
        return tileView;
    }
}
