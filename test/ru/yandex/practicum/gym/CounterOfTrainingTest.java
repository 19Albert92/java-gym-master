package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class CounterOfTrainingTest {

    private static Timetable timetable;

    @BeforeEach
    void ListUp() {
        timetable = new Timetable();
    }

    @Test
    void testGetCountByCoachesSingleCoachMultipleSessionTrainings() {
        Coach coachVasiliy = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.ADULT, 45);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        HashMap<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(2, countByCoaches.get(coachVasiliy),
                "У трененера Николая Васильева должно быть 2 тренировки"
        );
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSortedDescending() {
        Coach coachVasiliy = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession adultTrainingSession = new TrainingSession(groupAdult, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(adultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 45);
        TrainingSession childTrainingSession = new TrainingSession(groupChild, coachVasiliy,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(childTrainingSession);
        Coach coachPetya = new Coach("Иванов", "Петор", "Иванович");
        Group groupChild2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        TrainingSession child2TrainingSession = new TrainingSession(groupChild2, coachPetya,
                DayOfWeek.MONDAY, new TimeOfDay(16, 0));

        timetable.addNewTrainingSession(child2TrainingSession);

        HashMap<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(2, countByCoaches.get(coachVasiliy),
                "У трененера Николая Васильева должно быть 2 тренировки"
        );

        Assertions.assertEquals(1, countByCoaches.get(coachPetya),
                "У трененера Николая Васильева должно быть 1 тренировки"
        );

        System.out.println(countByCoaches);
    }

    @Test
    void testGetCountByCoachesNoTrainings() {
        Coach coachPetya = new Coach("Иванов", "Петор", "Иванович");

        HashMap<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertNull(countByCoaches.get(coachPetya),
                "У трененера Николая Васильева должно быть null тренировки"
        );
    }

    @Test
    void testGetCountByCoachesUniqTrainings() {
        Coach coachVasiliy = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession adultTrainingSession = new TrainingSession(groupAdult, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(adultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 45);
        TrainingSession childTrainingSession = new TrainingSession(groupChild, coachVasiliy,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(childTrainingSession);

        HashMap<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(1, countByCoaches.get(coachVasiliy));
    }

}
