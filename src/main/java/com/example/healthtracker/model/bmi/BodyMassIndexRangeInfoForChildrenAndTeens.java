package com.example.healthtracker.model.bmi;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("children_and_teens")
public class BodyMassIndexRangeInfoForChildrenAndTeens extends BodyMassIndexRangeInfo {
}
