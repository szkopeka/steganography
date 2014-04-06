package stegp;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SteganographyController {
    
    private SteganographyModel model;
    private SteganographyView view;
    private String  outputFilename, inputPath, textToHide;

    public SteganographyController(final SteganographyModel model, final SteganographyView view) {
        this.model = model;
        this.view = view;

        view.addInsertFileNameButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageFrameAccessibility(false);
                outputFilename = JOptionPane.showInputDialog(view, "Proszę wpisać nazwę pliku wyjściowego");
                view.manageFrameAccessibility(true);
            }
        });
        view.addInsertTextButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.manageFrameAccessibility(false);
                textToHide = JOptionPane.showInputDialog(view, "Proszę wpisać tekst do ukrycia w obrazku");
                view.manageFrameAccessibility(true);
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
                } else {
                    inputPath = null;
                }
                view.manageFrameAccessibility(true);
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
                if (inputPath == null || inputPath.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Nie wybrano pliku źródłowego!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (view.getEncodeRadioButton().isSelected()) {
                    if (textToHide == null || textToHide.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Nie wpisano tekstu do ukrycia!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (outputFilename == null || outputFilename.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Nie wybrano pliku wynikowego!", "Błąd", JOptionPane.ERROR_MESSAGE);
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
                        inputPath = null;
                        outputFilename = null;
                        textToHide = null;
                    }
                } else {
                    JOptionPane.showMessageDialog(view, model.decodeImage(inPath, inFilename), "Ukryta wiadomość", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        );
    }

}
