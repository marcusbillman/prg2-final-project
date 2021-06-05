import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Controls the remote-side application including its network connectivity.
 */
public class RemoteController {
    private final NetworkModel networkModel;
    private Thread screenCaptureThread;

    public RemoteController(NetworkModel networkModel) {
        this.networkModel = networkModel;

        try {
            this.networkModel.listen(1337);
            System.out.println("Connected to viewer at " + this.networkModel.getSocket().getRemoteSocketAddress());
            startReceiving();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a loop that continuously receives incoming Parcels from the viewer. Parcels are passed on to the
     * relevant handler based on feature name.
     */
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

    /**
     * Handler for Parcels of the popup feature. Displays a dialog box and responds to the viewer when the dialog box
     * is closed by the user.
     * @param payload string array with the popup's title and body
     */
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

    /**
     * Handler for Parcels of the terminal feature. Launches a cmd process, executes the specified command and sends
     * the output back to the viewer.
     * @param payload command to execute
     */
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

    /**
     * Handler for Parcels of the screen feature. Starts (or stops) a screen capture thread that continuously sends
     * screenshots to the viewer.
     * @param payload "start" or "stop"
     */
    private void handleScreenParcel(Object payload) {
        if (payload.equals("start")) {
            screenCaptureThread = new RemoteScreenCapture(networkModel);
            screenCaptureThread.start();
        } else if (payload.equals("stop")) {
            if (screenCaptureThread != null) screenCaptureThread.interrupt();
        }
    }
}
