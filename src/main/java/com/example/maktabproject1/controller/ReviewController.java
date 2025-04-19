package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ReviewDto;
import com.example.maktabproject1.dto.ResponseDto;
import com.example.maktabproject1.exception.ReviewNotFoundException;
import com.example.maktabproject1.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseDto<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        try {
            ReviewDto createdReview = reviewService.createReview(reviewDto);
            return new ResponseDto<>(true, createdReview, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error creating review: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseDto<ReviewDto> getReviewById(@PathVariable Long id) {
        try {
            ReviewDto reviewDto = reviewService.getReviewById(id);
            return new ResponseDto<>(true, reviewDto, null);
        } catch (ReviewNotFoundException e) {
            return new ResponseDto<>(false, null, "Review not found with id: " + id);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching review: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseDto<List<ReviewDto>> getAllReviews() {
        try {
            List<ReviewDto> reviewDtos = reviewService.getAllReviews();
            return new ResponseDto<>(true, reviewDtos, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching reviews: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseDto<ReviewDto> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        try {
            ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);
            return new ResponseDto<>(true, updatedReview, null);
        } catch (ReviewNotFoundException e) {
            return new ResponseDto<>(false, null, "Review not found with id: " + id);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error updating review: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseDto<>(true, null, null);
        } catch (ReviewNotFoundException e) {
            return new ResponseDto<>(false, null, "Review not found with id: " + id);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error deleting review: " + e.getMessage());
        }
    }
}

