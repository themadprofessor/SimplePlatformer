package me.simpleplatformer.OpenGL;

import me.simpleplatformer.Util.Cleanable;

/**
 * Created by User on 22/03/2015.
 */
public class Texture implements Cleanable {
    protected VertexBufferObject textureCoords;

    @Override
    public void cleanup() {
        textureCoords.delete();
    }
}
