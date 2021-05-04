import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerController {
    private final ServerView serverView;
    private final NetworkModel networkModel;

    public ServerController(ServerView serverView, NetworkModel networkModel) {
        this.serverView = serverView;
        this.networkModel = networkModel;

        // Attach listeners (end of this file) to view
        this.serverView.addPopupSendButtonListener(new PopupSendButtonListener());

        try {
            this.networkModel.listen(1337);
            this.serverView.setProgressBarVisible(false);
            this.serverView.setStatusLabelText("Connected to " +
                    this.networkModel.getSocket().getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------- Listeners

    private class PopupSendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String body = serverView.getPopupBody();
            String title = serverView.getPopupTitle();
            try {
                networkModel.sendParcel("popup", new String[]{body, title});
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
