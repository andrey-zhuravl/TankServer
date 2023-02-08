package nn.radio;

import java.io.*;
import java.util.ArrayList;

public class ConfigReader {

    java.util.List<String> rows = new ArrayList<>();
    String row;
    File configFile = new File("tank_config.txt");

    ConfigReader() {
        readFromFile();
    }

    public void readFromFile() {
        try (FileReader fr = new FileReader(configFile);
             BufferedReader reader = new BufferedReader(fr)) {

            do {
                row = reader.readLine();
                rows.add(row);
            } while (row != null);
            rows.remove(rows.size() - 1);

        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public String tankValues(int i, int j) {
        rows.toArray().toString();
        String[][] keywordsLine = new String[rows.size()][rows.get(i).split(",").length];
        keywordsLine[i] = rows.get(i).split(",");
        return (keywordsLine[i][j]);
    }

    public int getConfigSize() {
        return rows.size();
    }
}
