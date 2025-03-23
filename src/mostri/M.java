package mostri;

import java.util.Random;

import camp.Griglia;
import lombok.Data;
import personaggi.Ninja;
import personaggi.PG;

@Data
public abstract class M {
	
	protected int riga;
	protected int colonna;

	public boolean giamosso; //Per abilità da attivare una volta per turno
	
	//Variabili da chiarire per ogni tipo di mostro
		protected char identificatore;	
		protected String nome;
		protected int movimento;
		protected int salutemassima;
		protected int salute;
		protected int attacco;
		public int punteggio;
		public String nomemostro;
		public String dichiarazioneturno;
	//Fine variabili specifiche
		
	public M(char identificatore) {
		this.identificatore = identificatore;
	}
	
	//Genera un array di 2 per dare delle coordinate.
		public int[] coordinateCasuali(Griglia griglia) {
			Random rndx = new Random();
			int col = rndx.nextInt(0, griglia.getLarghezza());
			int row = rndx.nextInt(0, griglia.getAltezza());
			int[] coordinate = {row,col};
			return coordinate;
		}
	
	//Spawn di base tramite il metodo coordinateCasuali().
		public void coordinateIniziali(Griglia griglia) {
			var coordinate = coordinateCasuali(griglia);
			boolean fatto = false;
			while (!fatto){
				coordinate = coordinateCasuali(griglia);
				if (griglia.getCella(coordinate[0], coordinate[1]).vuoto == true) {
					riga = coordinate[0];
					colonna = coordinate[1];
					fatto = true;
				}
			}
		}
	
	public void muovi(Griglia griglia) {
		
	}

	public void Turno() {
		System.out.print(dichiarazioneturno);
	}

	public void messaggioStato(Griglia griglia) {
		System.out.println(String.format("%s (%d,%d): %d/%d", nome,
				riga+1, colonna+1, salute, salutemassima));	
	}
	
	public void subisceDanno(int a) {
		salute -= a;
	}

	public void vieneUcciso(Griglia griglia) {
		griglia.aggiungipunteggio(punteggio, nomemostro);
		griglia.getCella(riga, colonna).clear();
	}
	
	public PG trovaPG(Griglia griglia){
		PG pg = new Ninja();
		for (int row =0; row<griglia.getAltezza(); row++) {
			for (int col =0; col<griglia.getLarghezza(); col++) {
				if (griglia.getCella(row, col).getGiocatoreQui() != null)
					pg = griglia.getCella(row, col).getGiocatoreQui();
			}
		}
		return pg;
	}
	
	public int direzionePerInseguire(PG pg) {
		int deltaY = pg.getRiga() - riga;
		int deltaX = pg.getColonna() - colonna;
		boolean sinistra = false;
		boolean alto = false;
		if (deltaX<0) {
			sinistra = true;
			deltaX = -deltaX;
		}
		if (deltaY<0) {
			alto = true;
			deltaY = -deltaY;
		}
		
		if (deltaX<deltaY && alto) return 0;
		if (deltaX>=deltaY && sinistra) return 1;
		if (deltaX<deltaY && !alto) return 2;
		if (deltaX>=deltaY && !sinistra) return 3;
		return 5;
	}
	
	//Cura di base.
		public void Cura(int quantacura) {
			salute = salute + quantacura;
			if (salute > salutemassima)	salute = salutemassima;
		}

	public void fineTurno(Griglia griglia) {
	}
}
