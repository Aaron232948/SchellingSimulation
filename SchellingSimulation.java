import student.media.*;
import student.util.Random;
import java.awt.Color;
//-------------------------------------------------------------------------
/**
 *  Creates SchellingSimulator class.
 *  Creates model of Schelling Simulator.
 *
 *  @author Aaron Whang (aaronw04)
 *  @version (2022.03.02)
 */
public class SchellingSimulation
    extends Picture
{
    //~ Fields ................................................................
    private double satisfactionThreshold;
    private int redLine;
    private int width;
    private int height;

    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Initializes a newly created SchellingSimulation picture.
     * @param width1 is width of picture
     * @param height1 is height of picture
     */
    public SchellingSimulation(int width1, int height1)
    {
        super(width1, height1);
        satisfactionThreshold = 0.3;
        redLine = 0;
        width = width1;
        height = height1;
    }


    //~ Methods ...............................................................
    /**
     * Getter method for satisfactionThreshold.
     * @return satisfactionThreshold
     */
    public double getSatisfactionThreshold()
    {
        return this.satisfactionThreshold;
    }
    /**
     * Setter method for satisfactionThreshold.
     * @param sat is double value for satisfactionThreshold
     */
    public void setSatisfactionThreshold(double sat)
    {
        this.satisfactionThreshold = sat;
    }
    /**
     * Getter method for redLine.
     * @return redLine
     */
    public int getRedLine()
    {
        return this.redLine;
    }
    /**
     * Setter method for redLine.
     * @param rl is int value for redLine
     */
    public void setRedLine(int rl)
    {
        this.redLine = rl;
    }
    /**
     * Populates picture with colored pixels.
     * @param bl the percent chance a pixel is colored blue
     * with range from 0-1
     * @param or the percent chance a pixel is colored orange
     * with range from 0-1
     */
    public void populate(double bl, double or)
    {
        Random generator = Random.generator();
        int b = generator.nextInt(100);
        int o = generator.nextInt(100);
        for (Pixel pix : this.getPixels())
        {
            if (b < bl * 100)
            {
                pix.setColor(Color.BLUE);
            }
            if (bl * 100 < o && o < (or * 100) + b && pix.getY() > getRedLine())
            {
                pix.setColor(Color.ORANGE);
            }
        }
    }
    /**
     * Checks if two pixels are the same color.
     * @param pix1 is pixel 1
     * @param pix2 is pixel 2
     * @return true or false based on if
     * pixels are same color
     */
    public boolean areSameColor(Pixel pix1, Pixel pix2)
    {
        return pix1.getColor() == pix2.getColor();
    }
    /**
     * Checks if pixel is empty or white.
     * @param pix1 is pixel 1
     * @return true if pixel is empty
     */
    public boolean isEmpty(Pixel pix1)
    {   
        return pix1.getColor() == Color.WHITE;
    }
    /**
     * Checks if pixel is satisfied.
     * @param pix1 is pixel 1
     * @param col is color of pix1
     * @return true if isSatisfied
     */
    public boolean isSatisfied(Pixel pix1, Color col)
    {
        double t = 0;
        double p = 0;
        double w = 0;
        for (Pixel neighbor : pix1.getNeighborPixels())
        {
            if (neighbor.getColor() == col && 
                neighbor.getColor() != Color.WHITE)
            {
                t++;
                w++;
            }
            p++;
        }
        return t / (p - w) >= getSatisfactionThreshold() || w == 0;
    }
    /**
     * Checks if pixel will relocate.
     * @param pix1 is pixel 1.
     * @return true if initial pixel is empty
     */
    public boolean maybeRelocate(Pixel pix1)
    {
        Random generator = Random.generator();
        int x = generator.nextInt(width);
        int y = generator.nextInt(height);
        Pixel newPix1 = this.getPixel(x, y);
        if (pix1.getColor() == Color.BLUE)
        {
            if (newPix1.getColor() == Color.WHITE && 
                isSatisfied(newPix1, Color.BLUE))
            {
                newPix1.setColor(Color.BLUE);
                pix1.setColor(Color.WHITE);
            }
        }
        else
        {
            if (newPix1.getColor() == Color.WHITE && 
                isSatisfied(newPix1, Color.ORANGE))
            {
                newPix1.setColor(Color.ORANGE);
                pix1.setColor(Color.WHITE);
            }
        }
        return pix1.getColor() == Color.WHITE;
    }
    /**
     * Applies all methods to scan all pixels 
     * and check if they should stay or be moved.
     * @return int value of amount of times that 
     * this was run
     */
    public int cycleAgents()
    {
        int k = 0;
        for (Pixel pix : this.getPixels())
        {
            if (pix.getColor() != Color.WHITE 
                && !isSatisfied(pix, pix.getColor()))
            {
                maybeRelocate(pix);
                k++;
            }
        }
        return k;
    }
}
