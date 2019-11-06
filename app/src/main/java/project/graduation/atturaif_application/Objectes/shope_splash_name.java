package project.graduation.atturaif_application.Objectes;

public class shope_splash_name {
    String id , name , image,des ;

    public shope_splash_name(String id, String name, String image,String des) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.des=des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
} // class
