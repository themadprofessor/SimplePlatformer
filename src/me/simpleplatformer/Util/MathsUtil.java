package me.simpleplatformer.Util;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Created by User on 20/02/2015.
 */
public class MathsUtil {
    public static float degreesToRadians(float degrees) {
        return degrees * (float)(Math.PI / 180d);
    }

    public static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }
}
