package me.simpleplatformer.OpenGL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by User on 16/02/2015.
 */
public class Window {
    private GLFWErrorCallback errorCallback;
    private long window;
    int width, height;

    public Window(int width, int height, GLFWErrorCallback errorCallback, String title) {
        this.width = width;
        this.height = height;
        this.errorCallback = errorCallback;
        glfwSetErrorCallback(errorCallback);
        if (glfwInit() != GL_TRUE) {
            throw new RuntimeException("Failed To Initialise GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed To Create Window");
        }
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GLContext.createFromCurrent();
    }

    public void cleanup() {
        errorCallback.release();
        glfwDestroyWindow(window);
    }

    public boolean shouldWindowClose() {
        return glfwWindowShouldClose(window) == GL_FALSE;
    }

    public void updateWindow() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public boolean checkKey(int key, int state) {
        return glfwGetKey(window, key) == state;
    }

    public void setTitle(CharSequence title) {
        glfwSetWindowTitle(window, title);
    }
}
