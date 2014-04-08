package stegp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.metal.MetalComboBoxButton;

public class SteganographyView extends JFrame {

    public static final String openButtonText = "Wybierz plik";
    public static final String insertTextButtonText = "Wprowadz tekst do ukrycia";
    public static final String insertFileNameButtonText = "Wprowadz nazwę pliku wyjściowego";
    public static final String encodeButtonText = "Ukryj wiadomość";
    public static final String decodeButtonText = "Pokaż ukrytą wiadomośc";

    private JButton openButton = new JButton(openButtonText);
    private JButton insertTextButton = new JButton(insertTextButtonText);
    private JButton insertFileNameButton = new JButton(insertFileNameButtonText);
    private JButton codeButton = new JButton(encodeButtonText);
    private JRadioButton encodeRadioButton = new JRadioButton("Zakoduj");
    private JRadioButton decodeRadioButton = new JRadioButton("Dekoduj");
    private ButtonGroup group = new ButtonGroup();

    public SteganographyView() {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(openButton);
        buttonPanel.add(insertTextButton);
        buttonPanel.add(insertFileNameButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        group.add(encodeRadioButton);
        group.add(decodeRadioButton);
        encodeRadioButton.setSelected(true);
        radioPanel.add(encodeRadioButton);
        radioPanel.add(decodeRadioButton);

        JPanel codePanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        codePanel.add(codeButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);
        mainPanel.add(radioPanel, BorderLayout.CENTER);
        mainPanel.add(codePanel, BorderLayout.PAGE_END);
        this.add(mainPanel);

        //ustawienia głównego okna
        this.setTitle("Steganografia");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    JRadioButton getEncodeRadioButton() {
        return encodeRadioButton;
    }

    JRadioButton getDecodeRadioButton() {
        return decodeRadioButton;
    }

    void setOpenButtonText(String text) {
        openButton.setText(text);
    }

    void setInsertFileNameButtonText(String text) {
        insertFileNameButton.setText(text);
    }

    void setCodeButtonText(String text) {
        codeButton.setText(text);
    }

    void manageFrameAccessibility(boolean value) {
        this.setEnabled(value);
    }

    void manageButtonsAccessibility(boolean value) {
        insertTextButton.setEnabled(value);
        insertFileNameButton.setEnabled(value);
    }

    void addOpenButtonListener(ActionListener a) {
        openButton.addActionListener(a);
    }

    void addInsertTextButtonListener(ActionListener a) {
        insertTextButton.addActionListener(a);
    }

    void addInsertFileNameButtonListener(ActionListener a) {
        insertFileNameButton.addActionListener(a);
    }

    void addEncodeRadioButtonListener(ActionListener a) {
        encodeRadioButton.addActionListener(a);
    }

    void addDecodeRadioButtonListener(ActionListener a) {
        decodeRadioButton.addActionListener(a);
    }

    void addCodeButtonListener(ActionListener a) {
        codeButton.addActionListener(a);
    }

    public static void main(String[] args) {
        SteganographyModel model = new SteganographyModel();
        SteganographyView view = new SteganographyView();
        SteganographyController controller = new SteganographyController(model, view);
    }
}
