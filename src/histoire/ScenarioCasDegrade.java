package histoire;

import exceptions.VillageSansChefException;
import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
	    Gaulois obelix = new Gaulois("Obélix", 10);
	    Etal etal = new Etal();
	    Village village = new Village("Toulouse", 20, 20);
	    int quantiteAcheter = 0;
	    
	    try {
	        village.afficherVillageois();
	    } catch (VillageSansChefException e) {
	        e.printStackTrace();
	    }
	    
	    if (!etal.isEtalOccupe()) {
	        throw new IllegalStateException("L'étal n'est pas occupé, impossible d'acheter des produits.");
	    }

	    if (quantiteAcheter < 1) {
	        throw new IllegalArgumentException("La quantité à acheter doit être d'au moins 1.");
	    } else {
	        etal.acheterProduit(quantiteAcheter, obelix);
	    }

	    System.out.println("Fin du test");
	}

}