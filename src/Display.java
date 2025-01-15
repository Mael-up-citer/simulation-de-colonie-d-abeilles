import javax.swing.*;
import java.awt.*;

/**
 * La classe `Display` gère l'affichage de la fenêtre principale de la simulation de ruche.
 * Elle permet d'afficher la ruche, les abeilles, le fond, et les tas de pollen sur un JPanel.
 * La classe hérite de `JFrame` et utilise des `JLayeredPane` pour organiser l'affichage des éléments à différentes couches.
 * 
 */
public class Display extends JFrame {

    // Largeur et hauteur de la fenêtre
    private int w, h;

    // Tableau des labels représentant les abeilles
    private JLabel AbeilleLabel[];

    // Conteneur des éléments de l'interface, utilisé pour gérer les couches
    private JLayeredPane layeredPane = getLayeredPane();

    /**
     * Constructeur de la classe `Display`.
     * Initialise la fenêtre de simulation, définit sa taille, et prépare le tableau des abeilles.
     * 
     * @param w1 Largeur de la fenêtre en pixels
     * @param h1 Hauteur de la fenêtre en pixels
     * @param nbAbeille Nombre total d'abeilles à afficher
     */
    public Display(int w1, int h1, int nbAbeille) {
        super("Simulation de ruche");  // Crée la fenêtre avec un titre
        h = h1;
        w = w1;
        AbeilleLabel = new JLabel[nbAbeille];

        // Définir la taille de la fenêtre puis la centrer à l'écran
        this.setSize(w, h);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Utilisation du gestionnaire de disposition null (pas de gestion automatique)
        setLayout(null);

        setVisible(true);
    }

    /**
     * Affiche la ruche dans la fenêtre en ajoutant l'image correspondante.
     * L'image est placée dans le `JLayeredPane` avec la même priorité que les abeilles.
     * 
     * @param t Objet `Tableau` représentant l'état du plateau de simulation.
     */
    void affRuche(Tableau t) {
        // Ajoute l'image de la ruche au `layeredPane` à la bonne position
        layeredPane.add(addImage("images/" + t.getPlateau()[t.getYruche()][t.getXruche()].getType() + ".png", 
                                  t.getXCase() * t.getXruche(), t.getYCase() * t.getYruche(), t.getXCase(), t.getYCase()), 
                        JLayeredPane.MODAL_LAYER);
    }

    /**
     * Ajoute une image à la fenêtre à la position spécifiée avec les dimensions spécifiées.
     * 
     * @param imagePath Le chemin vers l'image à afficher.
     * @param x La position X en pixels.
     * @param y La position Y en pixels.
     * @param width La largeur de l'image en pixels.
     * @param height La hauteur de l'image en pixels.
     * @return Un `JLabel` contenant l'image redimensionnée.
     */
    private JLabel addImage(String imagePath, int x, int y, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(x, y, width, height);
        return imageLabel;
    }

    /**
     * Affiche les abeilles de la ruche à leur position respective.
     * Les abeilles de type "observatrice" sont affichées avec une taille plus petite que les autres.
     * 
     * @param r L'objet `Ruche` contenant la population d'abeilles.
     */
    void affAbeille(Ruche r) {
        String path;
        Abeille[] bees = r.getPopulation();

        for (int i = 0; i < bees.length; i++) {
            path = "images/" + bees[i].getType() + ".png";
            // Si c'est une abeille observatrice, l'affiche avec une taille spécifique
            if (bees[i].getType().equals("observatrice")) {
                AbeilleLabel[i] = addImage(path, (int) bees[i].getX(), (int) bees[i].getY(), 43, 43);
                layeredPane.add(AbeilleLabel[i], JLayeredPane.DRAG_LAYER);
            } else {
                AbeilleLabel[i] = addImage(path, (int) bees[i].getX(), (int) bees[i].getY(), 50, 50);
                layeredPane.add(AbeilleLabel[i], JLayeredPane.POPUP_LAYER);
            }
        }
    }

    /**
     * Supprime toutes les abeilles actuellement affichées dans la fenêtre.
     */
    void supAbeille() {
        for (int i = 0; i < AbeilleLabel.length; i++)
            layeredPane.remove(AbeilleLabel[i]);
        System.gc();  // Force la collecte des déchets (libération de la mémoire)
    }

    /**
     * Affiche le fond de la simulation en ajoutant une image de fond.
     */
    void affBackround() {
        layeredPane.add(addImage("images/fond.jpg", 0, 0, w, h), JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * Affiche les tas de pollen dans la fenêtre. Les tas sont dimensionnés en fonction de la quantité de pollen.
     * 
     * @param mat L'objet `Tableau` représentant le plateau de simulation.
     */
    void affPolen(Tableau mat) {
        float coef;  // Coefficient pour ajuster la taille de l'image en fonction de la quantité de pollen

        for (int i = 0; i < mat.getSize(); i++) {
            for (int j = 0; j < mat.getSize(); j++) {
                // Si la case n'est pas la ruche
                if (!mat.getPlateau()[i][j].getType().equals("ruche")) {
                    if (mat.getPlateau()[i][j].getQtt() != 0) {
                        coef = mat.getPlateau()[i][j].getQtt() / (mat.getQttpolen() / 6) + 1;
                        layeredPane.add(addImage("images/polen.png", mat.getXCase() * j, mat.getYCase() * i, 
                                                 (int) (mat.getYCase() * coef), (int) (mat.getYCase() * coef)), 
                                         JLayeredPane.MODAL_LAYER);
                    }
                }
            }
        }
    }

    /**
     * Retourne la largeur de la fenêtre.
     * 
     * @return La largeur de la fenêtre.
     */
    public int getW() {
        return w;
    }

    /**
     * Retourne la hauteur de la fenêtre.
     * 
     * @return La hauteur de la fenêtre.
     */
    public int getH() {
        return h;
    }

    /**
     * Modifie la largeur de la fenêtre.
     * 
     * @param var La nouvelle largeur de la fenêtre.
     */
    public void setW(int var) {
        w = var;
    }

    /**
     * Modifie la hauteur de la fenêtre.
     * 
     * @param var La nouvelle hauteur de la fenêtre.
     */
    public void setH(int var) {
        h = var;
    }
}