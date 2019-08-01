package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Review;
import com.kobobook.www.kobobook.dto.ReviewDTO;
import com.kobobook.www.kobobook.exception.UnauthorizedException;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewApiController {

    private ReviewService reviewService;

    private JwtService jwtService;

    /*
    * 리뷰 등록 후 리뷰 목록 리턴
    * */
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.createReview((Integer) jwtService.getString("userId"), review), HttpStatus.OK);
    }

    /*
    * 해당 리뷰 삭제 후 리뷰 목록 리턴
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(reviewService.deleteReview((Integer) jwtService.getString("userId"), id), HttpStatus.OK);
        } catch (UnauthorizedException ue) {
            ue.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /*
    * 리뷰 수정 후 리뷰 목록 리턴
    * */
    @PutMapping("/{id}")
    public ResponseEntity<List<ReviewDTO>> updateReview(@PathVariable("id") Review review, @RequestParam("content") String content) {
        try {
            return new ResponseEntity<>(reviewService.updateReview((Integer) jwtService.getString("userId"), review, content), HttpStatus.OK);
        } catch (UnauthorizedException ue) {
            ue.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

}
