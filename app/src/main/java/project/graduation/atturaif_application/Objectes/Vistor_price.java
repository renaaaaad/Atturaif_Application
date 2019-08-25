package project.graduation.atturaif_application.Objectes;

public class Vistor_price {
    String type ;
    int price , discount ;

    public Vistor_price(String type, int price, int discount) {
        this.type = type;
        this.price = price;
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
} // class
