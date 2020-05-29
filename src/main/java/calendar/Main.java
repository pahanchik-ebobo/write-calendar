package calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.YearMonth;

public class Main extends Application {
    private static Stage stage;
    private static BaseData baseData;
    private static MyView myView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("RGR OOP");
        myView = new MyView(YearMonth.now());
        primaryStage.setScene(new Scene(myView.getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        baseData = new BaseData();
        launch(args);
    }

    public static MyView getMyView() {
        return myView;
    }

    public static BaseData getBaseData() {
        return baseData;
    }

    public static Stage getStage() {
        return stage;
    }
}
