package com.jjoony.assetmanagement.domain.Category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {

    @Schema(description = "타입 입력 수입:ture 지출:false", example = "false")
    @NotNull
    private boolean type;

    @Schema(description = "카테고리명 입력", example = "식비")
    @NotBlank
    private String name;

}
