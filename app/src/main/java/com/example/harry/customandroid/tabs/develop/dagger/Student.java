package com.example.harry.customandroid.tabs.develop.dagger;

public class Student {

    public int id;

    public Student() {
        this.id = -1;
    }

    public Student(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }
}
