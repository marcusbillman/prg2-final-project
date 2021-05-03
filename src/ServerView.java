import javax.swing.*;

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

    public ServerView() {
        frame = new JFrame("Server");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void setProgressBarVisible(boolean isVisible) {
        progressBar.setVisible(isVisible);
    }

    public void setStatusLabelText(String text) {
        statusLabel.setText(text);
    }
}
