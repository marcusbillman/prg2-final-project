import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Controls the viewer-side application including its network connectivity and GUI.
 */
public class ViewerController {
    private final ViewerUI ui;
    private final NetworkModel networkModel;

    public ViewerController(ViewerUI ui, NetworkModel networkModel) {
        this.ui = ui;
        this.networkModel = networkModel;

        // Attach listeners (end of this file) to view
        this.ui.addPopupSendButtonListener(new PopupSendButtonListener());
        this.ui.addTerminalRunButtonListener(new TerminalRunButtonListener());
        this.ui.addTabSwitchListener(new TabSwitchListener());
        this.ui.addCloseListener(new CloseListener());

        String serverAddress = JOptionPane.showInputDialog("Remote address", "localhost");
        this.networkModel.connect(serverAddress, 1337);

        String connectedMessage = "Connected to remote at " + this.networkModel.getSocket().getRemoteSocketAddress();
        System.out.println(connectedMessage);
        this.ui.setProgressBarVisible(false);
        this.ui.setStatusLabelText(connectedMessage);

        startReceiving();
    }

    /**
     * Starts a loop that continuously receives incoming Parcels from the remote. Parcels are passed on to the
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
                    ui.setStatusLabelText((String) payload);
                    break;
                case "terminal":
                    ui.appendTerminalLine(payload + "\n");
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
     * Handler for Parcels of the screen feature. Gets the screenshot, calculates its dimensions and aspect ratio, and
     * displays the correctly scaled image in the GUI.
     * @param payload "start" or "stop"
     */
    private void handleScreenParcel(Object payload) {
        ImageIcon imageIcon = (ImageIcon) payload;
        Image image = imageIcon.getImage();

        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        double imageAspect = (double) imageWidth / (double) imageHeight;

        Dimension tabSize = ui.getTabSize();
        double tabWidth = tabSize.getWidth();
        double tabHeight = tabSize.getHeight();
        double tabAspect = tabWidth / tabHeight;

        int width;
        int height;

        // Calculate image size to fit in tab while preserving aspect ratio. Limit image height if tab is too wide
        // and vice-versa.
        if (tabAspect > imageAspect) {
            height = (int) tabHeight;
            width = (int) (height * imageAspect);
        } else {
            width = (int) tabWidth;
            height = (int) (width / imageAspect);
        }

        Image scaledImage = getScaledImage(image, width, height);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        ui.setScreenIcon(scaledImageIcon);
    }

    /**
     * Scales an image to the specified dimensions.
     * @param image source image to scale
     * @param width target image width
     * @param height target image height
     * @return scaled image
     */
    private Image getScaledImage(Image image, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = scaledImage.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();

        return scaledImage;
    }

    // -------------------- Listeners

    /**
     * Custom listener based on ActionListener that, when invoked, sends a popup request Parcel to the remote.
     */
    private class PopupSendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String body = ui.getPopupBody();
            String title = ui.getPopupTitle();
            try {
                networkModel.sendParcel("popup", new String[]{body, title});
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Custom listener based on ActionListener that, when invoked, sends a terminal request Parcel to the remote.
     */
    private class TerminalRunButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = ui.getTerminalCommand();
            try {
                networkModel.sendParcel("terminal", command);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Custom listener based on ChangeListener that, when invoked, sends a screen request Parcel to the remote.
     */
    private class TabSwitchListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
            int selectedIndex = tabbedPane.getSelectedIndex();

            String payload = selectedIndex == 2 ? "start" : "stop";
            try {
                networkModel.sendParcel("screen", payload);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Custom listener based on CloseListener that, when invoked, safely closes the network connection before exiting
     * the application.
     */
    private class CloseListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Closing connection");
            networkModel.closeConnection();
            ui.getFrame().dispose();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            System.out.println("Exiting");
            System.exit(0);
        }

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }
}
