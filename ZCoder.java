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
        // String a = args[0];
        // System.out.println (a);
        // decide whether we are encoding or decoding. 
        // loop based on user input
        Scanner sc = new Scanner(System.in);
        String mode = "";
        var validMode = false;

        // ask user whether to encode or decode
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

        

        var hasValidDir = false;
        String dir = "";
        // input file from specified directory
        System.out.println ("Please input file directory:");

        // LinkedList<String> output = new LinkedList<String>();
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



        LinkedList<String> toBeSaved = new LinkedList<String>();


        // decide whether to encode() or decode()
        switch (mode)
        {
            case "1":
                toBeSaved = encode(dir);
                break;
            case "2":
                toBeSaved = decode(dir);
                break;
            default:
                System.out.println("var mode out of bounds");
                break;
        }
        sc.close();

        try
        {
            FileWriter fw = new FileWriter(dir);

	        BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < toBeSaved.size(); i++)
            {
                bw.write(toBeSaved.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch (Exception e)
        {
            System.out.println ("oopsies!");
        }
        
        // terminate

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
            c = (char) Integer.parseInt(binaryString.substring(i, i + 8), 2);
            ascii.append(c);
        }
        return ascii.toString();
    }
    
    private static LinkedList<String> encode(String dir)
    {

        // System.out.println ("This is where files would be encoded :-)");

        Scanner usc = new Scanner (System.in);
        // input file from specified directory

        LinkedList<String> output = new LinkedList<String>();

        Scanner fileGetter = new Scanner("FAKE TEXT THAT WILL BE CHANGED.txt");
        File file = new File(dir);

        // Add all binary to output
        try
        {
            fileGetter = new Scanner(file);
        }
        catch (Exception e)
        {
            System.out.println ("Big Problems happened");
        }

        while (fileGetter.hasNext())
        {
            
            var current = fileGetter.nextLine();
            var binary = AsciiToBinary(current); 
            output.add(binary);

        }

        // terminate
        fileGetter.close();
        usc.close();

        return output;
    }

    private static LinkedList<String> decode(String dir)
    {
        
                // System.out.println ("This is where files would be encoded :-)");

                Scanner usc = new Scanner (System.in);
                // input file from specified directory
        
                LinkedList<String> output = new LinkedList<String>();
        
                Scanner fileGetter = new Scanner("FAKE TEXT THAT WILL BE CHANGED.txt");
                File file = new File(dir);
        
                // Add all binary to output
                try
                {
                    fileGetter = new Scanner(file);
                }
                catch (Exception e)
                {
                    System.out.println ("Big Problems happened");
                }
        
                while (fileGetter.hasNext())
                {
                    
                    var current = fileGetter.nextLine();
                    var binary = BinaryToAscii(current);
         
                    output.add(binary);
        
                }
        
                // terminate
                fileGetter.close();
                usc.close();
        
                return output;


    }
}