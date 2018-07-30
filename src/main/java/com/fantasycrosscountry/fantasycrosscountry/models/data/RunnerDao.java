package com.fantasycrosscountry.fantasycrosscountry.models.data;

import com.fantasycrosscountry.fantasycrosscountry.models.League;
import com.fantasycrosscountry.fantasycrosscountry.models.Runner;
import com.fantasycrosscountry.fantasycrosscountry.models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RunnerDao extends CrudRepository<Runner, Integer> {

    List<Runner> findByTeam(Team team);
    List<Runner> findByLeague(League league);
}
