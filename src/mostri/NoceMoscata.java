package mostri;

import java.util.Random;

import camp.Griglia;

public class NoceMoscata extends M{

	public NoceMoscata() {
		super('•');
		nome= "Noce Moscata";
		movimento = 3;
		salutemassima = 3;
		salute = salutemassima;
		attacco = 2;
		punteggio = 50;
		nomemostro = "una Noce Moscata";
		dichiarazioneturno = "Turno della Noce Moscata";
	}
	
	private void UrtoMessaggio(int a) {
		if (a==1)
		System.out.println(String.format("ma urta un muro e rimane lì dov'è."));
		if (a==2)
		System.out.println(String.format("ma urta un mostro e rimane lì dov'è."));
		
		System.out.println("Che stupida.");
		System.out.println("");
	}
	
	//Metodo richiamato da muoviNoceMoscata()
	//quando la cella in cui cerca di muoversi non è vuota (mostro o giocatore).
		private void NoceMoscataEvent(Griglia griglia, int riganemico, int colonnanemico) {
			if (griglia.getCella(riganemico, colonnanemico).mostro == false) {
				
				griglia.getCella(riganemico, colonnanemico).getGiocatoreQui().subisceDanno(attacco);
				
				System.out.println(String.format("e ti colpisce!%nScendi a %d punti salute.%n",
						griglia.getCella(riganemico, colonnanemico).getGiocatoreQui().getSalute()));
						
				if (griglia.getCella(riganemico, colonnanemico).getGiocatoreQui().getSalute() <= 0) {
					System.out.print(String.format("%nHai lasciato che la Noce Moscata%n"
												+ "ti urtasse pateticamente a morte.%n"
												+ "Notevole... Fine della partita."));
					System.exit(0);
				}
			}
			else UrtoMessaggio(2);				
		}
			
	//Gestisce il movimento di un mostro Noce Moscata.
	//Il metodo che richiama questo metodo sceglie una direzione casuale che da in input,
	//dopodiché la Noce Moscata prova a muoversi di una casella in quella direzione per 3 volte.
	public void muovi(Griglia griglia) {
		Random rnd = new Random();
		int direzione = rnd.nextInt(0,3);		
		for(int i=1; i<=movimento; i++) {
			
			if (direzione == 0) {
				System.out.println(String.format("Si sposta verso l'alto... (%d/%d)", i, movimento));
		
				if(riga-1 < 0) UrtoMessaggio(1);
				else {//Se non siamo ai confini della griglia
					if (griglia.getCella(riga-1, colonna).vuoto == true) {
						griglia.getCella(riga, colonna).clear();
						riga--;
					}				
					else NoceMoscataEvent(griglia, riga-1, colonna);
				}
			}
					
			if (direzione == 1) {
				System.out.println(String.format("Si sposta verso sinistra... (%d/%d)", i, movimento));
				
				if(colonna-1 < 0) UrtoMessaggio(1);	
				else{//se non siamo ai confini della mappa
					if (griglia.getCella(riga, colonna-1).vuoto == true){
						griglia.getCella(riga, colonna).clear();
						colonna--;
					}
					else NoceMoscataEvent(griglia, riga, colonna-1);
				}
			}
							
			if (direzione == 2) {
				System.out.println(String.format("Si sposta verso il basso... (%d/%d)", i, movimento));
				
				if(riga > griglia.getAltezza()-2) UrtoMessaggio(1);
				else{//se non siamo ai confini della mappa
					if (griglia.getCella(riga+1, colonna).vuoto == true) {
						griglia.getCella(riga, colonna).clear();
						riga++;
					}
					else NoceMoscataEvent(griglia, riga+1, colonna);
				}
			}
						
			if (direzione == 3) {
				System.out.println(String.format("Si sposta verso destra... (%d/%d)", i, movimento));
				
				if(colonna+1 > griglia.getLarghezza()-2) UrtoMessaggio(1);
				else{//Se non siamo al confine destro della mappa
					if (griglia.getCella(riga, colonna+1).vuoto == true){
						griglia.getCella(riga, colonna).clear();
						colonna++;
					}
					else NoceMoscataEvent(griglia, riga, colonna+1);
				}
			}
		}
	}
}