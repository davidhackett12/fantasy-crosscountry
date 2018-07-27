package com.fantasycrosscountry.fantasycrosscountry.models.data;

import com.fantasycrosscountry.fantasycrosscountry.models.League;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LeagueDao extends CrudRepository<League,Integer> {
}
