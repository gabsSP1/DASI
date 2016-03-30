/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import metier.service.ServiceMetier;
import util.Saisie;

/**
 *
 * @author Gabriel
 */
public class FinirCommande {
    public static void main (String[] args) throws Throwable
    {
        int id=Saisie.lireInteger("Id commande à cloturer: ");
        ServiceMetier.cloturerCommandeId((long)id);
    }
}
