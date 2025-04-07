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
     * Initializes a new GapBuffer instance.
     */
    public GapBuffer() {
        this.backingArray = new char[INIT_SIZE];
        this.size = 0;
        this.gapStart = 0;
        this.gapEnd = INIT_SIZE;
    }

    /**
     * Inserts character ch into the buffer at the gapStart and then
     * increments gapStart.
     *
     * @param ch the character to insert
     */
    public void insert(char ch) {
        expandBuffer();
        backingArray[gapStart] = ch;
        gapStart++;
        size++;
    }

    /**
     * Deletes the character before the cursor by decrementing gapStart.
     */
    public void delete() {
        if (gapStart > 0) {
            gapStart--;
            size--;
        }
    }

    /**
     * Returns the current gapStart, i.e., the cursor position.
     *
     * @return the current cursor position
     */
    public int getCursorPosition() {
        return gapStart;
    }

    /**
     * Moves the cursor one position to the left if possible.
     */
    public void moveLeft() {
        if (gapStart > 0) {
            gapStart--;
            gapEnd--;
            backingArray[gapEnd] = backingArray[gapStart];
        }
    }

    /**
     * Moves the cursor one position to the right if possible.
     */
    public void moveRight() {
        if (gapEnd < backingArray.length) {
            backingArray[gapStart] = backingArray[gapEnd];
            gapStart++;
            gapEnd++;
        }
    }

    /**
     * Returns the current number of characters in the buffer.
     *
     * @return the size of the buffer
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the character at the specified index.
     *
     * @param i the index of the character
     * @return the character at position i
     * @throws UnsupportedOperationException if the index is invalid
     */
    public char getChar(int i) {
        if (i < 0 || i >= this.size) {
            throw new UnsupportedOperationException("Invalid position");
        }

        if (i < gapStart) {
            return this.backingArray[i];
        } else {
            return this.backingArray[gapEnd + i - gapStart];
        }
    }

    /**
     * Grows the buffer when the gap is full.
     */
    private void expandBuffer() {
        if (gapStart == gapEnd) {
            int newSize = backingArray.length * 2;
            char[] newBuffer = new char[newSize];
            int afterGapEnd = this.size - gapStart;
            int newGapEnd = newSize - afterGapEnd;

            System.arraycopy(backingArray, 0, newBuffer, 0, gapStart);
            System.arraycopy(backingArray, gapEnd, newBuffer, newGapEnd, afterGapEnd);

            gapEnd = newGapEnd;
            backingArray = newBuffer;
        }
    }

    /**
     * Converts the gap buffer into a String.
     *
     * @return the contents of the buffer as a string
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
