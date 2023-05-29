package Jason.Mini.Project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;

@Repository
public class FlightBookingRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    public JsonObject insertTravelerInfoRepo(JsonObject travelerInfo) {
        return mongoTemplate.save(travelerInfo);
    }

}
