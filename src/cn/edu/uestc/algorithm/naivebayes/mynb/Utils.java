package cn.edu.uestc.algorithm.naivebayes.mynb;

import java.util.*;

/** Created by BurNI on 2016-04-26.*/
@SuppressWarnings("WeakerAccess")
public class Utils {
    private static final List<String> symbol = Arrays.asList(",", ".", "?", "!", ";", ":");

    public static Map<String, List<ArrayList<String>>> HandleData(Map<ArrayList<String>, String> data)
    {
        Map<String, List<ArrayList<String>>> result = new HashMap<>();
        for (Map.Entry<ArrayList<String>, String> entry : data.entrySet()) {
            result.put(entry.getValue(), result.getOrDefault(entry.getValue(), new ArrayList<>()));
            List<ArrayList<String>> temp = result.get(entry.getValue());
            temp.add(entry.getKey());
            result.put(entry.getValue(), temp);
        }
        return result;
    }

    public static String handleSentence(String sentence)
    {
        String temp = sentence;
        for(String s : symbol)
        {
            temp = temp.replace(s, "");
        }
        return temp.toLowerCase();
    }
}
