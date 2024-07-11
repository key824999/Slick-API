package toy.slick.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Timestamp;

public interface DJIRepository extends MongoRepository<DJIRepository.DowJonesIndustrialAverage, String> {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class DowJonesIndustrialAverage extends MongoData<DowJonesIndustrialAverage> {
        private String price;
        private String priceChange;
        private String priceChangePercent;

        @Override
        public DowJonesIndustrialAverage toMongoData(String _id) {
            this._id = _id;
            this._timestamp = new Timestamp(System.currentTimeMillis());

            return this;
        }
    }
}
