package com.example.movie.recommand.recommend.model;

/**
 * @author dd
 * @Date 2022/7/28-22:58
 * @function
 */

public class Similarity {
    private   int Item1;
    private   int Item2;
    private   double similarity;
    public Similarity() {
    }

    public int getItem1() {
        return Item1;
    }

    public void setItem1(int item1) {
        Item1 = item1;
    }

    public int getItem2() {
        return Item2;
    }

    public void setItem2(int item2) {
        if(item2<Item1) {
            Item2 = Item1;
            Item1 = item2;
        }
        else
            Item2=item2;

    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Similarity that = (Similarity) o;

        if (Item1 != that.Item1) return false;
        return Item2 == that.Item2;
    }

    @Override
    public int hashCode() {
        int result = Item1;
        result = 31 * result + Item2;
        return result;
    }
    @Override
    public String toString(){
        return  String.format("(%d,%d,%f)",Item1,Item2,similarity);
    }

}
