/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.ServerController;
import domain.Kategorija;
import domain.Knjiga;
import domain.Knjizar;
import domain.Kupac;
import domain.Porudzbina;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author Mila
 */
public class ThreadClient extends Thread {

    private Socket socket;

    ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) in.readObject();
                Response response = handleRequest(request);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        Response response = new Response(null, null, ResponseStatus.Success);
        try {
            switch (request.getOperation()) {
                case Operation.ADD_KUPAC:
                    ServerController.getInstance().addKupac((Kupac) request.getData());
                    break;
                case Operation.ADD_KATEGORIJA:
                    ServerController.getInstance().addKategorija((Kategorija) request.getData());
                    break;
                case Operation.ADD_KNJIGA:
                    ServerController.getInstance().addKnjiga((Knjiga) request.getData());
                    break;
                case Operation.ADD_PORUDZBINA:
                    ServerController.getInstance().addPorudzbina((Porudzbina) request.getData());
                    break;
                case Operation.DELETE_KUPAC:
                    ServerController.getInstance().deleteKupac((Kupac) request.getData());
                    break;
                case Operation.DELETE_KNJIGA:
                    ServerController.getInstance().deleteKnjiga((Knjiga) request.getData());
                    break;
                case Operation.DELETE_KATEGORIJA:
                    ServerController.getInstance().deleteKategorija((Kategorija) request.getData());
                    break;
                case Operation.DELETE_PORUDZBINA:
                    ServerController.getInstance().deletePorudzbina((Porudzbina) request.getData());
                    break;
                case Operation.UPDATE_KUPAC:
                    ServerController.getInstance().updateKupac((Kupac) request.getData());
                    break;
                case Operation.UPDATE_KNJIGA:
                    ServerController.getInstance().updateKnjiga((Knjiga) request.getData());
                    break;
                case Operation.UPDATE_KATEGORIJA:
                    ServerController.getInstance().updateKategorija((Kategorija) request.getData());
                    break;
                case Operation.UPDATE_PORUDZBINA:
                    ServerController.getInstance().updatePorudzbina((Porudzbina) request.getData());
                    break;
                case Operation.GET_ALL_KATEGORIJA:
                    response.setData(ServerController.getInstance().getAllKategorija((Kategorija) request.getData()));
                    break;
                case Operation.GET_ALL_KUPAC:
                    response.setData(ServerController.getInstance().getAllKupac((Kupac) request.getData()));
                    break;
                case Operation.GET_ALL_PORUDZBINA:
                    response.setData(ServerController.getInstance().getAllPorudzbina((Porudzbina) request.getData()));
                    break;
                case Operation.GET_ALL_KNJIGA:
                    response.setData(ServerController.getInstance().getAllKnjiga((Knjiga) request.getData()));
                    break;
                case Operation.LOGIN:
                    Knjizar knjizar = (Knjizar) request.getData();
                    Knjizar kn = ServerController.getInstance().login(knjizar);
                    response.setData(kn);
                    break;
                case Operation.LOGOUT:
                    Knjizar ulogovani = (Knjizar) request.getData();
                    ServerController.getInstance().logout(ulogovani);
                    break;
                default:
                    return null;
            }
        } catch (Exception ex) {
            response.setResponseStatus(ResponseStatus.Error);
            response.setException(ex);
        }
        return response;
    }

}
