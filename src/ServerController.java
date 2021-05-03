import java.io.IOException;

public class ServerController {
    private final ServerView serverView;
    private final ServerNetworkModel serverNetworkModel;

    public ServerController(ServerView serverView, ServerNetworkModel serverNetworkModel) {
        this.serverView = serverView;
        this.serverNetworkModel = serverNetworkModel;

        try {
            this.serverNetworkModel.listen();
            this.serverView.setProgressBarVisible(false);
            this.serverView.setStatusLabelText("Connected to " +
                    this.serverNetworkModel.getSocket().getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
