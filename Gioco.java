package camp;

import java.io.IOException;
import java.util.Random;

public class Gioco {
	
	Griglia griglia = new Griglia(11,11);
	boolean vittoria = false;
	
	//Semplice funzione per lasciare che il giocatore continui quando è pronto.
	//Di fatto non fa nulla.
	public void continua() {
		Input input = new Input();
		System.out.print("Premi 's' per continuare.");
		while(input.getInput() != 's') {
			try {
				int a;
				a = System.in.read();
				if (a == 's') 
					input.s();
					
			} catch (IOException e) {
			}
		}
	}
	
	//Elenca le scelte per il personaggio, prende in input una scelta,
	//e ritorna un nuovo elemento della classe scelta.
	//Aggiornabile con nuovi personaggi giocabili.
	public Elemento selezionePersonaggio() {
		final Cacciatore c = new Cacciatore();
		final Ninja n = new Ninja();
		Elemento scelta = new Elemento(1,1, '?', false);
		System.out.print(String.format("Benvenuto!%n"
										+ "Scegli un personaggio digitando%n"
										+ "il numero corrispondente.%n%n"
										+ "1. Cacciatore%n%s%n%n"
										+ "2. Ninja%n%s%n%n",
										c.presentazione(),n.presentazione()));
		
		// Prende in input un numero per scegliere la classe.
		// Facendo stampare il numero preso in input risulta che esso viene aumentato di 48.
		// Non so perché avviene questo, ma compenso facendo controllare (a-48) invece di a.
		try {
			int a;
			a = System.in.read();
			if (a-48 == 1)
				scelta = scelta.sceltaCacciatore();
			if (a-48 == 2)
				scelta = scelta.sceltaNinja();
		} catch (IOException e) {
		}	
		return scelta;
	}

 	public Elemento movimentoGiocatore(Elemento g1, TiramosauroRex Trex) {
 		int salvamovimento = g1.getMovimento();
			while (g1.getMovimento() != 0) {
				Trex.mostraAOE(griglia);
				griglia.stampaGriglia();
				statoGioco(g1, Trex);
				System.out.println(String.format("%s mosse rimaste.%nEffettua un movimento (wasd):",
												g1.getMovimento()));
				g1 = g1.muovi(griglia);
			}
		Trex.mostraAOE(griglia);
		g1.setMovimento(salvamovimento);
		griglia.stampaGriglia();
		return g1;
 	}
	
 	
	public void start() {
		var g1 = selezionePersonaggio();
		g1.setRiga(griglia.getAltezza()/2);
		g1.setColonna(griglia.getLarghezza()/2);
		griglia.setCella(g1);
		griglia.stampaGriglia();
		System.out.println(String.format("Tu sei il simbolo %s al centro!", g1.getIdentificatore()));
		
		continua();
		
		System.out.println("Generazione mostri...");
	//generazione mostri
		var nm1 = new NoceMoscata (0,0);
		var nm2 = new NoceMoscata (0,0);
		var nm3 = new NoceMoscata (0,0);
		var nm4 = new NoceMoscata (0,0);
		var tr = new TiramosauroRex (0,0);
		var ab1 = new Anabroccolo (0,0);
		var ab2 = new Anabroccolo (0,0);

		nm1.coordinateIniziali(griglia);
		nm2.coordinateIniziali(griglia);
		nm3.coordinateIniziali(griglia);
		nm4.coordinateIniziali(griglia);
		tr.coordinateIniziali(griglia);
		ab2.coordinateIniziali(griglia);
		ab2.coordinateIniziali(griglia);
		
		griglia.setCella(nm1);
		griglia.setCella(nm2);
		griglia.setCella(nm3);
		griglia.setCella(nm4);
		griglia.setCella(tr);
		griglia.setCella(ab1);
		griglia.setCella(ab2);
	//fine generazione mostri
		
//Inizio while di game loop.
	while (!vittoria) {
		
		for(int row = 0; row < griglia.getAltezza(); row++) {
			for(int col = 0; col<griglia.getLarghezza(); col++) {
				griglia.getCella(row, col).setGiamosso(false);
			}
		}
		
		g1 = movimentoGiocatore(g1, tr);
		controllaVittoria();
		
		System.out.println("Mosse finite.");
		System.out.println("Ora si muovono i mostri!");
		continua();
		
		//Tutte le Noci Moscate si muovono.
		for(int row = 0; row < griglia.getAltezza(); row++) {
			for(int col = 0; col<griglia.getLarghezza(); col++) {
			//Quando trovi una Noce Moscata che non si è ancora mossa...
				if (griglia.getCella(row, col).getIdentificatore() =='•'
						&& griglia.getCella(row, col).giamosso == false) {
					var nocemoscata = griglia.getCella(row, col);
					muoviNoceMoscata(nocemoscata);
				}			
			}
		}
		//Fine movimento della Noce Moscata	
		
		//Tutti gli anabroccoli si muovono.
		for(int row = 0; row < griglia.getAltezza(); row++) {
			for(int col = 0; col<griglia.getLarghezza(); col++) {
			//Quando trovi un anabroccolo che non si è ancora mosso...
				if ((griglia.getCella(row, col).getIdentificatore() =='♧'
					||griglia.getCella(row, col).getIdentificatore() =='♣')
					&& griglia.getCella(row, col).giamosso == false) {
					var anabroccolo = griglia.getCella(row, col);
					muoviAnabroccolo(anabroccolo, g1);
				}		
			}
		}
		//Fine movimento dell'Anabroccolo	
		
		//Il tiramosauro rex si muove.
		for(int row = 0; row < griglia.getAltezza(); row++) {
			for(int col = 0; col<griglia.getLarghezza(); col++) {	
			//Quando trovi il Tiramosauro Rex...
				if (griglia.getCella(row, col).getIdentificatore() =='Ψ') {
					tr.Divora(griglia);
					continua();
				}	
			}
		}
		//Fine movimento del Tiramosauro Rex
	}
//Fine while di game loop.
	}
	
