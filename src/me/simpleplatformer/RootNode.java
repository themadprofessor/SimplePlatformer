package me.simpleplatformer;

import me.simpleplatformer.OpenGL.Program;
import me.simpleplatformer.Util.Axis;
import me.simpleplatformer.Util.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by User on 12/03/2015.
 */
public class RootNode extends Node {
    private Matrix4f view, projection;
    private int viewLocation, projectionLocation;

    public RootNode(Matrix4f view, Matrix4f projection, Program program) {
        subNodes = new ArrayList<>();
        this.view = view;
        this.projection = projection;
        viewLocation = program.getUniformLocation("view");
        projectionLocation = program.getUniformLocation("proj");
    }

    @Override
    public void translate(Vector3f vector) {
        view.translate(vector);
    }

    @Override
    public void scale(Vector3f vector) {
        view.scale(vector);
    }

    @Override
    public void rotate(Axis axis, float angle) {
        view.rotate(angle, axis.toVector());
    }

    @Override
    public void renderSubNodes(Program program) {
        program.setUniformMatrix(viewLocation, view);
        program.setUniformMatrix(projectionLocation, projection);
        super.renderSubNodes(program);
    }
}
