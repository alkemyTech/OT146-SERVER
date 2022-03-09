package com.alkemy.ong.web.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class PageResponse<T extends Object>{
    private List<T> content;
    @ApiModelProperty(example = "/resource?page=2")
    private String nextPage;
    @ApiModelProperty(example = "/resource?page=0")
    private String previousPage;

    public PageResponse(List<T> content, String url, int page, int size){
        this.content = content;
        this.nextPage = (content.size() < size)? "" : url+"?page=" + (page +1);
        this.previousPage = ((page > 0)? (url+"?page=" + (page -1)) : "");
    }

}
