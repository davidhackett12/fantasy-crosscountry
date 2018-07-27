package com.fantasycrosscountry.fantasycrosscountry.controller;


import com.fantasycrosscountry.fantasycrosscountry.models.League;
import com.fantasycrosscountry.fantasycrosscountry.models.User;
import com.fantasycrosscountry.fantasycrosscountry.models.data.LeagueDao;
import com.fantasycrosscountry.fantasycrosscountry.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("league")
public class LeagueController {

    @Autowired
    UserDao userDao;

    @Autowired
    LeagueDao leagueDao;

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
    public String view(Model model, @PathVariable int leagueId){
        League league = leagueDao.findOne(leagueId);
        model.addAttribute("Title", league.getName());
        model.addAttribute("members", league.getUsers());

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

        return "redirect:/league/" + league.getId();

    }


}
