package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.BoardController;
import it.unibo.giocooca.view.BoardView;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import it.unibo.giocooca.audio.SoundEffect;
import it.unibo.giocooca.audio.SoundManager;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BoardViewImpl implements BoardView {

    private static final int ROW_LENGTH = 10;
    private static final int GROUP_SIZE = ROW_LENGTH + 1;

    private static final int CELL_WIDTH = 80;
    private static final int CELL_HEIGHT = 60;

    private static final int HGAP = 6;
    private static final int VGAP = 8;
    private static final int PIECE_SIZE = 30;

    private static final Color COLOR_NORMAL = Color.web("#dfe6e9");
    private static final Color COLOR_SPECIAL = Color.web("#fdcb6e");
    private static final Color COLOR_PRISON = Color.web("#d63031");
    private static final Color COLOR_START = Color.web("#74b9ff");
    private static final Color COLOR_BORDER = Color.DARKGRAY;
    private static final String PIECE_IMAGE_PATH = "/images/pieces/";

    private final Pane root;
    private final Pane pieceLayer;
    private final Map<String, ImageView> pieces = new HashMap<>();
    private final Map<Integer, StackPane> cellsByPosition = new HashMap<>();
    private final Map<String, Integer> currentPositions = new HashMap<>();
    private Animation currentAnimation;

    public BoardViewImpl(final BoardController controller) {
        final int boardSize = controller.getBoardSize();
        final int lastPosition = boardSize - 1;
        final int maxLogicalRow = maxLogicalRow(lastPosition);

        double boardWidth = ROW_LENGTH * CELL_WIDTH + (ROW_LENGTH - 1) * HGAP;
        double boardHeight = (maxLogicalRow + 1) * CELL_HEIGHT + maxLogicalRow * VGAP;

        final GridPane cellLayer = buildCellLayer(controller, lastPosition, maxLogicalRow);

        this.pieceLayer = new Pane();
        this.pieceLayer.setPrefSize(boardWidth, boardHeight);

        final StackPane stackRoot = new StackPane(cellLayer, this.pieceLayer);
        stackRoot.setAlignment(Pos.CENTER);
        stackRoot.setMaxSize(boardWidth, boardHeight);
        this.root = stackRoot;
    }

    private static LogicalCoords toLogicalCoords(final int position) {
        final int group = position / GROUP_SIZE;
        final int offset = position % GROUP_SIZE;
        final boolean leftToRight = group % 2 == 0;
        final boolean isCorner = offset == ROW_LENGTH;

        if (isCorner) {
            final int col = leftToRight ? ROW_LENGTH - 1 : 0;
            return new LogicalCoords(2 * group + 1, col);
        }
        final int col = leftToRight ? offset : (ROW_LENGTH - 1 - offset);
        return new LogicalCoords(2 * group, col);
    }

    private static int maxLogicalRow(final int lastPosition) {
        return toLogicalCoords(lastPosition).row();
    }

    private static GridCoords toGridCoords(final int position, final int maxLogicalRow) {
        final LogicalCoords logical = toLogicalCoords(position);
        return new GridCoords(maxLogicalRow - logical.row(), logical.col());
    }

    private static Color cellTypeToColor(final String cellType) {
        return switch (cellType) {
            case "START" -> COLOR_START;
            case "SPECIAL" -> COLOR_SPECIAL;
            case "PRISON" -> COLOR_PRISON;
            default -> COLOR_NORMAL;
        };
    }

    private static String pieceImageName(final String colorName) {
        return switch (colorName) {
            case "Rosso" -> "OcaRossoSvg.png";
            case "Verde" -> "OcaVerdeSvg.png";
            case "Blu" -> "OcaBluSvg.png";
            case "Giallo" -> "OcaGialloSvg.png";
            default -> throw new IllegalArgumentException("Colore pedina non supportato: " + colorName);
        };
    }

    private static double[] quadrantOffset(final String color) {
        final double quarterCellX = CELL_WIDTH / 4.0;
        final double quarterCellY = CELL_HEIGHT / 4.0;
        return switch (color) {
            case "Rosso"  -> new double[]{-quarterCellX, -quarterCellY};
            case "Verde"  -> new double[]{ quarterCellX, -quarterCellY};
            case "Blu"    -> new double[]{-quarterCellX,  quarterCellY};
            case "Giallo" -> new double[]{ quarterCellX,  quarterCellY};
            default       -> new double[]{0, 0};
        };
    }

    private static String formatOffset(final int offset) {
        return offset > 0 ? "+" + offset : String.valueOf(offset);
    }

    @Override
    public Pane getBoard() {
        return this.root;
    }

    @Override
    public void stopAnimations() {
        if (this.currentAnimation != null) {
            this.currentAnimation.stop();
            this.currentAnimation = null;
        }
    }

    @Override
    public void updatePlayerPositions(
            Map<String, Integer> playerPositions,
            String movingPieceColor,
            List<Integer> intermediatePositions,
            final Runnable onIntermediateReached,
            Runnable onAnimationFinished) {
        Map<Integer, Integer> playersPerCell = new HashMap<>();
        for (Integer pos : playerPositions.values()) {
            playersPerCell.put(pos, playersPerCell.getOrDefault(pos, 0) + 1);
        }

        final int oldPos = currentPositions.getOrDefault(movingPieceColor, 0);

        final List<Runnable> deferredMoves = new ArrayList<>();
        for (final Map.Entry<String, Integer> entry : playerPositions.entrySet()) {
            final String color = entry.getKey();
            final int newPos = entry.getValue();
            final boolean sharedDest = playersPerCell.get(newPos) > 1;
            currentPositions.put(color, newPos);
            if (!color.equals(movingPieceColor)) {
                deferredMoves.add(() -> movePieceDirectly(color, newPos, sharedDest));
            }
        }

        final Runnable onFinished = () -> {
            deferredMoves.forEach(Runnable::run);
            if (onAnimationFinished != null) {
                onAnimationFinished.run();
            }
        };

        if (movingPieceColor != null && playerPositions.containsKey(movingPieceColor)) {
            final int newPos = playerPositions.get(movingPieceColor);
            final boolean sharedDest = playersPerCell.get(newPos) > 1;
            final boolean hasIntermediateSteps = intermediatePositions != null && !intermediatePositions.isEmpty();
            if (oldPos != newPos) {
                if (hasIntermediateSteps) {
                    animatePiecePathThroughSteps(movingPieceColor, oldPos,
                            intermediatePositions, newPos, sharedDest, onIntermediateReached, onFinished);
                } else {
                    animatePiecePath(movingPieceColor, oldPos, newPos, sharedDest, onFinished);
                }
            } else {
                onFinished.run();
            }
        } else {
            deferredMoves.forEach(Runnable::run);
            if (onAnimationFinished != null) {
                onAnimationFinished.run();
            }
        }
    }

    private GridPane buildCellLayer(final BoardController controller, final int boardSize, final int maxLogicalRow) {
        final GridPane grid = new GridPane();
        grid.setHgap(HGAP);
        grid.setVgap(VGAP);
        for (int pos = 0; pos <= boardSize; pos++) {
            final GridCoords coords = toGridCoords(pos, maxLogicalRow);
            final StackPane cell = buildCellPane(pos, controller.getCellType(pos), controller.getCellOffset(pos));
            grid.add(cell, coords.col(), coords.row());
            this.cellsByPosition.put(pos, cell);
        }
        return grid;
    }

    private StackPane buildCellPane(final int position, final String cellType, final int cellOffset) {
        final Rectangle background = new Rectangle(CELL_WIDTH, CELL_HEIGHT);
        background.setFill(cellTypeToColor(cellType));
        background.setStroke(COLOR_BORDER);
        background.setStrokeWidth(1);

        final Label numberLabel = new Label(cellType.equals("START") ? "START" : String.valueOf(position));
        numberLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        final StackPane cell = new StackPane(background, numberLabel);
        StackPane.setAlignment(numberLabel, Pos.TOP_LEFT);

        if (cellType.equals("SPECIAL")) {
            final Label offsetLabel = new Label(formatOffset(cellOffset));
            offsetLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");
            StackPane.setAlignment(offsetLabel, Pos.BOTTOM_RIGHT);
            cell.getChildren().add(offsetLabel);
        }

        cell.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        return cell;
    }

    private ImageView getOrCreatePiece(final String color) {
        if (!pieces.containsKey(color)) {
            final Image pieceImage = new Image(PIECE_IMAGE_PATH + pieceImageName(color));
            final ImageView piece = new ImageView(pieceImage);
            piece.setFitWidth(PIECE_SIZE);
            piece.setFitHeight(PIECE_SIZE);

            pieces.put(color, piece);
            pieceLayer.getChildren().add(piece);
            
            final StackPane startCell = cellsByPosition.get(0);
            if (startCell != null) {
                this.root.layout();
                var bounds = startCell.getBoundsInParent();
                piece.setTranslateX(bounds.getMinX() + (bounds.getWidth() / 2.0) - (PIECE_SIZE / 2.0));
                piece.setTranslateY(bounds.getMinY() + (bounds.getHeight() / 2.0) - (PIECE_SIZE / 2.0));
            }
        }
        return pieces.get(color);
    }

    private void animatePiecePath(
            String color,
            int startPos,
            int endPos,
            boolean sharedDest,
            Runnable onFinished) {
        final ImageView piece = getOrCreatePiece(color);
        final SequentialTransition sequence = new SequentialTransition();

        addPathSegment(sequence, piece, color, startPos, endPos, endPos, sharedDest);

        sequence.setOnFinished(e -> {
            if (onFinished != null) {
                Platform.runLater(onFinished);
            }
        });

        this.currentAnimation = sequence;
        sequence.play();
    }

    private void animatePiecePathThroughSteps(
            String color,
            int startPos,
            List<Integer> intermediateSteps,
            int endPos,
            boolean sharedDest,
            final Runnable onIntermediateReached,
            final Runnable onFinished) {
        final ImageView piece = getOrCreatePiece(color);
        final SequentialTransition full = new SequentialTransition();

        int fromPos = startPos;
        for (final int interPos : intermediateSteps) {
            final int from = fromPos;
            final SequentialTransition seg = new SequentialTransition();

            addPathSegment(seg, piece, color, from, interPos, interPos, false);

            seg.setOnFinished(e -> {
                if (onIntermediateReached != null) {
                    Platform.runLater(onIntermediateReached);
                }
            });
            full.getChildren().add(seg);
            full.getChildren().add(new PauseTransition(Duration.millis(700)));
            fromPos = interPos;
        }

        final int lastFrom = fromPos;
        final SequentialTransition lastSeg = new SequentialTransition();
        addPathSegment(lastSeg, piece, color, lastFrom, endPos, endPos, sharedDest);
        full.getChildren().add(lastSeg);
        full.setOnFinished(e -> {
            if (onFinished != null) {
                Platform.runLater(onFinished);
            }
        });
        
        this.currentAnimation = full;
        full.play();
    }

    private void addPathSegment(final SequentialTransition sequence, final ImageView piece, final String color,
            final int fromPos, final int toPos, final int destinationPos, final boolean sharedDest) {
        if (fromPos == toPos) {
            return;
        }
        final int step = (fromPos < toPos) ? 1 : -1;

        for (int i = fromPos + step; i != toPos + step; i += step) {
            final StackPane cell = cellsByPosition.get(i);
            if (cell == null) continue;

            final var bounds = cell.getBoundsInParent();
            double targetX = bounds.getMinX() + (bounds.getWidth() / 2.0) - (PIECE_SIZE / 2.0);
            double targetY = bounds.getMinY() + (bounds.getHeight() / 2.0) - (PIECE_SIZE / 2.0);

            if (i == destinationPos && sharedDest) {
                final double[] offset = quadrantOffset(color);
                targetX += offset[0];
                targetY += offset[1];
            }

            TranslateTransition tt = new TranslateTransition(Duration.millis(400), piece);
            tt.setToX(targetX);
            tt.setToY(targetY);
            tt.setOnFinished(e -> Platform.runLater(() -> SoundManager.getInstance().playSfx(SoundEffect.PIECE_MOVE)));
            sequence.getChildren().add(tt);
        }
    }

    private void movePieceDirectly(String color, int position, boolean sharedDest) {
        final ImageView piece = getOrCreatePiece(color);
        StackPane cell = cellsByPosition.get(position);
        if (cell == null) return;

        final var bounds = cell.getBoundsInParent();
        double targetX = bounds.getMinX() + (bounds.getWidth() / 2.0) - (PIECE_SIZE / 2.0);
        double targetY = bounds.getMinY() + (bounds.getHeight() / 2.0) - (PIECE_SIZE / 2.0);

        if (sharedDest) {
            final double[] offset = quadrantOffset(color);
            targetX += offset[0];
            targetY += offset[1];
        }

        final TranslateTransition tt = new TranslateTransition(Duration.millis(400), piece);
        tt.setToX(targetX);
        tt.setToY(targetY);
        tt.play();
    }

    private record LogicalCoords(int row, int col) { }

    private record GridCoords(int row, int col) { }
}