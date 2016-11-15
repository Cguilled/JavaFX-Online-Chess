import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.*; 
import javafx.animation.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;

public class ChessGUI extends Application 
{
    public static void main(String[] args) 
    {
        // Automatic VM reset, thanks to Joseph Rachmuth.
        try
        {
            launch(args);
            System.exit(0);
        }
        catch (Exception error)
        {
            error.printStackTrace();
            System.exit(0);
        }
    }

    // Global constants
    final boolean WHITE = true;
    final boolean BLACK = false;
    
    public void start(Stage mainStage) 
    {
        mainStage.setTitle("Chess Game");
        //mainStage.getIcons().add( new Image("assets/icons/app_icon.png") );

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 400, 425);
        mainStage.setScene(mainScene);

        VBox vbox = new VBox();
        vbox.setAlignment( Pos.TOP_CENTER );
        root.setCenter(vbox);

        //draws board differently if White or Black
        ChessBoard board = new ChessBoard(WHITE);

        vbox.getChildren().add(board);
        
        // Menu -----------------------
        MenuBar menuBar = new MenuBar();
        root.setTop(menuBar);

        Menu gameMenu = new Menu("Game");
        menuBar.getMenus().add(gameMenu);

        MenuItem menuItemNewGame = new MenuItem("New Game");
        menuItemNewGame.setOnAction(e -> onNewGame());
        //menuItemNewGame.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemNewGame.setAccelerator( new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN) );
        gameMenu.getItems().add(menuItemNewGame);

        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        //menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator( new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN) );
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem("About");
        //menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        menuItemAbout.setAccelerator( new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN) ); // BUG: Key combo not working
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        MenuItem menuItemHelp = new MenuItem("Help");
        //menuItemHelp.setGraphic( new ImageView( new Image("assets/icons/help.png", 16, 16, true, true) ) );
        menuItemHelp.setAccelerator( new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN) );
        menuItemHelp.setOnAction(e -> onDisplayHelp());
        menuHelp.getItems().add(menuItemHelp);

        // custom code above --------------------------------------------
        mainStage.show();
    }

    // Start a new game
    public void onNewGame()
    {
        String playerType;

        // TODO: If a chess game is currently ongoing, warn that
        //         "Starting a new game while a match is in progress will count as a forfiet."
        //         "Do you still want to start a new game?"
        //            "Yes"   "No"
        //
        //       If no, just break/return and ignore the following code

        // Prompt user for new game
        Alert newGameAlert = new Alert(AlertType.CONFIRMATION);
        newGameAlert.setTitle("Start new game");
        newGameAlert.setHeaderText(null);
        newGameAlert.setContentText("Pick game type");

        ButtonType buttonTypeWhite = new ButtonType("Play White (Server)");
        ButtonType buttonTypeBlack = new ButtonType("Play Black (Client)");
        //ButtonType buttonTypeOffline = new ButtonType("Play Offline");

        newGameAlert.getButtonTypes().setAll(buttonTypeWhite, buttonTypeBlack);
        Optional<ButtonType> result = newGameAlert.showAndWait();

        if (result.get() == buttonTypeWhite)
            playerType = "White";
        else if (result.get() == buttonTypeBlack)
            playerType = "Black";
        //else if (result.get() == buttonTypeOffline)
        //  playerType = "Offline";

    }

    // Quits program
    public void onQuit()
    {
        Platform.exit();
        System.exit(0);
    }

    // Display 'about' menu
    public void onDisplayAbout()
    {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("About this program");
        infoAlert.setHeaderText(null); 

        // set window icon
        Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add( new Image("assets/icons/about.png") );

        // the graphic replaces the standard icon on the left
        //infoAlert.setGraphic( new ImageView( new Image("assets/icons/cat.png", 64, 64, true, true) ) );

        infoAlert.setContentText("Programmed by Maxwell Sirotin and Steven Vascellaro.\n" +
            "Chess Icons by \"Colin M.L. Burnett\".");
        infoAlert.showAndWait();
    }

    // Display 'help' menu
    public void onDisplayHelp()
    {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Help");
        infoAlert.setHeaderText(null); 
        infoAlert.setContentText("This is a simple networked chess program.\n" +
            "To start, pick a color to play as.");
        infoAlert.showAndWait();
    }
}