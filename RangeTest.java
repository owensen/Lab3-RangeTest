package org.jfree.custom;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RangeTest {

    private Range exampleRange;

    @Before
    public void setUp() throws Exception {
        exampleRange = new Range(-1.0, 1.0);
    }

    // getLength()
    @Test
    public void testGetLength_PositiveAndNegativeRange() {
        assertEquals(2.0, exampleRange.getLength(), 0.0001);
    }

    @Test
    public void testGetLength_ZeroLengthRange() {
        Range zeroRange = new Range(5.0, 5.0);
        assertEquals(0.0, zeroRange.getLength(), 0.0001);
    }

    // getLowerBound / getUpperBound
    @Test
    public void testGetLowerBound() {
        assertEquals(-1.0, exampleRange.getLowerBound(), 0.0001);
    }

    @Test
    public void testGetUpperBound() {
        assertEquals(1.0, exampleRange.getUpperBound(), 0.0001);
    }

    // getCentralValue()
    @Test
    public void testGetCentralValue() {
        assertEquals(0.0, exampleRange.getCentralValue(), 0.0001);
    }

    // contains()
    @Test
    public void testContains_ValueInsideRange() {
        assertTrue(exampleRange.contains(0));
    }

    @Test
    public void testContains_ValueBelowRange() {
        assertFalse(exampleRange.contains(-2));
    }

    @Test
    public void testContains_ValueAboveRange() {
        assertFalse(exampleRange.contains(2));
    }

    // constrain()
    @Test
    public void testConstrain_ValueWithinRange() {
        assertEquals(0.0, exampleRange.constrain(0.0), 0.0001);
    }

    @Test
    public void testConstrain_ValueBelowRange() {
        assertEquals(-1.0, exampleRange.constrain(-5.0), 0.0001);
    }

    @Test
    public void testConstrain_ValueAboveRange() {
        assertEquals(1.0, exampleRange.constrain(5.0), 0.0001);
    }

    // intersects()
    @Test
    public void testIntersects_True_LowOverlap() {
        assertTrue(exampleRange.intersects(-2.0, 0.0));
    }

    @Test
    public void testIntersects_True_HighOverlap() {
        assertTrue(exampleRange.intersects(0.0, 2.0));
    }

    @Test
    public void testIntersects_False() {
        assertFalse(exampleRange.intersects(2.0, 3.0));
    }

    // constructor exception
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidRangeThrowsException() {
        new Range(10.0, -10.0);
    }

    // combine()
    @Test
    public void testCombine_BothNotNull() {
        Range r1 = new Range(1, 5);
        Range r2 = new Range(3, 10);
        Range result = Range.combine(r1, r2);
        assertEquals(1.0, result.getLowerBound(), 0.0001);
        assertEquals(10.0, result.getUpperBound(), 0.0001);
    }

    @Test
    public void testCombine_FirstNull() {
        Range r2 = new Range(3, 10);
        Range result = Range.combine(null, r2);
        assertEquals(r2, result);
    }

    @Test
    public void testCombine_BothNull() {
        assertNull(Range.combine(null, null));
    }

    // expandToInclude()
    @Test
    public void testExpandToInclude_WithinRange() {
        Range r = new Range(2, 6);
        Range result = Range.expandToInclude(r, 4);
        assertEquals(r, result);
    }

    @Test
    public void testExpandToInclude_ExpandLower() {
        Range r = new Range(2, 6);
        Range result = Range.expandToInclude(r, 1);
        assertEquals(1.0, result.getLowerBound(), 0.0001);
        assertEquals(6.0, result.getUpperBound(), 0.0001);
    }

    @Test
    public void testExpandToInclude_ExpandUpper() {
        Range r = new Range(2, 6);
        Range result = Range.expandToInclude(r, 10);
        assertEquals(2.0, result.getLowerBound(), 0.0001);
        assertEquals(10.0, result.getUpperBound(), 0.0001);
    }

    @Test
    public void testExpandToInclude_NullRange() {
        Range result = Range.expandToInclude(null, 5);
        assertEquals(5.0, result.getLowerBound(), 0.0001);
        assertEquals(5.0, result.getUpperBound(), 0.0001);
    }

    // expand()
    @Test
    public void testExpand() {
        Range r = new Range(2, 6);
        Range result = Range.expand(r, 0.25, 0.5);
        assertEquals(1.0, result.getLowerBound(), 0.0001);
        assertEquals(8.0, result.getUpperBound(), 0.0001);
    }

    // shift()
    @Test
    public void testShift_AllowZeroCrossingTrue() {
        Range r = new Range(-5, -2);
        Range result = Range.shift(r, 6, true);
        assertEquals(1.0, result.getLowerBound(), 0.0001);
        assertEquals(4.0, result.getUpperBound(), 0.0001);
    }

    @Test
    public void testShift_AllowZeroCrossingFalse() {
        Range r = new Range(-5, -2);
        Range result = Range.shift(r, 6, false);
        assertEquals(0.0, result.getLowerBound(), 0.0001);
        assertEquals(1.0, result.getUpperBound(), 0.0001);
    }

    // equals()
    @Test
    public void testEquals_SameRange() {
        Range other = new Range(-1.0, 1.0);
        assertTrue(exampleRange.equals(other));
    }

    @Test
    public void testEquals_DifferentLowerBound() {
        Range other = new Range(-2.0, 1.0);
        assertFalse(exampleRange.equals(other));
    }

    @Test
    public void testEquals_DifferentUpperBound() {
        Range other = new Range(-1.0, 2.0);
        assertFalse(exampleRange.equals(other));
    }

    @Test
    public void testEquals_NonRangeObject() {
        assertFalse(exampleRange.equals("not a range"));
    }

    // hashCode()
    @Test
    public void testHashCode_Called() {
        exampleRange.hashCode();  // Just to execute it
    }

    // toString()
    @Test
    public void testToString_Format() {
        assertEquals("Range[-1.0,1.0]", exampleRange.toString());
    }
}
