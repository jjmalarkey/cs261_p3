package decoder;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class Decoder
{

	private class MatchPair
	{
		private int addr;
		private String match;
		public int getAddr() {
			return addr;
		}
		public String getMatch() {
			return match;
		}
		public MatchPair(int i, String s) { // do not need modify methods
			addr = i;
			match = s;
		}
	}
	
	private List<MatchPair> matches;

	private void addToList(int a, String match) {
		int mLength = match.length();
		for(int i = 0; i < matches.size(); i++) {
			if(matches.get(i).getMatch().length() > mLength) { //if string at address i is larger than new match, insert new match at addr
				matches.add(i, new MatchPair(a, match));
				return; //this way the larger string gets pushed down the list, maintaining a small to large order
			}
		}
		matches.add(new MatchPair(a, match));
	}

	public void showMatches() {
		for(int i = matches.size()-1; i >= 0; i--) {
			MatchPair x = matches.get(i);
			try {
				System.out.println(x.getMatch() + " [starting at " + x.getAddr() + "]");
			}
			catch (Exception e) {
				System.out.println("reading error: " + e.getMessage());
			}
		}
	}
	
	private int addToMatchesIfSuitable(int location, String match) {
		int code;
		if(match.length() >= 3) {
			addToList(location, match);
			code = 0;
		}
		else {
			code = 1;
		}
		return code;
	}
	
	public void findMatches (List<String> input)
	{
		//Strategy:
		//for each character in string length (because it is assumed that the strings will be of equal length)
		//iterate over each string at index
		//if character at index for all strings, append character to string blob
		//else, skip rest of strings, check if blob length >= 3
		// if >= 3, add to list
		// if at end of string length, evaluate blob again
		int strLength = input.get(0).length(); //Because all strings must have equal length
		String blobMatch = new String(); //create an empty string placeholder
		int charLocation = 0;
		for(int i = 0; i < strLength; i++) {
			char characterMatch = input.get(0).charAt(i);
			String it;
			int j;
			for(j = 1; j < input.size(); j++) {
				it = input.get(j);
				if(characterMatch != it.charAt(i)) {
					break;
				}
			}
			if(j == input.size()) {	//if character test did not complete, character is not a match
				if(blobMatch.length() == 0) {
					charLocation = i;
				}
				blobMatch += characterMatch;
			} else {
				addToMatchesIfSuitable(charLocation, blobMatch);
				blobMatch = "";
				charLocation = 0;
			}
		}
		addToMatchesIfSuitable(charLocation, blobMatch);
	}

	public Decoder() {
		matches = new ArrayList<MatchPair>();
	}
}
