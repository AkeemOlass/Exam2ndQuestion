import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageAnimator extends Application {
    protected TextField tfSpeed = new TextField();
    protected TextField tfPrefix = new TextField();
    protected TextField tfNumberOfImages = new TextField();
    protected TextField tfURL = new TextField();
    protected StackPane paneForImage = new StackPane();
    protected Timeline animation;
    protected int n = 1;
    private final static String URLBase = "http://cs.armstrong.edu/liang/common";


    public static void main(String[] args) {
        launch(args);
    }

        @Override // Override the start method in the Application class
        public void start(Stage primaryStage) {
            final int COLUMN_COUNT = 27;
            tfSpeed.setPrefColumnCount(COLUMN_COUNT);
            tfPrefix.setPrefColumnCount(COLUMN_COUNT);
            tfNumberOfImages.setPrefColumnCount(COLUMN_COUNT);
            tfURL.setPrefColumnCount(COLUMN_COUNT);

            // Create a button
            Button btStart = new Button("Start Animation");

            // Create a grid pane for labels and text fields
            GridPane paneForInfo = new GridPane();
            paneForInfo.setAlignment(Pos.CENTER);
            paneForInfo.add(new Label("Enter information for animation"), 0, 0);
            paneForInfo.add(new Label("Animation speed in milliseconds"), 0, 1);
            paneForInfo.add(tfSpeed, 1, 1);
            paneForInfo.add(new Label("Image file prefix"), 0, 2);
            paneForInfo.add(tfPrefix, 1, 2);
            paneForInfo.add(new Label("Number of images"), 0, 3);
            paneForInfo.add(tfNumberOfImages, 1, 3);
            paneForInfo.add(new Label("Audio file URL"), 0, 4);
            paneForInfo.add(tfURL, 1, 4);


            // Create a border pane
            BorderPane pane = new BorderPane();
            pane.setBottom(paneForInfo);
            pane.setCenter(paneForImage);
            pane.setTop(btStart);
            pane.setAlignment(btStart, Pos.TOP_RIGHT);

            // Create animation
            animation = new Timeline(
                    new KeyFrame(Duration.millis(1000), e -> nextImage()));
            animation.setCycleCount(Timeline.INDEFINITE);

            // Create and register the handler
            btStart.setOnAction(e -> {
                if (tfURL.getText().length() > 0) {
                    MediaPlayer mediaPlayer = new MediaPlayer(
                            new Media(tfURL.getText()));
                    mediaPlayer.play();
                }
                if (tfSpeed.getText().length() > 0)
                    animation.setRate(Integer.parseInt(tfSpeed.getText()));
                animation.play();
            });

            // Create a scene and place it in the stage
            Scene scene = new Scene(pane, 550, 680);
            primaryStage.setTitle("Question 2"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage
        }

        /** Place imageView in pane */
    private void getImage() {
        paneForImage.getChildren().clear();
        paneForImage.getChildren().add(new ImageView(new Image(
                URLBase +
                        "/image" + tfPrefix.getText()
                         + n + ".gif")));
    }

    /** Load next image */
    private void nextImage() {
        n = n < Integer.parseInt(
                tfNumberOfImages.getText()) ? n += 1 : 1;
        getImage();
    }
}
