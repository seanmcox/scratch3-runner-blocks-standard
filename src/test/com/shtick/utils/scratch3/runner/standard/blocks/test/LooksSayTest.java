package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.core.elements.Target;
import com.shtick.utils.scratch3.runner.standard.blocks.LooksSay;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class LooksSayTest {

	@Test
	void testOpcode() {
		LooksSay op = new LooksSay();
		assertEquals("looks_say",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		LooksSay op = new LooksSay();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("MESSAGE", DataType.STRING);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		LooksSay op = new LooksSay();
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("MESSAGE","something");
		SayableRuntime runtime = new SayableRuntime();
		Target target = new SelfParentTarget();
		OpcodeSubaction subaction = op.execute(runtime, new AllBadRunner(), target, arguments, new AllBadMutation());
		assertNull(subaction);
		assertNotNull(runtime.bubbleImage);
		assertEquals(target,runtime.bubbleTarget);
	}

	public static class SayableRuntime extends AllBadRuntime{
		public Target bubbleTarget;
		public Image bubbleImage;
		
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime#setSpriteBubbleImage(com.shtick.utils.scratch3.runner.core.elements.Target, java.awt.Image)
		 */
		@Override
		public void setSpriteBubbleImage(Target target, Image image) {
			this.bubbleImage = image;
			this.bubbleTarget = target;
		}
	}
	
	public static class SelfParentTarget extends AllBadTarget{
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}
	}
}
