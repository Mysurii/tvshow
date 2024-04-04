package com.abnamro.tvshowmanager.tvshow;

import com.abnamro.tvshowmanager.genre.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tv_show")
@Data
@NoArgsConstructor
public class TVShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull(message = "Genre is required")
    private Genre genre;

    public TVShow(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }
}
