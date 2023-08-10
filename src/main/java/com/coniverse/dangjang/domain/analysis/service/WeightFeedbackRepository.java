package com.coniverse.dangjang.domain.analysis.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coniverse.dangjang.domain.analysis.document.WeightFeedback;

public interface WeightFeedbackRepository extends MongoRepository<WeightFeedback, String> {

}
