package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.*;
import com.fantasycrosscountry.fantasycrosscountry.models.comparators.PerformanceComparator;
import com.fantasycrosscountry.fantasycrosscountry.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("commissioner")
public class CommissionerController {

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    RunnerDao runnerDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RaceDao raceDao;

    @Autowired
    PerformanceDao performanceDao;

    @Autowired
    TeamScoreDao teamScoreDao;

    @RequestMapping(value = "{leagueId}")
    public String index(Model model, @PathVariable int leagueId){
        League league = leagueDao.findOne(leagueId);
        model.addAttribute("league", league);
        return "commissioner/index";
    }

    @RequestMapping(value = "addRunners/{leagueId}", method = RequestMethod.GET)
    public String addRunners(){
        return "commissioner/addRunners";
    }

    @RequestMapping(value = "addRunners/{leagueId}", method = RequestMethod.POST)
    public String processAddRunners(String file, @PathVariable int leagueId){
        CsvConverter csvConverter = new CsvConverter();
        League league = leagueDao.findOne(leagueId);
        HashMap<String, String> runnerList = csvConverter.convertCsvToJava(file);
        for (Map.Entry<String, String> entry : runnerList.entrySet()){
            Runner runner = new Runner();
            runner.setName(entry.getKey());
            runner.setHighSchool(entry.getValue());
            runner.setLeague(league);
            runnerDao.save(runner);
        }
        return "redirect:";
    }

    @RequestMapping(value = "editRunnerList/{leagueId}", method = RequestMethod.GET)
    public String editRunnersList(Model model, @PathVariable int leagueId){
        League league = leagueDao.findOne(leagueId);
        model.addAttribute("runners", league.getRunners());
        model.addAttribute("league", league);
        model.addAttribute(new Runner());
        return "commissioner/editRunnerList";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteRunner(int deletedRunnerId){
        Runner runner = runnerDao.findOne(deletedRunnerId);
        int leagueId = runner.getLeague().getId();
        runnerDao.delete(runner);
        return "redirect:/commissioner/editRunnerList/"+ leagueId;
    }

    @RequestMapping(value = "createRunner", method = RequestMethod.POST)
    public String createRunner(@ModelAttribute Runner runner, int leagueId){
        League league = leagueDao.findOne(leagueId);
        runner.setLeague(league);
        runnerDao.save(runner);
        return "redirect:/commissioner/editRunnerList/" + leagueId;
    }

    @RequestMapping(value = "addRace/{leagueId}", method = RequestMethod.GET)
    public String addRace(Model model, @PathVariable int leagueId){
        League league = leagueDao.findOne(leagueId);
        model.addAttribute("league", league);
        model.addAttribute(new Race());
        return "commissioner/addRace";
    }

    @RequestMapping(value= "addRace/{leagueId}", method = RequestMethod.POST)
    public String processAddRace(@ModelAttribute Race race, @PathVariable int leagueId){
        League league = leagueDao.findOne(leagueId);
        race.setLeague(league);
        raceDao.save(race);
        for (Team team : league.getTeams()){
            TeamScore teamScore = new TeamScore();
            teamScore.setScore(0);
            teamScore.setRace(race);
            teamScore.setTeam(team);
            teamScoreDao.save(teamScore);
        }
        return "redirect:/commissioner/manageRace/"+league.getId()+"/"+race.getId();
    }

    @RequestMapping(value = "manageRace/{leagueId}/{raceId}", method = RequestMethod.GET)
    public String manageRace(Model model, @PathVariable int leagueId, @PathVariable int raceId){
        League league = leagueDao.findOne(leagueId);
        Race race = raceDao.findOne(raceId);
        List<Runner> competingRunners = new ArrayList<>();
        for (Team team : league.getTeams()){
            for (Runner runner : team.getStarters()){
                competingRunners.add(runner);
            }
        }
        model.addAttribute("competingRunners", competingRunners);
        model.addAttribute("performances", race.getPerformances());
        model.addAttribute("teamScores", race.getTeamScores());
        model.addAttribute(new Performance());
        return "commissioner/manageRace";
    }
    @RequestMapping(value = "manageRace/{leagueId}/{raceId}", method = RequestMethod.POST)
    public String processManageRace(@PathVariable int leagueId, @PathVariable int raceId,
                                    int runnerId, @ModelAttribute Performance performance){

        Runner runner = runnerDao.findOne(runnerId);
        Race race = raceDao.findOne(raceId);
        League league = leagueDao.findOne(leagueId);
        performance.setTime();
        performance.setRunner(runner);
        performance.setRace(race);
        performanceDao.save(performance);
        PerformanceComparator performanceComparator = new PerformanceComparator();
        race.getPerformances().sort(performanceComparator);
        int n = 1;
        for (Performance performanceToSort : race.getPerformances()) {
            performanceToSort.setPlace(n);
            performanceDao.save(performanceToSort);
            n++;
        }
        for (TeamScore teamScore : race.getTeamScores()) {
            int currentScore = 0;
            for (Performance performanceToScore : race.getPerformances()){
                int scoreToAdd = 0;
                if (performanceToScore.getRunner().getTeam().equals(teamScore.getTeam())) {
                    scoreToAdd =+ performanceToScore.getPlace();
                }
                currentScore += scoreToAdd;
            }
            teamScore.setScore(currentScore);
            teamScoreDao.save(teamScore);
        }
        return "redirect:/commissioner/manageRace/"+league.getId()+"/"+race.getId();
    }

}

