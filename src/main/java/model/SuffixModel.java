package model;

import java.util.Vector;

/**
 * Created by sujil on 10/8/2016.
 */
public class SuffixModel {
    Vector<WordProbability> nextWords = new Vector<WordProbability>();
    int total;

    public SuffixModel() {
        total = 0;
    }

    public boolean suffixIsEmpty() {
        return nextWords.isEmpty();
    }

    public void add(String given) {
        total++;
        int tempIndex = wordIndexInVector(given);
        if (tempIndex >=0 ) {
            nextWords.get(tempIndex).increaseCount();
        }
        else {
            nextWords.add(new WordProbability(given));
        }
    }

    public void computeProbabilityForAll() {
        for (WordProbability word : nextWords) {
            word.computeProbability(total);
        }
    }

    private int wordIndexInVector( String currentWord) {
        for (int i = 0; i < nextWords.size(); i++) {
            if (nextWords.get(i).getWord().equalsIgnoreCase(currentWord)) {
                return i;
            }
        }

        return -1;
    }

    public Vector<WordProbability> getNextWords () { return nextWords;}

    public WordProbability getWordAtIndex(int index) {
        return nextWords.get(index);
    }

    public boolean contains(String val) {
        for (WordProbability word : nextWords) {
            if (word.getWord().equalsIgnoreCase(val)) {
                return true;
            }
        }

        return false;
    }

    public String getNextProbableWord() {
        WordProbability mainWord = nextWords.get(0);
        for ( WordProbability word : nextWords) {
            if (word.getProbability() > mainWord.getProbability()) {
                mainWord = word;
            }
        }

        return mainWord.getWord();
    }
}
