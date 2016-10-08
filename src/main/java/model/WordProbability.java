package model;

/**
 * Created by sujil on 10/8/2016.
 */
public class WordProbability {
    private String word;
    private int totalOccurence;
    private double probability;

    public WordProbability(String given) {
        word = given;
        totalOccurence = 1;
    }

    public WordProbability(String given, int grandTotal) {
        word = given;
        totalOccurence = 1;
        probability = (1.0 * totalOccurence) / grandTotal;
        probability *= 100;
    }


    public boolean increaseCount () {
        totalOccurence++;
        return true;
    }

    public void computeProbability(int grandTotal) {
        probability = (1.0 *totalOccurence) / grandTotal;
        probability *= 100;
    }

    public double getProbability() {
        return probability;
    }

    public String getWord() {
        return word;
    }


}
