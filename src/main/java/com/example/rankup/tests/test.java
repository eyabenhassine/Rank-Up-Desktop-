package com.example.rankup.tests;

import com.example.rankup.entities.Equipe;
import com.example.rankup.entities.MatchEntity;
import com.example.rankup.services.EquipeService;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import com.example.rankup.services.*;

public class test {

    public static void main(String[] args) {
        MatchService matchService = new MatchService();
        MatchEntity matchentity = new MatchEntity();
        matchService.ajouter(matchentity);
        System.out.println("Successful!!");
    }
}

