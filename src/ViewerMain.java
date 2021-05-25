public class ViewerMain {
    public static void main(String[] args) {
        ViewerUI ui = new ViewerUI();
        NetworkModel networkModel = new NetworkModel(true);
        ViewerController controller = new ViewerController(ui, networkModel);
    }
}
