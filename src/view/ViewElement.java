package view;

import opengl.resource.object.drawable.GLColoredObject;
import controller.CommandHandler;
import controller.ICommand;
import controller.ICommandSender;

public abstract class ViewElement extends GLColoredObject implements ICommandSender {
	private CommandHandler commandHandler;
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
	
	@Override
	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
	
	protected void sendCommand(ICommand command) {
		this.commandHandler.executeCommand(command);
	}
}
