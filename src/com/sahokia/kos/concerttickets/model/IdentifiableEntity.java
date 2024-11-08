package com.sahokia.kos.concerttickets.model;
import com.sahokia.kos.concerttickets.annotations.NullableWarning;
import com.sahokia.kos.concerttickets.validation.FieldValidator;

public abstract class IdentifiableEntity {

    @NullableWarning(message = "Variable [%s] is null in [%s]!%n")
    protected String id;

    protected IdentifiableEntity() {
        id = null;
        FieldValidator.validateFieldsForNullable(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can not be null");
        }
        this.id = id;
    }
}