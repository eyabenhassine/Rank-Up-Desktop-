package tn.esprit.entities;

public class Subscription_plan {
    int id;
    String type, additional_info;
    float prix;

    public Subscription_plan() {
        this.id = id;
        this.type = type;
        this.additional_info = additional_info;
        this.prix = prix;
    }

    public Subscription_plan(String type, String additional_info, float prix) {
        this.type = type;
        this.additional_info = additional_info;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_plan(String additional_plan) {
        this.additional_info = additional_info;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Subscription_plan{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", additional_info='" + additional_info + '\'' +
                ", prix=" + prix +
                '}';
    }
}
