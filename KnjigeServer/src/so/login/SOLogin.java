/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.login;

import controller.ServerController;
import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Knjizar;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOLogin extends AbstractSO {

    Knjizar ulogovani;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Knjizar)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Knjizar!");
        }

        Knjizar k = (Knjizar) ado;

        for (Knjizar knjizar : ServerController.getInstance().getUlogovaniKnjizari()) {
            if (knjizar.getKorisnickoIme().equals(k.getKorisnickoIme())) {
                throw new Exception("Ovaj knjizar je vec ulogovan na sistem!");
            }
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {

        Knjizar k = (Knjizar) ado;

        ArrayList<Knjizar> knjizari
                = (ArrayList<Knjizar>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Knjizar knjizar : knjizari) {
            if (knjizar.getKorisnickoIme().equals(k.getKorisnickoIme())
                    && knjizar.getLozinka().equals(k.getLozinka())) {
                ulogovani = knjizar;
                ServerController.getInstance().getUlogovaniKnjizari().add(knjizar);
                return;
            }
        }

        throw new Exception("Ne postoji knjizar sa tim kredencijalima.");

    }

    public Knjizar getUlogovani() {
        return ulogovani;
    }

}
