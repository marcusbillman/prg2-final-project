import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerView {
    private final JFrame frame;
    private JPanel mainPanel;

    private JPanel statusPanel;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    private JTabbedPane tabbedPane;
    private JPanel popupTab;
    private JPanel applicationTab;
    private JPanel terminalTab;
    private JPanel filesTab;
    private JPanel screenTab;
    private JTextArea popupBodyTextArea;
    private JButton popupSendButton;
    private JTextField popupTitleTextField;
    private JTextArea terminalLogTextArea;
    private JTextField terminalCommandTextField;
    private JButton terminalRunButton;
    private JLabel screenLabel;

    public ServerView() {
        frame = new JFrame("Server");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public String getPopupTitle() {
        return popupTitleTextField.getText();
    }

    public String getPopupBody() {
        return popupBodyTextArea.getText();
    }

    public String getTerminalCommand() {
        return terminalCommandTextField.getText();
    }

    public Dimension getTabSize() {
        return screenTab.getSize();
    }

    public void setProgressBarVisible(boolean isVisible) {
        progressBar.setVisible(isVisible);
    }

    public void setStatusLabelText(String text) {
        statusLabel.setText(text);
    }

    public void setScreenIcon(ImageIcon imageIcon) {
        screenLabel.setIcon(imageIcon);
        screenLabel.setSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
    }

    public void appendTerminalLine(String line) {
        this.terminalLogTextArea.append(line);
    }

    // -------------------- Listeners

    public void addPopupSendButtonListener(ActionListener actionListener) {
        popupSendButton.addActionListener(actionListener);
    }

    public void addTerminalRunButtonListener(ActionListener actionListener) {
        terminalRunButton.addActionListener(actionListener);
    }

    public void addTabSwitchListener(ChangeListener changeListener) {
        tabbedPane.addChangeListener(changeListener);
    }
}
