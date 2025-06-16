package com.outsera.goldenraspberryawards.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "\"year\"", nullable = false)
    private Integer year;

    @ManyToMany()
    @JoinTable(
            name = "Movie_Studio",
            joinColumns = @JoinColumn(name = "Movie_id"),
            inverseJoinColumns = @JoinColumn(name = "Studio_id")
    )
    @JsonManagedReference
    private Set<Studio> studios;

    @ManyToMany()
    @JoinTable(
            name = "Movie_Productor",
            joinColumns = @JoinColumn(name = "Movie_id"),
            inverseJoinColumns = @JoinColumn(name = "Productor_id")
    )
    @JsonManagedReference
    private Set<Productor> producers;

    @Column(nullable = true)
    private boolean winner;


}
