/* HuffDecoder.java

   Starter code for compressed file decoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Add your name/studentID/date here)
*/

import java.io.*;


public class HuffDecoder{

    private HuffFileReader inputReader;
    private BufferedOutputStream outputFile;

    /* Basic constructor to open input and output files. */
    public HuffDecoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputReader = new HuffFileReader(inputFilename);
        outputFile = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }


    public void decode() throws IOException{

        /* This is where actual decoding should happen. */

        /* The outputFile.write() method can be used to write individual bytes to the output file.*/
        
    }


    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffDecoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffDecoder decoder = new HuffDecoder(inputFilename, outputFilename);
            decoder.decode();
        } catch (FileNotFoundException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
