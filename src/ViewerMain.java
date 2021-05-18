public class ViewerMain {
    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        NetworkModel networkModel = new NetworkModel(true);
        ViewerController controller = new ViewerController(serverView, networkModel);
    }
}
