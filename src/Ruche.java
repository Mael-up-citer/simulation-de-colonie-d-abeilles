/**
 * Représente une ruche dans le système de simulation.
 * La ruche contient des abeilles de différents types : éclaireuses, employées et observatrices.
 * Les abeilles peuvent être mobilisées pour différentes tâches, notamment la recherche de pollen et la gestion de la population de la ruche.
 */
public class Ruche extends Case {

    // Tableau contenant toutes les abeilles de la ruche
    private Abeille[] population;

    // Nombre d'éclaireuses dans la ruche
    private int nbEclaireuse;

    // Nombre d'employées dans la ruche
    private int nbEmployees;

    // Nombre d'observatrices dans la ruche
    private int nbObservatrice;

    // Indices des abeilles qui seront mobilisées par les éclaireuses et observatrices
    private static int IndiceEmployees = 0;
    private static int IndiceEclaireuse = 0;

    /**
     * Constructeur de la ruche.
     * Initialise la ruche avec le nombre d'éclaireuses, d'employées et d'observatrices spécifiés, ainsi que leurs positions.
     * Crée les différentes abeilles et les ajoute à la population de la ruche.
     * 
     * @param nbEclaireuse   Le nombre d'éclaireuses dans la ruche
     * @param nbEmployees    Le nombre d'employées dans la ruche
     * @param nbObservatrice Le nombre d'observatrices dans la ruche
     * @param x1             La coordonnée X de la ruche
     * @param y1             La coordonnée Y de la ruche
     */
    Ruche(int nbEclaireuse, int nbEmployees, int nbObservatrice, int x1, int y1) {
        super(x1, y1); // Position de la ruche sur le plateau
        this.nbEmployees = nbEmployees;
        this.nbEclaireuse = nbEclaireuse;
        this.nbObservatrice = nbObservatrice;

        // Initialisation de l'indice des employés
        if (Ruche.IndiceEmployees == 0) {
            Ruche.IndiceEmployees = nbEclaireuse;
        }

        // Création du tableau contenant toutes les abeilles
        population = new Abeille[nbEclaireuse + nbEmployees + nbObservatrice];

        // Création des abeilles éclaireuses
        for (int i = 0; i < nbEclaireuse; i++) {
            Eclaireuses e = new Eclaireuses(x1, y1, 0, 0);
            population[i] = e;
        }

        // Création des abeilles employées
        for (int i = nbEclaireuse; i < nbEmployees + nbEclaireuse; i++) {
            Employees em = new Employees(x1, y1, 0, 0);
            population[i] = em;
        }

        // Création des abeilles observatrices
        for (int i = nbEmployees + nbEclaireuse; i < nbEmployees + nbEclaireuse + nbObservatrice; i++) {
            Observatrices ob = new Observatrices(x1, y1, 0, 0, nbEmployees);
            population[i] = ob;
        }
    }

    /**
     * Affiche l'identifiant de la ruche.
     * Utilisé pour représenter la ruche sur le plateau.
     */
    void aff() {
        System.out.print("R ");
    }

    /**
     * Retourne la quantité associée à la ruche.
     * Dans ce cas, cette méthode retourne une valeur par défaut, car les ruches n'ont pas de quantité définie.
     * 
     * @return -1 (valeur par défaut)
     */
    public int getQtt() {
        return -1;
    }

    /**
     * Modifie la quantité associée à la ruche.
     * Cette méthode ne fait rien pour la ruche, car elle ne possède pas de quantité définie.
     * 
     * @param n La nouvelle quantité à définir
     */
    public void setQtt(int n) {
        System.err.println("Set qtt dans ruche");
    }

    /**
     * Retourne le type de la case, ici "ruche".
     * 
     * @return Le type de la case, ici "ruche"
     */
    public String getType() {
        return "ruche";
    }

    /**
     * Déplace toutes les éclaireuses vers de nouvelles positions aléatoires sur le plateau.
     * 
     * @param tab L'objet Tableau représentant le plateau de simulation
     */
    void goCaseEclaireuses(Tableau tab) {
        int x, y;

        for (int i = 0; i < nbEclaireuse; i++) {
            x = (int) (Math.random() * tab.getSize() * tab.getXCase());
            y = (int) (Math.random() * tab.getSize() * tab.getYCase());
            population[i].goCase(x, y);
        }
    }

    /**
     * Retourne le tableau des abeilles de la ruche.
     * 
     * @return Le tableau des abeilles de la ruche
     */
    public Abeille[] getPopulation() {
        return population;
    }

    /**
     * Retourne le nombre d'éclaireuses dans la ruche.
     * 
     * @return Le nombre d'éclaireuses
     */
    public int getNbEclaireuse() {
        return nbEclaireuse;
    }

    /**
     * Retourne le nombre d'employées dans la ruche.
     * 
     * @return Le nombre d'employées
     */
    public int getNbEmployees() {
        return nbEmployees;
    }

    /**
     * Retourne le nombre d'observatrices dans la ruche.
     * 
     * @return Le nombre d'observatrices
     */
    public int getNbObservatrice() {
        return nbObservatrice;
    }

    /**
     * Retourne l'indice actuel des employés dans la ruche.
     * 
     * @return L'indice des employés
     */
    public static int getIndiceEmployees() {
        return IndiceEmployees;
    }

    /**
     * Retourne l'indice actuel des éclaireuses dans la ruche.
     * 
     * @return L'indice des éclaireuses
     */
    public static int getIndiceEclaireuse() {
        return IndiceEclaireuse;
    }

    /**
     * Modifie l'indice des employés dans la ruche.
     * 
     * @param var Le nouvel indice des employés
     */
    public static void setIndiceEmployees(int var) {
        Ruche.IndiceEmployees = var;
    }

    /**
     * Modifie l'indice des éclaireuses dans la ruche.
     * 
     * @param var Le nouvel indice des éclaireuses
     */
    public static void setIndiceEclaireuse(int var) {
        Ruche.IndiceEclaireuse = var;
    }

    /**
     * Modifie la population de la ruche.
     * 
     * @param var Le nouveau tableau des abeilles de la ruche
     */
    public void setPopulation(Abeille[] var) {
        population = var;
    }

    /**
     * Modifie le nombre d'éclaireuses dans la ruche.
     * 
     * @param var Le nouveau nombre d'éclaireuses
     */
    public void setNbEclaireuse(int var) {
        nbEclaireuse = var;
    }

    /**
     * Modifie le nombre d'employées dans la ruche.
     * 
     * @param var Le nouveau nombre d'employées
     */
    public void setNbEmployees(int var) {
        nbEmployees = var;
    }

    /**
     * Modifie le nombre d'observatrices dans la ruche.
     * 
     * @param var Le nouveau nombre d'observatrices
     */
    public void setNbObservatrice(int var) {
        nbObservatrice = var;
    }
}