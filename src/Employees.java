/**
 * La classe `Employees` représente une abeille ouvrière qui récolte du pollen,
 * le transporte jusqu'à la ruche, et aide à gérer la source de pollen en associant
 * chaque abeille à une source spécifique.
 * 
 * Cette classe hérite de la classe `Abeille` et permet aux ouvrières de chercher
 * des fleurs, de transmettre le pollen à la ruche, et de coopérer pour gérer
 * les sources de pollen.
 * 
 */
public class Employees extends Abeille {
    private int pollenAssocie = 0; // Quantité de pollen que l'ouvrière transporte.
    private int where = 1; // Indicateur de l'étape actuelle de l'abeille (1: recherche, 2: transmission).

    /**
     * Constructeur de la classe `Employees`.
     * Initialise la position de l'abeille ouvrière ainsi que sa vitesse de déplacement.
     * 
     * @param x1 La position x de l'abeille en pixels.
     * @param y1 La position y de l'abeille en pixels.
     * @param dx1 La vitesse de déplacement de l'abeille sur l'axe x.
     * @param dy1 La vitesse de déplacement de l'abeille sur l'axe y.
     */
    Employees(int x1, int y1, int dx1, int dy1) {
        x = x1;
        y = y1;
        dx = dx1;
        dy = dy1;
    }

    /**
     * Retourne le type de l'abeille, ici "employees".
     * 
     * @return Le type de l'abeille.
     */
    public String getType() {
        return "employees";
    }

    /**
     * Définit le comportement de l'abeille ouvrière pendant son déplacement.
     * L'abeille se déplace pour chercher des fleurs, puis revient à la ruche
     * pour transmettre le pollen récolté.
     * 
     * @param tab L'objet `Tableau` représentant le plateau de simulation.
     * @return `true` si l'abeille a terminé sa mission (transmission de pollen),
     *         `false` sinon.
     */
    boolean mouv(Tableau tab) {
        boolean res = false; // Indicateur de réussite de la mission.

        // Si la case est atteinte :
        if (Atteint()) {
            // Si l'abeille est en train de chercher du pollen (première visite) :
            if (where == 1) {
                // Recherche les meilleures sources de pollen.
                checkVoisins(tab);
                // Retourne à la ruche.
                goCase(tab.getXruche() * tab.getXCase(), tab.getYruche() * tab.getYCase());
                where += 1; // Passe à l'étape suivante.
            }
            // Si l'abeille est à la ruche (deuxième visite) :
            else if (where == 2) {
                // Transmet les coordonnées de la source de pollen.
                danse(XSource, YSource);

                // Donne le pollen récolté à la ruche.
                givePollen(pollenAssocie, tab);

                // Retourne à la source de pollen pour continuer la récolte.
                goCase((int)(XSource * tab.getXCase()), (int)(YSource * tab.getYCase()));

                // La mission est terminée.
                res = true;
                where += 1; // Passe à l'étape suivante.
            }
        }
        // Si l'abeille n'a pas encore atteint sa destination, elle se déplace.
        else {
            deplace();
        }

        return res;
    }

    /**
     * Transmet la quantité de pollen récolté à la ruche.
     * L'abeille donne le pollen à la ruche et met à jour la source de pollen.
     * 
     * @param qttpol La quantité de pollen que l'abeille transporte.
     * @param tab L'objet `Tableau` représentant le plateau de simulation.
     */
    void givePollen(int qttpol, Tableau tab) {
        float pollenToGive;

        // Recherche la source de pollen associée et lui attribue un facteur de variation.
        for (int i = 0; i < Observatrices.getCoordonneesEtQualite().length; i++) {
            if (Observatrices.getCoordonneesEtQualite()[i][0] == XSource &&
                Observatrices.getCoordonneesEtQualite()[i][1] == YSource) {
                pollenToGive = (float)(Math.random());
                if (pollenToGive < 0.15)
                    pollenToGive += 1;
                else
                    pollenToGive = 0.85f;

                // Met à jour la quantité de pollen récolté à cette source.
                Observatrices.getCoordonneesEtQualite()[i][2] = (int)(qttpol * pollenToGive);
            }
        }

        // Ajoute le pollen à la ruche.
        tab.getPlateau()[tab.getXruche()][tab.getYruche()].setQtt(
            tab.getPlateau()[tab.getXruche()][tab.getYruche()].getQtt() + qttpol
        );
    }

    /**
     * Vérifie les sources de pollen voisines pour trouver la meilleure source
     * à récolter. La meilleure source est celle avec la quantité de pollen la
     * plus élevée et qui n'est pas encore occupée par une autre abeille.
     * 
     * @param tab L'objet `Tableau` représentant le plateau de simulation.
     */
    void checkVoisins(Tableau tab) {
        int i1 = YSource, j1 = XSource; // Coordonnées pour éviter les erreurs de mémoire.
        int qte = 0; // Quantité de pollen d'une source voisine.

        // Parcours les voisins pour trouver la meilleure source.
        for (int i = (int)((y / tab.getYCase()) - 3); i <= (int)((y / tab.getYCase()) + 3); i++) {
            i1 = (i + tab.getPlateau().length) % tab.getPlateau().length; // Évite les indices négatifs.

            for (int j = (int)((x / tab.getXCase()) - 3); j <= (int)((x / tab.getXCase()) + 3); j++) {
                j1 = (j + tab.getPlateau().length) % tab.getPlateau().length; // Évite les indices négatifs.
                qte = tab.getPlateau()[i1][j1].getQtt();

                // Si la source a plus de pollen et qu'elle n'est pas encore occupée,
                // on la choisit comme la meilleure source.
                if (qte > pollenAssocie && tab.getPlateau()[i1][j1].pollinisatrice == null) {
                    pollenAssocie = qte;
                    XSource = j1;
                    YSource = i1;
                }
            }
        }

        // Met à jour la tentative de la source.
        tab.getPlateau()[YSource][XSource].setEssaie(
            tab.getPlateau()[YSource][XSource].GetEssaie() - 1
        );

        // Associe la source à cette abeille.
        tab.getPlateau()[YSource][XSource].setPollinisatrice(this);
    }

    // Accesseurs et mutateurs pour les attributs :
    public int getPollenAssocie() {
        return pollenAssocie;
    }

    public void setPollenAssocie(int var) {
        pollenAssocie = var;
    }

    public int getWhere() {
        return where;
    }

    public void setWhere(int var) {
        where = var;
    }
}