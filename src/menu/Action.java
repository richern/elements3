 package menu;

import java.util.concurrent.Callable;

public class Action extends Option {

	Callable<Void> func;
	
	public Action(String text, Callable<Void> func) {
		super(text);
		this.func = func;
	}

	@Override
	public void open() {
		try {
			func.call();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	

}
