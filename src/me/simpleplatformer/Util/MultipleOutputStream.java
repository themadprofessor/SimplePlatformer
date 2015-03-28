package me.simpleplatformer.Util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by User on 11/03/2015.
 */
public class MultipleOutputStream extends OutputStream {
    private OutputStream[] streams;

    public MultipleOutputStream(OutputStream... streams) {
        this.streams = streams;
    }

    @Override
    public void write(int b) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(b);
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(b);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(b, off, len);
        }
    }
}
