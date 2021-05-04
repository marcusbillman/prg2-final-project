public class ServerMain {
    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        NetworkModel networkModel = new NetworkModel(true);
        ServerController controller = new ServerController(serverView, networkModel);
    }
}
