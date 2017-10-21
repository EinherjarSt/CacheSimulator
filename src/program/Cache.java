package program;

import java.util.HashMap;
/**
 * 	Crea el cache, este cache tiene multiples formatos cuya configuracion es decidida a 
 * traves de los parametos del contructor.  
 * 
 * 	Si el tamaño del conjunto es uno esta el cache actua como Direct Map
 * 	Si el tamaño del conjunto es igual al tamaño del cache, entonces este actua como Fully
 * associative.
 * 	Si el tamaño del conjunto es cualquier otro, entonces actua como set associative.
 * @author Gabriel
 *
 */
public class Cache {
	private HashMap<Long, CacheSet> sets;
	private int blockSize;
	private int cacheSize;
	private boolean wt; //Write-Through?
	private boolean fa; //Fully associative?
	private int sizeSet;  
	private boolean wna; //Write-No-Allocate?
	private boolean split;
	private int nBitTag;
	private int nBitOffset;
	private int nBitSet;
	
	public Cache(int blockSize, int cacheSize, boolean wt, boolean fa, int sa, boolean wna, boolean split) {
		this.sets = new HashMap<Long, CacheSet>();
		this.blockSize = blockSize;
		this.cacheSize = cacheSize;
		this.wt = wt;
		this.fa = fa;
		this.sizeSet = sa;
		this.wna = wna;
		this.split = split;
		this.nBitOffset = (int) (Math.log(blockSize)/Math.log(2));
		this.nBitSet = (int) (Math.log(this.cacheSize/sizeSet)/Math.log(2));;
		this.nBitTag = 32 - this.nBitSet - this.nBitOffset;
	}
	
	public void writeData(long address){
		Stadistic.nRefDatas++;
		if (split) address = passToDataZone(address);
		long tag = getTag(address);
		CacheSet cacheSet;
		if (!containBlock(address)) {
			Stadistic.nMissDates++;
			if(wna) {
				cacheSet = getCacheSet(address);
				if(cacheSet != null) {
					if(cacheSet.isSetFull()) Stadistic.nWordCopytoRam+= blockSize;
					return; //ya escribi en ram asi que no tengo xq continuar
				}
			}
			loadBlock(address);
		}
		cacheSet = this.getCacheSet(address);
		cacheSet.writeData(tag);
		//Si esta activo write no allocate escribe tambien en ram
		if(wt) Stadistic.nWordCopytoRam+= blockSize;
	}
	
	public void readData(long address){
		Stadistic.nRefDatas++;
		if (split) address = passToDataZone(address);
		long tag = getTag(address);
		if (!containBlock(address)) {
			Stadistic.nMissDates++;
			loadBlock(address);
		}
		CacheSet cacheSet = this.getCacheSet(address);
		cacheSet.readData(tag);
	}
	
	public void readIntruction(long address) {
		Stadistic.nRefIntruct++;
		if (split) address = passToIntructZone(address);
		long tag = getTag(address);
		if (!containBlock(address)) {
			Stadistic.nMissIntruction++;
			loadBlock(address);
		}
		CacheSet cacheSet = this.getCacheSet(address);
		cacheSet.readIntruction(tag);
	}
	
	public void loadBlock(long address) {
		long tag = getTag(address);
		long setAddress = getSet(address);
		CacheSet cacheSet;
		
		if (!this.sets.containsKey(setAddress)) {
			cacheSet = new CacheSet(sizeSet, blockSize, nBitOffset, wt, wna);
			this.sets.put(setAddress, cacheSet);
		} 
		cacheSet = this.getCacheSet(address);
		cacheSet.load(tag);
	}
	
	/**
	 * Revisa si algun set del cache contiene el bloque.
	 * @param address la direccion de memoria que incluye el tag|set|offset
	 * @return true si lo contiene, false caso contrario.
	 */
	public boolean containBlock(long address) {
		long setAddress = getSet(address); 
		if (this.sets.containsKey(setAddress)) {
			long tag = getTag(address);
			return this.sets.get(setAddress).contain(tag);
		}
		return false;
	}
	
