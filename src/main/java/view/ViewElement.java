package view;

import opengl.resource.object.GLDrawableObject;

public abstract class ViewElement extends GLDrawableObject {
	private String label;
	private int index;
	private boolean isSelected;
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
