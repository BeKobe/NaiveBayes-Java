package cn.edu.uestc.algorithm.naivebayes.mynb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** Created by BurNI on 2016-04-26.*/
@SuppressWarnings("WeakerAccess")
public class NaiveBayes {
    private Map<ArrayList<String>, String> data = new HashMap<>();
    private Map<String, Integer> attrNames = new HashMap<>();

    /**
     * 构造函数
     * 读取文本文件的数据存放在data和attrNames中, 并对数据进行预处理
     * */
    NaiveBayes(String filename)
    {
        File file = new File(filename);
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = reader.readLine()) != null)
            {
                int index = str.lastIndexOf(" ");
                String sub1 = str.substring(0, index);
                String sub2 = str.substring(index+1, str.length());
                String tmp = Utils.handleSentence(sub1);
                String[] sentence = tmp.split("\\s");
                String attr = sub2.trim();
                ArrayList<String> temp = new ArrayList<>();
                temp.addAll(Arrays.asList(sentence));
                data.put(temp, attr);
                attrNames.put(attr, attrNames.getOrDefault(attr, 0) + 1);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 进一步处理数据, 得到统计好的训练数据
     * */
    private Map<String, Map<String, Integer>> getStatisticalData()
    {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        Map<String, List<ArrayList<String>>> handledData = Utils.HandleData(data);
        for(Map.Entry<String, List<ArrayList<String>>> entry : handledData.entrySet())
        {
            Map<String, Integer> temp = new HashMap<>();
            for(ArrayList<String> each : entry.getValue())
            {
                for(String i : each)
                {
                    temp.put(i, temp.getOrDefault(i, 0) + 1);
                }
            }
            result.put(entry.getKey(), temp);
        }
        return result;
    }

    /**
     * 构造朴素贝叶斯分类器
     * @param sentence 需要分类的目标向量
     * @return 分类的类别结果
     * */
    public String classifyNB(ArrayList<String> sentence)
    {
        Map<String, Map<String, Integer>> train = getStatisticalData();
        System.out.println(train);
        Map<String, Double> resultProbability = new HashMap<>();
        for(Map.Entry<String, Map<String, Integer>> entry : train.entrySet())
        {
            Double probability = 1.0;
            for (String word : sentence)
            {
                int freq = entry.getValue().getOrDefault(word, 0);
                if (freq == 0)
                {
                    /**
                     * 对于训练集中没有出现的单词, 运用拉普拉斯平滑定理(加一平滑)处理。
                     * */
                    probability *= ((double)(freq + 1) / (double)(attrNames.get(entry.getKey()) + 2));
                }
                else
                {
                    probability *= ((double) freq / (double) attrNames.get(entry.getKey()));
                }
            }
            probability *= ((double)(attrNames.get(entry.getKey())) / ((double)data.size()));
            resultProbability.put(entry.getKey(), probability);
        }
        List<Map.Entry<String, Double>> sorted = new ArrayList<>(resultProbability.entrySet());
        Collections.sort(sorted, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        System.out.println(sorted);
        return sorted.get(0).getKey();
    }

    public Map<ArrayList<String>, String> getData() {
        return data;
    }

    @SuppressWarnings("unused")
    public void setData(Map<ArrayList<String>, String> data) {
        this.data = data;
    }

    public Map<String, Integer> getAttrNames() {
        return attrNames;
    }

    @SuppressWarnings("unused")
    public void setAttrNames(Map<String, Integer> attrNames) {
        this.attrNames = attrNames;
    }
}
