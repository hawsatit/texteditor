package edu.grinnell.csc207.texteditor;

/**
 * A naive implementation of a text buffer using a <code>String</code>.
 */
public class SimpleStringBuffer {

    private String backingString;
    private int index;

    /**
     * Initializes an empty buffer with cursor at position 0.
     */
    public SimpleStringBuffer() {
        this.backingString = "";
        this.index = 0;
    }

    /**
     * Inserts character ch into the buffer at the cursor's current position,
     * advancing the cursor one position forward.
     *
     * @param ch the character to insert
     */
    public void insert(char ch) {
        if (index < 0 || index > backingString.length()) {
            throw new UnsupportedOperationException("Insertion index is out of bounds");
        }
        String firstPart = backingString.substring(0, index);
        String secondPart = backingString.substring(index);
        backingString = firstPart + ch + secondPart;
        index++;
    }

    /**
     * Deletes the character before the cursor, moving the cursor one position backwards.
     * Does nothing if there are no characters before the cursor.
     */
    public void delete() {
        if (index > 0 && backingString.length() > 0) {
            String firstPart = backingString.substring(0, index - 1);
            String secondPart = backingString.substring(index);
            backingString = firstPart + secondPart;
            index--;
        }
    }

    /**
     * Returns the current cursor position.
     *
     * @return the cursor position
     */
    public int getCursorPosition() {
        return index;
    }

    /**
     * Moves the cursor one position to the left if possible.
     */
    public void moveLeft() {
        if (index > 0) {
            index--;
        }
    }

    /**
     * Moves the cursor one position to the right if possible.
     */
    public void moveRight() {
        if (index < backingString.length()) {
            index++;
        }
    }

    /**
     * Returns the number of characters in the buffer.
     *
     * @return the size of the buffer
     */
    public int getSize() {
        return backingString.length();
    }

    /**
     * Returns the character at the specified index.
     *
     * @param i the index of the character
     * @return the character at position i
     * @throws UnsupportedOperationException if the index is invalid
     */
    public char getChar(int i) {
        if (i < 0 || i >= backingString.length()) {
            throw new UnsupportedOperationException("Invalid index");
        }
        return backingString.charAt(i);
    }

    /**
     * Returns the contents of the buffer as a string.
     *
     * @return the buffer as a string
     */
    @Override
    public String toString() {
        return backingString;
    }
}