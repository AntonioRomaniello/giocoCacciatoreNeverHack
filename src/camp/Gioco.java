package camp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

import mostri.Anabroccolo;
import mostri.M;
import mostri.NoceMoscata;
import mostri.TiramosauroRex;
import personaggi.Cacciatore;
import personaggi.Gladiatore;
import personaggi.Ninja;
import personaggi.PG;

@Data
public class Gioco {
	
	Griglia griglia = new Griglia(11,11);
	boolean vittoria = false;
	List<PG> PG = new ArrayList<PG>();
	List<M> M = new ArrayList<M>();
	
	public Gioco () {
		
		final Cacciatore c = new Cacciatore();
		final Ninja n = new Ninja();
		final Gladiatore g = new Gladiatore();
		PG.add(c); PG.add(n); PG.add(g);
		
		final NoceMoscata nm1 = new NoceMoscata();
		final NoceMoscata nm2 = new NoceMoscata();
		final NoceMoscata nm3 = new NoceMoscata();

		final Anabroccolo ab1 = new Anabroccolo();
		final Anabroccolo ab2 = new Anabroccolo();
		
		final TiramosauroRex tr = new TiramosauroRex();
		M.add(nm1); M.add(nm2); M.add(nm3);
		M.add(ab1); M.add(ab2); M.add(tr);
	}
	
	//Semplice funzione per aspettare il giocatore.
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
	
	public PG selezionePersonaggio() {
		int scelta = 49;
		System.out.print(String.format("Benvenuto!%n"
										+ "Scegli un personaggio digitando%n"
										+ "il numero corrispondente.%n%n"));
		
		for (int i=0; i<PG.size(); i++)
			PG.get(i).presentazione();
		
		System.out.println(""); System.out.println("");
		
		// Prende in input un numero per scegliere la classe.
		// Facendo stampare il numero preso in input risulta che esso viene aumentato di 48.
		// Non so perché avviene questo, ma compenso facendo controllare (a-48) invece di a.
		try {
			scelta = System.in.read();
		} catch (IOException e) {
		}	
		return PG.get(scelta-49);
	}
	
	public PG start() {
 		var g1 = selezionePersonaggio();
		g1.setRiga(griglia.getAltezza()/2);
		g1.setColonna(griglia.getLarghezza()/2);
		griglia.setCella(g1);
		griglia.stampaGriglia();
		System.out.println(String.format("Tu sei il simbolo %s al centro!%n"
										+"Generazione mostri...", g1.getIdentificatore()));
		continua();
		return g1;
 	}

 	public void movimentoGiocatore(PG g1) {
 		System.out.println("Tocca a te.");
 		continua();
 		int salvamovimento = g1.getMovimento();
			while (g1.getMovimento() != 0) {
				System.out.println("");
				statoGioco(g1);
				griglia.stampaGriglia();
				System.out.println(String.format("%d mosse rimaste.%nEffettua un movimento (wasd):",
												g1.getMovimento()));
				g1.muovi(griglia);
				griglia.setCella(g1);
			}
		g1.setMovimento(salvamovimento);
		griglia.stampaGriglia();
 	}
 	
	public void NuovaPartita() {
		var g1 = start();
	//generazione mostri
		for (int i=0; i<M.size(); i++) {
			M.get(i).coordinateIniziali(griglia);
			griglia.setCella(M.get(i));
		}
		
	//fine generazione mostri
		griglia.stampaGriglia();
	//Eventuali effetti di inizio partita
		g1.inizioGioco(this);
	//Fine effetti di inizio partita
	//Inizio while di game loop.
		while (!vittoria) {
		
			for(int i=0; i<M.size(); i++) {
				M.get(i).setGiamosso(false);
				g1.setGiamosso(false);
			}
		//Movimento del giocatore.	
			movimentoGiocatore(g1);
			controllaVittoria();
		
		//Movimento dei mostri.
			System.out.println("Mosse finite.");
			System.out.println("Ora si muovono i mostri!");
			for (int i=0; i<M.size(); i++) {
				if (M.get(i).getSalute()>0) {
				//Dichiarazione del turno
					System.out.println("");
					M.get(i).Turno();
					System.out.println(String.format("%nin %d,%d.%n",
						M.get(i).getRiga()+1, M.get(i).getColonna()+1));
				//Fine dichiarazione del turno
					continua();
					System.out.println("");
				
					M.get(i).muovi(griglia);
					griglia.setCella(M.get(i));
					griglia.stampaGriglia();
				}
			}
			System.out.println("");
			System.out.println("I mostri hanno finito di muoversi.");
			continua();
		//Eventuali effetti che avvengono alla fine del turno
			g1.fineTurno();
			
			for (int i=0; i<M.size(); i++) {
				if (M.get(i).getSalute()>0) {
					M.get(i).fineTurno(griglia);
					System.out.println("");
				}
			}
		//Fine effetti di fine turno	
		}
	//Fine while di game loop.
	}
	
	//Fa terminare il gioco con una vittoria
	//se non ci sono più mostri.
	public void controllaVittoria(){
		vittoria = true;
		for(int i=0; i<M.size(); i++) {
			if (M.get(i).getSalute() > 0)
					vittoria = false;
			}
		if (vittoria) {
			System.out.println(String.format("Complimenti! Hai sconfitto%ntutti i mostri!%n"
					+ "Punteggio: %d", griglia.getPunteggio()));
			System.exit(0);
		}
	}
	
	public void statoGioco(PG g1) {
		int counter =0;
		for(int i=0; i<M.size(); i++) {
			if (M.get(i).getSalute() > 0)
					counter++;
			}
		System.out.print(String.format("Punteggio: %d%nSalute: %d/%d%n%nMostri rimasti (%d):%n",
										griglia.getPunteggio(),
										g1.getSalute(), g1.getSalutemassima(), counter));
		for(int i=0; i<M.size(); i++){
			if (M.get(i).getSalute()>0) {
				System.out.print("- ");
				M.get(i).messaggioStato(griglia);
			}
		}
	}
}