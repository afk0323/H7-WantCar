package softeer.wantcar.cartalog.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto.*;

@Service
@RequiredArgsConstructor
public class TrimOptionServiceImpl implements TrimOptionService {
    private final TrimOptionQueryRepository trimOptionQueryRepository;

    @Override
    public TrimOptionListResponseDto getTrimOptionList(Long detailTrimId, Long interiorColorId) {
        List<String> multipleSelectableCategories = trimOptionQueryRepository.findMultipleSelectableCategories();
        List<TrimOptionQueryRepository.TrimOptionInfo> options = trimOptionQueryRepository.findOptionsByDetailTrimId(detailTrimId);
        List<TrimOptionQueryRepository.TrimOptionInfo> packages = trimOptionQueryRepository.findPackagesByDetailTrimId(detailTrimId);
        if(Objects.isNull(options) || Objects.isNull(packages)) {
            return null;
        }

        Map<Boolean, List<TrimOptionQueryRepository.TrimOptionInfo>> collectedOptions = options.stream()
                .filter(option -> filterColorCondition(interiorColorId, option))
                .collect(Collectors.groupingBy(TrimOptionQueryRepository.TrimOptionInfo::isBasic));

        return builder()
                .multipleSelectParentCategory(multipleSelectableCategories)
                .defaultOptions(getDefaultOptions(collectedOptions))
                .selectOptions(getSelectableOptions(packages, collectedOptions))
                .build();
    }

    private boolean filterColorCondition(Long interiorColorId, TrimOptionQueryRepository.TrimOptionInfo option) {
        return !option.isColorCondition() || option.getTrimInteriorColorIds().contains(interiorColorId);
    }

    private List<TrimOptionDto> getDefaultOptions(Map<Boolean, List<TrimOptionQueryRepository.TrimOptionInfo>> collectedOptions) {
        return getOptions(collectedOptions.getOrDefault(true, new ArrayList<>()));
    }

    private List<TrimOptionDto> getSelectableOptions(List<TrimOptionQueryRepository.TrimOptionInfo> packages,
                                                     Map<Boolean, List<TrimOptionQueryRepository.TrimOptionInfo>> collectedOptions) {
        List<TrimOptionDto> selectableOptions = getOptions(collectedOptions.getOrDefault(false, new ArrayList<>()));
        List<TrimOptionDto> selectablePackages = getOptions(packages);
        selectablePackages.addAll(selectableOptions);
        return selectablePackages;
    }

    private List<TrimOptionDto> getOptions(List<TrimOptionQueryRepository.TrimOptionInfo> collectedOptions) {
        return collectedOptions.stream()
                .map(option -> new TrimOptionDto(option, 0))
                .collect(Collectors.toList());
    }
}
