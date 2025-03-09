package camp;

public class Cacciatore extends Elemento{

	public Cacciatore() {
		super(1, 1, 'C', false);
		salutemassima = 20;
		salute = 10;
		movimento = 3;
		attacco = 3;
	}
	

	public String presentazione() {
		return String.format("Salute:    %s/%s%nMovimento: %s%nAttacco:   %s%n%n"
										+ "Abilità: Abilità di sopravvivenza%n"
										+ "Ogni volta che il cacciatore%ncolpisce un nemico, "
										+ "si cura di 2.%n"
										+ "+2 Attacco a salute massima.",
										salute, salutemassima, movimento, attacco);
	}
}
