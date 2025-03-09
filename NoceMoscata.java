package camp;

public class NoceMoscata extends Elemento{

	public NoceMoscata(int riga, int colonna) {
		super(riga, colonna, '•', true);
		vuoto = false;
		salutemassima = 3;
		salute = salutemassima;
		attacco = 2;
	}
 	
	public void coordinateIniziali(Griglia griglia) {
		var coordinate = coordinateCasuali(griglia);
		boolean fatto = false;
		while (!fatto){
			coordinate = coordinateCasuali(griglia);
			if (griglia.getCella(coordinate[0], coordinate[1]).vuoto == true) {
				riga = coordinate[0];
				colonna = coordinate[1];
				fatto = true;
			}
		}
	}
}