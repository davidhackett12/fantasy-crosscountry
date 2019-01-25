package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.*;
import com.fantasycrosscountry.fantasycrosscountry.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    RaceDao raceDao;

    @Autowired
    OverallScoreDao overallScoreDao;

    @Autowired
    LineupDao lineupDao;

    @Autowired
    TradeProposalDao tradeProposalDao;

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
    @RequestMapping(value="lineupMenu/{teamId}", method = RequestMethod.GET)
    public String setLineupMenu(Model model,@PathVariable int teamId,
                                @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }
        Team team = teamDao.findOne(teamId);
        model.addAttribute("lineups", team.getLineups());
        return "team/lineupMenu";
    }

    @RequestMapping(value = "setLineup/{lineupId}", method = RequestMethod.GET)
    public String setLineup(Model model, @PathVariable int lineupId,
                            @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }
        Race race = lineupDao.findOne(lineupId).getRace();
        Team team = lineupDao.findOne(lineupId).getTeam();
        Lineup weeklyLineup = null;
        for (Lineup lineup : race.getLineups()){
            if (lineup.getTeam()== team){
                weeklyLineup = lineup;
            }
        }
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        List<Runner> starters = weeklyLineup.getRunners();
        model.addAttribute("starters", starters);
        List<Runner> bench = new ArrayList<>();
        for (Runner runner : team.getRunners()){
            if (!starters.contains(runner)){
                bench.add(runner);
            }
        }
        model.addAttribute("bench", bench);
        model.addAttribute("team", team);
        return "team/setLineup";
    }

    @RequestMapping(value = "setLineup/{lineupId}", method =RequestMethod.POST)
    public String processSetLineup(Model model,@PathVariable int lineupId,Integer removedRunnerId, Integer addedRunnerId){
        Race race = lineupDao.findOne(lineupId).getRace();
        Team team = lineupDao.findOne(lineupId).getTeam();
        Lineup changedLineup = null;
        String error = "";
        for (Lineup lineup : race.getLineups()){
            if (lineup.getTeam()== team){
                changedLineup = lineup;
            }
        }
        if (removedRunnerId != null && changedLineup != null) {
            Runner removedRunner = runnerDao.findOne(removedRunnerId);
            removedRunner.removeLineup(changedLineup);
            runnerDao.save(removedRunner);
        }
        if (addedRunnerId != null && changedLineup != null) {
            if (changedLineup.getRunners().size() < 7) {

                Runner runner = runnerDao.findOne(addedRunnerId);
                runner.addLineup(changedLineup);
                runnerDao.save(runner);
            } else{
                error = "This is an error";
            }
        }
        model.addAttribute("error", error);
        return "redirect:/team/setLineup/" + lineupId;
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
        if (user == team.getUser()){
            model.addAttribute("teamOwner", true);
        } else {
            model.addAttribute("teamOver", false);
        }


        return "team/index";
    }

    @RequestMapping(value = "proposeTrade/{teamId}", method = RequestMethod.GET )
    public String proposeTrade(Model model,@PathVariable int teamId,
                        @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }
        Team proposeeTeam = teamDao.findOne(teamId);
        Team proposerTeam = null;
        League league = proposeeTeam.getLeague();
        User user = userDao.findByUsername(username);
        for (Team team : user.getTeams()){
            if (team.getLeague().equals(league)){
                proposerTeam = team;
            }
        }

        model.addAttribute("proposeeTeam", proposeeTeam);
        model.addAttribute("proposerTeam", proposerTeam);

        return "team/proposeTrade";

    }

    @RequestMapping(value = "proposeTrade/{teamId}", method = RequestMethod.POST)
    public String processTradeProposal(@PathVariable int teamId,
            int[] proposerRunnerId, int[] proposeeRunnerId, int proposerTeamId ){


        if (proposeeRunnerId.length < 1 || proposerRunnerId.length< 1){
            return "redirect:/proposeTrade/" + teamId;
        }

        Team proposerTeam = teamDao.findOne(proposerTeamId);

        TradeProposal tradeProposal = new TradeProposal(proposerTeam);
        tradeProposalDao.save(tradeProposal);

        for (int runnerId : proposerRunnerId){
            Runner runnerTradeAway = runnerDao.findOne(runnerId);
            runnerTradeAway.setTrade_away(tradeProposal);
            runnerDao.save(runnerTradeAway);
        }

        for (int oppRunnerId : proposeeRunnerId){
            Runner runnerTradeFor = runnerDao.findOne(oppRunnerId);
            runnerTradeFor.setTrade_for(tradeProposal);
            runnerDao.save(runnerTradeFor);
        }

        if (proposerRunnerId.length < proposeeRunnerId.length){
            return "redirect:/team/tradeDropRunner/" + tradeProposal.getId();
        }

        return "redirect:/team/confirmTrade/" +tradeProposal.getId();

    }

    @RequestMapping(value = "tradeDropRunner/{tradeProposalId}", method = RequestMethod.GET)
    public String tradeDropRunner(Model model, @PathVariable int tradeProposalId,
                                  @CookieValue(value = "user", defaultValue = "none") String username){
        if (username.equals("none")){
            return "redirect:/home/login";
        }

        TradeProposal tradeProposal = tradeProposalDao.findOne(tradeProposalId);
        User user = userDao.findByUsername(username);
        List<Runner> runnerList = new ArrayList<>();
        if (tradeProposal.getProposee().getUser().equals(user)){
            for (Runner runner : tradeProposal.getProposee().getRunners()){
                if (!runner.getTrade_for().equals(tradeProposal)){
                    runnerList.add(runner);
                }
            }
        } else {
            for (Runner runner : tradeProposal.getProposer().getRunners()){
                if (!runner.getTrade_away().equals(tradeProposal)){
                    runnerList.add(runner);
                }
            }
        }

        model.addAttribute(runnerList);
        model.addAttribute(tradeProposal);
        return "team/tradeDropRunner";


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
