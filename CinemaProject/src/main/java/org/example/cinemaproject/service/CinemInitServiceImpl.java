package org.example.cinemaproject.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


import org.example.cinemaproject.dao.*;
import org.example.cinemaproject.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CinemInitServiceImpl implements IcinemaInitService {

	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository	salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private CategorieRepository categorieRepository;

 	@Override
	public void initVilles() {
		Stream.of("El Jadida","Casablanca","Rabat").forEach(nameVille->{
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
	}

	@Override
	public void initCinema() { // revoire pour corrige ca si ilya erreur ou une chose que jai oublie
		villeRepository.findAll().forEach(v->{
			Stream.of("Megarama","Imax","Daouliz")
					.forEach(nameCinema->{
						Cinema cinema = new Cinema();
						cinema.setName(nameCinema);
						cinema.setNombreSalle(3+(int)(Math.random()*7));
						cinema.setVille(v);
						cinemaRepository.save(cinema);
			});
		});
		
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
			for (int i=0;i<cinema.getNombreSalle();i++){
				Salle salle = new Salle();
				salle.setName("salle "+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15+(int)(Math.random()*20));
				salleRepository.save(salle);
			}
		});
		
	}

	//dans chaque salle on ajoute des places
	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle -> {
			for (int i=0;i<salle.getNombrePlace();i++){
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle); // cette place pour quelle salle
				placeRepository.save(place);

			}
				});
		
	}

	@Override
	public void initSeances() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("10:00","13:00","16:00","19:00")
				.forEach(s -> {
					Seance seance = new Seance();
                    try {
                        seance.setHeurDebut(dateFormat.parse(s));
						seanceRepository.save(seance);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
		
	}
	@Override
	public void initCategories() {
		Stream.of("Histoire","Actions","Drama","Romance","Ficton")
				.forEach(cat->{
					Categorie categorie = new Categorie();
					categorie.setName(cat);
					categorieRepository.save(categorie);
				});

		
	}

	@Override
	public void initFilm() {
		double[] durees= new double[]{1,1.5,2,2.5,3,3.5};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("batman","Iron Man","Game of thrones","vikings","riverdale","la casa de papel")
				.forEach(TitreFilm->{
					Film film = new Film();
					film.setTitre(TitreFilm);
					film.setDuree(durees[new Random().nextInt(durees.length)]);
					// on a fait replace pour suprimer espace dans les titre pour chemin de l'image
					film.setPhoto(TitreFilm.replaceAll(" ","")+".jpg");
					film.setCategorie(categories.get(new Random().nextInt(categories.size())));
					filmRepository.save(film);


				});
		
	}

	@Override
	public void initProjections() {
		double[] prices= new double[]{50,80,100,150};
		List<Film> films = filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema -> {
				cinema.getSalles().forEach(salle -> {
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
						seanceRepository.findAll().forEach(seance -> {
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);

						});

				});
			});
		});


	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection -> {
			projection.getSalle().getPlaces().forEach(place -> {
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});

	}

}
