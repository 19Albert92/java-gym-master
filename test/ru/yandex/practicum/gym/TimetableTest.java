package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        int sizeTrainingForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        Assertions.assertEquals(1, sizeTrainingForMonday, "Должно было вернуться одно занятие а вернулось " + sizeTrainingForMonday);

        //Проверить, что за вторник не вернулось занятий
        int sizeTrainingForTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(0, sizeTrainingForTuesday, "Должно было вернуться 0 занятие а вернулось " + sizeTrainingForTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

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
        Timetable timetable = new Timetable();

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
}
