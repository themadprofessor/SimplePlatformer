package me.simpleplatformer.OpenGL;

import me.simpleplatformer.Util.Util;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Created by User on 13/02/2015.
 */
public class TextureMap {
    int id;
    int glTextureName;
    int glTextureType;

    public TextureMap(File textureFile) {
        glTextureType = GL_TEXTURE_2D;
        glTextureName = GL_TEXTURE0;

        id = glGenTextures();
        glActiveTexture(glTextureName);
        glBindTexture(glTextureType, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(textureFile));
            BufferedImage image = ImageIO.read(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * image.getHeight() * image.getWidth());
            int[] pixelData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            for(int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixelData[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }
            buffer.flip();
            in.close();
            glTexImage2D(glTextureType, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glGenerateMipmap(glTextureType);
            glTexParameteri(glTextureType, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(glTextureType, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        } catch (IOException e) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    Util.err(e1);
                }
            }
            Util.err(e);
        }
    }

    public void bind() {
        glActiveTexture(glTextureName);
        glBindTexture(glTextureType, id);
    }

    public void unbind() {
        glActiveTexture(0);
        glBindTexture(glTextureType, 0);
    }
}
