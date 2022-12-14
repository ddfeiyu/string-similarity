package com.example.movie.recommand.recommend;

import com.example.movie.recommand.recommend.commonUtil.DBUtil;
import com.example.movie.recommand.recommend.model.RatingItem;
import lombok.Data;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Data
public class ForDataSet {

	public static void main(String[] args) throws SQLException {
        computeSimilary();

	}
public void getDataFromFile() throws FileNotFoundException, UnsupportedEncodingException {
	FileInputStream fout = new FileInputStream("ml-100k\\u.data");//("ml-100k/u.data");
	InputStreamReader reader = new InputStreamReader(fout, "utf8");
	BufferedReader   in   =   new   BufferedReader(reader);
	String line=null;
	ForDataSet hello=new ForDataSet();

	try {
		while(	(line=in.readLine())!=null) {
            System.out.println("-------------");
            System.out.println("--------------");
            System.out.println(line);
            String newline=line.replaceAll("[\t]", " ");
            System.out.println("--------");
            int i=0;
            String []ht=newline.split(" ");
            for(String k:ht) {
                i++;
                System.out.println("index"+k);
            }
            Integer userId=Integer.parseInt(ht[0]);
            Integer movieId=Integer.parseInt(ht[1]);
            Integer rating=Integer.parseInt(ht[2]);
            RatingItem ratingItem=new RatingItem();
            ratingItem.setUserId(userId);
            ratingItem.setItemId(movieId);
            ratingItem.setRating(rating);
            System.out.println(ratingItem);
            System.out.println("--------");

        }
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public  static  Long start(){
	    return  System.currentTimeMillis()/1000;
}
public static  Long end(){

    return  System.currentTimeMillis()/1000;
}
public static void computeSimilary() throws SQLException {
    DBUtil dbUtil=DBUtil.createDataSourc();
    /**
     *
     *
     *
     */

    //?????????????????????,????????????????????????????????????
    Long begin=System.currentTimeMillis();
    String sql="delete from avgrating";
    dbUtil.executeUpdate(sql);
    String avgSql="insert into avgrating SELECT usrid,avg(rating) avgRating FROM rating group by usrid";
    dbUtil.executeUpdate(avgSql);

    //??????????????????
    String deleteRecomSql="delete from recom";
    dbUtil.executeUpdate(deleteRecomSql);
    //???????????????????????????
    String deleteSimSql="delete from sim";
    dbUtil.executeUpdate(deleteSimSql);


    //??????itemId??????
    String findItemidSql="select distinct itemid from rating";
    ResultSet itemIdResultSet=dbUtil.executeQuery(findItemidSql);
    List<Integer> itemIdSet=new LinkedList<Integer>();
    Integer id=null;
    while(itemIdResultSet.next()){
        id=itemIdResultSet.getInt("itemid");
        itemIdSet.add(id);
    }
    //  itemIdResultSet.close();

    //???????????????id??????

    Integer itemSetSize=itemIdSet.size();
    for(int i=0;i<itemSetSize;i++){
        Integer itemi=null;
        Integer itemj = null;
        itemi=itemIdSet.get(i);
        for(int j=i;j<itemSetSize;j++) {

            itemj =itemIdSet.get(j);


                /*?????????????????????i?????????j
                *??????????????????
                *
                * */

            //??????itemi???itemj?????????????????????id?????????
            String findUserIdItemiSql="select usrid from rating where itemid='"+itemj+"'and usrid in (select usrid from rating where itemid='"
                    +itemi+"')";
            ResultSet userIdItemsResultSet= dbUtil.executeQuery(findUserIdItemiSql);
            List<Integer> userIdInterceptSet=new LinkedList<Integer>();
            while(userIdItemsResultSet.next()){
                Integer userId= userIdItemsResultSet.getInt("usrid");
                userIdInterceptSet.add(userId);
            }
            //   System.out.println(userIdInterceptSet.size());
            //  System.out.println("---------");
            //  userIdItemsResultSet.close();

            String findItemiRatingSql=null;
            String findItemjRatingSql=null;
            Integer userId=null;
            int itemiRating=0;
            int itemjRating=0;
            double avgRating=0;//??????????????????
            double fenzi=0;
            double fenmu=0;
            double fenmup1=0;
            double fenmup2=0;
            double formu1a1=0;
            double formula2=0;
            double sim=0;//?????????
            int userIdInterceptSetSize= userIdInterceptSet.size();
            for(int k=0; k<userIdInterceptSetSize; k++){
                //?????????????????????????????????????????????itemi?????????
                //???????????????Itemj?????????
                userId=userIdInterceptSet.get(k);
                findItemiRatingSql="select *  from rating where itemid='"+itemi+
                        "' and usrid="+userId;
                ResultSet resultItemRatingSet=dbUtil.executeQuery(findItemiRatingSql);
                if(resultItemRatingSet.next()){
                    itemiRating=resultItemRatingSet.getInt("rating");
                }
                //   resultItemRatingSet.close();
                findItemjRatingSql="select * from rating where itemid='"+itemj+
                        "' and usrid="+userId;
                resultItemRatingSet=dbUtil.executeQuery(findItemjRatingSql);
                if(resultItemRatingSet.next()){
                    itemjRating=resultItemRatingSet.getInt("rating");
                }
//                    resultItemRatingSet.close();

                String findUserAvgeRating="select avgRating from avgrating where usrid='"+userId+"'";
                ResultSet  resultaAvgeRatingSet=dbUtil.executeQuery(findUserAvgeRating);
                if(resultaAvgeRatingSet.next()){
                    avgRating=resultaAvgeRatingSet.getDouble("avgRating");

                }
                //     resultaAvgeRatingSet.close();

                //????????????
                formu1a1=itemiRating-avgRating;
                formula2=itemjRating-avgRating;
                fenzi+=formu1a1*formula2;
                fenmup1+=Math.pow(formu1a1,2);
                fenmup2+=Math.pow(formula2,2);
            }
            fenmu=Math.sqrt(fenmup1)*Math.sqrt(fenmup2);
            if(fenmu!=0){
                sim=fenzi/fenmu;
            }
            //    System.out.println(sim);
//                //??????????????????????????????????????????
            String saveSimilaritySql="insert into sim (midi,midj,sim,timestamp) VALUES ('"+
                    itemi+"','"+itemj+"','"+ sim+"','"+System.currentTimeMillis()+"')";
            //  System.out.println(saveSimilaritySql);
            dbUtil.executeUpdate(saveSimilaritySql);

        }

    }
    dbUtil.close();
    Long end=System.currentTimeMillis();
    System.out.println((end-begin)/1000);



}
}
