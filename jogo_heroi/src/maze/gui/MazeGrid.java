package maze.gui;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MazeGrid extends JPanel {
	private static final long serialVersionUID = -6007221560664604069L;
	private ImageIcon dragon;
	private ImageIcon sleepingDragon;
	private ImageIcon hero;
	private ImageIcon armedHero;
	private ImageIcon shieldedHero;
	private ImageIcon armedAndShieldedHero;
	private ImageIcon wall;
	private ImageIcon openedExit;
	private ImageIcon closedExit;
	private ImageIcon dard;
	private ImageIcon shield;
	@SuppressWarnings("unused")
	private ImageIcon fire;
	private ImageIcon sword;
	
	MazeGrid() {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//Varible for Panes size
		int paneSize = 1;
		if (MazeDisplay.getJogoG().getLabirinto().getSize() > 0) paneSize = MazeDisplay.getJogoG().getLabirinto().getSize();
		//Divide a frame numa grelha com o tamanho correto (layout)
		this.setLayout(new GridLayout(paneSize, paneSize));
		loadImages();
	}
	
	private ImageIcon scaleImage(ImageIcon im) {
		Image img = im.getImage();
		Image newimg = img.getScaledInstance(this.getWidth()/MazeDisplay.getJogoG().getLabirinto().getSize(),
				this.getHeight()/MazeDisplay.getJogoG().getLabirinto().getSize(),
				java.awt.Image.SCALE_FAST);
		return new ImageIcon(newimg);
	}
	
	private void loadImages() {
		dragon = new ImageIcon(this.getClass().getResource("res/dragon.png"));
		sleepingDragon = new ImageIcon(this.getClass().getResource("res/sleepingDragon.png"));
		hero = new ImageIcon(this.getClass().getResource("res/hero.png"));
		armedHero = new ImageIcon(this.getClass().getResource("res/armedHero.png"));
		shieldedHero = new ImageIcon(this.getClass().getResource("res/shieldedHero.png"));
		armedAndShieldedHero = new ImageIcon(this.getClass().getResource("res/armedAndShieldedHero.png"));
		wall = new ImageIcon(this.getClass().getResource("res/wall.png"));
		dard = new ImageIcon(this.getClass().getResource("res/dard.png"));
		shield = new ImageIcon(this.getClass().getResource("res/shield.png"));
		fire = new ImageIcon(this.getClass().getResource("res/fire.png"));
		sword = new ImageIcon(this.getClass().getResource("res/sword.png"));
		openedExit = new ImageIcon(this.getClass().getResource("res/openedExit.png")); 
		closedExit = new ImageIcon(this.getClass().getResource("res/closedExit.png")); 
	}
	
	private void introduzConteudo() {
		ImageIcon scaledDragon = scaleImage(dragon);
		ImageIcon scaledSleepingDragon = scaleImage(sleepingDragon);
		ImageIcon scaledHero = scaleImage(hero);
		ImageIcon scaledArmedHero = scaleImage(armedHero);
		ImageIcon scaledShieldedHero = scaleImage(shieldedHero);
		ImageIcon scaledArmedAndShieldedHero = scaleImage(armedAndShieldedHero);
		ImageIcon scaledWall = scaleImage(wall);
		ImageIcon scaledOpenedExit = scaleImage(openedExit);
		ImageIcon scaledClosedExit = scaleImage(closedExit);
		ImageIcon scaledDard = scaleImage(dard);
		ImageIcon scaledShield = scaleImage(shield);
		//ImageIcon scaledFire = scaleImage(fire);---------------------------------AINDA PARA USAR
		ImageIcon scaledSword = scaleImage(sword);
		
		for (int i = 0; i < MazeDisplay.getJogoG().getLabirinto().getSize(); i++) {
			for (int j = 0; j < MazeDisplay.getJogoG().getLabirinto().getSize(); j++) {
				if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'X')
					this.add(new JLabel(scaledWall));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == ' ')
					this.add(new JLabel(" "));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'A' && !MazeDisplay.getJogoG().getHeroi().isShielded())
					this.add(new JLabel(scaledArmedHero));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'A' && MazeDisplay.getJogoG().getHeroi().isShielded()) 
					this.add(new JLabel(scaledArmedAndShieldedHero));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'H' && !MazeDisplay.getJogoG().getHeroi().isShielded())
					this.add(new JLabel(scaledHero));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'H' && MazeDisplay.getJogoG().getHeroi().isShielded())
					this.add(new JLabel(scaledShieldedHero));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'D')
					this.add(new JLabel(scaledDragon));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'd') 
					this.add(new JLabel(scaledSleepingDragon));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == '\\')
					this.add(new JLabel(scaledDard));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'E')
					this.add(new JLabel(scaledSword));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'S') {
					if (MazeDisplay.getJogoG().checkIfDragonsAreAlive())
						this.add(new JLabel(scaledClosedExit));
					else
						this.add(new JLabel(scaledOpenedExit));
				}
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'O')
					this.add(new JLabel(scaledShield));
				else if (MazeDisplay.getJogoG().getLabirinto().getLab()[j][i] == 'F')
					this.add(new JLabel("F - MUDAR"));
				
			
				//--------------------------------FALTA-O-FOGO----------------------------------
			}
		}
	}
	
	public void game() {
		//Remove all content
		removeAll();
		//Repaints the content
		repaint();
		//Reintroduces the correct content
		introduzConteudo();
		//Valida novamente o conteudo
		revalidate();
	}
}


