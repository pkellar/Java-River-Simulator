package kellar_patrick.river.Model;

import java.awt.*;

/*
 * Author: Patrick Kellar
 * Description: Abstract class that holds land information
 * */
public abstract class LandArea {

    private Integer totalCost;
    private Integer currentCost;
    private Integer totalRevenue;
    private Integer currentRevenue;
    private final Point lastChanged;
    private final Point age;
    private final String name;
    private final Point landId;

    /*
     * Description: Will create a new landArea with the provided info
     *
     * @param    totalCost - Integer, The cost of the new land
     * @param    totalRevenue - Integer, The revenue of the new land
     * @param    lastChanged - Point with x being year and y being month, when this tile was added
     * @param    age - Point with x being year and y being month, how long this land area has been around
     * @param    name - String, the name of the land type
     * @return   this
     * */
    LandArea(Integer totalCost, Integer totalRevenue, Point lastChanged, Point age, String name, Point landId) {
        this.totalCost = totalCost;
        this.totalRevenue = totalRevenue;
        this.lastChanged = lastChanged;
        this.age = age;
        this.name = name;
        this.currentCost = totalCost;
        this.currentRevenue = totalRevenue;
        this.landId = landId;
    }

    /*
     * Description: Will update current revenue and add to the total revenue
     *
     * @param    addedRevenue - Integer, how much money a land brought in this month
     *
     * @return   void
     * */
    protected void addRevenue(Integer addedRevenue) {
        currentRevenue = addedRevenue;
        totalRevenue += addedRevenue;
    }

    /*
     * Description: Will update current cost and add to the total cost
     *
     * @param    addedCost - Integer, how much money a land cost this month
     *
     * @return   void
     * */
    protected void addTotalCost(Integer addedCost) {
        currentCost = addedCost;
        totalCost += addedCost;
    }

    protected Point getAge() {
        return age;
    }

    /*
     * Description: Will return the current cost and revenue to display this land
     *
     * @return   string of cost and revenue of this land to be on a land button
     * */
    public String getButtonInfo() {
        return "\n-$" + currentCost + "k\n+$" + currentRevenue + "k\nId: " + landId.x + landId.y;
    }

    public Integer getCurrentCost() {
        return currentCost;
    }

    public Integer getCurrentRevenue() {
        return currentRevenue;
    }

    /*
     * Description: Returns the information of this land to put in the top right of the application
     *
     * @return   string with the land name, last changed date, age, total cost, and total revenue
     * */
    public String getLandInfo() {
        return name + "\nLast changed: " + lastChanged.x + "-" + lastChanged.y + "\nAge: "+ age.x + "-" + age.y +
                "\nTotal Cost: $" + totalCost + "k\nTotal Revenue: $" + totalRevenue + "k\nLand Id: "
                + landId.x + landId.y;
    }

    /*
     * Description: Checks if a land was added this turn
     *
     * @return   boolean, true if made this turn, false if not
     * */
    public Boolean isNew() {
        return ( age.x == 0 && age.y == 1 );
    }

    /*
     * Description: Will increment the age of the landArea
     *
     * @param    timeSinceReset - Point, The current date with x = year and y = month
     *
     * @return   void
     * */
    public void nextMonth(Point timeSinceReset) {
        age.y += 1;

        if (age.y == 13) {
            age.y = 1;
            age.x += 1;
        }
    }
}