	private long getTag(long address) {
		address = address >> nBitOffset + nBitSet;
		return address;
	}

	private long getSet(long address) {
		address = address >> nBitOffset;
		address = (int) (address % Math.pow(2,nBitSet));
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
	private CacheSet getCacheSet(long address) {
		long setAddress = getSet(address); 
		if (this.sets.containsKey(setAddress)) {
			return this.sets.get(setAddress);
		}
		return null;
	}
	
	//Main de prueba local
	private static void main(String[] args) {
		int blockSize = 16; 
		int cacheSize = 512;
		boolean wt = false; // true Write-Through: false Write Back
		boolean fa = false; // FullAssociative?
		int sa = 1; // 0 no es set associative: cualquier otro si lo es
		boolean wna = false; // true Write-No-Allocate : false Write-Allocate
		boolean split = false; // cache split?
		Cache cache = new Cache(blockSize, cacheSize, wt, fa, sa, wna, split);
		long a = 16;
		System.out.println(Binary.binaryRepresentation(a));
		cache.printInfoAddress(a);
		System.out.println(cache.getSet(a));
	}
	
	private void printInfoAddress(long address) {
		System.out.println("------------------------------------------------");
		System.out.println("tamBloque = " + blockSize + " tamCache = " + cacheSize);
		System.out.println("bitTag = " + nBitTag + " bitSet = " + nBitSet + " bitOffset = " + nBitOffset);
		System.out.println("Numero: "+ address + " bit : "+ Binary.binaryRepresentation(address));
		System.out.println("Tag: " + getTag(address) + " bit : "+ Binary.binaryRepresentation(getTag(address)));
		System.out.println("set: " + getSet(address) + " bit : "+ Binary.binaryRepresentation(getSet(address)));
		System.out.println("offset: " + getOffset(address) + " bit : "+ Binary.binaryRepresentation(getOffset(address)));
	}
	
	public String formatAddress(String binarySequence) {
		String s1 = binarySequence.substring(0, nBitTag+6);
		String s2 = binarySequence.substring(nBitTag+6, nBitTag+nBitSet+6);
		String s3 = binarySequence.substring(nBitTag+nBitSet+6, binarySequence.length());
		return s1 + " | " + s2 + " | " + s3;
	}
	
	/*
	 * Invierte el bit mas significativo de la direccion del conjunto a 0.
	 * Si ya esta en 0 lo deja igual.
	 * 
	 * Solo se usan con split activo
	 */
	public long passToIntructZone(long address) {
		String representation = Binary.toBinaryString(address);
		if (!representation.subSequence(nBitTag+1, nBitTag+2).equals("0")) {
			long setAddress = getSet(address);
			int exponent = nBitSet;
			setAddress = (long) (setAddress + Math.pow(2, exponent-1) - Math.pow(2, exponent));
			String newRepresentation = representation.substring(0, nBitTag) + "0"+ Long.toBinaryString(setAddress) + representation.substring(nBitTag+nBitSet,representation.length());
			address = Binary.valuesOf(newRepresentation);
		}
		return address;
	}
	
	/*
	 * Invierte el bit mas significativo de la direccion del conjunto a 1.
	 * Si ya esta en 1 lo deja igual.
	 * Solo se usan con split activo
	 */
	public long passToDataZone(long address) {
		String representation = Binary.toBinaryString(address);
		if (!representation.subSequence(nBitTag+1, nBitTag+2).equals("1")) {
			long setAddress = getSet(address);
			int exponent = nBitSet;
			setAddress = (long) (setAddress + Math.pow(2, exponent-1));
			String newRepresentation = representation.substring(0, nBitTag) + Long.toBinaryString(setAddress) + representation.substring(nBitTag+nBitSet,representation.length());
			address = Binary.valuesOf(newRepresentation);
		}
		return address;
	}
}
