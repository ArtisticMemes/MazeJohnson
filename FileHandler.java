/* MapStorage
 * 
 * These methods are used to grab and write to a file in MazeJohnson
 * 
 * Author : Bryan Johnson
 */

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter; 
import java.util.Scanner;
import java.io.IOException;

public class FileHandler
{
  public static void main(String[] args) throws FileNotFoundException
  {
  }
  
  public static void writeToFile(String fileName, String content)
  {
    try
    {
      File file = new File(fileName);
      
      // If file doesn't exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }
      
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      
      bw.write(content);
      bw.close();
    }
    catch(FileNotFoundException e)
    {
      System.out.print(" \n" + e + " \n");
    }
    catch(IOException e)
    {
      System.out.print(" \n" + e + " \n");
    }
  }
  
  
  public static String getFileString(String fileName)
  {
    
    try 
    {
      File file = new File(fileName);
      if (!file.exists())
      {
        file.createNewFile();
      }
      
      Scanner scan = new Scanner(file);
      String output = "";
      
      while(scan.hasNext())
      {
        output += scan.nextLine();
        output += 'Y';
      }
      scan.close();
      return output;
    }
    catch(FileNotFoundException e)
    { 
      System.out.print(" \n" + e + " \n"); 
    }
    catch(IOException e)
    {
      System.out.print(" \n" + e + " \n");
    }
    return "";
  }
}