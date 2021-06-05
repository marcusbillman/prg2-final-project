/**
 * Entry point for the viewer-side application.
 */
public class ViewerMain {
    public static void main(String[] args) {
        ViewerUI ui = new ViewerUI();
        NetworkModel networkModel = new NetworkModel(false);
        new ViewerController(ui, networkModel);
    }
}
