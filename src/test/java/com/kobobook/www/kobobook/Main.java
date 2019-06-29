//package com.kobobook.www.kobobook;
//
//import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
//import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
//import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
//import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
//import org.apache.mahout.cf.taste.model.DataModel;
//import org.apache.mahout.cf.taste.model.JDBCDataModel;
//import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
//import org.apache.mahout.cf.taste.recommender.RecommendedItem;
//import org.apache.mahout.cf.taste.recommender.Recommender;
//import org.apache.mahout.cf.taste.similarity.UserSimilarity;
//
//import java.io.File;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) throws Exception{
//        DataModel model = new FileDataModel(new File("E:\\workspace\\IDEA_workspace\\kobobook\\src\\test\\java\\com\\kobobook\\www\\kobobook\\intro.csv"));
//
//        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//
//        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//
//        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
//
//        List<RecommendedItem> recommendations = recommender.recommend(1, 2);
//
//        for(RecommendedItem recommendation : recommendations) {
//            System.out.println(recommendation);
//        }
//
//    }
//
//}
