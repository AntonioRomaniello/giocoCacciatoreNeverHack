package camp;

import java.io.IOException;
import java.util.Random;

import lombok.Data;

@Data
public class Elemento {

	protected int riga;
	protected int colonna;
	protected char identificatore;
	protected int movimento;
	protected int salutemassima;
	protected int salute;
	protected int attacco;
	protected boolean vuoto;
	protected boolean mostro;
	public boolean giamosso;
	public boolean trex;
	public int punteggio;
	
	public Elemento(int riga, int colonna, char identificatore, boolean mostro) {
		this.riga = riga;
		this.colonna = colonna;
		this.identificatore = identificatore;
		this.mostro = mostro;
	}
	
	//Se vuoto è true, questo elemento viene considerato uno spazio vuoto.
	//Ciò è importante per consentire ad un altro elemento di spostarsi
	//nella posizione identificata da this.riga e this.colonna.
	public void clear() {
		vuoto = true;
		mostro = false;
		if(trex) identificatore ='!';
		else identificatore = '□';
	}

	//Modificano la classe di un elemento in quella più specifica
	//di un personaggio giocabile. Utilizzate nella selezione personaggio.
		public Cacciatore sceltaCacciatore() {
			System.out.println("Hai scelto il cacciatore!");
			return new Cacciatore();
		}
		
		public Ninja sceltaNinja() {
			System.out.println("Hai scelto il ninja!");
			return new Ninja();
		}
	
	//Funzione che genera un array di 2 per dare delle coordinate.
	//Usato solo per dare le coordinate iniziali ai mostri,
	//all'interno di un loro metodo coordinateIniziali.
		public int[] coordinateCasuali(Griglia griglia) {
			Random rndx = new Random();
			int col = rndx.nextInt(0, griglia.getLarghezza());
			int row = rndx.nextInt(0, griglia.getAltezza());
			int[] coordinate = {row,col};
			return coordinate;
		}
		
	//Questa funzione serve ad ultimare un movimento inizializzato da muovi().
	//L'elemento vuoto acquisisce tutte le caratteristiche di questo elemento,
	//poi questo elemento diventa vuoto.
	//In questo modo si saranno effettivamente scambiate le celle!
			public void scambiaCelle(Elemento vuoto) {
			
	 		vuoto.setAttacco(attacco);
	 		vuoto.setIdentificatore(identificatore);
	 		vuoto.setMovimento(movimento);
			vuoto.setSalute(salute);
			vuoto.setSalutemassima(salutemassima);
			vuoto.setVuoto(false);
			vuoto.setMostro(mostro);
			vuoto.setGiamosso(giamosso);
			
			clear();
		}	
		
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

		public void Cura(int quantacura) {
			salute = salute + quantacura;
			if (salute > salutemassima)
				salute = salutemassima;
		}
	 	
	//Decide quale funzione di movimento richiamare in base all'identificatore.
		public Elemento muovi(Griglia griglia) {
			movimento--;
			var risultato = griglia.getCella(riga, colonna);
			
			if (identificatore == 'C')
				risultato = muoviCacciatore(griglia);
			if (identificatore == 'N')
				risultato = muoviNinja(griglia);
			return risultato;
		}
		
	//Gestisce un attacco di base di un personaggio giocabile.
	//Abilità speciali vanno aggiunte nel "muovi" relativo al personaggio.
	public void Attacca(Griglia griglia, int riganemico, int colonnanemico, int attacco) {
			int salutenemico = griglia.getCella(riganemico, colonnanemico).getSalute();
			griglia.getCella(riganemico, colonnanemico).setSalute(salutenemico - attacco);
			System.out.println("Hai colpito un nemico...");
			
			//Se il movimento uccide il mostro dici che lo hai ucciso
			if (griglia.getCella(riganemico, colonnanemico).getSalute() <= 0) {
				System.out.println("e lo hai ucciso.");
				griglia.aggiungipunteggio(griglia.getCella(riganemico,colonnanemico));
				griglia.getCella(riganemico, colonnanemico).clear();
			}
			//altrimenti dici quanta salute gli è rimasta.
			else
			System.out.println(String.format("che ora ha %d salute.",
					griglia.getCella(riganemico, colonnanemico).getSalute()));
		}

