/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.knjiga;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Knjiga;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOGetAllKnjiga extends AbstractSO {

    private ArrayList<Knjiga> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Knjiga)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Knjiga!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> knjige = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Knjiga>) (ArrayList<?>) knjige;
    }

    public ArrayList<Knjiga> getLista() {
        return lista;
    }

}
