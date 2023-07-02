package Code;

import java.io.*;

public class ApiKey {
    public static String key;

    public ApiKey() { //create api key constructor
    }

    public static String getApiKey() {
        return key;
    }

    public static void ResetApiKey() {
        key = null;
    }

    public static void ReadApiKeyFromFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            key = bufferedReader.readLine();

            FileWriter fileWriter = new FileWriter("apikey.properties");
            fileWriter.write(key);
            fileWriter.close();

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void WriteApiKeyToFile(File file) {

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(ApiKey.key);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//new object = new APIkey();