package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.BoardController;
import it.unibo.giocooca.view.BoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class BoardViewImpl implements BoardView {

    private static final int COLS = 9;
    private static final int ROWS = 7;
    private static final int CELL_SIZE = 100;
    private static final int GAP = 2;
    private static final int PIECE_RADIUS = 10;
    private static final int BOARD_WIDTH = COLS * CELL_SIZE + (COLS - 1) * GAP;
    private static final int BOARD_HEIGHT = ROWS * CELL_SIZE + (ROWS - 1) * GAP;

    private static final Color COLOR_NORMAL = Color.web("#dfe6e9");
    private static final Color COLOR_SPECIAL = Color.web("#fdcb6e");
    private static final Color COLOR_PRISON = Color.web("#d63031");
    private static final Color COLOR_BORDER = Color.DARKGRAY;

    private final Pane root;

    private final Pane pieceLayer;

    private final Map<String, Circle> pieces = new HashMap<>();

    private final Map<Integer, StackPane> cellsByPosition = new HashMap<>();

    public BoardViewImpl(final BoardController controller) {
        final GridPane cellLayer = buildCellLayer(controller);

        this.pieceLayer = new Pane();
        this.pieceLayer.setPrefSize(BOARD_WIDTH, BOARD_HEIGHT);

        final StackPane stackRoot = new StackPane(cellLayer, this.pieceLayer);
        stackRoot.setAlignment(Pos.TOP_LEFT);
        stackRoot.setMaxSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.root = stackRoot;
    }

    private static Color cellTypeToColor(final String cellType) {
        return switch (cellType) {
            case "SPECIAL" -> COLOR_SPECIAL;
            case "PRISON" -> COLOR_PRISON;
            default -> COLOR_NORMAL;
        };
    }

    private static int[] toGridCoords(final int position) {
        final int zeroIndex = position - 1;
        final int rowFromBottom = zeroIndex / COLS;
        final int colInRow = zeroIndex % COLS;
        final int gridRow = (ROWS - 1) - rowFromBottom;
        final int gridCol = (rowFromBottom % 2 == 0) ? colInRow : (COLS - 1) - colInRow;

        return new int[]{gridRow, gridCol};
    }

    private static String mapPieceColor(final String colorName) {
        return switch (colorName) {
            case "Rosso"  -> "#e74c3c";
            case "Verde"  -> "#2ecc71";
            case "Blu"    -> "#3498db";
            case "Giallo" -> "#f1c40f";
            default       -> "#95a5a6";
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
    public void updatePlayerPositions(Map<String, Integer> playerPositions) {
        for (final Map.Entry<String, Integer> entry : playerPositions.entrySet()) {
            movePiece(entry.getKey(), entry.getValue());
        }
    }

    private GridPane buildCellLayer(final BoardController controller) {
        final GridPane grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
        for (int pos = 1; pos <= controller.getBoardSize(); pos++) {
            final int[] coords = toGridCoords(pos);
            final StackPane cell = buildCellPane(pos, controller.getCellType(pos), controller.getCellOffset(pos));
            grid.add(cell, coords[1], coords[0]);
            this.cellsByPosition.put(pos, cell);
        }

        return grid;
    }

    private StackPane buildCellPane(final int position, final String cellType, final int cellOffset) {
        final Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE);
        background.setFill(cellTypeToColor(cellType));
        background.setStroke(COLOR_BORDER);
        background.setStrokeWidth(1);

        final Label numberLabel = new Label(String.valueOf(position));
        numberLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        final StackPane cell = new StackPane(background, numberLabel);
        StackPane.setAlignment(numberLabel, Pos.TOP_LEFT);

        if (cellType.equals("SPECIAL")) {
            final Label offsetLabel = new Label(formatOffset(cellOffset));
            offsetLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");
            StackPane.setAlignment(offsetLabel, Pos.BOTTOM_RIGHT);
            cell.getChildren().add(offsetLabel);
        }

        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        return cell;
    }

    private void movePiece(final String color, final int position) {
        final StackPane cell = this.cellsByPosition.get(position);
        if (cell == null) {
            return;
        }
        // Il centro reale della cella viene letto dal suo bounding box già renderizzato,
        // evitando di ricalcolarlo a mano (e di introdurre scostamenti dovuti a gap/arrotondamenti).
        final var bounds = cell.getBoundsInParent();
        final double centerX = bounds.getMinX() + bounds.getWidth() / 2.0;
        final double centerY = bounds.getMinY() + bounds.getHeight() / 2.0;

        if (!pieces.containsKey(color)) {
            final Circle piece = new Circle(PIECE_RADIUS);
            piece.setFill(Color.web(mapPieceColor(color)));
            piece.setStroke(Color.BLACK);
            pieces.put(color, piece);
            pieceLayer.getChildren().add(piece);
        }

        final Circle piece = pieces.get(color);
        piece.setLayoutX(centerX);
        piece.setLayoutY(centerY);
    }
}
