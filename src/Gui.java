import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui extends JFrame {

    private JSONObject weatherData;
    public Gui(){
        // adds title and sets up gui
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // sets gui size
        setSize(450, 650);

        // sets gui at centre of the screen
        setLocationRelativeTo(null);

        setLayout(null);
        setResizable(false);
        // configure gui to close operation once closed.


        addGuiComponents();


    }

    private void addGuiComponents(){
        //search fields
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15,15,351,45);
        searchTextField.setFont(new Font("Dialog",Font.PLAIN,24));
        add(searchTextField);

        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        // temp text
        JLabel tempText = new JLabel("10 C");
        tempText.setBounds(0,350,450,54);
        tempText.setFont(new Font("Dialog",Font.BOLD,24));
        tempText.setHorizontalAlignment(SwingConstants.CENTER);
        add(tempText);

        // weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // humidity image and text
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b><br>100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(humidityText);

        // wind speed image and text
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(215,500,74,66);
        add(windSpeedImage);

        JLabel windSpeedText = new JLabel("<html><b>WindSpeed</b> 15km/h</html>");
        windSpeedText.setBounds(305,500,90,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);

        // search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gets location
                String userInput = searchTextField.getText();

                // validate user inputs.
                if(userInput.replaceAll("\\s","").length() <= 0){
                    return;
                }

                // retrieves weather data
                weatherData = App.getWeatherData(userInput);

                // update gui

                // update weather image
                assert weatherData != null;
                String weatherCondition = (String)weatherData.get("weather_condition");
                switch (weatherCondition) {
                    case "clear" -> weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                    case "Cloudy" -> weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                    case "Rain" -> weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                    case "Snow" -> weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                }

                // update temperature text
                double temp = (double) weatherData.get("temperature");
                tempText.setText(temp + " C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b><br>" + humidity+"%</html>");

                double windSpeed = (double) weatherData.get("windSpeed");
                windSpeedText.setText("<html><b>WindSpeed</b><br>" + windSpeed+"km/h</html>");



            }
        });

        add(searchButton);
    }


    // creates image in gui components
    private ImageIcon loadImage(String resourcePath){
        try{
            // reads image from the path
            BufferedImage image = ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Resource Not Found");
        return null;
    }

}
