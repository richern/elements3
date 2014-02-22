package menu;

import java.util.ArrayList;

public abstract class Composite implements Component {
	
	ArrayList<Leaf> options;
	
	public Composite() {
		options = new ArrayList<Leaf>();
	}
	
	public void addOption(Leaf o) {
		options.add(o);
	}

}
