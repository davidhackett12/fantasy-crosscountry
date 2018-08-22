package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.*;
import com.fantasycrosscountry.fantasycrosscountry.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("team")
public class TeamController {

    @Autowired
    UserDao userDao;

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    RunnerDao runnerDao;

    @Autowired
    OverallScoreDao overallScoreDao;

    @RequestMapping(value = "create/{leagueId}", method = RequestMethod.GET)
    public String createTeam(Model model, @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute(new Team());
        return "team/create";
    }

    @RequestMapping(value = "create/{leagueId}", method = RequestMethod.POST)
    public String processCreateTeam(Model model, @ModelAttribute Team team,
                                    @CookieValue(value = "user", defaultValue = "none") String username,
                                    @PathVariable int leagueId){

        User user = userDao.findByUsername(username);
        League league = leagueDao.findOne(leagueId);
        OverallScore overallScore = new OverallScore();
        team.setUser(user);
        team.setLeague(league);
        teamDao.save(team);
        overallScore.setLeague(league);
        overallScore.setTeam(team);
        overallScoreDao.save(overallScore);


        return "redirect:/league/"+ leagueId;

    }

    @RequestMapping(value = "addRunner/{teamId}", method = RequestMethod.GET)
    public String addRunner(Model model, @PathVariable int teamId,
                            @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        Team team = teamDao.findOne(teamId);
        if (!team.getUser().equals(user)){
            return "redirect:";
        }
        model.addAttribute("team", team);
        model.addAttribute("teamRoster", team.getRunners());
        List<Runner> availableRunners = runnerDao.findByLeague(team.getLeague());
        model.addAttribute("availableRunners", availableRunners);

        return "team/addRunner";
    }
    @RequestMapping(value="addRunner/{teamId}", method = RequestMethod.POST)
    public String processAddRunner( Model model,
                                    @PathVariable int teamId,
                                   Integer droppedRunnerId,
                                   Integer runnerId) {
        if (droppedRunnerId != null) {
            Runner droppedRunner = runnerDao.findOne(droppedRunnerId);
            droppedRunner.setTeam(null);
            runnerDao.save(droppedRunner);
        }
        if (runnerId != null) {
        Team team = teamDao.findOne(teamId);
        List<Runner> teamRoster = team.getRunners();
        if (teamRoster.size() < 10) {

            Runner runner = runnerDao.findOne(runnerId);
            runner.setTeam(team);
            runnerDao.save(runner);
        } else{
            model.addAttribute("error", "You must choose a runner to drop in order to add a runner");
            }
        }
        return "redirect:/team/addRunner/" + teamId;

    }

    @RequestMapping(value = "setLineup/{teamId}", method = RequestMethod.GET)
    public String setLineup(Model model, @PathVariable int teamId,
                            @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        Team team = teamDao.findOne(teamId);
        model.addAttribute("team", team);
        return "team/setLineup";
    }

    @RequestMapping(value = "setLineup/{teamId}", method =RequestMethod.POST)
    public String processSetLineup(Model model,@PathVariable int teamId, Integer removedRunnerId, Integer addedRunnerId){
        if (removedRunnerId != null) {
            Runner removedRunner = runnerDao.findOne(removedRunnerId);
            removedRunner.setLineup(null);
            runnerDao.save(removedRunner);
        }
        if (addedRunnerId != null) {
            Team team = teamDao.findOne(teamId);
            List<Runner> teamLineup = team.getStarters();
            if (teamLineup.size() < 7) {

                Runner runner = runnerDao.findOne(addedRunnerId);
                runner.setLineup(team);
                runnerDao.save(runner);
            } else{
                model.addAttribute("error", "You must choose a runner to remove from your lineup first");
            }
        }
        return "redirect:/team/setLineup/" + teamId;
    }

    @RequestMapping(value = "{teamId}")
    public String index(Model model,@PathVariable int teamId,
                        @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        Team team = teamDao.findOne(teamId);
        model.addAttribute("team", team);
        return "team/index";
    }

    @RequestMapping(value = "viewRunner/{runnerId}")
    public String viewRunner(Model model, @PathVariable int runnerId,
                             @CookieValue(value= "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        Runner runner = runnerDao.findOne(runnerId);
        model.addAttribute("runner", runner);
        return "team/viewRunner";

    }
}
