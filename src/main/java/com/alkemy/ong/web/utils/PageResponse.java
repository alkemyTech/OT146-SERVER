package com.alkemy.ong.web.utils;

import com.alkemy.ong.web.controller.TestimonialController;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PageResponse{
    private List<TestimonialController.TestimonialDTO> content;
    private String nextPage;
    private String previousPage;
}
