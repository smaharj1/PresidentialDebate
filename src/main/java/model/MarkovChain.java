package model;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 * Created by sujil on 10/8/2016.
 */
public class MarkovChain {

    private HashMap<String, SuffixModel> chain = new HashMap<String, SuffixModel>();
    private final String START = "__S";
    private final String END = "__E";
    static Random rnd = new Random();
    private String filename = "";


    public MarkovChain(String fileN) {
        filename = fileN;
        chain.put(START, new SuffixModel());
        chain.put(END, new SuffixModel());
    }

    public boolean buildModel() throws FileNotFoundException, IOException {
        File inputFile = new File(filename);
        if (!inputFile.exists()) {
            System.out.println("file does not exist");
            return false;
        }

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        String line;

        while ((line = reader.readLine()) != null) {
            addLineToModel(line);
        }

        computeProbabilities();

        return true;
    }

    private void computeProbabilities() {
        for (String key : chain.keySet()) {
            SuffixModel suffix = chain.get(key);
            suffix.computeProbabilityForAll();
        }
    }

    private void addLineToModel(String line) {
        if (line.isEmpty()) return;

        boolean isStartWord = true;
        String[] parsed = line.split(" ");

        for (int wordIndex = 0; wordIndex <parsed.length; wordIndex++) {
            // If word's index is 0, it means that it is the first word.

            if (isStartWord) {
                SuffixModel startWords = chain.get(START);

                startWords.add(parsed[wordIndex]);


                SuffixModel suffix = chain.get(parsed[wordIndex]);

                if (suffix == null && wordIndex+1 < parsed.length) {
                    suffix = new SuffixModel();
                    suffix.add(parsed[wordIndex+1]);
                    chain.put(parsed[wordIndex], suffix);
                }
                isStartWord = false;
            }
            else if (wordIndex == parsed.length-1 || parsed[wordIndex].charAt(parsed[wordIndex].length()-1) == '.') {
                SuffixModel endWords = chain.get(END);
                endWords.add(parsed[wordIndex]);
                isStartWord = true;
            }
            else {
                // This is the general case where we go to the word. Check if it already exists,
                // then add the suffix.
                String current = parsed[wordIndex];
                SuffixModel nextWords = chain.get(parsed[wordIndex]);

                if (nextWords == null) {
                    nextWords = new SuffixModel();
                    nextWords.add(parsed[wordIndex+1]);
                    chain.put(parsed[wordIndex], nextWords);
                }
                else {
                    nextWords.add(parsed[wordIndex+1]);

                    /*int tempIndex = wordIndexInVector(nextWords, parsed[wordIndex]);
                    if (tempIndex >=0) {
                        nextWords.get(tempIndex).increaseCount();
                    }
                    else {
                        nextWords.add(new WordProbability(parsed[wordIndex+1]));
                        chain.put(parsed[wordIndex], nextWords);
                    }*/
                }
                isStartWord = false;
            }
        }
    }

    public String print() {
        // Vector to hold the phrase
        String newPhrase = "";

        // String for the next word
        String nextWord = "";

        // Select the first word
        SuffixModel startWords = chain.get(START);
        SuffixModel endWords = chain.get(END);
        int startWordsLen = startWords.getNextWords().size();
        nextWord = startWords.getWordAtIndex(rnd.nextInt(startWordsLen)).getWord();
        //newPhrase.add(nextWord);

        newPhrase += nextWord;

        // Keep looping through the words until we've reached the end
        while (!endWords.contains(nextWord)) {
            SuffixModel wordSelection = chain.get(nextWord);
            if (wordSelection != null) {
                int wordSelectionLen = wordSelection.getNextWords().size();
                nextWord = wordSelection.getWordAtIndex(rnd.nextInt(wordSelectionLen)).getWord();
                //nextWord = wordSelection.getNextProbableWord();
            }
            else {
                nextWord = startWords.getWordAtIndex(rnd.nextInt(startWordsLen)).getWord();
            }
            newPhrase += " " +nextWord;
            //System.out.print(nextWord + " ");
        }

        return newPhrase;

    }

    private int wordIndexInVector(Vector<WordProbability> givenWords, String currentWord) {
        for (int i = 0; i < givenWords.size(); i++) {
            if (givenWords.get(i).getWord().equalsIgnoreCase(currentWord)) {
                return i;
            }
        }

        return -1;
    }
}
