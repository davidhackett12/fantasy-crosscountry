package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.League;
import com.fantasycrosscountry.fantasycrosscountry.models.Runner;
import com.fantasycrosscountry.fantasycrosscountry.models.Team;
import com.fantasycrosscountry.fantasycrosscountry.models.User;
import com.fantasycrosscountry.fantasycrosscountry.models.data.LeagueDao;
import com.fantasycrosscountry.fantasycrosscountry.models.data.RunnerDao;
import com.fantasycrosscountry.fantasycrosscountry.models.data.TeamDao;
import com.fantasycrosscountry.fantasycrosscountry.models.data.UserDao;
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

    @RequestMapping(value = "create/{leagueId}", method = RequestMethod.GET)
    public String createTeam(Model model){
        model.addAttribute(new Team());
        return "team/create";
    }

    @RequestMapping(value = "create/{leagueId}", method = RequestMethod.POST)
    public String processCreateTeam(Model model, @ModelAttribute Team team,
                                    @CookieValue(value = "user", defaultValue = "none") String username,
                                    @PathVariable int leagueId){

        User user = userDao.findByUsername(username);
        League league = leagueDao.findOne(leagueId);
        team.setUser(user);
        team.setLeague(league);
        teamDao.save(team);

        return "redirect:/league/"+ leagueId;

    }

    @RequestMapping(value = "addRunner/{teamId}", method = RequestMethod.GET)
    public String addRunner(Model model, @PathVariable int teamId,
                            @CookieValue(value = "user", defaultValue = "none") String username){
        User user = userDao.findByUsername(username);
        Team team = teamDao.findOne(teamId);
        if (!team.getUser().equals(user)){
            return "redirect:";
        }
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







}
