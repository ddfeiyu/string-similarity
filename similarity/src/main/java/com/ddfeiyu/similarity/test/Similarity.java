package com.ddfeiyu.similarity.test;

public class Similarity {


    public static final String content1 = "今天小小和爸爸一起去摘草莓，小小说今天的草莓特别的酸，而且特别的小，关键价格还贵";

    public static final String content2 = "今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的";


    public static void main(String[] args) {
        double score = CosineSimilarity.getSimilarity(content1, content2);
        System.out.println("相似度：" + score);

        score = CosineSimilarity.getSimilarity(content1, content1);
        System.out.println("相似度：" + score);
    }
}
