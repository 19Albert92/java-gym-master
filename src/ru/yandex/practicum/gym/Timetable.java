package ru.yandex.practicum.gym;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, List<TrainingSession>> trainsOfDay = timetable.getOrDefault(
                trainingSession.getDayOfWeek(), new TreeMap<>()
        );

        if (trainsOfDay.isEmpty()) {
            trainsOfDay.put(trainingSession.getTimeOfDay(), List.of(trainingSession));
        } else {
            List<TrainingSession> sessionsByDayOfWeek = trainsOfDay.getOrDefault(
                    trainingSession.getTimeOfDay(), new ArrayList<>()
            );

            sessionsByTimeOfDay.add(trainingSession);

            trainsOfDay.put(trainingSession.getTimeOfDay(), sessionsByTimeOfDay);
        }

        timetable.put(trainingSession.getDayOfWeek, trainsOfDay);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }
}
