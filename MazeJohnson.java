/* MazeJohnson
 * 
 * A game in which the player navigates through a maze
 * The user may also edit the maze and save their mazes to maps.txt
 * 
 * Author : Bryan Johnson
 */

import java.util.Arrays;
import java.util.Scanner;

public class MazeJohnson
{
  static Scanner scan = new Scanner(System.in); // Used for ALL input
  
  static char maze[][] = new char[0][0]; // The central array
  /* maze creation - start at S
   *               - goal is G
   *               - spaces are paths
   *               - X's are walls
   */
  
  static String maps[][] = // Stores custom mazes as "maps" --> horner and johnson will always appear in the game
  {
    {"horner", "johnson"},
    {"XSXXXXXXXXZX        XX XXX X XXX X X X  XX   X XXXXX XXX    XX   X XX XX X X X  XX   X X XXXXXXXXXGXX", "XXXXXZXS  XX G XX   XXXXXX"}
  };
  
  // Data for printMaze | Object positions
  static int playerX = 0;
  static int playerY = 0;
  static int cursorX = 0;
  static int cursorY = 0;
  static int startPointX = 0;
  static int startPointY = 0;
  static int goalX = 0;
  static int goalY = 0;
  
  // Direction inputs
  static String customUp = null;
  static String customDown = null;
  static String customLeft = null;
  static String customRight = null;
  
  // Editor inputs
  static String customRip = null; // shortcut for ripMap in editor
  static String customSave = null;
  
  // Important class variables
  static String input = "";
  static String map = "";
  
  // Other variables
  static boolean firstPlay = true;
  static boolean firstEdit = true;
  
  // MazeJohnson is run from this
  public static void main(String[] args)
  {
    overall(); // Kept this clutter free for testing
  } // main
  
  // This should function like main, but in a neat little package
  static void overall()
  {
    ingestFile();
    printText("welcome");
    while(0 < 1) // Infinite loop until the overall is terminated by return;
    {
      if(end())
      {
        populateFile();
        return;
      }
      else
      {
        System.out.print
          (
           " \n" +
           " - -- Main Menu -- -  \n"
          );
        input = scan.nextLine();
        sub(input);
      }
    }
  }
  
