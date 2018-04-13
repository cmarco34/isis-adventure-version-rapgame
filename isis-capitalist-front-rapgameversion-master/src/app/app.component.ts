import { Component, Input, OnInit } from '@angular/core';

import { RestserviceService } from './restservice.service';
import { World, Pallier, Product } from './world';
import { ProductComponent } from './product/product.component';
import { ToasterService, ToasterModule } from 'angular2-toaster';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ToasterModule]
})

export class AppComponent implements OnInit  {
  toasterService: ToasterService;
  title = 'ISIS RAP GAME';
  world: World = new World();
  server: string;
  qtmulti: string ="1";
  manager: Pallier;
  canBuyMan: boolean =false;
  manDisp: Array<number> = [0,0,0,0,0,0];
  username: string;
  

  @Input() productComponentInstance: ProductComponent;

  constructor(private service: RestserviceService, toasterService: ToasterService) {
    this.server = service.getServer();
    this.toasterService = toasterService;
    service.getWorld().then(
      world => {
        this.world = world;
      });
  }


  ngOnInit() {
    setInterval(() => { this.verifManager(); }, 100);
  }

  onProductionDone(p : Product) {
    this.world.money=this.world.money+p.revenu*p.quantite;
    this.world.score=this.world.score+p.revenu*p.quantite;
    console.log(this.world.score);
    console.log(this.world.money);
  }

  onBuyDone(c : number) {
    this.world.money=(this.world.money-c);
  }

  changeMulti(qtmulti : string) {
    switch(this.qtmulti) { 
      case "1": {
        this.qtmulti="10";
        break;
      }
      case "10": { 
        this.qtmulti="100"; 
        break; 
      } 
      case "100": { 
        this.qtmulti="Max"; 
        break;
      }
      case "Max": { 
        this.qtmulti="1"; 
        break;
      }
    }
    return this.qtmulti;
  }

  hireManager(m:Pallier) {
    if (this.world.money>=m.seuil && m.unlocked==false) {
      this.world.money=this.world.money-m.seuil;
      m.unlocked=true;
      this.canBuyMan=false;
      this.world.products.product.forEach(p => {
        if(p.id == m.idcible) {
          p.managerUnlocked=true;
          this.toasterService.pop('succes', "Bien joué mamène", "Le manager "+m.name+" rejoint ton crew pour soutenir " +p.name);
        }
      })
    }
    else {
      if(m.unlocked==true) {
        this.toasterService.pop('error', "Tu l'as déjà mamène", m.name+" fait déjà partie de ton crew");
      }
      else {
        this.toasterService.pop('error', "Tu manques de thunes mamène", "Va falloir bibi plus que ça pour se payer ce genre de bails");
      }     
    }

  }

  verifManager() {
    this.world.managers.pallier.forEach(m => {
      console.log(this.manDisp);
      if(this.world.money>=m.seuil && m.unlocked==false && this.canBuyMan==false) {
        this.canBuyMan=true;
        this.manDisp[m.idcible-1]=1;
      }
      }
             
      
    )}
  }
  
