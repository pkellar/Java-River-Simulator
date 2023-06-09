package kellar_patrick.river.Model;

import java.awt.*;

/*
 * Author: Patrick Kellar
 * Description: Extension of LandArea, Unused land type
 * */
public class Unused extends LandArea {

    /*
     * Description: Will make a new instance of an unused land
     *
     * @param    lastChanged - Point, the current date
     * @return   this
     * */
    public Unused(Point lastChanged, Point landId) {
        super(0, 0, new Point(lastChanged), new Point(0,1), "Unused", landId);
    }

    /*
     * Description: Returns string with land letter, and cost/revenue info form parent class
     *
     * @return   String with the text to display on this land's button
     * */
    @Override
    public String getButtonInfo() {
        return "-U-" + super.getButtonInfo();
    }

    /*
     * Description: Updates this land's information for the next month
     *
     * @param    timeSinceReset - Point, the current date
     * @return   void
     * */
    @Override
    public void nextMonth(Point timeSinceReset) {
        super.nextMonth(timeSinceReset);
    }


}
