import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe représentant l'interface graphique de la simulation des abeilles.
 * Elle permet de paramétrer les valeurs nécessaires à la simulation, de démarrer celle-ci,
 * de la mettre en pause et d'afficher des erreurs en cas de saisie invalide.
 */
public class InterfaceSimulation extends JFrame {

    // Attributs de l'interface
    private JLabel labelPollen, labelSources, labelObservatrices, labelEclaireuses, 
                   labelVisites, labelRafraichissement, valeurPollen, valeurSources, 
                   valeurEclaireuses, valeurObservatrices, valeurVisites, erreurs;
    private JButton boutonDemarrer, boutonPause;
    private int pollen, sources, observatrices, eclaireuses, visites, rafraichissement;
    private boolean start, pause, invalide;
    private JSlider sourcesSlider, visitesSlider;
    private JTextField textFieldPollen, textFieldObservatrices, textFieldEclaireuses;
    private String[] optionsRafraichissement = {"x1", "x2"};
    private JComboBox<String> comboRafraichissement;

    /**
     * Constructeur de l'interface graphique.
     * Il initialise les éléments de l'interface, les valeurs par défaut et gère les événements des boutons.
     */
    public InterfaceSimulation() {
        setTitle("Paramètres de la Simulation");
        setSize(700, 300);
        setLayout(new GridLayout(8, 2));

        // Initialisation des variables de statut
        pause = false; // Statue de la mise en pause
        start = false; // Simulation démarée ou pas

        // Initialisation des labels et autres composants de l'interface
        labelPollen = new JLabel("Quantité de pollen :");
        textFieldPollen = new JTextField();
        labelSources = new JLabel("Nombre de sources de pollen :");
        sourcesSlider = new JSlider(JSlider.HORIZONTAL, 1, 40, 10);
        sourcesSlider.setMajorTickSpacing(10);
        sourcesSlider.setMinorTickSpacing(1);
        sourcesSlider.setPaintTicks(true);
        sourcesSlider.setPaintLabels(true);
        labelObservatrices = new JLabel("Nombre d'abeilles observatrices :");
        textFieldObservatrices = new JTextField();
        labelEclaireuses = new JLabel("Nombre d'abeilles éclaireuses :");
        textFieldEclaireuses = new JTextField();
        labelVisites = new JLabel("Nombre de visites possibles pour une source :");
        visitesSlider = new JSlider(JSlider.HORIZONTAL, 2, 10, 5);
        visitesSlider.setMajorTickSpacing(1);
        visitesSlider.setPaintTicks(true);
        visitesSlider.setPaintLabels(true);
        labelRafraichissement = new JLabel("Vitesse de simulation :");
        comboRafraichissement = new JComboBox<>(optionsRafraichissement);

        // Action de gestion de la vitesse de simulation
        ActionListener comboListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboRafraichissement.getSelectedItem() == "x1") {
                    rafraichissement = 60;
                } else if (comboRafraichissement.getSelectedItem() == "x2") {
                    rafraichissement = 30;
                }
            }
        };
        comboRafraichissement.addActionListener(comboListener);

        erreurs = new JLabel(""); // Label pour afficher les erreurs
        erreurs.setForeground(Color.RED);

        // Valeurs par défaut
        textFieldPollen.setText("300");
        textFieldObservatrices.setText("20");
        textFieldEclaireuses.setText("30");

        // Initialisation des boutons et gestion des actions
        boutonDemarrer = new JButton("Démarrer la Simulation");
        boutonDemarrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!start) {
                    // Récupération des valeurs saisies et gestion des erreurs
                    invalide = false;
                    try {
                        pollen = Integer.parseInt(textFieldPollen.getText());
                    } catch (NumberFormatException er) {
                        erreurs.setText("La quantité de pollen n'est pas un entier valide.");
                        invalide = true;
                    }
                    try {
                        observatrices = Integer.parseInt(textFieldObservatrices.getText());
                    } catch (NumberFormatException er) {
                        erreurs.setText("Le nombre d'observatrices n'est pas un entier valide.");
                        invalide = true;
                    }
                    try {
                        eclaireuses = Integer.parseInt(textFieldEclaireuses.getText());
                    } catch (NumberFormatException er) {
                        erreurs.setText("Le nombre d'éclaireuses n'est pas un entier valide.");
                        invalide = true;
                    }

                    if (!invalide) {
                        sources = sourcesSlider.getValue();
                        visites = visitesSlider.getValue();
                        if (comboRafraichissement.getSelectedItem() == "x1") {
                            rafraichissement = 60;
                        } else if (comboRafraichissement.getSelectedItem() == "x2") {
                            rafraichissement = 30;
                        }

                        // Vérification des quantités et lancement de la simulation
                        if (sources > pollen) {
                            erreurs.setText("Nombre de sources > quantité de pollen");
                        } else if (observatrices < 0) {
                            erreurs.setText("Nombre d'observatrices négatif");
                        } else if (eclaireuses < 0) {
                            erreurs.setText("Nombre d'éclaireuses négatif");
                        } else if ((eclaireuses + observatrices) > 120) {
                            erreurs.setText("Nombre d'observatrices > 120");
                        } else {
                            demarrerSimulation(pollen, sources, observatrices, eclaireuses, visites, rafraichissement);
                        }
                    }
                } else {
                    erreurs.setText("Simulation déjà démarrée");
                }
            }
        });

        boutonPause = new JButton("Pause");
        boutonPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (start) {
                    if (pause) {
                        pause = false;
                        erreurs.setText("Fin de la mise en pause");
                        boutonPause.setText("Pause");
                    } else {
                        pause = true;
                        erreurs.setText("Mise en pause");
                        boutonPause.setText("Reprendre");
                    }
                } else {
                    erreurs.setText("Démarrez la simulation pour mettre en pause");
                }
            }
        });

        // Ajout des composants à la fenêtre
        add(labelPollen);
        add(textFieldPollen);
        add(labelSources);
        add(sourcesSlider);
        add(labelObservatrices);
        add(textFieldObservatrices);
        add(labelEclaireuses);
        add(textFieldEclaireuses);
        add(labelVisites);
        add(visitesSlider);
        add(labelRafraichissement);
        add(comboRafraichissement);
        add(boutonDemarrer);
        add(boutonPause);
        add(erreurs);

        setVisible(true);
    }

    /**
     * Démarre la simulation avec les paramètres saisis par l'utilisateur.
     * 
     * @param pollen           La quantité de pollen dans la simulation.
     * @param sources          Le nombre de sources de pollen.
     * @param observatrices    Le nombre d'abeilles observatrices.
     * @param eclaireuses      Le nombre d'abeilles éclaireuses.
     * @param visites          Le nombre de visites possibles pour une source.
     * @param rafraichissement La vitesse de simulation.
     */
    private void demarrerSimulation(int pollen, int sources, int observatrices, int eclaireuses, int visites, int rafraichissement) {
        // Affichage des paramètres de la simulation
        System.out.println("Démarrage de la simulation avec les paramètres suivants :");
        System.out.println("Pollen : " + pollen);
        System.out.println("Sources : " + sources);
        System.out.println("Observatrices : " + observatrices);
        System.out.println("Éclaireuses : " + eclaireuses);
        System.out.println("Visites par source : " + visites);
        System.out.println("Rafraichissement : " + rafraichissement);

        setVisible(false);
        // Suppression des composants de la fenêtre
        remove(labelPollen);
        remove(textFieldPollen);
        remove(labelSources);
        remove(sourcesSlider);
        remove(labelObservatrices);
        remove(textFieldObservatrices);
        remove(labelEclaireuses);
        remove(textFieldEclaireuses);
        remove(labelVisites);
        remove(visitesSlider);
        remove(labelRafraichissement);
        remove(comboRafraichissement);
        remove(boutonDemarrer);
        remove(boutonPause);
        remove(erreurs);

        // Affichage des résultats dans l'interface
        valeurPollen = new JLabel(pollen + "");
        valeurSources = new JLabel(sources + "");
        valeurEclaireuses = new JLabel(eclaireuses + "");
        valeurObservatrices = new JLabel(observatrices + "");
        valeurVisites = new JLabel(visites + "");
        add(labelPollen);
        add(valeurPollen);
        add(labelSources);
        add(valeurSources);
        add(labelObservatrices);
        add(valeurObservatrices);
        add(labelEclaireuses);
        add(valeurEclaireuses);
        add(labelVisites);
        add(valeurVisites);
        add(labelRafraichissement);
        add(comboRafraichissement);
        add(boutonDemarrer);
        add(boutonPause);
        add(erreurs);

        erreurs.setText("Simulation démarrée");
        setVisible(true);

        start = true; // Mise à jour du statut
    }

    /**
     * Retourne la quantité de pollen saisie pour la simulation.
     * 
     * @return La quantité de pollen.
     */
    public int getPollen() {
        return pollen;
    }

    /**
     * Retourne le nombre de sources de pollen saisies pour la simulation.
     * 
     * @return Le nombre de sources de pollen.
     */
    public int getSources() {
        return sources;
    }

    /**
     * Retourne le nombre d'abeilles observatrices saisies pour la simulation.
     * 
     * @return Le nombre d'abeilles observatrices.
     */
    public int getObservatrices() {
        return observatrices;
    }

    /**
     * Retourne le nombre d'abeilles éclaireuses saisies pour la simulation.
     * 
     * @return Le nombre d'abeilles éclaireuses.
     */
    public int getEclaireuses() {
        return eclaireuses;
    }

    /**
     * Retourne le nombre de visites possibles par source saisies pour la simulation.
     * 
     * @return Le nombre de visites par source.
     */
    public int getVisites() {
        return visites;
    }

    /**
     * Retourne la vitesse de rafraîchissement de la simulation.
     * 
     * @return La vitesse de rafraîchissement.
     */
    public int getRafraichissement() {
        return rafraichissement;
    }

    /**
     * Retourne l'état de la pause de la simulation.
     * 
     * @return true si la simulation est en pause, sinon false.
     */
    public boolean getPause() {
        return pause;
    }

    /**
     * Retourne l'état de démarrage de la simulation.
     * 
     * @return true si la simulation est démarrée, sinon false.
     */
    public boolean getStart() {
        return start;
    }

    /**
     * Méthode principale pour lancer l'interface.
     * 
     * @param args Arguments passés à l'exécution du programme.
     */
    public static void main(String[] args) {
        new InterfaceSimulation(); // Affichage de l'interface
    }
}