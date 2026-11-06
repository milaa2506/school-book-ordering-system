/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.knjiga;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Knjiga;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOAddKnjiga extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Knjiga)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Knjiga!");
        }

        Knjiga knjiga = (Knjiga) ado;

        if (knjiga.getCena() <= 0) {
            throw new Exception("Cena knjige mora biti veca od 0!");
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().insert(ado);
    }

}
