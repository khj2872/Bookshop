//package com.kobobook.www.kobobook;
//
//import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
//import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
//import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
//import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
//import org.apache.mahout.cf.taste.model.DataModel;
//import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
//import org.apache.mahout.cf.taste.recommender.RecommendedItem;
//import org.apache.mahout.cf.taste.recommender.Recommender;
//import org.apache.mahout.cf.taste.similarity.UserSimilarity;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.util.List;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
//public class Testee {
////    @Test
////    public void test() throws Exception {
//    public static void main(String[] args) throws Exception{
//        DataModel dataModel = new FileDataModel(new File("E:\\workspace\\IDEA_workspace\\kobobook\\src\\test\\java\\com\\kobobook\\www\\kobobook\\intro.csv"));
//
//        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
//
//        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
//
//        Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
//
//        List<RecommendedItem> recommendations = recommender.recommend(1, 1);
//
//        for (RecommendedItem recommendedItem : recommendations) {
//            System.out.println(recommendedItem);
//        }
//    }
//}
