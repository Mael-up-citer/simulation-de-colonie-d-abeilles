/**
 * Représente le plateau de la simulation, incluant la ruche, les sources de pollen et les abeilles.
 * Le tableau est une grille où chaque case peut contenir soit la ruche, soit une source de pollen.
 * Le tableau gère la répartition des sources de pollen, le placement des abeilles et les interactions entre ces éléments.
 */
public class Tableau {
    private int size;       // Taille du plateau (en nombre de cases)
    private int Xruche, Yruche;  // Coordonnées de la ruche sur le plateau
    private Case[][] plateau;    // Le plateau contenant les cases (source de pollen ou ruche)
    private int XCase, YCase;    // Taille en pixels de chaque case
    private int qttpolen;        // Quantité totale de pollen disponible dans le tableau

    /**
     * Constructeur de la classe Tableau.
     * Initialise un plateau de taille spécifiée avec une ruche, des sources de pollen et des abeilles.
     * Le nombre de sources de pollen et de leur quantité est défini lors de l'initialisation.
     * 
     * @param size          Taille du plateau en termes de nombre de cases
     * @param nbsource      Nombre de sources de pollen à générer
     * @param qttpolen      Quantité totale de pollen à répartir dans le plateau
     * @param nbObservatrice Nombre d'abeilles observatrices
     * @param nbEclaireuse   Nombre d'abeilles éclaireuses
     * @param h             Hauteur en pixels du plateau
     * @param w             Largeur en pixels du plateau
     * @param es            Nombre d'essais (visites autorisées) pour chaque source
     */
    public Tableau(int size, int nbsource, int qttpolen, int nbObservatrice, int nbEclaireuse, int h, int w, int es) {
        this.qttpolen = qttpolen;
        XCase = w / size;          // Calcul de la taille d'une case en pixels pour X
        YCase = (h - 20) / size;   // Calcul de la taille d'une case en pixels pour Y (ajuste pour une bande blanche)
        this.size = size;
        this.plateau = new Case[size][size];
        double qtt = 0;

        // Position aléatoire pour la ruche
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);
        plateau[y][x] = new Ruche(nbEclaireuse, nbsource, nbObservatrice, x * XCase, y * YCase);
        Xruche = x;
        Yruche = y;

        // Remplir le plateau avec des sources de pollen vides
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Si la case n'est pas la ruche, elle devient une source vide
                if (plateau[i][j] == null) {
                    plateau[i][j] = new Source(0, j, i, es);
                }
            }
        }

        // Répartition des sources de pollen (nbsource - 1 sources)
        while (nbsource > 1) {
            // Sélection d'une position aléatoire pour la source
            x = (int) (Math.random() * size);
            y = (int) (Math.random() * size);

            // Vérification que la case n'est pas la ruche et n'est pas déjà remplie
            // Elle doit être en dehors d'un rayon de 2 autour de la ruche
            while ((plateau[y][x] instanceof Ruche) || (plateau[y][x].getQtt() != 0) || 
                   ((Math.abs(x - Xruche) < 3) && (Math.abs(y - Yruche) < 3))) {
                x = (int) (Math.random() * size);
                y = (int) (Math.random() * size);
            }

            // Définir la quantité de pollen pour cette source
            qtt = Math.random() / 4;
            qtt = qtt * (qttpolen - nbsource + 1) + 1;
            plateau[y][x].setQtt((int) qtt);
            qttpolen -= (int) qtt;   // Mettre à jour la quantité de pollen restante
            nbsource -= 1;            // Réduire le nombre de sources à générer
        }

        // Dernière source de pollen (on place le reste du pollen)
        x = (int) (Math.random() * size);
        y = (int) (Math.random() * size);

        // Vérifier que la case n'est pas la ruche et n'est pas déjà remplie
        while (plateau[y][x] instanceof Ruche || plateau[y][x].getQtt() != 0 || 
               ((Math.abs(x - Xruche) < 3) && (Math.abs(y - Yruche) < 3))) {
            x = (int) (Math.random() * size);
            y = (int) (Math.random() * size);
        }

        // Mettre le reste du pollen dans cette dernière source
        plateau[y][x].setQtt(qttpolen);
    }

    /**
     * Affiche le plateau de jeu, avec toutes les sources de pollen et la ruche.
     * Utilise la méthode `aff()` de chaque case pour afficher son contenu.
     */
    void affTableau() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.plateau[i][j].aff();
            }
            System.out.println("");
        }
    }

    /**
     * Retourne la taille du plateau (en nombre de cases).
     * 
     * @return La taille du plateau
     */
    int getSize() {
        return size;
    }

    /**
     * Retourne la coordonnée X de la ruche.
     * 
     * @return La coordonnée X de la ruche
     */
    int getXruche() {
        return Xruche;
    }

    /**
     * Retourne la coordonnée Y de la ruche.
     * 
     * @return La coordonnée Y de la ruche
     */
    int getYruche() {
        return Yruche;
    }

    /**
     * Retourne le plateau complet (la grille de cases).
     * 
     * @return Le plateau sous forme de matrice de `Case`
     */
    Case[][] getPlateau() {
        return plateau;
    }

    /**
     * Retourne la taille d'une case en pixels sur l'axe X.
     * 
     * @return La taille d'une case en pixels sur l'axe X
     */
    int getXCase() {
        return XCase;
    }

    /**
     * Retourne la taille d'une case en pixels sur l'axe Y.
     * 
     * @return La taille d'une case en pixels sur l'axe Y
     */
    int getYCase() {
        return YCase;
    }

    /**
     * Retourne la quantité totale de pollen disponible sur le plateau.
     * 
     * @return La quantité totale de pollen
     */
    int getQttpolen() {
        return qttpolen;
    }
}