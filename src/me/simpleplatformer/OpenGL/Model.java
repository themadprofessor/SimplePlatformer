package me.simpleplatformer.OpenGL;

import me.simpleplatformer.OpenGL.*;
import me.simpleplatformer.Util.*;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;

import java.nio.Buffer;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by User on 08/03/2015.
 */
public class Model implements Renderable, Cleanable, Transformable {
    private VertexArrayObject vao;
    private ArrayList<VertexBufferObject> vbos;
    private VertexBufferObject order;
    private Matrix4f modelMatrix;
    private int modelMatrixLocation;

    public Model(Program program) {
        vao = new VertexArrayObject();
        vbos = new ArrayList<>();
        modelMatrix = new Matrix4f();
        modelMatrixLocation = program.getUniformLocation("model");
    }

    public int addData(Buffer buffer, int glDataType, int length, String name, int entrySize) {
        VertexBufferObject vbo = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, buffer, glDataType, length);
        vao.bind();
        int number = vao.addBuffer(vbo, entrySize);
        vbos.add(vbo);
        vao.unbind();
        return number;
    }

    public int addTexture(Texture texture) {
        vao.bind();
        int number = vao.addBuffer(texture.textureCoords, 2);
        vbos.add(texture.textureCoords);
        vao.unbind();
        return number;
    }

    public void setOrder(Buffer buffer, int glDataType, int length) {
        if (glDataType != GL_UNSIGNED_BYTE && glDataType != GL_UNSIGNED_INT) {
            throw new InvalidParameterException("glDataType Must Be GL_UNSIGNED_BYTE Or GL_UNSIGNED_INT");
        }
        order = new VertexBufferObject(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, glDataType, length);
    }

    @Override
    public void render(Program program) {
        program.setUniformMatrix(modelMatrixLocation, modelMatrix);
        vao.bind();
        vao.enableAllAttribs();
        order.bind();

        glDrawElements(GL_TRIANGLES, order.getSize(), order.getDataType(), 0);

        order.unbind();
        vao.disableAllAttribs();
        vao.unbind();
    }

    public void render(Program program, int... attribs) {
        program.setUniformMatrix(modelMatrixLocation, modelMatrix);
        vao.bind();
        for (int i = 0; i < attribs.length; i++) {
            vao.enableAttrib(i);
        }
        order.bind();

        glDrawElements(GL_TRIANGLES, order.getSize(), order.getDataType(), 0);

        order.unbind();
        for (int i = 0; i < attribs.length; i++) {
            vao.disableAttrib(i);
        }
        vao.unbind();
    }

    @Override
    public void cleanup() {
        vao.delete();
        vbos.forEach(VertexBufferObject::delete);
        order.delete();
    }

    @Override
    public void translate(Vector3f vector) {
        modelMatrix.translate(vector);
    }

    @Override
    public void scale(Vector3f vector) {
        modelMatrix.scale(vector);
    }

    @Override
    public void rotate(Axis axis, float angle) {
        modelMatrix.rotate(angle, axis.toVector());
    }
}
