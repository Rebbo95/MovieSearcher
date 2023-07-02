package Code;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
public static void main(String[] args) throws SQLException {


        Gui gui = new Gui();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(400, 200);
        gui.setVisible(true);

        ApiKey.ReadApiKeyFromFile(new java.io.File("apikey.properties")); //read api key from file and store it in a variable called key in ApiKey class




        }
        }