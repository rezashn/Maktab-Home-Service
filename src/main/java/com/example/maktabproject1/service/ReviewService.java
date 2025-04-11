package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(ReviewDto reviewDto);

    ReviewDto getReviewById(Long id);

    List<ReviewDto> getAllReviews();

    ReviewDto updateReview(Long id, ReviewDto reviewDto);

    void deleteReview(Long id);
}
