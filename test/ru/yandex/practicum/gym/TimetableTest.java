package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

public class TimetableTest {

    private static Timetable timetable;

    @BeforeEach
    void ListUp() {
        timetable = new Timetable();
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        int sizeTrainingForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        Assertions.assertEquals(1, sizeTrainingForMonday,
                "Должно было вернуться одно занятие а вернулось " + sizeTrainingForMonday
        );

        //Проверить, что за вторник не вернулось занятий
        int sizeTrainingForTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(0, sizeTrainingForTuesday,
                "Должно было вернуться 0 занятие а вернулось " + sizeTrainingForTuesday
        );
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        int sizeTrainingForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        Assertions.assertEquals(1, sizeTrainingForMonday,
                "Должно было вернуться 1 занятие а вернулось " + sizeTrainingForMonday
        );

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        int sizeTrainingForThursday = trainingSessionsForDay.size();
        Assertions.assertEquals(2, sizeTrainingForThursday,
                "Должно было вернуться 2 занятия а вернулось " + sizeTrainingForThursday
        );

        List<TimeOfDay> expectedTimeDays = List.of(new TimeOfDay(13, 0), new TimeOfDay(20, 0));
        List<TimeOfDay> actualTimeDays = List.of(trainingSessionsForDay.firstKey(), trainingSessionsForDay.lastKey());

        Assertions.assertEquals(expectedTimeDays, actualTimeDays,
                "Правильный порядок: сначала в 13:00, потом в 20:00 " + actualTimeDays
        );

        // Проверить, что за вторник не вернулось занятий
        int sizeTrainingForTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(0, sizeTrainingForTuesday,
                "Должно было вернуться 0 занятий а вернулось " + sizeTrainingForTuesday
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        int TrainingSessionsCountMonday13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size();
        Assertions.assertEquals(1, TrainingSessionsCountMonday13,
                "Должно было вернуться 1 занятие а вернулось " + TrainingSessionsCountMonday13
        );

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        int TrainingSessionsCountMonday14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size();
        Assertions.assertEquals(0, TrainingSessionsCountMonday14,
                "Должно было вернуться 0 занятие а вернулось " + TrainingSessionsCountMonday14
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeWithMultipleSessions() {
        Coach coachVasiliy = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Coach coachArtem = new Coach("Минеев", "Артем", "Валиевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 45);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachArtem,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        List<TrainingSession> trainingSessionsForDayAndTime = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)
        );

        Assertions.assertEquals(2, trainingSessionsForDayAndTime.size(),
                "Должно было вернуться 2 в заданный день и время занятие а вернулось " +
                        trainingSessionsForDayAndTime.size()
        );
    }

    @Test
    void testGetUniqTrainingSessionsForDayAndTimeWithMultipleSessions() {
        Coach coachVasiliy = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.ADULT, 45);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        List<TrainingSession> trainingSessionsForDayAndTime = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)
        );

        Assertions.assertEquals(1, trainingSessionsForDayAndTime.size(),
                "Должно было вернуться 1 в заданный день и время занятие а вернулось " +
                        trainingSessionsForDayAndTime.size()
        );
    }

    @Test
    void testAddNewTrainingSessionForThursday() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(
                group, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)
        );
        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size(),
                "На четверг запись не создалась"
        );
    }
}
