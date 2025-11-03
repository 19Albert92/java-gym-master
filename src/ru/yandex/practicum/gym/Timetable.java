package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    private final List<CounterOfTraining> counterOfTrainingList = new ArrayList<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, List<TrainingSession>> trainsOfDay = timetable.getOrDefault(
                trainingSession.getDayOfWeek(), new TreeMap<>()
        );

        if (trainsOfDay.isEmpty()) {

            addCounterOfTraining(trainingSession);

            ArrayList<TrainingSession> trainingSessions = new ArrayList<>();

            trainingSessions.add(trainingSession);

            trainsOfDay.put(trainingSession.getTimeOfDay(), trainingSessions);

        } else {
            List<TrainingSession> sessionsByTimeOfDay = trainsOfDay.getOrDefault(
                    trainingSession.getTimeOfDay(), new ArrayList<>()
            );
            trainsOfDay.put(trainingSession.getTimeOfDay(),
                    getUniqListByCoachWithDateTime(sessionsByTimeOfDay, trainingSession));
        }

        timetable.put(trainingSession.getDayOfWeek(), trainsOfDay);
    }

    private void addCounterOfTraining(TrainingSession trainingSession) {

        for (CounterOfTraining counterOfTraining : counterOfTrainingList) {
            if (counterOfTraining.getCoach().equals(trainingSession.getCoach())) {
                counterOfTraining.plusCount();
                return;
            }
        }

        counterOfTrainingList.add(new CounterOfTraining(trainingSession.getCoach()));
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {

        LinkedHashMap<Coach, Integer> result = new LinkedHashMap<>();

        Collections.sort(counterOfTrainingList);

        for (CounterOfTraining counterOfTraining : counterOfTrainingList) {
            result.put(counterOfTraining.getCoach(), counterOfTraining.getCount());
        }

        return result;
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> trainsByDayOfWeek = timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        if (trainsByDayOfWeek.isEmpty()) return Collections.emptyList();

        return trainsByDayOfWeek.getOrDefault(timeOfDay, Collections.emptyList());
    }

    private List<TrainingSession> getUniqListByCoachWithDateTime(List<TrainingSession> trainsOfTime,
                                                                 TrainingSession newTrainingSession) {
        //Не занят ли couch в этот день и это время
        for (TrainingSession trainingSession : trainsOfTime) {
            if (trainingSession.getCoach().equals(newTrainingSession.getCoach()) &&
                    trainingSession.getDayOfWeek().equals(newTrainingSession.getDayOfWeek()) &&
                    trainingSession.getTimeOfDay().equals(newTrainingSession.getTimeOfDay())) {
                return trainsOfTime;
            }
        }

        trainsOfTime.add(newTrainingSession);

        addCounterOfTraining(newTrainingSession);

        return trainsOfTime;
    }
}
