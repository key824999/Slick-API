package toy.slick.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface ApiKeyRepository extends MongoRepository<ApiKeyRepository.ApiKey, String> {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class ApiKey extends MongoData<ApiKey> {
        private String apiKey;
        private String email;
        private String role;
        private int requestCntPer30Sec;
        private boolean useYn;
        private String expiredZonedDateTime;

        @Override
        public ApiKey toMongoData(String _id) {
            this._id = _id;
            this._timestamp = new Timestamp(System.currentTimeMillis());

            return this;
        }
    }

    @Query("{'apiKey' :  ?0}")
    Optional<ApiKey> findByApiKey(String requestApiKey);
}
