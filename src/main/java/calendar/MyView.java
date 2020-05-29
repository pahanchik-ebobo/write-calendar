package calendar;

import calendar.note.MyNotes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;


public class MyView {

    private ArrayList<MyPane> allCalendarDays;
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    public MyView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        init(currentYearMonth);
    }

    public void init(YearMonth yearMonth) {
        allCalendarDays = new ArrayList<>();

        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                MyPane ap = new MyPane();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);

                if (j == 5 || j == 6) {
                    ap.setStyle("-fx-border-color: grey; -fx-background-color: #c2c2c2");
                } else {
                    ap.setStyle("-fx-border-color: grey; -fx-background-color: white;");
                }
                allCalendarDays.add(ap);
            }
        }

        Text[] dayNames = new Text[]{new Text("Понедельник"), new Text("Вторник"),
                new Text("Среда"), new Text("Четверг"), new Text("Пятница"),
                new Text("Суббота"), new Text("Воскресенье"),};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        calendarTitle = new Text();
        Button previousMonth = new Button("<-");
        previousMonth.setStyle("-fx-base: blue;");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button("->");
        nextMonth.setStyle("-fx-base: blue;");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);

        populateCalendar(yearMonth);

        view = new VBox(new Pane(), new VBox(dayLabels, calendar, titleBar));
    }

    public void populateCalendar(YearMonth yearMonth) {

        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);

        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        for (MyPane ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            LocalDate date = calendarDate;
            Text dayText = new Text(String.valueOf(date.getDayOfMonth()));
            ap.setDate(calendarDate);
            AnchorPane.setTopAnchor(dayText, 5.0);
            AnchorPane.setLeftAnchor(dayText, 5.0);
            ap.getChildren().add(dayText);
            MyNotes myNotes = Main.getBaseData().getByDate(date);
            if (myNotes.getCountNotes() > 0) {
                Text notes = new Text(String.valueOf(myNotes.getCountNotes()));
                notes.setFill(Color.BLUE);
                notes.setFont(Font.font(32));
                AnchorPane.setTopAnchor(notes, 20.0);
                AnchorPane.setLeftAnchor(notes, 20.0);
                ap.getChildren().add(notes);
            }
            calendarDate = calendarDate.plusDays(1);
        }

        calendarTitle.setText(" " + Translate.getMonth(yearMonth) + " " + yearMonth.getYear() + " ");
    }

    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateController();
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateController();
    }

    public void updateController() {
        init(currentYearMonth);
        Main.getStage().setScene(new Scene(Main.getMyView().getView()));
    }

    public VBox getView() {
        return view;
    }
}
