package com.crypto;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DashboardController {
    private final PriceService priceService;
    private final Map<String, CryptoPrice> cryptoMap;
    private final GridPane gridPane;
    private final Label statusLabel;
    private final Label clockLabel;
    private Timeline timeline;

    // Color scheme
    private static final Color BG_DARK = Color.web("#0a0e27");
    private static final Color CARD_BG = Color.web("#151a30");
    private static final Color GREEN_UP = Color.web("#00d084");
    private static final Color RED_DOWN = Color.web("#ff4757");
    private static final Color TEXT_WHITE = Color.web("#ffffff");
    private static final Color TEXT_GRAY = Color.web("#8892b0");

    public DashboardController() {
        this.priceService = new PriceService();
        this.cryptoMap = new LinkedHashMap<>();
        
        // Initialize tracked cryptocurrencies
        cryptoMap.put("BTCUSDT", new CryptoPrice("BTCUSDT", "Bitcoin"));
        cryptoMap.put("ETHUSDT", new CryptoPrice("ETHUSDT", "Ethereum"));
        cryptoMap.put("SOLUSDT", new CryptoPrice("SOLUSDT", "Solana"));
        cryptoMap.put("BNBUSDT", new CryptoPrice("BNBUSDT", "BNB"));
        cryptoMap.put("XRPUSDT", new CryptoPrice("XRPUSDT", "Ripple"));
        cryptoMap.put("DOGEUSDT", new CryptoPrice("DOGEUSDT", "Dogecoin"));
        cryptoMap.put("ADAUSDT", new CryptoPrice("ADAUSDT", "Cardano"));
        cryptoMap.put("DOTUSDT", new CryptoPrice("DOTUSDT", "Polkadot"));
        
        cryptoMap.put("LINKUSDT", new CryptoPrice("LINKUSDT", "Chainlink"));
        cryptoMap.put("AVAXUSDT", new CryptoPrice("AVAXUSDT", "Avalanche"));
        cryptoMap.put("MATICUSDT", new CryptoPrice("MATICUSDT", "Polygon"));
        cryptoMap.put("UNIUSDT", new CryptoPrice("UNIUSDT", "Uniswap"));

        this.gridPane = createGrid();
        this.statusLabel = createStatusLabel();
        this.clockLabel = createClockLabel();
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #0a0e27;");
        return grid;
    }

    private Label createStatusLabel() {
        Label label = new Label("Initializing...");
        label.setFont(Font.font("Segoe UI", 14));
        label.setTextFill(TEXT_GRAY);
        return label;
    }

    private Label createClockLabel() {
        Label label = new Label("--:--:--");
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        label.setTextFill(TEXT_GRAY);
        return label;
    }

    public Scene createScene() {
        VBox root = new VBox(15);
        root.setStyle("-fx-background-color: #0a0e27;");
        root.setPadding(new Insets(20));

        // Header
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("CRYPTO DASHBOARD");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(TEXT_WHITE);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(title, spacer, clockLabel, statusLabel);
        
        // Build cards
        buildCards();

        root.getChildren().addAll(header, gridPane);
        
        Scene scene = new Scene(root, 1000, 700);
        scene.setFill(BG_DARK);
        
        return scene;
    }

    private void buildCards() {
        int col = 0, row = 0;
        for (CryptoPrice crypto : cryptoMap.values()) {
            VBox card = createCard(crypto);
            gridPane.add(card, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createCard(CryptoPrice crypto) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setMinWidth(220);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
            "-fx-background-color: #151a30;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #1e2540;" +
            "-fx-border-width: 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 4);"
        );

        // Symbol label
        Label symbolLabel = new Label(crypto.getSymbol().replace("USDT", ""));
        symbolLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        symbolLabel.setTextFill(TEXT_WHITE);
        symbolLabel.setId("symbol-" + crypto.getSymbol());

        // Full name
        Label nameLabel = new Label(crypto.getName());
        nameLabel.setFont(Font.font("Segoe UI", 12));
        nameLabel.setTextFill(TEXT_GRAY);

        // Price
        Label priceLabel = new Label("---");
        priceLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 26));
        priceLabel.setTextFill(TEXT_WHITE);
        priceLabel.setId("price-" + crypto.getSymbol());

        // Change indicator
        Label changeLabel = new Label("---");
        changeLabel.setFont(Font.font("Consolas", 14));
        changeLabel.setTextFill(TEXT_GRAY);
        changeLabel.setId("change-" + crypto.getSymbol());

        // Last update
        Label updateLabel = new Label("Last: --:--:--");
        updateLabel.setFont(Font.font("Segoe UI", 10));
        updateLabel.setTextFill(TEXT_GRAY);
        updateLabel.setId("update-" + crypto.getSymbol());

        card.getChildren().addAll(symbolLabel, nameLabel, priceLabel, changeLabel, updateLabel);
        
        // Store reference to card for color updates
        card.setId("card-" + crypto.getSymbol());
        
        return card;
    }

    public void startUpdates() {
        // Immediate first fetch
        fetchAllPrices();
        
        // Then every second
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> fetchAllPrices())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void fetchAllPrices() {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        
        for (CryptoPrice crypto : cryptoMap.values()) {
            CompletableFuture<Void> future = priceService.fetchPrice(crypto.getSymbol())
                .thenAccept(price -> {
                    if (price != null) {
                        Platform.runLater(() -> updateUI(crypto, price));
                    }
                });
            futures.add(future);
        }

        // Update status when all complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenRun(() -> Platform.runLater(() -> {
                statusLabel.setText("Live • Updated " + java.time.LocalTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
                statusLabel.setTextFill(GREEN_UP);
            }));
    }

    private void updateUI(CryptoPrice crypto, double newPrice) {
        crypto.updatePrice(newPrice);

        Label priceLabel = (Label) gridPane.lookup("#price-" + crypto.getSymbol());
        Label changeLabel = (Label) gridPane.lookup("#change-" + crypto.getSymbol());
        Label updateLabel = (Label) gridPane.lookup("#update-" + crypto.getSymbol());
        VBox card = (VBox) gridPane.lookup("#card-" + crypto.getSymbol());

        if (priceLabel != null) {
            priceLabel.setText(String.format("$%,.2f", newPrice));
            
            // Flash effect for price change
            if (crypto.isPriceUp()) {
                priceLabel.setTextFill(GREEN_UP);
                flashCard(card, GREEN_UP);
            } else if (crypto.isPriceDown()) {
                priceLabel.setTextFill(RED_DOWN);
                flashCard(card, RED_DOWN);
            } else {
                priceLabel.setTextFill(TEXT_WHITE);
            }
        }

        if (changeLabel != null) {
            double change = crypto.getChangePercent();
            if (Math.abs(change) > 0.0001) {
                String sign = change > 0 ? "+" : "";
                changeLabel.setText(String.format("%s%.3f%%", sign, change));
                changeLabel.setTextFill(change > 0 ? GREEN_UP : RED_DOWN);
            }
        }

        if (updateLabel != null) {
            updateLabel.setText("Last: " + crypto.getLastUpdated());
        }

        // Update clock
        clockLabel.setText(java.time.LocalTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    private void flashCard(VBox card, Color color) {
        // Subtle border flash animation
        String baseStyle = 
            "-fx-background-color: #151a30;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: 2;" +
            "-fx-effect: dropshadow(gaussian, %s, 15, 0, 0, 0);";
        
        String flashColor = color.toString().replace("0x", "#");
        card.setStyle(String.format(baseStyle, flashColor, flashColor + "40"));
        
        // Reset after 300ms
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
            Duration.millis(300)
        );
        pause.setOnFinished(e -> card.setStyle(
            "-fx-background-color: #151a30;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #1e2540;" +
            "-fx-border-width: 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 4);"
        ));
        pause.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}