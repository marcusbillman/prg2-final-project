public class ServerController {
    private final ServerView serverView;
    private final ServerNetworkModel serverNetworkModel;

    public ServerController(ServerView serverView, ServerNetworkModel serverNetworkModel) {
        this.serverView = serverView;
        this.serverNetworkModel = serverNetworkModel;

        this.serverNetworkModel.listen();
    }
}
