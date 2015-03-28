package me.simpleplatformer;

import me.simpleplatformer.OpenGL.Model;
import me.simpleplatformer.Util.Renderable;

/**
 * Created by User on 12/03/2015.
 */
public abstract class RenderableNode extends Node implements Renderable {
    protected Model model;

    public void cleanup() {
        model.cleanup();
        super.cleanup();
    }
}
