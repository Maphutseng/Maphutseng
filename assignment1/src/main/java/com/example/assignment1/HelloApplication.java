package com.example.assignment1;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class HelloApplication extends Application {
    private int currentIndex = 0;
    private final String[] imagePaths = {
            Objects.requireNonNull(getClass().getResource("/images/A.jpg")).toExternalForm(),
            Objects.requireNonNull(getClass().getResource("/images/B.jpg")).toExternalForm(),
            Objects.requireNonNull(getClass().getResource("/images/C.jpg")).toExternalForm(),
            Objects.requireNonNull(getClass().getResource("/images/F.jpg")).toExternalForm(),
            Objects.requireNonNull(getClass().getResource("/images/E.jpg")).toExternalForm()
    };

    private final ImageView imageView = new ImageView();

    @Override
    public void start(Stage primaryStage) {

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("C:\\Users\\MAPHUTSENG\\IdeaProjects\\assignment1\\src\\main\\resources\\images\\back.jpg", 1920, 1080, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);


        imageView.setFitWidth(600);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setImage(new Image(imagePaths[currentIndex]));

        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        prevButton.setOnAction(e -> showPreviousImage());
        nextButton.setOnAction(e -> showNextImage());

        HBox buttonBox = new HBox(20, prevButton, nextButton);
        buttonBox.setStyle("-fx-alignment: center;");

        HBox thumbnailBox = new HBox(15);
        thumbnailBox.setStyle("-fx-alignment: center;");

        for (int i = 0; i < imagePaths.length; i++) {
            int index = i;
            ImageView thumbnail = new ImageView(new Image(imagePaths[i]));
            thumbnail.setFitWidth(80);
            thumbnail.setFitHeight(80);
            thumbnail.setPreserveRatio(true);
            thumbnail.setSmooth(true);

            thumbnail.setOnMouseEntered(e -> {
                thumbnail.setScaleX(1.2);
                thumbnail.setScaleY(1.2);
            });
            thumbnail.setOnMouseExited(e -> {
                thumbnail.setScaleX(1);
                thumbnail.setScaleY(1);
            });

            thumbnail.setOnMouseClicked(e -> setImage(index));
            thumbnailBox.getChildren().add(thumbnail);
        }

        VBox bottomBox = new VBox(10, thumbnailBox, buttonBox);
        bottomBox.setStyle("-fx-alignment: center;");

        BorderPane root = new BorderPane();
        root.setCenter(imageView);
        root.setBottom(bottomBox);
        root.setBackground(background);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Responsive Image Gallery with Background");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.widthProperty().addListener((obs, oldVal, newVal) -> adjustImageSize(scene));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> adjustImageSize(scene));
    }

    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            rotateAndChangeImage();
        }
    }

    private void showNextImage() {
        if (currentIndex < imagePaths.length - 1) {
            currentIndex++;
            rotateAndChangeImage();
        }
    }

    private void setImage(int index) {
        currentIndex = index;
        rotateAndChangeImage();
    }

    private void rotateAndChangeImage() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), imageView);
        rotateTransition.setByAngle(360);
        rotateTransition.setOnFinished(e -> {
            imageView.setImage(new Image(imagePaths[currentIndex]));
        });
        rotateTransition.play();
    }

    private void adjustImageSize(Scene scene) {
        double width = scene.getWidth() * 0.75;
        double height = scene.getHeight() * 0.6;
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
