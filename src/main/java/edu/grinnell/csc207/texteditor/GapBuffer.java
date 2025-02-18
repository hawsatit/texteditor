package edu.grinnell.csc207.texteditor;

/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {

public static final int MAX_SIZE = 1000;
    
    private char[] BackingArray;
    private int index;
    private int size;
    
    /**
     * initializes GapBuffer
     */
    public GapBuffer(){
        this.BackingArray = new char[MAX_SIZE];
        this.size = 0;
        this.index = 0;
    }
    
    /**
     * @param ch 
     * inserts character ch into the buffer at the cursor's current position, 
     * advancing the cursor one position forward
     */
    public void insert(char ch) {
        
        if (this.index < 0 || this.index > this.BackingArray.length) {
        throw new UnsupportedOperationException("Insertion index is out of bounds");
        } else {
            int numToShift = size - index;
            char[] temp = new char[numToShift];
            for (int i = 0; i < numToShift; i++) {
                temp[i] = this.BackingArray[index + i];
            }
            
            this.BackingArray[index] = ch;
            
            for (int i = 0; i < numToShift; i++) {
                this.BackingArray[index + 1 + i] = temp[i];
            }
            
            size++;
            index++;
        }
    }
    
    /**
     * deletes the character at the cursor's current position, moving the cursor one position backwards. 
     * Does nothing if there are no characters in the buffer.
     * 
     */
    public void delete() {
        if (this.index > 0 && this.size > 0){
            for (int i = index - 1; i < size - 1; i++) {
                this.BackingArray[i] = this.BackingArray[i + 1];
            }
            
            BackingArray[size - 1] = '\0';
                    
            size--;
            index--;
        } else {
            throw new UnsupportedOperationException("nothing to delete");
        }
    }

    /**
     * 
     * @return returns the current index ie. the cursor position
     */
    public int getCursorPosition() {
        
        if (this.index < 0 || this.index > this.size) {
        throw new UnsupportedOperationException("Invalid Index Position");
        }
        
        return this.index; 
    }
    
    /**
     * if you can move left, then move the index one to the left
     */
    public void moveLeft() {
        
        //check if cursor is in a valid position and can move left
        if ((this.index < 0 || this.index > this.size) && (this.index <= 0)) {
        throw new UnsupportedOperationException("Unable to move left'");
        } else {
            this.index--;
        }
    }

    /**
     * if you can move right, then move the index one to the right
     */
    public void moveRight() {
        
        //check if cursor is in a valid position and can move right
        if (this.index < 0 || this.index > this.size) {
        throw new UnsupportedOperationException("Unable to move right");
        } else {
            this.index++;
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
        //if i is a valid index in the Backing String, return the character at the positon i
        if (i < 0 || i >= this.size){
        throw new UnsupportedOperationException("i is an invalid position");
        } else {
            return this.BackingArray[i];
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(BackingArray[i]);
        }
        return result.toString();
    }
}
