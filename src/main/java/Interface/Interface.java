package Interface;

import javax.swing.*;

public class Interface {
	public static void AfficheBarreDOutils(){
		JButton nouveau=new JButton("Nouveau");
		JButton charger=new JButton("Charger");
		JButton sauvegarder=new JButton("Sauvegarder");
		JButton supprimer=new JButton("Supprimer");
		JPanel bdoHaut = new JPanel();
		bdoHaut.add(nouveau);
		bdoHaut.add(charger);
		bdoHaut.add(sauvegarder);
		bdoHaut.add(supprimer);
		bdoHaut.setSize(200, 400);
		bdoHaut.setVisible(true);
	}

};
