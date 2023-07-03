

# <span style="color:lightYellow">MoveSearcher</span>

##  <span style="color:Lightgreen"> Description </span>
```text
This is a simple MovieSearcher app, that uses the OMDb API to search for movies and display the results.
The user can search for a movie by title and the results will be displayed in a a separate window.
```

## <span style="color:LightBlue"> Installation/Libraries used 
* [json](https://mvnrepository.com/artifact/org.json/json/20230227)
* [sqlite-jdbc](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.34.0)
* [Lombok](https://projectlombok.org/download)

<span style= "color: Red">Make sure to install these libraries before running the application.



## <span style="color:pink"> Usage </span>
```text
The GUI provides the following options:

Menu Button: Clicking the "Menu" button displays a dropdown menu with the following options:

Search by title: Allows the user to search for a movie by title. The search results are displayed in a separate window.
Search in database: Enables searching for movies in the database.
Exit: Closes the application.
API Key Button: Clicking the "API Key Options" button displays a dialog with the following options:

Pick Location: Allows the user to select the location of the API key file.
Create File: Enables the creation of a new API key file.
Reset API Key: Resets the API key to the default value (null).
List All Movies Button: Clicking the "List all movies" button displays all the movies stored in the database in a new window, complete with a scroll bar.
```

<span style= "color: Blue">Todo:
+ [x] Fix the bug that causes the application to crash when the user clicks the "List all movies" button without having searched for a movie first.

+ [x] Fix the order of the information displayed in the "List all movies" window.

+ [x] Add the option to search for Actors, Directors, ect.


```text
It will be fixed as soon as I've got the time to do it, as I'm currently working on other projects.
```
