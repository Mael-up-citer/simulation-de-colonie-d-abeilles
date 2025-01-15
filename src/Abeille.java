/**
 * Représente une abeille dans la simulation. L'abeille peut se déplacer, interagir avec des sources de nourriture,
 * et effectuer des danses pour communiquer l'emplacement des sources aux autres abeilles.
 * Chaque abeille possède une position, une direction et une vitesse.
 * 
 */
abstract class Abeille implements Affichable {
    
    /** Position de l'abeille sur le plateau (en pixels) */
    protected float x, y;
    
    /** Vecteur de direction de l'abeille (dx, dy) */
    protected float dx, dy;
    
    /** Vitesse de déplacement de l'abeille */
    protected float v = 5;
    
    /** Position de destination de l'abeille (en pixels) */
    protected int destX = -10, destY = -10;
    
    /** Position de la source associée en matrice (coordonnées de la source) */
    protected int XSource = -1, YSource = -1;


    /**
     * Méthode abstraite définissant le comportement de mouvement spécifique de chaque type d'abeille.
     * 
     * @param t Tableau représentant l'environnement de simulation.
     * @return vrai si l'abeille à réalisé sont objectif.
     */
    abstract boolean mouv(Tableau t);


    /**
     * Déplace l'abeille en fonction de son vecteur de direction et de sa vitesse.
     * Le déplacement est effectué selon le vecteur normalisé (dx, dy).
     */
    void deplace() {
        this.x += (dx * v) / Math.sqrt(dx * dx + dy * dy);
        this.y += (dy * v) / Math.sqrt(dx * dx + dy * dy);
    }


    /**
     * Vérifie si l'abeille a atteint sa destination (en considérant une tolérance de 5px).
     * 
     * @return true si l'abeille est à moins de 5px de sa destination, sinon false.
     */
    boolean Atteint() {
        return ((x - 5 < destX) && (x + 5 > destX) && (y - 5 < destY) && (y + 5 > destY));
    }


    /**
     * Vérifie si la case située aux coordonnées de l'abeille contient une fleur avec du pollen (quantité > 0).
     * 
     * @param tab L'environnement de simulation (tableau des cases).
     * @return true si la case contient une fleur avec du pollen, sinon false.
     */
    boolean isFlower(Tableau tab) {
        // Convertit les coordonnées x et y en indices de matrice
        int x1 = (int) (x / tab.getXCase());
        int y1 = (int) (y / tab.getYCase());

        return (tab.getPlateau()[y1][x1].getQtt() > 0);
    }
    

    /**
     * Met à jour la destination de l'abeille en fonction des coordonnées d'une case donnée (en pixels).
     * Cette méthode calcule également le vecteur de direction (dx, dy) pour atteindre cette case.
     * 
     * @param px Coordonnée x de la destination (en pixels).
     * @param py Coordonnée y de la destination (en pixels).
     */
    void goCase(int px, int py) {
        // Met à jour la destination
        destX = px;
        destY = py;
        
        // Met à jour le vecteur de direction (dx, dy)
        dx = px - x;
        dy = py - y;
    }


    /**
     * Effectue une danse pour informer les observatrices de la position d'une source de nourriture.
     * Si la source est nouvelle, elle est ajoutée à la liste des sources observées.
     * 
     * @param mx Coordonnée x de la source de nourriture (en matrice).
     * @param my Coordonnée y de la source de nourriture (en matrice).
     * @return true si la source a été ajoutée, sinon false (si elle était déjà présente).
     */
    boolean danse(int mx, int my) {
        boolean flag = false;
    
        // Vérifie si la source n'est pas déjà dans la liste des sources observées
        for (int i = 0; i < Observatrices.getCoordonneesEtQualite().length; i++) {
            if ((Observatrices.getCoordonneesEtQualite()[i][0] == mx) && (Observatrices.getCoordonneesEtQualite()[i][1] == my)) {
                flag = true;
            }
        }
        
        // Si la source est nouvelle, l'ajoute à la liste
        if (!flag) {
            Observatrices.getCoordonneesEtQualite()[Observatrices.getIndiceRemplissage()][0] = mx;
            Observatrices.getCoordonneesEtQualite()[Observatrices.getIndiceRemplissage()][1] = my;
            Observatrices.setIndiceRemplissage(Observatrices.getIndiceRemplissage() + 1);
        }
        
        return !flag;
    }


