package mostri;

import java.util.ArrayList;
import java.util.List;

import camp.Griglia;

public class TiramosauroRex extends M{
	
	boolean attaccherà;

	public TiramosauroRex() {
		super('Ψ');
		salutemassima = 20;
		salute = salutemassima;
		attacco = 100;
		punteggio = 500;
		nomemostro = "un Tiramosauro Rex";
		dichiarazioneturno = "Turno dell'albero sospetto";
	}
	
	//Il tiramosauro rex può apparire solo ad almeno 2 caselle di distanza da tutti i confini.
	public void coordinateIniziali(Griglia griglia) {
		boolean fatto = false;
		while (!fatto) {
			var coordinate = coordinateCasuali(griglia);
			if(griglia.getCella(coordinate[0], coordinate[1]).vuoto == true
				&& coordinate[0]<griglia.getAltezza()-2
				&& coordinate[0]>1
				&& coordinate[1]<griglia.getLarghezza()-2
				&& coordinate[1]>1){
				riga = coordinate[0];
				colonna = coordinate[1];
				mostraAOE(griglia);
				fatto = true;
			}
		}	
	}
	
	public List<int[]> AOE(){
		List<int[]> AOE = new ArrayList<int[]>();
		int[] c1 = {riga-1,colonna-2};
		int[] c2 = {riga,colonna-2};
		int[] c3 = {riga+1,colonna-2};
		int[] c4 = {riga,colonna-1};
		int[] c5 = {riga-2,colonna-1};
		int[] c6 = {riga-2,colonna};
		int[] c7 = {riga-2,colonna+1};
		int[] c8 = {riga-1,colonna};
		int[] c9 = {riga-1,colonna+2};
		int[] c10 = {riga,colonna+2};
		int[] c11 = {riga+1,colonna+2};
		int[] c12 = {riga,colonna+1};
		int[] c13 = {riga+2,colonna-1};
		int[] c14 = {riga+2,colonna};
		int[] c15 = {riga+2,colonna+1};
		int[] c16 = {riga+1,colonna};
		AOE.add(c1); AOE.add(c2); AOE.add(c3); AOE.add(c4);
		AOE.add(c5); AOE.add(c6); AOE.add(c7); AOE.add(c8);
		AOE.add(c9); AOE.add(c10); AOE.add(c11); AOE.add(c12);
		AOE.add(c13); AOE.add(c14); AOE.add(c15); AOE.add(c16);
		return AOE;
	}
	
	public void mostraAOE(Griglia griglia) {
		var AOE = AOE();
		for (int i=0; i<AOE.size();i++) {
			int[] considera = AOE.get(i);
			griglia.getCella(considera[0], considera[1]).trex = true;
			if (griglia.getCella(considera[0], considera[1]).vuoto) {
				griglia.getCella(considera[0], considera[1]).clear();
			}
		}
	}

	@Override
	public void messaggioStato(Griglia griglia) {
		var AOE = AOE();
		attaccherà = false;
		for (int i=0; i<AOE.size();i++) {
			int[] considera = AOE.get(i);
			if (griglia.getCella(considera[0], considera[1]).vuoto == false) {
				attaccherà = true;
			}
		}
		if (attaccherà) System.out.println(String.format("L'albero sospetto in %d,%d%n"
										+ "sembra pregustare lo spuntino...%n", riga+1, colonna+1));
		else System.out.println(String.format("L'albero sospetto in %d,%d riposa.%n",
				riga+1, colonna+1));
	}
	
	@Override
	public void muovi(Griglia griglia) {
		var AOE = AOE();
		for (int i=0; i<AOE.size();i++) {
			int[] considera = AOE.get(i);
			if (griglia.getCella(considera[0], considera[1]).vuoto == false) {
				if (griglia.getCella(considera[0], considera[1]).mostro == true) {
					System.out.println(String.format("%nIl Tiramosauro Rex%n"
							+ "trova il mostro in %d,%d%ne ne fa il suo spuntino!%n",
							considera[0]+1, considera[1]+1));
					griglia.getCella(considera[0], considera[1]).getMostroQui().setSalute(0);
					griglia.getCella(considera[0], considera[1]).clear();
					giamosso = true;
				}
				else {
					System.out.println(String.format("Il Tiramosauro Rex%n"
													+"fa di te il suo spuntino!%n"
													+"Fine della partita!"));
						 System.exit(0);
				} 
			}
		}
		//Sfruttiamo il booleano giamosso, normalmente inutile per un elemento fermo,
		//per determinare se questo messaggio è già stato mandato.
		if (!giamosso)
			System.out.println(String.format("L'albero sospetto%nrimane a bocca asciutta..."));		
	}
	
	public void vieneUcciso(Griglia griglia) {
		griglia.aggiungipunteggio(punteggio, nomemostro);
		griglia.getCella(riga, colonna).clear();
		var AOE = AOE();
		for (int i=0; i<AOE.size();i++) {
			int[] considera = AOE.get(i);
			griglia.getCella(considera[0], considera[1]).trex = false;
			if (griglia.getCella(considera[0], considera[1]).vuoto) {
				griglia.getCella(considera[0], considera[1]).clear();
			}
		}
	}
}