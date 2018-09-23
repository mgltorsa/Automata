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
	private String alp;
	public Alphabet() {
		this("1,0");
	}
	public Alphabet(String alph) {
		alp=alph;
		String[] info = alph.split(",");
		alphabet= new char[info.length];
		for (int i = 0; i < info.length; i++) {
			alphabet[i]=info[i].charAt(0);
		}
	}
	public char[] getAlphabet() {
		return alphabet;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return alp;
	}
}
