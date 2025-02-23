package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

public class GapBufferTests {
    @Test
    public void emptyString() {
		GapBuffer test = new GapBuffer();
		assertEquals("", test.toString());
	}
    
    @Test
    public void initializeStringSingleCharacter() {
		GapBuffer test = new GapBuffer();
                test.insert('A');
		assertEquals("A", test.toString());
	}
    
    @Test
    public void DeleteGetSizeGetChar() {
		GapBuffer test = new GapBuffer();
                test.insert('A');
                test.insert('B');
                test.insert('C');
                test.insert('D');
                test.delete();
		assertEquals("ABC", test.toString());
                assertEquals(3, test.getSize());
                assertEquals('B', test.getChar(1));
	}
    
    @Test
    public void MoveAndIndex() {
		GapBuffer test = new GapBuffer();
                test.insert('A');
                test.insert('B');
                test.insert('C');
                test.insert('D');
                test.moveLeft();
                test.moveLeft();
		assertEquals(2, test.getCursorPosition());
                test.moveRight();
                assertEquals(3, test.getCursorPosition());
                
	}
    
    @Test
    public void GreaterThanInit() {
		GapBuffer test = new GapBuffer();
                for (int i = 0; i < 15; i++){
                    test.insert('A');
                }
                assertEquals("AAAAAAAAAAAAAAA", test.toString());
                
	}
    
    @Property
    public boolean PropertyTestRandomSize(@ForAll @IntRange(min = 1, max = 1000) int sz,
                                                                    @ForAll char k) {
	GapBuffer test = new GapBuffer();
	for (int i = 0; i < sz; i++) {
            test.insert(k);
	}
	return test.getSize() == sz;
    }
    
        @Property
    public boolean PropertTestGetRandomIndex(@ForAll @IntRange(min = 1, max = 1000) int sz,
                                                                    @ForAll char k) {
	GapBuffer test = new GapBuffer();
	for (int i = 0; i <= 1000; i++) {
            test.insert(k);
	}
	return test.getChar(sz) == k;
    }
}
