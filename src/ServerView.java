import javax.swing.*;
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

    public void setProgressBarVisible(boolean isVisible) {
        progressBar.setVisible(isVisible);
    }

    public void setStatusLabelText(String text) {
        statusLabel.setText(text);
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
}
