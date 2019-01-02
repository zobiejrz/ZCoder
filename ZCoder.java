/*
The ZCoder file inputs files then decrypts or encodes based on capitalization

 */

import java.util.LinkedList;
import java.util.Scanner;

import com.sun.jdi.InvalidLineNumberException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.io.BufferedWriter;

class ZCoder
{

	public static void main(String[] args)
	{
        /* This function will set mode to encode or decode, get a valid directory.
        ** Then it will grab the file at the directory and pass it through AsciiToBinary or BinaryToAscii (depending on user input)
        ** Then it will overwrite the directory with the new text
        */

        Scanner sc = new Scanner(System.in);

        String mode = "";
        String dir = "";

        LinkedList<String> file = new LinkedList<String>();
        LinkedList<String> outputFile = new LinkedList<String>();

        // These variables are used for looping when input is incorrect
        var hasValidDir = false;
        var validMode = false;

        // Asks user whether to encode or decode
        // Will loop until input is either '1' or '2'
        do
        {
            System.out.println("Set mode equal to ENCODE (1) or DECODE (2)?");
            mode = sc.nextLine();
            
            switch (mode)
            {
                case "1":
                    validMode = true;
                    break;
                case "2":
                    validMode = true;
                    break;
                default:
                    System.out.println("Input not understood. Please input '1' to set to ENCODE, or '2' for DECODE.");
                    break;
            }
        }
        while(!validMode);


        // Input file from a specified directory
        // Will loop until it can find a file at the directory
        System.out.println ("Please input file directory:");
        do
        {
            dir = sc.nextLine();

            try
            {
                Scanner tempFile = new Scanner (new File(dir));
                hasValidDir = true;
                tempFile.close();
            }
            catch (Exception e)
            {
                System.out.println ("Directory invalid, please try again:");
            }
        }
        while (!hasValidDir);

        // Sets LinkedList<String> file equal to the LinkedList<String> output from filGrab() using the directory
        file = fileGrab(dir);

        // Uses the inputted mode to process the file
        // Using LinkedList<String> file will be sent line by line through AsciiToBinary() or BinaryToAscii() and added to LinkedList<String> outputFile
        switch (mode)
        {
            case "1":
                
                for (int i = 0; i<file.size(); i++)
                {
                    outputFile.add(AsciiToBinary(file.get(i)));
                }
                break;
            case "2":
                for (int i = 0; i<file.size(); i++)
                {
                    outputFile.add(BinaryToAscii(file.get(i)));
                }
        }

        // Each index of LinkedList<String> outputFile is know written to the inputted directory
        try
        {
            FileWriter fw = new FileWriter(dir);
	        BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < outputFile.size(); i++)
            {
                bw.write(outputFile.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch (Exception e)
        {
            System.out.println ("oopsies!");
        }
        
        // terminate
        sc.close();
    }

    // Pulled from
    // https://stackoverflow.com/questions/19867918/ascii-to-binary-conversion-program-in-java
    private static String AsciiToBinary(String asciiString)
    {  

        byte[] bytes = asciiString.getBytes();  
        StringBuilder binary = new StringBuilder();  
        for (byte b : bytes)  
        {  
           int val = b;  
           for (int i = 0; i < 8; i++)  
           {  
              binary.append((val & 128) == 0 ? 0 : 1);  
              val <<= 1;  
           }  
          // binary.append(' ');  
        }  
        return binary.toString();  
    }

    // Pulled from
    // https://github.com/ReflxctionDev/BinaryConverter/blob/master/src/main/java/net/reflxction/binary/utils/BinaryUtils.java
    private static String BinaryToAscii(String binaryString)
    {
        StringBuilder ascii = new StringBuilder();
        char c;

        // original code was i+=9, that was incorrect.
        for (int i = 0; i <= binaryString.length() - 8; i += 8) {
            
            // Use try-catch to see if inputted data is valid (only 1s & 0s), otherwise just return itself
            try
            {
                var x = binaryString.substring(i, i + 8);
                c = (char) Integer.parseInt(x, 2);
                ascii.append(c);
            }
            catch(Exception e)
            {
                var errOutput = binaryString.substring(i, i + 0);
                ascii.append(errOutput);
            }
                
        }
        return ascii.toString();
    }

    private static LinkedList<String> fileGrab(String dir)
    {

        /* This function will grab a file from an inputted directory and output a LinkedList<String> where every index is a line of the file
        */

        Scanner fileGetter = new Scanner("FAKE TEXT THAT WILL BE CHANGED.txt");

        // This is the file that is passed into Scanner fileGetter
        File file = new File(dir);

        // Scanner for user input (uSERscANNER)
        Scanner usc = new Scanner (System.in);

        // LinkedList<String> to be outputted.
        LinkedList<String> output = new LinkedList<String>();

        // Puts the file im grabbing into a Scanner
        // This won't ever catch an exception because the directory was tested in public static void main(String[] args)
        try
        {
            fileGetter = new Scanner(file);
        }
        catch (Exception e)
        {
            System.out.println ("Big Problems happened");
        }

        // Puts file into LinkedList<String> output line by line.
        while (fileGetter.hasNext())
        {

            var current = fileGetter.nextLine();
            output.add(current);

        }

        // closes scanners and returns the LinkedList<String> output
        fileGetter.close();
        usc.close();

        return output;        
    }
    
}