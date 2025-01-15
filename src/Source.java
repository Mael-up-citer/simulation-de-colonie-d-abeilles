/**
 * Représente une source de pollen dans le système de simulation.
 * Une source de pollen possède une quantité de pollen disponible et un nombre d'essais, c'est-à-dire le nombre de visites autorisées.
 * Les abeilles peuvent visiter cette source pour collecter du pollen, jusqu'à ce que le nombre d'essais soit épuisé.
 */
public class Source extends Case {
    // Nombre de visites restantes pour cette source
    private int nbEssaie;

    /**
     * Constructeur de la source de pollen.
     * Initialise la source avec une quantité de pollen donnée, une position sur le plateau et un nombre d'essais.
     * 
     * @param qttpolen La quantité de pollen disponible dans la source
     * @param x1       La coordonnée X de la source
     * @param y1       La coordonnée Y de la source
     * @param es       Le nombre d'essais (visites autorisées) pour cette source
     */
    public Source(int qttpolen, int x1, int y1, int es) {
        super(x1, y1); // Position de la source sur le plateau
        qtt = qttpolen; // Quantité de pollen disponible
        nbEssaie = es;  // Nombre d'essais (visites autorisées)
    }

    /**
     * Affiche la quantité de pollen disponible dans la source.
     * Cette méthode est utilisée pour représenter la source sur le plateau.
     */
    void aff() {
        System.out.print(qtt + " ");
    }

    /**
     * Retourne le type de la case, ici "source".
     * 
     * @return Le type de la case, ici "source"
     */
    public String getType() {
        return "source";
    }

    /**
     * Retourne la population des abeilles associées à cette source.
     * Dans le cas d'une source, cette méthode retourne `null` car la source n'a pas de population d'abeilles.
     * 
     * @return `null` car les sources n'ont pas de population d'abeilles
     */
    public Abeille[] getPopulation() {
        return null;
    }

    /**
     * Retourne le nombre d'essais restants pour cette source.
     * Un essai correspond à une visite autorisée de la source par une abeille.
     * 
     * @return Le nombre d'essais restants
     */
    public int GetEssaie() {
        return nbEssaie;
    }

    /**
     * Modifie le nombre d'essais restants pour cette source.
     * 
     * @param var Le nouveau nombre d'essais à définir
     */
    public void setEssaie(int var) {
        nbEssaie = var;
    }
}