package org.wildfly.examples.swarm.jaxrs;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


/**
 * @author Scott Marlow
 */
@Path("/Hello")
public class MyResource {

    @Inject @Named("mongodbtestprofile")
        MongoDatabase database;

    @GET
    @Produces("text/plain")
    public String get() {
        return "Howdy at " + new DateTime();
    }

    @GET
    @Path("/createDiaryEntry")
    @Produces("text/plain")
    public String createDiaryEntry() {
        MongoCollection collection = null;
        Document query = null;
        try {
            String who = "Felix";
            Document entry = new Document("_id", who)
                    .append("name", who)

                    .append("entry", "Friday, August 24, added MongoDB example.");
            // save the comment
            collection = database.getCollection("diary");
            collection.insertOne(entry);

            query = new Document("_id", who);
            FindIterable cursor = collection.find(query);
            Object diaryEntry = cursor.first();
            return diaryEntry.toString();
        } finally {
            collection.drop();
        }
    }

}
