package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ReviewDto;
import com.example.maktabproject1.entity.OrderEntity;
import com.example.maktabproject1.entity.ReviewEntity;
import com.example.maktabproject1.repository.OrderRepository;
import com.example.maktabproject1.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderRepository orderRepository){
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto){
        ReviewEntity reviewEntity = new ReviewEntity();
        OrderEntity orderEntity = orderRepository.findById(reviewDto.getOrderId()).orElseThrow();
        reviewEntity.setOrder(orderEntity);
        reviewEntity.setRating(reviewDto.getRating());
        reviewEntity.setComment(reviewDto.getComment());

        ReviewEntity savedReview = reviewRepository.save(reviewEntity);

        ReviewDto savedReviewDto = new ReviewDto();
        savedReviewDto.setId(savedReview.getId());
        savedReviewDto.setOrderId(savedReview.getOrder().getId());
        savedReviewDto.setRating(savedReview.getRating());
        savedReviewDto.setComment(savedReview.getComment());

        return savedReviewDto;
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElseThrow();
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setOrderId(reviewEntity.getOrder().getId());
        reviewDto.setRating(reviewEntity.getRating());
        reviewDto.setComment(reviewEntity.getComment());
        return reviewDto;
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::mapReviewEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElseThrow();
        OrderEntity orderEntity = orderRepository.findById(reviewDto.getOrderId()).orElseThrow();
        reviewEntity.setOrder(orderEntity);
        reviewEntity.setRating(reviewDto.getRating());
        reviewEntity.setComment(reviewDto.getComment());
        ReviewEntity updatedReview = reviewRepository.save(reviewEntity);
        return mapReviewEntityToDto(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDto mapReviewEntityToDto(ReviewEntity reviewEntity) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setOrderId(reviewEntity.getOrder().getId());
        reviewDto.setRating(reviewEntity.getRating());
        reviewDto.setComment(reviewEntity.getComment());
        return reviewDto;
    }
}