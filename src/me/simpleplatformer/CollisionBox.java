package me.simpleplatformer;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by User on 16/03/2015.
 */
public class CollisionBox {
    private Vector3f botLeft, topRight;

    public CollisionBox(Vector3f botLeft, Vector3f topRight) {
        this.botLeft = botLeft;
        this.topRight = topRight;
    }

    public boolean isColliding(CollisionBox box) {
        if (box.botLeft.x <= topRight.x && box.topRight.x >= botLeft.x) {
            if (box.botLeft.y <= topRight.y && box.topRight.y >= botLeft.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingRight(CollisionBox box) {
        if (topRight.x >= box.botLeft.x) {
            if (box.botLeft.y <= topRight.y && box.topRight.y >= botLeft.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingLeft(CollisionBox box) {
        if (botLeft.x <= box.topRight.x) {
            if (box.botLeft.y <= topRight.y && box.topRight.y >= botLeft.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingBottom(CollisionBox box) {
        if (botLeft.y <= box.topRight.y) {
            if (box.botLeft.x <= topRight.x && box.topRight.x >= botLeft.x) {
                return true;
            }
        }
        return false;
    }

    public void translate(Vector3f vector) {
        botLeft.translate(vector.x, vector.y, vector.z);
        topRight.translate(vector.x, vector.y, vector.z);
    }
}
