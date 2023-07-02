package Code;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OMDBApi {

    public OMDBApi() throws IOException { //constructor for OMDBApi
    }


    public void getDataByTitle(String movieTitle) { //get data from api by title
        String apiUrl = "http://www.omdbapi.com/?apikey=" + ApiKey.getApiKey() + "&t=" + movieTitle; //create api url to get data from api by title (api key is stored in ApiKey class) (t= title)
        String response = getResponse(apiUrl);
        jsonToObject(response);
    }

    private static String getResponse(String apiUrl) { //get response from api (connection)
        try {
            URL url = new URL(apiUrl); //create url object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //open connection
            connection.setRequestMethod("GET"); // set request method to get (get data from api)
            connection.setRequestProperty("Accept", "application/json"); //set request property to accept json
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); //create buffered reader object to read response from api (input stream)
            StringBuilder sb = new StringBuilder(); //create string builder object to append response from api to it
            String line; //create string object to store response from api in it

            while ((line = br.readLine()) != null) { //read response from api
                sb.append(line).append("\n"); //append response to string builder object
            }
            br.close(); //close buffered reader object (close connection)
            connection.disconnect(); //disconnect connection

            return sb.toString();//return response from api (string builder object) as string (response) to jsonToObject method (convert json to object)

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null; //return null if error occurred (no response)
        }
    }

    public static void jsonToObject(String response) { //convert json to object (movie)
        JSONObject movieJson = new JSONObject(response); //create json object
        String title = movieJson.getString("Title");
        String year = movieJson.getString("Year");
        String actors = movieJson.getString("Actors");
        String director = movieJson.getString("Director");
        String Poster = movieJson.getString("Poster");
        String genre = movieJson.getString("Genre");
        new Movie(title, genre ,year , actors , director , Poster);
        
    }


public static Component DisplayImage(String imageUrl) throws IOException { //display image from url (poster)
    URL url = new URL(imageUrl);
    BufferedImage image = ImageIO.read(url);
    ImageIcon icon = new ImageIcon(image);
    icon.getImage(); // transform it to image icon object (image)

    return new JLabel(icon); // return image icon object (image)
}
}
