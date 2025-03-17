package by.lms.libraryms.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Objects;

@Data
public abstract class AbstractDomainClass {
    @Id
    private String id;

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        AbstractDomainClass that = (AbstractDomainClass) object;
        return id.equals(that.id) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
