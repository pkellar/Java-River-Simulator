package kellar_patrick.river.Model;

import java.awt.*;

/*
 * Author: Patrick Kellar
 * Description: The manager class for the tiles
 * */
public class RiverSim {
    private Point timeSinceReset = new Point(0, 1);
    private Integer funds = 0;
    private Integer filled = 0;
    private Tile[][] tiles;
    private static final Point dimensions = new Point(3,4);

    /*
     * Description: This will fill in all the Tiles with Unused lands
     *
     * @return   this
     * */
    public RiverSim() {
        //Make a 2D grid of tiles
        tiles = new Tile[dimensions.x][dimensions.y];
        for (int x = 0; x < dimensions.x; x++) {
            for (int y = 0; y < dimensions.y; y++) {
                tiles[x][y] = new Tile();
                //Set the land with Unused and current timeSinceReset
                tiles[x][y].setLandArea(new Unused(timeSinceReset, new Point(x,y)));
            }
        }
    }

    /*
     * Description: This will return the dimensions of the current model
     *
     * @return   dimensions - Point with x holding row count and y holding col count
     * */
    public Point getDimensions() {
        return dimensions;
    }

    /*
     * Description: Will get and return string of information representing a land
     *
     * @param    xCord - x coordinate of tile being accessed
     * @param    yCord - y coordinate of tile being accessed
     *
     * @return   string of information describing the land queried
     * */
    public String getLandInfo(Integer xCord, Integer yCord) {
        return tiles[xCord][yCord].getLandInfo();
    }

    /*
     * Description: Will get and return string of information representing this current model
     *
     * @return   string of information describing this model including time since reset, filled count, and funds
     * */
    public String getRiverSimInfo() {
        return "Year: " + timeSinceReset.x + " Month: " + timeSinceReset.y + "\nFilled: " + filled + "\nFunds: $" + funds + "k";
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public Point getTimeSinceReset() {
        return timeSinceReset;
    }

    /*
     * Description: Will increment time since reset, add flooded next to river if month 3, add unused next to river if month 4
     *
     * @return  void
     * */
    public void nextMonth() {
        //Add 1 to month
        timeSinceReset.y += 1;

        //If month is 13, will update year and reset month
        if (timeSinceReset.y == 13) {
            timeSinceReset.y = 1;
            timeSinceReset.x += 1;
        }

        //Will go through each tile and have them update to the next month
        for (int x = 0; x < dimensions.x; x++) {
            for (int y = 0; y < dimensions.y; y++) {
                tiles[x][y].nextMonth(timeSinceReset);

                //Will get the current costs and revenues from tiles and update funds accordingly
                funds += tiles[x][y].getCurrentRevenue();
                funds -= tiles[x][y].getCurrentCost();
            }
        }

        //If month 3, set flooded next to river
        if (timeSinceReset.y == 3) {

            //Set the offset to place flooded correctly
            int offset = 1;
            if (dimensions.y == 8) {
                offset = 3;
            }
            else if (dimensions.y == 6) {
                offset = 2;
            }

            //Finds where the middle of the grid is
            int middle = (dimensions.y/2);

            //iterate over the tiles to left and right of river
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < dimensions.x; j++) {

                    //If first loop, use offset to add middle to correct spot
                    if (i == 0) {
                        //Check if previous land is unused, if so, add 1 to filled
                        if (tiles[j][middle + offset].getLandClass() == Unused.class) {
                            filled += 1;
                        }
                        //Set the tile next to the river as Flooded
                        tiles[j][middle + offset].setLandArea(new Flooded(timeSinceReset, new Point(j,middle + offset)));
                    }

                    //If second loop, use offset to subtract middle to correct spot
                    if (i == 1) {
                        //Check if previous land is unused, if so, add 1 to filled
                        if (tiles[j][middle - offset].getLandClass() == Unused.class) {
                            filled += 1;
                        }
                        //Set the tile next to the river as Flooded
                        tiles[j][middle - offset].setLandArea(new Flooded(timeSinceReset, new Point(j,middle - offset)));
                    }
                }

                // Move middle position back 1
                middle -= 1;
            }
        }

