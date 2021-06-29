package spell;

import com.sun.source.tree.Tree;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector{
    Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        //Regex that delimits words of any whitespace.
        scanner.useDelimiter("((\\s+))+");

        while (scanner.hasNext()) {
            String str = scanner.next().toLowerCase();
            trie.add(str);
        }
        scanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Set<String> possibleWords = new TreeSet<String>();
        inputWord = inputWord.toLowerCase();
        if (trie.find(inputWord) != null){
            return inputWord;
        }
        else{
            deletion(inputWord, possibleWords);
            transposition(inputWord, possibleWords);
            alteration(inputWord, possibleWords);
            insertion(inputWord, possibleWords);
            String suggestedWord = wordSuggestion(possibleWords,1);
            if (suggestedWord == null){
                Set<String> roundTwo = new TreeSet<String>();
                for(String s : possibleWords){
                    insertion(s, roundTwo);
                    deletion(s, roundTwo);
                    transposition(s, roundTwo);
                    alteration(s, roundTwo);
                }
                suggestedWord = wordSuggestion(roundTwo,2);
                if (suggestedWord == null){
                    return null;
                }
            }
            return suggestedWord;
        }
    }

    @Override
    public String wordSuggestion(Set<String> words, int round) {
        int maxCount = 0;
        Set<String> maxWords = new TreeSet<String>();
        String suggestedWord = null;
        for (String word: words){
            if (trie.find(word) != null){
                int num = trie.find(word).getValue();
                if (num == maxCount){
                    maxWords.add(word);
                }
                if (num > maxCount){
                    maxWords.clear();
                    maxWords.add(word);
                    maxCount = num;
                    suggestedWord = word;
                }
            }
        }
        //If there are more than one maxWords, return the word at the top of the set.
        if (maxWords.size() > 1){
            for(String word: maxWords){
                suggestedWord = word;
                break;
            }
        }
        return suggestedWord;
    }

    @Override
    public void deletion(String word, Set<String> words) {
        for (int i = 0; i < word.length(); ++i){
            StringBuilder sb=new StringBuilder(word);
            sb.deleteCharAt(i);
            String newWord = sb.toString();
            words.add(newWord);
        }
    }

    @Override
    public void transposition(String word, Set<String> words) {
        char[] myArray = word.toCharArray();
        //Swapping each character with it proceeding charactering.
        for (int i = 0; i < word.length() - 1; ++i){
            char temp = myArray[i];
            myArray[i] = myArray[i + 1];
            myArray[i + 1] = temp;
            String newWord = new String(myArray);
            words.add(newWord);
            myArray = word.toCharArray();
        }
    }

    @Override
    public void alteration(String word, Set<String> words) {
        char[] myArray = word.toCharArray();
        for (int i = 0; i < word.length(); i++){
            //Iterating through each letter in the alphabet.
            for (int j = 0; j < 26; j++){
                myArray[i] = (char)('a' + j);
                String newWord = new String(myArray);
                words.add(newWord);
                myArray = word.toCharArray();
            }
        }
    }

    @Override
    public void insertion(String word, Set<String> words) {
        for (int i = 0; i < word.length() + 1; i++){
            //Iterating through each letter in the alphabet.
            for(char c = 'a'; c <= 'z'; c++){
                StringBuilder sb = new StringBuilder(word);
                sb.insert(i,c);
                String newWord = sb.toString();
                words.add(newWord);
            }
        }
    }

}
