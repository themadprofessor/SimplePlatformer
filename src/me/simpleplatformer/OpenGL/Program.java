package me.simpleplatformer.OpenGL;

import me.simpleplatformer.Util.Matrix4f;
import me.simpleplatformer.Util.Util;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by User on 04/02/2015.
 */
public class Program {
    private int id;
    private HashMap<Integer, Integer> shaders;

    public Program() {
        id = glCreateProgram();
        shaders = new HashMap<>();
    }

    public void addShader(File shader, int glShaderType) {
        try {
            StringBuilder builder = new StringBuilder();
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(shader.toPath());
            lines.forEach(line -> builder.append(line).append("\n"));

            int shaderId = glCreateShader(glShaderType);
            glShaderSource(shaderId, builder.toString());
            glCompileShader(shaderId);
            if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                Util.err(glGetShaderInfoLog(shaderId, glGetShaderi(shaderId, GL_INFO_LOG_LENGTH)));
                glDeleteShader(shaderId);
            }
            glAttachShader(id, shaderId);
            shaders.put(glShaderType, shaderId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkShaders() {
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL11.GL_FALSE) {
            String log = glGetProgramInfoLog(id);
            Util.err(log);
            glDeleteProgram(id);
            shaders.forEach((type, i) -> glDeleteShader(i));
        } else {
            glValidateProgram(id);
        }
    }

    public void use() {
        glUseProgram(id);
    }

    public void disuse() {
        glUseProgram(0);
    }

    public void setUniformTexture(int uniformLocation, int textureNumber) {
        glUniform1i(uniformLocation, textureNumber);
    }

    public void setUniformMatrix(int uniformLocation, Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.store(buffer);
        buffer.flip();
        glUniformMatrix4(uniformLocation, false, buffer);
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(id, name);
    }

    public void bindAttribLocation(int attribNo, String name) {
        glBindAttribLocation(id, attribNo, name);
    }

    public void cleanup() {
        shaders.forEach((type, id) -> glDeleteShader(id));
        glDeleteProgram(id);
    }
}
