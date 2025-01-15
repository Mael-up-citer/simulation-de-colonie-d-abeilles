/**
 * Classe abstraite représentant une case dans le plateau de simulation.
 * Une case peut contenir une abeille pollinisatrice et une quantité de pollen.
 * Elle définit les méthodes pour accéder et modifier les informations de la case.
 * 
 * 
 */
public abstract class Case implements Affichable {

    // Position de la case sur le plateau (en pixels)
    protected int x, y;
    
    // Abeille pollinisatrice associée à la case (null si aucune abeille)
    protected Abeille pollinisatrice = null;
    
    // Quantité de pollen disponible sur la case
    protected int qtt = 0;

    /**
     * Méthode abstraite permettant d'obtenir la population d'abeilles associée à cette case.
     * Les classes dérivées doivent implémenter cette méthode.
     * 
     * @return Un tableau d'abeilles représentant la population associée à cette case.
     */
    abstract Abeille[] getPopulation();

    /**
     * Méthode permettant de récupérer la quantité de pollen sur la case.
     * 
     * @return La quantité de pollen sur la case.
     */
    public int getQtt(){
        return qtt;
    }

    /**
     * Méthode permettant de récupérer la position x de la case.
     * 
     * @return La position x de la case (en pixels).
     */
    public int getX(){
        return x;
    }

    /**
     * Méthode permettant de récupérer la position y de la case.
     * 
     * @return La position y de la case (en pixels).
     */
    public int getY(){
        return y;
    }

    /**
     * Méthode permettant de définir la quantité de pollen de la case.
     * 
     * @param n La nouvelle quantité de pollen.
     */
    public void setQtt(int n){
        qtt = n;
    }

    /**
     * Méthode permettant de récupérer l'abeille pollinisatrice associée à cette case.
     * 
     * @return L'abeille pollinisatrice (null si aucune abeille associée).
     */
    public Abeille getPollinisatrice(){
        return pollinisatrice;
    }

    /**
     * Méthode permettant de définir l'abeille pollinisatrice associée à cette case.
     * 
     * @param a L'abeille à associer à la case.
     */
    public void setPollinisatrice(Abeille a){
        pollinisatrice = a;
    }

    /**
     * Constructeur de la classe `Case`.
     * Initialise la position de la case avec les coordonnées x et y.
     * 
     * @param x1 La position x de la case (en pixels).
     * @param y1 La position y de la case (en pixels).
     */
    Case(int x1, int y1){
        x = x1;
        y = y1;
    }

    /**
     * Méthode d'affichage de la case (actuellement ne fait rien, pourrait être surchargée dans les classes dérivées).
     */
    void aff(){
        System.out.print("");
    }

    /**
     * Méthode pour définir un essai (ne semble pas utilisée dans cette classe mais peut être définie dans une sous-classe).
     * 
     * @param var La valeur de l'essai.
     */
    public void setEssaie(int var){
        System.out.println("laaa");
    }

    /**
     * Méthode pour obtenir un essai (ne semble pas utilisée dans cette classe mais peut être définie dans une sous-classe).
     * 
     * @return La valeur de l'essai (ici retourne 0 par défaut).
     */
    public int GetEssaie(){
        System.out.println("err");
        return 0;
    }
}