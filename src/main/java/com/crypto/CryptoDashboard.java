package com.crypto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CryptoDashboard extends Application {
    private DashboardController controller;

    @Override
    public void start(Stage primaryStage) {
        controller = new DashboardController();
        
        Scene scene = controller.createScene();
        
        primaryStage.setTitle("Real-Time Crypto Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.show();
        
        // Start fetching data
        controller.startUpdates();
        
        // Handle window close
        primaryStage.setOnCloseRequest(e -> {
            controller.stop();
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}