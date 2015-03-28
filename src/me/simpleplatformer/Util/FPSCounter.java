package me.simpleplatformer.Util;

import me.simpleplatformer.OpenGL.Window;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by User on 03/03/2015.
 */
public class FPSCounter {
    private double lastTime;
    private int fps;

    public FPSCounter() {
        lastTime = glfwGetTime();
        fps = 0;
    }

    public void update(Window window) {
        if (glfwGetTime() - lastTime >= 1) {
            window.setTitle("FPS: " + (1000 / fps));
            fps = 0;
            lastTime += 1;
        }
        fps++;
    }
}
