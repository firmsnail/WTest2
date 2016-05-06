package com.worksap.stm2016.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Person.class)
public class Person_ {
	public static volatile ListAttribute<Person, Skill> skillList;
}
