package controllerTest;

import controller.CommandHandler;
import controller.ICommand;
import model.Graph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {
	private int x;

	@Test
	public void CommandHandlerTest() {
		ICommand command1 = new ICommand() {
            @Override
			public void execute(Graph graph) {
				CommandTest.this.x += 7;
			}

			@Override
			public void undo() {
				CommandTest.this.x -= 7;
			}
		};
		
		ICommand command2 = new ICommand() {
			@Override
			public void execute(Graph graph) {
				CommandTest.this.x += 9;
			}

			@Override
			public void undo() {
				CommandTest.this.x -= 9;
			}
		};

		this.x = 0;
		CommandHandler handler = new CommandHandler(null);
		
		handler.executeCommand(command1);
		assertEquals(7, this.x);
		
		handler.executeCommand(command2);
		assertEquals(16, this.x);
		
		handler.executeCommand(command1);
		assertEquals(23, this.x);
		
		handler.undo();
		assertEquals(16, this.x);
		
		handler.redo();
		assertEquals(23, this.x);
		
		handler.redo();
		assertEquals(23, this.x);
		
		handler.undo();
		assertEquals(16, this.x);
		
		handler.undo();
		assertEquals(7, this.x);
		
		handler.executeCommand(command1);
		assertEquals(14, this.x);
		
		handler.redo();
		assertEquals(14, this.x);
		
		handler.undo();
		assertEquals(7, this.x);
		
		handler.redo();
		assertEquals(14, this.x);
		
		handler.undo();
		handler.undo();
		assertEquals(0, this.x);
		
		handler.undo();
		assertEquals(0, this.x);
	}
}
