package me.simpleplatformer.Util;


/**
 * Created by User on 17/02/2015.
 */
public class Matrix4f extends org.lwjgl.util.vector.Matrix4f {
    /**
     * Creates a Matrix4f for use as a projection matrix
     * @param fov Filed of View
     * @param width Width of the screen
     * @param height Height of the screen
     * @param near The near plane
     * @param far The far plane
     * @return The projection matrix
     */
    public static Matrix4f projectionMatrix(float fov, int width, int height, float near, float far) {
        Matrix4f matrix4f = new Matrix4f();
        float yScale = (float) (1f / Math.tan((fov / 2) * (float)(Math.PI / 180d)));
        float xScale = yScale / ((float) width / (float) height);
        float length = far - near;

        matrix4f.m00 = xScale;
        matrix4f.m11 = yScale;
        matrix4f.m22 = -((far + near) / length);
        matrix4f.m23 = -1;
        matrix4f.m32 = -((2 * near * far) / length);
        matrix4f.m33 = 0;

        return matrix4f;
    }
}
