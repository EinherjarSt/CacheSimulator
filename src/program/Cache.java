package program;

import java.util.HashMap;

public class Cache {
	private int sizeCache;
	private HashMap<Long, CacheLine> line;
	private int blockSize;
	private int cacheSize;
	private boolean wt;
	private boolean fa; 
	private int sa;  
	private boolean wna; 
	private boolean split;
	private int nBitTag;
	private int nBitLine;
	private int nBitOffset;
	
	public Cache(int blockSize, int cacheSize, boolean wt, boolean fa, int sa, boolean wna, boolean split) {
		this.line = new HashMap<Long, CacheLine>();
		this.blockSize = blockSize;
		this.cacheSize = cacheSize;
		this.wt = wt;
		this.fa = fa;
		this.sa = sa;
		this.wna = wna;
		this.split = split;
		this.nBitOffset = (int) (Math.log(blockSize)/Math.log(2));
		this.nBitLine = (int) (Math.log(cacheSize)/Math.log(2));
		this.nBitTag = 32 - this.nBitLine - this.nBitOffset;
	}
	
	public void writeData(long address){
		Stadistic.nRefDatas++;
		if (containBlock(address)) {
		}
		else {
			loadBlock(address);
			getCacheLine(address).setDirty(true);
			Stadistic.nMissDates++;
		}
	}
	
	public void readData(long address){
		Stadistic.nRefDatas++;
		if (containBlock(address)) {
			//lee
		}
		else {
			loadBlock(address);
			Stadistic.nMissDates++;
		}
	}
	
	public void readIntruction(long address) {
		Stadistic.nRefIntruct++;
		if (containBlock(address)) {
			//lee
		}
		else {
			loadBlock(address);
			Stadistic.nMissIntruction++;

		}
	}
	
	public void loadBlock(long address) {
		long lineAddress = getLine(address);
		long tag = getTag(address);
		if (this.line.containsKey(lineAddress)) {
			CacheLine cacheLine = this.line.get(lineAddress);
			if (!cacheLine.isDirty()) {
				cacheLine.setTag(tag);
				Stadistic.nWordCopyFromRam++;
			}
			else {
				//se supone que actualiza en ram
				Stadistic.nWordCopytoRam++;
			}
		}
		else {
			CacheLine cacheLine = new CacheLine();
			cacheLine.setValid(true);
			cacheLine.setTag(tag);
			this.line.put(lineAddress, cacheLine);
			//carga el bloque
		}
	}
	
	/**
	 * Revisa si alguna linea del cache contiene el bloque.
	 * @param address la direccion de memoria que incluye el tag|line|offset
	 * @return true si lo contiene, false caso contrario.
	 */
	public boolean containBlock(long address) {
		long lineAddress = getLine(address); 
		if (this.line.containsKey(lineAddress)) {
			long tag = getTag(address);
			return this.line.get(lineAddress).compareTag(tag);
		}
		return false;
	}
	
	private long getTag(long address) {
		address = address >> nBitOffset + nBitLine;
		return address;
	}
	private long getLine(long address) {
		address = address >> nBitOffset;
		address = (int) (address % Math.pow(2,nBitLine));
		return address;
	}
	private long getOffset(long address) {
		address = (int) (address % Math.pow(2, this.nBitOffset));
		
		return address;
	}
	
	/**
	 * Pide la linea del cache
	 * @param address la direccion que contiene tag|line|offset
	 * @return la linea de cache
	 */
	private CacheLine getCacheLine(long address) {
		long lineAddress = getLine(address); 
		if (this.line.containsKey(lineAddress)) {
			return this.line.get(lineAddress);
		}
		return null;
	}
	
	//Main de prueba local
	public static void main(String[] args) {
		int blockSize = 256; 
		int cacheSize = 128;
		boolean wt = false; // true Write-Through: false Write Back
		boolean fa = false; // FullAssociative?
		int sa = 0; // 0 no es set associative: cualquier otro si lo es
		boolean wna = false; // true Write-No-Allocate : false Write-Allocate
		boolean split = false; // cache split?
		Cache cache = new Cache(blockSize, cacheSize, wt, fa, sa, wna, split);
	}
	
	private void printInfoAddress(long address) {
		System.out.println("------------------------------------------------");
		System.out.println("tamBloque = " + blockSize + " tamCache = " + cacheSize);
		System.out.println("bitTag = " + nBitTag + " bitLine = " + nBitLine + " bitOffset = " + nBitOffset);
		System.out.println("Numero: "+ address + " bit : "+ Binary.binaryRepresentation(address));
		System.out.println("Tag: " + getTag(address) + " bit : "+ Binary.binaryRepresentation(getTag(address)));
		System.out.println("line: " + getLine(address) + " bit : "+ Binary.binaryRepresentation(getLine(address)));
		System.out.println("offset: " + getOffset(address) + " bit : "+ Binary.binaryRepresentation(getOffset(address)));
	}
}
