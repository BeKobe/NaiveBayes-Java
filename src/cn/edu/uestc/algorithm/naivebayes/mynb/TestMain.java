package cn.edu.uestc.algorithm.naivebayes.mynb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Created by BurNI on 2016-0 4-26.*/
public class TestMain {
    public static void main(String[] args)
    {
        NaiveBayes nb = new NaiveBayes("data.txt");
        System.out.println(nb.getData());
        System.out.println(nb.getAttrNames());

        String a = "Stupid garbage.";
        a = Utils.handleSentence(a);
        List<String> sentence = Arrays.asList(a.split("\\s"));
        System.out.println(nb.classifyNB(new ArrayList<>(sentence)));
    }
}
