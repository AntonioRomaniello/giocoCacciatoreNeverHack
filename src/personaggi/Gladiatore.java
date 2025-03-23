package personaggi;

import camp.Gioco;

public class Gladiatore extends PG{

	public Gladiatore() {
		super('☆');
		salutemassima = 11;
		salute = salutemassima;
		movimento = 3;
		attacco = 3;
	}

	@Override
	public void presentazione() {
		System.out.println(String.format("3. Gladiatore%n"
									+ "Salute:    %s/%s%nMovimento: %s%nAttacco:   %s%n%n"
									+ "Abilità: Pubblico in Visibilio%n"
									+ "Stupisci tutti combattendo senza abilità%n"
									+ "e moltiplica i punti per 1,5!%n"
									+ "Punta al record!%n%n",
									salute, salutemassima, movimento, attacco));
	}
	
	public void inizioGioco(Gioco gioco) {
		for (int i=0; i<gioco.getM().size(); i++) {
			gioco.getM().get(i).setPunteggio(gioco.getM().get(i).getPunteggio()
											+ (gioco.getM().get(i).getPunteggio() / 2));
		}
	}
}