  // Performs all logic trees not in overall
  // Allows for end to work on ALL prompts
  static void sub(String useMode) // sub is short for subSystem
  {
    boolean firstRun = true; // Useful for while loops with error reporting
    
    if(useMode.equals("help"))
    {
      firstPlay = false; // The user doesn't need help if they have already getting it
      printText("manual");
      return;
    }
    else if(useMode.equals("config"))
    { 
      firstPlay = false; // The user doesn't need help if they use config
      
      while(0 < 1)
      {
        printText("config");
        input = scan.nextLine();
        if(exit())
          return;
        
        // Reset maps.txt
        if(input.equals("reset"))
        {
          do {
            if(!firstRun)
              System.out.print("Please use yes or no. \n");
            System.out.print
              (
               "Would you like to reset maps.txt? (yes/no) \n" +
               " - config -  \n"
              );
          input = scan.nextLine();
          if(exit())
            return;
          if(input.equals("yes"))
            FileHandler.writeToFile("maps.txt", "");
          firstRun = false;
          } while(!input.equals("yes") && !input.equals("no"));
          firstRun = true; // Reset firstRun for next do while loop
        }
        
        // Custom controls
        if(input.equals("custom controls"))
        {
          System.out.print(" \nYou may not use default controls as custom controls \n");
          
          System.out.print
            (
             "CustomUp : \n" +
             " - config - \n"
            );
          input = scan.nextLine();
          if(exit())
            return;
          if(!input.equals("") && !alreadyDefault())
            customUp = input;
          else
            System.out.print("Skipped \n");
          System.out.print(" \n");
          
          System.out.print
            (
             "CustomDown : \n" +
             " - config - \n"
            );
          input = scan.nextLine();
          if(exit())
            return;
          if(!input.equals("") && !alreadyDefault())
            customDown = input;
          else
            System.out.print("Skipped \n");
          System.out.print(" \n");
          
          System.out.print
            (
             "CustomLeft : \n" +
             " - config - \n"
            );
          input = scan.nextLine();
          if(exit())
            return;
          if(!input.equals("") && !alreadyDefault())
            customLeft = input;
          else
            System.out.print("Skipped \n");
          System.out.print(" \n");
          
          System.out.print
            (
             "CustomRight : \n" +
             " - config - \n"
            );
          input = scan.nextLine();
          if(exit())
            return;
          if(!input.equals("") && !alreadyDefault())
            customRight = input;
          else
            System.out.print("Skipped \n");
          System.out.print(" \n");
          
          System.out.print
            (
             "Allows for output of maze \n" +
             "CustomRip : \n" +
             " - config - \n"
            );
          input = scan.nextLine();
          if(exit())
            return;
          if(!input.equals("") && !alreadyDefault())
            customRip = input;
          else
            System.out.print("Skipped \n");
          System.out.print(" \n");
          
          System.out.print
            (
             "Saves current maze \n" +
             "CustomSave : \n" +
             " - config - \n"
            );
          input = scan.nextLine();
          if(exit())
            return;
          if(!input.equals("") && !alreadyDefault())
            customSave = input;
          else
            System.out.print("Skipped \n");
          System.out.print(" \n");
        }
      }
    }
    else if(useMode.equals("play"))
    {
      // Print help if it's the first time the user is playing
      if(firstPlay)
      {
        printText("manual");
        firstPlay = false;
      }
      
      // Select a map
      do {
        if(!firstRun)
          System.out.print("The name you entered isn't valid. \n");
      System.out.print("Type the name of a map : \n");
      printMaps();
      System.out.print(" - play -  \n");
      input = scan.nextLine();
      if(exit())
        return;
      firstRun = false;
      } while(!mapNamed(input));
      firstRun = true; // Reset firstRun for next do while loop
      map = input;
      
      // Set up maze array & reset cursor and player positions & reset turns
      useMap(map);
      reset("play");
      int turns = 0;
      
      // For the rest of play
      while(0 < 1)
      {
        printMaze();
        
        // Show status
        turns++;
        System.out.print
          (
           "Turn " + turns + " \n" +
           " - Play -  \n"
          );
        
        // Get input and move player
        input = scan.nextLine();
        if(exit())
          return;
        movePlayer(input); // movePlayer handles bad input
        
        // Logic for winning
        if(onGoal())
        {
          printMaze();
          System.out.print("You won! | Turn " + turns + " \n");
          return;
        }
      }
    }
    else if(useMode.equals("edit"))
    {
      firstPlay = false; // The user doesn't need help if they go in to edit mode
      
      // Reset
      int canvasX = 1;
      int canvasY = 1;
      reset("edit");
      
      // Initial prompt & info
      printText("edit");
      input = scan.nextLine();
      if(exit())
        return;
      
      // Get a map ready for editing
      if(input.equals("blank"))
      {
        System.out.print
          (
           "Canvas Length : \n" +
           " - Edit -  \n"
          );
        input = scan.nextLine();
        if(exit())
          return;
        canvasX = makeInt(input, 5);
        makeCanvas(canvasY, canvasX);
        System.out.print
          (
           "Canvas Height : \n" +
           " - Edit -  \n"
          );
        input = scan.nextLine();
        if(exit())
          return;
        canvasY = makeInt(input, 5);
        makeCanvas(canvasY, canvasX);
      }
      else
        useMap(input);
      
      // User edits the map with the cursor
      while(0 < 1)
      {
        printMaze();
        System.out.print(" - Edit -  \n");
        input = scan.nextLine();
        if(exit())
          return;
        else
          useCursor(input);
        if(exit()) // For the case of exit becoming true during useCursor
          return;
      }
    }
  }
  
  // Short method so input.equals("exit") || input.equals("end") doesn't have to appear 100 times
  static boolean exit()
  {
    if(input.equals("exit") || input.equals("end"))
      return true;
    return false;
  }
  
  // Short method so input.equals("end") doens't show up a bunch
  static boolean end()
  {
    if(input.equals("end"))
      return true;
    return false;
  }
  
  // Reset variables based on edit mode or play mode
  static void reset(String type)
  {
    if(type.equals("play"))
    {
      cursorX = -1;
      cursorY = -1;
      for(int a = 0; a < maze.length; a++)
      {
        for(int b = 0; b < maze[a].length; b++)
        {
          if(maze[a][b] == 'S')
          {
            playerX = b;
            playerY = a;
          }
        }
      }
    }
    else if(type.equals("edit"))
    {
      cursorX = 0;
      cursorY = 0;
      playerX = -1;
      playerY = -1;
    }
    else
      System.out.print("Bad type supplied to reset()"); // Error reporting
  } // reset
  
  // Used to determine when the game has been won
  static boolean onGoal()
  {
    for(int a = 0; a < maze.length; a++)
    {
      for(int b = 0; b < maze[a].length; b++)
      {
        if(maze[a][b] == 'G' && playerX == b && playerY == a)
          return true;
      }
    }
    return false;
  } // onGoal
  
