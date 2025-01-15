/**
 * La classe `Eclaireuses` représente une abeille éclaireuse qui recherche des fleurs, 
 * revient à la ruche, et communique les informations de la source de pollen aux abeilles ouvrières.
 * 
 * Elle hérite de la classe `Abeille` et possède des comportements spécifiques pour 
 * déterminer la source de pollen et l'envoyer aux ouvrières.
 * 
 */
public class Eclaireuses extends Abeille {
    private boolean aVisite = false; // Indique si l'éclaireuse a trouvé une source de pollen.

    /**
     * Constructeur de la classe `Eclaireuses`.
     * Initialise la position de l'éclaireuse et sa direction de déplacement.
     * 
     * @param x1 La position x de l'éclaireuse en pixels.
     * @param y1 La position y de l'éclaireuse en pixels.
     * @param dx1 La vitesse de déplacement de l'éclaireuse sur l'axe x.
     * @param dy1 La vitesse de déplacement de l'éclaireuse sur l'axe y.
     */
    Eclaireuses(int x1, int y1, int dx1, int dy1) {
        x = x1;
        y = y1;
        dx = dx1;
        dy = dy1;
    }

    /**
     * Retourne le type de l'abeille.
     * 
     * @return Le type de l'abeille, ici "eclaireuses".
     */
    public String getType() {
        return "eclaireuses";
    }

    /**
     * Définit le comportement d'une abeille éclaireuse pendant son mouvement.
     * L'éclaireuse recherche des fleurs et, si elle en trouve, revient à la ruche et 
     * communique la position de la fleur aux abeilles ouvrières.
     * 
     * @param tab L'objet `Tableau` représentant le plateau de simulation.
     * @return `true` si l'éclaireuse a trouvé une nouvelle source de pollen, `false` sinon.
     */
    boolean mouv(Tableau tab) {
        boolean success = false; // Indique si l'éclaireuse a trouvé une nouvelle source de pollen

        // Recherche si l'éclaireuse est sur une fleur et que la fleur n'a pas été visitée
        if ((isFlower(tab)) && (XSource == -1) && 
            (tab.getPlateau()[(int) (y / tab.getYCase())][(int) (x / tab.getXCase())].getPollinisatrice() == null)) {
            // Récupère les coordonnées de la fleur
            XSource = (int) (x / tab.getXCase());
            YSource = (int) (y / tab.getYCase());
            // Retourne à la ruche
            goCase(tab.getXruche() * tab.getXCase(), tab.getYruche() * tab.getYCase());
        }

        // Si l'éclaireuse a atteint la ruche
        if (Atteint()) {
            // Si l'éclaireuse est revenue à la ruche, elle communique la source trouvée
            if (XSource != -1) {
                aVisite = true;
                // Communique les coordonnées de la fleur aux observatrices
                success = danse(XSource, YSource);

                // Si la source est nouvelle, une ouvrière est envoyée à la fleur
                if (success) {
                    int xsrc = Observatrices.getCoordonneesEtQualite()[Observatrices.getIndiceRemplissage() - 1][0] * tab.getXCase();
                    int ysrc = Observatrices.getCoordonneesEtQualite()[Observatrices.getIndiceRemplissage() - 1][1] * tab.getYCase();
                    System.out.println("La fleur est à x=" + xsrc + " y=" + ysrc);

                    // Accéder à la population d'ouvrières dans la ruche
                    Ruche r = (Ruche) (tab.getPlateau()[tab.getYruche()][tab.getXruche()]);
                    Employees empl = (Employees) (r.getPopulation()[Ruche.getIndiceEmployees()]);
                    // Envoie l'ouvrière à la source de pollen
                    empl.goCase(xsrc, ysrc);
                    // Donne les coordonnées de la source à l'ouvrière
                    empl.XSource = (int) (xsrc / tab.getXCase());
                    empl.YSource = (int) (ysrc / tab.getYCase());
                    Ruche.setIndiceEmployees(Ruche.getIndiceEmployees() + 1);
                }
            } else {
                // Si l'éclaireuse n'a pas trouvé de source, elle se déplace aléatoirement
                int x = (int) (Math.random() * tab.getSize() * tab.getXCase());
                int y = (int) (Math.random() * tab.getSize() * tab.getYCase());
                this.goCase(x, y);
            }
        }

        // Si l'éclaireuse n'a pas trouvé de source, elle continue de se déplacer
        if (!aVisite) {
            deplace();
        }

        return success;
    }

    /**
     * Retourne si l'éclaireuse a visité une source de pollen.
     * 
     * @return `true` si l'éclaireuse a visité une source de pollen, sinon `false`.
     */
    boolean getAVisite() {
        return aVisite;
    }

    /**
     * Modifie l'état de visite de l'éclaireuse.
     * 
     * @param var Le nouvel état de visite de l'éclaireuse (true ou false).
     */
    void setAVisite(boolean var) {
        aVisite = var;
    }
}