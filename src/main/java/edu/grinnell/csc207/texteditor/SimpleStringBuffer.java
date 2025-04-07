package edu.grinnell.csc207.texteditor;

/**
 * A naive implementation of a text buffer using a <code>String</code>.
 */
public class SimpleStringBuffer {

    private String BackingString;
    private int index;

    public SimpleStringBuffer() {
        this.BackingString = "";
        this.index = 0;
    }

    /**
     *
     * @param ch inserts character ch into the buffer at the cursor's current
     * position, advancing the cursor one position forward
     */
    public void insert(char ch) {

        //checks if the index is in a possible position
        if (this.index < 0 || this.index > this.BackingString.length()) {
            throw new UnsupportedOperationException("Insertion index is out of bounds");
        }

        String firstPart = this.BackingString.substring(0, this.index);
        String secondPart = this.BackingString.substring(this.index);
        this.BackingString = firstPart + ch + secondPart;
        this.index++;

    }

    /**
     * deletes the character at the cursor's current position, moving the cursor
     * one position backwards. Does nothing if there are no characters in the
     * buffer.
     *
     */
    public void delete() {
        if (this.index > 0 && this.BackingString.length() > 0) {
            String firstPart = this.BackingString.substring(0, this.index - 1);
            String secondPart = this.BackingString.substring(this.index);
            this.BackingString = firstPart + secondPart;
            this.index--;
        }
    }

    /**
     *
     * @return returns the current index ie. the cursor position
     */
    public int getCursorPosition() {

        return this.index;
    }

    /**
     * if you can move left, then move the index one to the left
     */
    public void moveLeft() {

        //check if cursor is in a valid position and can move left
        if (this.index > 0) {
            this.index--;
        }
    }

    /**
     * if you can move right, then move the index one to the right
     */
    public void moveRight() {

        //check if cursor is in a valid position and can move right
        if (this.index < this.BackingString.length()) {
            this.index++;
        }
    }

    /**
     *
     * @return the size of the Backing string if it is initialized
     */
    public int getSize() {

        return this.BackingString.length();
    }

    /**
     * Returns the character at the specified index.
     *
     * @param i the index of the character
     * @return the character at position i
     */
    public char getChar(int i) {

        //if i is a valid index in the Backing String, return the character at the positon i
        if (i < 0 || i >= this.BackingString.length()) {
            throw new UnsupportedOperationException("Invalid Index");
        } else {
            return this.BackingString.charAt(i);
        }
    }

    /**
     * Returns the full backing string.
     */
    @Override
    public String toString() {
        if (this.BackingString == null) {
            throw new UnsupportedOperationException("String is Empty");
        }
        return this.BackingString;

    }
}