		public Elemento muoviCacciatore(Griglia griglia) {
			boolean fatto = false;
			if (salute == salutemassima)
				System.out.println("Abilità di sopravvivenza: +2 Attacco!");
			//Finché una decisione non viene presa
			while (!fatto) {
				Input input = input();
				char veroinput = input.getInput();
				//Se l'input è w l'elemento guarda la cella sopra (riga-1, stessa colonna)
						if (veroinput == 'w') {
						//Controlla che non siamo ai confini della mappa.	
						if(riga-1 < 0) 
							System.out.print(String.format("Sei al confine superiore della griglia.%n"
															+ "Non puoi spostarti verso l'alto!%n"
															+ "Effettua un altro movimento (wasd): "));
						
						//Se non siamo ai confini della mappa...
						else {
							if (griglia.getCella(riga-1, colonna).vuoto == true) {//Cella sopra vuota
							scambiaCelle(griglia.getCella(riga-1, colonna));
							fatto = true;
							return griglia.getCella(riga-1, colonna);
							}
							else {//Se la cella sopra non è vuota (mostro)
								if (salute == salutemassima)
									Attacca(griglia, riga-1, colonna, attacco+2);
								else {
									Attacca(griglia, riga-1, colonna, attacco);
									System.out.println("Abilità di sopravvivenza: +2 salute!");
									Cura(2);
								}
									
								fatto = true;
							}
						}
					}
				//Se l'input è a l'elemento guarda la cella a sinistra (stessa riga, colonna-1)
						if (veroinput == 'a') {
						//Controlla che non siamo ai confini della mappa.
						if(colonna-1 < 0)
							System.out.print(String.format("Sei al confine sinistro della griglia.%n"
															+ "Non puoi spostarti verso sinistra!%n"
															+ "Effettua un altro movimento (wasd): "));
						
						//Se non siamo ai confini della mappa...
						else{
							if (griglia.getCella(riga, colonna-1).vuoto == true) {//Cella sinistra vuota
							scambiaCelle(griglia.getCella(riga, colonna-1));
							fatto = true;
							return griglia.getCella(riga, colonna-1);
							}
							else {//Se la cella a sinistra non è vuota (mostro)
								if (salute == salutemassima)
									Attacca(griglia, riga, colonna-1, attacco+2);
								else {
									Attacca(griglia, riga, colonna-1, attacco);
									System.out.println("Abilità di sopravvivenza: +2 salute!");
									Cura(2);
								}
									
								fatto = true;
							}
						}				
					}
				//Se l'input è s l'elemento guarda la cella sotto (riga+1, stessa colonna)
						if (veroinput == 's') {
							if(riga > griglia.getAltezza()-2) {
								System.out.print(String.format("Sei al confine inferiore della griglia.%n"
																+ "Non puoi spostarti verso il basso!%n"
																+ "Effettua un altro movimento (wasd): "));
							}
							else {
								if (griglia.getCella(riga+1, colonna).vuoto == true) {
									scambiaCelle(griglia.getCella(riga+1, colonna));
									fatto = true;
									return griglia.getCella(riga+1, colonna);
								}
								else {
									if (salute == salutemassima)
										Attacca(griglia, riga+1, colonna, attacco+2);
									else {
										Attacca(griglia, riga+1, colonna, attacco);
										System.out.println("Abilità di sopravvivenza: +2 salute!");
										Cura(2);
									}
										
									fatto = true;
								}
							}
						}	
				//Se l'input è d l'elemento guarda la cella a destra (stessa riga, colonna+1)
						if (veroinput == 'd') {
							if(colonna > griglia.getLarghezza()-2) {
								System.out.print(String.format("Sei al confine destro della griglia.%n"
																+ "Non puoi spostarti verso destra!%n"
																+ "Effettua un altro movimento (wasd): "));
							}
							else {
								if (griglia.getCella(riga, colonna+1).vuoto == true) {
									scambiaCelle(griglia.getCella(riga, colonna+1));
									fatto = true;
									return griglia.getCella(riga, colonna+1);
								}
								else {
									if (salute == salutemassima)
										Attacca(griglia, riga, colonna+1, attacco+2);
									else {
										Attacca(griglia, riga, colonna+1, attacco);
										System.out.println("Abilità di sopravvivenza: +2 salute!");
										Cura(2);
									}
										
									fatto = true;
								}
							}	
						}
			}
		fatto = true;
		return griglia.getCella(riga, colonna);	
	}
	
