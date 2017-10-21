package program;

public class Binary {
	
	public static void main(String[] args) {
			//Integer a =  Integer.parseUnsignedInt(Integer.toUnsignedString(i));
			//Integer a =  -1;
			Integer a = - 1000000;
			String print = binaryRepresentation(a);
			System.out.println(Integer.toUnsignedString(a) +" : "+ print);
	}
	
	public static long valuesOf(String value,int radix) {
		return Long.valueOf(value, radix);
	}
	
	/**
	 * Devuelve un long que representa el value.
	 * @param value String con la cadena binaria.
	 * @return
	 */
	public static long valuesOf(String value) {
		return Long.valueOf(value, 2);
	}
	
	public static String binaryRepresentation(long value){
		//String string = Integer.toBinaryString(value);
		String string = Binary.toBinaryString(value);
		string = " ("+ string.length() + ") " + string;
		return string;
	}
	
	/**
	 * Devuelve en un string value en binario (32bits)
	 * @param value
	 * @return
	 */
	public static String toBinaryString(long value){
		String s = "";
		for(int i= 31; i >= 0; i--){
			if ((value & (1 << i)) != 0) {
				s += 1;
			}
			else {
				s += 0;
			}
		}
		return s;
	}
	
	public static String binaryRepresentation(String value,int radix){
		return Long.toBinaryString(valuesOf(value, radix));
	}
}
