package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Recording type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Recordings")
public final class Recording implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField DATE = field("date");
  public static final QueryField USER = field("recordingUserId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime date;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "recordingUserId", type = User.class) User user;
  public String getId() {
      return id;
  }
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public User getUser() {
      return user;
  }
  
  private Recording(String id, Temporal.DateTime date, User user) {
    this.id = id;
    this.date = date;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Recording recording = (Recording) obj;
      return ObjectsCompat.equals(getId(), recording.getId()) &&
              ObjectsCompat.equals(getDate(), recording.getDate()) &&
              ObjectsCompat.equals(getUser(), recording.getUser());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDate())
      .append(getUser())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Recording {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("user=" + String.valueOf(getUser()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Recording justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Recording(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      date,
      user);
  }
  public interface BuildStep {
    Recording build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep date(Temporal.DateTime date);
    BuildStep user(User user);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Temporal.DateTime date;
    private User user;
    @Override
     public Recording build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Recording(
          id,
          date,
          user);
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        this.user = user;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Temporal.DateTime date, User user) {
      super.id(id);
      super.date(date)
        .user(user);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  
}
