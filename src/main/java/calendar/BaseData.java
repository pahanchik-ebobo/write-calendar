package calendar;

import calendar.note.MyNotes;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseData {
    private Map<LocalDate, MyNotes> allBaseNotes;

    public BaseData() {
        allBaseNotes = new HashMap<>();
        addForTest();
    }

    public void addEmptyNote(LocalDate localDate) {
        allBaseNotes.putIfAbsent(localDate, new MyNotes());
    }

    public MyNotes getByDate(LocalDate localDate) {
        if (!allBaseNotes.containsKey(localDate)) {
            addEmptyNote(localDate);
        }
        return allBaseNotes.get(localDate);
    }

    private void addForTest() {
        LocalDate localDate = LocalDate.now();
        MyNotes myNotes = getByDate(localDate);
        myNotes.addNote("Важная встреча днём");
        myNotes.addNote("Забрать посылку");
        myNotes.addNote("Позвонить брату");
    }
}
