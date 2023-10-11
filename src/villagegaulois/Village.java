package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import exceptions.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMax) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMax);
	}
	
	private static class Marche {
		private int nbEtalsMax;
		private Etal[] etals = new Etal[50];
		
		private Marche(int nbEtalsMax) {
			this.nbEtalsMax = nbEtalsMax;
		    for (int i = 0; i < nbEtalsMax; i++) {
		        etals[i] = new Etal();
		    }
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < nbEtalsMax; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbProduitEtals = 0;
			Etal[] produitEtals = new Etal[nbEtalsMax];
			for (int i = 0; i < nbEtalsMax; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					produitEtals[nbProduitEtals] = etals[i];
					nbProduitEtals++;
				}
			}
			return produitEtals;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < nbEtalsMax; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = nbEtalsMax;
			for (int i = 0; i < nbEtalsMax; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					nbEtalVide--;
				}
			}
			if (nbEtalVide != 0) {
				chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché \n");
			}
			return chaine.toString();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		
	    if (chef == null) {
	        throw new VillageSansChefException("Le village n'a pas de chef.");
	    }
		
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int etalNb = marche.trouverEtalLibre();
		if (etalNb == -1) {
			chaine.append("Il n'existe pas d'étal libre, " + vendeur.getNom() + " s'en va.\n");
		} else {
			marche.utiliserEtal(etalNb, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + etalNb + ".\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] produitEtals = marche.trouverEtals(produit);
		if (produitEtals == null) {
			chaine.append("Aucun vendeur ne propose des " + produit + ".");
		} else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; produitEtals[i] != null; i++) {
				chaine.append("- " + produitEtals[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
}