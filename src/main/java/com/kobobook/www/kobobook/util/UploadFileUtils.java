//package com.kobobook.www.kobobook.util;
//
//import org.imgscalr.Scalr;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.FileCopyUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.text.DecimalFormat;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//public class UploadFileUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
//
//    public static Map<String, Object> uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
//        Map<String, Object> returnMap = new HashMap<>();
//
//        //겹쳐지지 않는 파일명을 위한 유니크한 값 생성
//        UUID uid = UUID.randomUUID();
//
//        //원본파일 이름과 UUID 결합
//        String savedName = uid.toString() + "_" + originalName;
//        returnMap.put("storedFileName", savedName);
//
//        //파일을 저장할 폴더 생성(년 월 일 기준)
//        String savedPath = calcPath(uploadPath);
//        returnMap.put("path", savedPath);
//
//        //저장할 파일준비
//        File target = new File(uploadPath + savedPath, savedName);
//
//        //파일을 저장
//        FileCopyUtils.copy(fileData, target);
//
//        String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);
//
//        String uploadedFileName = null;
//
//        //파일의 확장자에 따라 썸네일(이미지일경우) 또는 아이콘을 생성함.
//        if (MediaUtils.getMediaType(formatName) != null) {
//            uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
//            returnMap.put("uploadedFileName", uploadedFileName);
//        } else {
//            uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
//            returnMap.put("uploadedFileName", uploadedFileName);
//        }
//
//        //uploadedFileName는 썸네일명으로 화면에 전달된다.
//        return returnMap;
//    }//
//
//    //폴더 생성 함수
//    @SuppressWarnings("unused")
//    private static String calcPath(String uploadPath) {
//
//        Calendar cal = Calendar.getInstance();
//
//        String yearPath = File.separator + cal.get(Calendar.YEAR);
//
//        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
//
//        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
//
//        makeDir(uploadPath, yearPath, monthPath, datePath);
//
//        logger.info(datePath);
//
//        return datePath;
//    }//calcPath
//
//    //폴더 생성 함수
//    private static void makeDir(String uploadPath, String... paths) {
//
//        if (new File(uploadPath + paths[paths.length - 1]).exists()) {
//            return;
//        }//if
//
//        for (String path : paths) {
//
//            File dirPath = new File(uploadPath + path);
//
//            if (!dirPath.exists()) {
//                dirPath.mkdir();
//            }//if
//
//        }//for
//
//    }//makeDir
//
//    //음??? 아이콘? 이미지 파일이 아닌경우  썸네일을 대신?
//    private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
//        String iconName = uploadPath + path + File.separator + fileName;
//        return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
//    }
//
//    //썸네일 이미지 생성
//    private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
//
//        BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
//
//        BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
//
//        String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
//
//        File newFile = new File(thumbnailName);
//        String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//        ImageIO.write(destImg, formatName.toUpperCase(), newFile);
//
//        return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
//    }
//}