package me.simpleplatformer.Util;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by User on 16/02/2015.
 */
public enum Axis {
    X, Y, Z;
    private Vector3f vector;

    Axis() {
        switch (this) {
            case X :
                vector = new Vector3f(1, 0, 0);
                break;
            case Y :
                vector = new Vector3f(0, 1, 0);
                break;
            case Z :
                vector = new Vector3f(0, 0, 1);
                break;
            default:
                vector = new Vector3f();
                break;
        }
    }

    public Vector3f toVector() {
        return vector;
    }
}