		public Elemento muoviNinja(Griglia griglia) {
			boolean fatto = false;
			//Finché una decisione non viene presa
			while (!fatto) {
				Input input = input();
				char veroinput = input.getInput();
				//Se l'input è w l'elemento guarda la cella sopra (riga-1, stessa colonna)
						if (veroinput == 'w') {
						//Controlla che non siamo ai confini della mappa.	
						if(riga-1 < 0) 
							System.out.print(String.format("Sei al confine superiore della griglia.%n"
															+ "Non puoi spostarti verso l'alto!%n"
															+ "Effettua un altro movimento (wasd): "));
						
						//Se non siamo ai confini della mappa...
						else {
							if (griglia.getCella(riga-1, colonna).vuoto == true) {//Cella sopra vuota
							scambiaCelle(griglia.getCella(riga-1, colonna));
							fatto = true;
							return griglia.getCella(riga-1, colonna);
							}
							else {//Se la cella sopra non è vuota (mostro)
								Attacca(griglia, riga-1, colonna, attacco);
			
						//Usa il booleano giamosso (normalmente inutile per il giocatore)
						//per determinare se Pié Lesto si è già attivato questo turno.
								if (giamosso == false) {
									this.movimento++;
									System.out.println("Piè lesto: +1 mossa questo turno!");
									giamosso = true;
								}
								fatto = true;
							}
						}
					}
				//Se l'input è a l'elemento guarda la cella a sinistra (stessa riga, colonna-1)
						if (veroinput == 'a') {
						//Controlla che non siamo ai confini della mappa.
						if(colonna-1 < 0)
							System.out.print(String.format("Sei al confine sinistro della griglia.%n"
															+ "Non puoi spostarti verso sinistra!%n"
															+ "Effettua un altro movimento (wasd): "));
						
						//Se non siamo ai confini della mappa...
						else{
							if (griglia.getCella(riga, colonna-1).vuoto == true) {//Cella sinistra vuota
							scambiaCelle(griglia.getCella(riga, colonna-1));
							fatto = true;
							return griglia.getCella(riga, colonna-1);
							}
							else {
								//Se la cella a sinistra non è vuota (mostro)
								Attacca(griglia, riga, colonna-1, attacco);
								
						//Usa il booleano giamosso (normalmente inutile per il giocatore)
						//per determinare se Pié Lesto si è già attivato questo turno.
								if (giamosso == false) {
									this.movimento++;
									System.out.println("Piè lesto: +1 mossa questo turno!");
									giamosso = true;
								}
								fatto = true;
							}
						}				
					}
				//Se l'input è s l'elemento guarda la cella sotto (riga+1, stessa colonna)
						if (veroinput == 's') {
							if(riga > griglia.getAltezza()-2) {
								System.out.print(String.format("Sei al confine inferiore della griglia.%n"
																+ "Non puoi spostarti verso il basso!%n"
																+ "Effettua un altro movimento (wasd): "));
							}
							else {
								if (griglia.getCella(riga+1, colonna).vuoto == true) {
									scambiaCelle(griglia.getCella(riga+1, colonna));
									fatto = true;
									return griglia.getCella(riga+1, colonna);
								}
								else {
									Attacca(griglia, riga+1, colonna, attacco);
						//Usa il booleano giamosso (normalmente inutile per il giocatore)
						//per determinare se Pié Lesto si è già attivato questo turno.
								if (giamosso == false) {
									this.movimento++;
									System.out.println("Piè lesto: +1 mossa questo turno!");
									giamosso = true;
								}
								fatto = true;
							}
						}	
					}
				//Se l'input è d l'elemento guarda la cella a destra (stessa riga, colonna+1)
						if (veroinput == 'd') {
							if(colonna > griglia.getLarghezza()-2) {
								System.out.print(String.format("Sei al confine destro della griglia.%n"
																+ "Non puoi spostarti verso destra!%n"
																+ "Effettua un altro movimento (wasd): "));
							}
							else {
								if (griglia.getCella(riga, colonna+1).vuoto == true) {
									scambiaCelle(griglia.getCella(riga, colonna+1));
									fatto = true;
									return griglia.getCella(riga, colonna+1);
								}
								else {
									Attacca(griglia, riga, colonna+1, attacco);
						//Usa il booleano giamosso (normalmente inutile per il giocatore)
						//per determinare se Pié Lesto si è già attivato questo turno.
								if (giamosso == false) {
									this.movimento++;
									System.out.println("Piè lesto: +1 mossa questo turno!");
									giamosso = true;
								}
								fatto = true;
							}
						}
					}
			
		}
			fatto = true;	
		return griglia.getCella(riga, colonna);	
	}
		
