package nn.radio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TankiProperty {
    Properties properties = new Properties();

    public void readProperies () {
        String configFilePath = "config.properties";

        try (FileInputStream propsInput = new FileInputStream(configFilePath)) {

            properties.load(propsInput);
            properties.forEach( (k,v) -> {
                System.out.println(k + " " + v.toString());
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key){
        return properties.getProperty(key);
    }
}
