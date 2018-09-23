/**
 * 
 */
package com.automata;

/**
 * @author Miguel
 *
 */
public class Alphabet {

	private char[] alphabet;
	public Alphabet() {
		this("1,0");
	}
	public Alphabet(String alph) {
		String[] info = alph.split(",");
		alphabet= new char[info.length];
		for (int i = 0; i < info.length; i++) {
			alphabet[i]=info[i].charAt(0);
		}
	}
	
	@Override
	public String toString() {
		String me = "";
		for (int i = 0; i < alphabet.length-1; i++) {
			me+=alphabet[i]+",";
		}
		me+=alphabet[alphabet.length-1];
		return me;
	}
}
