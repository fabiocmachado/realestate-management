package com.realestate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PropertyPageDTO {
    private List<PropertyDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
