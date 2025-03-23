package personaggi;

import camp.Griglia;
import camp.Input;

public class Cacciatore extends PG{		

	public Cacciatore() {
		super('C');
		salutemassima = 16;
		salute = 10;
		movimento = 3;
		attacco = 3;
	}	

	public void presentazione() {
		System.out.println(String.format("1. Cacciatore%n"
										+ "Salute:    %s/%s%nMovimento: %s%nAttacco:   %s%n%n"
										+ "Abilità: Sopravvivenza%n"
										+ "Ogni volta che il cacciatore%ncolpisce un nemico, "
										+ "si cura di 2.%n"
										+ "+2 Attacco a salute massima.%n%n",
										salute, salutemassima, movimento, attacco));
	}
	
	private int Sopravvivenza() {
		int temp = attacco;
		if (salute == salutemassima) {
			attacco += 2;
			System.out.println("Sopravvivenza: +2 Attacco!");
		}
		return temp;
	}
	
	@Override
	//Il codice del movimento è lo stesso di PG,
	//con la differenza che il Cacciatore
	//si cura di 2 dopo l'attacco
	//e chiama l'abilità Sopravvivenza prima del tutto (temp per evitare aumenti d'attacco continui).
	public void muovi(Griglia griglia) {
		
		int temp = Sopravvivenza();
		
		movimento --;
		boolean fatto = false;
		while (!fatto) {Input input = input(); char veroinput = input.getInput();
				if (veroinput == 'w') {if (!ConfSuperiore()) {
										if (griglia.getCella(riga-1, colonna).vuoto == true) {
											griglia.getCella(riga, colonna).clear(); riga--;
										} else {Attacca(griglia, riga-1, colonna);
						
							Cura(2);
							
										}fatto = true;}}
				if (veroinput == 'a') {if(!ConfSinistro()){
										if (griglia.getCella(riga, colonna-1).vuoto == true) {
											griglia.getCella(riga, colonna).clear(); colonna--;
										}else {Attacca(griglia, riga, colonna-1);
										
							Cura(2);
							
										}fatto = true;}}
				if (veroinput == 's') {if (!ConfInferiore(griglia)){
										if (griglia.getCella(riga+1, colonna).vuoto == true) {
											griglia.getCella(riga, colonna).clear(); riga++;
										}else {Attacca(griglia, riga+1, colonna);
							
							Cura(2);
							
										}fatto = true;}}
				if (veroinput == 'd') {if(!ConfDestro(griglia)){
										if (griglia.getCella(riga, colonna+1).vuoto == true) {
											griglia.getCella(riga, colonna).clear();colonna++;
										}else {Attacca(griglia, riga, colonna+1);
										
							Cura(2);
							
										}fatto = true;}}
		}
		attacco = temp;
	}
}