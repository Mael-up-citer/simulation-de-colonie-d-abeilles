/**
 * Classe principale de l'application représentant la simulation des abeilles et de leur interaction avec l'environnement.
 * La classe initialise et lance la simulation, en affichant les éléments nécessaires dans la fenêtre d'affichage.
 * Elle gère également l'initialisation et l'exécution des actions des abeilles, des sources de pollen et de la ruche.
 * 
 */
public class App {

    /**
     * Méthode principale pour démarrer la simulation.
     * 
     * Cette méthode crée les objets nécessaires à la simulation, attend le démarrage de l'interface utilisateur,
     * initialise la carte (Tableau), crée la fenêtre d'affichage et lance l'exécution de la simulation.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args){
        // Initialisation de l'interface utilisateur
        InterfaceSimulation interfacesimulation = new InterfaceSimulation();
        
        // Attente du démarrage de la simulation dans l'interface
        while(!interfacesimulation.getStart()) {
            try {
                Thread.sleep(100); // Attendre 100 millisecondes avant de vérifier à nouveau
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        // Paramètres de la simulation (dimensions, sources, pollen, abeilles, etc.)
        int w = 1400;
        int h = 800;
        int sizeMat = 25, nbsource = interfacesimulation.getSources(), qttpolen = interfacesimulation.getPollen();
        int nbAbeilleObservatrice = interfacesimulation.getObservatrices(), nbAbeilleEclaireuse = interfacesimulation.getEclaireuses();
        int es = interfacesimulation.getVisites();

        // Création de la carte de simulation
        Tableau map = new Tableau(sizeMat, nbsource, qttpolen, nbAbeilleObservatrice, nbAbeilleEclaireuse, h, w, es);
        
        // Création de la fenêtre d'affichage
        Display d = new Display(w, h, nbsource + nbAbeilleObservatrice + nbAbeilleEclaireuse);

        // Affichage initial des éléments : fond, ruche, abeilles, pollen
        d.affBackround();
        d.affRuche(map);
        d.affAbeille((Ruche) map.getPlateau()[map.getYruche()][map.getXruche()]);
        d.affPolen(map);

        // Lancement de la simulation
        run(d, map, nbAbeilleObservatrice + nbsource, interfacesimulation);
    }

    /**
     * Méthode exécutant la simulation en animant les actions des abeilles et de l'environnement.
     * 
     * La méthode met à jour l'état de la simulation à chaque étape, en simulant le mouvement des abeilles
     * (éclaireuses, employés, observatrices) et en affichant les changements à l'écran.
     * 
     * La simulation continue jusqu'à ce que 85% du pollen soit récolté dans la ruche.
     * 
     * @param d La fenêtre d'affichage utilisée pour afficher l'état de la simulation.
     * @param map La carte représentant l'environnement de la simulation.
     * @param nbAbeille Le nombre total d'abeilles dans la simulation (éclaireuses, employés, observatrices).
     * @param interfacesimu L'interface de simulation contenant les paramètres de la simulation.
     */
    private static void run(Display d, Tableau map, int nbAbeille, InterfaceSimulation interfacesimu){
        // Accède à la ruche et à sa population d'abeilles
        Ruche r = (Ruche) map.getPlateau()[map.getYruche()][map.getXruche()];
        Abeille[] bees = r.getPopulation();
        
        // Initialisation des éclaireuses et de la simulation
        r.goCaseEclaireuses(map);

        int max_employees = r.getNbEclaireuse(); // Nombre maximal d'employés actifs à un moment donné
        int base = max_employees + r.getNbEmployees(); // Indice de la première observatrice
        int max_observatrice = base; // Nombre maximal d'observatrices actives à un moment donné
        int cpt = 0; // Compteur pour l'activation des observatrices

        // La simulation continue jusqu'à ce que 85% du pollen soit récolté dans la ruche
        while(map.getPlateau()[map.getXruche()][map.getYruche()].getQtt() < map.getQttpolen() * 0.85){
            // Met en pause le processus pour avoir un affichage du mouvement
            try { 
                Thread.sleep(interfacesimu.getRafraichissement()); 
            } catch(InterruptedException e) {}
            
            if(!interfacesimu.getPause()){
                // Action des éclaireuses
                for(int i = 0; i < r.getNbEclaireuse(); ++i){
                    if(bees[i].mouv(map)) {
                        max_employees += 1; // Augmente le nombre d'employés
                    }
                }

                // Action des employés (qui rapportent du pollen)
                for(int i = r.getNbEclaireuse(); i < max_employees; ++i){
                    if(bees[i].mouv(map)){
                        cpt += 1;
                        // Active un certain nombre d'observatrices en fonction du nombre d'employés
                        max_observatrice = (int)((cpt * r.getNbObservatrice()) / r.getNbEmployees()) + base;
                    }
                }

                // Action des observatrices
                for(int i = r.getNbEclaireuse() + r.getNbEmployees(); i < max_observatrice; ++i){
                    bees[i].mouv(map);
                }

                // Supprimer les abeilles et réafficher leur nouvelle position
                d.supAbeille();
                d.affAbeille(r);
            }
        }
        
        // Affichage des coordonnées des sources observées
        for(int i = 0; i < Observatrices.getCoordonneesEtQualite().length; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(Observatrices.getCoordonneesEtQualite()[i][j] + "   ");
            }
            System.out.println();
        }

        // Affichage du pollen récolté et du pollen total
        System.out.print("Pollen récolté: "+ map.getPlateau()[map.getXruche()][map.getYruche()].getQtt()+"  Pollen total: " + map.getQttpolen());
    }
}