package com.abnamro.tvshowmanager.genre;

import com.abnamro.tvshowmanager.tvshow.TVShow;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<TVShow> tvShows;


    public Genre(String name) {
        this.name = name;
    }
}
