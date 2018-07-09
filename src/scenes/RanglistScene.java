package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Ranking;
import model.User;

import java.util.Observer;

public class RanglistScene extends BaseReactScene {
    
    private Ranking ranking;
    private TableView<User> rankingView;
    private ObservableList<User> list;
    
    public RanglistScene(Ranking ranking, Observer o) {
        super("Rangliste", o);
        this.ranking = ranking;
        this.init();
    }
    
    public RanglistScene(Ranking ranking, ObservableScene o) {
        super("Rangliste", o);
        this.ranking = ranking;
        this.init();
    }
    
    private void init() {
        GridPane gridPane = getGrid();
        
        this.rankingView = new TableView<>();
        TableColumn<User, String> userCol = new TableColumn<>("User");
        userCol.setMinWidth(150);
        userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<User, String> pointsCol = new TableColumn<>("Punkte");
        pointsCol.setMinWidth(50);
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        TableColumn<User, String> dateCol = new TableColumn<>("Datum");
        dateCol.setMinWidth(150);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("datePlayedFormatted"));
        
        list = FXCollections.observableArrayList(this.ranking.getRanking());
        this.rankingView.setItems(list);
        rankingView.getColumns().addAll(userCol, pointsCol, dateCol);
        
        gridPane.add(rankingView, 0, 0);
        
    }
}
