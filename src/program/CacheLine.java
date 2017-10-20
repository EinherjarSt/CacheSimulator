package program;

public class CacheLine {
	private boolean dirty;
	private long tag;
	private boolean valid;
	private int countUse;
	
	public CacheLine() {
		tag = -1;
		this.dirty = false;
		this.valid = false;
	}

	public boolean compareTag(long tag) {
		return this.tag == tag;
	}

	public long getTag() {
		return tag;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}
	
	public int getCountUse() {
		return countUse;
	}

	public void incrementCount() {
		this.countUse++;
	}
	
	public void resetCount()
	{
		this.countUse = 0;
	}
	
}
