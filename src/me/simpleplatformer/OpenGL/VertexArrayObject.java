package me.simpleplatformer.OpenGL;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL45.glCreateVertexArrays;

/**
 * Created by stuart on 11/10/14.
 */
public class VertexArrayObject {
    private int id;
    private int length;

    /**
     * Generates an OpenGL VertexArrayObject
     */
    public VertexArrayObject() {
        id = glCreateVertexArrays();
    }

    /**
     * Binds this VertexArrayObject
     */
    public void bind() {
        glBindVertexArray(id);
    }

    /**
     * Unbinds this VertexArrayObject
     */
    public void unbind() {
        glBindVertexArray(0);
    }

    /**
     * Adds the given VertexBufferObject to this VertexArrayObjects with the given name
     * @param vbo The VertexBufferObject to be added
     * @param defSize The size of each definition<br>E.G. 3 for a 3 dimensional vertex<br>E.G. 4 for a RGBA colour value
     */
    public int addBuffer(VertexBufferObject vbo, int defSize) {
        length++;
        bind();
        vbo.bind();

        enableAttrib(length - 1);
        glVertexAttribPointer(length - 1, defSize, vbo.getDataType(), false, 0, 0);

        vbo.unbind();
        unbind();
        return length - 1;
    }

    /**
     * Delete this VertexArrayObject
     */
    public void delete() {
        bind();
        glDeleteVertexArrays(id);
    }

    /**
     * Enables the given attribute <strong>MUST BE ALREADY ADDED AS A BUFFER</strong>.
     * @param attribNumber The attribute number
     */
    public void enableAttrib(int attribNumber) {
        if (attribNumber >= length) {
            throw new InvalidParameterException("AttribNumber [" + attribNumber + "] Is Larger Then Length [" + length + ']');
        }
        glEnableVertexAttribArray(attribNumber);
    }

    /**
     * Disables the given attribute <strong>MUST ALREADY BE ADDED AS A BUFFER</strong>.
     * @param attribNumber The attribute number
     */
    public void disableAttrib(int attribNumber) {
        if (attribNumber >= length) {
            throw new InvalidParameterException("AttribNumber [" + attribNumber + "] Is Larger Then Length [" + length + ']');
        }
        glDisableVertexAttribArray(attribNumber);
    }

    /**
     * Enables all attributes
     */
    public void enableAllAttribs() {
        for (int i = 0; i == length; i++) {
            glEnableVertexAttribArray(i);
        }
    }

    /**
     * Disables all attributes
     */
    public void disableAllAttribs() {
        for (int i = 0; i == length; i++) {
            glDeleteVertexArrays(i);
        }
    }
}
