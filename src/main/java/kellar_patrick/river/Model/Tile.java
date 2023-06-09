package kellar_patrick.river.Model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*
 * Author: Patrick Kellar
 * Description: Constant holder for a land area. Is the Subject in the observer pattern.
 * */
public class Tile {

    private LandArea landArea;
    private final PropertyChangeSupport subject;

    /*
     * Description: Will create a tile that is the subject in the observer pattern
     *
     * @return   this
     * */
    public Tile() {
        subject = new PropertyChangeSupport(this);
    }

    /*
     * Description: This adds a property change listener to this class, setting it as a subject in the observer pattern
     *
     * @param    propChangeListener - the observer that is listening for changes in this tile
     *
     * @return   void
     * */
    //GRADING: SUBJECT
    public void addPropertyChangeListener(PropertyChangeListener propChangeListener) {
        subject.addPropertyChangeListener(propChangeListener);
    }

    /*
     * Description: Return the information for the button to show for its land
     *
     * @return   string with the land letter, current cost, and current revenue of the land
     * */
    public String getButtonInfo() {
        return this.landArea.getButtonInfo();
    }

    /*
     * Description: Returns how much money the land cost this month
     * */
    public Integer getCurrentCost() {
        return landArea.getCurrentCost();
    }

    /*
     * Description: Returns how much money the land made this month
     * */
    public Integer getCurrentRevenue() {
        return landArea.getCurrentRevenue();
    }

    /*
     * Description: Returns the class of the current land
     * */
    public Class<?> getLandClass() {
        return landArea.getClass();
    }

    /*
     * Description: Returns the information of a land to put in the top right of the application
     *
     * @return   string with the land name, last changed date, age, total cost, and total revenue
     * */
    public String getLandInfo() {
        return landArea.getLandInfo();
    }

    /*
     * Description: Checks if a land was added this turn
     *
     * @return   boolean, true if made this turn, false if not
     * */
    public Boolean isNew() {
        return landArea.isNew();
    }

    /*
     * Description: This will have the land area update its information for the next month and signal that there was a
     *  property change to the TileView
     *
     * @param    timeSinceReset - Point with x being the year and y being the month
     *
     * @return   void
     * */
    public void nextMonth(Point timeSinceReset) {
        //Tell the land area to update its info and the current year and month
        landArea.nextMonth(timeSinceReset);
        //Fire prop change with the new info to be the button's text
        subject.firePropertyChange("nextMonth", null, this.landArea.getButtonInfo());
    }

    /*
     * Description: This will set the new land tell observer to update info
     *
     * @param    landArea - A land area that you want to set
     *
     * @return   void
     * */
    //GRADING: TRIGGER
    public void setLandArea(LandArea landArea) {
        this.landArea = landArea;
        //Fire prop change with the new info to be the button's text
        subject.firePropertyChange("setLand", null, this.landArea.getButtonInfo());
    }
}
