package org.hexlet.gamexo.ai.gardnerway;

import org.hexlet.gamexo.ai.utils.LoggerAI;

import java.io.*;

/**
 * @author  KOlegA
 * Date: 01.10.13
 * Time: 22:18
 */
public class FileMaster {

    private File file;
    private RandomAccessFile fileWriter;

    public FileMaster(String baseDir, String filename) {

        File f1 = new File("src/org/hexlet/gamexo/ai/gardnerway/bin/" + baseDir);

	    f1.mkdirs();
        file = new File(f1, filename);
	    try{
	    fileWriter = new RandomAccessFile(file, "rw");
	    } catch (FileNotFoundException e){
		    LoggerAI.p("File not found");
        }
    }


    public void writeFile(String position)  {
	    try {

		    fileWriter.seek(file.length());
		    fileWriter.writeBytes(position + '\n');
		    fileWriter.close();

	    } catch (FileNotFoundException ex) {
		    LoggerAI.p("File " + file.getName() + " not found");

	    } catch (IOException e) {
		    LoggerAI.p("IOException");
	    }
    }

    public String readFile() {

	    try {
		    return fileWriter.readLine();
	    } catch (IOException e){
		    LoggerAI.p("Read File error");
	    }
        return null;
    }

	public void readFromScratch(){
		try{
			fileWriter.seek(0);
		}catch (IOException e){
			LoggerAI.p("Set reading at 0 error");
		}
	}

	public void closeReading() {

		try {
			fileWriter.close();
		} catch (IOException e) {
			LoggerAI.p("Close error");
		}
	}

    public boolean isEmpty() {
        return file.length() == 0;
    }
}
