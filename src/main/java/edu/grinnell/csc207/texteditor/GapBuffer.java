package edu.grinnell.csc207.texteditor;

/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {

public static final int INIT_SIZE = 10;
    
    private char[] backingArray;
    public int gapStart;
    private int gapEnd;
    private int size;
    
    /**
     * initializes GapBuffer
     */
    public GapBuffer(){
        this.backingArray = new char[INIT_SIZE];
        this.size = 0;
        this.gapStart = 0;
        this.gapEnd = INIT_SIZE;
    }
    
    /**
     * @param ch 
     * inserts character ch into the buffer at the gapStart and then increments
     * gapStart
     *
     */
    public void insert(char ch) {

        backingArray[gapStart] = ch;
        gapStart++;
        size++;
        
        if (gapStart == gapEnd) {
            expandBuffer();
        }
    }
    
    /**
     * deletes the character before the cursor by decrementing gapStart
     * 
     */
    public void delete() {
        
        if (gapStart == 0) {
            throw new UnsupportedOperationException("No character to delete");
        } else {
            gapStart--;
            size--;
        }
        
    }

    /**
     * 
     * @return returns the current gapStart ie. the cursor position
     */
    public int getCursorPosition() {
        return gapStart; 
    }
    
    /**
     * if you can move left, then move the index one to the left
     */
    public void moveLeft() {
        
         if (gapStart == 0) {
            throw new UnsupportedOperationException("Cursor is at the beginning");
        }

        gapStart--;
        gapEnd--;
        backingArray[gapEnd] = backingArray[gapStart];
    }


    /**
     * if you can move right, then move the index one to the right
     */
    public void moveRight() {
        if (gapEnd == backingArray.length) {
            throw new UnsupportedOperationException("Cursor is at the end");
        }

        if (gapStart < gapEnd) {
            backingArray[gapStart] = backingArray[gapEnd];
            gapStart++;
            gapEnd++;
        }
    }

    /**
     * 
     * @return the size of the Backing string
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the character at the specified index.
     *
     * @param i the index of the character
     * @return the character at position i
     */
    public char getChar(int i) {
        if (i < 0 || i >= this.size) {
            throw new UnsupportedOperationException("Invalid position");
        } else {
            return this.backingArray[i];
        }
    }
    
    /**
     * grows the buffer
     */
    private void expandBuffer() {
        int newSize = backingArray.length * 2;
        char[] newBuffer = new char[newSize];
        int afterGapEnd = backingArray.length - gapEnd;
        int newGapEnd = newSize - afterGapEnd;

        System.arraycopy(backingArray, 0, newBuffer, 0, gapStart);

       
        if ((this.size - gapEnd) > 0){
            System.arraycopy(backingArray, gapEnd, newBuffer, newGapEnd, afterGapEnd);
        }

        gapEnd = newGapEnd;
        backingArray = newBuffer;
    }


    /**
     * 
     * @return converts the gapBuffer into an string
     */
    @Override
    public String toString() {
        char[] resultArray = new char[size];

        // Copy characters before the gap
        System.arraycopy(backingArray, 0, resultArray, 0, gapStart);

        // Copy characters after the gap
        System.arraycopy(backingArray, gapEnd, resultArray, gapStart, size - gapStart);

        return new String(resultArray);
    }
}