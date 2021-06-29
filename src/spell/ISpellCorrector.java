package spell;

import java.io.IOException;
import java.util.Set;

/**
 * DO NOT MODIFY THIS FILE!
 * If you think you need to modify this document for your code to work, your code will not pass the TA driver.
 * Your spell corrector class MUST implement the ISpellCorrector interface.
 * Your spell corrector class MUST be named "SpellCorrector" found in the file "SpellCorrector.java".
 * Your spell corrector class MUST be in the spell package.
 */
public interface ISpellCorrector {

	/**
	 * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
	 * for generating suggestions.
	 * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
	 * @param dictionaryFileName the file containing the words to be used
	 * @throws IOException If the file cannot be read
	 * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
	 */
	void useDictionary(String dictionaryFileName) throws IOException;

	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>.
	 * @param inputWord the word we are trying to find or find a suggestion for
	 * @return the suggestion or null if there is no similar word in the dictionary
	 */
	String suggestSimilarWord(String inputWord);

	void deletion(String word, Set<String> words);
	void transposition(String word, Set<String> words);
	void alteration(String word, Set<String> words);
	void insertion(String word, Set<String> words);
	/**
	 *  Goes through each word in the set and
	 *  returns the word with the highest count.
	*/
	String wordSuggestion(Set<String> words, int round);

}

