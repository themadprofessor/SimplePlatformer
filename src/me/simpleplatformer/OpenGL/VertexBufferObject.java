package me.simpleplatformer.OpenGL;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

/**
 * Created by stuart on 10/10/14.
 */
public class VertexBufferObject {
    private int id;
    private int size;
    private int type;
    private int dataType;

    /**
     * Generates an OpenGL VertexBufferObject of type given<br>E.G. GL_ARRAY_BUFFER, GL_ELEMENT_ARRAY_BUFFER
     * @param type Type of OpenGL VertexBufferObject
     */
    public VertexBufferObject(int type) {
        id = glCreateBuffers();
        size = 0;
        dataType = 0;
        this.type = type;
    }

    public VertexBufferObject(int type, Buffer buffer, int glDataType, int length) {
        id = glCreateBuffers();
        this.type = type;
        addData(buffer, length, glDataType);
    }

    /**
     * Binds this VertexObjectBuffer
     */
    public void bind() {
        glBindBuffer(type, id);
    }

    /**
     * Unbinds this VertexObjectBuffer
     */
    public void unbind() {
        glBindBuffer(type, 0);
    }

    /**
     * Stores the given buffer in this VertexObjectBuffer
     * @param buffer Buffer to be stores
     * @param size The number of values stored in the Buffer
     * @param dataType The type of data to be stored<br>E.G. GL_FLOAT, GL_UNSIGNED_BYTE
     */
    public void addData(Buffer buffer, int size, int dataType) {
        bind();
        this.size = size;
        this.dataType = dataType;

        switch (dataType) {
            case GL_FLOAT: glBufferData(type, (FloatBuffer) buffer, GL_STATIC_DRAW);
                break;
            case GL_UNSIGNED_BYTE: glBufferData(type, (ByteBuffer) buffer, GL_STATIC_DRAW);
                break;
            case GL_UNSIGNED_INT: glBufferData(type, (IntBuffer) buffer, GL_STATIC_DRAW);
                break;
        }
        unbind();
    }

    /**
     * Deletes this VertexBufferObject
     */
    public void delete() {
        bind();
        glDeleteBuffers(id);
    }

    /**
     * Returns the number of values stored in the VertexObjectBuffer
     * @return Numbre of values
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the type of VertexBufferObject this VertexBufferObject is
     * @return Type of VertexBufferObject
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the type of data stored in this VertexBufferObjects
     * @return Type of data
     */
    public int getDataType() {
        return dataType;
    }
}
