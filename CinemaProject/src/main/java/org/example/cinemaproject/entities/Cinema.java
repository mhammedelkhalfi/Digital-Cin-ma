    package org.example.cinemaproject.entities;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;
    import java.util.Collection;

    @Entity
    @Table
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Cinema implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(length = 75)
        private String name;
        private double longitude,latitude,altitude;
        private int nombreSalle;

        @OneToMany(mappedBy = "cinema")
        private Collection<Salle> salles;

        @ManyToOne
        private Ville ville;
    }
