package kellar_patrick.river.Model;


import java.awt.*;

/*
 * Author: Patrick Kellar
 * Description: Extension of LandArea, Agriculture land type
 * */
public class Agriculture extends LandArea {

    /*
     * Description: Will make a new instance of an Agriculture land, costs 300 at start
     *
     * @param    lastChanged - Point, the current date
     * @return   this
     * */
    public Agriculture(Point lastChanged, Point landId) {
        super(300, 0,  new Point(lastChanged),  new Point(0,1), "Agriculture", landId);
    }

    /*
     * Description: Returns string with land letter, and cost/revenue info form parent class
     *
     * @return   String with the text to display on this land's button
     * */
    @Override
    public String getButtonInfo() {
        return "-A-" + super.getButtonInfo();
    }

    /*
     * Description: Updates this land's information for the next month
     *
     * @param    timeSinceReset - Point, the current date
     * @return   void
     * */
    @Override
    public void nextMonth(Point timeSinceReset) {

        //If it is month 5, the land costs another 50
        if (timeSinceReset.y == 5) {
            addTotalCost(50);
        }
        else {
            addTotalCost(0);
        }

        //If it is month 10, check if the land is older than 3 months
        if (timeSinceReset.y == 10) {
            Point agricultureAge = getAge();

           if (!(agricultureAge.x == 0 && agricultureAge.y <= 3)) {
               //If older than 3 months, add 65 to land revenue
               addRevenue(65);
           }else {
               addRevenue(0);
           }
        }else {
            addRevenue(0);
        }

        //Update the land age
        super.nextMonth(timeSinceReset);
    }
}