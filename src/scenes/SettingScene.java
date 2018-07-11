package scenes;

import design.Colors;
import design.Labels;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.NotEnoughSongsException;
import model.Ranking;
import model.SongLibrary;
import scenes.elements.BackButton;
import scenes.elements.ReButton;

import java.io.File;
import java.util.HashSet;
import java.util.Observer;

public class SettingScene extends BaseReactScene {
    
    private ObservableList<String> musicSrcs;
    private ListView<String> musicSrcsView;
    private SongLibrary songLibrary;
    private StackPane bg;
    private File tempFile;
    private ReButton commit;
    private ReButton cancel;
    private BackButton backButton;
    private SimpleBooleanProperty hasChanges;
    private Label info;
    private ReButton resetRanking;
    
    public SettingScene(Observer o) {
        super("Einstellungen", o);
        this.init();
    }
    
    public SettingScene(ObservableScene observableScene) {
        super("Einstellungen", observableScene);
        this.init();
    }
    
    private void init() {
        this.musicSrcs = FXCollections.observableArrayList();
        try {
            this.songLibrary = new SongLibrary();
            
            this.musicSrcs.setAll(songLibrary.getLibrary());
        } catch (NotEnoughSongsException e) {
        }
        
        this.hasChanges = new SimpleBooleanProperty(false);
        this.hasChanges.addListener((o, ov, nv) -> {
            this.commit.setDisable(! nv);
            if (nv) {
                if (musicSrcs.size() >= SongLibrary.MIN_SONG_COUNT) {
                    fadeInNode(commit);
                }
            } else {
                fadeOutNode(commit);
            }
        });
        this.commit = new ReButton("Speichern");
        this.commit.setOnAction(event -> {
            if (this.musicSrcs.size() < SongLibrary.MIN_SONG_COUNT) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Sie benötigen noch " + (SongLibrary.MIN_SONG_COUNT - this.musicSrcs.size())
                                + " Lieder um spielen zu können.",
                        ButtonType.OK);
                alert.showAndWait();
                return;
            }
            String[] strings = new String[this.musicSrcs.size()];
            for (int i = 0; i < this.musicSrcs.size(); i++) {
                strings[i] = this.musicSrcs.get(i);
            }
            try {
                songLibrary.setLib(strings);
            } catch (NullPointerException e) {
                songLibrary = new SongLibrary(strings);
            }
            this.musicSrcs.setAll(this.songLibrary.getLibrary());
            this.hasChanges.set(false);
        });
        this.cancel = new ReButton("Verwerfen");
        this.cancel.setOnAction(event -> {
            this.musicSrcs.setAll(this.songLibrary.getLibrary());
            this.hasChanges.set(false);
        });

        HBox commitBtns = new HBox(cancel, commit);
        // ranking
        this.resetRanking = new ReButton("Ranking resetten");
        resetRanking.setOnAction(event -> {
            new Ranking().reset();
        });
        //
        VBox buttons = new VBox(commitBtns, this.resetRanking);
        
        this.musicSrcsView = new ListView<>();
        this.musicSrcsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.musicSrcsView.setEditable(true);
        this.musicSrcsView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                ObservableList<Integer> rev = this.musicSrcsView.getSelectionModel().getSelectedIndices();
                
                for(int idx = rev.size() - 1; idx >= 0; idx--){
                    this.musicSrcs.remove(this.musicSrcsView.getItems().get(rev.get(idx)));
                }
            }
        });
        
        this.musicSrcs.addListener((ListChangeListener<String>) c -> {
            if (musicSrcs.size() < SongLibrary.MIN_SONG_COUNT) {
                fadeOutNode(commit);
            } else {
                fadeInNode(commit);
            }
        });
        
        GridPane grid = getGrid();
        
        info = Labels.M_SMALL.getLabel("Loslassen um hinzuzufügen", Colors.LABEL_MENU);
        
        this.bg = new StackPane(info, musicSrcsView);
        grid.add(bg, 0, 1);
        grid.add(buttons, 0, 2);
        this.musicSrcsView.setMinWidth(500);
        
        this.musicSrcsView.setItems(this.musicSrcs);
        
        this.musicSrcsView.setOnDragEntered(event -> {
            fadeNode(this.musicSrcsView, this.musicSrcsView.getOpacity(), 0.3);
            event.consume();
        });
        
        this.musicSrcsView.setOnDragExited(event -> {
            fadeInNode(this.musicSrcsView);
            event.consume();
        });
        
        this.musicSrcsView.setOnDragOver(event -> {
            musicSrcsView.setEffect(new GaussianBlur(5));
            
            event.acceptTransferModes(TransferMode.LINK);
            event.consume();
        });
        
        this.musicSrcsView.setOnDragDropped(event -> {
            musicSrcsView.setEffect(new GaussianBlur(0));
            fadeInNode(this.musicSrcsView);
            
            Dragboard dragboard = event.getDragboard();
            HashSet<String> set = new HashSet<>();
            for (File f : dragboard.getFiles()) {
                set.add(f.getAbsolutePath());
            }
            set.addAll(this.musicSrcs);
            this.musicSrcs.setAll(set);
            hasChanges.set(true);
            event.setDropCompleted(true);
            event.consume();
        });
    }
    
}
