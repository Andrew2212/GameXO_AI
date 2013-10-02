package org.hexlet.gamexo.ai.gardnerway;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author  KOlegA
 * Date: 01.10.13
 * Time: 22:18
 */
public class FileMaster {

    private RandomAccessFile file;

    public FileMaster(String filename, String position) {
        filename += ".xo";
	    try{
	    file = new RandomAccessFile(filename, "rw");
        } catch (FileNotFoundException ex){
	        System.out.println("File " + filename + " not found");
        }


	    try {
		    file.seek(file.getFilePointer());
		    file.writeChars(position);
	    }catch (IOException ex) {
		    System.out.println("IOException");
	    }

    }


    public static void writeFile(){

    }

    public static void readFile(){

    }
}
