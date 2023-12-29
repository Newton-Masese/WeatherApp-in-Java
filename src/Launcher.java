import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               new Gui().setVisible(true);
               System.out.println(App.getWeatherData("London"));
                System.out.println(App.getLocationData("London"));

            }
        });
    }
}
