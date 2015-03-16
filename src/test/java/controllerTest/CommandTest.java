package controllerTest;

import controller.Command;
import controller.CommandContext;
import controller.CommandHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {
	private int x;

	@Test
	public void CommandHandlerTest() {
		Command command1 = new Command() {
            @Override
            public boolean isUndoable() {
                return true;
            }

            @Override
			public void execute() {
				CommandTest.this.x += 7;
			}

			@Override
			public void undo() {
				CommandTest.this.x -= 7;
			}
		};
		
		Command command2 = new Command() {
            @Override
            public boolean isUndoable() {
                return true;
            }

			@Override
			public void execute() {
				CommandTest.this.x += 9;
			}

			@Override
			public void undo() {
				CommandTest.this.x -= 9;
			}
		};

		this.x = 0;
        CommandContext context = new CommandContext();
		CommandHandler handler = new CommandHandler(context);
		
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
