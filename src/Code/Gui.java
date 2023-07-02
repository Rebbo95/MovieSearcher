

package Code;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;


import static Code.Movie.Poster; // Import Poster variable from Movie class
import static Code.Movie.director; // Import director variable from Movie class
import static Code.OMDBApi.DisplayImage; // Import DisplayImage method from OMDBApi class





class Gui extends JFrame implements ActionListener {
    SQlite sqlite = new SQlite(); // Create object of SQlite class

    public Gui() throws SQLException { // Constructor for the GUI
        super("Movie Searcher");
        setLayout(new FlowLayout());
        Menu_Button();
        ApiKey_Button();
        ListAllMovies_Button();

    }

    public void Menu_Button() { // Menu button
        JButton menu_Button = new JButton("Menu");
        add(menu_Button, BorderLayout.WEST);
        menu_Button.addActionListener(this);
        menu_Button.addActionListener(e -> {
            try {
                showMenu();


            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });

    }

    public void showMenu() throws IOException, SQLException {  //drop down menu

        String[] options = {"Search by title", "Search in database", "Exit"}; // Options for the drop down menu
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Menu",  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]); // Create drop down menu with the options above and store the choice in a variable called choice (int)

        if (choice == 0) { // If choice is 0, search by title
            String movieTitle = JOptionPane.showInputDialog("Enter movie title"); // Ask user to enter movie title and store it in a variable called movieTitle (String)
            try {
                OMDBApi omdbApi = new OMDBApi();
                omdbApi.getDataByTitle(movieTitle);
                Movie_Info_GUI();
                DisplayImage(Poster);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice == 1) { // If choice is 1, search in database for movie
            SQlite sqlite = new SQlite();
            String movieTitle = JOptionPane.showInputDialog("Enter movie title");
            sqlite.Search(movieTitle);
        }
    }




    public static void Movie_Info_GUI() throws IOException { // Movie info GUI
        JFrame frame = new JFrame("Movie Searcher");

        JPanel panel = new JPanel(); // Create panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout of panel to BoxLayout with the Y_AXIS parameter (vertical)

        JLabel titleLabel = new JLabel("Title: " + Movie.title);
        JLabel yearLabel = new JLabel("Year: " + Movie.year);
        JLabel actorsLabel = new JLabel("Actors: " + Movie.actors);
        JLabel genreLabel = new JLabel("Genre: " + Movie.genre);
        JLabel directorLabel = new JLabel("Director: " + director);

        panel.add(titleLabel);
        panel.add(yearLabel);
        panel.add(actorsLabel);
        panel.add(genreLabel);
        panel.add(directorLabel);

        panel.add(OMDBApi.DisplayImage(Poster));

        panel.add(Box.createVerticalGlue()); // Adds spacing between the poster and the button
        panel.add(addMovie_Button());

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes the window when the user clicks the X button in the top right corner of the window
        frame.pack();
        frame.setVisible(true);
    }

    private static Component addMovie_Button() { // Adds a button to add the movie to the database
            JButton addMovieButton = new JButton("Add movie to database"); // Adds a button to add the movie to the database

            addMovieButton.addActionListener(e -> { // When the button is clicked, add the movie to the database
                try {
                    SQlite sqlite = new SQlite();
                    sqlite.addMovieToDatabase(Movie.title, Movie.year, Movie.actors, Movie.genre, director);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            return addMovieButton; // Return the button to the Movie_Info_GUI method
        }


    public void ApiKey_Button() { //api key button
        JButton enterApiKey_Button = new JButton("Api-Key Options"); // Create button called enterApiKey_Button with the text "Api-Key Options"
        add(enterApiKey_Button, BorderLayout.EAST);
        enterApiKey_Button.addActionListener(e -> {
            try {
                showApiKeyOptions();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void showApiKeyOptions() throws IOException { //api key options
        String[] options = {"Pick Location", "Create File", "Reset API Key"};

        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "API Key Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]); // Create drop down menu with the options above and store the choice in a variable called choice (int)

        if (choice == 0) {
            pickApiKeyLocation(); //pick api key location button method (line 150)

        } else if (choice == 1) {
            createApiKeyFile(); //create api key file button method  (line 160)

        } else if (choice == 2) {
            ApiKey.ResetApiKey(); //reset api key button method (sets api key to default value(null))
            JOptionPane.showMessageDialog(null, "API Key has been reset."); //show message dialog box with message "API Key has been reset."
        }
    }

    public void pickApiKeyLocation() {//pick api key location button
        JFileChooser fileChooser = new JFileChooser(); //create file chooser object (file chooser is a window that allows user to pick a file)
        int returnValue = fileChooser.showOpenDialog(this);
        String filePath = fileChooser.getSelectedFile().getAbsolutePath(); //get file path from file chooser (file path is the location of the file) (getAbsolutePath() gets the path as a string)

        if (returnValue == JFileChooser.APPROVE_OPTION) { //if user clicks approve button then read api key from file and set it
            File file = new File(filePath);
            ApiKey.ReadApiKeyFromFile(file); //read api key from file method (From ApiKey class)
        }
    }
    public void createApiKeyFile() { //create api key file button
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this); //show save dialog box (save dialog box is a window that allows user to save a file)
        String filePath = fileChooser.getSelectedFile().getAbsolutePath(); //get file path from file chooser (file path is the location of the file) (getAbsolutePath() gets the path as a string)

        if (returnValue == JFileChooser.APPROVE_OPTION) { //if user clicks approve button then read api key from file and set it
            ApiKey.key = JOptionPane.showInputDialog("Enter API Key");
            File file = new File(filePath);
            ApiKey.WriteApiKeyToFile(file);

        }
    }

    public void ListAllMovies_Button() { //list all movies button
        JButton listAllMovies_Button = new JButton("List all movies");
        add(listAllMovies_Button, BorderLayout.SOUTH);
        listAllMovies_Button.addActionListener(e -> {
            try {
                sqlite.removeDuplicates(); //removes duplicates from the database before displaying it
                showAllMovies();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void showAllMovies() throws SQLException { //show all movies button, shows all added movies in the database in a new window with a scroll bar
        sqlite.ListMovies(String.valueOf(sqlite)); //converts the list to a string and then displays it.
    }
    @Override
    public void actionPerformed(ActionEvent e) { //action listener


    }
}