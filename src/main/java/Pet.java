import javax.json.*;
import java.util.*;
public class Pet {

    //only using these in this context so static class is appropriate
    public static class Catagory{
      public int id;
      public String name;

        public Catagory(){
            id = 0;
            name = "DEFAULT";
        }

        public Catagory(String name, int id){
            this.name = name;
            this.id = id;
        }
    }
    public static class Tag{
       public int id;
       public String name;

        public String toString(){
            return "{" + "\"id\"" + id + ",\n\"name\": \"" + name + "\"\n}";
        }
    }
    public static final String[] Statuses = {
            "Availible",
            "Pending",
            "Sold"};

    public String self_as_json = "";
    private Catagory catagory;
    private List<String> photos;
    private List<Tag> tags;
    private String status;
    private String name;
    private int id;

    public Pet(Catagory catagory, List photos, List tags, int statusID, String name, int id){
        this.catagory = catagory;
        this.photos = photos;
        this.tags = tags;
        this.status = Statuses[statusID];
        this.name = name;
        this.id = id;
    }

    public Pet(){
        name = "DEFAULT_NAME";
        id = 0xFFFFFFFF;
        status = Statuses[2];
    }


    public void setCatagory(Catagory cat){
        catagory = cat;
    }
    public Catagory getCatagory(){
        return catagory;
    }
    public void setPhotos(List<String> photo){
        photos = photo;
    }
    public List<String> getPhotos(){
        return photos;
    }
    public void setStatus(int statusID){
        status = Statuses[statusID];
    }
    public void setStatus(String status){
        boolean statusExists = false;
        for(String str : Statuses)
            if(str == status)
                statusExists = true;
        if(statusExists)
            this.status = status;
    }
    public String getStatus(){
        return status;
    }

    public String ListToJsonArray(List list){
        String PsuedoJsonArray = "[\n";
        for(Object o : list){
            PsuedoJsonArray = "\"" + o.toString() + "\"" + ",\n";
        }
        PsuedoJsonArray = PsuedoJsonArray.substring(0,PsuedoJsonArray.length()-2);
        PsuedoJsonArray += "\n]";
        return PsuedoJsonArray;
    }

    public JsonObject ExportAsJSON(){
        JsonObject obj = Json.createObjectBuilder().add("id",id).add("category",Json.createObjectBuilder()
                .add("id",catagory.id).add("name",catagory.name))
                .add("name", name).add("photoUrls", ListToJsonArray(photos)).add("tags",ListToJsonArray(tags))
                .add("status", getStatus()).build();/*tbc*/

        return obj;
    }
    /*
    {
  "id": 0,
  "category": {
    "id": 0,
    "name": "string"
  },
  "name": "doggie",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 0,
      "name": "string"
    }
  ],
  "status": "available"
}
     */
}
