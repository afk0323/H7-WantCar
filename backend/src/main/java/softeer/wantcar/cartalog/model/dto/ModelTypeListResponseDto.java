package softeer.wantcar.cartalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class ModelTypeListResponseDto {
    private List<ModelTypeDto> modelTypes;
}
