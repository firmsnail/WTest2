package com.worksap.stm2016.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Person_;
import com.worksap.stm2016.model.Skill;

public class PersonSpecification {
	
	public static Specification<Person> hasSkill(Skill skill) {
		return new Specification<Person>() {

			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.isMember(skill, root.get(Person_.skillList));		// root.<Date>get("")
			}
			
		};
	}
	
}
