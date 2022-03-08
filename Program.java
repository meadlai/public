package com.libra.sinvoice;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        final double sampleRate = 44100.0;
        final double frequency = 35000;
        final double frequency2 = 11900;
        final double frequency3 = 20190;
        final double amplitude = 1.0;
        final double seconds = 10.0;
        final double twoPiF = 1 * Math.PI * frequency;
        final double piF = 1* Math.PI * frequency2;
        final double PiF3 = 1 * Math.PI * frequency3;

        float[] buffer = new float[(int)(seconds * sampleRate)];

        for (int sample = 0; sample < buffer.length; sample++) {
            double time = sample / sampleRate;
            buffer[sample] = (float)(amplitude * (Math.cos(piF * time) * Math.sin(twoPiF * time) * Math.cos(PiF3 * time)));
        }

        final byte[] byteBuffer = new byte[buffer.length * 2];

        int bufferIndex = 0;
        for (int i = 0; i < byteBuffer.length; i++) {
            final int x = (int)(buffer[bufferIndex++] * 32767.0);

            byteBuffer[i++] = (byte)x;
            byteBuffer[i] = (byte)(x >>> 8);
        }

        File out = new File("out10.wav");

        final boolean bigEndian = false;
        final boolean signed = true;

        final int bits = 16;
        final int channels = 1;

        AudioFormat format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
    }
}

