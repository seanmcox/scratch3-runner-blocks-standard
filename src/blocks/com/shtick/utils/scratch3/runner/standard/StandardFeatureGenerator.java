/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.shtick.utils.scratch3.runner.core.FeatureSet;
import com.shtick.utils.scratch3.runner.core.FeatureSetGenerator;
import com.shtick.utils.scratch3.runner.core.GraphicEffect;
import com.shtick.utils.scratch3.runner.core.Opcode;
import com.shtick.utils.scratch3.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch3.runner.standard.blocks.*;
import com.shtick.utils.scratch3.runner.standard.effects.BrightnessEffect;
import com.shtick.utils.scratch3.runner.standard.effects.GhostEffect;
import com.shtick.utils.scratch3.runner.standard.effects.MosaicEffect;
//import com.shtick.utils.scratch3.runner.standard.mcommands.GetVar;

/**
 * @author sean.cox
 *
 */
public class StandardFeatureGenerator implements FeatureSetGenerator{
	/**
	 * 
	 */
	private static final GraphicEffect[] STANDARD_GRAPHIC_EFFECTS = new GraphicEffect[] {
			new BrightnessEffect(),
			new GhostEffect(),
			new MosaicEffect()
	};
	private static final Font SAY_FONT = new Font("sansserif", Font.BOLD, 15);
	private static final Stroke SAY_STROKE = new BasicStroke(4.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke SAY_STROKE_2 = new BasicStroke(3.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke SAY_STROKE_3 = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke SAY_STROKE_4 = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final BufferedImage TEST_IMAGE;
	private static final FontMetrics SAY_FONT_METRICS;
	static {
		TEST_IMAGE = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		SAY_FONT_METRICS = TEST_IMAGE.getGraphics().getFontMetrics(SAY_FONT);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.FeatureSetGenerator#getFeatureSetName()
	 */
	@Override
	public String getFeatureSetName() {
		return "standard";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.FeatureSetGenerator#generateFeatureSet()
	 */
	@Override
	public FeatureSet generateFeatureSet() {
		/*
		final Answer answer = new Answer();
		final Timer timer = new Timer();
		final WhenSceneStarts whenSceneStarts = new WhenSceneStarts();
		*/
		final EventWhenBroadcastReceived eventWhenBroadcastReceived = new EventWhenBroadcastReceived();

		Opcode[] opcodesArray = new Opcode[] {
				new ArgumentReporterBoolean(),
				new ArgumentReporterStringNumber(),
				new ControlWait(),
				new ControlWaitUntil(),
				new EventBroadcast(eventWhenBroadcastReceived),
				eventWhenBroadcastReceived,
				new EventWhenFlagClicked(),
				new ProceduresDefinition(),
				new ProceduresPrototype(),
				/*
				new _DividedBy(),
				new _Equals(),
				new _GreaterThan(),
				new _LessThan(),
				new _LogicalAnd(),
				new _LogicalOr(),
				new _Minus(),
				new _Modulus(),
				new _Plus(),
				new _Times(),
				new Abs(),
				answer,
				new AppendToList(),
				new BackgroundIndex(),
				new BounceOffEdge(),
				new Broadcast(whenIReveice),
				new Call(procDef),
				new ChangeGraphicEffectBy(),
				new ChangePenHueBy(),
				new ChangePenShadeBy(),
				new ChangePenSizeBy(),
				new ChangeSizeBy(),
				// TODO changeTempoBy:
				new ChangeVarBy(),
				new ChangeVolumeBy(),
				new ChangeXPosBy(),
				new ChangeYPosBy(),
				new ClearPenTrails(),
				// TODO color:sees:
				new ComeToFront(),
				new ComputeFunctionOf(),
				new ConcatenateWith(),
				new ContentsOfList(),
				new CostumeIndex(),
				new CreateCloneOf(),
				new DeleteClone(),
				new DeleteLineOfList(),
				new DistanceTo(),
				new DoAsk(answer),
				new DoBroadcastAndWait(whenIReveice),
				new DoForever(),
				new DoForeverIf(),
				new DoForLoop(),
				new DoIf(),
				new DoIfElse(),
				new DoPlaySoundAndWait(),
				new DoRepeat(),
				new DoReturn(),
				new DoUntil(),
				new DoWaitUntil(),
				// Don't implement doWhile. It does not appear to be spec'd and in use.
				// TODO drum:duration:elapsed:from:
				new FilterReset(),
				new Forward(),
				// Don't implement fxTest
				new GetAttributeOf(),
				new GetLineOfList(),
				new GetParam(),
				new GetUserId(),
				new GetUserName(),
				new GlideSecsToXYElapsedFrom(),
				new GoBackByLayers(),
				new GotoSpriteOrMouse(),
				new GotoXY(),
				new Heading(),
				new HeadingSet(),
				new Hide(),
				// TODO hideAll
				new HideList(),
				new HideVariable(),
				new InsertAtOfList(),
				// TODO instrument:
				// TODO isLoud
				new KeyPressed(),
				new LetterOf(),
				new LineCountOfList(),
				new ListContains(),
				new LookLike(),
				// TODO midiInstrument:
				new MousePressed(),
				new MouseX(),
				new MouseY(),
				new NextCostume(),
				new NextScene(),
				new Not(),
				// TODO noteOn:duration:elapsed:from:
				new PenColor(),
				new PenSize(),
				// TODO playDrum
				new PlaySound(),
				new PointTowards(),
				new PutPenDown(),
				new PutPenUp(),
				new RandomFromTo(),
				new ReadVariable(),
				// TODO rest:elapsed:from:
				new Rounded(),
				new Say(),
				new SayDurationElapsedFrom(),
				new SayNothing(),
				new Scale(),
				new SceneName(),
				// Don't implement scrollAlign. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
				// Don't implement scrollRight. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
				// Don't implement scrollUp. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
				// TODO senseVideoMotion
				// TODO sensor:
				// TODO sensorPressed:
				new SetGraphicEffectTo(),
				new SetLineOfListTo(),
				new SetPenHueTo(),
				new SetPenShadeTo(),
				// TODO setRotationStyle
				new SetSizeTo(),
				// TODO setTempoTo:
				new SetVarTo(),
				// TODO setVideoState
				// TODO setVideoTransparency
				new SetVolumeTo(),
				new Show(),
				new ShowList(),
				new ShowVariable(),
				// TODO soundLevel
				new Sqrt(),
				new StampCostume(),
				new StartScene(),
				new StartSceneAndWait(whenSceneStarts),
				// No longer current: stopAll See: https://wiki.scratch.mit.edu/wiki/Stop_All_(block)
				// TODO stopAllSounds
				new StopScripts(),
				new StringLength(),
				// TODO tempo
				new Think(),
				new ThinkDurationElapsedFrom(),
				new TimeAndDate(),
				timer,
				new TimerReset(timer),
				// TODO timestamp
				new Touching(),
				// TODO touchingColor:
				// No longer current: turnAwayFromEdge See: https://wiki.scratch.mit.edu/wiki/Point_Away_From_Edge_(block)
				new TurnLeft(),
				new TurnRight(),
				new Volume(),
				new WaitElapsedFrom(),
				// Don't implement warpSpeed. See: https://wiki.scratch.mit.edu/wiki/All_at_Once_(block)
				new WhenClicked(),
				new WhenCloned(),
				new WhenGreenFlag(timer),
				whenIReveice,
				new WhenKeyPressed(),
				whenSceneStarts,
				// TODO whenSensorGreaterThan
				new Xpos(),
				new XposSet(),
				// Don't implement xScroll. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
				new Ypos(),
				new YposSet()
				// Don't implement yScroll. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
				*/
		};
		final HashMap<String,Opcode> opcodesMap = new HashMap<>(opcodesArray.length);
		for(Opcode opcode:opcodesArray)
			opcodesMap.put(opcode.getOpcode(), opcode);
		
		StageMonitorCommand[] stageMonitorCommandsArray = new StageMonitorCommand[] {
				/*
				new com.shtick.utils.scratch.runner.standard.mcommands.Answer(answer),
				// TODO backgroundIndex
				// TODO costumeIndex
				new GetVar(),
				// TODO heading
				// TODO scale
				new com.shtick.utils.scratch.runner.standard.mcommands.SceneName(),
				// TODO senseVideoMotion
				// TODO soundLevel
				// TODO tempo
				new com.shtick.utils.scratch.runner.standard.mcommands.TimeAndDate()
				// TODO timer
				// TODO volume
				// TODO xpos
				// TODO ypos
				*/
		};
		final HashMap<String,StageMonitorCommand> stageMonitorCommandsMap = new HashMap<>(stageMonitorCommandsArray.length);
		for(StageMonitorCommand stageMonitorCommand:stageMonitorCommandsArray)
			stageMonitorCommandsMap.put(stageMonitorCommand.getCommand(), stageMonitorCommand);

		final HashMap<String,GraphicEffect> graphicEffectsMap = new HashMap<>(STANDARD_GRAPHIC_EFFECTS.length);
		for(GraphicEffect graphicEffect:STANDARD_GRAPHIC_EFFECTS)
			graphicEffectsMap.put(graphicEffect.getName(), graphicEffect);
		return new FeatureSet() {
			
			@Override
			public Set<StageMonitorCommand> getStageMonitorCommands() {
				return new HashSet<>(stageMonitorCommandsMap.values());
			}
			
			@Override
			public StageMonitorCommand getStageMonitorCommand(String id) {
				return stageMonitorCommandsMap.get(id);
			}
			
			@Override
			public Set<Opcode> getOpcodes() {
				return new HashSet<>(opcodesMap.values());
			}
			
			@Override
			public Opcode getOpcode(String opcode) {
				return opcodesMap.get(opcode);
			}
			
			@Override
			public Set<GraphicEffect> getGraphicEffects() {
				return new HashSet<>(graphicEffectsMap.values());
			}
			
			@Override
			public GraphicEffect getGraphicEffect(String id) {
				return graphicEffectsMap.get(id);
			}
		};
	}

	/**
	 * 
	 * @param text The text to show.
	 * @return The talk bubble image.
	 */
	public static Image createTalkBubbleImage(String text) {
		if((text==null)||(text.length()==0))
			return null;
		int fontHeight = SAY_FONT_METRICS.getHeight();
		
		Image textImage = createTextImage(text);
		int imageWidth = textImage.getWidth(null)+fontHeight*2;
		int imageHeight = textImage.getHeight(null)+fontHeight*3;
		int controlDistance = (int)Math.sqrt(fontHeight*fontHeight/6);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		GeneralPath path = new GeneralPath();
		path.moveTo(fontHeight, fontHeight/2);
		path.lineTo(imageWidth-fontHeight, fontHeight/2);
		path.quadTo(imageWidth-fontHeight+controlDistance, fontHeight-controlDistance, imageWidth-fontHeight/2, fontHeight);
		path.lineTo(imageWidth-fontHeight/2, imageHeight - fontHeight*2);
		path.quadTo(imageWidth-fontHeight+controlDistance, imageHeight - fontHeight*2+controlDistance, imageWidth-fontHeight, imageHeight - fontHeight*3/2);
		path.lineTo(fontHeight*5/2, imageHeight - fontHeight*3/2);
		path.lineTo(fontHeight/2, imageHeight - fontHeight/2);
		path.lineTo(fontHeight, imageHeight - fontHeight*3/2);
		path.quadTo(fontHeight-controlDistance, imageHeight - fontHeight*2+controlDistance, fontHeight/2, imageHeight - fontHeight*2);
		path.lineTo(fontHeight/2, fontHeight);
		path.quadTo(fontHeight-controlDistance, fontHeight-controlDistance, fontHeight, fontHeight/2);
		path.closePath();
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fill(path);
		g2.setColor(Color.GRAY);
		g2.setStroke(SAY_STROKE);
		g2.draw(path);
		g2.drawImage(textImage, fontHeight, fontHeight, null);
		return image;
	}
	
	/**
	 * 
	 * @param text The text to show.
	 * @return The talk bubble image.
	 */
	public static Image createThoughtBubbleImage(String text) {
		if((text==null)||(text.length()==0))
			return null;
		int fontHeight = SAY_FONT_METRICS.getHeight();
		
		Image textImage = createTextImage(text);
		int imageWidth = textImage.getWidth(null)+fontHeight*2;
		int imageHeight = textImage.getHeight(null)+fontHeight*3;
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		Shape mainBubble;
		Shape bubble2;
		Shape bubble3;
		Shape bubble4;
		{
			mainBubble = new RoundRectangle2D.Float(fontHeight/2,fontHeight/2,textImage.getWidth(null)+fontHeight,textImage.getHeight(null)+fontHeight,fontHeight,fontHeight);
			bubble2 = new Ellipse2D.Float(fontHeight+3*fontHeight/4,imageHeight - fontHeight-fontHeight/3,fontHeight,3*fontHeight/5);
			bubble3 = new Ellipse2D.Float(fontHeight+fontHeight/2,imageHeight - fontHeight/2-fontHeight/3,2*fontHeight/3,2*fontHeight/5);
			bubble4 = new Ellipse2D.Float(fontHeight,imageHeight - fontHeight/2,fontHeight/2,fontHeight/4);
		}
		
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fill(mainBubble);
		g2.fill(bubble2);
		g2.fill(bubble3);
		g2.fill(bubble4);
		g2.setColor(Color.GRAY);
		g2.setStroke(SAY_STROKE);
		g2.draw(mainBubble);
		g2.setStroke(SAY_STROKE_2);
		g2.draw(bubble2);
		g2.setStroke(SAY_STROKE_3);
		g2.draw(bubble3);
		g2.setStroke(SAY_STROKE_4);
		g2.draw(bubble4);
		g2.drawImage(textImage, fontHeight, fontHeight, null);
		return image;
	}
	
	/**
	 * 
	 * @param text
	 * @return An image containing the text layed out on a transparent background.
	 */
	public static Image createTextImage(String text) {
		if((text==null)||(text.length()==0))
			return null;
		String[] words = text.split("\\s+");
		int minWidth = 0;
		for(String word:words)
			minWidth = Math.max(minWidth, SAY_FONT_METRICS.stringWidth(word));
		int fontHeight = SAY_FONT_METRICS.getHeight();
		int fontAscent = SAY_FONT_METRICS.getAscent();
		int lineWidth = SAY_FONT_METRICS.stringWidth(text);
		int requiredAreaInSquareFontHeights = (int)Math.ceil(lineWidth/(double)fontHeight);
		int unitsOf4x3Ratio = (int)Math.ceil(Math.sqrt(requiredAreaInSquareFontHeights/(3.0*4)));
		int estimatedWidth = unitsOf4x3Ratio * 4 * fontHeight;
		if(estimatedWidth<minWidth)
			estimatedWidth = (int)Math.ceil(minWidth/(double)fontHeight);
		String textRemaining = text;
		String normalizedTextRemaining = textRemaining.replaceAll("\\s", " ");
		LinkedList<String> lines = new LinkedList<>();
		int index;
		while(textRemaining.length()>0) {
			int currentLineLength = 0;
			index = 0;
			while(index<textRemaining.length()) {
				index = normalizedTextRemaining.indexOf(' ', currentLineLength+1);
				if(index<0) {
					int tempLineWidth = SAY_FONT_METRICS.stringWidth(textRemaining);
					if(currentLineLength>0) {
						if(tempLineWidth<estimatedWidth) {
							currentLineLength = textRemaining.length();
						}
					}
					else {
						currentLineLength = textRemaining.length();
						if(tempLineWidth>estimatedWidth) {
							estimatedWidth = tempLineWidth;
						}
					}
					break;
				}
				int tempLineWidth = SAY_FONT_METRICS.stringWidth(textRemaining.substring(0, index));
				if(tempLineWidth>estimatedWidth) {
					if(currentLineLength>0)
						break;
					else
						estimatedWidth = tempLineWidth;
				}
				currentLineLength = index;
			}
			lines.add(textRemaining.substring(0, currentLineLength));
			textRemaining = textRemaining.substring(currentLineLength).replaceAll("^\\s+","");
			normalizedTextRemaining = normalizedTextRemaining.substring(currentLineLength).replaceAll("^\\s+","");
		}
		
		int imageWidth = estimatedWidth;
		int imageHeight = lines.size()*fontHeight;
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setFont(SAY_FONT);
		g2.setColor(Color.BLACK);
		int i=0;
		for(String line:lines) {
			g2.drawString(line, 0, i*fontHeight+fontAscent);
			i++;
		}

		return image;
	}
	
	/**
	 * 
	 * @param e 
	 * @return The Scratch key ID for the key that generated the given event.
	 */
	public static String getKeyIdForEvent(KeyEvent e) {
		String keyID;
		if(((e.getKeyChar()>='a')&&(e.getKeyChar()<='z'))||((e.getKeyChar()>='A')&&(e.getKeyChar()<='Z'))||((e.getKeyChar()>='0')&&(e.getKeyChar()<='9'))){
			keyID = ""+e.getKeyChar();
		}
		else {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				keyID = "space";
				break;
			case KeyEvent.VK_UP:
				keyID = "up arrow";
				break;
			case KeyEvent.VK_DOWN:
				keyID = "down arrow";
				break;
			case KeyEvent.VK_LEFT:
				keyID = "left arrow";
				break;
			case KeyEvent.VK_RIGHT:
				keyID = "right arrow";
				break;
			case KeyEvent.VK_ENTER:
				keyID = "enter";
				break;
			default:
				keyID = null;
			}
		}
		return keyID;
	}
}
