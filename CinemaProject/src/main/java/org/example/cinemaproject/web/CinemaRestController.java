package org.example.cinemaproject.web;

import lombok.Data;
import org.example.cinemaproject.dao.FilmRepository;
import org.example.cinemaproject.dao.TicketRepository;
import org.example.cinemaproject.entities.Film;
import org.example.cinemaproject.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        Film f = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film not found"));
        String photoName = f.getPhoto();
        File file = new File("C:/Users/mhamm/OneDrive/Bureau/Data/Cinema/CinemaProject/Images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
        List<Ticket> listTickets = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket -> {
            System.out.println(idTicket);
            ticketRepository.findById(idTicket).ifPresent(ticket -> {
                ticket.setNomClient(ticketForm.getNomClient());
                ticket.setReserve(true);
                ticket.setCodePayement(ticketForm.getCodePayment());
                ticketRepository.save(ticket);
                listTickets.add(ticket);
            });
        });
        return listTickets;
    }

}

    @Data
class TicketForm{
    private List<Long> tickets = new ArrayList<>();
    private  String nomClient;
    private int codePayment;
}
