package com.example.dagger2example;

import dagger.Component;

@Component (modules = {WheelsModule.class, WheelsModule.class})
public interface CarComponent {


void inject(MainActivity mainActivity);
}
