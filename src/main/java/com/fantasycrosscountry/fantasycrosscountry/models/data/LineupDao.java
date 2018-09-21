package com.fantasycrosscountry.fantasycrosscountry.models.data;

import com.fantasycrosscountry.fantasycrosscountry.models.Lineup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LineupDao extends CrudRepository<Lineup, Integer> {
}
