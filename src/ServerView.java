import javax.swing.*;

public class ServerView {
    private final JFrame frame;
    private JPanel mainPanel;

    public ServerView() {
        frame = new JFrame("Server");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
