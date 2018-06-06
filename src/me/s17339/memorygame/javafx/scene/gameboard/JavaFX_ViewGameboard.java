package me.s17339.memorygame.javafx.scene.gameboard;


import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneContentRoot;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.model.gameboard.GameTileInfo;
import me.s17339.memorygame.model.gameboard.GameboardInterface;
import me.s17339.memorygame.model.gameboard.GameboardObserver;
import me.s17339.memorygame.resinfo.LanguageNode;
import me.s17339.memorygame.utils.Parsers;

public class JavaFX_ViewGameboard extends JavaFX_SceneContentRoot implements GameboardObserver {

	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private GameboardInterface modelGameboard;
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_ControllerGameboard controller;
	protected JavaFX_ViewGameboard(GameboardInterface modelGameboard, JavaFX_GameEnvironmentInfo environment, JavaFX_ControllerGameboard controller,
			JavaFX_SceneRoot sceneRoot, int rows, int columns) {
		super(sceneRoot);
		this.controller = controller;
		this.modelGameboard = modelGameboard;
		this.environment = environment;
		createContent(rows, columns);
		modelGameboard.registerObserver(this);
	}

	private String styleNormal, styleHovered, styleSelected, styleCollected;
	
	private boolean started;
	private Text timerView;
	private StringProperty timerText = new SimpleStringProperty();
	private GameboardViewTile[][] tiles;
	private void createContent(int rows, int columns) {
		GridPane grid = new GridPane();
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("specified/Gameboard"));
		
		int imageSize = Math.min(960/columns, 540/rows);
		int layouts = imageSize / 10;
		grid.setVgap(layouts);
		grid.setHgap(layouts);
		
		this.styleNormal = "-fx-border-width: " + layouts + ";-fx-effect: dropshadow(three-pass-box, #000000, " + layouts + ", 0.5, 0, 0)";
		this.styleHovered = "-fx-border-width: " + layouts + ";-fx-effect: dropshadow(three-pass-box, #c5b1e7, " + layouts + ", 0.5, 0, 0)";
		this.styleSelected = "-fx-border-width: " + layouts + ";-fx-effect: dropshadow(three-pass-box, #d4e157, " + layouts + ", 0.5, 0, 0)";
		this.styleCollected = "-fx-border-width: " + layouts + ";-fx-effect: dropshadow(three-pass-box, #8fd477, " + layouts + ", 0.5, 0, 0);";
		
