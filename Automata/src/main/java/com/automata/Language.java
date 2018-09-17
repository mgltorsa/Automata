/**
 * 
 */
package com.automata;

/**
 * @author Miguel
 *
 */
public class Language {

	private char[] language;
	public Language() {
		this("1,0");
	}
	public Language(String lang) {
		String[] info = lang.split(",");
		language= new char[info.length];
		for (int i = 0; i < info.length; i++) {
			language[i]=info[i].charAt(0);
		}
	}
}
