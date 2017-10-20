package program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

public class Main {

	public static void main(String[] args) {
		readArgs(args);
	}
	
	public static void readArgs(String[] args){
		//opciones por defecto
		int blockSize = 256; 
		int cacheSize = 128;
		boolean wt = false; // true Write-Through: false Write Back
		boolean fa = false; // FullAssociative?
		int sa = 1; // tamaño de conjunto
		boolean wna = false; // true Write-No-Allocate : false Write-Allocate
		boolean split = false; // cache split?
		String fileDir = "";  
		for(int i = 0; i < args.length; i++) {
			if (args[i].equals("-bs")) {
				blockSize = Integer.valueOf(args [++i]);
			}
			else if (args[i].equals("-cs")) {
				cacheSize = Integer.valueOf(args [++i]);
			}
			else if (args[i].equals("-wt")) {
				wt = true;
			}
			else if (args[i].equals("-fa")) {
				fa = true;
			}
			else if (args[i].equals("-sa")) {
				sa = Integer.valueOf(args [++i]);
			}
			else if (args[i].equals("-wna")) {
				wna = true;
			}
			else if (args[i].equals("-split")) {
				split = true;
			}
			else {
				fileDir = args[i];
			}
		}
			printInfoCache(blockSize, cacheSize, wt, fa, sa, wna, split, fileDir);
			Cache cache = new Cache(blockSize, cacheSize, wt, fa, sa, wna, split);
			trace(fileDir, cache);
		}
		
		private static void printInfoCache(int blockSize, int cacheSize, boolean wt, boolean fa, int sa, boolean wna,
			boolean split, String fileDir) {
			
			System.out.println("BlockSize = " + blockSize);
			System.out.println("cacheSize = " + cacheSize);
			System.out.println("wt = " + wt);
			System.out.println("fa = " + fa);
			System.out.println("sa = " + sa);
			System.out.println("wna = " + wna);
			System.out.println("split = " + split);
			System.out.println("fileDir = " + fileDir);
		}
		
		private static void trace(String fileDir, Cache cache) {
				File file = new File(fileDir);
				FileReader in;
				int i=0;
				try{
					in = new FileReader(file);
					BufferedReader input = new BufferedReader(in);
					String line;
					while ((line = input.readLine()) != null) {
						//0 Tipo de operacion
						//1 Direccion
						String[] token = line.split(" ");
						long address = Binary.valuesOf(token[1], 16);
						System.out.println(token[0] + " : " + Binary.binaryRepresentation(address));
						/* 0 denota leer dato, 
						 * 1 denota escribir dato, y 
						 * 2 denota leer instrucción
						 * */
						if (token[0].equals("0")) {
							cache.readData(address);
						}
						if (token[0].equals("1")) {
							cache.writeData(address);
						}
						if (token[0].equals("2")) {
							cache.readIntruction(address);
						}
					}
					Stadistic.printStadistic();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.out.println("No se encontro el archivo");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error al leer");
				}
		}
}
