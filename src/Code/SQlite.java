package Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;

public class SQlite { // Class for SQLite database
    Connection conn = DriverManager.getConnection("jdbc:sqlite:OMDBMovie Database.db"); // Connect to database
    String databaseName = "OMDBMovie Database"; // Name of database

    public SQlite() throws SQLException {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db"); // Connect to database
            System.out.println("Connected! \nDatabase name is: " + databaseName); // Print out confirmation message
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        createTable(); // Create table if it doesn't exist directly after connecting to database
    }


    private void createTable() { // Create table if it doesn't exist already
        String sql = """
                CREATE TABLE IF NOT EXISTS movies (
                id integer PRIMARY KEY,
                title varchar(50),
                year varchar(50),
                genre varchar(50),
                director varchar(50),
                actors varchar(50),
                date varchar(50)
                );""";
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }






    // panel.add(titleLabel);
    //        panel.add(yearLabel);
    //        panel.add(actorsLabel);
    //        panel.add(genreLabel);
    //        panel.add(directorLabel);
    public void addMovieToDatabase(String title, String year, String actors, String director, String genre) { // Add movie to database
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        try {
            PreparedStatement pStatement = conn.prepareStatement("INSERT INTO movies (title, year, director, actors, date) VALUES (?, ?, ?, ?, ?)"); // Insert movie into database with these values
            pStatement.setString(1, title);
            pStatement.setString(2, year);
            pStatement.setString(3, actors);
            pStatement.setString(4, genre);
            pStatement.setString(5, director);

            int result = pStatement.executeUpdate();
            if (result < 0) {
                JOptionPane.showMessageDialog(null, "Movie added to database!");
            } else {
                JOptionPane.showMessageDialog(null, "Movie already exists in database!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void Search(String movieTitle) { // Search for movie in database
        String sql = "SELECT * FROM movies WHERE title = ? OR genre = ? OR director = ? OR actors = ?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, movieTitle);


            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String year = resultSet.getString("year");
                String director = resultSet.getString("actors");
                String genre = resultSet.getString("genre");
                String actors = resultSet.getString("director");


                // Create a separate window to display the movie details
                JFrame frame = new JFrame("Movie Details");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(300, 200);
                frame.setLayout(new FlowLayout());

                frame.add(new JLabel("Title: " + title));
                frame.add(new JLabel("Year: " + year));
                frame.add(new JLabel("Director: " + director));
                frame.add(new JLabel("Actors: " + actors));
                frame.add(new JLabel("Genre: " + resultSet.getString("genre")));
                frame.add(new JLabel("Date added: " + resultSet.getString("date")));

                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Movie not found!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeDuplicates() { // Remove duplicates from database (if any)
        String sql = "DELETE FROM movies WHERE id NOT IN (SELECT MIN(id) FROM movies GROUP BY title, year, director, actors)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql); // Create a prepared statement to execute the query
            int result = pStatement.executeUpdate(); // Execute the query and store the result in a variable

            if (result > 0) { // If the result is greater than 0
                JOptionPane.showMessageDialog(null, "Duplicates removed!"); // Display a message if duplicates were found and removed
            } else {
                JOptionPane.showMessageDialog(null, "No duplicates found!"); // Display a message if no duplicates were found
            }
        } catch (SQLException e) { // Catch any SQL exceptions
            System.out.println(e.getMessage()); // Print the error message to the console
        }
    }


    public void ListMovies(String movieTitle) { // List all the movies in the database
        String sql = "SELECT * FROM movies"; // Create a SQL query to get all the movies from the database

        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet resultSet = pStatement.executeQuery();

            // Create a separate window to display the list of movies
            JFrame frame = new JFrame("Movie List");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridLayout(0, 1));

            DefaultListModel<String> listModel = new DefaultListModel<>(); // Create a list model to store the movie titles in
            JList<String> movieList = new JList<>(listModel); // Create a JList to display the movie titles
            JScrollPane scrollPane = new JScrollPane(movieList); // Add a scroll pane to the JList
            frame.add(scrollPane); // Add the scroll pane to the frame

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                listModel.addElement(title);
            }

            movieList.addMouseListener(new MouseAdapter() { // Add a mouse listener to the JList to detect double clicks
                @Override
                public void mouseClicked(MouseEvent e) { // Override the mouseClicked method
                    if (e.getClickCount() == 2) {
                        String selectedMovie = movieList.getSelectedValue(); // Get the selected movie from the JList
                        String sql = "SELECT * FROM movies WHERE title = ?"; // Create a SQL query to get the movie details from the database

                        try {
                            PreparedStatement pStatement = conn.prepareStatement(sql); // Create a prepared statement to execute the query and pass the selected movie as a parameter to the query
                            pStatement.setString(1, selectedMovie); // Set the first parameter to the selected movie from the JList
                            ResultSet resultSet = pStatement.executeQuery(); // Execute the query and store the result in a variable (resultSet)

                            if (resultSet.next()) {
                                String title = resultSet.getString("title");
                                String year = resultSet.getString("year");
                                String director = resultSet.getString("director");
                                String actors = resultSet.getString("actors");

                                // Create a separate window to display the movie details
                                JFrame frame = new JFrame("Movie Details"); // Create a new JFrame
                                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set the default close operation
                                frame.setLayout(new GridLayout(0, 2)); // Set the layout of the frame to a grid layout with 2 columns and 0 rows (unlimited rows) to display the movie details

                                frame.add(new JLabel("Title:"));
                                frame.add(new JLabel(title));

                                frame.add(new JLabel("Year:"));
                                frame.add(new JLabel(year));

                                frame.add(new JLabel("Actors:"));
                                frame.add(new JLabel(actors));

                                frame.add(new JLabel("Genre:"));
                                frame.add(new JLabel(resultSet.getString("genre")));

                                frame.add(new JLabel("Director:"));
                                frame.add(new JLabel(director));


                                frame.pack(); // Pack the frame
                                frame.setVisible(true); // Set the frame to visible
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            });

            frame.pack();
            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

