import {Component, Input, OnInit} from '@angular/core';
import {CinemaService} from "../services/cinema.service";
import {tick} from "@angular/core/testing";

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.css']
})


export class CinemaComponent implements OnInit {

  public villes;
  public cinemas;
  public salles;

  public currentVille;
  public currentCinema;
  public currentProjection;
  public selectedTickets: any;

  constructor(public cinemaService:CinemaService) { }


  ngOnInit(){
    this.cinemaService.getVilles()
    .subscribe(data=>{
        this.villes=data;
      },err=>{
        console.log(err);
      })
  }

  onGetCinemas(v) {
    this.currentVille=v;
    this.salles=undefined;
    this.cinemaService.getCinemas(v)
      .subscribe(data=>{
        this.cinemas=data;
      },err=>{
        console.log(err);
      })
  }

  onGetSalles(c) {
    this.currentCinema=c;
    this.cinemaService.getSalles(c)
      .subscribe(data=>{
        this.salles=data;
        this.salles._embedded.salles.forEach(salle=>{
          this.cinemaService.getProjections(salle)
            .subscribe(data=>{
              salle.projections=data;
            },err=>{
              console.log(err);
            })
        })
      },err=>{
        console.log(err);
      })

  }

  onGetTicketsPlaces(p) {
    this.currentProjection=p;
    this.cinemaService.getTicketsPlace(p).subscribe(data=>{
      this.currentProjection.tickets=data;
      this.selectedTickets=[];
    },err=>{
      console.log(err);
    })

  }

  protected readonly tick = tick;

  onSelectTicket(t) {
    if(!t.selected){
      t.selected=true;
      this.selectedTickets.push(t);
    }else{
      t.selected=false;
      this.selectedTickets.splice(this.selectedTickets.indexOf(t),1);
    }
    console.log(this.selectedTickets);
  }


  getTicketClass(t: any) {
    let str="btn ticket";
    if(t.resreve==true){
      str+=" btn-danger";
    }else if(t.selected){
      str+=" btn-warning";
    }else {
      str+=" btn-success";
    }
    return str;
  }

  onPayTickets(dataForm) {
    //console.log(form);
    let tickets: number[] = [];
    //console.log(tickets);
    this.selectedTickets.forEach(t=>{
      tickets.push(t.id);
    });
    dataForm.tickets=tickets;
    console.log(dataForm);
    this.cinemaService.payerTickets(dataForm)
      .subscribe(data=>{
        alert("Tickets Réserves Avec Succés !")
        this.onGetTicketsPlaces(this.currentProjection);
      },err=>{
        console.log(err);
      })
   }
}
