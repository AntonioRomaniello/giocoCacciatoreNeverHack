package camp;

import lombok.Data;
import mostri.M;
import personaggi.PG;

@Data
public class Elemento {

	public char identificatore;
	public PG giocatoreQui;
	public M mostroQui;
	public boolean vuoto;
	public boolean trex;
	public boolean mostro;
	
	public Elemento(char identificatore) {
		this.identificatore = identificatore;
		vuoto = true;
		trex = false;
	}
	
	//Se vuoto è true, questo elemento viene considerato uno spazio vuoto
	//e nel quale, quindi, ci si può spostare.
	public void clear() {
		vuoto = true;
		mostro = false;
		if(trex) identificatore ='!';
		else identificatore = '□';
	}
}