package com.outsera.goldenraspberryawards.domain.service;

import com.outsera.goldenraspberryawards.domain.model.Studio;
import com.outsera.goldenraspberryawards.domain.repository.StudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class StudioService {

    private final StudioRepository repository;

    public void saveAll(Set<Studio> studios){
        repository.saveAll(studios);
    }

    public Set<Studio> findByNameIn(String[] names){
        return repository.findByNameIn(names);
    }
}