	//Fa terminare il gioco con una vittoria
	//se non ci sono più mostri.
	public void controllaVittoria(){
		vittoria = true;
		for(int row = 0; row < griglia.getAltezza(); row++) {
			for(int col = 0; col<griglia.getLarghezza(); col++) {
				if (griglia.getCella(row, col).mostro == true)
					vittoria = false;
			}
		}
		if (vittoria) {
			System.out.println(String.format("Complimenti! Hai sconfitto%ntutti i mostri!%n"
					+ "Punteggio: %d", griglia.getPunteggio()));
			System.exit(0);
		}
	}
	
	
	public void statoGioco(Elemento g1, TiramosauroRex Trex) {
		int counter =0;
		for(int row = 0; row < griglia.getAltezza(); row++) {
			for(int col = 0; col<griglia.getLarghezza(); col++) {
				if (griglia.getCella(row, col).mostro == true)
					counter++;
			}
		}
		Trex.messaggioAOE(griglia);
		System.out.println(String.format("Punteggio: %d%nSalute: %d/%d%nCi sono ancora %d mostri.%n",
										griglia.getPunteggio(),
										g1.getSalute(), g1.getSalutemassima(), counter));
	}
	
	//Funzione che fa muovere una noce moscata 3 volte.
	//Genera una direzione casuale in cui farla muovere
	//e richiama la funzione muoviNoceMoscata dalla classe Elemento.
	public void muoviNoceMoscata(Elemento nocemoscata) {
		//Scelta di una direzione casuale e movimento di una Noce Moscata
			Random rnd = new Random();
			int direzione = rnd.nextInt(0,3);
			//(Si muove 3 volte)
			for(int i=0; i<3; i++)
				nocemoscata = nocemoscata.muoviNoceMoscata(griglia, direzione);
			//Dici che questa Noce si è già mossa e mostra il movimento finale
			nocemoscata.setGiamosso(true);
			griglia.stampaGriglia();
			continua();
	}

	public int direzionePerInseguire(Elemento inseguitore, Elemento g1) {
		int direzione = 4;
		int deltaX =  g1.getColonna() - inseguitore.getColonna();
		int deltaY = g1.getRiga() - inseguitore.getRiga();
		if (deltaY >= deltaX) {
            if (deltaY < 0) {
                direzione = 0; // Muovi verso l'alto
            } else if (deltaY > 0) {
                direzione = 2; // Muovi verso il basso
            } else {
                // Se le righe coincidono, muove orizzontalmente
                if (deltaX < 0)
                    direzione = 1; // Sinistra
                else
                    direzione = 3; // Destra
            }
        } else {
            if (deltaX < 0)
                direzione = 1; // Sinistra
            else
                direzione = 3; // Destra
        }
		return direzione;
	}
	
	public void crescitaAnabroccolo(Elemento anabroccolo) {
		if (anabroccolo.getIdentificatore() == '♧') {
			anabroccolo.setSalutemassima(anabroccolo.getSalutemassima()+2);
			anabroccolo.Cura(2);
			anabroccolo.setMovimento(anabroccolo.getMovimento()+1);
			anabroccolo.setAttacco(anabroccolo.getAttacco()+1);
			System.out.println(String.format("L'anabroccolo in %d,%d cresce%n"
					+ "e ottiene +2 salute massima e salute,%n"
					+ "+1 movimento e +1 attacco!",
					anabroccolo.getRiga()+1, anabroccolo.getColonna()+1));
			if (anabroccolo.getMovimento() == 5) {
				anabroccolo.setIdentificatore('♣');
				System.out.println(String.format("L'anabroccolo è diventato adulto!"));
			}
		}
		
	}
	
	public void muoviAnabroccolo(Elemento anabroccolo, Elemento g1) {
		int direzione = direzionePerInseguire(anabroccolo, g1);
		for (int i=0; i<anabroccolo.getMovimento(); i++) {
			anabroccolo = anabroccolo.muoviAnabroccolo(griglia, direzione);
			direzione = direzionePerInseguire(anabroccolo, g1);
		}
		anabroccolo.setGiamosso(true);
		crescitaAnabroccolo(anabroccolo);
		griglia.stampaGriglia();
		continua();
	}

}