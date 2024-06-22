package org.example.cinemaproject;

import org.example.cinemaproject.entities.Film;
import org.example.cinemaproject.entities.Salle;
import org.example.cinemaproject.entities.Ticket;
import org.example.cinemaproject.service.IcinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaProjectApplication implements CommandLineRunner {
	@Autowired
	private IcinemaInitService cinemaInitService;

	@Autowired
	private RepositoryRestConfiguration restConfiguration;

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
		cinemaInitService.initVilles();
		cinemaInitService.initCinema();
		cinemaInitService.initSalles();
		cinemaInitService.initPlaces();
		cinemaInitService.initSeances();
		cinemaInitService.initCategories();
		cinemaInitService.initFilm();
		cinemaInitService.initProjections();
		cinemaInitService.initTickets();

	}

	public static void main(String[] args){

		SpringApplication.run(CinemaProjectApplication.class, args);
	}

}
