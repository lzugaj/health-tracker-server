package com.example.healthtracker.model.bmi;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("adults")
public class BodyMassIndexRangeInfoForAdults extends BodyMassIndexRangeInfo {
}