	//Metodo richiamato da muociNoceMoscata()
	//Se la cella in cui cerca di muoversi non è vuota (mostro o giocatore)
	private void NoceMoscataEvent(Griglia griglia, int riganemico, int colonnanemico) {
		if (griglia.getCella(riganemico, colonnanemico).mostro == false) {
			int saluteGiocatore = griglia.getCella(riganemico, colonnanemico).getSalute();
			griglia.getCella(riga-1, colonna).setSalute(saluteGiocatore - attacco);
			System.out.println(String.format("%nLa Noce Moscata ti ha colpito!%n"
											+ "Scendi a %d punti salute.%n",
											griglia.getCella(riganemico, colonnanemico).getSalute()));
					
			if (griglia.getCella(riganemico, colonnanemico).getSalute() <= 0) {
				System.out.print(String.format("%nHai lasciato che la Noce Moscata%n"
											+ "ti urtasse lievemente%nfino alla morte.%n"
											+ "Notevole... Fine della partita."));
				System.exit(0);
			}
		}
		else
			System.out.println(String.format("ma urta un altro mostro%n"
											+ "e rimane lì dov'è.%nChe stupida.%n"));
	}
		
	//Gestisce il movimento di un mostro Noce Moscata.
	//Il metodo che richiama questo metodo sceglie una direzione casuale che da in input,
	//dopodiché la Noce Moscata prova a muoversi di una casella in quella direzione per 3 volte.
	public Elemento muoviNoceMoscata(Griglia griglia, int direzione) {
		//Se l'input è 0 l'elemento guarda la cella sopra (riga-1, stessa colonna)
		if (direzione == 0) {
			System.out.println(String.format("%nLa Noce Moscata in %d,%d%n"
											+ "si sposta verso l'alto...", riga+1, colonna+1));
	
			if(riga-1 < 0) //Se siamo ai confini della griglia:
				System.out.println(String.format("ma urta un muro%ne rimane lì dov'è.%n"
						+ "Che stupida.%n"));
			else {//Se non siamo ai confini della griglia
				if (griglia.getCella(riga-1, colonna).vuoto == true) {
					scambiaCelle(griglia.getCella(riga-1, colonna));
					return griglia.getCella(riga-1, colonna);
				}
				else NoceMoscataEvent(griglia, riga-1, colonna);
			}
		}
				
		//Se l'input è 1 l'elemento guarda la cella a sinistra (stessa riga, colonna-1)	
		if (direzione == 1) {
			System.out.println(String.format("%nLa Noce Moscata in %d,%d%n"
											+ "si sposta verso sinistra...", riga+1, colonna+1));
			
			if(colonna-1 < 0)
				System.out.println(String.format("ma urta un muro%ne rimane lì dov'è.%n"
						+ "Che stupida.%n"));	
			else{//se non siamo ai confini della mappa
				if (griglia.getCella(riga, colonna-1).vuoto == true) {
				scambiaCelle(griglia.getCella(riga, colonna-1));
				return griglia.getCella(riga, colonna-1);
				}
				else NoceMoscataEvent(griglia, riga, colonna-1);
			}
		}
				
		//Se l'input è 2 l'elemento guarda la cella sotto (riga+1, stessa colonna)		
		if (direzione == 2) {
			System.out.println(String.format("%nLa Noce Moscata in %d,%d%n"
											+ "si sposta verso il basso...", riga+1, colonna+1));
			
			if(riga > griglia.getAltezza()-2)
				System.out.println(String.format("ma urta un muro%ne rimane lì dov'è.%n"
						+ "Che stupida.%n"));	
			else{//se non siamo ai confini della mappa
				if (griglia.getCella(riga+1, colonna).vuoto == true) {
				scambiaCelle(griglia.getCella(riga+1, colonna));
				return griglia.getCella(riga+1, colonna);
				}
				else NoceMoscataEvent(griglia, riga+1, colonna);
			}
		}
				
		//Se l'input è 3 l'elemento guarda la cella a destra (stessa riga, colonna+1)		
		if (direzione == 3) {
			System.out.println(String.format("%nLa Noce Moscata in %d,%d%n"
											+ "si sposta verso destra...", riga+1, colonna+1));
			
			if(colonna+1 > griglia.getLarghezza()-2)  
				System.out.println(String.format("ma urta un muro%ne rimane lì dov'è.%n"
						+ "Che stupida.%n"));	
			else{//Se non siamo al confine destro della mappa
				if (griglia.getCella(riga, colonna+1).vuoto == true) {
				scambiaCelle(griglia.getCella(riga, colonna+1));
				return griglia.getCella(riga, colonna+1);
				}
				else NoceMoscataEvent(griglia, riga, colonna+1);
			}
		}		
		return griglia.getCella(riga, colonna);
	}
	
