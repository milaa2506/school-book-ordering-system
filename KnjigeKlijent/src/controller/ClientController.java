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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import session.Session;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author Mila
 */
public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public Knjizar login(Knjizar knjizar) throws Exception {
        return (Knjizar) sendRequest(Operation.LOGIN, knjizar);
    }

    public void logout(Knjizar ulogovani) throws Exception {
        sendRequest(Operation.LOGOUT, ulogovani);
    }

    public void addKupac(Kupac kupac) throws Exception {
        sendRequest(Operation.ADD_KUPAC, kupac);
    }

    public void addKategorija(Kategorija kategorija) throws Exception {
        sendRequest(Operation.ADD_KATEGORIJA, kategorija);
    }

    public void addKnjiga(Knjiga knjiga) throws Exception {
        sendRequest(Operation.ADD_KNJIGA, knjiga);
    }

    public void addPorudzbina(Porudzbina porudzbina) throws Exception {
        sendRequest(Operation.ADD_PORUDZBINA, porudzbina);
    }

    public void deleteKupac(Kupac kupac) throws Exception {
        sendRequest(Operation.DELETE_KUPAC, kupac);
    }

    public void deleteKnjiga(Knjiga knjiga) throws Exception {
        sendRequest(Operation.DELETE_KNJIGA, knjiga);
    }

    public void deleteKategorija(Kategorija kategorija) throws Exception {
        sendRequest(Operation.DELETE_KATEGORIJA, kategorija);
    }

    public void deletePorudzbina(Porudzbina porudzbina) throws Exception {
        sendRequest(Operation.DELETE_PORUDZBINA, porudzbina);
    }

    public void updateKupac(Kupac kupac) throws Exception {
        sendRequest(Operation.UPDATE_KUPAC, kupac);
    }

    public void updateKnjiga(Knjiga knjiga) throws Exception {
        sendRequest(Operation.UPDATE_KNJIGA, knjiga);
    }

    public void updateKategorija(Kategorija kategorija) throws Exception {
        sendRequest(Operation.UPDATE_KATEGORIJA, kategorija);
    }

    public void updatePorudzbina(Porudzbina porudzbina) throws Exception {
        sendRequest(Operation.UPDATE_PORUDZBINA, porudzbina);
    }

    public ArrayList<Kupac> getAllKupac(Kupac kupac) throws Exception {
        return (ArrayList<Kupac>) sendRequest(Operation.GET_ALL_KUPAC, kupac);
    }

    public ArrayList<Porudzbina> getAllPorudzbina(Porudzbina porudzbina) throws Exception {
        return (ArrayList<Porudzbina>) sendRequest(Operation.GET_ALL_PORUDZBINA, porudzbina);
    }

    public ArrayList<Knjiga> getAllKnjiga(Knjiga knjiga) throws Exception {
        return (ArrayList<Knjiga>) sendRequest(Operation.GET_ALL_KNJIGA, knjiga);
    }

    public ArrayList<Kategorija> getAllKategorija(Kategorija kategorija) throws Exception {
        return (ArrayList<Kategorija>) sendRequest(Operation.GET_ALL_KATEGORIJA, kategorija);
    }

    private synchronized Object sendRequest(int operation, Object data) throws Exception {
        Request request = new Request(operation, data);

        
        ObjectOutputStream out = new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        out.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getResponseStatus().equals(ResponseStatus.Error)) {
            throw response.getException();
        } else {
            return response.getData();
        }

    }

}
