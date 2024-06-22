package org.example.cinemaproject.entities;

import java.util.Collection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 75)
    private String name;
    private double longitude,latitude,altitude;
    @OneToMany(mappedBy = "ville")
    private Collection<Cinema> cinemas;
	/*public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
    
}