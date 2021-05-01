public class ServerMain {
    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        ServerNetworkModel serverNetworkModel = new ServerNetworkModel(1337);
        ServerController controller = new ServerController(serverView, serverNetworkModel);
    }
}
