package com.coniverse.dangjang.domain.guide.weight.dto;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record WeightGuideResponse(@JsonIgnore String id, LocalDate createdAt, int weightDiff, Alert alert, String content) implements GuideResponse {

}
