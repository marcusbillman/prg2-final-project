import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientScreenCapture implements Runnable {
    private NetworkModel networkModel;
    private AtomicBoolean running = new AtomicBoolean(false);
    private double fps = 1;

    public ClientScreenCapture(NetworkModel networkModel) {
        this.networkModel = networkModel;
    }

    public void run() {
        running.set(true);

        double frameUpdateInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = 0;

        while (running.get()) {
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

    public void stop() {
        running.set(false);
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
