package program;

import java.awt.RenderingHints.Key;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class CacheSet {
	private HashMap<Long, CacheLine> lines;
	private int setSize;
	private int nBitOffset;
	
	public void writeData(long tag) {
		this.lines.get(tag).setDirty(true);
		Stadistic.nRefDatas++;

	}
	
	public void readData(long tag) {
		Stadistic.nRefDatas++;
	}
	
	public void readIntruction(long tag) {
		Stadistic.nRefIntruct++;
	}

	public CacheSet(int setSize, int nBitOffset) {
		this.lines = new HashMap<Long, CacheLine>();
		this.setSize = setSize;
		this.nBitOffset = nBitOffset;
	}
	
	public void load (long tag) {
		if (lines.size() == setSize) {
			deleteLeastRecently();
		}

		CacheLine cacheLine = new CacheLine();
		cacheLine.setValid(true);
		cacheLine.setTag(tag);
		this.lines.put(tag, cacheLine);
		Stadistic.nWordCopyFromRam++;
	}
	
	public void deleteLeastRecently () {
		Collection<CacheLine> cacheLines = this.lines.values();
		long tag = 0;
		int mayor = 0;
		for (CacheLine cacheLine : cacheLines) {
			if (cacheLine.getCountUse() > mayor) {
				mayor = cacheLine.getCountUse();
				tag = cacheLine.getTag();
			}
		}
		CacheLine cacheLine = this.lines.get(tag);
		if (cacheLine.isDirty()) {
			Stadistic.nWordCopytoRam++;
		}
		this.lines.remove(tag);
	}
	
	public boolean contain(long tag) {
		return lines.containsKey(tag);
	}
	
	public CacheLine getCacheLine(long tag) {
		return this.lines.get(tag);
	}
}
