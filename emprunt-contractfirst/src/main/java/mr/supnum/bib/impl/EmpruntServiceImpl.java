package mr.supnum.bib.impl;

import jakarta.jws.WebService;
import com.sun.xml.ws.developer.SchemaValidation;
import mr.supnum.bib.contrat.EmpruntPortType;
import mr.supnum.bib.contrat.EmpruntType;
import mr.supnum.bib.contrat.LivreIndisponible;
import mr.supnum.bib.contrat.LivreIndisponibleFault;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@WebService(serviceName = "EmpruntService", portName = "EmpruntPort",
        targetNamespace = "http://supnum.mr/bib",
        endpointInterface = "mr.supnum.bib.contrat.EmpruntPortType",
        wsdlLocation = "wsdl/EmpruntService.wsdl")
@SchemaValidation
public class EmpruntServiceImpl implements EmpruntPortType {
    @Override
    public EmpruntType emprunterLivre(String idAdherent, String isbn) throws LivreIndisponibleFault {
        if ("978-2-10-074444-4".equals(isbn)) {
            LivreIndisponible info = new LivreIndisponible();
            info.setIsbn(isbn);
            info.setRetourPrevu(date(LocalDate.of(2026, 12, 2)));
            throw new LivreIndisponibleFault("Livre indisponible", info);
        }
        EmpruntType emprunt = new EmpruntType();
        emprunt.setNumero("E-2026-0812");
        emprunt.setDateRetour(date(LocalDate.now().plusDays(14)));
        return emprunt;
    }

    private static XMLGregorianCalendar date(LocalDate value) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(value.toString());
        } catch (Exception e) {
            throw new IllegalStateException("Impossible de créer la date XML", e);
        }
    }
}
