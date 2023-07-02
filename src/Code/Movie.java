package Code;

import lombok.*; //Lombok is a library that automatically plugs into your editor and build tools, spicing up your java.
// an example of this is the @Data annotation below, which automatically creates getters and setters for all fields in the class
//I do recommend you that you learn how to write getters and setters yourself at first, but once you get the hang of it, you can use Lombok to save time.

@Data // Lombok's annotation to create getters and setters for all fields in the class automatically (with one line of code)

public class Movie {

    public static String genre;
    static String title;
    static String year;
    static String actors;
    static String  director;

    static String Poster;




  public Movie(String title,String genre, String year, String actors, String director, String Poster){  // constructor for the movie object
      Movie.title = title;
      Movie.year = year;
      Movie.actors = actors;
      Movie.director = director;
      Movie.Poster = Poster;
      Movie.genre = genre;

}
}


