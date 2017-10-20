package program;

import java.util.Collection;
import java.util.HashMap;

public class CacheSet {
	private int blocksize;
	private HashMap<Long, CacheLine> lines;
	private int nBitOffset;
	private int setSize;
	private boolean wna;
	private boolean wt;

	public CacheSet(int setSize, int nBitOffset, int blocksize, boolean wt, boolean wna) {
		this.lines = new HashMap<Long, CacheLine>();
		this.setSize = setSize;
		this.blocksize = blocksize;
		this.nBitOffset = nBitOffset;
		this.wt = wt;//write throught
		this.wna = wna;//write no allocate
	}
	
	public boolean contain(long tag) {
		return lines.containsKey(tag);
	}
	
	private void deleteLeastRecently () {
		Collection<CacheLine> cacheLines = this.lines.values();
		long tag = -1;
		int mayor = -1; // ya que el el tag no sera negativo se usa como bandera para comparar el mayor elemento
		for (CacheLine cacheLine : cacheLines) {
			if (cacheLine.getCountUse() > mayor) {
				mayor = cacheLine.getCountUse();
				tag = cacheLine.getTag();
			}
		}
		CacheLine cacheLine = this.lines.get(tag);
		if (cacheLine.isDirty()) {
			Stadistic.nWordCopytoRam+= blocksize;
		}
		this.lines.remove(tag);
	}

	public CacheLine getCacheLine(long tag) {
		return this.lines.get(tag);
	}
	
	private void incrementCountLine() {
		this.lines.forEach((key, cacheline) -> {
			cacheline.incrementCount();
		});
	}
	
	public boolean isSetFull() {
		return this.lines.size() == this.setSize;
	}
	
	public void load (long tag) {
		if (lines.size() == setSize) {
			deleteLeastRecently();
		}

		CacheLine cacheLine = new CacheLine();
		cacheLine.setValid(true);
		cacheLine.setTag(tag);
		this.lines.put(tag, cacheLine);
		
		Stadistic.nWordCopyFromRam+= blocksize;
	}
	
	/*
	 * Siempre se debe preguntar si este set contiene el dato
	 * @param tag la parte de los bits correspondientes al tag
	 */
	public void readData(long tag) {
		CacheLine cacheLine = this.lines.get(tag); 
		incrementCountLine();
		cacheLine.resetCount();
	}
	
	/*
	 * Siempre se debe preguntar si este set contiene el dato
	 * @param tag la parte de los bits correspondientes al tag
	 */
	public void readIntruction(long tag) {
		CacheLine cacheLine = this.lines.get(tag); 
		incrementCountLine();
		cacheLine.resetCount();

	}
	
	/*
	 * Siempre se debe preguntar si este set contiene el dato
	 * @param tag la parte de los bits correspondientes al tag
	 */
	public void writeData(long tag) {
		CacheLine cacheLine = this.lines.get(tag);
		
		/*si esta activo write write throught no se activa el dirty bit 
		 *porque ya se actualiza la ram tambien.
		 */
		if(!wt) {
			cacheLine.setDirty(true);
		}
		incrementCountLine();
		cacheLine.resetCount();
	}
}
