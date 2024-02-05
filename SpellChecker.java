
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		String strTail = str.substring(1);
		return strTail;
	}

	public static String head(String str) {
		String strHead = str.substring(0, 1);
		return strHead;
	}

	public static int levenshtein(String word1, String word2) {
		// so it won't affect the edit distance
		word1 = word1.toLowerCase(); 
		word2 = word2.toLowerCase();

		int minimum;

		if (word2.length() == 0) {
			return 0;
		}

		if (word1.length() == 0) {
			return 0;
		}

		if (head(word1).equals(head(word2))) {
			levenshtein(tail(word1), tail(word2));
		}

		minimum = Math.min(levenshtein(tail(word1), word2), levenshtein(word1, tail(word2)));
		minimum = Math.min(minimum, levenshtein(tail(word1), tail(word2)));

		return 1 + minimum;
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i =  0; i < dictionary.length; i++) {
			String word = in.readLine();
			dictionary[i] = word;
		}
	
		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int minDistance = word.length();
		String similarWord = word;
		for (int i = 0; i < dictionary.length; i++) {
			if (levenshtein(word, dictionary[i]) < minDistance) {
				minDistance = levenshtein(word, dictionary[i]);
				similarWord = dictionary[i];
			}
		}

		if (minDistance > threshold) {
			similarWord = word;
		}

		return similarWord;
	}

}
