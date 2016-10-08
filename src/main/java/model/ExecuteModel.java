package model;


import java.io.IOException;

/**
 * Created by sujil on 10/8/2016.
 */
public class ExecuteModel {

    public static void main(String[] args) throws IOException, InterruptedException {
        MarkovChain donald = new MarkovChain("donald.txt");
        MarkovChain hillary = new MarkovChain("hillary.txt");
        donald.buildModel();
        hillary.buildModel();

        while (true) {
            System.out.print("Donald: " + donald.print());
            System.out.println();
            System.out.println();

            Thread.sleep(3000);

            System.out.print("Hillary: "+ hillary.print());
            System.out.println();
            System.out.println();

            Thread.sleep(3000);
        }

    }

}
