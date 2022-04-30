import student.micro.*;
import static org.assertj.core.api.Assertions.*;
import student.media.*;
import java.awt.Color;
// -------------------------------------------------------------------------
/**
 *  Tests SchellingSimulator
 *  Tests methods in SchellingSimulator
 *
 *  @author Aaron Whang (aaronw04)
 *  @version (2022.03.02)
 */
public class SchellingSimulationTest
    extends TestCase
{
    //~ Fields ................................................................


    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new SchellingSimulationTest test object.
     */
    public SchellingSimulationTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }


    //~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    public void setUp()
    {
        /*# Insert your own setup code here */
    }


    // ----------------------------------------------------------
    /**
     * Tests getSatisfactionThreshold.
     */
    public void testGetSatisfactionThreshold()
    {
        SchellingSimulation sim = new SchellingSimulation(1, 1);
        
        sim.getSatisfactionThreshold();
        
        assertThat(sim.getSatisfactionThreshold()).isEqualTo(0.3);
        
    }
    /**
     * Tests setSatisfactionThreshold.
     */
    public void testSetSatisfactionThreshold()
    {
        SchellingSimulation sim = new SchellingSimulation(1, 1);
        
        sim.setSatisfactionThreshold(0.5);
        
        assertThat(sim.getSatisfactionThreshold()).isEqualTo(0.5);
        
    }
    /**
     * Tests getRedLine.
     */
    public void testGetRedLine()
    {
        SchellingSimulation sim = new SchellingSimulation(1, 1);
        
        sim.getRedLine();
        
        assertThat(sim.getRedLine()).isEqualTo(0);
        
    }
    /**
     * Tests setRedLine.
     */
    public void testSetRedLine()
    {
        SchellingSimulation sim = new SchellingSimulation(1, 10);
        
        sim.setRedLine(5);
        
        assertThat(sim.getRedLine()).isEqualTo(5);
        
    }
    /**
     * Tests populate for only 
     * probability possible is blue.
     */
    public void testPopulateBlue()
    {
        SchellingSimulation sim = new SchellingSimulation(1, 1);
        sim.setRedLine(1);
        
        sim.populate(1, 0);
        
        for (Pixel pix : sim.getPixels())
        {
            assertThat(pix.getColor()).isEqualTo(Color.BLUE);
        }
    }
    /**
     * Tests popuate for only
     * probability possible is orange.
     */
    public void testPopulateOrange()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        sim.populate(0, 1);
        Pixel pix = sim.getPixel(1, 5);
        assertThat(pix.getColor()).isEqualTo(Color.ORANGE);
    }
    /**
     * Tests areSameColor..
     */
    public void testAreSameColor()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        Pixel pix1 = sim.getPixel(1, 5);
        Pixel pix2 = sim.getPixel(2, 5);
        
        pix1.setColor(Color.BLUE);
        pix2.setColor(Color.BLUE);
        
        assertThat(sim.areSameColor(pix1, pix2)).isTrue();
    }
    /**
     * Tests isEmpty.
     */
    public void testIsEmpty()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        Pixel pix1 = sim.getPixel(1, 5);
        
        pix1.setColor(Color.WHITE);
        
        assertThat(sim.isEmpty(pix1)).isTrue();
    }
    /**
     * Tests isSatisfied.
     */
    public void testIsSatisfied()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        Pixel pix1 = sim.getPixel(1, 5);
        
        Pixel pix2 = sim.getPixel(1, 6);
        
        pix1.setColor(Color.BLUE);
        pix2.setColor(Color.BLUE);
        
        // cant execute all statements or 
        // conditions becaus eof randomness
        boolean satisfied = sim.isSatisfied(pix1, Color.BLUE);
        
        assertThat(satisfied).isTrue();
    }
    /**
     * Tests maybeRelocate with initial pixel blue.
     */
    public void testMaybeRelocateBlue()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        Pixel pix1 = sim.getPixel(1, 5);
        
        Pixel pix2 = sim.getPixel(1, 6);
        Pixel pix3 = sim.getPixel(2, 5);
        Pixel pix4 = sim.getPixel(1, 4);
        
        pix1.setColor(Color.BLUE);
        
        pix2.setColor(Color.ORANGE);
        pix3.setColor(Color.ORANGE);
        pix4.setColor(Color.ORANGE);
        
        sim.maybeRelocate(pix1);
        
        // assume that the new random generated 
        // pixel is empty and satisfied
        assertThat(sim.maybeRelocate(pix1)).isTrue();
    }
    /**
     * Tests maybe relocate with inital pixel orange.
     */
    public void testMaybeRelocateOrange()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        Pixel pix1 = sim.getPixel(1, 5);
        
        Pixel pix2 = sim.getPixel(1, 6);
        Pixel pix3 = sim.getPixel(2, 5);
        Pixel pix4 = sim.getPixel(1, 4);
        
        pix1.setColor(Color.ORANGE);
        
        pix2.setColor(Color.BLUE);
        pix3.setColor(Color.BLUE);
        pix4.setColor(Color.BLUE);
        
        sim.maybeRelocate(pix1);
        
        // assume that the new random generated 
        // pixel is empty and satisfied
        assertThat(sim.maybeRelocate(pix1)).isTrue();
    }
    /**
     * Tests cycleAgents.
     */
    public void testCycleAgents()
    {
        SchellingSimulation sim = new SchellingSimulation(10, 10);
        
        sim.cycleAgents();
        
        // assume that this method would 
        // call maybeRelocate 5 times
        assertThat(sim.cycleAgents()).isEqualTo(5);
    }
}
