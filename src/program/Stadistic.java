package program;

public class Stadistic {
	public static int nRefIntruct;
	public static int nRefDatas;
	public static int nMissIntruction;
	public static int nMissDates;
	public static int nWordCopyFromRam;
	public static int nWordCopytoRam;
	
	static{
		nRefIntruct = 0;        	
		nRefDatas = 0;          	
	    nMissIntruction = 0;    
	    nMissDates = 0;         
	    nWordCopyFromRam = 0;   
	    nWordCopytoRam = 0;
	}
	
	public static void printStadistic() {
		System.out.println("Numero de referencias a instrucciones: " + nRefIntruct);
		System.out.println("Numero de referencias a datos: " + nRefDatas);
		System.out.println("Numero de referencias a instrucciones: " + nRefIntruct);
		System.out.println("Numero de faltas a instrucciones: " + nMissIntruction);
		System.out.println("Numero de faltas a datos: " + nMissDates);
		System.out.println("Numero de palabras copiadas desde RAM: " + nWordCopyFromRam);
		System.out.println("Numero de palabras copiadas a RAM: " + nWordCopytoRam);
	}
}

                            