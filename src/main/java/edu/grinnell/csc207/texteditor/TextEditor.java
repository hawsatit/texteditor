package edu.grinnell.csc207.texteditor;

import com.googlecode.lanterna.TerminalPosition;
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
            screen.setCharacter(col, row, new TextCharacter(character));
            col++;
        }
        
        screen.setCursorPosition(new TerminalPosition(col, 0));
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
            drawBuffer(buf, screen);
        
            boolean isRunning = true;
        
            while (isRunning) {
            KeyStroke stroke = screen.readInput();
                if (stroke.getKeyType() == KeyType.Character){
                    buf.insert(stroke.getCharacter());
                    drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.ArrowLeft){
                   if (buf.getCursorPosition() > 0){
                       buf.moveLeft();
                   }
                   drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.ArrowRight){
                   if (buf.getCursorPosition() < buf.getSize() - 1){
                       buf.moveRight();
                   }
                   drawBuffer(buf, screen);
                } else if (stroke.getKeyType() == KeyType.Backspace){
                    if (buf.getSize() > 0){
                        buf.delete();
                    }
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
