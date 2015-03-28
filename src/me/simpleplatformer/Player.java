package me.simpleplatformer;

import me.simpleplatformer.OpenGL.Model;
import me.simpleplatformer.OpenGL.Program;
import me.simpleplatformer.Util.Axis;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by User on 12/03/2015.
 */
public class Player extends RenderableNode {
    public Vector3f velocity;
    public Vector3f centrePos;
    public float width, height;
    public boolean isFalling = true;
    public CollisionBox collisionBox;

    public Player(Model m, Vector3f centrePos, float width, float height) {
        subNodes = new ArrayList<>();
        model = m;
        this.centrePos = centrePos;
        velocity = new Vector3f();
        this.width = width;
        this.height = height;
        collisionBox = new CollisionBox(new Vector3f(centrePos.x - width / 2, centrePos.y - height / 2, 0), new Vector3f(centrePos.x + width / 2, centrePos.y + height / 2, 0));
    }

    public void update() {
        translate(velocity);
    }

    @Override
    public void render(Program program) {
        model.render(program);
    }

    @Override
    public void translate(Vector3f vector) {
        centrePos.translate(vector.x, vector.y, vector.z);
        model.translate(new Vector3f(vector.x / 100, vector.y / 100, vector.z / 100));
        collisionBox.translate(vector);
    }

    @Override
    public void scale(Vector3f vector) {
        model.scale(vector);
        height = height * vector.y;
        width = width * vector.z;
    }

    @Override
    public void rotate(Axis axis, float angle) {
        model.rotate(axis, angle);
    }
}