  // Move player if the move is valid, otherwise don't. turn++; either way
  static void movePlayer(String direction)
  {
    if(direction.equals("up") || direction.equals(customUp))
    {
      if(playerY - 1 >= 0 && maze[playerY - 1][playerX] != 'X')
      {
        playerY--;
      }
    }
    else if(direction.equals("down") || direction.equals(customDown))
    {
      if(playerY + 1 <= maze.length - 1 && maze[playerY + 1][playerX] != 'X')
      {
        playerY++;
      }
    }
    else if(direction.equals("left") || direction.equals(customLeft))
    {
      if(playerX - 1 >= 0 && maze[playerY][playerX - 1] != 'X')
      {
        playerX--;
      }
    }
    else if(direction.equals("right") || direction.equals(customRight))
    {
      if(playerX + 1 <= maze[0].length - 1 && maze[playerY][playerX + 1] != 'X')
      {
        playerX++;
      }
    }
    else
      System.out.print("You have inputted a direction incorrectly. You lost a turn");
  } // movePlayer
  
  // Print one of these huge messages
  static void printText(String text)
  {
    if(text.equals("welcome"))
    {
      System.out.print
        (
         "Welcome to MazeJohnson! \n" +
         "Thanks to Stevie and Ciara for great play-testing! \n" +
         "Type play to get started : \n"
        );
    }
    else if(text.equals("manual")) // help
    {
      System.out.print
        (
         " - Commands - \n" +
         " help    : Displays instructions \n" +
         " end     : Ends MazeJohnson \n" +
         " exit    : Exit the current part of MazeJohnson (help/play/config/edit) \n" +
         " play    : Enter the game \n" +
         " config  : Change settings \n" +
         " edit    : Enter the maze editor \n" +
         " \n" +
         " - Symbols -  \n" +
         "    P    : The player (you) \n" +
         "    X    : Walls / Barriers \n" +
         "    S    : Start point \n" +
         "    G    : Goal \n" +
         "   [ ]   : The cursor / Your selection (editor only) \n" +
         " \n" +
         " - Instructions -  \n" +
         " Play    : \n" +
         "           Navigate the player from the start point to the goal. \n" +
         "           Enter right, left, up or down to move. \n" +
         " Edit    : \n" +
         "           Create a map and output it with ripMap or save with save. \n" +
         "           Enter right, left up or down to move. Enter a symbol to change the selected area. \n" +
         " \n" +
         " - Available Maps -  \n"
        );
      printMaps();
    }
    else if(text.equals("config"))
    {
      System.out.print
        (
         " \nHit Enter on any option to keep it the same \n" +
         "Config options : \n" +
         "   custom controls \n" +
         "   reset \n" +
         " - config -  \n"
        );
    }
    else if(text.equals("edit"))
    {
      System.out.print
        (
         "What map would you like to load? \n" +
         "blank   : blank maze \n" +
         "default : default maze \n" +
         "johnson : loads the map named johnson \n" +
         " - Edit - \n"
        );
    }
    else
    {
      System.out.print("Couldn't find text " + text); // Error message
    }
  } // printText
  
  
  
  /* Above is player interaction
   * Below is maze manipulation
   */
  
  // Move the cursor, edit items in the maze, print maps, save maps
  static void useCursor(String action)
  {
    if(action.equals("up") || action.equals(customUp))
    {
      if(cursorY > 0)
        cursorY--;
    }
    else if(action.equals("down") || action.equals(customDown))
    {
      if(cursorY < maze.length - 1)
        cursorY++;
    }
    else if(action.equals("left") || action.equals(customLeft))
    {
      if(cursorX > 0)
        cursorX--;
    }
    else if(action.equals("right") || action.equals(customRight))
    {
      if(cursorX < maze[0].length - 1)
        cursorX++;
    }
    else if(action.equals("x"))
    {
      maze[cursorY][cursorX] = 'X';
    }
    else if(action.equals("s"))
    {
      maze[cursorY][cursorX] = 'S';
    }
    else if(action.equals("g"))
    {
      maze[cursorY][cursorX] = 'G';
    }
    else if(action.equals(" "))
    {
      maze[cursorY][cursorX] = ' ';
    }
    else if(action.equals("ripMap") || action.equals(customRip))
    {
      ripMap(); // Print the contents of maze in map format
      System.out.println(""); // spacer
      System.out.println(""); // spacer
    }
    else if(action.equals("save") || action.equals(customSave))
    {
      boolean firstRun = true;
      if(!mazeHas('S').equals("") && !mazeHas('G').equals(""))
      {
        do {
          if(firstRun)
          {
            System.out.print("Enter the name of the map : \n");
            firstRun = false;
          }
          else
          {
            System.out.print
              (
               "You can not use @  or another map's name. \n" +
               "Enter the name of the map : \n"
              );
          }
          input = scan.nextLine();
          if(exit())
            return;
        } while(input.contains("@") || mapNamed(input));
        maps[0] = Arrays.copyOf(maps[0], maps[0].length + 1);
        maps[0][maps[0].length - 1] = input;
        maps[1] = Arrays.copyOf(maps[1], maps[1].length + 1);
        maps[1][maps[1].length - 1] = ripMap();
        populateFile();
      }
      else
        System.out.print("You must have a start point and goal to save. \n");
    }
  } // useCursor
  