		tiles = new GameboardViewTile[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++) {
				final int x = j, y = i;
				ImageView image = new ImageView(JavaFX_GameResources.getPictureOfReverse());
				BooleanBinding visibleProperty = new BooleanBinding() {
					{
						super.bind(environment.getModelGameboardSettings().getHideOnCollectProperty());
					}
					
					@Override
					protected boolean computeValue() {
						return !(environment.getModelGameboardSettings().getHideOnCollectProperty().get() && modelGameboard.getTileInfo(x, y).isCollected());
					}
				};
				image.setFitHeight(imageSize);
				image.setFitWidth(imageSize);
				StackPane toBorderWrapper = new StackPane();
				toBorderWrapper.setOnMouseClicked(event -> {
					if(!started) {
						started = true;
						timerText.unbind();
					}
					controller.handleImageClick(x, y);
				});
				toBorderWrapper.visibleProperty().bind(visibleProperty);
				toBorderWrapper.getChildren().add(image);
				setHoverable(toBorderWrapper);
				grid.add(toBorderWrapper, j, i);
				tiles[i][j] = new GameboardViewTile(image, visibleProperty);
			}
		timerText.bind(environment.getModelSettings().getLanguageNode(LanguageNode.INGAME_PRESSTOSTART));
		timerView = JavaFX_GameLayout.createTitleText(timerText, false, 50);
		BorderPane.setMargin(timerView, new Insets(0, 0, 25, 0));
		BorderPane.setAlignment(timerView, Pos.CENTER);
		BorderPane border = new BorderPane();
		border.setTop(timerView);
		border.setCenter(grid);
		StackPane stack = new StackPane();
		stack.getChildren().add(border);
		getRoot().getStage().getScene().setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ESCAPE)
				this.controller.handleEscapeButtonPress(event);
		});
		setContent(stack);
	}
	
	private void setHoverable(Parent toBorderWrapper) {
		toBorderWrapper.setOnMouseEntered(event -> toBorderWrapper.setStyle(styleHovered));
		toBorderWrapper.setOnMouseExited(event -> toBorderWrapper.setStyle(styleNormal));
		toBorderWrapper.setId("tile-ingame");
		toBorderWrapper.setStyle(styleNormal);
	}
	
	private void setNotHoverable(Parent toBorderWrapper, String id, String style) {
		toBorderWrapper.setOnMouseEntered(null);
		toBorderWrapper.setOnMouseExited(null);
		toBorderWrapper.setId(id);
		toBorderWrapper.setStyle(style);
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	private ClickEffect effect;
	public void changeController(JavaFX_ControllerGameboard newController) {
		this.controller = newController;
	}
	
	@Override
	public void notifyTileRevealed(int x, int y) {
		if(effect == null)
			effect = ClickEffect.REVEALED;
		GameTileInfo tileInfo = modelGameboard.getTileInfo(x, y);
		tiles[y][x].physicalLayer.setImage(JavaFX_GameResources.getPicture(tileInfo.getNumber()));
		setNotHoverable(tiles[y][x].physicalLayer.getParent(), "tile-revealed", styleSelected);
	}

	@Override
	public void notifyTileUnrevealed(int x, int y) {
		tiles[y][x].physicalLayer.setImage(JavaFX_GameResources.getPictureOfReverse());
		setHoverable(tiles[y][x].physicalLayer.getParent());
	}

	@Override
	public void notifyWrongMatch(int x1, int y1, int x2, int y2) {
		effect = ClickEffect.WRONG_MATCH;
	}

	@Override
	public void notifyUndo(int x1, int y1) {
		effect = ClickEffect.WRONG_MATCH;
	}
	
	@Override
	public void notifyGoodMatch(int x1, int y1, int x2, int y2) {
		effect = ClickEffect.CORRECT_MATCH;
		tiles[y1][x1].visibleProperty.invalidate();
		tiles[y2][x2].visibleProperty.invalidate();
		tiles[y1][x1].visibleProperty.get();
		tiles[y2][x2].visibleProperty.get();
		setNotHoverable(tiles[y1][x1].physicalLayer.getParent(), "tile-collected", styleCollected);
		setNotHoverable(tiles[y2][x2].physicalLayer.getParent(), "tile-collected", styleCollected);
	}

	@Override
	public void notifyGameFinished(long finishTime) {
		controller.handleGameEnd(finishTime);
	}
	
	@Override
	public void notifyTimeUpdate(long difference) {
		String parsedTime = Parsers.formatGameTime(modelGameboard.getCountedTime());
		timerText.set(environment.getModelSettings().getNativeModel().getLanguage().get(LanguageNode.INGAME_TIME) + ": " + parsedTime);
	}

	@Override
	public void notifyCheckingFinished() {
		if(effect == null)
			return;
		switch(effect) {
			case NONE:
				break;
			case REVEALED:
				controller.playRevealingSound();
				break;
			case WRONG_MATCH:
				controller.playWrongMatchSound();
				break;
			case CORRECT_MATCH:
				controller.playCorrectMatchSound();
				break;
			default:
				break;
		}
		effect = null;
	}
	
	@Override
	public void notifyGamePaused() {}

	@Override
	public void notifyGameUnpaused() {}
	
	@Override
	public void notifyLayerAttached(JavaFX_AbstractSceneLayer layer) {
		controller.notifyLayerAttached(layer);
	}
	
	@Override
	public void notifyLayerDetached(JavaFX_AbstractSceneLayer layer) {
		controller.notifyLayerDetached(layer);
	}
	
	private enum ClickEffect {
		NONE, REVEALED, WRONG_MATCH, CORRECT_MATCH;
	}
	
	private class GameboardViewTile {
		private ImageView physicalLayer;
		private BooleanBinding visibleProperty;
		
		public GameboardViewTile(ImageView physicalLayer, BooleanBinding visibleProperty) {
			this.physicalLayer = physicalLayer;
			this.visibleProperty = visibleProperty;
		}
	}
}
