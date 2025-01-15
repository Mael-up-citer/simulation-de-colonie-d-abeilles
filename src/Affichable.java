/**
 * Interface représentant un objet affichable dans la simulation.
 * Cette interface permet de définir quel type d'image ou de représentation
 * graphique un objet doit afficher lorsqu'il est rendu à l'écran.
 * 
 */
interface Affichable {

    /**
     * Méthode permettant de récupérer le type d'affichage de l'objet.
     * Le type d'affichage est lié à une image
     * 
     * @return Une chaîne de caractères représentant le type de l'objet à afficher.
     */
    String getType();
}