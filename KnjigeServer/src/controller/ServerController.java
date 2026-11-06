/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Kategorija;
import domain.Knjiga;
import domain.Knjizar;
import domain.Kupac;
import domain.Porudzbina;
import java.util.ArrayList;
import so.kategorija.SOAddKategorija;
import so.kategorija.SODeleteKategorija;
import so.kategorija.SOGetAllKategorija;
import so.kategorija.SOUpdateKategorija;
import so.knjiga.SOAddKnjiga;
import so.knjiga.SODeleteKnjiga;
import so.knjiga.SOGetAllKnjiga;
import so.knjiga.SOUpdateKnjiga;
import so.kupac.SOAddKupac;
import so.kupac.SODeleteKupac;
import so.kupac.SOGetAllKupac;
import so.kupac.SOUpdateKupac;
import so.login.SOLogin;
import so.porudzbina.SOAddPorudzbina;
import so.porudzbina.SODeletePorudzbina;
import so.porudzbina.SOGetAllPorudzbina;
import so.porudzbina.SOUpdatePorudzbina;

/**
 *
 * @author Mila
 */
public class ServerController {

    private static ServerController instance;
    private ArrayList<Knjizar> ulogovaniKnjizari = new ArrayList<>();

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public ArrayList<Knjizar> getUlogovaniKnjizari() {
        return ulogovaniKnjizari;
    }

    public void setUlogovaniKnjizari(ArrayList<Knjizar> ulogovaniKnjizari) {
        this.ulogovaniKnjizari = ulogovaniKnjizari;
    }

    public Knjizar login(Knjizar knjizar) throws Exception {
        SOLogin so = new SOLogin();
        so.templateExecute(knjizar);
        return so.getUlogovani();
    }

    public void addKupac(Kupac kupac) throws Exception {
        (new SOAddKupac()).templateExecute(kupac);
    }

    public void addKategorija(Kategorija kategorija) throws Exception {
        (new SOAddKategorija()).templateExecute(kategorija);
    }

    public void addKnjiga(Knjiga knjiga) throws Exception {
        (new SOAddKnjiga()).templateExecute(knjiga);
    }

    public void addPorudzbina(Porudzbina porudzbina) throws Exception {
        (new SOAddPorudzbina()).templateExecute(porudzbina);
    }

    public void deleteKupac(Kupac kupac) throws Exception {
        (new SODeleteKupac()).templateExecute(kupac);
    }

    public void deleteKategorija(Kategorija kategorija) throws Exception {
        (new SODeleteKategorija()).templateExecute(kategorija);
    }

    public void deleteKnjiga(Knjiga knjiga) throws Exception {
        (new SODeleteKnjiga()).templateExecute(knjiga);
    }

    public void deletePorudzbina(Porudzbina porudzbina) throws Exception {
        (new SODeletePorudzbina()).templateExecute(porudzbina);
    }

    public void updateKupac(Kupac kupac) throws Exception {
        (new SOUpdateKupac()).templateExecute(kupac);
    }

    public void updateKategorija(Kategorija kategorija) throws Exception {
        (new SOUpdateKategorija()).templateExecute(kategorija);
    }

    public void updateKnjiga(Knjiga knjiga) throws Exception {
        (new SOUpdateKnjiga()).templateExecute(knjiga);
    }

    public void updatePorudzbina(Porudzbina porudzbina) throws Exception {
        (new SOUpdatePorudzbina()).templateExecute(porudzbina);
    }

    public ArrayList<Kupac> getAllKupac(Kupac kupac) throws Exception {
        SOGetAllKupac so = new SOGetAllKupac();
        so.templateExecute(kupac);
        return so.getLista();
    }

    public ArrayList<Porudzbina> getAllPorudzbina(Porudzbina porudzbina) throws Exception {
        SOGetAllPorudzbina so = new SOGetAllPorudzbina();
        so.templateExecute(porudzbina);
        return so.getLista();
    }

    public ArrayList<Knjiga> getAllKnjiga(Knjiga knjiga) throws Exception {
        SOGetAllKnjiga so = new SOGetAllKnjiga();
        so.templateExecute(knjiga);
        return so.getLista();
    }

    public ArrayList<Kategorija> getAllKategorija(Kategorija kategorija) throws Exception {
        SOGetAllKategorija so = new SOGetAllKategorija();
        so.templateExecute(kategorija);
        return so.getLista();
    }

    public void logout(Knjizar ulogovani) {
        ulogovaniKnjizari.remove(ulogovani);
    }

}
