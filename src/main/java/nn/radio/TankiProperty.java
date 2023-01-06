package nn.radio;

import java.io.FileInputStream;
import java.util.Properties;

public class TankiProperty {
    Properties properties = new Properties();
    public void readProperies () {
        try {
            String configFilePath = "config.properties";
            FileInputStream propsInput = new FileInputStream(configFilePath);
            properties.load(propsInput);
            properties.forEach( (k,v) -> {
                System.out.println(k + " " + v.toString());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key){
        return properties.getProperty(key);
    }
}
