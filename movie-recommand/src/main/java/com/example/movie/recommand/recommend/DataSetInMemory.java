package com.example.movie.recommand.recommend;

import com.example.movie.recommand.recommend.model.RatingItem;

import java.io.*;
import java.util.*;

/**
 * @author dd
 * @Date 2022/7/28-22:44
 * @function
 */
public class DataSetInMemory {
    private   static long begin;
    private   static  long end;
    public static  void begin(){
        begin=System.currentTimeMillis()/1000;
    }
    public static void end(){
            end =System.currentTimeMillis()/1000;
    }
    public static  String getCosume(){
        Long internal=end-begin;
//        Long minute=0L;
//        Long hour=0L;
//        internal=internal%60;
//        hour=minute/60;
//         minute=(internal/60)%60;
//        return  String.format("花费(%d时%d分%d秒)",hour,minute,internal);

        return  String.format("花费(%d秒)",internal);



    }
    public  static  void main(String[]args) throws FileNotFoundException, UnsupportedEncodingException {
        begin();

        //获取用户评分数据
        List<RatingItem> ratingItems=getDataFromFile();
        Integer ratingItemSize=ratingItems.size();
        //获取评分的物体id值列表
        Set<Integer> itemSet=new HashSet<>();
        //获取用户-用户评分列表字典
        //测试通过
        Map<Integer,Map<Integer,RatingItem>> userRatingMapper=new HashMap<>();
        RatingItem ratingItem=null;
        Integer userId=null;
         Map<Integer,RatingItem> ratingItemsMap=null;
        for(int i=0;i<ratingItemSize;i++) {
            ratingItem = ratingItems.get(i);
            itemSet.add(ratingItem.getItemId());
            userId = ratingItem.getUserId();
            ratingItemsMap = userRatingMapper.get(userId);
            if (ratingItemsMap == null) {
                ratingItemsMap = new HashMap<>();
            }
            ratingItemsMap.put(ratingItem.getItemId(), ratingItem);
            userRatingMapper.put(userId, ratingItemsMap);
        }

    //    userRatingMapperOuput(userRatingMapper);



        //输入：等待计算的物品数据集合Set
        // 按照物品数据集合，构建物体评分矩阵
        //(ij)横向为i;纵向为j.similarity代表网格坐标
        // 网格中的数据格式为(userid:列表)
        Map<Similarity,Map<Integer,Map<Integer,RatingItem>>> itemMatrix= new HashMap<>();

        Object[]itemSetArray= itemSet.toArray();
        int itemSetSize=itemSet.size();
        Similarity similarity=null;
        for(int i=0;i<itemSetSize;i++){
            for(int j=i;j<itemSetSize;j++) {
                similarity = new Similarity();
                similarity.setItem1((int)itemSetArray[i]);
                similarity.setItem2((int)itemSetArray[j]);
                itemMatrix.put(similarity,null);



                //网格构建
                //寻找有共同评分的用户userId和用户的评分列表加入到矩阵网格i,j当中去。
                 //数据格式{userId:{itemId:rating}}
                for(Map.Entry<Integer,Map<Integer,RatingItem>> entry:userRatingMapper.entrySet()){
                    //取得 用户userId的{itemId:Rating}
                    Map<Integer,RatingItem> ratingItemMap=entry.getValue();
                    //获取用户id值
                    Integer z=entry.getKey();
                    //判断用户的{item：rating}是否包含对物品i和物品j的评分
                    //包含则取出user评分的字典，将当前用户加入字典当中去。
                    if(ratingItemMap.containsKey(similarity.getItem1())&&ratingItemMap.containsKey(similarity.getItem2())){
                        Map<Integer,Map<Integer,RatingItem>> ratingOfUser=itemMatrix.get(similarity);
                        if(ratingOfUser==null){
                            ratingOfUser=new HashMap<>();
                        }
                        ratingOfUser.put(z,ratingItemMap);
                        itemMatrix.put(similarity,ratingOfUser);

                    }


                }
                //计算矩阵 i 和 j 的 相似度。
                double ru=0;//用户的平均分
                double rui=0;//用户对i的评分
                double ruj=0;//用户对j的评分
                double fenzi=0;//分子部分
                double fomula1=0;//子公式1
                double fomula2=0;//子公式2；
                double fenmu1=0;//分母1;
                double  fenmu2=0;//分母2;
                double fenmu=0;//分母
                //求得网格当中的评分用户列表
                Map<Integer,Map<Integer,RatingItem>> usersList=itemMatrix.get(similarity);
                if(usersList!=null){
                    //数据格式{userId:{itemId,rating}
                    for(Map.Entry<Integer,Map<Integer,RatingItem>> entry2:usersList.entrySet()){
                        //物品id,评分列表

                        Map<Integer,RatingItem> ratingListOfUser=entry2.getValue();
                        //用户评分物品的数量
                        int ratingSize=0;
                       //求用户评分的平均分
                        int sum=0;
                        for(Map.Entry<Integer,RatingItem> ratingEntry:ratingListOfUser.entrySet()){
                            sum+=ratingEntry.getValue().getRating();
                            ratingSize++;
                        }
                        ru=sum/ratingSize;
                        if(entry2.getKey()==1){
                            System.out.println("("+entry2.getKey()+"):"+ru);

                        }
                        rui=ratingListOfUser.get(similarity.getItem1()).getRating();
                        ruj=ratingListOfUser.get(similarity.getItem2()).getRating();
                        fomula1=rui-ru;
                        fomula2=ruj-ru;
                        fenzi+=fomula1*fomula2;
                        fenmu1+=Math.pow(fomula1,2);
                        fenmu2+=Math.pow(fomula2,2);
                    }
                    fenmu=Math.sqrt(fenmu1)*Math.sqrt(fenmu2);

                    if(fenmu!=0){
                        similarity.setSimilarity(fenzi/fenmu);
                    }
                }
                else
                    similarity.setSimilarity(0);
           }
        }
        //System.out.println("矩结束");



        System.out.println("输出相似度信息");

        for(Map.Entry<Similarity,Map<Integer,Map<Integer,RatingItem>>> similarityMapEntry: itemMatrix.entrySet()){
            if(similarityMapEntry.getKey().getItem1()==2)
            System.out.println(similarityMapEntry.getKey().toString());
        }
        System.out.println("相识度信息输出结束");
        end();
        System.out.println(itemMatrix.size());
        System.out.println(getCosume());

//        Set<Integer> usersSet= userRatingMapper.keySet();
//        Object[] usersArray=usersSet.toArray();
//        Integer usersArraySize=usersSet.size();
//        double threhold=2.5;
//        Map<Integer,Map<Integer,RatingItem>>filteUserRatingMapper=new HashMap<>();
//        //对用户评分数据进行过滤，过滤掉分值小与threhold的评分项目
//        for(int i=0;i<usersArraySize;i++){
//            int m=(Integer)usersArray[i];
//            Map<Integer,RatingItem> ratingOFCurrent= userRatingMapper.get(m);
//            Map<Integer,RatingItem> filterResult=null;
//            for(Map.Entry<Integer,RatingItem> entry: ratingOFCurrent.entrySet() ){
//                if(entry.getValue().getRating()<threhold){
//                }
//                else {
//                    filterResult=filteUserRatingMapper.get(m);
//                    if(filterResult==null) {
//                        filterResult=new HashMap<>();
//
//                    }
//                    filterResult.put(entry.getKey(), entry.getValue());
//                    filteUserRatingMapper.put(m,filterResult);
//                }
//            }
//
//        }


    }
    public static List<RatingItem> getDataFromFile() throws FileNotFoundException, UnsupportedEncodingException {
        List<RatingItem> ratingData=new LinkedList<>();
        FileInputStream fout = new FileInputStream("ml-100k\\u.data");//("ml-100k/u.data");
        InputStreamReader reader = new InputStreamReader(fout, "utf8");
        BufferedReader in   =   new   BufferedReader(reader);
        String line=null;
        ForDataSet hello=new ForDataSet();

        try {
            while(	(line=in.readLine())!=null) {
             //   System.out.println("-------------");
           //     System.out.println("--------------");
               // System.out.println(line);
                String newline=line.replaceAll("[\t]", " ");
          //      System.out.println("--------");
                int i=0;
                String []ht=newline.split(" ");
                for(String k:ht) {
                    i++;
               //     System.out.println("index"+k);
                }
                Integer userId=Integer.parseInt(ht[0]);
                Integer movieId=Integer.parseInt(ht[1]);
                Integer rating=Integer.parseInt(ht[2]);
                RatingItem ratingItem=new RatingItem();
                ratingItem.setUserId(userId);
                ratingItem.setItemId(movieId);
                ratingItem.setRating(rating);
                ratingData.add(ratingItem);
                //     System.out.println(ratingItem);
             //   System.out.println("--------");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratingData;
    }
    public  static  void userRatingMapperOuput(Map<Integer,Map<Integer,RatingItem>>userRatingMapper){
        System.out.println("共有用户"+userRatingMapper.size());
        for(Map.Entry<Integer,Map<Integer,RatingItem>> entry:userRatingMapper.entrySet()){
            System.out.println("----");
            System.out.println("用户"+entry.getKey()+"共有："+entry.getValue().size()+"评分");
            System.out.println("评分：");
            for(Map.Entry<Integer,RatingItem> entry2:entry.getValue().entrySet()){
                System.out.println(entry2.getValue().getItemId()+":"+entry2.getValue().getRating());
            }
            System.out.println("----");
        }
    }
    public static  void  itemSetArrayOuput(  Set<Integer> itemSet) {
        Object[]itemSetArray= itemSet.toArray();
        System.out.println(itemSet.size());
        for(Object object:itemSetArray){
            System.out.println(object);
        }
    }
}
/**
 for(int i=0;i<itemSetSize;i++){
 for(int j=i;j<itemSetSize;j++) {
 similarityCalutor = new Similarity();
 similarityCalutor.setItem1((Integer)itemSetArray[i]);
 similarityCalutor.setItem1((Integer) itemSetArray[j]);
 itemMatrix.put(similarityCalutor,null);
 //寻找有共同评分的用户userId和用户的评分列表加入到矩阵网格i,j当中去。
 // 数据格式{userId:{itemId:rating}}
 for(Map.Entry<Integer,Map<Integer,RatingItem>> entry:userRatingMapper.entrySet()){
 //取得 用户userId的{itemId:Rating}
 Map<Integer,RatingItem> ratingItemMap=entry.getValue();
 //获取用户id值
 Integer z=entry.getKey();
 //判断用户的{item：rating}是否包含对物品i和物品j的评分
 //包含则取出user评分的字典，将当前用户加入字典当中去。
 if(ratingItemMap.containsKey(similarityCalutor.getItem1())&&ratingItemMap.containsKey(similarityCalutor.getItem2())){
 Map<Integer,Map<Integer,RatingItem>> ratingOfUser=itemMatrix.get(similarityCalutor);
 if(ratingOfUser==null){
 ratingOfUser=new HashMap<>();
 }
 ratingOfUser.put(z,ratingItemMap);
 itemMatrix.put(similarityCalutor,ratingOfUser);

 }


 }
 //计算矩阵 i 和 j 的 相似度。
 double ru=0;//用户的平均分
 double rui=0;//用户对i的评分
 double ruj=0;//用户对j的评分
 double fenzi=0;//分子部分
 double fomula1=0;//子公式1
 double fomula2=0;//子公式2；
 double fenmu1=0;//分母1;
 double  fenmu2=0;//分母2;
 double fenmu=0;//分母
 Map<Integer,Map<Integer,RatingItem>> usersList=itemMatrix.get(similarityCalutor);
 if(usersList!=null){
 //数据格式{userId:{itemId,rating}
 for(Map.Entry<Integer,Map<Integer,RatingItem>> entry2:usersList.entrySet()){

 Map<Integer,RatingItem> ratingListOfUser=entry2.getValue();
 int ratingSize=0;
 int sum=0;
 for(Map.Entry<Integer,RatingItem> ratingEntry:ratingListOfUser.entrySet()){
 sum+=ratingEntry.getValue().getRating();
 ratingSize++;
 }
 ru=sum/ratingItemSize;
 rui=ratingListOfUser.get(similarityCalutor.getItem1()).getRating();
 ruj=ratingListOfUser.get(similarityCalutor.getItem2()).getRating();
 fomula1=rui-ru;
 fomula2=ruj-ru;
 fenzi+=fomula1*fomula2;
 fenmu1+=Math.pow(fomula1,2);
 fenmu2+=Math.pow(fomula2,2);
 }
 fenmu=Math.sqrt(fenmu1)*Math.sqrt(fenmu2);
 if(fenmu!=0){
 similarityCalutor.setSimilarity(fenzi/fenmu);
 }
 }
 else
 similarityCalutor.setSimilarity(0);
 }
 }
 //System.out.println("矩结束");

 */