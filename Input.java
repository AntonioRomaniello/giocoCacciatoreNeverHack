package camp;

import lombok.Data;

@Data
public class Input {
	
	char input;

	public Input() {
	}
	
	public void w() {
		input = 'w';
	}
	public void a() {
		input = 'a';
	}
	public void s() {
		input = 's';
	}
	public void d() {
		input = 'd';
	}
}
