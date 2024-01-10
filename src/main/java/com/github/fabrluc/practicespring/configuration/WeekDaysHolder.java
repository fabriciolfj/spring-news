package com.github.fabrluc.practicespring.configuration;

import com.github.fabrluc.practicespring.entities.SimpleWeeksDays;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class WeekDaysHolder {

    @Value("${monday}")
    private SimpleWeeksDays monday;
    @Value("${tuesday}")
    private SimpleWeeksDays tuesday;
    @Value("${wednesday}")
    private SimpleWeeksDays wednesday;
    @Value("${thursday}")
    private SimpleWeeksDays thursday;
    @Value("${friday}")
    private SimpleWeeksDays friday;
    @Value("${saturday}")
    private SimpleWeeksDays saturday;
    @Value("${sunday}")
    private SimpleWeeksDays sunday;
}