import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RemoteScreenCapture extends Thread {
    private final NetworkModel networkModel;

    public RemoteScreenCapture(NetworkModel networkModel) {
        this.networkModel = networkModel;
    }

    @Override
    public void run() {
        double fps = 15;
        double frameUpdateInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (!interrupted()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / frameUpdateInterval;
            lastTime = now;

            while (delta >= 1) {
                BufferedImage bufferedImage = captureScreen();
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                sendScreen(imageIcon);

                delta--;
            }
        }
    }

    private BufferedImage captureScreen() {
        BufferedImage bufferedImage = null;
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            bufferedImage = robot.createScreenCapture(screenRect);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    private void sendScreen(ImageIcon imageIcon) {
        try {
            networkModel.sendParcel("screen", imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
