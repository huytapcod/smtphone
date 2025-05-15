package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.dto.request.CreateReviewRequest;
import com.hqshop.ecommerce.multivendor1.entity.Product;
import com.hqshop.ecommerce.multivendor1.entity.Review;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.internal.http2.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());

        product.getReviews().add(review);

        return reviewRepository.save(review);

    }
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
    public void deleteReview(Long reviewId, Long userId) {

        Review review = getReviewById(reviewId);
        if (review.getUser().getId().equals(userId)) {
            throw new AppException(Errorcode.REVIEW_CANT_DELETE);
        }
        reviewRepository.delete(review);

    }
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(()->
                new AppException(Errorcode.REVIEW_NOT_FOUND));
    }
}
