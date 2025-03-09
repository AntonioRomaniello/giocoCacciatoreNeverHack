package camp;

public class Ninja extends Elemento{

	public Ninja() {
		super(0, 0, 'N', false);
		salutemassima = 11;
		salute = salutemassima;
		movimento = 4;
		attacco = 2;
		mostro = false;
	}
	

	public String presentazione() {
		return String.format("Salute:    %s/%s%nMovimento: %s%nAttacco:   %s%n%n"
										+ "Abilità: Piè Lesto%nLa prima volta in ogni turno%n"
										+ "che il ninja colpisce un nemico,%n"
										+ "ottiene 1 mossa extra per il turno.",
										salute, salutemassima, movimento, attacco);
	}
}
