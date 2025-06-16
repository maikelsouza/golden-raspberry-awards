package com.outsera.goldenraspberryawards.api.config.files;

import com.outsera.goldenraspberryawards.domain.model.Movie;
import com.outsera.goldenraspberryawards.domain.model.Productor;
import com.outsera.goldenraspberryawards.domain.model.Studio;
import com.outsera.goldenraspberryawards.domain.service.MovieService;
import com.outsera.goldenraspberryawards.domain.service.ProductorService;
import com.outsera.goldenraspberryawards.domain.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CvsFile implements ApplicationRunner {

    private final MovieService movieService;

    private final StudioService studioService;

    private final ProductorService productorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var resource = new ClassPathResource("data/movielist.csv");

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            popularDomainTable(bufferedReader);
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            populeMovieTable(bufferedReader);
        }

    }

    private void populeMovieTable(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
        String strcurrentline;
        while ((strcurrentline = bufferedReader.readLine()) != null) {

            String[] parts = strcurrentline.split(";");
            String[] studiosParts = Arrays.stream(parts[2].split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
            Set<Studio> studios = studioService.findByNameIn(studiosParts);

            String[] productorParts = Arrays.stream(parts[3].split(",| and "))
                    .map(String::trim)
                    .toArray(String[]::new);
            Set<Productor> producers = productorService.findByNameIn(productorParts);

            Movie movie = Movie.builder()
                    .year(Integer.parseInt(parts[0]))
                    .title(parts[1])
                    .studios(studios)
                    .producers(producers)
                    .build();

            if (parts.length == 5){
                movie.setWinner("yes".equals(parts[4]) ? true : false);
            }
            movieService.save(movie);
        }
    }


    private void popularDomainTable(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
        String strcurrentline;
        Set<Studio> studios = new HashSet<>();
        Set<Productor> productors = new HashSet<>();
        while ((strcurrentline = bufferedReader.readLine()) != null) {

            String[] parts = strcurrentline.split(";");
            String[] studiosParts = parts[2].split(",");

            for (String studioPart: studiosParts){
                studios.add(Studio.builder()
                        .name(studioPart.trim())
                        .build());
            }

            String[] productorParts = parts[3].split(",");
            for (String productorPart: productorParts){
                String[] productorPartsAnd = productorPart.trim().split(" and ");
                for (String productorPartAnd:  productorPartsAnd){
                    productors.add(Productor.builder()
                            .name(productorPartAnd.trim())
                            .build());
                }
            }
        }
        productorService.saveAll(productors);
        studioService.saveAll(studios);
    }
}
