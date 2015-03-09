package main;

public class KeyBinding {
	private boolean state = false;
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return this.state;
	}
}