        //If month 4, set unused next to river
        if (timeSinceReset.y == 4) {

            //Set the offset to place flooded correctly
            int offset = 1;
            if (dimensions.y == 8) {
                offset = 3;
            }
            else if (dimensions.y == 6) {
                offset = 2;
            }

            //Finds where the middle of the grid is
            int middle = (dimensions.y/2);

            //iterate over the tiles to left and right of river
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < dimensions.x; j++) {

                    //If first loop, use offset to add middle to correct spot
                    if (i == 0) {
                        //Check if previous land is not unused, if so, subtract 1 from filled
                        if (tiles[j][middle + offset].getLandClass() != Unused.class) {
                            filled -= 1;
                        }

                        //Set the tile next to the river as Unused
                        tiles[j][middle + offset].setLandArea(new Unused(timeSinceReset, new Point(j, middle + offset)));
                    }

                    //If second loop, use offset to subtract middle to correct spot
                    if (i == 1) {
                        //Check if previous land is not unused, if so, subtract 1 from filled
                        if (tiles[j][middle - offset].getLandClass() != Unused.class) {
                            filled -= 1;
                        }

                        //Set the tile next to the river as Unused
                        tiles[j][middle - offset].setLandArea(new Unused(timeSinceReset, new Point(j, middle - offset)));
                    }
                }

                // Move middle position back 1
                middle -= 1;
            }
        }
    }

    /*
     * Description: This will reset this model to have different dimensions of tiles and reset all member variables
     *
     * @param    newRowCount - The number of rows wanted in the new model
     * @param    newColCount - The number of columns wanted in the new model
     *
     * @return   void
     * */
    public void reset(Integer newRowCount, Integer newColCount) {
        //Reset timeSinceReset, funds, and filled count to starting values
        timeSinceReset = new Point(0, 1);
        funds = 0;
        filled = 0;

        //Update the dimensions this model
        dimensions.x = newRowCount;
        dimensions.y = newColCount;

        //Make new 2D array with the provided dimensions
        tiles = new Tile[newRowCount][newColCount];
        for (int x = 0; x < newRowCount; x++) {
            for (int y = 0; y < newColCount; y++) {
                //Fill in new array with tiles that hold unused lands
                tiles[x][y] = new Tile();
                tiles[x][y].setLandArea(new Unused(timeSinceReset, new Point(x, y)));
            }
        }
    }

    /*
     * Description: This will set a new land area on a specific tile and update the model info accordingly
     *
     * @param    landArea - The new land area to be added to a tile
     * @param    xCord - x coordinate of tile being changed
     * @param    yCord - y coordinate of tile being changed
     *
     * @return   void
     * */
    public void setLandArea(LandArea landArea, Integer xCord, Integer yCord) {

        //Check if new land isn't Unused, check if old land is an unused, check if new and old land are not the same
        if(landArea.getClass() != Unused.class && tiles[xCord][yCord].getLandClass() == Unused.class
                && landArea.getClass() != tiles[xCord][yCord].getLandClass()) {
            filled += 1;
        }
        //Check if new land is Unused, check if old land isn't an unused, check if new and old land are not the same
        else if(landArea.getClass() == Unused.class && tiles[xCord][yCord].getLandClass() != Unused.class
                && landArea.getClass() != tiles[xCord][yCord].getLandClass()) {
            filled -= 1;
        }

        //Check if the previous tile was already new, if so add back its starting cost
        if (tiles[xCord][yCord].isNew()) {
            funds += tiles[xCord][yCord].getCurrentCost(); //.getLandArea().getTotalCost();
        }
        //Subtract from funds the cost of the new land
        funds -= landArea.getCurrentCost();

        //Set the land area
        tiles[xCord][yCord].setLandArea(landArea);
    }

}
