package stegp;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class SteganographyModel {

    public static final String outputFormat = "png";
    public static String outputPath = "C:\\Users\\Artur\\Documents\\NetBeansProjects\\steganography\\";

    public boolean encodeImage(String inPath, String inFilename, String inExt, String outPath, String outFilename, String message) {
        BufferedImage originalImage = readImage(makePathToImage(inPath, inFilename, inExt));
        BufferedImage encodedImage = addText(originalImage, message);
        return writeImage(encodedImage, new File(makePathToImage(outPath, outFilename, outputFormat)), outputFormat);
    }

    public String decodeImage(String inPath, String inFilename) {
        BufferedImage image = readImage(makePathToImage(inPath, inFilename, outputFormat));
        byte[] img = convertImageToBytes(image);
        byte[] text = decodeText(img);
        return (new String(text));
    }

    private String makePathToImage(String path, String filename, String ext) {
        return path + "\\" + filename + "." + ext;
    }

    private BufferedImage addText(BufferedImage image, String text) {
        byte[] img = convertImageToBytes(image);
        byte[] msg = text.getBytes();
        byte[] len = convertIntToBytes(msg.length);

        try {
            encodeText(img, len, 0);
            encodeText(img, msg, 32);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Niestety, tekst okazał się zbyt długi!", "Błąd!", JOptionPane.ERROR_MESSAGE);
        }
        return image;
    }

    private BufferedImage readImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie udało się otworzyć pliku");
        }
        return img;
    }

    private boolean writeImage(BufferedImage image, File file, String ext) {
        try {
            file.delete();
            ImageIO.write(image, ext, file);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nie udało się zapisać pliku z obrazem!", "Błąd!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private byte[] convertImageToBytes(BufferedImage image) {
        WritableRaster raster = image.getRaster();
        DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
        return buffer.getData();
    }

    private byte[] convertIntToBytes(int i) {
        byte byte3 = (byte) ((i & 0xFF000000) >>> 24);
        byte byte2 = (byte) ((i & 0x00FF0000) >>> 16);
        byte byte1 = (byte) ((i & 0x0000FF00) >>> 8);
        byte byte0 = (byte) ((i & 0x000000FF));
        return (new byte[]{byte3, byte2, byte1, byte0});
    }

    private byte[] encodeText(byte[] image, byte[] aditionalText, int offset) throws IllegalAccessException {
        if (aditionalText.length + offset > image.length) {
            throw new IllegalArgumentException("Plik jest zbyt mały, aby ukryć w nim zadaną ilość tekstu!");
        }
        for (int i = 0; i < aditionalText.length; i++) {
            int add = aditionalText[i];
            for (int bit = 7; bit >= 0; bit--, offset++) {
                int b = (add >>> bit) & 1;
                image[offset] = (byte) ((image[offset] & 0xFE) | b);
            }
        }
        return image;
    }

    private byte[] decodeText(byte[] image) {
        int length = 0;
        int offset = 32;
        for (int i = 0; i < 32; ++i) {
            length = (length << 1) | (image[i] & 1);
        }

        byte[] result = new byte[length];

        for (int b = 0; b < result.length; ++b) {
            for (int i = 0; i < 8; ++i, ++offset) {
                result[b] = (byte) ((result[b] << 1) | (image[offset] & 1));
            }
        }
        return result;
    }
}
