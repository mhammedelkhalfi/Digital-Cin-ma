package org.example.cinemaproject.entities;

import org.springframework.data.rest.core.config.Projection;

//http://localhost:8080/projections/1/tickets?projection=ticketProj
@Projection(name="ticketProj",types = Ticket.class)
public interface TicketsProjection {
    public Long getId();
    public  String getNomClient();
    public double getPrix();
    public Integer getCodePayement();
    public boolean getReserve();
    public Place getPlace();
}