	private void AnabroccoloEvent(Griglia griglia, int riganemico, int colonnanemico) {
	    if (griglia.getCella(riganemico, colonnanemico).mostro == false) {
	    	int saluteNemico = griglia.getCella(riganemico, colonnanemico).getSalute();
		    griglia.getCella(riganemico, colonnanemico).setSalute(saluteNemico - attacco);
		    if (identificatore == '♧') {
		    	System.out.println(String.format("L'anabroccolo ti ha colpito!%nScendi a %d "
		    			+ "punti salute.", griglia.getCella(riganemico, colonnanemico).getSalute()));
		    	if (griglia.getCella(riganemico, colonnanemico).getSalute() <= 0) {
					System.out.print(String.format("%nL'anabroccolo ti ha beccato a morte.%n"
												+ "Ma se era solo un pulcino!%n"
												+ "Partita finita."));
					System.exit(0);
		    	}
		    }
		    
		    if (identificatore == '♣') {
		    	System.out.println(String.format("L'anabroccolo adulto ti ha colpito!%nScendi a %d "
		    			+ "punti salute.", griglia.getCella(riganemico, colonnanemico).getSalute()));
		    	if (griglia.getCella(riganemico, colonnanemico).getSalute() <= 0) {
					System.out.print(String.format("%nL'anabroccolo adulto%nti ha beccato a morte.%n"
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
	    			final var attaccotemp = griglia.getCella(riganemico, colonnanemico).getAttacco();
			    	final var salutetemp = griglia.getCella(riganemico, colonnanemico).getSalute();
			    	final var salutemassimatemp = griglia.getCella(riganemico, colonnanemico).getSalutemassima();
			    	final var movimentotemp = griglia.getCella(riganemico, colonnanemico).getMovimento();
			    	final var identificatoretemp = griglia.getCella(riganemico, colonnanemico).identificatore;
			    	final var mostrotemp = griglia.getCella(riganemico, colonnanemico).mostro;
			    	final var giamossotemp = griglia.getCella(riganemico, colonnanemico).giamosso;
			    	final var vuototemp = griglia.getCella(riganemico, colonnanemico).vuoto;
			    	scambiaCelle(griglia.getCella(riganemico, colonnanemico));
			    	griglia.getCella(riganemico, colonnanemico).setAttacco(attaccotemp);
			    	griglia.getCella(riganemico, colonnanemico).setSalute(salutetemp);
			    	griglia.getCella(riganemico, colonnanemico).setSalutemassima(salutemassimatemp);
			    	griglia.getCella(riganemico, colonnanemico).setMovimento(movimentotemp);
			    	griglia.getCella(riganemico, colonnanemico).setIdentificatore(identificatoretemp);
			    	griglia.getCella(riganemico, colonnanemico).setMostro(mostrotemp);
			    	griglia.getCella(riganemico, colonnanemico).setGiamosso(giamossotemp);
			    	griglia.getCella(riganemico, colonnanemico).setVuoto(vuototemp);
	    		}
	    		else
	    			System.out.println(String.format("e per stavolta%n"
		    				+ "è costretto a fermarsi."));
		    	
	    	}
	    	
	    }
	}
	    
	public Elemento muoviAnabroccolo(Griglia griglia, int direzione) {
	System.out.println("");
	System.out.print("L'anabroccolo ");
	if (identificatore == '♣')
		System.out.print("adulto ");
	System.out.println(String.format("in %d,%d", riga+1, colonna+1));
	
	    if (direzione == 0) {// Muoversi verso l'alto
	    	 System.out.println(String.format("si sposta verso l'alto...%n"));
	    	 
	        if (griglia.getCella(riga - 1, colonna).vuoto == true) {
	            scambiaCelle(griglia.getCella(riga - 1, colonna));
	            return griglia.getCella(riga - 1, colonna);
	        } else AnabroccoloEvent(griglia, riga - 1, colonna);
	    }
	    
	    if (direzione == 1) { // Muoversi verso sinistra
	    	System.out.println(String.format("si sposta verso sinistra...%n"));
	        if (griglia.getCella(riga, colonna - 1).vuoto == true) {
	            scambiaCelle(griglia.getCella(riga, colonna - 1));
	            return griglia.getCella(riga, colonna - 1);
	        } else AnabroccoloEvent(griglia, riga, colonna - 1);
	    }
	    
	    if (direzione == 2) { // Muoversi verso il basso
	    	System.out.println(String.format("si sposta verso il basso...%n"));
	        if (griglia.getCella(riga + 1, colonna).vuoto == true) {
	            scambiaCelle(griglia.getCella(riga + 1, colonna));
	            return griglia.getCella(riga + 1, colonna);
	        } else AnabroccoloEvent(griglia, riga + 1, colonna);
	    }
	    
	    if (direzione == 3) { // Muoversi verso destra
	    	System.out.println(String.format("si sposta verso destra...%n"));
	    	if (griglia.getCella(riga, colonna + 1).vuoto == true) {
	            scambiaCelle(griglia.getCella(riga, colonna + 1));
	            return griglia.getCella(riga, colonna + 1);
	        } else AnabroccoloEvent(griglia, riga, colonna + 1);
	    }
	    
	    return griglia.getCella(riga, colonna);
	 }
}
