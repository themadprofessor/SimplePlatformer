package me.simpleplatformer.Util;

import org.lwjgl.opengl.GL30;
import org.lwjgl.util.glu.GLU;

import javax.swing.*;
import java.nio.charset.Charset;
import java.time.LocalTime;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by User on 30/01/2015.
 */
public class Util {
    public static boolean debug = true;

    public static void out(CharSequence msg) {
        System.out.println("[" + LocalTime.now() + "] " + msg);
    }

    public static void err(CharSequence err) {
        System.err.println("[" + LocalTime.now() + "] " + err);
    }

    public static void err(Exception e) {
        if (debug) {
            System.err.print("[" + LocalTime.now() + "] ");
            e.printStackTrace();
        } else {
            System.err.println("[" + LocalTime.now() + "] " + e.getLocalizedMessage());
        }
    }

    public static void showErr(Exception e) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel label = new JLabel(e.getLocalizedMessage());
            panel.add(label);
            frame.add(panel);
            frame.pack();
            frame.setTitle("Error HALP MEH");
            frame.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            Util.err(e);
        }
    }

    public static void checkGlError() {
        int code = glGetError();
        if (code != GL_NO_ERROR) {
            String error;
            switch (code) {
                case GL_INVALID_ENUM :
                    error = "Invalid Enum";
                    break;
                case GL_INVALID_VALUE :
                    error = "Invalid Value";
                    break;
                case GL_INVALID_OPERATION :
                    error = "Invalid Operation";
                    break;
                case GL30.GL_INVALID_FRAMEBUFFER_OPERATION :
                    error = "Invalid Framebuffer Operation";
                    break;
                case GL_OUT_OF_MEMORY :
                    error = "Out Out Of Memory";
                    break;
                case GL_STACK_UNDERFLOW :
                    error = "Stack Underflow";
                    break;
                case GL_STACK_OVERFLOW :
                    error = "Stack Overflow";
                    break;
                default:
                    error = "Unknown Error";
                    break;
            }
            Util.err("OpenGL Error [" + error + ']');
        }
    }
}
