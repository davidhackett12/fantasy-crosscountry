package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.*;
import com.fantasycrosscountry.fantasycrosscountry.models.comparators.PerformanceComparator;
import com.fantasycrosscountry.fantasycrosscountry.models.comparators.ScoreComparator;
import com.fantasycrosscountry.fantasycrosscountry.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index(Model model, @PathVariable int leagueId,
                        @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        League league = leagueDao.findOne(leagueId);
        model.addAttribute("league", league);
        return "commissioner/index";
    }

    @RequestMapping(value = "addRunners/{leagueId}", method = RequestMethod.GET)
    public String addRunners(Model model, @PathVariable int leagueId,
                             @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        League league = leagueDao.findOne(leagueId);
        model.addAttribute("league", league);
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
        return "redirect:/commissioner/editRunnerList/" + leagueId;
    }

    @RequestMapping(value = "editRunnerList/{leagueId}", method = RequestMethod.GET)
    public String editRunnersList(Model model, @PathVariable int leagueId,
                                  @CookieValue(value = "user", defaultValue = "none") String username){

        if (username.equals("none")){
            return "redirect:/home/login";
        }
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
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
    public String addRace(Model model, @PathVariable int leagueId,
                          @CookieValue(value = "user", defaultValue = "none") String username){

        if (username.equals("none")){
            return "redirect:/home/login";
        }
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
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
        return "redirect:/commissioner/manageRace/"+race.getId();
    }

    @RequestMapping(value = "manageRace/{raceId}", method = RequestMethod.GET)
    public String manageRace(Model model, @PathVariable int raceId,
                             @CookieValue(value = "user", defaultValue = "none") String username){

        if (username.equals("none")){
            return "redirect:/home/login";
        }
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        Race race = raceDao.findOne(raceId);
        League league = race.getLeague();
        List<Runner> competingRunners = new ArrayList<>();
        for (Team team : league.getTeams()){
            for (Runner runner : team.getStarters()){
                competingRunners.add(runner);
            }
        }
        model.addAttribute("competingRunners", competingRunners);
        model.addAttribute("race" , race);
        model.addAttribute("league", league);
        model.addAttribute(new Performance());
        return "commissioner/manageRace";
    }
    @RequestMapping(value = "manageRace/{raceId}", method = RequestMethod.POST)
    public String processManageRace(@PathVariable int raceId,
                                    int runnerId, @ModelAttribute Performance performance){

        Runner runner = runnerDao.findOne(runnerId);
        Race race = raceDao.findOne(raceId);
        Team team = runner.getTeam();
        League league = race.getLeague();
        performance.setTime();
        performance.setRunner(runner);
        performance.setRace(race);
        performance.setTeam(team);
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
        return "redirect:/commissioner/manageRace/"+race.getId();
    }

    @RequestMapping(value = "finalizeRace/{raceId}", method = RequestMethod.POST)
    public String finalizeRace(@PathVariable int raceId){
        Race race = raceDao.findOne(raceId);
        League league = race.getLeague();

        for (OverallScore overallScore : league.getOverallScores()){
            overallScore.updateScore();
        }
        ScoreComparator scoreComparator = new ScoreComparator();
        league.getOverallScores().sort(scoreComparator);
        int n = 1;
        for (OverallScore overallScore : league.getOverallScores()){
            overallScore.setPlace(n);
            n++;
        }
        return "redirect:/commissioner/"+league.getId();

    }

}

