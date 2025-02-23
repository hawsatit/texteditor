package edu.grinnell.csc207.texteditor;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The driver for the TextEditor Application.
 */
public class TextEditor {
    
    public static void drawBuffer(GapBuffer buf, Screen screen) throws IOException {
        screen.clear();
        
        int row = 0;
        int col = 0;
        int size = buf.getSize();

        for (int i = 0; i < size; i++) {
            char character = buf.getChar(i);
            if (character == '\n') {
                row++;
                col = 0;
            } else {
                screen.setCharacter(row, col, new TextCharacter(character));
                col++;
            }
        }

        screen.refresh();
    }
    
    
    public static Screen createScreen() throws IOException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        return screen;
    }

    /**
     * The main entry point for the TextEditor application.
     * @param args command-line arguments.
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        if (args.length != 1) {
            System.err.println("Usage: java TextEditor <filename>");
            System.exit(1);
        }
        
        GapBuffer buf = new GapBuffer();
        String pathString = args[0];
        System.out.format("Loading %s...\n", pathString);
        Path path = Paths.get(pathString);
        
        
        if (Files.exists(path) && Files.isRegularFile(path)){
            String existingContent = Files.readString(path);
            for (int i = 0; i < existingContent.length(); i++){
                buf.insert(existingContent.charAt(i));
            }
            
            Screen screen = createScreen();
            screen.startScreen();
        
            boolean isRunning = true;
        
            while (isRunning) {
            KeyStroke stroke = screen.readInput();
                if (stroke.getKeyType() == KeyType.Character){
                    buf.insert(stroke.getCharacter());
                    drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.ArrowLeft){
                   buf.moveLeft();
                   drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.ArrowRight){
                   buf.moveRight();
                   drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.Backspace){
                    buf.delete();
                    drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.Escape){
                    screen.stopScreen();
                    isRunning = false;
                }
            }
            
            Files.writeString(path, buf.toString());

        }        
           
    }
}
