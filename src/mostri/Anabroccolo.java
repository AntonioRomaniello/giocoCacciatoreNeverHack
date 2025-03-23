package mostri;

import camp.Griglia;

public class Anabroccolo extends M{
	
	int crescitasalute = 2;
	int crescitamovimento = 1;
	int crescitaattacco = 2;

	public Anabroccolo() {
		super('♧');
		nome = "Anabroccolo";
		salutemassima = 4;
		salute = salutemassima;
		attacco = 2;
		movimento = 2;
		punteggio = 100;
		nomemostro = "un Anabroccolo";
		dichiarazioneturno = "Turno dell'Anabroccolo";
	}


	private void AnabroccoloEvent(Griglia griglia, int riganemico, int colonnanemico) {
	    if (griglia.getCella(riganemico, colonnanemico).mostro == false) {
	    	griglia.getCella(riganemico, colonnanemico).getGiocatoreQui().subisceDanno(attacco);
	    	
		    if (identificatore == '♧') {
		    	System.out.println(String.format("L'anabroccolo ti ha colpito!%n"
		    									+"Scendi a %d punti salute.%n",
		    			griglia.getCella(riganemico, colonnanemico).
		    			getGiocatoreQui().getSalute()));
		    	
		    	if (griglia.getCella(riganemico, colonnanemico).
		    			getGiocatoreQui().getSalute() <= 0) {
					System.out.print(String.format("%nL'anabroccolo ti ha beccato a morte.%n"
												+ "Ma se era solo un pulcino!%n"
												+ "Fine della partita."));
					System.exit(0);
		    	}
		    }
		    
		    if (identificatore == '♣') {
		    	System.out.println(String.format("L'anabroccolo adulto ti ha colpito!%n"
		    									+"Scendi a %d punti salute.", 
		    			griglia.getCella(riganemico, colonnanemico)
		    			.getGiocatoreQui().getSalute()));
		    	if (griglia.getCella(riganemico, colonnanemico)
		    			.getGiocatoreQui().getSalute() <= 0) {
					System.out.print(String.format("%nL'anabroccolo adulto%n"
												+ "ti ha beccato a morte.%n"
												+ "Era proprio arrabbiato!%n"
												+ "Fine della partita."));
					System.exit(0);
		    	}
		    }
		}
	    else {
	    	System.out.println(String.format("C'è un altro mostro in %d,%d...",
	    			riganemico, colonnanemico));
	    	if (identificatore == '♧') {
	    		System.out.println(String.format("quindi l'anabroccolo lo urta%n"
	    				+ "e rimane lì dov'è.%nChe carino!"));
	    	}
	    	if (identificatore == '♣') {
	    		if (griglia.getCella(riganemico, colonnanemico).getIdentificatore() != 'Ψ') {
		    		System.out.println(String.format("quindi l'anabroccolo adulto%n"
		    				+ "lo scaglia all'indietro%ne continua imperterrito!"));
		    		riga = riganemico;
		    		colonna = colonnanemico;
	    			griglia.getCella(riganemico, colonnanemico).getMostroQui().setRiga(riga);
	    			griglia.getCella(riganemico, colonnanemico).getMostroQui().setColonna(colonna);
	    			griglia.setCella(griglia.getCella(riganemico, colonnanemico).getMostroQui());    			
	    		}
	    		else System.out.println(String.format("e per stavolta%nè costretto a fermarsi."));
		    }
	    }
	}
	    
	public void muovi(Griglia griglia) {
		
	var pg = trovaPG(griglia);
	
	for(int i=1; i<=movimento; i++) {
		var direzione = direzionePerInseguire (pg);
	    if (direzione == 0) {// Muoversi verso l'alto
	    	System.out.println(String.format("Si sposta verso l'alto... (%d/%d)", i, movimento));
	    	 
	        if (griglia.getCella(riga - 1, colonna).vuoto == true) {
	        	griglia.getCella(riga, colonna).clear();
				riga--;
	        } else AnabroccoloEvent(griglia, riga-1, colonna);
	    }
	    
	    if (direzione == 1) { // Muoversi verso sinistra
	    	System.out.println(String.format("Si sposta verso sinistra... (%d/%d)", i, movimento));
	    	
	        if (griglia.getCella(riga, colonna - 1).vuoto == true) {
	        	griglia.getCella(riga, colonna).clear();
				colonna--;
	        } else AnabroccoloEvent(griglia, riga, colonna-1);
	    }
	    
	    if (direzione == 2) { // Muoversi verso il basso
	    	System.out.println(String.format("Si sposta verso il basso... (%d/%d)", i, movimento));
	    	
	    	if (griglia.getCella(riga + 1, colonna).vuoto == true) {
	        	griglia.getCella(riga, colonna).clear();
				riga++;
	        } else AnabroccoloEvent(griglia, riga+1, colonna);
	    }
	    
	    if (direzione == 3) { // Muoversi verso destra
	    	System.out.println(String.format("Si sposta verso destra... (%d/%d)", i, movimento));
	    	
	    	if (griglia.getCella(riga, colonna + 1).vuoto == true) {
	    		griglia.getCella(riga, colonna).clear();
				colonna++;
	        } else AnabroccoloEvent(griglia, riga, colonna+1);
	    }
	}
	}
	
	@Override
	public void fineTurno(Griglia griglia) {
		System.out.println(String.format("L'anabroccolo (%d,%d) cresce e ottiene%n"
										+ "+%d Salute e +%d movimento!",
										riga+1, colonna+1, crescitasalute, crescitamovimento));
		salutemassima += crescitasalute;
		Cura(crescitasalute);
		movimento += crescitamovimento;
		
		if (salutemassima >= 10) {
			System.out.println(String.format("L'anabroccolo è diventato adulto!%n"
											+"Ora infligge +%d danni.", crescitaattacco));
			identificatore = '♣';
			griglia.getCella(riga, colonna).getMostroQui().setIdentificatore('♣');
		}
	}
}
