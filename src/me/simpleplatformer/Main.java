package me.simpleplatformer;

import me.simpleplatformer.OpenGL.Model;
import me.simpleplatformer.OpenGL.Program;
import me.simpleplatformer.OpenGL.TextureMap;
import me.simpleplatformer.OpenGL.Window;
import me.simpleplatformer.Util.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.glfw.GLFW.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 25/02/2015.
 */
public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        try {
            LocalTime time = LocalTime.now();
            LocalDate date = LocalDate.now();
            String timeDate = time.toString().replace(":", ".") + "_" + date.toString().replace(":", ".");
            FileOutputStream errFile = new FileOutputStream("logs/" + timeDate + " err.log");
            FileOutputStream outFile = new FileOutputStream("logs/" + timeDate + " out.log");
            FileOutputStream fullFile = new FileOutputStream("logs/" + timeDate + " full.log");
            MultipleOutputStream errStream = new MultipleOutputStream(System.err, errFile, fullFile);
            MultipleOutputStream outStream = new MultipleOutputStream(System.out, outFile, fullFile);
            System.setErr(new PrintStream(errStream));
            System.setOut(new PrintStream(outStream));
        } catch (FileNotFoundException e) {
            Util.err(e);
        }

        //System.setProperty("org.lwjgl.librarypath","/home/stuart/IdeaProjects/SimplePlatformer/res/lwjgl-3/native/linux/x64");
        System.setProperty("org.lwjgl.librarypath","D:\\IdeaProjects\\SimplePlatformer\\res\\lwjgl-3\\native\\windows\\x64");

        Window window = new Window(800, 600, Callbacks.errorCallbackThrow(), "Simple Game");
        Util.out("Created Window");

        Matrix4f projectionMatrix = Matrix4f.projectionMatrix(60f, 800, 600, 0.1f, 100f);
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.translate(new Vector3f(0, 0, -1));

        Program program = new Program();
        program.addShader(new File("assets/shaders/shader.vert"), GL_VERTEX_SHADER);
        program.addShader(new File("assets/shaders/shader.frag"), GL_FRAGMENT_SHADER);
        program.bindAttribLocation(0, "vert");
        program.bindAttribLocation(1, "vertTexCoord");
        program.linkShaders();
        program.use();
        program.setUniformTexture(program.getUniformLocation("sampler"), 0);
        Util.out("Setup Shaders");

        TextureMap textures = new TextureMap(new File("assets/textures/textures.png"));

        float[] playerVerts = new float[] {
                -0.75f, 0.1f, 0f,    // Left top         ID: 0
                -0.75f, 0f, 0f,      // Left bottom      ID: 1
                -0.6f, 0f, 0f,       // Right bottom     ID: 2
                -0.6f, 0.1f, 0f      // Right top       ID: 3
        };
        byte[] playerOrder = new byte[] {
                // Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0
        };
        float[] playerTexs = new float[] {
                0, 0.5f,
                0, 1,
                0.5f, 1,
                0.5f, 0.5f
        };
        FloatBuffer playerVertsBuffer = BufferUtils.createFloatBuffer(playerVerts.length);
        playerVertsBuffer.put(playerVerts);
        playerVertsBuffer.flip();
        ByteBuffer playerOrderBuffer = BufferUtils.createByteBuffer(playerOrder.length);
        playerOrderBuffer.put(playerOrder);
        playerOrderBuffer.flip();
        FloatBuffer playerTexBuffer = BufferUtils.createFloatBuffer(playerTexs.length);
        playerTexBuffer.put(playerTexs);
        playerTexBuffer.flip();
        Model playerModel = new Model(program);
        playerModel.addData(playerVertsBuffer, GL_FLOAT, playerVerts.length, "verts", 3);
        playerModel.addData(playerTexBuffer, GL_FLOAT, playerTexs.length, "texs", 2);
        playerModel.setOrder(playerOrderBuffer, GL_UNSIGNED_BYTE, playerOrder.length);
        //playerModel.setTexture(new Texture(new File("assets/textures/pedro.png")));

        float[] player2Verts = new float[] {
                0.75f, 0.5f, 0f,    // Left top         ID: 0
                0.75f, 0.4f, 0f,      // Left bottom      ID: 1
                0.6f, 0.4f, 0f,       // Right bottom     ID: 2
                0.6f, 0.5f, 0f      // Right top       ID: 3
        };
        byte[] player2Order = new byte[] {
                // Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0
        };
        float[] player2Texs = new float[] {
                0.5f, 0,
                0.5f, 0.5f,
                0, 0.5f,
                0, 0
        };
        FloatBuffer player2VertsBuffer = BufferUtils.createFloatBuffer(player2Verts.length);
        player2VertsBuffer.put(player2Verts);
        player2VertsBuffer.flip();
        ByteBuffer player2OrderBuffer = BufferUtils.createByteBuffer(player2Order.length);
        player2OrderBuffer.put(player2Order);
        player2OrderBuffer.flip();
        FloatBuffer player2TexBuffer = BufferUtils.createFloatBuffer(player2Texs.length);
        player2TexBuffer.put(player2Texs);
        player2TexBuffer.flip();
        Model player2Model = new Model(program);
        player2Model.addData(player2VertsBuffer, GL_FLOAT, player2Verts.length, "verts", 3);
        player2Model.addData(player2TexBuffer, GL_FLOAT, player2Texs.length, "texs", 2);
        player2Model.setOrder(player2OrderBuffer, GL_UNSIGNED_BYTE, player2Order.length);
        Util.out("Setup Player");

        float[] platformVerts = new float[] {
                -0.75f, -0.25f, 0f,    // Left top         ID: 0
                -0.75f, -0.3f, 0f,     // Left bottom      ID: 1
                -0.25f, -0.3f, 0f,     // Right bottom     ID: 2
                -0.25f, -0.25f, 0f     // Right top       ID: 3
        };
        byte[] platformOrder = new byte[] {
                // Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0
        };
        float[] platformTex = new float[] {
                1f, 0,
                1f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0
        };
        FloatBuffer platformVertsBuff = BufferUtils.createFloatBuffer(platformVerts.length);
        platformVertsBuff.put(platformVerts);
        platformVertsBuff.flip();
        ByteBuffer platformOrderBuff = BufferUtils.createByteBuffer(platformOrder.length);
        platformOrderBuff.put(platformOrder);
        platformOrderBuff.flip();
        FloatBuffer platformTexBuff = BufferUtils.createFloatBuffer(platformTex.length);
        platformTexBuff.put(platformTex);
        platformTexBuff.flip();
        Model platformModel = new Model(program);
        platformModel.addData(platformVertsBuff, GL_FLOAT, platformVerts.length, "verts", 3);
        platformModel.addData(platformTexBuff, GL_FLOAT, platformTex.length, "tex", 2);
        platformModel.setOrder(platformOrderBuff, GL_UNSIGNED_BYTE, platformOrder.length);

        float[] platform2Verts = new float[] {
                0.25f, 0.05f, 0f,    // Left top         ID: 0
                0.25f, 0f, 0f,     // Left bottom      ID: 1
                0.75f, 0f, 0f,     // Right bottom     ID: 2
                0.75f, 0.05f, 0f     // Right top       ID: 3
        };
        byte[] platform2Order = new byte[] {
                // Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0
        };
        float[] platform2Tex = new float[] {
                1f, 0,
                1f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0
        };
        FloatBuffer platform2VertsBuff = BufferUtils.createFloatBuffer(platform2Verts.length);
        platform2VertsBuff.put(platform2Verts);
        platform2VertsBuff.flip();
        ByteBuffer platform2OrderBuff = BufferUtils.createByteBuffer(platform2Order.length);
        platform2OrderBuff.put(platform2Order);
        platform2OrderBuff.flip();
        FloatBuffer platform2TexBuff = BufferUtils.createFloatBuffer(platform2Tex.length);
        platform2TexBuff.put(platform2Tex);
        platform2TexBuff.flip();
        Model platform2Model = new Model(program);
        platform2Model.addData(platform2VertsBuff, GL_FLOAT, platform2Verts.length, "verts", 3);
        platform2Model.addData(platform2TexBuff, GL_FLOAT, platform2Tex.length, "tex", 2);
        platform2Model.setOrder(platform2OrderBuff, GL_UNSIGNED_BYTE, platform2Order.length);

        RootNode rootNode = new RootNode(viewMatrix, projectionMatrix, program);
        Player player = new Player(playerModel, new Vector3f(-67.5f, 5f, 0), 15, 10);
        Player player2 = new Player(player2Model, new Vector3f(67.5f, 45f, 0), 15, 10);
        Platform platform = new Platform(platformModel, new Vector3f(-50f, -27.5f, 0f), 50, 5);
        Platform platform2 = new Platform(platform2Model, new Vector3f(50f, 2.5f, 0f), 50, 5);
        rootNode.addNode(player);
        rootNode.addNode(player2);
        rootNode.addNode(platform);
        rootNode.addNode(platform2);
        Util.out("Setup Scene Graph");

        ScheduledExecutorService playerHandlingThread = Executors.newScheduledThreadPool(2);
        KeyMap player1KeyMap = new KeyMap();
        player1KeyMap.setKeyFunction(KeyFunction.JUMP, GLFW_KEY_LEFT_SHIFT);
        player1KeyMap.setKeyFunction(KeyFunction.WALK_LEFT, GLFW_KEY_A);
        player1KeyMap.setKeyFunction(KeyFunction.WALK_RIGHT, GLFW_KEY_D);
        ArrayList<CollisionBox> player1Collidables = new ArrayList<>();
        player1Collidables.add(platform.collisionBox);
        player1Collidables.add(platform2.collisionBox);
        playerHandlingThread.scheduleAtFixedRate(new PlayerHandlingThread(player1KeyMap, player1Collidables, window, player), 0, 17, TimeUnit.MILLISECONDS);

        KeyMap player2KeyMap = new KeyMap();
        player2KeyMap.setKeyFunction(KeyFunction.JUMP, GLFW_KEY_RIGHT_SHIFT);
        player2KeyMap.setKeyFunction(KeyFunction.WALK_LEFT, GLFW_KEY_LEFT);
        player2KeyMap.setKeyFunction(KeyFunction.WALK_RIGHT, GLFW_KEY_RIGHT);
        ArrayList<CollisionBox> player2Collidables = new ArrayList<>();
        player2Collidables.add(platform.collisionBox);
        player2Collidables.add(platform2.collisionBox);
        playerHandlingThread.scheduleAtFixedRate(new PlayerHandlingThread(player2KeyMap, player2Collidables, window, player2), 0, 17, TimeUnit.MILLISECONDS);

        glClearColor(0.275f, 0.234f, 0.827f, 0.0f);
        FPSCounter counter = new FPSCounter();
        Util.out("Entering Render Loop");
        while (window.shouldWindowClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            counter.update(window);

            rootNode.renderSubNodes(program);

            window.updateWindow();
        }

        window.cleanup();
        program.cleanup();
        playerHandlingThread.shutdownNow();
        rootNode.cleanup();
    }
}