  // Checks if the input is already in use by default
  static boolean alreadyDefault()
  {
    if(input.equals("up") || input.equals("down") || input.equals("left") || input.equals("right") || input.equals("x") || input.equals("s") || input.equals("g") || input.equals("rip") || input.equals("save"))
      return true;
    return false;
  } // alreadyDefault
  
  // Prints the maze objects in it
  static void printMaze()
  {
    String mazeDisplay = "\n"; // Store the output in a string
    for(int a = 0; a < maze.length; a++)
    {
      for(int b = 0; b < maze[a].length; b++)
      {
        if(a == playerY && b == playerX) // If the player is on that symbol
          mazeDisplay += (" P "); // Print the player instead of the symbol
        else if(a == cursorY && b == cursorX) // If the cursor is on that symbol
          mazeDisplay += ("[" + maze[a][b] + "]"); // Sorround the symbol with brackets
        else
          mazeDisplay += (" " + maze[a][b] + " "); // Print the symbol with padding
      }
      mazeDisplay += ("\n"); // Add a newline to start the next row
    }
    System.out.println(mazeDisplay); // Print the output in one command --> Huge improvement to render speed
  } //printMaze
  
  
  // Prints the data that is currently stored in the maze array so it can be injected in to useMap for future games
  static String ripMap()
  {
    String map = "";
    for(int a = 0; a < maze.length; a++)
    {
      for(int b = 0; b < maze[a].length; b++)
      {
        map += maze[a][b];
      }
      if(a == 0)
        map += 'Z';
    }
    System.out.println(map);
    return map;
  } // ripMap
  
  // Reset the maze to a "map"
  static void useMap(String map)
  {
    for(int i = 0; i < maps[0].length; i++)
    {
      if(map.equals(maps[0][i]))
      {
        setMap(maps[1][i]);
        return;
      }
    }
    setMap("XSXXXXXXXXZX        XX XXX X XXX X X X  XX   X XXXXX XXX    XX   X XX XX X X X  XX   X X XXXXXXXXXGXX "); // default --> "hard coded" copy of horner
    return;
  } // useMap
  
  // Print the map names and lengths
  static void printMaps()
  {
    String output = "";
    // First row
    for(int i = 0; i < maps[0].length; i++)
    {
      output += "   ";
      output += maps[0][i];
      output += " \n";
    }
    System.out.print(output + " \n");
  }
  
  // Set the maze array to a predefined map
  static void setMap(String map)
  {
    // Get map dimensions
    int mapX = -1;
    int mapY = -1;
    for(int a = 0; a < map.length(); a++)
    {
      if(map.charAt(a) == 'Z') // Z is used to mark the end of the first row in ripMap();
      {
        mapX = a;
      }
    }
    mapY = map.length() / mapX; // Amount of rows = total items / amount of columns
    
    
    // Check for valid values --> good map
    if(mapX == -1 || mapY == -1)
    {
      System.out.println("A bad map was supplied to a setMap(); command. Please recreate the map");
    }
    else
    {
      // Make the maze the correct size for the map
      resizeMaze(mapX, mapY);
      
      // Remove 'Z' from the map
      String temp = "";
      for(int i = 0; i < map.length(); i++)
      {
        if(map.charAt(i) != 'Z')
          temp += map.charAt(i);
      }
      map = temp;
      
      
      // Fill the array with the map
      int c = 0;
      for(int a = 0; a < mapY; a++)
      {
        for(int b = 0; b < mapX; b++)
        {
          maze[a][b] = map.charAt(c);
          c++;
        }
      }
      
      // Find start point and goal
      
      
    }
    
  } // setMap
  
