package personaggi;

import java.io.IOException;

import camp.Gioco;
import camp.Griglia;
import camp.Input;
import lombok.Data;

@Data
public abstract class PG{
	protected int riga;
	protected int colonna;
	protected char identificatore;
	protected int movimento;
	protected int salutemassima; //Per le cure
	protected int salute;
	protected int attacco;
	public boolean giamosso; //Per abilità che si attivano una volta per turno
	
	public PG(char identificatore) {
		this.identificatore = identificatore;
	}
	
	public abstract void presentazione();
	
	//Questa funzione e la classe Input esistono
	//per mia incapacità di replicare il codice del professore
	//per prendere in input un carattere.
		public Input input() {
			Input input = new Input();
			try {
				int a;
				a = System.in.read();
				if (a == 'w') 
					input.w();
				if (a == 'a')
					input.a();
				if (a == 's') 
					input.s();
				if (a == 'd') 
					input.d();	
				} catch (IOException e) {
				}
			return input;
		}
		
	//Metodi per controllare che non si cerchi di superare il confine
		public boolean ConfSuperiore(){
			if(riga-1 < 0) {
				System.out.print(String.format("Sei al confine superiore della griglia.%n"
						+ "Non puoi spostarti verso l'alto!%n"
						+ "Effettua un altro movimento (wasd): "));
				return true;	
			}
			else return false;
		}
		
		public boolean ConfSinistro() {
			if(colonna-1 < 0) {
				System.out.print(String.format("Sei al confine sinistro della griglia.%n"
												+ "Non puoi spostarti verso sinistra!%n"
												+ "Effettua un altro movimento (wasd): "));
				return true;	
			}
			else return false;
		}
		
		public boolean ConfInferiore(Griglia griglia) {
			if(riga > griglia.getAltezza()-2) {
				System.out.print(String.format("Sei al confine inferiore della griglia.%n"
												+ "Non puoi spostarti verso il basso!%n"
												+ "Effettua un altro movimento (wasd): "));
				return true;	
			}
			else return false;
		}
		
		public boolean ConfDestro(Griglia griglia) {
			if(colonna > griglia.getLarghezza()-2) {
				System.out.print(String.format("Sei al confine destro della griglia.%n"
												+ "Non puoi spostarti verso destra!%n"
												+ "Effettua un altro movimento (wasd): "));
				return true;
			}
			else return false;
		}
	//Fine metodi per controllare il superamento dei confini
		
	//Movimento di base.
		public void muovi(Griglia griglia) {
			movimento --;
			boolean fatto = false;
			//Finché una decisione non viene presa
			while (!fatto) {
				Input input = input();
				char veroinput = input.getInput();
				//Se l'input è w l'elemento guarda la cella sopra (riga-1, stessa colonna)
					if (veroinput == 'w') {
						if (!ConfSuperiore()) {
							if (griglia.getCella(riga-1, colonna).vuoto == true) {//Cella sopra vuota
								griglia.getCella(riga, colonna).clear();
								riga--;
							}
							else {//Se la cella sopra non è vuota (mostro)
								Attacca(griglia, riga-1, colonna);
							}
						fatto = true;
						}
					}
				//Se l'input è a l'elemento guarda la cella a sinistra (stessa riga, colonna-1)
					if (veroinput == 'a') {
						if(!ConfSinistro()){
							if (griglia.getCella(riga, colonna-1).vuoto == true) {//Cella sinistra vuota
								griglia.getCella(riga, colonna).clear();
								colonna--;
							}
							else {//Se la cella a sinistra non è vuota (mostro)
								Attacca(griglia, riga, colonna-1);
							}
						fatto = true;
						}				
					}
				//Se l'input è s l'elemento guarda la cella sotto (riga+1, stessa colonna)
					if (veroinput == 's') {
						if (!ConfInferiore(griglia)){
							if (griglia.getCella(riga+1, colonna).vuoto == true) {
								griglia.getCella(riga, colonna).clear();
								riga++;
							}
							else {//Se la cella in basso non è vuota (mostro)
								Attacca(griglia, riga+1, colonna);
							}
						fatto = true;
						}	
					}
				//Se l'input è d l'elemento guarda la cella a destra (stessa riga, colonna+1)
					if (veroinput == 'd') {
						if(!ConfDestro(griglia)){
							if (griglia.getCella(riga, colonna+1).vuoto == true) {
								griglia.getCella(riga, colonna).clear();
								colonna++;
							}
							else {//Se la cella a destra non è vuota (mostro)
								Attacca(griglia, riga, colonna+1);
							}
						fatto = true;
						}
					}
			}
		}
		
	//Attacco di base.
		public void Attacca(Griglia griglia, int riganemico, int colonnanemico) {
			griglia.getCella(riganemico, colonnanemico).getMostroQui().subisceDanno(attacco);
			System.out.println("");
			System.out.println("Hai colpito un nemico...");
				
			//Se il movimento uccide il mostro dici che lo hai ucciso
			if (griglia.getCella(riganemico, colonnanemico).getMostroQui().getSalute() <= 0) {
				System.out.println("e lo hai ucciso.");
				griglia.getCella(riganemico, colonnanemico).getMostroQui().vieneUcciso(griglia);
			}
			//altrimenti dici quanta salute gli è rimasta.
			else System.out.println(String.format("che ora ha %d salute.",
					griglia.getCella(riganemico, colonnanemico).getMostroQui().getSalute()));		
			}
		
	//Cura di base.
		public void Cura(int quantacura) {
			salute = salute + quantacura;
			if (salute > salutemassima)	salute = salutemassima;
		}
		
		public void subisceDanno(int a) {
			salute -= a;
		}

		public void fineTurno() {
		}

		public void inizioGioco(Gioco gioco) {
		}
}