package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AllgemeinerScheduler {

    public AllgemeinerScheduler(int everyXhours, MedikamenteSpeicher medikamenteSpeicher, Nachrichtenformat nachrichtenformat) {
        ErinnerungUndWarnungBeiAblaufUseCase erinnerung = new ErinnerungUndWarnungBeiAblaufUseCase(medikamenteSpeicher, nachrichtenformat);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> erinnerung.sendeErinnerungen();

        // Ausführung erfolgt alle x Stunden
        executor.scheduleAtFixedRate(task, 0, everyXhours, TimeUnit.HOURS);

    }

}
