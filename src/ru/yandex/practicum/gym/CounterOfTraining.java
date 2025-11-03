package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTraining implements Comparable<CounterOfTraining> {

    private final Coach coach;

    private int count;

    public CounterOfTraining(Coach coach) {
        this.coach = coach;
        count++;
    }

    @Override
    public int compareTo(CounterOfTraining o) {
        return Integer.compare(count, o.count);
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTraining that = (CounterOfTraining) o;
        return count == that.count && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }

    public void plusCount() {
        this.count++;
    }

    @Override
    public String toString() {
        return "CounterOfTraining{" +
                "coach=" + coach +
                ", count=" + count +
                '}';
    }
}
