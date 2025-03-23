package camp;


import lombok.Data;
import mostri.M;
import personaggi.PG;

@Data
public class Griglia {

	private int altezza;
	private int larghezza;
	private final Elemento[][] celle;
	private int punteggio;

	public Griglia(int altezza, int larghezza) {
		this.altezza = altezza;
		this.larghezza = larghezza;	

		celle = new Elemento[altezza][];
		for (int row = 0; row < altezza; row++) {
			celle[row] = new Elemento[larghezza];
			for (int col = 0; col<larghezza; col ++) {
				Elemento vuoto = new Elemento ('□');
				celle [row][col] = vuoto;
				vuoto.clear();
			}
		}
   }
	
	public void stampaGriglia() {
		
		System.out.print("   ");
		for (int i=0; i<larghezza; i++) {
			int a =(i+1)/10;
			if (a != 0) System.out.print((i+1)/10); else System.out.print(" ");
			System.out.print(" ");
		}
		System.out.println("");
		
		System.out.print("   ");
		for (int i=0; i<larghezza; i++) {
			System.out.print((i+1)%10);
			System.out.print(" ");
		}
		System.out.println("");
		
		for (int row=0; row<getAltezza(); row++) {
			if (row<9)
				System.out.print(" ");
			System.out.print(row+1);
			System.out.print(" ");
			
			for (int col=0; col<getLarghezza(); col++) {
				System.out.print(getCella(row,col).getIdentificatore());
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	public Elemento getCella(int riga, int colonna) {
		return celle[riga][colonna];
	}
	
	public void setCella(PG pg) {
		celle[pg.getRiga()][pg.getColonna()].setGiocatoreQui(pg);
		celle[pg.getRiga()][pg.getColonna()].setIdentificatore(pg.getIdentificatore());
		celle[pg.getRiga()][pg.getColonna()].setVuoto(false);
	}
	
	public void setCella(M m) {
		celle[m.getRiga()][m.getColonna()].setMostroQui(m);
		celle[m.getRiga()][m.getColonna()].setIdentificatore(m.getIdentificatore());
		celle[m.getRiga()][m.getColonna()].setVuoto(false);
		celle[m.getRiga()][m.getColonna()].setMostro(true);
	}
	
	public void aggiungipunteggio(int punteggio, String nomemostro) {
		this.punteggio += punteggio;
		System.out.println(String.format("Hai ucciso %s.%n+%d punti!", nomemostro, punteggio));		
	}
}