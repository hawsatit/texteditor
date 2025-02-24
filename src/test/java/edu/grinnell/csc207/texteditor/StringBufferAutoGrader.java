package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class StringBufferAutoGrader {
    private SimpleStringBuffer MakeSimpleStringBuffer(String s) {
        SimpleStringBuffer buf = new SimpleStringBuffer();
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        return buf;
    }

    @Test
    @DisplayName("Gap: hello end-to-end")
    public void helloExampleTest() {
        SimpleStringBuffer buffer = new SimpleStringBuffer();
        buffer.insert('h');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        buffer.insert(' ');
        buffer.insert('w');
        buffer.insert('o');
        buffer.insert('r');
        buffer.insert('l');
        buffer.insert('d');
    
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.insert('!');
    
        buffer.moveLeft();
        buffer.delete();
        buffer.delete();
        buffer.delete();
        buffer.delete();
        buffer.delete();
    
        buffer.insert('a');
        buffer.insert('b');
        buffer.insert('c');
        assertEquals("abc! world", buffer.toString());
    }

    @Test
    @DisplayName("Gap: empty")
    public void emptyBufTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("");
        assertEquals(0, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
    }

    @Test
    @DisplayName("Gap: cursor movement")
    public void cursorMovementTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("abc");
        assertEquals(3, buf.getCursorPosition(), "initial cursor");
        buf.moveLeft();
        assertEquals(2, buf.getCursorPosition(), "after L");
        buf.moveLeft();
        assertEquals(1, buf.getCursorPosition(), "after LL");
        buf.moveLeft();
        assertEquals(0, buf.getCursorPosition(), "after LLL");
        buf.moveLeft();
        assertEquals(0, buf.getCursorPosition(), "after LLLL");
        buf.moveRight();
        assertEquals(1, buf.getCursorPosition(), "after LLLLR");
        buf.moveRight();
        assertEquals(2, buf.getCursorPosition(), "after LLLLRR");
        buf.moveRight();
        assertEquals(3, buf.getCursorPosition(), "after LLLLRRR");
        buf.moveRight();
        assertEquals(3, buf.getCursorPosition(), "after LLLLRRRR");
    }

    @Test
    @DisplayName("Gap: insert middle")
    public void cursorInsertMiddleTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("abc");
        buf.moveLeft();
        buf.moveLeft();
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(3, buf.getCursorPosition(), "cursor");
        assertEquals("a!!bc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete middle")
    public void cursorDeleteMiddleTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("abc");
        buf.moveLeft();
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("c", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: insert front")
    public void cursorInsertFrontTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(2, buf.getCursorPosition(), "cursor");
        assertEquals("!!abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete front")
    public void cursorDeleteFrontTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.delete();
        assertEquals(3, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete end")
    public void cursorDeleteEndTest() {
        SimpleStringBuffer buf = MakeSimpleStringBuffer("abc");
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(1, buf.getCursorPosition(), "cursor");
        assertEquals("a", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: big buffer")
    public void bigBufferTest() {
        SimpleStringBuffer buf = new SimpleStringBuffer();
        for (int i = 0; i < 16384; i++) {
            buf.insert((char) (i % 10 + '0'));
        }
        assertEquals(16384, buf.getSize(), "size");
        assertEquals(16384, buf.getCursorPosition(), "cursor");

        for (int i = 0; i < 300; i++) {
            buf.moveLeft();
        }
        buf.insert('!');
        buf.insert('!');
        buf.delete();
        assertEquals(16385, buf.getSize(), "size");
        assertEquals(16085, buf.getCursorPosition(), "cursor");
    }
}