package camp;


import lombok.Data;

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
				Elemento vuoto = new Elemento (row, col, '□', false);
				celle [row][col] = vuoto;
				vuoto.clear();
			}
		}
   }
	
	//Fa sì che la griglia cambi un proprio elemento
	//in base alle variabili riga e colonna dell'elemento in input.
	//Usato solo per inserire un nuovo elemento nella griglia di gioco.
	public void setCella(Elemento ele) {
		celle[ele.getRiga()][ele.getColonna()]= ele;
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
	
	public void aggiungipunteggio(Elemento ele) {
		if (ele.getIdentificatore() == '•') {
		punteggio += 50;
		System.out.println("Hai ucciso una Noce Moscata.");
		System.out.println("+50 punti!");
		}
		
		if (ele.getIdentificatore() == '♧') {
		punteggio += 100;	
		System.out.println("Hai ucciso un Anabroccolo.");
		System.out.println("+100 punti!");
		}
		
		if (ele.getIdentificatore() == '♣') {
		punteggio += 300;	
		System.out.println("Hai ucciso un Anabroccolo adulto.");
		System.out.println("+300 punti!");
		}
		
		if (ele.getIdentificatore() == 'Ψ') {
		punteggio += 500;	
		System.out.println("Hai ucciso un Tiramosauro Rex.");
		System.out.println("+500 punti!");
		}
	}
}
