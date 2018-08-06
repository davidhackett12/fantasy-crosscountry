package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.*;
import com.fantasycrosscountry.fantasycrosscountry.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("league")
public class LeagueController {

    @Autowired
    UserDao userDao;

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    RaceDao raceDao;

    @Autowired
    RunnerDao runnerDao;

    @Autowired
    TeamDao teamDao;

    // to display and process creating a league //
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createLeague(Model model){
        model.addAttribute(new League());
        return "league/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreate(Model model, @ModelAttribute League league,
                                @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        league.setCommissioner(user);
        user.addLeague(league);
        leagueDao.save(league);
        userDao.save(user);

        return "redirect:/league/"+league.getId();

    }


    // just viewing a league //
    @RequestMapping(value = "{leagueId}")
    public String index(Model model, @PathVariable int leagueId,
                        @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        League league = leagueDao.findOne(leagueId);

        boolean hasTeam = false;
        for (Team team : user.getTeams()){
            boolean inLeague = false;
            if (team.getLeague().equals(league)){
                inLeague = true;
            }
            hasTeam = inLeague;
        }

        if (!hasTeam){
            return "redirect:/team/create/"+leagueId;
        }


        model.addAttribute("Title", league.getName());
        model.addAttribute("teams", league.getTeams());
        model.addAttribute("league", league);

        return "league/index";
    }

    // joining a league //
    @RequestMapping(value = "join/{leagueId}")
    public String join(@PathVariable int leagueId,
                       @CookieValue(value = "user", defaultValue = "none") String username){

        if (username.equals("none")){
            return "redirect:/home/login";
        }

        User user = userDao.findByUsername(username);
        League league = leagueDao.findOne(leagueId);
        user.addLeague(league);
        userDao.save(user);

        return "redirect:/team/create/" + league.getId();

    }

    @RequestMapping(value = "addRunners/{leagueId}", method = RequestMethod.GET)
    public String addRunners(){
        return "league/addRunners";
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



    @RequestMapping(value = "results/{raceId}")
    public String viewResults(Model model, @PathVariable int raceId){
        Race race = raceDao.findOne(raceId);
        model.addAttribute("race", race);
        return "league/results";
    }


}
