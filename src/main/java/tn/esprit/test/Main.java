package tn.esprit.test;

import tn.esprit.models.Equipe;
import tn.esprit.services.EquipeService;
import tn.esprit.services.PlayersService;

import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
//        DBConnection connection = new DBConnection();

        EquipeService equipeService = new EquipeService();
        List<Equipe> equipes = equipeService.recuperer();
        System.out.println(equipes);
    }

}