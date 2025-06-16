package com.outsera.goldenraspberryawards.domain.service;

import com.outsera.goldenraspberryawards.api.dto.AwardIntervalDto;
import com.outsera.goldenraspberryawards.api.dto.ProducerAndYearDto;
import com.outsera.goldenraspberryawards.api.dto.ProductorDto;
import com.outsera.goldenraspberryawards.domain.model.Productor;
import com.outsera.goldenraspberryawards.domain.repository.ProductorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductorService {

    private final ProductorRepository repository;

    public void saveAll(Set<Productor> productors){
        repository.saveAll(productors);
    }

    public Set<Productor> findByNameIn(String[] names){
        return repository.findByNameIn(names);
    }

    public AwardIntervalDto findAwardInterval() {
        List<ProductorDto> productorDtos = new ArrayList<>();

        List<Long> ids = repository.findIdProducerWithWinningMovies();
        List<ProducerAndYearDto> producerAndYearDtos = repository.findProducersAndYearsWithWinningMoviesByIDs(ids);

        Map<String, List<ProducerAndYearDto>> producerAndYearDtosGroupByName = producerAndYearDtos.stream()
                .collect(Collectors.groupingBy(ProducerAndYearDto::getName));

        for (String key : producerAndYearDtosGroupByName.keySet()) {
            var groupTwo = groupForTwo(producerAndYearDtosGroupByName.get(key));
            for (List<ProducerAndYearDto> producerAndYearDtoList : groupTwo) {
                productorDtos.add(ProductorDto
                        .builder()
                        .producer(producerAndYearDtoList.get(0).getName())
                        .previousWin(producerAndYearDtoList.get(0).getYear())
                        .followingWin(producerAndYearDtoList.get(1).getYear())
                        .build());
            }
        }

        return AwardIntervalDto.builder()
                .min(menorIntervalo(productorDtos))
                .max(largerInterval(productorDtos))
                .build();
    }

    private List<ProductorDto> menorIntervalo(List<ProductorDto> productorDtos) {
        int minorInterval = productorDtos.stream()
                            .mapToInt(ProductorDto::getInterval)
                            .min()
                            .getAsInt();

        return productorDtos.stream()
                                .filter(p -> p.getInterval() == minorInterval)
                                .toList();
    }

    private List<ProductorDto> largerInterval(List<ProductorDto> productorDtos) {

        int largerInterval = productorDtos.stream()
                .mapToInt(ProductorDto::getInterval)
                .max()
                .getAsInt();

        return productorDtos.stream()
                .filter(p -> p.getInterval() == largerInterval)
                .toList();
    }

    private  List<List<ProducerAndYearDto>> groupForTwo(List<ProducerAndYearDto> list) {
        List<List<ProducerAndYearDto>> result = new ArrayList<>();

        for (int i = 0; i + 1 < list.size(); i += 2) {
            result.add(List.of(list.get(i), list.get(i + 1)));
        }

        if (list.size() % 2 == 1) {
            result.add(List.of(list.get(list.size() - 2),
                    list.get(list.size() - 1)));
        }
        return result;
    }
}
