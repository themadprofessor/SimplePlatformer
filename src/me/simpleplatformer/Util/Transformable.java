package me.simpleplatformer.Util;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by User on 07/03/2015.
 */
public interface Transformable {
    public abstract void translate(Vector3f vector);
    public abstract void scale(Vector3f vector);
    public abstract void rotate(Axis axis, float angle);
}
