package com.mezzanine.s_expression;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for the SExpression class
 *
 * Created by alex on 8/8/17.
 */
public class SExpressionTest {

    @Test
    public void createEmptySExpression(){
        SExpression s = SExpression.empty();
        assertNotNull(s);
        assertEquals("()", s.toString());
    }

    @Test
    public void setName(){
        SExpression s = SExpression.empty();
        s.name = "alpha";
        assertNotNull(s);
        assertEquals("(alpha)", s.toString());
    }

    @Test
    public void setNameAndValues(){
        SExpression s = SExpression.empty();
        s.name = "alpha";
        s.values.addAll(Arrays.asList("bravo", "charlie"));
        assertNotNull(s);
        assertEquals("(alpha bravo charlie)", s.toString());
    }

    @Test
    public void setNameAndValuesAndAddChildren(){
        SExpression s = SExpression.empty();
        s.name = "alpha";
        s.values.addAll(Arrays.asList("bravo", "charlie"));

        SExpression firstChild = SExpression.empty();
        firstChild.name = "delta";
        firstChild.values.addAll(Arrays.asList("echo", "foxtrot"));
        s.children.add(firstChild);

        SExpression secondChild = SExpression.empty();
        secondChild.name = "golf";
        secondChild.values.addAll(Arrays.asList("hotel", "india"));
        s.children.add(secondChild);

        assertNotNull(s);
        assertEquals("(alpha bravo charlie (delta echo foxtrot) (golf hotel india))", s.toString());
    }

    @Test
    public void createSExpressionFromString() throws SExpressionFormatException {
        SExpression s = SExpression.fromString("(alpha bravo charlie (delta echo foxtrot) (golf hotel india))");

        assertNotNull(s);
        assertEquals("alpha", s.name);
        assertEquals(2, s.values.size());
        assertEquals("bravo", s.values.get(0));
        assertEquals("charlie", s.values.get(1));
        assertEquals(2, s.children.size());

        assertEquals("delta", s.children.get(0).name);
        assertEquals(2, s.children.get(0).values.size());
        assertEquals("echo", s.children.get(0).values.get(0));
        assertEquals("foxtrot", s.children.get(0).values.get(1));
        assertEquals(0, s.children.get(0).children.size());

        assertEquals("golf", s.children.get(1).name);
        assertEquals(2, s.children.get(1).values.size());
        assertEquals("hotel", s.children.get(1).values.get(0));
        assertEquals("india", s.children.get(1).values.get(1));
        assertEquals(0, s.children.get(1).children.size());
    }

    @Test
    public void createSExpressionFromBadlySpacedString() throws SExpressionFormatException {
        SExpression s = SExpression.fromString("(a  b       c  (d      e )  )");

        assertNotNull(s);
        assertEquals("(a b c (d e))", s.toString());

        assertEquals("a", s.name);
        assertEquals(2, s.values.size());
        assertEquals("b", s.values.get(0));
        assertEquals("c", s.values.get(1));
        assertEquals(1, s.children.size());

        assertEquals("d", s.children.get(0).name);
        assertEquals(1, s.children.get(0).values.size());
        assertEquals("e", s.children.get(0).values.get(0));
        assertEquals(0, s.children.get(0).children.size());
    }

    @Test(expected = SExpressionFormatException.class)
    public void createSExpressionWithNullString() throws SExpressionFormatException{
        SExpression.fromString(null);
    }

    @Test(expected = SExpressionFormatException.class)
    public void createSExpressionWithIncorrectlyFormattedString() throws SExpressionFormatException{
        SExpression.fromString("This is not an S-expression!");
    }
}
