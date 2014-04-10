package stegp;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SteganographyController {

    private SteganographyModel model;
    private SteganographyView view;
    private String outputFilename, inputPath, textToHide;
    private int maxChars;

    public SteganographyController(final SteganographyModel model, final SteganographyView view) {
        this.model = model;
        this.view = view;

        view.addInsertFileNameButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageFrameAccessibility(false);
                outputFilename = JOptionPane.showInputDialog(view, "Proszę wpisać nazwę pliku wyjściowego");
                view.manageFrameAccessibility(true);
                view.toFront();
            }
        });
        view.addInsertTextButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageFrameAccessibility(false);
                textToHide = JOptionPane.showInputDialog(view, "Proszę wpisać tekst do ukrycia w obrazku\n"
                        + "Maksymalna liczba znaków możliwa do zakodowania = " + maxChars);
                view.manageFrameAccessibility(true);
                view.toFront();
            }
        });
        view.addOpenButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageFrameAccessibility(false);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG Images", "jpg", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    inputPath = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        BufferedImage img = ImageIO.read(chooser.getSelectedFile());
                        maxChars = (img.getHeight() * img.getWidth() - 32) / 8;
                    } catch (IOException ex) {
                        Logger.getLogger(SteganographyController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    inputPath = null;
                }
                view.manageFrameAccessibility(true);
                view.toFront();
            }
        });
        view.addEncodeRadioButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageButtonsAccessibility(true);
                view.setCodeButtonText(view.encodeButtonText);
            }
        });
        view.addDecodeRadioButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageButtonsAccessibility(false);
                view.setCodeButtonText(view.decodeButtonText);
            }
        });
        view.addCodeButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isEmptyOrNull(inputPath)) {
                    JOptionPane.showMessageDialog(view, "Nie wybrano pliku źródłowego!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    view.toFront();
                    return;
                } else if (view.getEncodeRadioButton().isSelected()) {
                    if (isEmptyOrNull(textToHide)) {
                        JOptionPane.showMessageDialog(view, "Nie wpisano tekstu do ukrycia!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        view.toFront();
                        return;
                    } else if (isEmptyOrNull(outputFilename)) {
                        JOptionPane.showMessageDialog(view, "Nie wybrano pliku wynikowego!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        view.toFront();
                        return;
                    }
                }
                String fulloutputPath = PathFilter.path(inputPath) + outputFilename + '.' + SteganographyModel.outputFormat;

                String inFilename = PathFilter.filename(inputPath);
                String inPath = PathFilter.path(inputPath);
                String inExt = PathFilter.extension(inputPath);
                String outFilename = PathFilter.filename(fulloutputPath);
                String outPath = PathFilter.path(fulloutputPath);

                if (view.getEncodeRadioButton().isSelected()) {
                    if (model.encodeImage(inPath, inFilename, inExt, outPath, outFilename, textToHide)) {
                        JOptionPane.showMessageDialog(view, "Zapis wiadomości zakończony powodzeniem!", "OK!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    view.toFront();
                } else {
                    String decryptedText = model.decodeImage(inPath, inFilename);
                    if (decryptedText != null && !decryptedText.isEmpty()) {
                        view.makeTextFrame(decryptedText);
                    } else {
                        JOptionPane.showMessageDialog(view, "Odczyt nieudany(brak ukrytej wiadomości?)");
                    }
                    view.toFront();
                }
            }
        }
        );
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }
}
