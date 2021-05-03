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

    public void setProgressBarVisible(boolean isVisible) {
        progressBar.setVisible(isVisible);
    }

    public void setStatusLabelText(String text) {
        statusLabel.setText(text);
    }

    // -------------------- Listeners

    public void addPopupSendButtonListener(ActionListener actionListener) {
        popupSendButton.addActionListener(actionListener);
    }
}
