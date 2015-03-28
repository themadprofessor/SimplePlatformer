package me.simpleplatformer;

import me.simpleplatformer.OpenGL.Program;
import me.simpleplatformer.Util.Cleanable;
import me.simpleplatformer.Util.Renderable;
import me.simpleplatformer.Util.Transformable;

import java.util.ArrayList;

/**
 * Created by User on 11/03/2015.
 */
public abstract class Node implements Transformable, Cleanable {
    ArrayList<Node> subNodes;

    public void addNode(Node node) {
        subNodes.add(node);
    }

    public void removeNode(Node node) {
        subNodes.remove(node);
    }

    public void renderSubNodes(Program program) {
        subNodes.forEach(node -> {
            if (node instanceof RenderableNode) {
                ((RenderableNode) node).render(program);
                node.renderSubNodes(program);
            } else {
                node.renderSubNodes(program);
            }
        });
    }

    public void cleanup() {
        subNodes.forEach(Cleanable::cleanup);
    }
}
