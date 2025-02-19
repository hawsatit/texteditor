package edu.grinnell.csc207.texteditor;

/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {

public static final int INIT_SIZE = 10;
    
    private char[] BackingArray;
    private int gapStart;
    private int gapEnd;
    private int size;
    
    /**
     * initializes GapBuffer
     */
    public GapBuffer(){
        this.BackingArray = new char[INIT_SIZE];
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
        
        if (gapStart == gapEnd) {
            expandBuffer();
        }
        
        BackingArray[gapStart] = ch;
        gapStart++;
        size++;
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
            BackingArray[gapStart] = '\0';
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

        BackingArray[gapEnd - 1] = BackingArray[gapStart - 1];
        gapStart--;
        gapEnd--;
    }

    /**
     * if you can move right, then move the index one to the right
     */
    public void moveRight() {
        
        if (gapEnd == BackingArray.length) {
            throw new UnsupportedOperationException("Cursor is at the end");
        }

        BackingArray[gapStart] = BackingArray[gapEnd];
        gapStart++;
        gapEnd++;
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
        //if i is a valid index in the Backing String, return the character at the positon i
        if (i < 0 || i >= this.size){
        throw new UnsupportedOperationException("i is an invalid position");
        } else {
            return this.BackingArray[i];
        }
    }
    
    private void expandBuffer() {
        int newSize = BackingArray.length * 2;
        char[] newBuffer = new char[newSize];
        int newGapEnd = newSize - (BackingArray.length - gapEnd);

        System.arraycopy(BackingArray, 0, newBuffer, 0, gapStart);

        System.arraycopy(BackingArray, gapEnd, newBuffer, newGapEnd, BackingArray.length - gapEnd);

        gapEnd = newGapEnd;
        BackingArray = newBuffer;
    }

    @Override
    public String toString() {
        char[] resultArray = new char[size];
        int index = 0;

        // Copy before the gap
        for (int i = 0; i < gapStart; i++) {
            resultArray[index++] = BackingArray[i];
        }

        // Copy after the gap
        for (int i = gapEnd; i < BackingArray.length; i++) {
            resultArray[index++] = BackingArray[i];
        }

        return new String(resultArray);
    }
}