    /**
     * Vérifie si l'abeille est à proximité de la ruche, avec une tolérance de 5px.
     * 
     * @param tab L'environnement de simulation (tableau des cases).
     * @return true si l'abeille est à proximité de la ruche, sinon false.
     */
    boolean isRuche(Tableau tab) {
        return ((((x - 5 < tab.getXruche() * tab.getXCase()) && (y + 5 > tab.getYruche() * tab.getYCase())) && 
                 ((x + 5 > tab.getXruche() * tab.getXCase()) && (y - 5 < tab.getYruche() * tab.getYCase()))));
    }

    // Getters et Setters pour les attributs

    /**
     * Récupère la position x de l'abeille.
     * 
     * @return La position x de l'abeille (en pixels).
     */
    public float getX() {
        return x;
    }

    /**
     * Récupère la position y de l'abeille.
     * 
     * @return La position y de l'abeille (en pixels).
     */
    public float getY() {
        return y;
    }

    /**
     * Récupère la direction x (dx) de l'abeille.
     * 
     * @return La direction x de l'abeille.
     */
    public float getDx() {
        return dx;
    }

    /**
     * Récupère la direction y (dy) de l'abeille.
     * 
     * @return La direction y de l'abeille.
     */
    public float getDy() {
        return dy;
    }

    /**
     * Récupère la vitesse de l'abeille.
     * 
     * @return La vitesse de l'abeille.
     */
    public float getV() {
        return v;
    }

    /**
     * Récupère la position x de la destination de l'abeille.
     * 
     * @return La position x de la destination.
     */
    public int getDestX() {
        return destX;
    }

    /**
     * Récupère la position y de la destination de l'abeille.
     * 
     * @return La position y de la destination.
     */
    public int getDestY() {
        return destY;
    }

    /**
     * Récupère la position x de la source associée.
     * 
     * @return La position x de la source.
     */
    public int getXSource() {
        return XSource;
    }

    /**
     * Récupère la position y de la source associée.
     * 
     * @return La position y de la source.
     */
    public int getYSource() {
        return YSource;
    }

    // Setters

    /**
     * Définit la vitesse de l'abeille.
     * 
     * @param v La nouvelle vitesse.
     */
    public void setV(float v) {
        this.v = v;
    }

    /**
     * Définit la position x de l'abeille.
     * 
     * @param newX La nouvelle position x.
     */
    public void setX(float newX) {
        this.x = newX;
    }

    /**
     * Définit la position y de l'abeille.
     * 
     * @param newY La nouvelle position y.
     */
    public void setY(float newY) {
        this.y = newY;
    }

    /**
     * Définit la direction x (dx) de l'abeille.
     * 
     * @param newDx La nouvelle direction x.
     */
    public void setDx(float newDx) {
        this.dx = newDx;
    }

    /**
     * Définit la direction y (dy) de l'abeille.
     * 
     * @param newDy La nouvelle direction y.
     */
    public void setDy(float newDy) {
        this.dy = newDy;
    }

    /**
     * Définit la position x de la destination de l'abeille.
     * 
     * @param newDestX La nouvelle position x de la destination.
     */
    public void setDestX(int newDestX) {
        this.destX = newDestX;
    }

    /**
     * Définit la position y de la destination de l'abeille.
     * 
     * @param newDestY La nouvelle position y de la destination.
     */
    public void setDestY(int newDestY) {
        this.destY = newDestY;
    }

    /**
     * Définit la position x de la source associée à l'abeille.
     * 
     * @param newXSource La nouvelle position x de la source.
     */
    public void setXSource(int newXSource) {
        this.XSource = newXSource;
    }

    /**
     * Définit la position y de la source associée à l'abeille.
     * 
     * @param newYSource La nouvelle position y de la source.
     */
    public void setYSource(int newYSource) {
        this.YSource = newYSource;
    }
}