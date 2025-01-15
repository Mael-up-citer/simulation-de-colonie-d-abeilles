/**
 * Représente une abeille observatrice dans le système de simulation.
 * Les abeilles observatrices sont responsables de l'exploration des sources de pollen et de leur collecte.
 * Elles peuvent se déplacer, choisir une source de pollen à visiter et interagir avec d'autres abeilles.
 */
public class Observatrices extends Abeille {

    // Contient les coordonnées (x, y) et la qualité de chaque source de pollen
    private static int[][] coordonneesEtQualite;

    // Indice où la prochaine source de pollen sera stockée dans coordonneesEtQualite
    private static int IndiceRemplissage = 0;

    // Variable indiquant si le tableau coordonneesEtQualite a déjà été initialisé
    private static boolean tableauInitialise = false;

    // Source de pollen assignée à l'abeille
    private Case src = null;

    /**
     * Constructeur de l'abeille observatrice.
     * Initialise les coordonnées de l'abeille et initialise le tableau des sources si ce n'est pas déjà fait.
     * 
     * @param x1   La coordonnée X de l'abeille
     * @param y1   La coordonnée Y de l'abeille
     * @param dx1  Le déplacement en X de l'abeille
     * @param dy1  Le déplacement en Y de l'abeille
     * @param nbSrc Le nombre de sources de pollen dans la simulation
     */
    Observatrices(int x1, int y1, int dx1, int dy1, int nbSrc) {
        // Vérifie si le tableau des sources a déjà été initialisé
        if (!tableauInitialise) {
            coordonneesEtQualite = new int[nbSrc][3];
            tableauInitialise = true; // Marque le tableau comme initialisé
        }
        x = x1;
        y = y1;
        dx = dx1;
        dy = dy1;
    }

    /**
     * Retourne le type de l'abeille.
     * 
     * @return Le type de l'abeille, ici "observatrice"
     */
    public String getType() {
        return "observatrice";
    }

    /**
     * Méthode de mouvement de l'abeille observatrice.
     * Si l'abeille n'a pas de source assignée, elle en choisit une. 
     * Si elle a atteint une source, elle vérifie si celle-ci peut encore être visitée.
     * Si la source est pleine, l'abeille abandonne la source et réactive une éclaireuse.
     * 
     * @param tab L'objet Tableau représentant le plateau de simulation
     * @return false (car la méthode ne termine pas l'exécution du programme)
     */
    public boolean mouv(Tableau tab) {
        // Si l'abeille n'a pas de source assignée
        if (this.getSrc() == null) {
            this.choisiSrc(tab); // Choisit une source
        } else if (Atteint()) {
            // Si l'abeille a atteint la source
            if (tab.getPlateau()[YSource][XSource].GetEssaie() == 0) {
                src = null; // Enlève la source
                Ruche r = (Ruche) (tab.getPlateau()[tab.getYruche()][tab.getXruche()]);
                // Réactive une éclaireuse
                Eclaireuses ecl = (Eclaireuses) (r.getPopulation()[Ruche.getIndiceEclaireuse()]);
                // Active l'éclaireuse
                ecl.XSource = -1;
                ecl.setAVisite(false);

                // Donne de nouvelles coordonnées aléatoires à l'éclaireuse
                int x = (int) (Math.random() * tab.getSize() * tab.getXCase());
                int y = (int) (Math.random() * tab.getSize() * tab.getYCase());
                ecl.goCase(x, y);

                System.out.println("L'observatrice parle à " + Ruche.getIndiceEclaireuse() + " car la source est pleine");
                // Incrémente l'indice de l'éclaireuse pour la suite
                Ruche.setIndiceEclaireuse((Ruche.getIndiceEclaireuse() + 1) % r.getNbEclaireuse());
            }
        } else {
            deplace(); // Déplace l'abeille
        }
        return false;
    }

    /**
     * Choisit une source de pollen pour l'abeille observatrice.
     * L'abeille choisit la source ayant la meilleure qualité de pollen et qui reste disponible.
     * 
     * @param tab L'objet Tableau représentant le plateau de simulation
     */
    public void choisiSrc(Tableau tab) {
        int max = 0;   // La qualité maximale du pollen
        int indice = 0; // L'indice de la meilleure source

        for (int i = 0; i < Observatrices.IndiceRemplissage; i++) {
            // Si la source est meilleure, avec une probabilité de 85%, et si elle reste disponible
            if ((Observatrices.coordonneesEtQualite[i][2] > max) && (Math.random() < 0.85) && 
                (tab.getPlateau()[Observatrices.coordonneesEtQualite[i][1]][Observatrices.coordonneesEtQualite[i][0]].GetEssaie() > 0)) {
                max = Observatrices.coordonneesEtQualite[i][2]; // Met à jour la qualité maximale
                indice = i; // Sauvegarde l'indice de la source
            }
        }
        
        // Si une source valide a été trouvée
        if (max != 0) {
            // Sauvegarde les coordonnées de la source
            XSource = Observatrices.coordonneesEtQualite[indice][0];
            YSource = Observatrices.coordonneesEtQualite[indice][1];

            // Associe la source à l'abeille
            src = tab.getPlateau()[XSource][YSource];

            // Met à jour le nombre d'essais restants pour la source
            tab.getPlateau()[YSource][XSource].setEssaie(tab.getPlateau()[YSource][XSource].GetEssaie() - 1);

            System.out.println("L'observatrice prend la source de quantité " + Observatrices.coordonneesEtQualite[indice][2] + 
                               " à (" + XSource + ", " + YSource + ")");
            System.out.println("Essais restants : " + tab.getPlateau()[Observatrices.coordonneesEtQualite[indice][1]][Observatrices.coordonneesEtQualite[indice][0]].GetEssaie());

            // Déplace l'abeille vers la source choisie
            goCase(XSource * tab.getXCase(), YSource * tab.getYCase());
        }
    }

    /**
     * Retourne la source de pollen actuellement assignée à l'abeille.
     * 
     * @return La source assignée à l'abeille
     */
    Case getSrc() {
        return src;
    }

    /**
     * Retourne les coordonnées et la qualité de toutes les sources de pollen.
     * 
     * @return Le tableau des coordonnées et de la qualité des sources de pollen
     */
    public static int[][] getCoordonneesEtQualite() {
        return Observatrices.coordonneesEtQualite;
    }

    /**
     * Retourne l'indice actuel de remplissage du tableau des sources de pollen.
     * 
     * @return L'indice de remplissage du tableau des sources
     */
    public static int getIndiceRemplissage() {
        return Observatrices.IndiceRemplissage;
    }

    /**
     * Modifie l'indice de remplissage du tableau des sources de pollen.
     * 
     * @param var Le nouvel indice de remplissage
     */
    public static void setIndiceRemplissage(int var) {
        Observatrices.IndiceRemplissage = var;
    }
}