package com.fantasycrosscountry.fantasycrosscountry.models.data;

import com.fantasycrosscountry.fantasycrosscountry.models.OverallScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OverallScoreDao extends CrudRepository<OverallScore, Integer> {
}
