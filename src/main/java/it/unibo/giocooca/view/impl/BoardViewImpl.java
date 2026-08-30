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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardViewImpl implements BoardView {

    private static final int ROW_LENGTH = 10;
    private static final int GROUP_SIZE = ROW_LENGTH + 1;

    private static final int CELL_WIDTH = 80;
    private static final int CELL_HEIGHT = 60;

    private static final int HGAP = 6;
    private static final int VGAP = 8;
    private static final int PIECE_RADIUS = 8;

    private static final Color COLOR_NORMAL = Color.web("#dfe6e9");
    private static final Color COLOR_SPECIAL = Color.web("#fdcb6e");
    private static final Color COLOR_PRISON = Color.web("#d63031");
    private static final Color COLOR_BORDER = Color.DARKGRAY;

    private final Pane root;
    private final Pane pieceLayer;
    private final Map<String, Circle> pieces = new HashMap<>();
    private final Map<Integer, StackPane> cellsByPosition = new HashMap<>();

    public BoardViewImpl(final BoardController controller) {
        final int boardSize = controller.getBoardSize();
        final int maxLogicalRow = maxLogicalRow(boardSize);

        double boardWidth = ROW_LENGTH * CELL_WIDTH + (ROW_LENGTH - 1) * HGAP;
        double boardHeight = (maxLogicalRow + 1) * CELL_HEIGHT + maxLogicalRow * VGAP;

        final GridPane cellLayer = buildCellLayer(controller, boardSize, maxLogicalRow);

        this.pieceLayer = new Pane();
        this.pieceLayer.setPrefSize(boardWidth, boardHeight);

        final StackPane stackRoot = new StackPane(cellLayer, this.pieceLayer);
        stackRoot.setAlignment(Pos.CENTER);
        stackRoot.setMaxSize(boardWidth, boardHeight);
        this.root = stackRoot;
    }

    private static LogicalCoords toLogicalCoords(final int position) {
        final int zeroIndexed = position - 1;
        final int group = zeroIndexed / GROUP_SIZE;
        final int offset = zeroIndexed % GROUP_SIZE;
        final boolean leftToRight = group % 2 == 0;
        final boolean isCorner = offset == ROW_LENGTH;

        if (isCorner) {
            final int col = leftToRight ? ROW_LENGTH - 1 : 0;
            return new LogicalCoords(2 * group + 1, col);
        }
        final int col = leftToRight ? offset : (ROW_LENGTH - 1 - offset);
        return new LogicalCoords(2 * group, col);
    }

    private static int maxLogicalRow(final int boardSize) {
        return toLogicalCoords(boardSize).row();
    }

    private static GridCoords toGridCoords(final int position, final int maxLogicalRow) {
        final LogicalCoords logical = toLogicalCoords(position);
        return new GridCoords(maxLogicalRow - logical.row(), logical.col());
    }

    private static Color cellTypeToColor(final String cellType) {
        return switch (cellType) {
            case "SPECIAL" -> COLOR_SPECIAL;
            case "PRISON" -> COLOR_PRISON;
            default -> COLOR_NORMAL;
        };
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
    public void updatePlayerPositions(Map<String, Integer> playerPositions) {
        final Map<Integer, List<String>> colorsByPosition = new HashMap<>();
        for (final Map.Entry<String, Integer> entry : playerPositions.entrySet()) {
            colorsByPosition
                    .computeIfAbsent(entry.getValue(), pos -> new ArrayList<>())
                    .add(entry.getKey());
        }
        for (final Map.Entry<Integer, List<String>> entry : colorsByPosition.entrySet()) {
            placePiecesOnCell(entry.getKey(), entry.getValue());
        }
    }

    private GridPane buildCellLayer(final BoardController controller, final int boardSize, final int maxLogicalRow) {
        final GridPane grid = new GridPane();
        grid.setHgap(HGAP);
        grid.setVgap(VGAP);
        for (int pos = 1; pos <= boardSize; pos++) {
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

        cell.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        return cell;
    }

    private void placePiecesOnCell(final int position, final List<String> colors) {
        final StackPane cell = this.cellsByPosition.get(position);
        if (cell == null) {
            return;
        }

        final var bounds = cell.getBoundsInParent();
        final double centerX = bounds.getMinX() + (bounds.getWidth() / 2.0);
        final double centerY = bounds.getMinY() + (bounds.getHeight() / 2.0);

        final boolean sharedCell = colors.size() > 1;
        for (final String color : colors) {
            final double[] offset = sharedCell ? quadrantOffset(color) : new double[]{0, 0};
            movePiece(color, centerX + offset[0], centerY + offset[1]);
        }
    }

    private void movePiece(final String color, final double x, final double y) {
        if (!pieces.containsKey(color)) {
            final Circle piece = new Circle(PIECE_RADIUS);
            piece.setFill(Color.web(mapPieceColor(color)));
            piece.setStroke(Color.BLACK);
            pieces.put(color, piece);
            pieceLayer.getChildren().add(piece);
        }

        final Circle piece = pieces.get(color);
        piece.setLayoutX(x);
        piece.setLayoutY(y);
    }

    private record LogicalCoords(int row, int col) { }

    private record GridCoords(int row, int col) { }
}