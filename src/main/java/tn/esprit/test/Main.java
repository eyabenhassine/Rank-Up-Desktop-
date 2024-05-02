package tn.esprit.test;

import tn.esprit.entities.Subscription_plan;
import tn.esprit.services.Subscription_PlanService;
import tn.esprit.util.MaConnexion;

public class Main {
    public static void main(String[] args) {
        Subscription_PlanService sp = new Subscription_PlanService();
        Subscription_plan subplan = new Subscription_plan("ll", "gng" , 654 );
        sp.add(subplan);
        sp.update(subplan);

        System.out.println(sp.getAll());
    }
}