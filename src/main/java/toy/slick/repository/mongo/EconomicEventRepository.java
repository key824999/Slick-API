package toy.slick.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface EconomicEventRepository extends MongoRepository<EconomicEventRepository.EconomicEvent, String> {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class EconomicEvent extends MongoData<EconomicEvent> {
        private Date dateUTC;
        private String id;
        private String name;
        private String country;
        private String importance;
        private String actual;
        private String forecast;
        private String previous;

        @Override
        public EconomicEvent toMongoData(String _id) {
            this._id = _id;
            this._timestamp = new Timestamp(System.currentTimeMillis());

            return this;
        }
    }

    @Query("{'dateUTC' : {'$gte' : ?0, '$lt' :  ?1}}")
    List<EconomicEvent> findAll(Date gteDateUTC, Date ltDateUTC);
}
