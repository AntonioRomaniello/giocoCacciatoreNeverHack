package personaggi;

import camp.Griglia;

public class Ninja extends PG{

	public Ninja() {
		super('N');
		salutemassima = 11;
		salute = salutemassima;
		movimento = 4;
		attacco = 2;
	}
	
	public void presentazione() {
		System.out.println(String.format("2. Ninja%nSalute:    %s/%s%nMovimento: %s%nAttacco:   %s%n%n"
										+ "Abilità: Piè Lesto%nLa prima volta in ogni turno%n"
										+ "che il ninja colpisce un nemico,%n"
										+ "ottiene 1 mossa extra per il turno.%n%n",
										salute, salutemassima, movimento, attacco));
	}
	
	//Usa il booleano giamosso per determinare
	//se Pié Lesto si è già attivato questo turno.
	private void PièLesto() {
		if (giamosso == false) {
			movimento++;
			System.out.println("Piè lesto: +1 mossa questo turno!");
			giamosso = true;
		}
	}

	@Override
	//Il codice dell'attacco è lo stesso di PG,
	//con la differenza che il Ninja
	//chiama il metodo PièLesto alla fine di tutto.
	public void Attacca(Griglia griglia, int riganemico, int colonnanemico) {	
		griglia.getCella(riganemico, colonnanemico).getMostroQui().subisceDanno(attacco);
		System.out.println("Hai colpito un nemico...");
		
		if (griglia.getCella(riganemico, colonnanemico).getMostroQui().getSalute() <= 0) {
			System.out.println("e lo hai ucciso.");
			griglia.getCella(riganemico, colonnanemico).getMostroQui().vieneUcciso(griglia);
		}
		else System.out.println(String.format("che ora ha %d salute.",
				griglia.getCella(riganemico, colonnanemico).getMostroQui().getSalute()));
		
		PièLesto();
		}
}