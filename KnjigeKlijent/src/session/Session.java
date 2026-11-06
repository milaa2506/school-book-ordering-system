/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import domain.Knjizar;
import forme.MainForm;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Mila
 */
public class Session {

    private static Session instance;
    private Socket socket;
    private Knjizar ulogovani;
    private MainForm mf;

    private Session() {
        try {
            socket = new Socket("localhost", 9000);
        } catch (IOException ex) {
//            ex.printStackTrace();
        }
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setUlogovani(Knjizar ulogovani) {
        this.ulogovani = ulogovani;
    }

    public Knjizar getUlogovani() {
        return ulogovani;
    }

    public MainForm getMf() {
        return mf;
    }

    public void setMf(MainForm mf) {
        this.mf = mf;
    }

}
