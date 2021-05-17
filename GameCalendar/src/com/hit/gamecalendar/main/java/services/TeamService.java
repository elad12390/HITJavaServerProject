package com.hit.gamecalendar.main.java.services;
import com.hit.gamecalendar.main.java.dao.TeamModel;
import com.hit.gamecalendar.main.java.repositories.TeamRepository;
import com.hit.gamecalendar.main.java.services.abstracts.BaseService;

import java.util.List;

public class TeamService extends BaseService<TeamModel, TeamRepository> {

    public TeamService() {
        super(new TeamRepository());
    }

    public List<TeamModel> getAllTeams() {
        return this.repo.getAll();
    }

    public TeamModel getTeamById(int id) {
        return this.repo.getItemById(id);
    }

    public Long createTeam(TeamModel teamModel) {
        return this.repo.createTableItem(teamModel);
    }

    public boolean updateTeam(int id, TeamModel teamModel) {
        return this.repo.updateTableItem(id, teamModel);
    }

    public boolean deleteTeam(int id) {
        return this.repo.deleteTableItem(id);
    }
}