  // Change the size of the maze array
  static void resizeMaze(int mazeX, int mazeY)
  {
    maze = Arrays.copyOf(maze, mazeY); // Change the amount of rows
    for(int i = 0; i < maze.length; i++) // For each column in the row
    {
      if (maze[i] == null) // If the column doesn't exist
        maze[i] = new char[mazeX]; // Make it
      else // Otherwise the column needs its size to be changed
        maze[i] = Arrays.copyOf(maze[i], mazeX); // Change length of column
    }
  } // resizeMaze
  
  // Makes a blank maze with a border
  static void makeCanvas(int mazeX, int mazeY)
  {
    resizeMaze(mazeX, mazeY);
    
    // Create border
    for(int i = 0; i < maze.length; i++)
    {
      maze[i][0] = 'X'; // Paint left side
      maze[i][maze[i].length - 1] = 'X'; // Paint right side
    } // Paint sides
    for(int i = 0; i < maze[0].length; i++)
    {
      maze[0][i] = 'X'; // Paint top
      maze[maze.length - 1][i] = 'X'; // Paint bottom
    }
    
    // Fill in with spaces
    for(int a = 0; a < maze.length; a++)
    {
      for(int b = 0; b < maze[a].length; b++)
      {
        if(maze[a][b] != 'X')
          maze[a][b] = ' ';
      }
    }
  } // makeCanvas
  
  // Convert a string to an int
  static int makeInt(String str, int fallBack)
  {
    String tmp = "";
    for(int i = 0; i < str.length(); i++)
    { // Scariest Line Here
      switch(str.charAt(i))
      {
        case '0' :
          tmp += str.charAt(i);
          break;
        case '1' :
          tmp += str.charAt(i);
          break;
        case '2' :
          tmp += str.charAt(i);
          break;
        case '3' :
          tmp += str.charAt(i);
          break;
        case '4' :
          tmp += str.charAt(i);
          break;
        case '5' :
          tmp += str.charAt(i);
          break;
        case '6' :
          tmp += str.charAt(i);
          break;
        case '7' :
          tmp += str.charAt(i);
          break;
        case '8' :
          tmp += str.charAt(i);
          break;
        case '9' :
          tmp += str.charAt(i);
          break;
        default :
          break;
      }
    }
    if(tmp.length() == 0)
      return fallBack;
    else
      return Integer.parseInt(tmp);
  } // makeInt
  
  static boolean mapNamed(String str)
  {
    for(int a = 0; a < maps[0].length; a++)
    {
      if(maps[0][a].equals(str))
        return true;
    }
    return false;
  }
  
  static String mazeHas(char symbol)
  {
    String output = "";
    for(int a = 0; a < maze.length; a++)
    {
      for(int b = 0; b < maze[a].length; b++)
      {
        // Make output
        if(maze[a][b] == symbol)
        {
          output += a;
          output += "@";
          output += b;
          return output;
        }
      }
    }
    return "";
  }
  
  /* File Manipulation
   */
  
  // Take in maps.txt and put the data in maps
  static void ingestFile()
  {
    System.out.print("Importing from maps.txt | Please wait... \n"); // "welcome" message
    String file = FileHandler.getFileString("maps.txt"); // Grab the contents of the file
    String temp = ""; // Temp is short for temporary
    boolean name = true; // Keep track if the current part of the string is a map or name
    
    // Process the string
    for(int a = 0; a < file.length(); a++) // For every character
    {
      if(file.charAt(a) == '@')
      {
        if(name)
        {
          maps[0] = Arrays.copyOf(maps[0], maps[0].length + 1);
          maps[0][maps[0].length - 1] = temp;
          name = false;
        }
        else
        {
          maps[1] = Arrays.copyOf(maps[1], maps[1].length + 1);
          maps[1][maps[0].length - 1] = temp;
          name = true;
        }
        temp = "";
      }
      else
        temp += file.charAt(a);
    }
    System.out.print("Done! \n"); // Completion message
  }
  
  // Take the data from maps and put it in to maps.txt
  static void populateFile()
  {
    System.out.print("Exporting to maps.txt | Please wait... \n");
    String temp = "";
    for(int a = 0; a < maps[1].length; a++) // For some reason, testing maps[0].length() doesn't work
    {
      if(maps[0][a].equals("horner") || maps[0][a].equals("johnson"))
        a++;
      else
      {
        temp += maps[0][a];
        temp += '@';
        temp += maps[1][a];
        temp += '@';
      }
    }
    FileHandler.writeToFile("maps.txt", temp);
    System.out.print("Done! \n");
  }
  
} // MazeJohnson