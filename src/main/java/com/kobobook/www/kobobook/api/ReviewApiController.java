package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Review;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reviews")
public class ReviewApiController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtService jwtService;

    /*
    * 리뷰 등록 후 리뷰 목록 리턴
    * */
    @PostMapping("")
    public ResponseEntity<List<Review>> createReview(@RequestParam("itemId") Integer itemId,
                                         @RequestParam("content") String content,
                                         @RequestParam("rating") Float rating) {
        return new ResponseEntity<>(reviewService.createReview((Integer) jwtService.get("member").get("id"), itemId, content, rating), HttpStatus.OK);
    }

    /*
    * 해당 리뷰 삭제 후 리뷰 목록 리턴
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Review>> deleteReview(@PathVariable("id") Integer reviewId) {
        return new ResponseEntity<>(reviewService.deleteReview(reviewId), HttpStatus.OK);
    }

    /*
    * 리뷰 수정 후 리뷰 목록 리턴
    * */
    @PutMapping("/{id}")
    public ResponseEntity<List<Review>> updateReview(@PathVariable("id") Integer reviewId, @RequestParam("content") String content) {
        return new ResponseEntity<>(reviewService.updateReview(reviewId, content), HttpStatus.OK);
    }

    /*
    * 권한 체크
    * */
    @GetMapping("/{id}/auth")
    public ResponseEntity<Boolean> authCheck(@PathVariable("id") Integer reviewId) {
        Boolean auth = reviewService.authCheck((Integer) jwtService.get("member").get("id"), reviewId);
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }
}
