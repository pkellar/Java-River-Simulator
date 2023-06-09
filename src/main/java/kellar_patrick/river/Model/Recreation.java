package kellar_patrick.river.Model;

import java.awt.*;

/*
 * Author: Patrick Kellar
 * Description: Extension of LandArea, Recreation land type
 * */
public class Recreation extends LandArea {

    /*
     * Description: Will make a new instance of a Recreation land
     *
     * @param    lastChanged - Point, the current date
     * @return   this
     * */
    public Recreation(Point lastChanged, Point landId) {
        super(10, 0, new Point(lastChanged),  new Point(0,1), "Recreation", landId);
    }

    /*
     * Description: Returns string with land letter, and cost/revenue info form parent class
     *
     * @return   String with the text to display on this land's button
     * */
    @Override
    public String getButtonInfo() {
        return "-R-" + super.getButtonInfo();
    }

    /*
     * Description: Updates this land's information for the next month
     *
     * @param    timeSinceReset - Point, the current date
     * @return   void
     * */
    @Override
    public void nextMonth(Point timeSinceReset) {
        addTotalCost(0);

        //If it is month 12, add 5 to the revenue
        if (timeSinceReset.y == 12) {
            addRevenue(5);
        }
        else {
            addRevenue(0);
        }

        //update the land's age
        super.nextMonth(timeSinceReset);
    }
}
