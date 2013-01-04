package freedays.timesheet;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import freedays.util.DateUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class FreeDayAbstractionTest {
    
    private FreeDayAbstraction abstraction;
    
    @Before
    public void setUp(){
        abstraction = new FreeDayAbstraction();
        abstraction.setUsername("nenea gugu");
        abstraction.setPattern(Mockito.mock(Pattern.class));
        abstraction.setStart(DateUtils.convString2Calendar("12/12/1989"));
        abstraction.setEnd(DateUtils.convString2Calendar("12/14/1989"));
    }
    
    
    @Test
    public void testIsBetweenFilterEnclosed(){
        Calendar left = DateUtils.convString2Calendar("12/11/1989");
        Calendar right = DateUtils.convString2Calendar("12/14/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsBetweenFilterOverlapExact(){
        Calendar left = DateUtils.convString2Calendar("12/12/1989");
        Calendar right = DateUtils.convString2Calendar("12/14/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsBetweenFilterOverlapStart(){
        Calendar left = DateUtils.convString2Calendar("12/12/1989");
        Calendar right = DateUtils.convString2Calendar("12/12/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsBetweenFilterOverlapStartAbstraction(){
        Calendar left = DateUtils.convString2Calendar("12/12/1989");
        Calendar right = DateUtils.convString2Calendar("12/13/1989");
        abstraction.setEnd(left);
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsBetweenFilterOverlapRight(){
        Calendar left = DateUtils.convString2Calendar("12/13/1989");
        Calendar right = DateUtils.convString2Calendar("12/14/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsBetweenFilterOverlapLeft(){
        Calendar left = DateUtils.convString2Calendar("12/11/1989");
        Calendar right = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsBetweenFilterDisjointLeft(){
        Calendar left = DateUtils.convString2Calendar("12/10/1989");
        Calendar right = DateUtils.convString2Calendar("12/11/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertFalse(result);
    }
    
    @Test
    public void testIsBetweenFilterDisjointRight(){
        Calendar left = DateUtils.convString2Calendar("12/15/1989");
        Calendar right = DateUtils.convString2Calendar("12/16/1989");
        
        boolean result = abstraction.isBetweenFilter(left, right);
        
        Assert.assertFalse(result);
    }
    
    @Test
    public void testCreateBetweenFilterFullOverlap() {
        Calendar left = DateUtils.convString2Calendar("12/11/1989");
        Calendar right = DateUtils.convString2Calendar("12/15/1989");
        
        FreeDayAbstraction result = abstraction.createBetweenFilter(left, right);
        
        Assert.assertTrue(DateUtils.isDayEqual(result.getStart(), abstraction.getStart()));
        Assert.assertTrue(DateUtils.isDayEqual(result.getEnd(), abstraction.getEnd()));
    }
    
    @Test
    public void testCreateBetweenFilterFullOverlap2() {
        Calendar left = DateUtils.convString2Calendar("12/13/1989");
        Calendar right = DateUtils.convString2Calendar("12/13/1989");
        
        FreeDayAbstraction result = abstraction.createBetweenFilter(left, right);
        
        Assert.assertTrue(DateUtils.isDayEqual(result.getStart(), left));
        Assert.assertTrue(DateUtils.isDayEqual(result.getEnd(), right));
    }
    
    @Test
    public void testCreateBetweenFilterLeftOverlap() {
        Calendar left = DateUtils.convString2Calendar("12/10/1989");
        Calendar right = DateUtils.convString2Calendar("12/13/1989");
        
        FreeDayAbstraction result = abstraction.createBetweenFilter(left, right);
        
        Assert.assertTrue(DateUtils.isDayEqual(result.getStart(), abstraction.getStart()));
        Assert.assertTrue(DateUtils.isDayEqual(result.getEnd(), right));
    }
    
    @Test
    public void testCreateBetweenFilterRightOverlap() {
        Calendar left = DateUtils.convString2Calendar("12/13/1989");
        Calendar right = DateUtils.convString2Calendar("12/16/1989");
        
        FreeDayAbstraction result = abstraction.createBetweenFilter(left, right);
        
        Assert.assertTrue(DateUtils.isDayEqual(result.getStart(), left));
        Assert.assertTrue(DateUtils.isDayEqual(result.getEnd(), abstraction.getEnd()));
    }
    
    @Test
    public void testCompareTo() {
        Calendar smaller = DateUtils.convString2Calendar("12/11/1989");
        Calendar bigger = DateUtils.convString2Calendar("12/12/1989");
        FreeDayAbstractionComparator abstractionComparator = new FreeDayAbstractionComparator();
        
        FreeDayAbstraction smallerAbstraction = new FreeDayAbstraction();
        smallerAbstraction.setStart(smaller);
        FreeDayAbstraction biggerAbstraction = new FreeDayAbstraction();
        biggerAbstraction.setStart(bigger);
        FreeDayAbstraction biggerAbstractionReloaded = new FreeDayAbstraction();
        biggerAbstractionReloaded.setStart(bigger);
        
        Assert.assertEquals(0, abstractionComparator.compare(smallerAbstraction, smallerAbstraction));
        Assert.assertEquals(-1, abstractionComparator.compare(smallerAbstraction,biggerAbstraction));
        Assert.assertEquals(1, abstractionComparator.compare(biggerAbstraction,smallerAbstraction));
        Assert.assertEquals(0, abstractionComparator.compare(biggerAbstraction,biggerAbstractionReloaded));
    }
    
    @Test
    public void cucu(){
        abstraction.setEnd(DateUtils.convString2Calendar("12/12/1989"));
        abstraction.setStart(DateUtils.convString2Calendar("12/12/1989"));
        
        Calendar left = DateUtils.convString2Calendar("12/12/1989");
        Calendar right = DateUtils.convString2Calendar("12/15/1989");
        
        FreeDayAbstraction result = abstraction.createBetweenFilter(left, right);
        
        Assert.assertTrue(DateUtils.isDayEqual(result.getStart(), left));
        Assert.assertTrue(DateUtils.isDayEqual(result.getEnd(), left));
    }
    
    
    
    
    

}
    