package me.simpleplatformer;

import me.simpleplatformer.OpenGL.Window;
import me.simpleplatformer.Util.KeyFunction;
import me.simpleplatformer.Util.KeyMap;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by User on 12/03/2015.
 */
public class PlayerHandlingThread implements Runnable {
    private Player player;
    private Window window;
    private ArrayList<CollisionBox> collidables;
    private boolean collided;
    private KeyMap playerKeyMap;

    public PlayerHandlingThread(KeyMap player1KeyMap, ArrayList<CollisionBox> collidables, Window window, Player player) {
        this.playerKeyMap = player1KeyMap;
        this.collidables = collidables;
        this.window = window;
        this.player = player;
    }

    @Override
    public void run() {
        collidables.forEach(box -> {
            if (player.collisionBox.isColliding(box)) {
                collided = true;
            }
        });
        player.isFalling = !collided;
        if (window.checkKey(playerKeyMap.getKey(KeyFunction.WALK_RIGHT), GLFW_PRESS) && player.velocity.x <= 0) {
            player.velocity.x += 1;
        }
        if (window.checkKey(playerKeyMap.getKey(KeyFunction.WALK_RIGHT), GLFW_RELEASE) && player.velocity.x > 0) {
            player.velocity.x -= 1;
        }
        if (window.checkKey(playerKeyMap.getKey(KeyFunction.WALK_LEFT), GLFW_PRESS) && player.velocity.x >= 0) {
            player.velocity.x -= 1;
        }
        if (window.checkKey(playerKeyMap.getKey(KeyFunction.WALK_LEFT), GLFW_RELEASE) && player.velocity.x < 0) {
            player.velocity.x += 1;
        }
        if (window.checkKey(playerKeyMap.getKey(KeyFunction.JUMP), GLFW_PRESS) && !player.isFalling) {
            player.velocity.y += 0.9;
            player.isFalling = true;
        }
        if (player.isFalling) {
            player.velocity.y -= 0.01;
        } else {
            player.velocity.y = 0;
        }

        collided = false;
        player.update();
    }
}
