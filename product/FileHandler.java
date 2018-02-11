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

/*

MIT License

Copyright (c) 2018 Bryan Christopher Johnson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/