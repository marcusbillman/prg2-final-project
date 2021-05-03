import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerController {
    private final ServerView serverView;
    private final ServerNetworkModel serverNetworkModel;

    public ServerController(ServerView serverView, ServerNetworkModel serverNetworkModel) {
        this.serverView = serverView;
        this.serverNetworkModel = serverNetworkModel;

        // Attach listeners (end of this file) to view
        this.serverView.addPopupSendButtonListener(new PopupSendButtonListener());

        try {
            this.serverNetworkModel.listen();
            this.serverView.setProgressBarVisible(false);
            this.serverView.setStatusLabelText("Connected to " +
                    this.serverNetworkModel.getSocket().getRemoteSocketAddress());
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
                serverNetworkModel.sendParcel("popup", new String[]{body, title});
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
